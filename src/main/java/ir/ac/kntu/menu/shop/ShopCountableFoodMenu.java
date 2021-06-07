package ir.ac.kntu.menu.shop;

import ir.ac.kntu.menu.Menu;
import ir.ac.kntu.models.CountableFoodMenu;
import ir.ac.kntu.models.Food;
import ir.ac.kntu.utils.ScannerWrapper;

public class ShopCountableFoodMenu extends Menu {

    private CountableFoodMenu foodMenu;

    public ShopCountableFoodMenu(CountableFoodMenu foodMenu) {
        this.foodMenu = foodMenu;
    }

    @Override
    public void show() {
        ShopCountableFoodMenuOption option;
        while ((option = printMenuOptions("Food Menu", ShopCountableFoodMenuOption.class))
                != ShopCountableFoodMenuOption.BACK) {
            if (option != null) {
                switch (option) {
                    case BUY:
                        buy();
                        break;
                    case UPDATE:
                        update();
                        break;
                    case SHOW_FOOD:
                        showFood();
                        break;
                    case SHOW_ALL:
                        showAll();
                        break;
                    default:
                        break;
                }
            }
        }
    }

    private void buy() {
        String name = getName();
        Food food = new Food(name);
        if (!foodMenu.containsFood(food)) {
            Integer price = getPrice();
            if (price == null) {
                return;
            }
            food.setPrice(price);
        }
        Integer amount = getAmount();
        if (amount == null) {
            return;
        }
        boolean bought = foodMenu.buyFood(food, amount);
        if (bought){
            System.out.println("You have bought the food");
        }else {
            System.out.println("Couldn't buy the food");
        }
    }

    private void update() {
        String name = getName();
        Food food = new Food(name);
        Integer price = getPrice();
        if (price == null) {
            return;
        }
        food.setPrice(price);
        boolean isUpdated = foodMenu.updateFood(food);
        if (isUpdated){
            System.out.println("Food updated");
        }else {
            System.out.println("Food not found");
        }
    }

    private void showFood(){
        String name = getName();
        Food food = foodMenu.getFoodByName(name);
        if (food==null){
            System.out.println("Food not found");
            return;
        }
        int amount = foodMenu.getAmountOfFood(food);
        System.out.println(food + " ," + amount + " in stock");
    }

    private void showAll(){
        foodMenu.printAllFoods();
    }

    private Integer getAmount() {
        System.out.println("Enter amount :");
        int amount = Integer.parseInt(ScannerWrapper.nextLine());
        if (amount < 1) {
            System.out.println("Invalid amount");
            return null;
        }
        return amount;
    }
}
