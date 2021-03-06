package ir.ac.kntu.models;

import ir.ac.kntu.db.CouriersDB;
import ir.ac.kntu.service.FruitShopPeriodsService;
import ir.ac.kntu.service.PeriodicalOrdersService;

public class FruitShop extends Shop<PeriodicalOrdersService> {

    private final FruitShopPeriodsService periodsService;

    private CountableFoodMenu foodMenu;

    public FruitShop(Owner owner, String name, String address, Schedule schedule,
                     CouriersDB hiredCouriers, PeriodicalOrdersService ordersService,
                     int deliveryPrice, int periodBasePrice, int fruitKGLimit, ShopPriceType priceType, CountableFoodMenu foodMenu) {
        super(owner, name, address, schedule, hiredCouriers, ordersService, deliveryPrice, priceType);
        this.foodMenu = foodMenu;
        periodsService = new FruitShopPeriodsService(this, periodBasePrice, fruitKGLimit);
    }

    public FruitShop(Owner owner, String name, String address, Schedule schedule, int deliveryPrice,
                     int periodBasePrice, int fruitKGLimit, ShopPriceType priceType) {
        super(owner, name, address, schedule, deliveryPrice, priceType);
        periodsService = new FruitShopPeriodsService(this, periodBasePrice, fruitKGLimit);
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
    public int getAdditionalPrices(Order order) {
        PeriodicalOrder periodicalOrder = (PeriodicalOrder) order;
        return getDeliveryPrice() + periodsService.getPriceOfPeriod(periodicalOrder.getTimePeriod());
    }

    @Override
    public String toString() {
        String parentString = super.toString().substring(0, super.toString().lastIndexOf("}"));
        return parentString +
                ", period base price=" + periodsService.getPeriodBasePrice() +
                ", fruit kg limit=" + periodsService.getFruitKGLimit() +
                "}";
    }
}
