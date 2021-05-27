package ir.ac.kntu.models;

import java.util.*;

public class Order {
    private int id;

    private Map<Food,Integer> foods;

    private Feedback feedback;

    private Customer customer;

    private Courier courier;

    private OrderState orderState;

    public Order(Map<Food,Integer> foods, Feedback feedback, Customer customer,
                 Courier courier, OrderState orderState) {
        this.foods = foods;
        this.feedback = feedback;
        this.customer = customer;
        this.courier = courier;
        this.orderState = orderState;
    }

    public Order(int id, Map<Food,Integer> foods, Feedback feedback, Customer customer, Courier courier, OrderState orderState) {
        this(foods, feedback, customer, courier, orderState);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Map<Food,Integer> getFoods() {
        return new HashMap<>(foods);
    }

    public void setFoods(Map<Food,Integer> foods) {
        this.foods = foods;
    }

    public Feedback getFeedback() {
        return feedback;
    }

    public void setFeedback(Feedback feedback) {
        this.feedback = feedback;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Courier getCourier() {
        return courier;
    }

    public void setCourier(Courier courier) {
        this.courier = courier;
    }

    public OrderState getOrderState() {
        return orderState;
    }

    public void setOrderState(OrderState orderState) {
        this.orderState = orderState;
    }

    public OrderState getNextOrderState() {
        if (orderState == OrderState.PROCESSING) {
            return OrderState.SENDING;
        } else if (orderState == OrderState.SENDING) {
            return OrderState.DELIVERED;
        }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Order order = (Order) o;
        return id == order.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        String feedBackString;
        if (feedback == null) {
            feedBackString = "No Feedback Inserted";
        } else {
            feedBackString = feedback.toString();
        }
        return "{id=" + id +
                ", food=" + foods +
                ", feedback=" + feedBackString +
                ", customer=" + customer +
                ", courier=" + courier +
                ", orderState=" + orderState + "}";
    }
}
