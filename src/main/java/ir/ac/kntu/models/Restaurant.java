package ir.ac.kntu.models;

import ir.ac.kntu.db.CouriersDB;

import java.util.Calendar;
import java.util.List;
import java.util.Objects;

public class Restaurant {

    private int id;

    private String name;

    private String address;

    private FoodMenu foodMenu;

    private Schedule schedule;

    private RestaurantPriceType priceType;

    private double rating = 5;

    private CouriersDB hiredCouriers;

    private OrdersService ordersService;

    public Restaurant(int id, String name, String address, FoodMenu foodMenu,
                      Schedule schedule, RestaurantPriceType priceType,
                      CouriersDB hiredCouriers, OrdersService ordersService) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.foodMenu = foodMenu;
        this.schedule = schedule;
        this.priceType = priceType;
        this.hiredCouriers = hiredCouriers;
        this.ordersService = ordersService;
    }

    public Restaurant(String name, String address, FoodMenu foodMenu,
                      Schedule schedule, RestaurantPriceType priceType,
                      CouriersDB hiredCouriers, OrdersService ordersService) {
        this.name = name;
        this.address = address;
        this.foodMenu = foodMenu;
        this.schedule = schedule;
        this.priceType = priceType;
        this.hiredCouriers = hiredCouriers;
        this.ordersService = ordersService;
    }

    public Restaurant(String name, String address, Schedule schedule, RestaurantPriceType priceType) {
        this.name = name;
        this.address = address;
        this.schedule = schedule;
        this.priceType = priceType;
    }

    public int getId() {
        return id;
    }

    public double getRating() {
        return rating;
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

    public FoodMenu getFoodMenu() {
        return foodMenu;
    }

    public void setFoodMenu(FoodMenu foodMenu) {
        this.foodMenu = foodMenu;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public RestaurantPriceType getPriceType() {
        return priceType;
    }

    public void setPriceType(RestaurantPriceType priceType) {
        this.priceType = priceType;
    }

    public CouriersDB getHiredCouriers() {
        return hiredCouriers;
    }

    public void setHiredCouriers(CouriersDB hiredCouriers) {
        this.hiredCouriers = hiredCouriers;
    }

    public OrdersService getOrdersService() {
        return ordersService;
    }

    public void setOrdersService(OrdersService ordersService) {
        this.ordersService = ordersService;
    }

    public double updateRating() {
        List<Feedback> feedbacks = ordersService.getAllFeedbacks();
        double sum = 5;
        for (Feedback feedback : feedbacks) {
            sum += feedback.getRating().getValue();
        }
        double avg = sum / (feedbacks.size() + 1);
        rating = avg;
        return avg;
    }

    public boolean isActive() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        Day day = Day.values()[calendar.get(Calendar.DAY_OF_WEEK) - 1];
        return schedule.getDays().contains(day) && schedule.isTimeInInterval(new Time(hour, minute));
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Restaurant that = (Restaurant) o;
        return id == that.id;
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
        return "{id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", schedule=" + schedule +
                ", priceType=" + priceType +
                ", rating=" + rating +
                ", alpha score=" + getAlphaScore() +
                ", is active=" + isActive + "}";
    }
}
