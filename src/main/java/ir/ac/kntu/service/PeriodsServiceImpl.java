package ir.ac.kntu.service;

import ir.ac.kntu.models.TimePeriod;

import java.util.List;

public interface PeriodsServiceImpl {
    List<TimePeriod> getActivePeriods();
    int getPriceOfPeriod(TimePeriod timePeriod);
    int getPeriodCapacity(TimePeriod timePeriod);
}
