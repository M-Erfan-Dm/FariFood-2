package ir.ac.kntu.menu.order;

public enum UpdateOrderOption {
    ORDER_STATE,
    FEEDBACK,
    BACK;

    public static void printOptions() {
        System.out.println("\n1.Order state\n" +
                "2.Feedback\n" +
                "3.Back\n");
    }
}
