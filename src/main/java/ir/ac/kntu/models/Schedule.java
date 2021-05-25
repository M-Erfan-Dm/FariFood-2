package ir.ac.kntu.models;

import java.time.LocalTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Schedule {
    private LocalTime startTime;

    private LocalTime endTime;

    private Set<Day> days;

    public Schedule(LocalTime startTime, LocalTime endTime, Set<Day> days) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.days = new HashSet<>(days);
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public Set<Day> getDays() {
        return new HashSet<>(days);
    }

    public void setDays(Set<Day> days) {
        this.days = new HashSet<>(days);
    }

    public boolean isTimeInInterval(LocalTime time) {
        return time.isAfter(startTime) && endTime.isAfter(time);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Schedule schedule = (Schedule) o;
        return Objects.equals(startTime, schedule.startTime) && Objects.equals(endTime, schedule.endTime) && Objects.equals(days, schedule.days);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startTime, endTime, days);
    }

    @Override
    public String toString() {
        return "{startTime=" + startTime +
                ", endTime=" + endTime +
                ", days=" + days + "}";
    }
}
