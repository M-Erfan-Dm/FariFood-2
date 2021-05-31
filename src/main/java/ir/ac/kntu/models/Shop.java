package ir.ac.kntu.models;

import ir.ac.kntu.db.CouriersDB;
import ir.ac.kntu.db.ShopsDB;

import java.time.LocalTime;
import java.util.*;

public abstract class Shop<O extends Order, T extends OrdersService<O>> {
    private int id;

    private String name;

    private String address;

    private Schedule schedule;

    private double rating = 5;

    private CouriersDB hiredCouriers;

    private T ordersService;

    private int deliveryPrice;

    public Shop(int id, String name, String address, Schedule schedule,
                CouriersDB hiredCouriers, T ordersService, int deliveryPrice) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.schedule = schedule;
        this.hiredCouriers = hiredCouriers;
        this.ordersService = ordersService;
        this.deliveryPrice = deliveryPrice;
    }

    public Shop(String name, String address, Schedule schedule, int deliveryPrice) {
        this.name = name;
        this.address = address;
        this.schedule = schedule;
        this.deliveryPrice = deliveryPrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public double getRating() {
        return rating;
    }

    public CouriersDB getHiredCouriers() {
        return hiredCouriers;
    }

    public void setHiredCouriers(CouriersDB hiredCouriers) {
        this.hiredCouriers = hiredCouriers;
    }

    public T getOrdersService() {
        return ordersService;
    }

    public void setOrdersService(T ordersService) {
        this.ordersService = ordersService;
    }

    public int getDeliveryPrice() {
        return deliveryPrice;
    }

    public void setDeliveryPrice(int deliveryPrice) {
        this.deliveryPrice = deliveryPrice;
    }

    public double updateRating() {
        List<Feedback> feedbacks = ordersService.getAllFeedbacks();
        double sum = feedbacks.stream().map(feedback -> feedback.getRating().getValue())
                .reduce(5, Integer::sum);
        double avg = sum / (feedbacks.size() + 1);
        rating = avg;
        return avg;
    }

    public boolean isActive() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        Day day = Day.values()[calendar.get(Calendar.DAY_OF_WEEK) - 1];
        return schedule.getDays().contains(day) && schedule.isTimeInInterval(LocalTime.of(hour, minute));
    }

    public boolean hireCourier(Courier courier, CourierJobInfo courierJobInfo) {
        boolean isCourierHired = courier.addJob(courierJobInfo);
        if (isCourierHired) {
            hiredCouriers.addCourier(courier);
            return true;
        }
        return false;
    }

    public boolean dismissCourier(String courierPhoneNumber) {
        Courier courier = hiredCouriers.getCourierByPhoneNumber(courierPhoneNumber);
        if (courier != null) {
            courier.quitJob(id);
            return hiredCouriers.removeCourier(courier);
        }
        return false;
    }

    public double getAlphaScore() {
        return 2 * rating + ordersService.getCount();
    }

    abstract public int getAdditionalPrices(O order);


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Shop<?,?> shop = (Shop<?,?>) o;
        return id == shop.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        String isActive;
        if (isActive()) {
            isActive = "Yes";
        } else {
            isActive = "No";
        }
        return "{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", schedule=" + schedule +
                ", rating=" + rating +
                ", alpha score=" + getAlphaScore() +
                ", is active=" + isActive +
                '}';
    }
}
