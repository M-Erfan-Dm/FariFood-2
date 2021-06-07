package ir.ac.kntu.db;

import ir.ac.kntu.models.Food;
import ir.ac.kntu.models.PeriodicalOrder;
import ir.ac.kntu.models.Supermarket;
import ir.ac.kntu.utils.ListSorting;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class SupermarketsDB extends ShopsDB<Supermarket>{
    public SupermarketsDB(Set<Supermarket> supermarkets) {
        super(supermarkets);
    }

    public List<Supermarket> getSupermarketsByFood(Food food){
        return getShops().stream().filter(
                supermarket -> supermarket.getFoodMenu().containsFood(food)).collect(Collectors.toList());
    }

    public List<Supermarket> getBestSupermarketsByFood(Food food, int count){
        List<Supermarket> orderedList = getSupermarketsByFood(food);
        return ListSorting.sortList(orderedList,count,false,
                supermarket -> supermarket.getOrdersService().getRatingAverageOfFood(food.getName()));
    }
}
