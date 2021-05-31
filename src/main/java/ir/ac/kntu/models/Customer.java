package ir.ac.kntu.models;

import java.util.Objects;

public class Customer {
    private String phoneNumber;

    private String password;

    private String address;

    public Customer(String phoneNumber, String password, String address) {
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Customer customer = (Customer) o;
        return phoneNumber.equals(customer.phoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(phoneNumber);
    }

    @Override
    public String toString() {
        return "{phoneNumber='" + phoneNumber + '\'' +
                ", address='" + address + '\'' + "}";
    }
}
