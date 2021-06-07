package ir.ac.kntu.menu.order;

import ir.ac.kntu.db.RestaurantsDB;
import ir.ac.kntu.models.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RestaurantAddOrderMenu extends AddOrderMenu<Restaurant, RestaurantsDB>{

    public RestaurantAddOrderMenu(Customer customer, RestaurantsDB shopsDB, Settings settings) {
        super(customer, shopsDB, settings);
    }

    @Override
    public void show() {
        System.out.println("Restaurant :");
        Restaurant restaurant = chooseShop();
        if (restaurant == null) {
            return;
        }
        System.out.println("Food :");
        boolean continueSubmittingFoods = showFoods(restaurant);
        if (!continueSubmittingFoods) {
            return;
        }
        Map<Food, Integer> foods = chooseFoods(restaurant);
        if (foods.size() == 0) {
            System.out.println("No food was added");
            return;
        }
        Order order = new Order(foods, null, getCustomer(), null, OrderState.PROCESSING);
        restaurant.getOrdersService().addOrder(order);
        System.out.println("Your order is in process");
    }

    @Override
    public List<Food> getAllFoods(Restaurant shop) {
        return new ArrayList<>(shop.getFoodMenu().getFoods());
    }

    @Override
    public List<Food> getThreeBestFoods(Restaurant shop) {
        return shop.getOrdersService().getBestFoods(3);
    }

    @Override
    public Food getFoodByName(Restaurant shop, String name) {
        return shop.getFoodMenu().getFoodByName(name);
    }

    @Override
    public List<Restaurant> getFiveBestShopsByFood(Food food) {
        return getShopsDB().getBestRestaurantsByFood(food,5);
    }
}
