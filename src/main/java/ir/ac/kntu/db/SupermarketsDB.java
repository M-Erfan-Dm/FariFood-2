package ir.ac.kntu.db;

import ir.ac.kntu.models.PeriodicalOrder;
import ir.ac.kntu.models.Supermarket;

import java.util.Set;

public class SupermarketsDB extends ShopsDB<Supermarket, PeriodicalOrder>{
    public SupermarketsDB(Set<Supermarket> supermarkets) {
        super(supermarkets);
    }
}
