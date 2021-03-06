package ir.ac.kntu.menu.shop.shop_info;

import ir.ac.kntu.db.CouriersDB;
import ir.ac.kntu.db.OwnersDB;
import ir.ac.kntu.menu.shop.restaurant.RestaurantFoodMenu;
import ir.ac.kntu.models.Restaurant;

public class RestaurantInfoMenu extends ShopInfoMenu<Restaurant> {

    public RestaurantInfoMenu(Restaurant shop, CouriersDB couriersDB, OwnersDB ownersDB) {
        super(shop, couriersDB, ownersDB);
    }

    @Override
    public void show() {
        RestaurantInfoMenuOption option;
        while ((option = printMenuOptions("Restaurant Info", RestaurantInfoMenuOption.class))
                != RestaurantInfoMenuOption.BACK) {
            if (option != null) {
                switch (option) {
                    case UPDATE:
                        updateShop();
                        break;
                    case GENERAL_INFO:
                        showShopGeneralInfo();
                        break;
                    case FOOD:
                        showShopFoodMenu();
                        break;
                    case COURIER:
                        showShopCouriers();
                        break;
                    case ORDER:
                        showOrders();
                        break;
                    case FEEDBACK:
                        showFeedbacks();
                        break;
                    default:
                        break;
                }
            }
        }
    }

    @Override
    public void showShopFoodMenu() {
        RestaurantFoodMenu restaurantFoodMenu = new RestaurantFoodMenu(getShop());
        restaurantFoodMenu.show();
    }

    @Override
    public void updateShop() {
        Restaurant newRestaurantInfo = getRestaurantGeneralInfo(getOwnersDB());
        if (newRestaurantInfo == null) {
            return;
        }
        getShop().setOwner(newRestaurantInfo.getOwner());
        getShop().setName(newRestaurantInfo.getName());
        getShop().setAddress(newRestaurantInfo.getAddress());
        getShop().setSchedule(newRestaurantInfo.getSchedule());
        getShop().setPriceType(newRestaurantInfo.getPriceType());
        getShop().setDeliveryPrice(newRestaurantInfo.getDeliveryPrice());
        System.out.println("Restaurant is updated");
    }
}
