package ir.ac.kntu.menu.admin;

import ir.ac.kntu.db.CustomersDB;
import ir.ac.kntu.db.ShopsDB;
import ir.ac.kntu.menu.Menu;
import ir.ac.kntu.menu.order.AddOrderMenu;
import ir.ac.kntu.models.*;
import ir.ac.kntu.service.OrdersService;

import java.util.List;

public class OrdersMenu<T extends Shop<? extends OrdersService<? extends Order>>, D extends ShopsDB<T>> extends Menu {

    private final D shopsDB;

    private final AddOrderMenu<T, D> addOrderMenu;

    private final CustomersDB customersDB;

    public OrdersMenu(D shopsDB, AddOrderMenu<T, D> addOrderMenu, CustomersDB customersDB) {
        this.shopsDB = shopsDB;
        this.addOrderMenu = addOrderMenu;
        this.customersDB = customersDB;
    }

    @Override
    public void show() {
        OrdersOption ordersOption;
        while ((ordersOption = printMenuOptions("Orders Menu", OrdersOption.class))
                != OrdersOption.BACK) {
            if (ordersOption != null) {
                switch (ordersOption) {
                    case ADD:
                        addOrder();
                        break;
                    case UPDATE:
                        updateOrder();
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
        }
    }


    private void addOrder() {
        System.out.println("Customer :");
        String phoneNumber = getPhoneNumber();
        Customer customer = customersDB.getCustomerByPhoneNumber(phoneNumber);
        if (customer == null) {
            System.out.println("Customer not found");
            return;
        }
        addOrderMenu.setCustomer(customer);
        addOrderMenu.show();
    }

    private void updateOrder() {
        UpdateOrderMenu updateOrderMenu = new UpdateOrderMenu(shopsDB);
        updateOrderMenu.show();
    }


    private void showOrders() {
        ShowOrdersMenu showOrdersMenu = new ShowOrdersMenu(shopsDB);
        showOrdersMenu.show();
    }

    private void showFeedbacksOfFood() {
        System.out.println("Food name : ");
        String foodName = getName();
        List<Feedback> feedbacks = shopsDB.getAllFeedbacksOfFood(new Food(foodName));
        printList(feedbacks, "feedbacks");
    }

}