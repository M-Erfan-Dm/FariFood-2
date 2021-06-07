package ir.ac.kntu.menu.shop;

import ir.ac.kntu.db.CouriersDB;
import ir.ac.kntu.db.OwnersDB;
import ir.ac.kntu.db.ShopsDB;
import ir.ac.kntu.menu.Menu;
import ir.ac.kntu.models.*;

import java.util.ArrayList;
import java.util.List;

public abstract class ShopsMenu  <T extends Shop<? extends OrdersService<? extends Order>>,
        D extends ShopsDB<T>> extends Menu {

    private final D shopsDB;

    private final Settings settings;

    private final CouriersDB couriersDB;

    private final OwnersDB ownersDB;

    public ShopsMenu(D shopsDB, Settings settings, CouriersDB couriersDB, OwnersDB ownersDB) {
        this.shopsDB = shopsDB;
        this.settings = settings;
        this.couriersDB = couriersDB;
        this.ownersDB = ownersDB;
    }

    public D getShopsDB() {
        return shopsDB;
    }

    public Settings getSettings() {
        return settings;
    }

    public CouriersDB getCouriersDB() {
        return couriersDB;
    }

    public OwnersDB getOwnersDB() {
        return ownersDB;
    }

    @Override
    public void show() {
        ShopsOption option;
        while ((option = printMenuOptions("Shops Menu", ShopsOption.class))
                != ShopsOption.BACK) {
            if (option != null) {
                switch (option) {
                    case ADD:
                        addShop();
                        break;
                    case FIND_BY_ID:
                        findById();
                        break;
                    case FIND_BY_NAME:
                        findByName();
                        break;
                    case FIND_BY_PRICE_TYPE:
                        findByPriceType();
                        break;
                    case SHOW_ALL:
                        showAll();
                        break;
                    case REMOVE:
                        removeShop();
                        break;
                    default:
                        break;
                }
            }
        }
    }

    abstract public void addShop();

    abstract public void findById();

    public void findByName() {
        String name = getName();
        List<T> shops = new ArrayList<>(shopsDB.getShopsByName(name));
        printList(shops,"shops");
    }

    public void findByPriceType() {
        ShopPriceType shopPriceType = getShopPriceType();
        if (shopPriceType == null) {
            return;
        }
        List<T> shops = new ArrayList<>(shopsDB.getShopsByPriceType(shopPriceType));
        printList(shops,"shops");
    }

    public void showAll() {
        List<T> shops = shopsDB.getOrderedListOfShops(settings);
        printList(shops,"shops");
    }

    public void removeShop() {
        int id = getId();
        T shops = shopsDB.getShopById(id);
        boolean isRemoved = shopsDB.remove(shops);
        if (isRemoved) {
            System.out.println("Shop is removed");
        } else {
            System.out.println("Shop not found");
        }
    }
}
