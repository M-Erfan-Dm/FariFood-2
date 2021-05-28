package ir.ac.kntu.models;

import java.util.*;
import java.util.function.Predicate;

public class FoodMenu {
    private Set<Food> foods;

    public FoodMenu(Set<Food> foods) {
        this.foods = foods;
    }

    public Set<Food> getFoods() {
        return new HashSet<>(foods);
    }

    public void setFoods(Set<Food> foods) {
        this.foods = foods;
    }

    public void addFood(Food food) {
        removeFood(food);
        foods.add(food);
    }

    public boolean removeFood(Food food) {
        return foods.remove(food);
    }

    public Food getFoodByName(String name) {
        return foods.stream().filter(food -> food.getName().equals(name)).
                findFirst().orElse(null);
    }

    public boolean containsFood(Food food) {
        return foods.contains(food);
    }

    public void printAllFoods() {
        List<Food> foodsList = new ArrayList<>(foods);
        for (int i = 0; i < foodsList.size(); i++) {
            Food food = foodsList.get(i);
            System.out.println("No." + (i + 1) + " " + food);
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
        FoodMenu foodMenu = (FoodMenu) o;
        return foods.equals(foodMenu.foods);
    }

    @Override
    public int hashCode() {
        return Objects.hash(foods);
    }

    @Override
    public String toString() {
        return "{foods=" + foods + "}";
    }
}
