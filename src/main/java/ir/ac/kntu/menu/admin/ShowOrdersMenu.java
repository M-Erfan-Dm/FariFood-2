package ir.ac.kntu.menu.admin;

import ir.ac.kntu.db.ShopsDB;
import ir.ac.kntu.menu.Menu;
import ir.ac.kntu.models.Order;
import ir.ac.kntu.models.OrderState;
import ir.ac.kntu.service.OrdersService;
import ir.ac.kntu.models.Shop;

import java.util.ArrayList;

public class ShowOrdersMenu extends Menu {

    private final ShopsDB<? extends Shop<? extends OrdersService<? extends Order>>> shopsDB;

    public ShowOrdersMenu(ShopsDB<? extends Shop<? extends OrdersService<? extends Order>>> shopsDB) {
        this.shopsDB = shopsDB;
    }

    @Override
    public void show() {
        ShowOrdersOption option;
        while ((option = printMenuOptions("Show Orders Menu",ShowOrdersOption.class))
                != ShowOrdersOption.BACK) {
            if (option != null) {
                switch (option) {
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
        }
    }

    private void showAllOrders() {
        printList(new ArrayList<>(shopsDB.getAllOrders()), "orders");
    }

    private void showOrderById() {
        int id = getId();
        Shop<?> shop = shopsDB.getShopByOrderId(id);
        if (shop == null) {
            System.out.println("Order not found");
            return;
        }
        Order order = shop.getOrdersService().getOrderById(id);
        System.out.println("Order : " + order);
    }

    private void showOrderByState() {
        OrderState orderState = getOrderState();
        if (orderState == null) {
            return;
        }
        printList(new ArrayList<>(new OrdersService<>(
                shopsDB.getAllOrders()).getOrdersByState(orderState)), "orders");
    }

    private OrderState getOrderState() {
        printEnumOptions(OrderState.class);
        System.out.println("Enter your choice :");
        return getOption(OrderState.class);
    }
}

