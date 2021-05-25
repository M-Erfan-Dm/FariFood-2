package ir.ac.kntu.menu.admin;

import ir.ac.kntu.db.AdminsDB;
import ir.ac.kntu.menu.Menu;
import ir.ac.kntu.menu.main.MainMenu;
import ir.ac.kntu.models.Admin;

public class AdminMenu extends Menu {

    private final AdminsDB adminsDB;
    private final MainMenu mainMenu;

    public AdminMenu(AdminsDB adminsDB, MainMenu mainMenu) {
        this.adminsDB = adminsDB;
        this.mainMenu = mainMenu;
    }

    @Override
    public void show() {
        System.out.println("Welcome to Fari Food");
        AdminOption adminOption = printMenuOptions();
        while (adminOption != AdminOption.EXIT) {
            if (adminOption != null) {

                switch (adminOption) {
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
            adminOption = printMenuOptions();
        }
        System.exit(0);
    }


    private AdminOption printMenuOptions() {
        System.out.println("----------Admin Menu----------");
        AdminOption.printOptions();
        System.out.print("Enter your choice : ");
        return getOption(AdminOption.class);
    }

    private void login() {
        System.out.println("---Login---");
        String username = getUsername();
        String password = getPassword();
        Admin admin = new Admin(username, password);
        if (adminsDB.isAdminValid(admin)) {
            mainMenu.show();
        } else {
            System.out.println("Username or password is invalid");
        }
    }

    private void register() {
        System.out.println("---Register---");
        String username = getUsername();
        String password = getPassword();
        Admin admin = new Admin(username, password);
        boolean isAdded = adminsDB.addAdmin(admin);
        if (isAdded) {
            System.out.println("Admin is registered");
        } else {
            System.out.println("Username already exists");
        }
    }

}
