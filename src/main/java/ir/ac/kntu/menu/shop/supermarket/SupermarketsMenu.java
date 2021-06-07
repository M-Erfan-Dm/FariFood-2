package ir.ac.kntu.menu.shop.supermarket;

import ir.ac.kntu.db.CouriersDB;
import ir.ac.kntu.db.CustomersDB;
import ir.ac.kntu.db.OwnersDB;
import ir.ac.kntu.db.SupermarketsDB;
import ir.ac.kntu.menu.shop.ShopsMenu;
import ir.ac.kntu.menu.shop.shop_info.SupermarketInfoMenu;
import ir.ac.kntu.models.*;
import ir.ac.kntu.service.PremiumCustomersService;

import java.util.HashMap;
import java.util.HashSet;

public class SupermarketsMenu extends ShopsMenu<Supermarket,SupermarketsDB> {

    private final CustomersDB customersDB;

    public SupermarketsMenu(SupermarketsDB shopsDB, Settings settings, CouriersDB couriersDB, OwnersDB ownersDB, CustomersDB customersDB) {
        super(shopsDB, settings, couriersDB, ownersDB);
        this.customersDB = customersDB;
    }


    @Override
    public void addShop() {
        Supermarket supermarket = getSupermarketGeneralInfo(getOwnersDB());
        if (supermarket == null) {
            return;
        }
        supermarket.setFoodMenu(new CountableFoodMenu(new HashMap<>()));
        supermarket.setHiredCouriers(new CouriersDB(new HashSet<>()));
        supermarket.setOrdersService(new PeriodicalOrdersService(new HashSet<>()));
        supermarket.setPremiumCustomersService(new PremiumCustomersService(new HashSet<>()));
        boolean isAdded = getShopsDB().add(supermarket);
        if (isAdded) {
            System.out.println("Supermarket is added");
        } else {
            System.out.println("Supermarket already exists");
        }
    }

    @Override
    public void findById() {
        int id = getId();
        Supermarket supermarket = getShopsDB().getShopById(id);
        if (supermarket == null) {
            System.out.println("Supermarket not found");
            return;
        }
        SupermarketInfoMenu supermarketInfoMenu = new SupermarketInfoMenu(supermarket,
                getCouriersDB(),getOwnersDB(),customersDB);
        supermarketInfoMenu.show();
    }


}
