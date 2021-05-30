package ir.ac.kntu.service;

import ir.ac.kntu.models.*;

import java.util.List;
import java.util.stream.Collectors;

public class SupermarketPeriodsService extends PeriodsService{

    private static final int PERIOD_LENGTH_IN_MINUTE = 60;


    public SupermarketPeriodsService(Supermarket supermarket, int periodBasePrice) {
        super(supermarket, periodBasePrice);
    }

    @Override
    public int getPeriodLengthInMinute() {
        return PERIOD_LENGTH_IN_MINUTE;
    }

}
