package ir.ac.kntu.menu.order;

import ir.ac.kntu.db.CustomersDB;
import ir.ac.kntu.db.RestaurantsDB;
import ir.ac.kntu.menu.Menu;
import ir.ac.kntu.models.*;
import ir.ac.kntu.utils.ScannerWrapper;

import java.util.*;

public class AddOrderMenu extends Menu {

    private final CustomersDB customersDB;

    private final RestaurantsDB restaurantsDB;

    private final Settings settings;

    public AddOrderMenu(CustomersDB customersDB, RestaurantsDB restaurantsDB, Settings settings) {
        this.customersDB = customersDB;
        this.restaurantsDB = restaurantsDB;
        this.settings = settings;
    }

    @Override
    public void show() {
        System.out.println("Customer :");
        Customer customer = getCustomer();
        if (customer == null) {
            return;
        }
        System.out.println("Restaurant :");
        Restaurant restaurant = chooseRestaurant();
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
        Order order = new Order(foods, null, customer, null, OrderState.PROCESSING);
        restaurant.getOrdersService().addOrder(order);
        System.out.println("Your order is in process");
    }

    private Customer getCustomer() {
        String phoneNumber = getPhoneNumber();
        Customer customer = customersDB.getCustomerByPhoneNumber(phoneNumber);
        if (customer == null) {
            System.out.println("Customer not found");
            return null;
        }
        return customer;
    }

    private Restaurant chooseRestaurant() {
        OrdersRestaurantOption.printOptions();
        System.out.println("Enter your choice :");
        OrdersRestaurantOption ordersRestaurantOption = getOption(OrdersRestaurantOption.class);
        if (ordersRestaurantOption == null) {
            return null;
        }
        switch (ordersRestaurantOption) {
            case SHOW_ACTIVE_RESTAURANTS:
                return showActiveRestaurants();
            case SHOW_THREE_BEST_RESTAURANTS:
                return showThreeBestRestaurants();
            case SHOW_RESTAURANTS_BY_NAME:
                return showRestaurantsByName();
            case SHOW_RESTAURANTS_BY_PRICE_TYPE:
                return showRestaurantsByPriceType();
            case SHOW_FIVE_BEST_RESTAURANTS_BY_FOOD:
                return showFiveBestRestaurantsByFood();
            default:
                break;
        }
        return null;
    }

    private OrdersFoodOption printOrderFoodOptions() {
        OrdersFoodOption.printOptions();
        System.out.println("Enter your choice :");
        return getOption(OrdersFoodOption.class);
    }

    private boolean showFoods(Restaurant restaurant) {
        OrdersFoodOption ordersFoodOption = printOrderFoodOptions();
        while (ordersFoodOption != OrdersFoodOption.BACK) {
            if (ordersFoodOption != null) {
                boolean shouldContinueSubmittingFoods = false;
                switch (ordersFoodOption) {
                    case SHOW_ALL:
                        shouldContinueSubmittingFoods = showAllFoods(restaurant);
                        break;
                    case SHOW_THREE_BEST:
                        shouldContinueSubmittingFoods = showThreeBestFoods(restaurant);
                        break;
                    default:
                        break;
                }
                if (shouldContinueSubmittingFoods) {
                    return true;
                }
            }
            ordersFoodOption = printOrderFoodOptions();
        }
        return false;
    }


    private Restaurant showActiveRestaurants() {
        List<Restaurant> restaurants = new RestaurantsDB(restaurantsDB.getActiveRestaurants()).getOrderedListOfRestaurants(settings);
        restaurantsDB.printRestaurants(restaurants);
        if (restaurants.size() == 0) {
            return null;
        }
        return getRestaurantById();
    }

    private Restaurant showThreeBestRestaurants() {
        List<Restaurant> restaurants = restaurantsDB.getBestRestaurants(3);
        restaurantsDB.printRestaurants(restaurants);
        if (restaurants.size() == 0) {
            return null;
        }
        return getRestaurantById();
    }

    private Restaurant showRestaurantsByName() {
        String name = getName();
        List<Restaurant> foundRestaurantsByName = new RestaurantsDB(restaurantsDB.getRestaurantsByName(name)).getOrderedListOfRestaurants(settings);
        restaurantsDB.printRestaurants(foundRestaurantsByName);
        if (foundRestaurantsByName.size() == 0) {
            return null;
        }
        return getRestaurantById();
    }

    private Restaurant showRestaurantsByPriceType() {
        RestaurantPriceType restaurantPriceType = getRestaurantPriceType();
        if (restaurantPriceType == null) {
            return null;
        }
        List<Restaurant> foundRestaurantsByPriceType = new RestaurantsDB(restaurantsDB.
                getRestaurantsByPriceType(restaurantPriceType)).getOrderedListOfRestaurants(settings);
        restaurantsDB.printRestaurants(foundRestaurantsByPriceType);
        if (foundRestaurantsByPriceType.size() == 0) {
            return null;
        }
        return getRestaurantById();
    }

    private Restaurant showFiveBestRestaurantsByFood() {
        String foodName = getName();
        List<Restaurant> restaurants = restaurantsDB.getBestRestaurantsByFood(foodName, 5);
        restaurantsDB.printRestaurants(restaurants);
        if (restaurants.size() == 0) {
            return null;
        }
        return getRestaurantById();
    }

    private Restaurant getRestaurantById() {
        int id = getId();
        Restaurant restaurant = restaurantsDB.getRestaurantById(id);
        if (restaurant == null) {
            System.out.println("Restaurant not found");
            return null;
        }
        if (!restaurant.isActive()) {
            System.out.println("Restaurant is closed!");
            return null;
        }
        return restaurant;
    }

    private boolean showAllFoods(Restaurant restaurant) {
        FoodMenu foodMenu = restaurant.getFoodMenu();
        Set<Food> foods = foodMenu.getFoods();
        foodMenu.printAllFoods();
        if (foods.size() == 0) {
            return false;
        }
        return continueSubmittingFood();
    }

    private boolean showThreeBestFoods(Restaurant restaurant) {
        List<Food> foods = restaurant.getOrdersService().getBestFoods(3);
        new FoodMenu(new HashSet<>(foods)).printAllFoods();
        if (foods.size() == 0) {
            return false;
        }
        return continueSubmittingFood();
    }

    private boolean continueSubmittingFood() {
        System.out.println("Continue ?\n1.Yes 2.No");
        int choice = Integer.parseInt(ScannerWrapper.nextLine()) - 1;
        return choice == 0;
    }

    private Map<Food, Integer> chooseFoods(Restaurant restaurant) {
        Map<Food, Integer> foods = new HashMap<>();
        System.out.println("Enter foods you want (or blank to stop adding)");
        String name;
        while (!(name = getName()).isBlank()) {
            Food food = restaurant.getFoodMenu().getFoodByName(name);
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
}
