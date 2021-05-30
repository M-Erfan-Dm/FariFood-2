package ir.ac.kntu.service;

import ir.ac.kntu.db.CouriersDB;
import ir.ac.kntu.models.*;
import ir.ac.kntu.utils.TimeUtils;

import java.time.Period;
import java.util.List;
import java.util.stream.Collectors;

public abstract class PeriodsService implements PeriodsServiceImpl{

    private static final int PERIOD_ORDER_CAPACITY_MIN = 2;

    private Shop<PeriodicalOrder,PeriodicalOrdersService> shop;

    private int periodBasePrice;

    public PeriodsService(Shop<PeriodicalOrder,PeriodicalOrdersService> shop, int periodBasePrice) {
        this.shop = shop;
        this.periodBasePrice = periodBasePrice;
    }

    public Shop<PeriodicalOrder,PeriodicalOrdersService> getShop() {
        return shop;
    }

    public int getPeriodBasePrice() {
        return periodBasePrice;
    }

    public void setPeriodBasePrice(int periodBasePrice) {
        this.periodBasePrice = periodBasePrice;
    }

    public List<TimePeriod> getTotalPeriods() {
        return TimeUtils.splitTimePeriod(shop.getSchedule().getStartTime(),
                shop.getSchedule().getEndTime(), getPeriodLengthInMinute());
    }

    @Override
    public int getPeriodCapacity(TimePeriod timePeriod) {
        int capacity = shop.getHiredCouriers().size() / getTotalPeriods().size();
        return Math.max(capacity, PERIOD_ORDER_CAPACITY_MIN);
    }

    public boolean isPeriodCapacityUsedByHalf(TimePeriod timePeriod) {
        int capacity = getPeriodCapacity(timePeriod);
        int activeOrders = shop.getOrdersService().getActiveOrders(timePeriod).size();
        return activeOrders>=capacity/2;
    }

    public abstract int getPeriodLengthInMinute();

}
