package ir.ac.kntu.models;

import ir.ac.kntu.db.CouriersDB;
import ir.ac.kntu.service.FruitShopPeriodsService;

public class FruitShop extends Shop<PeriodicalOrder,PeriodicalOrdersService> {

    private final FruitShopPeriodsService periodsService;

    private CountableFoodMenu foodMenu;

    public FruitShop(int id, Owner owner, String name, String address, Schedule schedule,
                     CouriersDB hiredCouriers, PeriodicalOrdersService ordersService,
                     int deliveryPrice, int periodBasePrice, int fruitKGLimit, ShopPriceType priceType, CountableFoodMenu foodMenu) {
        super(id, owner, name, address, schedule, hiredCouriers, ordersService, deliveryPrice, priceType);
        this.foodMenu = foodMenu;
        periodsService = new FruitShopPeriodsService(this,periodBasePrice,fruitKGLimit);
    }

    public FruitShop(String name, String address, Schedule schedule, int deliveryPrice
            , int periodBasePrice, int fruitKGLimit,ShopPriceType priceType) {
        super(name, address, schedule, deliveryPrice, priceType);
        periodsService = new FruitShopPeriodsService(this,periodBasePrice,fruitKGLimit);
    }

    public FruitShopPeriodsService getPeriodsService() {
        return periodsService;
    }

    public CountableFoodMenu getFoodMenu() {
        return foodMenu;
    }

    public void setFoodMenu(CountableFoodMenu foodMenu) {
        this.foodMenu = foodMenu;
    }

    @Override
    public int getAdditionalPrices(PeriodicalOrder order) {
        return getDeliveryPrice() + periodsService.getPriceOfPeriod(order.getTimePeriod());
    }

}
