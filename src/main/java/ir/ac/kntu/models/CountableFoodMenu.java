package ir.ac.kntu.models;

import java.util.*;

public class CountableFoodMenu {

    private Map<Food, Integer> foods;

    public CountableFoodMenu(Map<Food, Integer> foods) {
        this.foods = foods;
    }

    public Map<Food, Integer> getFoods() {
        return new HashMap<>(foods);
    }

    public void setFoods(Map<Food, Integer> foods) {
        this.foods = foods;
    }

    public boolean buyFood(Food food, int amountToBuy) {
        if (amountToBuy < 1) {
            return false;
        }
        if (foods.containsKey(food)) {
            foods.put(food, foods.get(food) + amountToBuy);
        } else {
            foods.put(food, amountToBuy);
        }
        return true;
    }

    public boolean sellFood(Food food, int amountToSell) {
        if (amountToSell < 1 || !foods.containsKey(food)) {
            return false;
        }
        int currentAmount = foods.get(food) - amountToSell;
        if (currentAmount < 0) {
            return false;
        }
        foods.put(food, currentAmount);
        return true;
    }

    public boolean updateFood(Food food) {
        if (foods.containsKey(food)) {
            int amount = foods.get(food);
            foods.put(food, amount);
            return true;
        }
        return false;
    }

    public Food getFoodByName(String name) {
        Map.Entry<Food, Integer> entry = foods.entrySet().stream().filter(foodIntegerEntry -> foodIntegerEntry.getKey().getName().equals(name))
                .findFirst().orElse(null);
        if (entry == null) {
            return null;
        }
        return entry.getKey();
    }

    public Integer getAmountOfFood(Food food){
        return foods.get(food);
    }

    public boolean containsFood(Food food) {
        return foods.containsKey(food) && foods.get(food) > 0;
    }

    public void printAllFoods() {
        List<Map.Entry<Food, Integer>> foodsList = new ArrayList<>(foods.entrySet());
        for (int i = 0; i < foodsList.size(); i++) {
            Food food = foodsList.get(i).getKey();
            int amount = foodsList.get(i).getValue();
            System.out.println("No." + (i + 1) + " " + food + " ," + amount + " in stock");
        }
        System.out.println(foodsList.size() + " foods found");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CountableFoodMenu that = (CountableFoodMenu) o;
        return foods.equals(that.foods);
    }

    @Override
    public int hashCode() {
        return Objects.hash(foods);
    }

    @Override
    public String toString() {
        return "{" +
                "foods=" + foods +
                '}';
    }
}
