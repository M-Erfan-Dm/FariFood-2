package ir.ac.kntu.utils;

import ir.ac.kntu.models.TimePeriod;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class TimeUtils {

    private TimeUtils() {
    }

    public static List<TimePeriod> splitTimePeriod(LocalTime startTime, LocalTime endTime, int stepInMinute) {
        List<TimePeriod> slicedPeriods = new ArrayList<>();
        int start = convertTimeToMinute(startTime);
        int end = convertTimeToMinute(endTime);
        int iterator = start + stepInMinute;
        while (iterator <= end) {
            slicedPeriods.add(new TimePeriod(convertMinuteToTime(start), convertMinuteToTime(iterator)));
            start = iterator;
            iterator += stepInMinute;
        }
        if ((end - start) % stepInMinute != 0) {
            slicedPeriods.add(new TimePeriod(convertMinuteToTime(start), convertMinuteToTime(end)));
        }
        return slicedPeriods;
    }

    public static int convertTimeToMinute(LocalTime time) {
        return time.getHour() * 60 + time.getMinute();
    }

    public static LocalTime convertMinuteToTime(int minute) {
        int h = minute / 60;
        int m = minute % 60;
        return LocalTime.of(h, m);
    }
}
