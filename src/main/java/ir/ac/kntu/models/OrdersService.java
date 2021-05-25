package ir.ac.kntu.models;

import ir.ac.kntu.utils.IdGenerator;

import java.util.*;
import java.util.stream.Stream;

public class OrdersService {
    private Set<Order> orders;

    public OrdersService(Set<Order> orders) {
        this.orders = orders;
    }

    public Set<Order> getOrders() {
        return new HashSet<>(orders);
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }

    public void addOrder(Order order) {
        Order newOrder = new Order(IdGenerator.generateNewId(), order.getFoods(), order.getFeedback(),
                order.getCustomer(), order.getCourier(), order.getOrderState());
        orders.add(newOrder);
    }

    public boolean removeOrder(Order order) {
        return orders.remove(order);
    }

    public Order getOrderById(int orderId) {
        for (Order order : orders) {
            if (order.getId() == orderId) {
                return order;
            }
        }
        return null;
    }

    public Set<Order> getOrdersByCustomer(Customer customer) {
        Set<Order> foundOrders = new HashSet<>();
        for (Order order : orders) {
            if (order.getCustomer().equals(customer)) {
                foundOrders.add(order);
            }
        }
        return foundOrders;
    }

    public Set<Order> getOrdersByCourier(Courier courier) {
        Set<Order> foundOrders = new HashSet<>();
        for (Order order : orders) {
            if (order.getCourier().equals(courier)) {
                foundOrders.add(order);
            }
        }
        return foundOrders;
    }

    public Set<Order> getOrdersByFood(Food food) {
        Set<Order> foundOrders = new HashSet<>();
        for (Order order : orders) {
            if (order.getFoods().containsKey(food)) {
                foundOrders.add(order);
            }
        }
        return foundOrders;
    }

    public Set<Order> getOrdersByState(OrderState orderState) {
        Set<Order> foundOrders = new HashSet<>();
        for (Order order : orders) {
            if (order.getOrderState() == orderState) {
                foundOrders.add(order);
            }
        }
        return foundOrders;
    }

    public List<Feedback> getAllFeedbacks() {
        List<Feedback> feedbacks = new ArrayList<>();
        for (Order order : orders) {
            Feedback feedback = order.getFeedback();
            if (feedback != null) {
                feedbacks.add(feedback);
            }
        }
        return feedbacks;
    }

    public int getCountOfAllFeedbacks() {
        int count = 0;
        for (Order order : orders) {
            Feedback feedback = order.getFeedback();
            if (feedback != null) {
                count++;
            }
        }
        return count;
    }

    public List<Feedback> getFeedbacksOfFood(Food food) {
        List<Feedback> feedbacks = new ArrayList<>();
        for (Order order : orders) {
            if (order.getFoods().containsKey(food) && order.getFeedback() != null) {
                feedbacks.add(order.getFeedback());
            }
        }
        return feedbacks;
    }

    public List<Food> getBestFoods(int count) {
        List<Order> allOrders = new ArrayList<>(getOrdersByState(OrderState.DELIVERED));
        allOrders.sort((o1, o2) -> o2.getFeedback().getRating().getValue() - o1.getFeedback().getRating().getValue());
        if (allOrders.size() > count) {
            allOrders = allOrders.subList(0, count);
        }
        List<Food> bestFoods = new ArrayList<>();
        for (Order order : allOrders) {
            bestFoods.addAll(order.getFoods().keySet());
        }
        return bestFoods;
    }

    public double getRatingAverageOfFood(String foodName) {
        double sum = 0;
        int count = 0;
        for (Order order : orders) {
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
        for (Order order : orders) {
            if (order.getId() == id) {
                return true;
            }
        }
        return false;
    }

    public void printAllOrders() {
        List<Order> ordersList = new ArrayList<>(orders);
        for (int i = 0; i < ordersList.size(); i++) {
            Order order = ordersList.get(i);
            System.out.println("No." + (i + 1) + " " + order);
        }
        System.out.println(ordersList.size() + " orders found");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        OrdersService that = (OrdersService) o;
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
