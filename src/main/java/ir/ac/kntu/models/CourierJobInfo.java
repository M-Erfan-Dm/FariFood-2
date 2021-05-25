package ir.ac.kntu.models;

import java.util.Objects;

public class CourierJobInfo {
    private Restaurant restaurant;

    private Schedule schedule;

    private Salary salary;

    public CourierJobInfo(Restaurant restaurant, Schedule schedule, Salary salary) {
        this.restaurant = restaurant;
        this.schedule = schedule;
        this.salary = salary;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public Salary getSalary() {
        return salary;
    }

    public void setSalary(Salary salary) {
        this.salary = salary;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CourierJobInfo that = (CourierJobInfo) o;
        return restaurant.equals(that.restaurant) && schedule.equals(that.schedule) && salary.equals(that.salary);
    }

    @Override
    public int hashCode() {
        return Objects.hash(restaurant, schedule, salary);
    }

    @Override
    public String toString() {
        return "{restaurant=" + restaurant +
                ", schedule=" + schedule +
                ", salary=" + salary + "}";

    }
}
