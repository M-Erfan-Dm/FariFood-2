package ir.ac.kntu.service;

import ir.ac.kntu.models.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FruitShopPeriodsService extends PeriodsService{

    private static final int PERIOD_LENGTH_IN_MINUTE = 120;

    private int fruitKGLimit;

    public FruitShopPeriodsService(Shop<PeriodicalOrder, PeriodicalOrdersService> shop, int periodBasePrice, int fruitKGLimit) {
        super(shop, periodBasePrice);
        this.fruitKGLimit = fruitKGLimit;
    }

    public int getFruitKGLimit() {
        return fruitKGLimit;
    }

    public void setFruitKGLimit(int fruitKGLimit) {
        this.fruitKGLimit = fruitKGLimit;
    }

    @Override
    public int getPeriodLengthInMinute() {
        return PERIOD_LENGTH_IN_MINUTE;
    }

    @Override
    public List<TimePeriod> getActivePeriods(Order order) {
        if (order.getCustomer()==null){
            return new ArrayList<>();
        }
        return super.getActivePeriods(order).stream().filter(
                timePeriod -> getTotalCountOfFruits(timePeriod,order.getCustomer()) +
                        order.getCountOfFoods() <=fruitKGLimit).collect(Collectors.toList());
    }


    private int getTotalCountOfFruits(TimePeriod timePeriod, Customer customer){
        return getShop().getOrdersService().getActiveOrders(timePeriod).stream().filter(
                periodicalOrder -> periodicalOrder.getCustomer().equals(customer))
                .map(Order::getCountOfFoods).reduce(0,Integer::sum);
    }
}
