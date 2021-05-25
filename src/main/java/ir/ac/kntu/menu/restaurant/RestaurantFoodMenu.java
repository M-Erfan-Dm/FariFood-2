package ir.ac.kntu.menu.restaurant;

import ir.ac.kntu.menu.Menu;
import ir.ac.kntu.models.Food;
import ir.ac.kntu.models.FoodMenu;
import ir.ac.kntu.models.Restaurant;

public class RestaurantFoodMenu extends Menu {

    private final Restaurant restaurant;

    public RestaurantFoodMenu(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    @Override
    public void show() {
        RestaurantFoodMenuOption restaurantFoodMenuOption = printMenuOptions();
        while (restaurantFoodMenuOption != RestaurantFoodMenuOption.BACK) {
            if (restaurantFoodMenuOption != null) {
                switch (restaurantFoodMenuOption) {
                    case ADD:
                        addFood();
                        break;
                    case UPDATE:
                        updateFood();
                        break;
                    case FIND_FOOD_BY_NAME:
                        findByName();
                        break;
                    case SHOW_ALL:
                        showAll();
                        break;
                    case REMOVE:
                        removeFood();
                        break;
                    default:
                        break;
                }
            }
            restaurantFoodMenuOption = printMenuOptions();
        }
    }

    private RestaurantFoodMenuOption printMenuOptions() {
        System.out.println("----------Restaurant Food Menu----------");
        RestaurantFoodMenuOption.printOptions();
        System.out.print("Enter your choice : ");
        return getOption(RestaurantFoodMenuOption.class);
    }

    private void addFood() {
        Food food = getFoodInfo();
        if (food == null) {
            return;
        }
        FoodMenu foodMenu = restaurant.getFoodMenu();
        if (foodMenu.containsFood(food)) {
            System.out.println("Food already exists!");
            return;
        }
        foodMenu.addFood(food);
        System.out.println("Food id added");
    }

    private void updateFood() {
        Food food = getFoodInfo();
        if (food == null) {
            return;
        }
        FoodMenu foodMenu = restaurant.getFoodMenu();
        if (!foodMenu.containsFood(food)) {
            System.out.println("Food not found");
            return;
        }
        foodMenu.addFood(food);
        System.out.println("Food is updated");
    }

    private void findByName() {
        String name = getName();
        FoodMenu foodMenu = restaurant.getFoodMenu();
        Food food = foodMenu.getFoodByName(name);
        if (food == null) {
            System.out.println("Food not found");
            return;
        }
        System.out.println(food);
    }

    private void showAll() {
        FoodMenu foodMenu = restaurant.getFoodMenu();
        foodMenu.printAllFoods();
    }

    private void removeFood() {
        String name = getName();
        FoodMenu foodMenu = restaurant.getFoodMenu();
        Food food = new Food(name, 0);
        boolean isRemoved = foodMenu.removeFood(food);
        if (isRemoved) {
            System.out.println("Food is removed");
        } else {
            System.out.println("Food not found");
        }
    }

    private Food getFoodInfo() {
        String name = getName();
        Integer price = getPrice();
        if (price == null) {
            return null;
        }
        return new Food(name, price);
    }

}
