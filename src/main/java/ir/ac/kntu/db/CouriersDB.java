package ir.ac.kntu.db;

import ir.ac.kntu.models.Courier;
import ir.ac.kntu.models.Order;
import ir.ac.kntu.models.OrdersService;
import ir.ac.kntu.models.ShopsDBReference;

import java.util.*;

public class CouriersDB {
    private Set<Courier> couriers;

    public CouriersDB(Set<Courier> couriers) {
        this.couriers = couriers;
    }

    public Set<Courier> getCouriers() {
        return new HashSet<>(couriers);
    }

    public void setCouriers(Set<Courier> couriers) {
        this.couriers = couriers;
    }

    public void addCourier(Courier courier) {
        removeCourier(courier);
        couriers.add(courier);
    }

    public boolean removeCourier(Courier courier) {
        return couriers.remove(courier);
    }


    public Courier getCourierByPhoneNumber(String phoneNumber) {
        for (Courier courier : couriers) {
            if (courier.getPhoneNumber().equals(phoneNumber)) {
                return courier;
            }
        }
        return null;
    }

    public Set<Order> getOrdersOfCourier(String courierPhoneNumber, ShopsDBReference shopsDBReference) {
        Courier courier = getCourierByPhoneNumber(courierPhoneNumber);
        if (courier != null) {
            OrdersService<Order> ordersService = new OrdersService<>(shopsDBReference.getAllOrders());
            return ordersService.getOrdersByCourier(courier);
        }
        return null;
    }

    public boolean containsCourier(Courier courier) {
        return couriers.contains(courier);
    }

    public List<Courier> getAvailableCouriers(int restaurantId) {
        List<Courier> foundCouriers = new ArrayList<>();
        for (Courier courier : couriers) {
            if (courier.isAvailable(restaurantId)) {
                foundCouriers.add(courier);
            }
        }
        return foundCouriers;
    }

    public void printAllCouriers() {
        List<Courier> courierList = new ArrayList<>(couriers);
        for (int i = 0; i < courierList.size(); i++) {
            Courier courier = courierList.get(i);
            System.out.println("No." + (i + 1) + " " + courier);
        }
        System.out.println(courierList.size() + " couriers found");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CouriersDB that = (CouriersDB) o;
        return couriers.equals(that.couriers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(couriers);
    }

    @Override
    public String toString() {
        return "{couriers=" + couriers + "}";
    }
}
