package ir.ac.kntu.db;

import ir.ac.kntu.models.*;
import ir.ac.kntu.utils.IdGenerator;

import java.util.*;

public class RestaurantsDB {
    private Set<Restaurant> restaurants;

    public RestaurantsDB(Set<Restaurant> restaurants) {
        this.restaurants = restaurants;
    }

    public void setRestaurants(Set<Restaurant> restaurants) {
        this.restaurants = restaurants;
    }

    public boolean addRestaurant(Restaurant restaurant) {
        if (containsRestaurant(restaurant.getName(), restaurant.getAddress())) {
            return false;
        }
        Restaurant newRestaurant = new Restaurant(IdGenerator.generateNewId(), restaurant.getName(),
                restaurant.getAddress(), restaurant.getSchedule(), restaurant.getHiredCouriers(), restaurant.getOrdersService(),
                restaurant.getDeliveryPrice(), restaurant.getFoodMenu(), restaurant.getPriceType());
        restaurants.add(newRestaurant);
        return true;
    }

    public boolean removeRestaurant(Restaurant restaurant) {
        return restaurants.remove(restaurant);
    }

    public boolean containsRestaurant(Restaurant restaurant) {
        return restaurants.contains(restaurant);
    }

    public boolean containsRestaurant(String name, String address) {
        for (Restaurant restaurant : restaurants) {
            if (restaurant.getName().equals(name) && restaurant.getAddress().equals(address)) {
                return true;
            }
        }
        return false;
    }

    public Set<Restaurant> getRestaurantsByName(String name) {
        Set<Restaurant> foundRestaurants = new HashSet<>();
        for (Restaurant restaurant : restaurants) {
            if (restaurant.getName().equals(name)) {
                foundRestaurants.add(restaurant);
            }
        }
        return foundRestaurants;
    }

    public Set<Restaurant> getRestaurantsByPriceType(RestaurantPriceType priceType) {
        Set<Restaurant> foundRestaurants = new HashSet<>();
        for (Restaurant restaurant : restaurants) {
            if (restaurant.getPriceType() == priceType) {
                foundRestaurants.add(restaurant);
            }
        }
        return foundRestaurants;
    }

    public Set<Restaurant> getActiveRestaurants() {
        Set<Restaurant> activeRestaurants = new HashSet<>();
        for (Restaurant restaurant : restaurants) {
            if (restaurant.isActive()) {
                activeRestaurants.add(restaurant);
            }
        }
        return activeRestaurants;
    }

    public Restaurant getRestaurantById(int restaurantId) {
        for (Restaurant restaurant : restaurants) {
            if (restaurant.getId() == restaurantId) {
                return restaurant;
            }
        }
        return null;
    }

    public Set<Order> getAllOrders() {
        Set<Order> orders = new HashSet<>();
        for (Restaurant restaurant : restaurants) {
            orders.addAll(restaurant.getOrdersService().getOrders());
        }
        return orders;
    }

    public List<Feedback> getAllFeedbacksOfFood(Food food) {
        List<Feedback> feedbacks = new ArrayList<>();
        for (Restaurant restaurant : restaurants) {
            feedbacks.addAll(restaurant.getOrdersService().getFeedbacksOfFood(food));
        }
        return feedbacks;
    }

    public List<Restaurant> getBestRestaurants(int count) {
        List<Restaurant> orderedRestaurants = getOrderedListOfRestaurantsByRating(false);
        if (orderedRestaurants.size() > count) {
            orderedRestaurants = orderedRestaurants.subList(0, count);
        }
        return orderedRestaurants;
    }

    public List<Food> getBestFoodsOfEachRestaurant(int bestFoodsCountOfEachRestaurant) {
        List<Food> bestFoods = new ArrayList<>();
        for (Restaurant restaurant : restaurants) {
            bestFoods.addAll(restaurant.getOrdersService().getBestFoods(bestFoodsCountOfEachRestaurant));
        }
        return bestFoods;
    }

