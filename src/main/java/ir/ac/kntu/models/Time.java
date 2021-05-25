package ir.ac.kntu.models;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Objects;

public class Time {
    private int hour;

    private int minute;

    public Time(int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public boolean isAfter(Time time) {
        Calendar now = Calendar.getInstance();
        int year = now.get(Calendar.YEAR);
        int month = now.get(Calendar.MONTH);
        int day = now.get(Calendar.DAY_OF_MONTH);

        Calendar firstCalendar = Calendar.getInstance();
        firstCalendar.set(year, month, day, hour, minute);

        Calendar secondCalendar = Calendar.getInstance();
        secondCalendar.set(year, month, day, time.getHour(), time.getMinute());
        return firstCalendar.after(secondCalendar);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Time time = (Time) o;
        return hour == time.hour && minute == time.minute;
    }

    @Override
    public int hashCode() {
        return Objects.hash(hour, minute);
    }

    @Override
    public String toString() {
        DecimalFormat decimalFormat = new DecimalFormat("00");
        return "{" + decimalFormat.format(hour) + ":" + decimalFormat.format(minute) + "}";
    }
}
