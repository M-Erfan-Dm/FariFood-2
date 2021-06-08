package ir.ac.kntu.menu.order;

import ir.ac.kntu.db.SupermarketsDB;
import ir.ac.kntu.models.*;
import ir.ac.kntu.utils.ScannerWrapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SupermarketAddOrderMenu extends AddOrderMenu<Supermarket, SupermarketsDB> {

    public SupermarketAddOrderMenu(SupermarketsDB shopsDB, Settings settings) {
        super(shopsDB, settings);
    }

    public SupermarketAddOrderMenu(Customer customer, SupermarketsDB shopsDB, Settings settings) {
        super(customer, shopsDB, settings);
    }

    @Override
    public void show() {
        System.out.println("Supermarket :");
        Supermarket supermarket = chooseShop();
        if (supermarket == null) {
            return;
        }
        System.out.println("Food :");
        boolean continueSubmittingFoods = showFoods(supermarket);
        if (!continueSubmittingFoods) {
            return;
        }
        Map<Food, Integer> foods = chooseFoods(supermarket);
        if (foods.size() == 0) {
            System.out.println("No food was added");
            return;
        }
        TimePeriod timePeriod = choosePeriod(supermarket,
                new Order(foods, null, getCustomer(), null, OrderState.PROCESSING));
        if (timePeriod == null) {
            return;
        }
        sellFoods(foods, supermarket);
        PeriodicalOrder order = new PeriodicalOrder(foods, null, getCustomer(),
                null, OrderState.PROCESSING, timePeriod);
        int totalPrice = supermarket.getOrdersService().getTotalPriceForOrder(order, supermarket);
        System.out.println("Total price : " + totalPrice);
        supermarket.getOrdersService().addOrder(order);
        System.out.println("Your order is in process");
    }

    @Override
    public Map<Food, Integer> chooseFoods(Supermarket shop) {
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
            boolean isSold = shop.getFoodMenu().canSellFood(food, count);
            if (!isSold) {
                System.out.println("Not having enough food");
                continue;
            }
            if (foods.containsKey(food)) {
                foods.put(food, foods.get(food) + count);
            } else {
                foods.put(food, count);
            }
            System.out.println("Food is added");
        }
        return foods;
    }

    @Override
    public void showAllFoods(Supermarket shop) {
        Map<Food, Integer> foods = shop.getFoodMenu().getFoods();
        printList(new ArrayList<>(foods.entrySet()), "foods",
                (food, count) -> food.getKey() + " , " + food.getValue() + " in stock");
    }

    @Override
    public void showThreeBestFoods(Supermarket shop) {
        List<Food> foods = shop.getOrdersService().getBestFoods(3);
        printList(new ArrayList<>(foods), "foods", (food, count) -> {
            int amount = shop.getFoodMenu().getAmountOfFood(food);
            return "No." + count + " " + food + " , " + amount + " in stock";
        });
    }

    @Override
    public List<Supermarket> getFiveBestShopsByFood(Food food) {
        return getShopsDB().getBestSupermarketsByFood(food, 5);
    }

    private TimePeriod choosePeriod(Supermarket supermarket, Order order) {
        printEnumOptions(OrdersPeriodOption.class);
        System.out.println("Choose your option :");
        OrdersPeriodOption option = getOption(OrdersPeriodOption.class);
        if (option != null) {
            switch (option) {
                case SHOW_ACTIVE_PERIODS:
                    return showActivePeriods(supermarket, order);
                case SHOW_BEST_ACTIVE_PERIODS:
                    return showBestPeriods(supermarket, order);
                default:
                    break;
            }
        }
        return null;
    }

    private TimePeriod showActivePeriods(Supermarket supermarket, Order order) {
        List<TimePeriod> activePeriods = supermarket.getPeriodsService().getActivePeriods(order);
        return showPeriods(supermarket, activePeriods);
    }

    private TimePeriod showBestPeriods(Supermarket supermarket, Order order) {
        List<TimePeriod> bestPeriods = supermarket.getPeriodsService().getBestActivePeriods(order);
        return showPeriods(supermarket, bestPeriods);
    }

    private TimePeriod showPeriods(Supermarket supermarket, List<TimePeriod> timePeriods) {
        if (timePeriods.size() == 0) {
            System.out.println("No period is active now!");
            return null;
        }
        for (int i = 0; i < timePeriods.size(); i++) {
            TimePeriod timePeriod = timePeriods.get(i);
            int price = supermarket.getPeriodsService().getPriceOfPeriod(timePeriod);
            System.out.println("No." + (i + 1) + " " + timePeriod + " ,period price : " + price);
        }
        System.out.println("Choose period : ");
        int choice = Integer.parseInt(ScannerWrapper.nextLine()) - 1;
        if (choice >= 0 && choice < timePeriods.size()) {
            return timePeriods.get(choice);
        }
        System.out.println("Wrong choice");
        return null;
    }

    private void sellFoods(Map<Food, Integer> foods, Supermarket supermarket) {
        for (Map.Entry<Food, Integer> food : foods.entrySet()) {
            supermarket.getFoodMenu().sellFood(food.getKey(), food.getValue());
        }
    }
}
