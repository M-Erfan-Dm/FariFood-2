package ir.ac.kntu.menu.order;

import ir.ac.kntu.db.RestaurantsDB;
import ir.ac.kntu.menu.Menu;
import ir.ac.kntu.models.Order;
import ir.ac.kntu.models.OrderState;
import ir.ac.kntu.models.OrdersService;
import ir.ac.kntu.models.Restaurant;

public class ShowOrderMenu extends Menu {

    private final RestaurantsDB restaurantsDB;

    public ShowOrderMenu(RestaurantsDB restaurantsDB) {
        this.restaurantsDB = restaurantsDB;
    }

    @Override
    public void show() {
        ShowOrdersOption showOrdersOption = printShowOrderOptions();
        while (showOrdersOption != ShowOrdersOption.BACK) {
            if (showOrdersOption != null) {
                switch (showOrdersOption) {
                    case SHOW_ALL:
                        showAllOrders();
                        break;
                    case SHOW_BY_ID:
                        showOrderById();
                        break;
                    case SHOW_BY_STATE:
                        showOrderByState();
                        break;
                    default:
                        break;
                }
            }
            showOrdersOption = printShowOrderOptions();
        }
    }

    private ShowOrdersOption printShowOrderOptions() {
        ShowOrdersOption.printOptions();
        System.out.println("Enter your choice");
        return getOption(ShowOrdersOption.class);
    }

    private void showAllOrders() {
        new OrdersService(restaurantsDB.getAllOrders()).printAllOrders();
    }

    private void showOrderById() {
        int id = getId();
        Restaurant restaurant = restaurantsDB.getShopByOrderId(id);
        if (restaurant == null) {
            System.out.println("Order not found");
            return;
        }
        Order order = restaurant.getOrdersService().getOrderById(id);
        System.out.println("Order : " + order);
    }

    private void showOrderByState() {
        OrderState orderState = getOrderState();
        if (orderState == null) {
            return;
        }
        OrdersService allOrdersService = new OrdersService(restaurantsDB.getAllOrders());
        OrdersService foundOrders = new OrdersService(allOrdersService.getOrdersByState(orderState));
        foundOrders.printAllOrders();
    }

    private OrderState getOrderState() {
        OrderState.printOptions();
        System.out.println("Enter your choice :");
        return getOption(OrderState.class);
    }
}
