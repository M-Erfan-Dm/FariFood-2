package ir.ac.kntu.menu.restaurant;

import ir.ac.kntu.db.CouriersDB;
import ir.ac.kntu.db.RestaurantsDB;
import ir.ac.kntu.menu.Menu;
import ir.ac.kntu.models.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class RestaurantsMenu extends Menu {

    private final RestaurantsDB restaurantsDB;

    private final Settings settings;

    private final CouriersDB couriersDB;

    public RestaurantsMenu(RestaurantsDB restaurantsDB, Settings settings, CouriersDB couriersDB) {
        this.restaurantsDB = restaurantsDB;
        this.settings = settings;
        this.couriersDB = couriersDB;
    }

    @Override
    public void show() {
        RestaurantsOption restaurantsOption = printMenuOptions();
        while (restaurantsOption != RestaurantsOption.BACK) {
            if (restaurantsOption != null) {
                switch (restaurantsOption) {
                    case ADD:
                        addRestaurant();
                        break;
                    case UPDATE:
                        updateRestaurant();
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
                        removeRestaurant();
                        break;
                    default:
                        break;
                }
            }
            restaurantsOption = printMenuOptions();
        }
    }

    private RestaurantsOption printMenuOptions() {
        System.out.println("----------Restaurants Menu----------");
        RestaurantsOption.printOptions();
        System.out.print("Enter your choice : ");
        return getOption(RestaurantsOption.class);
    }

    private void addRestaurant() {
        Restaurant restaurant = getRestaurantGeneralInfo();
        if (restaurant == null) {
            return;
        }
        restaurant.setFoodMenu(new FoodMenu(new HashSet<>()));
        restaurant.setHiredCouriers(new CouriersDB(new HashSet<>()));
        restaurant.setOrdersService(new OrdersService(new HashSet<>()));
        boolean isAdded = restaurantsDB.addRestaurant(restaurant);
        if (isAdded) {
            System.out.println("Restaurant is added");
        } else {
            System.out.println("Restaurant already exists");
        }
    }

    private void updateRestaurant() {
        int id = getId();
        Restaurant restaurant = restaurantsDB.getShopById(id);
        if (restaurant == null) {
            System.out.println("Restaurant not found!");
            return;
        }
        Restaurant newRestaurantInfo = getRestaurantGeneralInfo();
        if (newRestaurantInfo == null) {
            return;
        }
        restaurant.setName(newRestaurantInfo.getName());
        restaurant.setAddress(newRestaurantInfo.getAddress());
        restaurant.setSchedule(newRestaurantInfo.getSchedule());
        restaurant.setPriceType(newRestaurantInfo.getPriceType());
        System.out.println("Restaurant is updated");
    }

    private void findById() {
        int id = getId();
        Restaurant restaurant = restaurantsDB.getShopById(id);
        if (restaurant == null) {
            System.out.println("Restaurant not found");
            return;
        }
        RestaurantInfoOption.printOptions();
        System.out.println("Enter your choice :");
        RestaurantInfoOption restaurantInfoOption = getOption(RestaurantInfoOption.class);
        while (restaurantInfoOption != null && restaurantInfoOption != RestaurantInfoOption.BACK) {
            switch (restaurantInfoOption) {
                case GENERAL:
                    showRestaurantGeneralInfo(restaurant);
                    break;
                case FOOD:
                    showRestaurantFoodMenu(restaurant);
                    break;
                case COURIER:
                    showRestaurantCouriers(restaurant);
                    break;
                case ORDER:
                    showOrders(restaurant);
                    break;
                case FEEDBACK:
                    showFeedbacks(restaurant);
                    break;
                default:
                    break;
            }
            RestaurantInfoOption.printOptions();
            System.out.println("Enter your choice :");
            restaurantInfoOption = getOption(RestaurantInfoOption.class);
        }
    }

    private void findByName() {
        String name = getName();
        List<Restaurant> restaurants = new ArrayList<>(restaurantsDB.getRestaurantsByName(name));
        restaurantsDB.printRestaurants(restaurants);
    }

    private void findByPriceType() {
        RestaurantPriceType restaurantPriceType = getRestaurantPriceType();
        if (restaurantPriceType == null) {
            return;
        }
        List<Restaurant> restaurants = new ArrayList<>(restaurantsDB.getRestaurantsByPriceType(restaurantPriceType));
        restaurantsDB.printRestaurants(restaurants);
    }

    private void showAll() {
        List<Restaurant> restaurants = restaurantsDB.getOrderedListOfRestaurants(settings);
        restaurantsDB.printRestaurants(restaurants);
    }

    private void removeRestaurant() {
        int id = getId();
        Restaurant restaurant = restaurantsDB.getShopById(id);
        boolean isRemoved = restaurantsDB.remove(restaurant);
        if (isRemoved) {
            System.out.println("Restaurant is removed");
        } else {
            System.out.println("Restaurant not found");
        }
    }

    private Restaurant getRestaurantGeneralInfo() {
        String name = getName();
        String address = getAddress();
        Schedule schedule = getSchedule();
        if (schedule == null) {
            return null;
        }
        RestaurantPriceType restaurantPriceType = getRestaurantPriceType();
        if (restaurantPriceType == null) {
            return null;
        }
        return new Restaurant(name, address, schedule, restaurantPriceType);
    }

    private void showRestaurantGeneralInfo(Restaurant restaurant) {
        System.out.println(restaurant);
    }

    private void showRestaurantFoodMenu(Restaurant restaurant) {
        RestaurantFoodMenu restaurantFoodMenu = new RestaurantFoodMenu(restaurant);
        restaurantFoodMenu.show();
    }

    private void showRestaurantCouriers(Restaurant restaurant) {
        RestaurantCourierMenu restaurantCourierMenu = new RestaurantCourierMenu(couriersDB, restaurant);
        restaurantCourierMenu.show();
    }

    private void showOrders(Restaurant restaurant) {
        restaurant.getOrdersService().printAllOrders();
    }

    private void showFeedbacks(Restaurant restaurant) {
        List<Feedback> feedbacks = restaurant.getOrdersService().getAllFeedbacks();
        for (int i = 0; i < feedbacks.size(); i++) {
            Feedback feedback = feedbacks.get(i);
            System.out.println("No." + (i + 1) + " " + feedback);
        }
        System.out.println(feedbacks.size() + " feedbacks found");
    }

}
