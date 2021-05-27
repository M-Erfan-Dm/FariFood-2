package ir.ac.kntu.models;

import java.time.LocalTime;
import java.util.Objects;

public class TimePeriod {
    private LocalTime startTime;

    private LocalTime endTime;

    public TimePeriod(LocalTime startTime, LocalTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
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

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }
        if (o == null || getClass() != o.getClass()){
            return false;
        }
        TimePeriod timePeriod = (TimePeriod) o;
        return startTime.equals(timePeriod.startTime) && endTime.equals(timePeriod.endTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startTime, endTime);
    }

    @Override
    public String toString() {
        return "{" +
                "startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}
