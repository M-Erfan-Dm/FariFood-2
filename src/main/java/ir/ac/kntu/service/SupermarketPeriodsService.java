package ir.ac.kntu.service;

import ir.ac.kntu.db.CouriersDB;
import ir.ac.kntu.models.*;

import java.util.List;
import java.util.stream.Collectors;

public class SupermarketPeriodsService extends PeriodsService{

    private static final int PERIOD_LENGTH_IN_MINUTE = 60;

    private static final double HALF_USED_PERIOD_PRICE_COEFFICIENT = 1.5;

    public SupermarketPeriodsService(Supermarket supermarket, int periodBasePrice) {
        super(supermarket, periodBasePrice);
    }

    @Override
    public int getPeriodLengthInMinute() {
        return PERIOD_LENGTH_IN_MINUTE;
    }

    @Override
    public List<TimePeriod> getActivePeriods() {
        return getTotalPeriods().stream()
                .filter(timePeriod -> getShop().getOrdersService().getActiveOrders(
                        timePeriod).size() < getPeriodCapacity(timePeriod))
                .collect(Collectors.toList());
    }

    @Override
    public int getPriceOfPeriod(TimePeriod timePeriod) {
        if (isPeriodCapacityUsedByHalf(timePeriod)){
            return (int) (getPeriodBasePrice() * HALF_USED_PERIOD_PRICE_COEFFICIENT);
        }
        return getPeriodBasePrice();
    }
}
