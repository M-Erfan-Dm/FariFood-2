package ir.ac.kntu.service;

import ir.ac.kntu.models.Customer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PremiumCustomersService {
    Set<Customer> premiumCustomers;

    public PremiumCustomersService(Set<Customer> premiumCustomers) {
        this.premiumCustomers = premiumCustomers;
    }

    public void setPremiumCustomers(Set<Customer> premiumCustomers) {
        this.premiumCustomers = premiumCustomers;
    }

    public void addPremiumCustomer(Customer customer){
        premiumCustomers.add(customer);
    }

    public boolean removePremiumCustomer(Customer customer){
        return premiumCustomers.remove(customer);
    }

    public boolean containsCustomer(Customer customer){
        return premiumCustomers.contains(customer);
    }

    public void printPremiumCustomers(){
        List<Customer> customers = new ArrayList<>(premiumCustomers);
        for (int i = 0;i<customers.size();i++){
            System.out.println("No." + (i+1) + " " +customers.get(i));
        }
        System.out.println(customers.size() + " customers found");
    }
}
