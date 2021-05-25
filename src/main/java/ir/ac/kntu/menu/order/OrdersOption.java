package ir.ac.kntu.menu.order;

public enum OrdersOption {
    ADD,
    UPDATE,
    CANCEL,
    SHOW_ORDERS,
    SHOW_FEEDBACKS_OF_FOOD,
    BACK;

    public static void printOptions() {
        System.out.println(
                "\n1.Add order\n" +
                        "2.Update order\n" +
                        "3.Cancel order\n" +
                        "4.Show orders\n" +
                        "5.Show feedbacks of food\n" +
                        "6.Back\n"
        );
    }
}
