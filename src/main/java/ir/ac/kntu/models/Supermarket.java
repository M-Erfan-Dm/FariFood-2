package ir.ac.kntu.models;

import ir.ac.kntu.db.CouriersDB;
import ir.ac.kntu.service.PeriodsService;
import ir.ac.kntu.service.PeriodsServiceImpl;
import ir.ac.kntu.service.PremiumCustomersService;
import ir.ac.kntu.service.SupermarketPeriodsService;

import java.util.List;

public class Supermarket extends Shop<PeriodicalOrder,PeriodicalOrdersService> {


    private final SupermarketPeriodsService periodsService;

    private PremiumCustomersService premiumCustomersService;

    public Supermarket(int id, String name, String address, Schedule schedule,
                       CouriersDB hiredCouriers, PeriodicalOrdersService ordersService,
                       int deliveryPrice, int periodBasePrice, PremiumCustomersService premiumCustomersService) {
        super(id, name, address, schedule, hiredCouriers, ordersService, deliveryPrice);
        periodsService = new SupermarketPeriodsService(this, periodBasePrice);
        this.premiumCustomersService = premiumCustomersService;
    }

    public Supermarket(String name, String address, Schedule schedule, int deliveryPrice, int periodBasePrice) {
        super(name, address, schedule, deliveryPrice);
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
