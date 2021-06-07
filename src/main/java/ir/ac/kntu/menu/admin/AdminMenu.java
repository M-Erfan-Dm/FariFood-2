package ir.ac.kntu.menu.admin;

import ir.ac.kntu.db.CouriersDB;
import ir.ac.kntu.menu.Menu;
import ir.ac.kntu.menu.courier.CouriersMenu;
import ir.ac.kntu.menu.settings.SettingsMenu;
import ir.ac.kntu.models.Admin;
import ir.ac.kntu.models.ShopsDBReference;

public class AdminMenu extends Menu {

    private final Admin admin;

    private final ShopsDBReference shopsDBReference;

    private final CouriersDB couriersDB;


    public AdminMenu(Admin admin, ShopsDBReference shopsDBReference, CouriersDB couriersDB) {
        this.admin = admin;
        this.shopsDBReference = shopsDBReference;
        this.couriersDB = couriersDB;
    }

    @Override
    public void show() {
        AdminMenuOption option ;

        while ((option = printMenuOptions("Admin Menu",AdminMenuOption.class))
                != AdminMenuOption.LOGOUT){
            if (option!=null){
                switch (option){
                    case ORDERS:
                        break;
                    case COURIERS:
                        showCouriersMenu();
                        break;
                    case SHOPS:
                        break;
                    case CUSTOMERS:
                        break;
                    case SETTINGS:
                        showSettingsMenu();
                        break;
                }
            }
        }

    }

    private void showCouriersMenu(){
        CouriersMenu couriersMenu = new CouriersMenu(couriersDB,shopsDBReference);
        couriersMenu.show();
    }

    private void showSettingsMenu(){
        SettingsMenu settingsMenu = new SettingsMenu(admin.getSettings());
        settingsMenu.show();
    }

}
