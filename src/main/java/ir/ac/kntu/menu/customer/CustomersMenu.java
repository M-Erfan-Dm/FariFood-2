package ir.ac.kntu.menu.customer;

import ir.ac.kntu.menu.Menu;
import ir.ac.kntu.menu.order.FruitShopAddOrderMenu;
import ir.ac.kntu.menu.order.RestaurantAddOrderMenu;
import ir.ac.kntu.menu.order.SupermarketAddOrderMenu;
import ir.ac.kntu.menu.settings.SettingsMenu;
import ir.ac.kntu.models.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CustomersMenu extends Menu {
    private final Customer customer;

    private final ShopsDBReference shopsDBReference;

    public CustomersMenu(Customer customer, ShopsDBReference shopsDBReference) {
        this.customer = customer;
        this.shopsDBReference = shopsDBReference;
    }

    @Override
    public void show() {
        CustomersMenuOption option;
        while ((option = printMenuOptions("Customer Menu", CustomersMenuOption.class))
                != CustomersMenuOption.LOGOUT) {
            if (option != null) {
                switch (option) {
                    case ADD_ORDER:
                        addOrder();
                        break;
                    case UPDATE_ORDER_FEEDBACK:
                        updateOrderFeedback();
                        break;
                    case FEEDBACKS_HISTORY:
                        showFeedbacksHistory();
                        break;
                    case ORDERS_HISTORY:
                        showOrdersHistory();
                        break;
                    case SETTINGS:
                        SettingsMenu settingsMenu = new SettingsMenu(customer.getSettings());
                        settingsMenu.show();
                        break;
                    default:
                        break;
                }
            }
        }
    }

    private void addOrder() {
        printEnumOptions(ShopType.class);
        ShopType shopType = getOption(ShopType.class);
        if (shopType != null) {
            switch (shopType) {
                case RESTAURANT:
                    RestaurantAddOrderMenu restaurantAddOrderMenu = new RestaurantAddOrderMenu(customer,
                            shopsDBReference.getRestaurantsDB(), customer.getSettings());
                    restaurantAddOrderMenu.show();
                    break;
                case SUPERMARKET:
                    SupermarketAddOrderMenu supermarketAddOrderMenu = new SupermarketAddOrderMenu(customer,
                            shopsDBReference.getSupermarketsDB(), customer.getSettings());
                    supermarketAddOrderMenu.show();
                    break;
                case FRUIT_SHOP:
                    FruitShopAddOrderMenu fruitShopAddOrderMenu = new FruitShopAddOrderMenu(customer,
                            shopsDBReference.getFruitShopsDB(), customer.getSettings());
                    fruitShopAddOrderMenu.show();
                default:
                    break;
            }
        }
    }

    private void updateOrderFeedback() {
        System.out.println("Order id :");
        int id = getId();
        Order order = new OrdersService<>(shopsDBReference.getAllOrders()).getOrderById(id);
        if (order == null) {
            System.out.println("Order not found");
            return;
        }
        if (order.getOrderState() != OrderState.DELIVERED) {
            System.out.println("Order isn't delivered yet");
            return;
        }
        Feedback feedback = getFeedback();
        order.setFeedback(feedback);
        shopsDBReference.getShopByOrderId(order.getId()).updateRating();
    }

    private void showFeedbacksHistory() {
        List<Feedback> feedbacks = new ArrayList<>();
        Set<Order> orders = new OrdersService<>(shopsDBReference.getAllOrders()).getOrdersByCustomer(customer);
        for (Order order : orders) {
            Feedback feedback = order.getFeedback();
            if (feedback != null) {
                feedbacks.add(feedback);
            }
        }
        printList(feedbacks, "feedbacks");
    }

    private void showOrdersHistory() {
        List<Order> orders = new ArrayList<>(new OrdersService<>(
                shopsDBReference.getAllOrders()).getOrdersByCustomer(customer));
        printList(orders, "orders");
    }
}
