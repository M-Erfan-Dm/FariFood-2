package ir.ac.kntu.models;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class PeriodicalOrdersService extends OrdersService<PeriodicalOrder> {
    public PeriodicalOrdersService(Set<PeriodicalOrder> orders) {
        super(orders);
    }

    public List<PeriodicalOrder> getOrdersByTimePeriod(TimePeriod timePeriod) {
        return getOrders().stream().filter(periodicalOrder -> periodicalOrder.getTimePeriod().equals(
                timePeriod)).collect(Collectors.toList());
    }

    public List<PeriodicalOrder> getActiveOrders(TimePeriod timePeriod) {
        return getOrdersByTimePeriod(timePeriod).stream().filter(
                periodicalOrder -> periodicalOrder.getOrderState() != OrderState.DELIVERED)
                .collect(Collectors.toList());
    }
}
