package ir.ac.kntu;

import ir.ac.kntu.db.AdminsDB;
import ir.ac.kntu.db.CouriersDB;
import ir.ac.kntu.db.CustomersDB;
import ir.ac.kntu.db.RestaurantsDB;
import ir.ac.kntu.menu.admin.AdminMenu;
import ir.ac.kntu.menu.courier.CouriersMenu;
import ir.ac.kntu.menu.customer.CustomersMenu;
import ir.ac.kntu.menu.main.MainMenu;
import ir.ac.kntu.menu.order.OrdersMenu;
import ir.ac.kntu.menu.restaurant.RestaurantsMenu;
import ir.ac.kntu.menu.settings.SettingsMenu;
import ir.ac.kntu.models.RestaurantsFilteringStrategy;
import ir.ac.kntu.models.Settings;

import java.util.HashSet;

public class FariFood {

    private AdminMenu adminMenu;

    public void start() {
        initialize();
        adminMenu.show();
    }

    private void initialize() {
        AdminsDB adminsDB = new AdminsDB(new HashSet<>());
        CouriersDB couriersDB = new CouriersDB(new HashSet<>());
        RestaurantsDB restaurantsDB = new RestaurantsDB(new HashSet<>());
        CustomersDB customersDB = new CustomersDB(new HashSet<>());

        Settings settings = new Settings(RestaurantsFilteringStrategy.BY_RATING_DESCENDING);
        SettingsMenu settingsMenu = new SettingsMenu(settings);
        CustomersMenu customersMenu = new CustomersMenu(customersDB, restaurantsDB);
        CouriersMenu couriersMenu = new CouriersMenu(couriersDB, restaurantsDB);
        RestaurantsMenu restaurantsMenu = new RestaurantsMenu(restaurantsDB, settings, couriersDB);
        OrdersMenu ordersMenu = new OrdersMenu(restaurantsDB, customersDB, settings);
        MainMenu mainMenu = new MainMenu(ordersMenu, couriersMenu, restaurantsMenu, customersMenu, settingsMenu);
        adminMenu = new AdminMenu(adminsDB, mainMenu);
    }

}
