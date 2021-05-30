package ir.ac.kntu.models;

import ir.ac.kntu.db.CouriersDB;
import ir.ac.kntu.service.PeriodsService;
import ir.ac.kntu.service.PeriodsServiceImpl;
import ir.ac.kntu.service.PremiumCustomersService;
import ir.ac.kntu.service.SupermarketPeriodsService;

import java.util.List;

public class Supermarket extends Shop<PeriodicalOrder,PeriodicalOrdersService> implements PeriodsServiceImpl {


    private final PeriodsService periodsService;

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

    public int getPeriodBasePrice() {
        return periodsService.getPeriodBasePrice();
    }

    public void setPeriodBasePrice(int periodBasePrice) {
        periodsService.setPeriodBasePrice(periodBasePrice);
    }

    @Override
    public int getAdditionalPrices(PeriodicalOrder order) {
        if (premiumCustomersService.containsCustomer(order.getCustomer())){
            return getPriceOfPeriod(order.getTimePeriod());
        }
        return getDeliveryPrice() + getPriceOfPeriod(order.getTimePeriod());
    }

    @Override
    public List<TimePeriod> getActivePeriods() {
        return periodsService.getActivePeriods();
    }

    @Override
    public int getPriceOfPeriod(TimePeriod timePeriod) {
        return periodsService.getPriceOfPeriod(timePeriod);
    }

    @Override
    public int getPeriodCapacity(TimePeriod timePeriod) {
        return periodsService.getPeriodCapacity(timePeriod);
    }
}
