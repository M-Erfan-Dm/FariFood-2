package ir.ac.kntu.models;

import ir.ac.kntu.db.CouriersDB;
import ir.ac.kntu.service.FruitShopPeriodsService;
import ir.ac.kntu.service.PeriodsService;
import ir.ac.kntu.service.PeriodsServiceImpl;

import java.util.List;

public class FruitShop extends Shop<PeriodicalOrder,PeriodicalOrdersService> {

    private final FruitShopPeriodsService periodsService;

    public FruitShop(int id, Owner owner,String name, String address, Schedule schedule,
                     CouriersDB hiredCouriers, PeriodicalOrdersService ordersService,
                     int deliveryPrice, int periodBasePrice, int fruitKGLimit) {
        super(id, owner, name, address, schedule, hiredCouriers, ordersService, deliveryPrice);
        periodsService = new FruitShopPeriodsService(this,periodBasePrice,fruitKGLimit);
    }

    public FruitShop(String name, String address, Schedule schedule, int deliveryPrice
            , int periodBasePrice, int fruitKGLimit) {
        super(name, address, schedule, deliveryPrice);
        periodsService = new FruitShopPeriodsService(this,periodBasePrice,fruitKGLimit);
    }

    public FruitShopPeriodsService getPeriodsService() {
        return periodsService;
    }

    @Override
    public int getAdditionalPrices(PeriodicalOrder order) {
        return getDeliveryPrice() + periodsService.getPriceOfPeriod(order.getTimePeriod());
    }

}
