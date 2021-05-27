package ir.ac.kntu.models;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class PeriodicalOrdersService extends OrdersService<PeriodicalOrder> {
    public PeriodicalOrdersService(Set<PeriodicalOrder> orders) {
        super(orders);
    }

    public List<PeriodicalOrder> getListOfOrdersByTimePeriod(TimePeriod timePeriod) {
        return getOrders().stream().filter(periodicalOrder -> periodicalOrder.getTimePeriod().equals(
                timePeriod)).collect(Collectors.toList());
    }
}
