package ir.ac.kntu.menu.restaurant;

public enum RestaurantsOption {
    ADD,
    UPDATE,
    FIND_BY_ID,
    FIND_BY_NAME,
    FIND_BY_PRICE_TYPE,
    SHOW_ALL,
    REMOVE,
    BACK;

    public static void printOptions() {
        System.out.println("\n1.Add restaurant\n" +
                "2.Update existing restaurant\n" +
                "3.Find restaurant by id\n" +
                "4.Find restaurants by name\n" +
                "5.Find restaurants by price type\n" +
                "6.Show all\n" +
                "7.Remove restaurant\n" +
                "8.Back\n");
    }
}
