package ir.ac.kntu.menu.shop.fruit_shop;

import ir.ac.kntu.db.CouriersDB;
import ir.ac.kntu.db.FruitShopsDB;
import ir.ac.kntu.db.OwnersDB;
import ir.ac.kntu.menu.shop.ShopsMenu;
import ir.ac.kntu.menu.shop.shop_info.FruitShopInfoMenu;
import ir.ac.kntu.models.CountableFoodMenu;
import ir.ac.kntu.models.FruitShop;
import ir.ac.kntu.models.Settings;
import ir.ac.kntu.service.PeriodicalOrdersService;

import java.util.HashMap;
import java.util.HashSet;

public class FruitShopsMenu extends ShopsMenu<FruitShop, FruitShopsDB> {

    public FruitShopsMenu(FruitShopsDB shopsDB, Settings settings, CouriersDB couriersDB, OwnersDB ownersDB) {
        super(shopsDB, settings, couriersDB, ownersDB);
    }

    @Override
    public void addShop() {
        FruitShop fruitShop = getFruitShopGeneralInfo(getOwnersDB());
        if (fruitShop == null) {
            return;
        }
        fruitShop.setHiredCouriers(new CouriersDB(new HashSet<>()));
        fruitShop.setOrdersService(new PeriodicalOrdersService(new HashSet<>()));
        fruitShop.setFoodMenu(new CountableFoodMenu(new HashMap<>()));
        boolean isAdded = getShopsDB().add(fruitShop);
        if (isAdded) {
            System.out.println("Fruit shop is added");
        } else {
            System.out.println("Fruit shop already exists");
        }
    }


    @Override
    public void findById() {
        int id = getId();
        FruitShop fruitShop = getShopsDB().getShopById(id);
        if (fruitShop == null) {
            System.out.println("Fruit shop not found");
            return;
        }
        FruitShopInfoMenu fruitShopInfoMenu = new FruitShopInfoMenu(fruitShop, getCouriersDB(), getOwnersDB());
        fruitShopInfoMenu.show();
    }


}