    public List<Restaurant> getRestaurantsByFood(String foodName) {
        List<Restaurant> foundRestaurants = new ArrayList<>();
        for (Restaurant restaurant : restaurants) {
            if (restaurant.getFoodMenu().containsFood(new Food(foodName))) {
                foundRestaurants.add(restaurant);
            }
        }
        return foundRestaurants;
    }

    public List<Restaurant> getBestRestaurantsByFood(String foodName, int count) {
        List<Restaurant> orderedList = getRestaurantsByFood(foodName);
        orderedList.sort((o1, o2) -> compareNumbers(o2.getOrdersService().getRatingAverageOfFood(foodName),
                o1.getOrdersService().getRatingAverageOfFood(foodName)));
        if (orderedList.size() > count) {
            orderedList = orderedList.subList(0, count);
        }
        return orderedList;
    }

    public Restaurant getRestaurantByOrderId(int orderId) {
        for (Restaurant restaurant : restaurants) {
            if (restaurant.getOrdersService().containsOrder(orderId)) {
                return restaurant;
            }
        }
        return null;
    }

    public List<Restaurant> getOrderedListOfRestaurantsByRating(boolean isAscending) {
        List<Restaurant> orderedList = new ArrayList<>(restaurants);
        orderedList.sort((o1, o2) -> {
            if (isAscending) {
                return compareNumbers(o1.getRating(), o2.getRating());
            } else {
                return compareNumbers(o2.getRating(), o1.getRating());
            }
        });
        return orderedList;
    }

    public List<Restaurant> getOrderedListOfRestaurantsByFeedbacksCount(boolean isAscending) {
        List<Restaurant> orderedList = new ArrayList<>(restaurants);
        orderedList.sort((o1, o2) -> {
            int size1 = o1.getOrdersService().getCountOfAllFeedbacks();
            int size2 = o2.getOrdersService().getCountOfAllFeedbacks();
            if (isAscending) {
                return compareNumbers(size1, size2);
            } else {
                return compareNumbers(size2, size1);
            }
        });
        return orderedList;
    }

    public List<Restaurant> getWeakerRestaurants() {
        List<Restaurant> orderedRestaurants = new ArrayList<>(restaurants);
        orderedRestaurants.sort((o1, o2) -> compareNumbers(o1.getAlphaScore(), o2.getAlphaScore()));
        return orderedRestaurants;
    }

    public List<Restaurant> getOrderedListOfRestaurants(Settings settings) {
        switch (settings.getRestaurantsFilteringStrategy()) {
            case BY_RATING_ASCENDING:
                return getOrderedListOfRestaurantsByRating(true);
            case BY_RATING_DESCENDING:
                return getOrderedListOfRestaurantsByRating(false);
            case BY_FEEDBACKS_COUNT_ASCENDING:
                return getOrderedListOfRestaurantsByFeedbacksCount(true);
            case BY_FEEDBACKS_COUNT_DESCENDING:
                return getOrderedListOfRestaurantsByFeedbacksCount(false);
            case BY_ALPHA_SCORE:
                return getWeakerRestaurants();
            default:
                break;
        }
        return getOrderedListOfRestaurantsByRating(false);
    }

    private int compareNumbers(double one, double two) {
        if (one < two) {
            return -1;
        } else if (one > two) {
            return 1;
        }
        return 0;
    }

    public void printRestaurants(List<Restaurant> restaurants) {
        for (int i = 0; i < restaurants.size(); i++) {
            Restaurant restaurant = restaurants.get(i);
            System.out.println("No." + (i + 1) + " " + restaurant);
        }
        System.out.println(restaurants.size() + " restaurants found");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RestaurantsDB that = (RestaurantsDB) o;
        return restaurants.equals(that.restaurants);
    }

    @Override
    public int hashCode() {
        return Objects.hash(restaurants);
    }

    @Override
    public String toString() {
        return "{" +
                "restaurants=" + restaurants +
                '}';
    }
}
