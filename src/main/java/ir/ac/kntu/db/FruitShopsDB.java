package ir.ac.kntu.db;

import ir.ac.kntu.models.Food;
import ir.ac.kntu.models.FruitShop;
import ir.ac.kntu.utils.ListSorting;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class FruitShopsDB extends ShopsDB<FruitShop> {
    public FruitShopsDB(Set<FruitShop> shops) {
        super(shops);
    }

    public List<FruitShop> getFruitShopsByFood(Food food) {
        return getShops().stream().filter(
                fruitShop -> fruitShop.getFoodMenu().containsFood(food)).collect(Collectors.toList());
    }

    public List<FruitShop> getBestFruitShopsByFood(Food food, int count) {
        return ListSorting.sortList(getFruitShopsByFood(food), count, false,
                fruitShop -> fruitShop.getOrdersService().getRatingAverageOfFood(food.getName()));
    }
}
