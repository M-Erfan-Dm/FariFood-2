package ir.ac.kntu.models;

import java.util.Objects;

public class CourierJobInfo {
    private Shop<? extends Order,? extends OrdersService<? extends Order>> shop;

    private Schedule schedule;

    private Salary salary;

    public CourierJobInfo(Shop<? extends Order, ? extends OrdersService<? extends Order>> shop,
                          Schedule schedule, Salary salary) {
        this.shop = shop;
        this.schedule = schedule;
        this.salary = salary;
    }

    public Shop<? extends Order, ? extends OrdersService<? extends Order>> getShop() {
        return shop;
    }

    public void setShop(Shop<? extends Order, ? extends OrdersService<? extends Order>> shop) {
        this.shop = shop;
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
        if (this == o){
            return true;
        }
        if (o == null || getClass() != o.getClass()){
            return false;
        }
        CourierJobInfo that = (CourierJobInfo) o;
        return shop.equals(that.shop) && schedule.equals(that.schedule) && salary.equals(that.salary);
    }

    @Override
    public int hashCode() {
        return Objects.hash(shop, schedule, salary);
    }

    @Override
    public String toString() {
        return "{shop=" + shop +
                ", schedule=" + schedule +
                ", salary=" + salary + "}";
    }
}
