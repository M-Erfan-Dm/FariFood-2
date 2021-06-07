package ir.ac.kntu.service;

import ir.ac.kntu.models.Order;
import ir.ac.kntu.models.TimePeriod;

import java.util.List;

public interface PeriodsServiceImpl {
    List<TimePeriod> getActivePeriods(Order order);
    int getPriceOfPeriod(TimePeriod timePeriod);
    int getPeriodCapacity(TimePeriod timePeriod);
    List<TimePeriod> getBestActivePeriods(Order order);
}
