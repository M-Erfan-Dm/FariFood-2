package ir.ac.kntu.menu.order;

import ir.ac.kntu.db.RestaurantsDB;
import ir.ac.kntu.models.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RestaurantAddOrderMenu extends AddOrderMenu<Restaurant, RestaurantsDB>{

    public RestaurantAddOrderMenu(RestaurantsDB shopsDB, Settings settings) {
        super(shopsDB, settings);
    }

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
    public Map<Food, Integer> chooseFoods(Restaurant shop) {
        Map<Food, Integer> foods = new HashMap<>();
        System.out.println("Enter foods you want (or blank to stop adding)");
        String name;
        while (!(name = getName()).isBlank()) {
            Food food = shop.getFoodMenu().getFoodByName(name);
            if (food == null) {
                System.out.println("Food not found");
                continue;
            }
            Integer count = getCount(1);
            if (count == null) {
                System.out.println("Invalid count");
                continue;
            }
            if (foods.containsKey(food)){
                foods.put(food,foods.get(food) + count);
            }else {
                foods.put(food,count);
            }
            System.out.println("Food is added");
        }
        return foods;
    }

    @Override
    public void showAllFoods(Restaurant shop) {
        List<Food> foods = new ArrayList<>(shop.getFoodMenu().getFoods());
        printList(foods,"foods");
    }

    @Override
    public void showThreeBestFoods(Restaurant shop) {
        List<Food> foods = shop.getOrdersService().getBestFoods(3);
        printList(foods,"foods");
    }


    @Override
    public List<Restaurant> getFiveBestShopsByFood(Food food) {
        return getShopsDB().getBestRestaurantsByFood(food,5);
    }
}
