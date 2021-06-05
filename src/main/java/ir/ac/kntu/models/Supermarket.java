package ir.ac.kntu.models;

import ir.ac.kntu.db.CouriersDB;
import ir.ac.kntu.service.PremiumCustomersService;
import ir.ac.kntu.service.SupermarketPeriodsService;

public class Supermarket extends Shop<PeriodicalOrder,PeriodicalOrdersService> {


    private final SupermarketPeriodsService periodsService;

    private PremiumCustomersService premiumCustomersService;

    public Supermarket(int id, Owner owner, String name, String address, Schedule schedule,
                       CouriersDB hiredCouriers, PeriodicalOrdersService ordersService,
                       int deliveryPrice, int periodBasePrice, PremiumCustomersService premiumCustomersService,ShopPriceType priceType) {
        super(id, owner,name, address, schedule, hiredCouriers, ordersService, deliveryPrice, priceType);
        periodsService = new SupermarketPeriodsService(this, periodBasePrice);
        this.premiumCustomersService = premiumCustomersService;
    }

    public Supermarket(String name, String address, Schedule schedule, int deliveryPrice, int periodBasePrice,ShopPriceType priceType) {
        super(name, address, schedule, deliveryPrice, priceType);
        periodsService = new SupermarketPeriodsService(this, periodBasePrice);
    }

    public SupermarketPeriodsService getPeriodsService() {
        return periodsService;
    }

    @Override
    public int getAdditionalPrices(PeriodicalOrder order) {
        if (premiumCustomersService.containsCustomer(order.getCustomer())){
            return periodsService.getPriceOfPeriod(order.getTimePeriod());
        }
        return getDeliveryPrice() + periodsService.getPriceOfPeriod(order.getTimePeriod());
    }

}
