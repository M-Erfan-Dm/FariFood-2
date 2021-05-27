package ir.ac.kntu.models;

import java.util.Map;

public class PeriodicalOrder extends Order {
    private TimePeriod timePeriod;

    public PeriodicalOrder(Map<Food, Integer> foods, Feedback feedback, Customer customer,
                           Courier courier, OrderState orderState, TimePeriod timePeriod) {
        super(foods, feedback, customer, courier, orderState);
        this.timePeriod = timePeriod;
    }

    public PeriodicalOrder(int id, Map<Food, Integer> foods, Feedback feedback, Customer customer,
                           Courier courier, OrderState orderState, TimePeriod timePeriod) {
        super(id, foods, feedback, customer, courier, orderState);
        this.timePeriod = timePeriod;
    }

    public TimePeriod getTimePeriod() {
        return timePeriod;
    }

    public void setTimePeriod(TimePeriod timePeriod) {
        this.timePeriod = timePeriod;
    }

    @Override
    public String toString() {
        String parentString = super.toString().substring(0, super.toString().lastIndexOf("}"));
        return parentString +
                ", timePeriod=" + timePeriod +
                '}';
    }
}
