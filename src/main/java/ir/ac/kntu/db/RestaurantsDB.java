package ir.ac.kntu.db;

import ir.ac.kntu.models.*;
import ir.ac.kntu.utils.ListSorting;

import java.util.*;
import java.util.stream.Collectors;

public class RestaurantsDB extends ShopsDB<Restaurant,Order>{

    public RestaurantsDB(Set<Restaurant> restaurants) {
        super(restaurants);
    }


    public List<Restaurant> getRestaurantsByFood(Food food) {
        return getShops().stream().filter(shop->shop.getFoodMenu().containsFood(food)).collect(Collectors.toList());
    }

    public List<Restaurant> getBestRestaurantsByFood(Food food, int count) {
        return ListSorting.sortList(getRestaurantsByFood(food),count,false,
                restaurant->restaurant.getOrdersService().getRatingAverageOfFood(food.getName()));
    }

    public void printRestaurants(List<Restaurant> restaurants) {
        for (int i = 0; i < restaurants.size(); i++) {
            Restaurant restaurant = restaurants.get(i);
            System.out.println("No." + (i + 1) + " " + restaurant);
        }
        System.out.println(restaurants.size() + " restaurants found");
    }
}
