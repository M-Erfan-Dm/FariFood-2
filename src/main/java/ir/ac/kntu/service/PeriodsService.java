package ir.ac.kntu.service;

import ir.ac.kntu.models.Order;
import ir.ac.kntu.models.Shop;
import ir.ac.kntu.models.TimePeriod;
import ir.ac.kntu.utils.ListSorting;
import ir.ac.kntu.utils.TimeUtils;

import java.util.List;
import java.util.stream.Collectors;

public abstract class PeriodsService implements PeriodsServiceImpl {

    private static final int PERIOD_ORDER_CAPACITY_MIN = 2;

    private static final double HALF_USED_PERIOD_PRICE_COEFFICIENT = 1.5;

    private final Shop<PeriodicalOrdersService> shop;

    private int periodBasePrice;

    public PeriodsService(Shop<PeriodicalOrdersService> shop, int periodBasePrice) {
        this.shop = shop;
        this.periodBasePrice = periodBasePrice;
    }

    public Shop<PeriodicalOrdersService> getShop() {
        return shop;
    }

    public int getPeriodBasePrice() {
        return periodBasePrice;
    }

    public void setPeriodBasePrice(int periodBasePrice) {
        this.periodBasePrice = periodBasePrice;
    }

    private List<TimePeriod> getTotalPeriods() {
        return TimeUtils.splitTimePeriod(shop.getSchedule().getStartTime(),
                shop.getSchedule().getEndTime(), getPeriodLengthInMinute());
    }

    @Override
    public int getPeriodCapacity(TimePeriod timePeriod) {
        int capacity = shop.getHiredCouriers().size() / getTotalPeriods().size();
        return Math.max(capacity, PERIOD_ORDER_CAPACITY_MIN);
    }

    private boolean isPeriodCapacityUsedByHalf(TimePeriod timePeriod) {
        int capacity = getPeriodCapacity(timePeriod);
        int activeOrders = shop.getOrdersService().getActiveOrders(timePeriod).size();
        return activeOrders >= capacity / 2;
    }

    @Override
    public List<TimePeriod> getActivePeriods(Order order) {
        return getTotalPeriods().stream()
                .filter(timePeriod -> getShop().getOrdersService().getActiveOrders(
                        timePeriod).size() < getPeriodCapacity(timePeriod))
                .collect(Collectors.toList());
    }

    @Override
    public int getPriceOfPeriod(TimePeriod timePeriod) {
        if (isPeriodCapacityUsedByHalf(timePeriod)) {
            return (int) (getPeriodBasePrice() * HALF_USED_PERIOD_PRICE_COEFFICIENT);
        }
        return getPeriodBasePrice();
    }

    @Override
    public List<TimePeriod> getBestActivePeriods(Order order) {
        List<TimePeriod> timePeriods = getActivePeriods(order);
        return ListSorting.sortList(timePeriods, timePeriods.size(), false,
                timePeriod -> Double.valueOf(getShop().getOrdersService().
                        getOrdersByTimePeriod(timePeriod).size()));
    }

    public abstract int getPeriodLengthInMinute();
}
