package ir.ac.kntu.menu.shop.restaurant;

import ir.ac.kntu.db.CouriersDB;
import ir.ac.kntu.db.OwnersDB;
import ir.ac.kntu.db.RestaurantsDB;
import ir.ac.kntu.menu.shop.ShopsMenu;
import ir.ac.kntu.menu.shop.shop_info.RestaurantInfoMenu;
import ir.ac.kntu.models.FoodMenu;
import ir.ac.kntu.models.OrdersService;
import ir.ac.kntu.models.Restaurant;
import ir.ac.kntu.models.Settings;

import java.util.HashSet;

public class RestaurantsMenu extends ShopsMenu<Restaurant, RestaurantsDB> {


    public RestaurantsMenu(RestaurantsDB shopsDB, Settings settings, CouriersDB couriersDB, OwnersDB ownersDB) {
        super(shopsDB, settings, couriersDB, ownersDB);
    }

    @Override
    public void addShop() {
        Restaurant restaurant = getRestaurantGeneralInfo(getOwnersDB());
        if (restaurant == null) {
            return;
        }
        restaurant.setFoodMenu(new FoodMenu(new HashSet<>()));
        restaurant.setHiredCouriers(new CouriersDB(new HashSet<>()));
        restaurant.setOrdersService(new OrdersService<>(new HashSet<>()));
        boolean isAdded = getShopsDB().add(restaurant);
        if (isAdded) {
            System.out.println("Restaurant is added");
        } else {
            System.out.println("Restaurant already exists");
        }
    }

    @Override
    public void findById() {
        int id = getId();
        Restaurant restaurant = getShopsDB().getShopById(id);
        if (restaurant == null) {
            System.out.println("Restaurant not found");
            return;
        }
        RestaurantInfoMenu restaurantInfoMenu = new RestaurantInfoMenu(restaurant, getCouriersDB(), getOwnersDB());
        restaurantInfoMenu.show();
    }


}
