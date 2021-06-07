package ir.ac.kntu.menu.shop.premium_customers;

import ir.ac.kntu.db.CustomersDB;
import ir.ac.kntu.menu.Menu;
import ir.ac.kntu.models.Customer;
import ir.ac.kntu.service.PremiumCustomersService;

import java.util.ArrayList;

public class PremiumCustomersMenu extends Menu {

    private final PremiumCustomersService premiumCustomersService;

    private final CustomersDB customersDB;

    public PremiumCustomersMenu(PremiumCustomersService premiumCustomersService, CustomersDB customersDB) {
        this.premiumCustomersService = premiumCustomersService;
        this.customersDB = customersDB;
    }

    @Override
    public void show() {
        PremiumCustomersOption option;
        while ((option = printMenuOptions("Premium Customers Menu", PremiumCustomersOption.class))
                != PremiumCustomersOption.BACK) {
            if (option != null) {
                switch (option) {
                    case ADD_CUSTOMER:
                        addCustomer();
                        break;
                    case REMOVE_CUSTOMER:
                        removeCustomer();
                        break;
                    case SHOW_ALL:
                        showAll();
                        break;
                    default:
                        break;
                }
            }
        }
    }

    private void addCustomer() {
        System.out.println("Customer phone number");
        String phoneNumber = getPhoneNumber();
        Customer customer = customersDB.getCustomerByPhoneNumber(phoneNumber);
        if (customer == null) {
            System.out.println("Customer not found");
            return;
        }
        if (premiumCustomersService.containsCustomer(customer)) {
            System.out.println("Customer is already added");
            return;
        }
        premiumCustomersService.addPremiumCustomer(customer);
        System.out.println("Customer is added");
    }

    private void removeCustomer() {
        System.out.println("Customer phone number");
        String phoneNumber = getPhoneNumber();
        Customer customer = customersDB.getCustomerByPhoneNumber(phoneNumber);
        if (customer == null) {
            System.out.println("Customer not found");
            return;
        }
        boolean isRemoved = premiumCustomersService.removePremiumCustomer(customer);
        if (isRemoved) {
            System.out.println("Customer is removed");
        } else {
            System.out.println("Customer not found");
        }
    }

    private void showAll() {
        printList(new ArrayList<>(premiumCustomersService.getPremiumCustomers()), "premium customers");
    }

}
