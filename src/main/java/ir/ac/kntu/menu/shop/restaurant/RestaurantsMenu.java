package ir.ac.kntu.menu.shop.restaurant;

import ir.ac.kntu.db.CouriersDB;
import ir.ac.kntu.db.OwnersDB;
import ir.ac.kntu.db.RestaurantsDB;
import ir.ac.kntu.menu.Menu;
import ir.ac.kntu.menu.shop.ShopCourierMenu;
import ir.ac.kntu.menu.shop.ShopsOption;
import ir.ac.kntu.models.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class RestaurantsMenu extends Menu {

    private final RestaurantsDB restaurantsDB;

    private final Settings settings;

    private final CouriersDB couriersDB;

    private final OwnersDB ownersDB;

    public RestaurantsMenu(RestaurantsDB restaurantsDB, Settings settings, CouriersDB couriersDB, OwnersDB ownersDB) {
        this.restaurantsDB = restaurantsDB;
        this.settings = settings;
        this.couriersDB = couriersDB;
        this.ownersDB = ownersDB;
    }

    @Override
    public void show() {
        ShopsOption option;
        while ((option = printMenuOptions("Restaurants Menu",ShopsOption.class))
                != ShopsOption.BACK) {
            if (option != null) {
                switch (option) {
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
        }
    }

    private void addRestaurant() {
        Restaurant restaurant = getRestaurantGeneralInfo();
        if (restaurant == null) {
            return;
        }
        restaurant.setFoodMenu(new FoodMenu(new HashSet<>()));
        restaurant.setHiredCouriers(new CouriersDB(new HashSet<>()));
        restaurant.setOrdersService(new OrdersService<>(new HashSet<>()));
        boolean isAdded = restaurantsDB.add(restaurant);
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
        restaurant.setOwner(newRestaurantInfo.getOwner());
        restaurant.setName(newRestaurantInfo.getName());
        restaurant.setAddress(newRestaurantInfo.getAddress());
        restaurant.setSchedule(newRestaurantInfo.getSchedule());
        restaurant.setPriceType(newRestaurantInfo.getPriceType());
        restaurant.setDeliveryPrice(newRestaurantInfo.getDeliveryPrice());
        System.out.println("Restaurant is updated");
    }

    private void findById() {
        int id = getId();
        Restaurant restaurant = restaurantsDB.getShopById(id);
        if (restaurant == null) {
            System.out.println("Restaurant not found");
            return;
        }
        printEnumOptions(RestaurantInfoOption.class);
        System.out.println("Enter your choice :");
        RestaurantInfoOption option = getOption(RestaurantInfoOption.class);
        while (option != null && option != RestaurantInfoOption.BACK) {
            switch (option) {
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
            printEnumOptions(RestaurantInfoOption.class);
            System.out.println("Enter your choice :");
            option = getOption(RestaurantInfoOption.class);
        }
    }

    private void findByName() {
        String name = getName();
        List<Restaurant> restaurants = new ArrayList<>(restaurantsDB.getShopsByName(name));
        restaurantsDB.printRestaurants(restaurants);
    }

    private void findByPriceType() {
        ShopPriceType restaurantPriceType = getRestaurantPriceType();
        if (restaurantPriceType == null) {
            return;
        }
        List<Restaurant> restaurants = new ArrayList<>(restaurantsDB.getShopsByPriceType(restaurantPriceType));
        restaurantsDB.printRestaurants(restaurants);
    }

    private void showAll() {
        List<Restaurant> restaurants = restaurantsDB.getOrderedListOfShops(settings);
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
        Owner owner = getOwner(ownersDB);
        if (owner==null){
            return null;
        }
        String name = getName();
        String address = getAddress();
        Schedule schedule = getSchedule();
        if (schedule == null) {
            return null;
        }
        ShopPriceType restaurantPriceType = getRestaurantPriceType();
        if (restaurantPriceType == null) {
            return null;
        }
        System.out.println("Delivery price :");
        Integer deliveryPrice=  getPrice();
        if (deliveryPrice==null){
            return null;
        }
        return new Restaurant(owner,name, address, schedule, restaurantPriceType,deliveryPrice);
    }

    private void showRestaurantGeneralInfo(Restaurant restaurant) {
        System.out.println(restaurant);
    }

    private void showRestaurantFoodMenu(Restaurant restaurant) {
        RestaurantFoodMenu restaurantFoodMenu = new RestaurantFoodMenu(restaurant);
        restaurantFoodMenu.show();
    }

    private void showRestaurantCouriers(Restaurant restaurant) {
        ShopCourierMenu shopCourierMenu = new ShopCourierMenu(couriersDB,restaurant);
        shopCourierMenu.show();
    }

    private void showOrders(Restaurant restaurant) {
        restaurant.getOrdersService().printAllOrders();
    }

    private void showFeedbacks(Restaurant restaurant) {
        List<Feedback> feedbacks = restaurant.getOrdersService().getAllFeedbacks();
        printList(feedbacks,"feedbacks");
    }

}
