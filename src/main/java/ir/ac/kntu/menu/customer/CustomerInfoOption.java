package ir.ac.kntu.menu.customer;

public enum CustomerInfoOption {
    GENERAL,
    FEEDBACKS,
    ORDERS_HISTORY;

    public static void printOptions() {
        System.out.println("1.General info\n" +
                "2.Feedbacks of Customer\n" +
                "3.Orders history\n");
    }
}
