package ir.ac.kntu.db;

import ir.ac.kntu.models.Customer;
import ir.ac.kntu.models.Feedback;
import ir.ac.kntu.models.Order;
import ir.ac.kntu.models.OrdersService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class CustomersDB {
    private Set<Customer> customers;

    public CustomersDB(Set<Customer> customers) {
        this.customers = customers;
    }

    public void setCustomers(Set<Customer> customers) {
        this.customers = customers;
    }

    public void addCustomer(Customer customer) {
        removeCustomer(customer);
        customers.add(customer);
    }

    public boolean removeCustomer(Customer customer) {
        return customers.remove(customer);
    }

    public boolean containsCustomer(Customer customer) {
        return customers.contains(customer);
    }

    public Customer getCustomerByPhoneNumber(String phoneNumber) {
        for (Customer customer : customers) {
            if (customer.getPhoneNumber().equals(phoneNumber)) {
                return customer;
            }
        }
        return null;
    }

    public Set<Order> getOrdersOfCustomer(String customerPhoneNumber, RestaurantsDB restaurantsDB) {
        Customer customer = getCustomerByPhoneNumber(customerPhoneNumber);
        if (customer != null) {
            OrdersService ordersService = new OrdersService(restaurantsDB.getAllOrders());
            return ordersService.getOrdersByCustomer(customer);
        }
        return null;
    }

    public List<Feedback> getFeedbacksOfCustomer(String customerPhoneNumber, RestaurantsDB restaurantsDB) {
        List<Feedback> feedbacks = new ArrayList<>();
        Set<Order> orders = getOrdersOfCustomer(customerPhoneNumber, restaurantsDB);
        for (Order order : orders) {
            Feedback feedback = order.getFeedback();
            if (feedback != null) {
                feedbacks.add(feedback);
            }
        }
        return feedbacks;
    }

    public void printAllCustomers() {
        List<Customer> customersList = new ArrayList<>(customers);
        for (int i = 0; i < customersList.size(); i++) {
            Customer customer = customersList.get(i);
            System.out.println("No." + (i + 1) + " " + customer);
        }
        System.out.println(customersList.size() + " customers found");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CustomersDB that = (CustomersDB) o;
        return customers.equals(that.customers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customers);
    }

    @Override
    public String toString() {
        return "{customers=" + customers + "}";
    }
}

