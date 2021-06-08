package ir.ac.kntu.menu.admin;

import ir.ac.kntu.db.ShopsDB;
import ir.ac.kntu.menu.Menu;
import ir.ac.kntu.models.*;
import ir.ac.kntu.service.OrdersService;
import ir.ac.kntu.utils.ScannerWrapper;

import java.util.List;
import java.util.Random;

public class UpdateOrderMenu extends Menu {

    private final ShopsDB<? extends Shop<? extends OrdersService<? extends Order>>> shopsDB;

    public UpdateOrderMenu(ShopsDB<? extends Shop<? extends OrdersService<? extends Order>>> shopsDB) {
        this.shopsDB = shopsDB;
    }

    @Override
    public void show() {
        int id = getId();
        Shop<?> shop = shopsDB.getShopByOrderId(id);
        if (shop == null) {
            System.out.println("Order not found");
            return;
        }
        Order order = shop.getOrdersService().getOrderById(id);
        UpdateOrderOption option;
        while ((option = printMenuOptions("Update Order Menu", UpdateOrderOption.class))
                != UpdateOrderOption.BACK) {
            if (option != null) {
                switch (option) {
                    case ORDER_STATE:
                        updateOrderState(order, shop);
                        break;
                    case FEEDBACK:
                        updateFeedback(order, shop);
                        break;
                    default:
                        break;
                }
            }
        }
    }

    private void updateOrderState(Order order, Shop<?> shop) {
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
                    handleSendingOrderState(shop, order, nextOrderState);
                    break;
                case DELIVERED:
                    handleDeliveredOrderState(shop, order, nextOrderState);
                    break;
                default:
                    break;
            }
        }
    }

    private void updateFeedback(Order order, Shop<?> shop) {
        if (order.getOrderState() != OrderState.DELIVERED) {
            System.out.println("Order isn't delivered yet");
            return;
        }
        Feedback feedback = getFeedback();
        order.setFeedback(feedback);
        shop.updateRating();
    }

    private Courier getRandomCourier(Shop<?> shop) {
        List<Courier> couriers = shop.getHiredCouriers().getAvailableCouriers(shop.getId());
        if (couriers.size() == 0) {
            System.out.println("Couriers are busy!");
            return null;
        }
        int randomIndex = new Random().nextInt(couriers.size());
        return couriers.get(randomIndex);
    }

    private void handleSendingOrderState(Shop<?> shop, Order order, OrderState orderState) {
        Courier courier = getRandomCourier(shop);
        if (courier != null) {
            System.out.println("Courier of order : phone number : " +
                    courier.getPhoneNumber() + " , name : " + courier.getName());
            order.setCourier(courier);
            order.setOrderState(orderState);
        }
    }

    private void handleDeliveredOrderState(Shop<?> shop, Order order, OrderState orderState) {
        Feedback feedback = getFeedback();
        order.setFeedback(feedback);
        order.setOrderState(orderState);
        shop.updateRating();
    }
}
