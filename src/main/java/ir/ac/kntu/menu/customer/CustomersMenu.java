package ir.ac.kntu.menu.customer;

import ir.ac.kntu.db.CustomersDB;
import ir.ac.kntu.db.RestaurantsDB;
import ir.ac.kntu.menu.Menu;
import ir.ac.kntu.models.Customer;
import ir.ac.kntu.models.Feedback;
import ir.ac.kntu.models.OrdersService;

import java.util.List;

public class CustomersMenu extends Menu {

    private final CustomersDB customersDB;

    private final RestaurantsDB restaurantsDB;

    public CustomersMenu(CustomersDB customersDB, RestaurantsDB restaurantsDB) {
        this.customersDB = customersDB;
        this.restaurantsDB = restaurantsDB;
    }

    @Override
    public void show() {
        CustomersOption customersOption = printMenuOptions();
        while (customersOption != CustomersOption.BACK) {
            if (customersOption != null) {
                switch (customersOption) {
                    case REGISTER:
                        registerCustomer();
                        break;
                    case UPDATE:
                        updateCustomer();
                        break;
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
            customersOption = printMenuOptions();
        }

    }


    private CustomersOption printMenuOptions() {
        System.out.println("----------Customers Menu----------");
        CustomersOption.printOptions();
        System.out.print("Enter your choice : ");
        return getOption(CustomersOption.class);
    }

    private void registerCustomer() {
        Customer customer = getCustomerInfo();
        if (customersDB.containsCustomer(customer)) {
            System.out.println("Customer with this phone number already exists!");
            return;
        }
        customersDB.addCustomer(customer);
        System.out.println("Customer is registered");
    }

    private void updateCustomer() {
        Customer customer = getCustomerInfo();
        if (!customersDB.containsCustomer(customer)) {
            System.out.println("Customer not found");
            return;
        }
        customersDB.addCustomer(customer);
        System.out.println("Customer is updated");
    }

    public void findByPhoneNumber() {
        String phoneNumber = getPhoneNumber();
        Customer customer = customersDB.getCustomerByPhoneNumber(phoneNumber);
        if (customer == null) {
            System.out.println("Customer not found");
            return;
        }
        CustomerInfoOption.printOptions();
        System.out.println("Enter your choice :");
        CustomerInfoOption customerInfoOption = getOption(CustomerInfoOption.class);
        if (customerInfoOption == null) {
            return;
        }
        switch (customerInfoOption) {
            case GENERAL:
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
        customersDB.printAllCustomers();
    }

    private void removeCustomer() {
        String phoneNumber = getPhoneNumber();
        Customer customer = new Customer(phoneNumber, "","");
        boolean isRemoved = customersDB.removeCustomer(customer);
        if (isRemoved) {
            System.out.println("Customer is removed");
        } else {
            System.out.println("Customer not found");
        }
    }

    private void showGeneralInfoOfCustomer(Customer customer) {
        System.out.println("General info of customer : " + customer);
    }

    private void showFeedbacksOfCustomer(Customer customer) {
        List<Feedback> feedbacks = customersDB.getFeedbacksOfCustomer(customer.getPhoneNumber(), restaurantsDB);
        for (int i = 0; i < feedbacks.size(); i++) {
            Feedback feedback = feedbacks.get(i);
            System.out.println("No." + (i + 1) + " " + feedback);
        }
        System.out.println(feedbacks.size() + " feedbacks found");
    }

    private void showOrdersHistoryOfCustomer(Customer customer) {
        OrdersService ordersService = new OrdersService(customersDB.getOrdersOfCustomer(
                customer.getPhoneNumber(), restaurantsDB));
        ordersService.printAllOrders();
    }

    private Customer getCustomerInfo() {
        String phoneNumber = getPhoneNumber();
        String password = getPassword();
        String address = getAddress();
        return new Customer(phoneNumber,password,address);
    }
}
