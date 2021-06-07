package ir.ac.kntu.menu.order;

import ir.ac.kntu.db.FruitShopsDB;
import ir.ac.kntu.models.*;
import ir.ac.kntu.utils.ScannerWrapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FruitShopAddOrderMenu extends AddOrderMenu<FruitShop, FruitShopsDB> {

    public FruitShopAddOrderMenu(FruitShopsDB shopsDB, Settings settings) {
        super(shopsDB, settings);
    }

    public FruitShopAddOrderMenu(Customer customer, FruitShopsDB shopsDB, Settings settings) {
        super(customer, shopsDB, settings);
    }

    @Override
    public void show() {
        System.out.println("Fruit shop :");
        FruitShop fruitShop = chooseShop();
        if (fruitShop == null) {
            return;
        }
        System.out.println("Food :");
        boolean continueSubmittingFoods = showFoods(fruitShop);
        if (!continueSubmittingFoods) {
            return;
        }
        Map<Food, Integer> foods = chooseFoods(fruitShop);
        if (foods.size() == 0) {
            System.out.println("No food was added");
            return;
        }
        TimePeriod timePeriod = choosePeriod(fruitShop,
                new Order(foods, null, getCustomer(), null, OrderState.PROCESSING));
        if (timePeriod == null) {
            return;
        }
        PeriodicalOrder order = new PeriodicalOrder(foods, null, getCustomer(),
                null, OrderState.PROCESSING, timePeriod);
        fruitShop.getOrdersService().addOrder(order);
        System.out.println("Your order is in process");
        int totalPrice = fruitShop.getOrdersService().getTotalPriceForOrder(order, fruitShop);
        System.out.println("Total price : " + totalPrice);
    }

    @Override
    public Map<Food, Integer> chooseFoods(FruitShop shop) {
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
            boolean isSold = shop.getFoodMenu().sellFood(food, count);
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
    public void showAllFoods(FruitShop shop) {
        Map<Food, Integer> foods = shop.getFoodMenu().getFoods();
        for (Map.Entry<Food, Integer> food : foods.entrySet()) {
            System.out.println(food.getKey() + " , " + food.getValue() + " in stock");
        }
    }

    @Override
    public void showThreeBestFoods(FruitShop shop) {
        List<Food> foods = shop.getOrdersService().getBestFoods(3);
        for (Food food : foods) {
            int amount = shop.getFoodMenu().getAmountOfFood(food);
            System.out.println(food + " , " + amount + " in stock");
        }
    }

    @Override
    public List<FruitShop> getFiveBestShopsByFood(Food food) {
        return getShopsDB().getBestFruitShopsByFood(food, 5);
    }

    private TimePeriod choosePeriod(FruitShop fruitShop, Order order) {
        printEnumOptions(OrdersPeriodOption.class);
        System.out.println("Choose your option :");
        OrdersPeriodOption option = getOption(OrdersPeriodOption.class);
        if (option != null) {
            switch (option) {
                case SHOW_ACTIVE_PERIODS:
                    return showActivePeriods(fruitShop, order);
                case SHOW_BEST_ACTIVE_PERIODS:
                    return showBestPeriods(fruitShop, order);
                default:
                    break;
            }
        }
        return null;
    }

    private TimePeriod showActivePeriods(FruitShop fruitShop, Order order) {
        List<TimePeriod> activePeriods = fruitShop.getPeriodsService().getActivePeriods(order);
        return showPeriods(fruitShop, activePeriods);
    }

    private TimePeriod showBestPeriods(FruitShop fruitShop, Order order) {
        List<TimePeriod> bestPeriods = fruitShop.getPeriodsService().getBestActivePeriods(order);
        return showPeriods(fruitShop, bestPeriods);
    }

    private TimePeriod showPeriods(FruitShop fruitShop, List<TimePeriod> timePeriods) {
        if (timePeriods.size() == 0) {
            System.out.println("No period is active now!");
            return null;
        }
        for (int i = 0; i < timePeriods.size(); i++) {
            TimePeriod timePeriod = timePeriods.get(i);
            int price = fruitShop.getPeriodsService().getPriceOfPeriod(timePeriod);
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
}
