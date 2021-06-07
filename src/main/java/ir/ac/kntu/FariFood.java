package ir.ac.kntu;

import ir.ac.kntu.db.*;
import ir.ac.kntu.menu.auth.AuthenticationMenu;
import ir.ac.kntu.models.ShopsDBReference;

import java.util.HashSet;

public class FariFood {

    private AuthenticationMenu authenticationMenu;

    public void start() {
        initialize();
        authenticationMenu.show();
    }

    private void initialize() {
        AdminsDB adminsDB = new AdminsDB(new HashSet<>());
        OwnersDB ownersDB = new OwnersDB(new HashSet<>());
        CouriersDB couriersDB = new CouriersDB(new HashSet<>());
        RestaurantsDB restaurantsDB = new RestaurantsDB(new HashSet<>());
        SupermarketsDB supermarketsDB = new SupermarketsDB(new HashSet<>());
        FruitShopsDB fruitShopsDB = new FruitShopsDB(new HashSet<>());
        CustomersDB customersDB = new CustomersDB(new HashSet<>());
        ShopsDBReference shopsDBReference = new ShopsDBReference(restaurantsDB,supermarketsDB,fruitShopsDB);

//        MainMenu mainMenu = new MainMenu(ordersMenu, couriersMenu, restaurantsMenu, customersMenu, settingsMenu);
        authenticationMenu = new AuthenticationMenu(adminsDB,ownersDB,customersDB, couriersDB, shopsDBReference);
    }

}
