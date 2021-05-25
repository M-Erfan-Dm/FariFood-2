package ir.ac.kntu.menu.main;

public enum MainMenuOption {
    ORDERS,
    COURIERS,
    RESTAURANTS,
    CUSTOMERS,
    SETTINGS,
    BACK;

    public static void printOptions() {
        System.out.println("\n1.Orders\n" +
                "2.Couriers\n" +
                "3.Restaurants\n" +
                "4.Customers\n" +
                "5.Settings\n" +
                "6.Back\n");
    }
}
