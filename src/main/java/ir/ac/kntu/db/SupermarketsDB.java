package ir.ac.kntu.db;

import ir.ac.kntu.models.Food;
import ir.ac.kntu.models.PeriodicalOrder;
import ir.ac.kntu.models.Supermarket;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class SupermarketsDB extends ShopsDB<Supermarket>{
    public SupermarketsDB(Set<Supermarket> supermarkets) {
        super(supermarkets);
    }

    public List<Supermarket> getSupermarketsByFood(String foodName){
        Food food = new Food(foodName);
        return getShops().stream().filter(
                supermarket -> supermarket.getFoodMenu().containsFood(food)).collect(Collectors.toList());
    }

}
