package ir.ac.kntu.models;

import java.util.*;

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
