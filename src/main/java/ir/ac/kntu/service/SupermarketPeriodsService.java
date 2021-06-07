package ir.ac.kntu.service;

import ir.ac.kntu.models.Supermarket;

public class SupermarketPeriodsService extends PeriodsService {

    private static final int PERIOD_LENGTH_IN_MINUTE = 60;


    public SupermarketPeriodsService(Supermarket supermarket, int periodBasePrice) {
        super(supermarket, periodBasePrice);
    }

    @Override
    public int getPeriodLengthInMinute() {
        return PERIOD_LENGTH_IN_MINUTE;
    }

}
