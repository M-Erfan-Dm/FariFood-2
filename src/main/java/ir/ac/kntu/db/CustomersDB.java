package ir.ac.kntu.db;

import ir.ac.kntu.models.*;

import java.util.*;

public class CustomersDB {
    private Set<Customer> customers;

    public CustomersDB(Set<Customer> customers) {
        this.customers = customers;
    }

    public Set<Customer> getCustomers() {
        return new HashSet<>(customers);
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

    public boolean isCustomerValid(String phoneNumber , String password){
        return customers.stream().anyMatch(existingCustomer->existingCustomer.getPhoneNumber().equals(phoneNumber) &&
                existingCustomer.getPassword().equals(password));
    }

    public Customer getCustomerByPhoneNumber(String phoneNumber) {
        for (Customer customer : customers) {
            if (customer.getPhoneNumber().equals(phoneNumber)) {
                return customer;
            }
        }
        return null;
    }

    public Set<Order> getOrdersOfCustomer(String customerPhoneNumber, Set<Order> allOrders) {
        Customer customer = getCustomerByPhoneNumber(customerPhoneNumber);
        if (customer != null) {
            return new OrdersService<>(allOrders).getOrdersByCustomer(customer);
        }
        return null;
    }

    public List<Feedback> getFeedbacksOfCustomer(String customerPhoneNumber, Set<Order> allOrders) {
        List<Feedback> feedbacks = new ArrayList<>();
        Set<Order> orders = getOrdersOfCustomer(customerPhoneNumber, allOrders);
        for (Order order : orders) {
            Feedback feedback = order.getFeedback();
            if (feedback != null) {
                feedbacks.add(feedback);
            }
        }
        return feedbacks;
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

