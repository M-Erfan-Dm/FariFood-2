package ir.ac.kntu.menu.restaurant;

public enum RestaurantFoodMenuOption {
    ADD,
    UPDATE,
    FIND_FOOD_BY_NAME,
    SHOW_ALL,
    REMOVE,
    BACK;

    public static void printOptions() {
        System.out.println("\n1.Add new food\n" +
                "2.Update existing food\n" +
                "3.Find food by name\n" +
                "4.Show all\n" +
                "5.Remove food\n" +
                "6.Back\n");
    }
}
