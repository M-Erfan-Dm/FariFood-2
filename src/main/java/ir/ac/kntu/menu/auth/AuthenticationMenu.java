package ir.ac.kntu.menu.auth;

import ir.ac.kntu.db.AdminsDB;
import ir.ac.kntu.db.CustomersDB;
import ir.ac.kntu.db.OwnersDB;
import ir.ac.kntu.menu.Menu;
import ir.ac.kntu.menu.admin.AdminMenu;
import ir.ac.kntu.menu.customer.CustomersMenu;
import ir.ac.kntu.menu.owner.OwnersMenu;
import ir.ac.kntu.models.*;

public class AuthenticationMenu extends Menu {

    private final AdminsDB adminsDB;

    private final OwnersDB ownersDB;

    private final CustomersDB customersDB;

    private final ShopsDBReference shopsDBReference;

    public AuthenticationMenu(AdminsDB adminsDB, OwnersDB ownersDB, CustomersDB customersDB, ShopsDBReference shopsDBReference) {
        this.adminsDB = adminsDB;
        this.ownersDB = ownersDB;
        this.customersDB = customersDB;
        this.shopsDBReference = shopsDBReference;
    }

    @Override
    public void show() {
        AuthenticationOption option;
        while ((option = printMenuOptions("Authentication Menu", AuthenticationOption.class))
                != AuthenticationOption.EXIT) {
            if (option != null) {
                switch (option) {
                    case LOGIN:
                        login();
                        break;
                    case REGISTER:
                        register();
                        break;
                    default:
                        break;
                }
            }
        }
        System.exit(0);
    }

    private void login() {
        String phoneNumber = getPhoneNumber();
        String password = getPassword();
        printEnumOptions(Roll.class);
        System.out.println("Select your roll : ");
        Roll roll = getOption(Roll.class);
        if (roll == null) {
            return;
        }
        switch (roll){
            case ADMIN:
                loginAdmin(phoneNumber,password);
                break;
            case OWNER:
                loginOwner(phoneNumber,password);
                break;
            case CUSTOMER:
                loginCustomer(phoneNumber,password);
                break;
            default:
                break;
        }
    }


    private void register() {
        String phoneNumber = getPhoneNumber();
        String password = getPassword();
        printEnumOptions(Roll.class);
        System.out.println("Select your roll : ");
        Roll roll = getOption(Roll.class);
        if (roll == null) {
            return;
        }
        switch (roll) {
            case ADMIN:
                registerAdmin(phoneNumber, password);
                break;
            case OWNER:
                registerOwner(phoneNumber, password);
                break;
            case CUSTOMER:
                registerCustomer(phoneNumber, password);
                break;
            default:
                break;
        }
    }

    private void loginAdmin(String phoneNumber, String password){
        boolean isAdminValid = adminsDB.isAdminValid(phoneNumber, password);
        if (isAdminValid) {
            Admin admin = adminsDB.getAdminByPhoneNumber(phoneNumber);
            AdminMenu adminMenu = new AdminMenu(admin,shopsDBReference);
            adminMenu.show();
        }else {
            System.out.println("Admin not found");
        }
    }

    private void loginOwner(String phoneNumber, String password){
        boolean isOwnerValid = ownersDB.isOwnerValid(phoneNumber, password);
        if (isOwnerValid) {
            Owner owner = ownersDB.getOwnerByPhoneNumber(phoneNumber);
            OwnersMenu ownersMenu = new OwnersMenu(owner, shopsDBReference);
            ownersMenu.show();
        }else {
            System.out.println("Owner not found");
        }
    }

    private void loginCustomer(String phoneNumber, String password){
        boolean isCustomerValid = customersDB.isCustomerValid(phoneNumber, password);
        if (isCustomerValid) {
            Customer customer = customersDB.getCustomerByPhoneNumber(phoneNumber);
            CustomersMenu customersMenu = new CustomersMenu(customer,shopsDBReference);
            customersMenu.show();
        }else {
            System.out.println("Customer not found");
        }
    }

    private void registerAdmin(String phoneNumber, String password) {
        Admin admin = new Admin(phoneNumber, password);
        if (adminsDB.containsAdmin(admin)) {
            System.out.println("Admin already exists");
            return;
        }
        adminsDB.addAdmin(admin);
        System.out.println("Admin is registered");
    }

    private void registerOwner(String phoneNumber, String password) {
        String name = getName();
        Owner owner = new Owner(phoneNumber, name, password);
        if (ownersDB.containsOwner(owner)) {
            System.out.println("Owner already exists");
            return;
        }
        ownersDB.addOwner(owner);
        System.out.println("Owner is registered");
    }

    private void registerCustomer(String phoneNumber, String password) {
        String address = getAddress();
        Customer customer = new Customer(phoneNumber, password, address);
        if (customersDB.containsCustomer(customer)) {
            System.out.println("Customer already exists");
            return;
        }
        customersDB.addCustomer(customer);
        System.out.println("Customer is registered");
    }
}
