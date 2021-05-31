package ir.ac.kntu.menu.order;

import ir.ac.kntu.db.CustomersDB;
import ir.ac.kntu.db.RestaurantsDB;
import ir.ac.kntu.menu.Menu;
import ir.ac.kntu.models.*;

import java.util.List;

public class OrdersMenu extends Menu {

    private final RestaurantsDB restaurantsDB;

    private final CustomersDB customersDB;

    private final Settings settings;

    public OrdersMenu(RestaurantsDB restaurantsDB, CustomersDB customersDB, Settings settings) {
        this.restaurantsDB = restaurantsDB;
        this.customersDB = customersDB;
        this.settings = settings;
    }

    @Override
    public void show() {
        OrdersOption ordersOption = printMenuOptions();
        while (ordersOption != OrdersOption.BACK) {
            if (ordersOption != null) {
                switch (ordersOption) {
                    case ADD:
                        addOrder();
                        break;
                    case UPDATE:
                        updateOrder();
                        break;
                    case CANCEL:
                        cancelOrder();
                        break;
                    case SHOW_ORDERS:
                        showOrders();
                        break;
                    case SHOW_FEEDBACKS_OF_FOOD:
                        showFeedbacksOfFood();
                        break;
                    default:
                        break;
                }
            }
            ordersOption = printMenuOptions();
        }
    }

    private OrdersOption printMenuOptions() {
        System.out.println("----------Orders Menu----------");
        OrdersOption.printOptions();
        System.out.print("Enter your choice : ");
        return getOption(OrdersOption.class);
    }

    private void addOrder() {
        AddOrderMenu addOrderMenu = new AddOrderMenu(customersDB, restaurantsDB, settings);
        addOrderMenu.show();
    }

    private void updateOrder() {
        UpdateOrderMenu updateOrderMenu = new UpdateOrderMenu(restaurantsDB);
        updateOrderMenu.show();
    }

    private void cancelOrder() {
        int id = getId();
        Restaurant restaurant = restaurantsDB.getShopByOrderId(id);
        if (restaurant == null) {
            System.out.println("Order not found");
            return;
        }
        Order order = restaurant.getOrdersService().getOrderById(id);
        OrderState orderState = order.getOrderState();
        if (orderState == OrderState.PROCESSING || orderState == OrderState.SENDING) {
            boolean isRemoved = restaurant.getOrdersService().removeOrder(order);
            if (isRemoved) {
                System.out.println("Order is canceled");
            } else {
                System.out.println("Order not found");
            }
        } else {
            System.out.println("Can't cancel order , it is already delivered");
        }
    }

    private void showOrders() {
        ShowOrderMenu showOrderMenu = new ShowOrderMenu(restaurantsDB);
        showOrderMenu.show();
    }

    private void showFeedbacksOfFood() {
        System.out.println("Food name : ");
        String foodName = getName();
        List<Feedback> feedbacks = restaurantsDB.getAllFeedbacksOfFood(new Food(foodName));
        for (int i = 0; i < feedbacks.size(); i++) {
            Feedback feedback = feedbacks.get(i);
            System.out.println("No." + (i + 1) + " " + feedback);
        }
        System.out.println(feedbacks.size() + " feedbacks found");
    }

}