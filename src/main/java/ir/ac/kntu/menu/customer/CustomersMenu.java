package ir.ac.kntu.menu.customer;

import ir.ac.kntu.menu.Menu;
import ir.ac.kntu.models.Customer;
import ir.ac.kntu.models.ShopsDBReference;

public class CustomersMenu extends Menu {
    private final Customer customer;

    private final ShopsDBReference shopsDBReference;

    public CustomersMenu(Customer customer, ShopsDBReference shopsDBReference) {
        this.customer = customer;
        this.shopsDBReference = shopsDBReference;
    }

    @Override
    public void show() {

    }
}
