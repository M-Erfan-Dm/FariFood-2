package ir.ac.kntu.models;

import java.util.Objects;

public class Food {
    private String name;

    private int price;

    public Food(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public Food(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Food food = (Food) o;
        return name.equals(food.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "{name='" + name + '\'' +
                ", price=" + price + "}";
    }
}
