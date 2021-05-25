package ir.ac.kntu.menu.courier;

public enum CourierInfoOption {
    GENERAL,
    FEEDBACKS,
    ORDERS_HISTORY;

    public static void printOptions() {
        System.out.println("\n1.General info\n" +
                "2.Feedbacks of Courier\n" +
                "3.Orders history\n");
    }
}
