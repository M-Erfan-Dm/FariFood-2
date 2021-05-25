package ir.ac.kntu.menu.order;

import ir.ac.kntu.db.RestaurantsDB;
import ir.ac.kntu.menu.Menu;
import ir.ac.kntu.models.*;
import ir.ac.kntu.utils.ScannerWrapper;

import java.util.List;
import java.util.Random;

public class UpdateOrderMenu extends Menu {

    private final RestaurantsDB restaurantsDB;

    public UpdateOrderMenu(RestaurantsDB restaurantsDB) {
        this.restaurantsDB = restaurantsDB;
    }

    @Override
    public void show() {
        int id = getId();
        Restaurant restaurant = restaurantsDB.getRestaurantByOrderId(id);
        if (restaurant == null) {
            System.out.println("Order not found");
            return;
        }
        Order order = restaurant.getOrdersService().getOrderById(id);
        UpdateOrderOption updateOrderOption = printMenuOptions();
        while (updateOrderOption != UpdateOrderOption.BACK) {
            if (updateOrderOption != null) {
                switch (updateOrderOption) {
                    case ORDER_STATE:
                        updateOrderState(order, restaurant);
                        break;
                    case FEEDBACK:
                        updateFeedback(order, restaurant);
                        break;
                    default:
                        break;
                }
            }
            updateOrderOption = printMenuOptions();
        }
    }

    private UpdateOrderOption printMenuOptions() {
        UpdateOrderOption.printOptions();
        System.out.println("Enter your choice :");
        return getOption(UpdateOrderOption.class);
    }

    private void updateOrderState(Order order, Restaurant restaurant) {
        System.out.println("Current state of order : " + order.getOrderState());
        if (order.getOrderState() == OrderState.DELIVERED) {
            System.out.println("Can't change order state anymore");
            return;
        }
        System.out.println("Change to " + order.getNextOrderState() + " state ?\n1.Yes\n2.No\nEnter your choice : ");
        int choice = Integer.parseInt(ScannerWrapper.nextLine()) - 1;
        if (choice == 0) {
            OrderState nextOrderState = order.getNextOrderState();
            switch (nextOrderState) {
                case SENDING:
                    Courier courier = getRandomCourier(restaurant);
                    if (courier != null) {
                        System.out.println("Courier of order : phone number : " +
                                courier.getPhoneNumber() + " , name : " + courier.getName());
                        order.setCourier(courier);
                        order.setOrderState(nextOrderState);
                    }
                    break;
                case DELIVERED:
                    Feedback feedback = getFeedback();
                    order.setFeedback(feedback);
                    order.setOrderState(nextOrderState);
                    restaurant.updateRating();
                    break;
                default:
                    break;
            }
        }
    }

    private void updateFeedback(Order order, Restaurant restaurant) {
        if (order.getOrderState() != OrderState.DELIVERED) {
            System.out.println("Order isn't delivered yet");
            return;
        }
        Feedback feedback = getFeedback();
        order.setFeedback(feedback);
        restaurant.updateRating();
    }

    private Courier getRandomCourier(Restaurant restaurant) {
        List<Courier> couriers = restaurant.getHiredCouriers().getAvailableCouriers(restaurant.getId());
        if (couriers.size() == 0) {
            System.out.println("Couriers are busy!");
            return null;
        }
        int randomIndex = new Random().nextInt(couriers.size());
        return couriers.get(randomIndex);
    }
}
