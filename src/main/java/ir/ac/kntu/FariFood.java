package ir.ac.kntu;

import ir.ac.kntu.db.*;
import ir.ac.kntu.menu.auth.AuthenticationMenu;
import ir.ac.kntu.models.*;
import ir.ac.kntu.service.PeriodicalOrdersService;
import ir.ac.kntu.service.PremiumCustomersService;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

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
        ShopsDBReference shopsDBReference = new ShopsDBReference(restaurantsDB, supermarketsDB, fruitShopsDB);


        adminsDB.addAdmin(new Admin("1","1",new Settings()));
        Owner owner = new Owner("2","erfan","2",new Settings());
        ownersDB.addOwner(owner);
        customersDB.addCustomer(new Customer("3","3","semnan",new Settings()));

        supermarketsDB.add(new Supermarket(owner,"restaurant","semnan",new Schedule(
                LocalTime.of(5, 30), LocalTime.of(23, 0), new HashSet<>(Arrays.asList(Day.values()))),
                new CouriersDB(new HashSet<>()),new PeriodicalOrdersService(new HashSet<>()),50,20,
                new PremiumCustomersService(new HashSet<>()),ShopPriceType.LUXURIOUS,new CountableFoodMenu(new HashMap<>())));

        authenticationMenu = new AuthenticationMenu(adminsDB, ownersDB, customersDB, couriersDB, shopsDBReference);
    }

}
