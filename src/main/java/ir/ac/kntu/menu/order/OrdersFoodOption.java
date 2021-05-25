package ir.ac.kntu.menu.order;

public enum OrdersFoodOption {
    SHOW_ALL,
    SHOW_THREE_BEST,
    BACK;

    public static void printOptions() {
        System.out.println("\n1.Show all\n" +
                "2.Show three of best foods\n" +
                "3.Back");
    }
}
