package ir.ac.kntu.menu.customer;

public enum CustomersOption {
    REGISTER,
    UPDATE,
    FIND_BY_PHONE_NUMBER,
    SHOW_ALL,
    REMOVE,
    BACK;

    public static void printOptions() {
        System.out.println("\n1.Register new customer\n" +
                "2.Update existing customer\n" +
                "3.Find customer by phone number\n" +
                "4.Show all\n" +
                "5.Remove customer\n" +
                "6.Back\n");
    }
}
