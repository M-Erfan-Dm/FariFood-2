package ir.ac.kntu.menu.admin;

import ir.ac.kntu.db.CouriersDB;
import ir.ac.kntu.db.CustomersDB;
import ir.ac.kntu.db.OwnersDB;
import ir.ac.kntu.menu.Menu;
import ir.ac.kntu.menu.courier.CouriersMenu;
import ir.ac.kntu.menu.order.FruitShopAddOrderMenu;
import ir.ac.kntu.menu.order.RestaurantAddOrderMenu;
import ir.ac.kntu.menu.order.SupermarketAddOrderMenu;
import ir.ac.kntu.menu.settings.SettingsMenu;
import ir.ac.kntu.menu.shop.fruit_shop.FruitShopsMenu;
import ir.ac.kntu.menu.shop.restaurant.RestaurantsMenu;
import ir.ac.kntu.menu.shop.supermarket.SupermarketsMenu;
import ir.ac.kntu.models.Admin;
import ir.ac.kntu.models.ShopType;
import ir.ac.kntu.models.ShopsDBReference;

public class AdminMenu extends Menu {

    private final Admin admin;

    private final ShopsDBReference shopsDBReference;

    private final CouriersDB couriersDB;

    private final OwnersDB ownersDB;

    private final CustomersDB customersDB;


    public AdminMenu(Admin admin, ShopsDBReference shopsDBReference,
                     CouriersDB couriersDB, OwnersDB ownersDB, CustomersDB customersDB) {
        this.admin = admin;
        this.shopsDBReference = shopsDBReference;
        this.couriersDB = couriersDB;
        this.ownersDB = ownersDB;
        this.customersDB = customersDB;
    }

    @Override
    public void show() {
        AdminMenuOption option;

        while ((option = printMenuOptions("Admin Menu", AdminMenuOption.class))
                != AdminMenuOption.LOGOUT) {
            if (option != null) {
                switch (option) {
                    case ORDERS:
                        showOrdersMenu();
                        break;
                    case COURIERS:
                        showCouriersMenu();
                        break;
                    case SHOPS:
                        showShops();
                        break;
                    case CUSTOMERS:
                        AdminCustomersMenu adminCustomersMenu = new AdminCustomersMenu(customersDB, shopsDBReference);
                        adminCustomersMenu.show();
                        break;
                    case SETTINGS:
                        showSettingsMenu();
                        break;
                    default:
                        break;
                }
            }
        }

    }

    private void showOrdersMenu() {
        printEnumOptions(ShopType.class);
        ShopType shopType = getOption(ShopType.class);
        if (shopType != null) {
            switch (shopType) {
                case RESTAURANT:
                    RestaurantAddOrderMenu restaurantAddOrderMenu = new RestaurantAddOrderMenu(
                            shopsDBReference.getRestaurantsDB(), admin.getSettings());
                    new OrdersMenu<>(shopsDBReference.getRestaurantsDB(),
                            restaurantAddOrderMenu, customersDB).show();
                    break;
                case SUPERMARKET:
                    SupermarketAddOrderMenu supermarketAddOrderMenu = new SupermarketAddOrderMenu(
                            shopsDBReference.getSupermarketsDB(), admin.getSettings());
                    new OrdersMenu<>(shopsDBReference.getSupermarketsDB(), supermarketAddOrderMenu, customersDB).show();
                    break;
                case FRUIT_SHOP:
                    FruitShopAddOrderMenu fruitShopAddOrderMenu = new FruitShopAddOrderMenu(
                            shopsDBReference.getFruitShopsDB(), admin.getSettings());
                    new OrdersMenu<>(shopsDBReference.getFruitShopsDB(), fruitShopAddOrderMenu, customersDB).show();
                default:
                    break;
            }
        }
    }

    private void showCouriersMenu() {
        CouriersMenu couriersMenu = new CouriersMenu(couriersDB, shopsDBReference);
        couriersMenu.show();
    }

    private void showSettingsMenu() {
        SettingsMenu settingsMenu = new SettingsMenu(admin.getSettings());
        settingsMenu.show();
    }

    private void showShops() {
        printEnumOptions(ShopType.class);
        ShopType shopType = getOption(ShopType.class);
        if (shopType != null) {
            switch (shopType) {
                case RESTAURANT:
                    RestaurantsMenu restaurantsMenu = new RestaurantsMenu(
                            shopsDBReference.getRestaurantsDB(), admin.getSettings(), couriersDB, ownersDB);
                    restaurantsMenu.show();
                    break;
                case SUPERMARKET:
                    SupermarketsMenu supermarketsMenu = new SupermarketsMenu(
                            shopsDBReference.getSupermarketsDB(), admin.getSettings(), couriersDB, ownersDB, customersDB);
                    supermarketsMenu.show();
                    break;
                case FRUIT_SHOP:
                    FruitShopsMenu fruitShopsMenu = new FruitShopsMenu(
                            shopsDBReference.getFruitShopsDB(), admin.getSettings(), couriersDB, ownersDB);
                    fruitShopsMenu.show();
                    break;
                default:
                    break;
            }
        }
    }

}
