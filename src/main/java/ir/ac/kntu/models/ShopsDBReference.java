package ir.ac.kntu.models;

import ir.ac.kntu.db.FruitShopsDB;
import ir.ac.kntu.db.RestaurantsDB;
import ir.ac.kntu.db.SupermarketsDB;

import java.util.*;

public class ShopsDBReference {
    private final RestaurantsDB restaurantsDB;

    private final SupermarketsDB supermarketsDB;

    private final FruitShopsDB fruitShopsDB;

    public ShopsDBReference(RestaurantsDB restaurantsDB, SupermarketsDB supermarketsDB, FruitShopsDB fruitShopsDB) {
        this.restaurantsDB = restaurantsDB;
        this.supermarketsDB = supermarketsDB;
        this.fruitShopsDB = fruitShopsDB;
    }

    public RestaurantsDB getRestaurantsDB() {
        return restaurantsDB;
    }

    public SupermarketsDB getSupermarketsDB() {
        return supermarketsDB;
    }

    public FruitShopsDB getFruitShopsDB() {
        return fruitShopsDB;
    }

    public Set<Order> getAllOrders(){
        Set<Order> orders = new HashSet<>();
        orders.addAll(restaurantsDB.getAllOrders());
        orders.addAll(supermarketsDB.getAllOrders());
        orders.addAll(fruitShopsDB.getAllOrders());
        return orders;
    }
}
