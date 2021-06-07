package ir.ac.kntu.menu.admin;

import ir.ac.kntu.db.CustomersDB;
import ir.ac.kntu.menu.Menu;
import ir.ac.kntu.models.Customer;
import ir.ac.kntu.models.Feedback;
import ir.ac.kntu.models.Order;
import ir.ac.kntu.models.ShopsDBReference;

import java.util.ArrayList;
import java.util.List;

public class AdminCustomersMenu extends Menu {

    private final CustomersDB customersDB;

    private final ShopsDBReference shopsDBReference;

    public AdminCustomersMenu(CustomersDB customersDB, ShopsDBReference shopsDBReference) {
        this.customersDB = customersDB;
        this.shopsDBReference = shopsDBReference;
    }

    @Override
    public void show() {
        AdminCustomerMenuOption option;
        while ((option = printMenuOptions("Customer Menu", AdminCustomerMenuOption.class))
                != AdminCustomerMenuOption.BACK) {
            if (option != null) {
                switch (option) {
                    case FIND_BY_PHONE_NUMBER:
                        findByPhoneNumber();
                        break;
                    case SHOW_ALL:
                        showAll();
                        break;
                    case REMOVE:
                        removeCustomer();
                        break;
                    default:
                        break;
                }
            }
        }
    }

    public void findByPhoneNumber() {
        String phoneNumber = getPhoneNumber();
        Customer customer = customersDB.getCustomerByPhoneNumber(phoneNumber);
        if (customer == null) {
            System.out.println("Customer not found");
            return;
        }
        printEnumOptions(AdminCustomerInfoOption.class);
        System.out.println("Enter your choice :");
        AdminCustomerInfoOption customerInfoOption = getOption(AdminCustomerInfoOption.class);
        if (customerInfoOption == null) {
            return;
        }
        switch (customerInfoOption) {
            case GENERAL_INFO:
                showGeneralInfoOfCustomer(customer);
                break;
            case FEEDBACKS:
                showFeedbacksOfCustomer(customer);
                break;
            case ORDERS_HISTORY:
                showOrdersHistoryOfCustomer(customer);
                break;
            default:
                break;
        }
    }

    private void showAll() {
        printList(new ArrayList<>(customersDB.getCustomers()), "customers");
    }

    private void removeCustomer() {
        String phoneNumber = getPhoneNumber();
        Customer customer = customersDB.getCustomerByPhoneNumber(phoneNumber);
        if (customer == null) {
            System.out.println("Customer not found");
            return;
        }
        customersDB.removeCustomer(customer);
        System.out.println("Customer is removed");
    }

    private void showGeneralInfoOfCustomer(Customer customer) {
        System.out.println("General info of customer : " + customer);
    }

    private void showFeedbacksOfCustomer(Customer customer) {
        List<Feedback> feedbacks = customersDB.getFeedbacksOfCustomer(customer.getPhoneNumber(), shopsDBReference.getAllOrders());
        printList(feedbacks, "feedbacks");
    }

    private void showOrdersHistoryOfCustomer(Customer customer) {
        List<Order> orders = new ArrayList<>(customersDB.getOrdersOfCustomer(customer.getPhoneNumber(), shopsDBReference.getAllOrders()));
        printList(orders, "orders");
    }

}
