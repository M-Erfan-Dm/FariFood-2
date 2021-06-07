package ir.ac.kntu.models;

import ir.ac.kntu.db.CouriersDB;
import ir.ac.kntu.service.PremiumCustomersService;
import ir.ac.kntu.service.SupermarketPeriodsService;

public class Supermarket extends Shop<PeriodicalOrdersService> {


    private final SupermarketPeriodsService periodsService;

    private PremiumCustomersService premiumCustomersService;

    private CountableFoodMenu foodMenu;

    public Supermarket(int id, Owner owner, String name, String address, Schedule schedule,
                       CouriersDB hiredCouriers, PeriodicalOrdersService ordersService,
                       int deliveryPrice, int periodBasePrice, PremiumCustomersService premiumCustomersService, ShopPriceType priceType, CountableFoodMenu foodMenu) {
        super(id, owner, name, address, schedule, hiredCouriers, ordersService, deliveryPrice, priceType);
        periodsService = new SupermarketPeriodsService(this, periodBasePrice);
        this.premiumCustomersService = premiumCustomersService;
        this.foodMenu = foodMenu;
    }

    public Supermarket(Owner owner, String name, String address, Schedule schedule, int deliveryPrice, int periodBasePrice, ShopPriceType priceType) {
        super(owner, name, address, schedule, deliveryPrice, priceType);
        periodsService = new SupermarketPeriodsService(this, periodBasePrice);
    }

    public SupermarketPeriodsService getPeriodsService() {
        return periodsService;
    }

    public CountableFoodMenu getFoodMenu() {
        return foodMenu;
    }

    public void setFoodMenu(CountableFoodMenu foodMenu) {
        this.foodMenu = foodMenu;
    }

    public PremiumCustomersService getPremiumCustomersService() {
        return premiumCustomersService;
    }

    public void setPremiumCustomersService(PremiumCustomersService premiumCustomersService) {
        this.premiumCustomersService = premiumCustomersService;
    }

    @Override
    public int getAdditionalPrices(Order order) {
        PeriodicalOrder periodicalOrder = (PeriodicalOrder) order;
        if (premiumCustomersService.containsCustomer(periodicalOrder.getCustomer())) {
            return periodsService.getPriceOfPeriod(periodicalOrder.getTimePeriod());
        }
        return getDeliveryPrice() + periodsService.getPriceOfPeriod(periodicalOrder.getTimePeriod());
    }


}
