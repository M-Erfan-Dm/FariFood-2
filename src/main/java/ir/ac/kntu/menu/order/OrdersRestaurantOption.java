package ir.ac.kntu.menu.order;

public enum OrdersRestaurantOption {
    SHOW_ACTIVE_RESTAURANTS,
    SHOW_THREE_BEST_RESTAURANTS,
    SHOW_RESTAURANTS_BY_NAME,
    SHOW_RESTAURANTS_BY_PRICE_TYPE,
    SHOW_FIVE_BEST_RESTAURANTS_BY_FOOD;

    public static void printOptions() {
        System.out.println("\n1.Show active restaurants\n" +
                "2.Show three of best restaurant\n" +
                "3.Show restaurants by name\n" +
                "4.Show restaurants by price type\n" +
                "5.Show five of best restaurants by food\n");
    }
}
