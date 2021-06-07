package ir.ac.kntu.db;

import ir.ac.kntu.models.FruitShop;
import ir.ac.kntu.models.PeriodicalOrder;

import java.util.Set;

public class FruitShopsDB extends ShopsDB<FruitShop>{
    public FruitShopsDB(Set<FruitShop> shops) {
        super(shops);
    }
}
