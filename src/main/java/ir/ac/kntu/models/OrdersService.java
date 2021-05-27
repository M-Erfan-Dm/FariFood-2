package ir.ac.kntu.models;

import ir.ac.kntu.utils.IdGenerator;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class OrdersService<T extends Order> {
    private Set<T> orders;

    public OrdersService(Set<T> orders) {
        this.orders = orders;
    }

    public Set<T> getOrders() {
        return new HashSet<>(orders);
    }

    public void setOrders(Set<T> orders) {
        this.orders = orders;
    }

    public void addOrder(T order) {
        order.setId(IdGenerator.generateNewId());
        orders.add(order);
    }

    public boolean removeOrder(T order) {
        return orders.remove(order);
    }

    public T getOrderById(int orderId) {
        for (T order : orders) {
            if (order.getId() == orderId) {
                return order;
            }
        }
        return null;
    }

    public Set<T> getOrdersByCustomer(Customer customer) {
        Set<T> foundOrders = new HashSet<>();
        for (T order : orders) {
            if (order.getCustomer().equals(customer)) {
                foundOrders.add(order);
            }
        }
        return foundOrders;
    }

    public Set<T> getOrdersByCourier(Courier courier) {
        Set<T> foundOrders = new HashSet<>();
        for (T order : orders) {
            if (order.getCourier().equals(courier)) {
                foundOrders.add(order);
            }
        }
        return foundOrders;
    }

    public Set<T> getOrdersByFood(Food food) {
        Set<T> foundOrders = new HashSet<>();
        for (T order : orders) {
            if (order.getFoods().containsKey(food)) {
                foundOrders.add(order);
            }
        }
        return foundOrders;
    }

    public Set<T> getOrdersByState(OrderState orderState) {
        Set<T> foundOrders = new HashSet<>();
        for (T order : orders) {
            if (order.getOrderState() == orderState) {
                foundOrders.add(order);
            }
        }
        return foundOrders;
    }

    public List<Feedback> getAllFeedbacks() {
        List<Feedback> feedbacks = new ArrayList<>();
        for (T order : orders) {
            Feedback feedback = order.getFeedback();
            if (feedback != null) {
                feedbacks.add(feedback);
            }
        }
        return feedbacks;
    }

    public int getCountOfAllFeedbacks() {
        return getAllFeedbacks().size();
    }

    public List<Feedback> getFeedbacksOfFood(Food food) {
        List<Feedback> feedbacks = new ArrayList<>();
        for (T order : orders) {
            if (order.getFoods().containsKey(food) && order.getFeedback() != null) {
                feedbacks.add(order.getFeedback());
            }
        }
        return feedbacks;
    }

    public List<Food> getBestFoods(int count) {
        List<T> allOrders = new ArrayList<>(getOrdersByState(OrderState.DELIVERED));
        allOrders.sort((o1, o2) -> o2.getFeedback().getRating().getValue() - o1.getFeedback().getRating().getValue());
        if (allOrders.size() > count) {
            allOrders = allOrders.subList(0, count);
        }
        List<Food> bestFoods = new ArrayList<>();
        for (T order : allOrders) {
            bestFoods.addAll(order.getFoods().keySet());
        }
        if (bestFoods.size()>count){
            bestFoods = bestFoods.subList(0,count);
        }
        return bestFoods;
    }

    public double getRatingAverageOfFood(String foodName) {
        double sum = 0;
        int count = 0;
        for (T order : orders) {
            if (order.getFoods().containsKey(new Food(foodName)) && order.getFeedback() != null) {
                sum += order.getFeedback().getRating().getValue();
                count++;
            }
        }
        if (count == 0) {
            return 0;
        }
        return sum / count;
    }

    public int getCount() {
        return orders.size();
    }

    public boolean containsOrder(int id) {
        for (T order : orders) {
            if (order.getId() == id) {
                return true;
            }
        }
        return false;
    }

    public void printAllOrders() {
        List<T> ordersList = new ArrayList<>(orders);
        for (int i = 0; i < ordersList.size(); i++) {
            T order = ordersList.get(i);
            System.out.println("No." + (i + 1) + " " + order);
        }
        System.out.println(ordersList.size() + " orders found");
    }


    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }
        if (o == null || getClass() != o.getClass()){
            return false;
        }
        OrdersService<?> that = (OrdersService<?>) o;
        return orders.equals(that.orders);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orders);
    }

    @Override
    public String toString() {
        return "{orders=" + orders + "}";
    }
}
