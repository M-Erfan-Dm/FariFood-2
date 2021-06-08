package ir.ac.kntu.menu.owner;

import ir.ac.kntu.db.CouriersDB;
import ir.ac.kntu.db.CustomersDB;
import ir.ac.kntu.db.OwnersDB;
import ir.ac.kntu.menu.Menu;
import ir.ac.kntu.menu.settings.SettingsMenu;
import ir.ac.kntu.menu.shop.shop_info.FruitShopInfoMenu;
import ir.ac.kntu.menu.shop.shop_info.RestaurantInfoMenu;
import ir.ac.kntu.menu.shop.shop_info.SupermarketInfoMenu;
import ir.ac.kntu.models.*;

import java.util.List;
import java.util.stream.Collectors;

public class OwnersMenu extends Menu {

    private final Owner owner;

    private final ShopsDBReference shopsDBReference;

    private final CouriersDB couriersDB;

    private final OwnersDB ownersDB;

    private final CustomersDB customersDB;

    public OwnersMenu(Owner owner, ShopsDBReference shopsDBReference,
                      CouriersDB couriersDB, OwnersDB ownersDB, CustomersDB customersDB) {
        this.owner = owner;
        this.shopsDBReference = shopsDBReference;
        this.couriersDB = couriersDB;
        this.ownersDB = ownersDB;
        this.customersDB = customersDB;
    }

    @Override
    public void show() {
        OwnersMenuOption option;
        while ((option = printMenuOptions("Owner Menu", OwnersMenuOption.class))
                != OwnersMenuOption.LOGOUT) {
            if (option != null) {
                switch (option) {
                    case SHOP_INFO:
                        showShopInfo();
                        break;
                    case SHOW_ALL_SHOPS:
                        showAll();
                        break;
                    case SETTINGS:
                        SettingsMenu settingsMenu = new SettingsMenu(owner.getSettings());
                        settingsMenu.show();
                        break;
                    default:
                        break;
                }
            }
        }
    }


    private void showShopInfo() {
        printEnumOptions(ShopType.class);
        ShopType shopType = getOption(ShopType.class);
        if (shopType != null) {
            switch (shopType) {
                case RESTAURANT:
                    Restaurant restaurant = getRestaurant();
                    if (restaurant != null) {
                        new RestaurantInfoMenu(restaurant, couriersDB, ownersDB).show();
                    }
                    break;
                case SUPERMARKET:
                    Supermarket supermarket = getSupermarket();
                    if (supermarket != null) {
                        new SupermarketInfoMenu(supermarket, couriersDB, ownersDB, customersDB).show();
                    }
                    break;
                case FRUIT_SHOP:
                    FruitShop fruitShop = getFruitShop();
                    if (fruitShop != null) {
                        new FruitShopInfoMenu(fruitShop, couriersDB, ownersDB).show();
                    }
                    break;
                default:
                    break;
            }
        }
    }


    private Restaurant getRestaurant() {
        int id = getId();
        Restaurant restaurant = shopsDBReference.getRestaurantsDB().getShopById(id);
        if (restaurant == null) {
            return null;
        }
        if (restaurant.getOwner().equals(owner)) {
            return restaurant;
        }
        System.out.println("Restaurant not found");
        return null;
    }

    private Supermarket getSupermarket() {
        int id = getId();
        Supermarket supermarket = shopsDBReference.getSupermarketsDB().getShopById(id);
        if (supermarket == null) {
            return null;
        }
        if (supermarket.getOwner().equals(owner)) {
            return supermarket;
        }
        System.out.println("Supermarket not found");
        return null;
    }

    private FruitShop getFruitShop() {
        int id = getId();
        FruitShop fruitShop = shopsDBReference.getFruitShopsDB().getShopById(id);
        if (fruitShop == null) {
            return null;
        }
        if (fruitShop.getOwner().equals(owner)) {
            return fruitShop;
        }
        System.out.println("Fruit shop not found");
        return null;
    }

    private void showAll() {
        printEnumOptions(ShopType.class);
        ShopType shopType = getOption(ShopType.class);
        if (shopType != null) {
            switch (shopType) {
                case RESTAURANT:
                    showAllRestaurants();
                    break;
                case SUPERMARKET:
                    showAllSupermarkets();
                    break;
                case FRUIT_SHOP:
                    showAllFruitShops();
                    break;
                default:
                    break;
            }
        }
    }

    private void showAllRestaurants() {
        List<Restaurant> restaurants = shopsDBReference.getRestaurantsDB().getShops().stream().
                filter(restaurant -> restaurant.getOwner().equals(owner)).collect(Collectors.toList());
        printList(restaurants, "restaurants");
    }

    private void showAllSupermarkets() {
        List<Supermarket> supermarkets = shopsDBReference.getSupermarketsDB().getShops().stream().
                filter(supermarket -> supermarket.getOwner().equals(owner)).collect(Collectors.toList());
        printList(supermarkets, "supermarkets");
    }

    private void showAllFruitShops() {
        List<FruitShop> fruitShops = shopsDBReference.getFruitShopsDB().getShops().stream().
                filter(fruitShop -> fruitShop.getOwner().equals(owner)).collect(Collectors.toList());
        printList(fruitShops, "fruit shops");
    }
}
