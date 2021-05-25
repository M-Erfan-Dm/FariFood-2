package ir.ac.kntu.menu.order;

public enum ShowOrdersOption {
    SHOW_ALL,
    SHOW_BY_ID,
    SHOW_BY_STATE,
    BACK;

    public static void printOptions() {
        System.out.println(
                "\n1.Show all orders\n" +
                        "2.Show order by id\n" +
                        "3.Show orders by state\n" +
                        "4.Back\n");
    }
}
