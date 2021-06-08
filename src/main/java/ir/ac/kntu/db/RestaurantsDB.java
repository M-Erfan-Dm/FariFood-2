package ir.ac.kntu.db;

import ir.ac.kntu.models.Food;
import ir.ac.kntu.models.Restaurant;
import ir.ac.kntu.utils.ListSorting;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class RestaurantsDB extends ShopsDB<Restaurant> {

    public RestaurantsDB(Set<Restaurant> restaurants) {
        super(restaurants);
    }


    public List<Restaurant> getRestaurantsByFood(Food food) {
        return getShops().stream().filter(shop -> shop.getFoodMenu().containsFood(food))
                .collect(Collectors.toList());
    }

    public List<Restaurant> getBestRestaurantsByFood(Food food, int count) {
        return ListSorting.sortList(getRestaurantsByFood(food), count, false,
                restaurant -> restaurant.getOrdersService().getRatingAverageOfFood(food.getName()));
    }
}
