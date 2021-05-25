package ir.ac.kntu.menu.restaurant;

public enum RestaurantCourierOption {
    HIRE,
    UPDATE_JOB_INFO,
    DISMISS,
    FIND_BY_PHONE_NUMBER,
    SHOW_ALL,
    BACK;

    public static void printOptions() {
        System.out.println("\n1.Hire courier\n" +
                "2.Update courier job info\n" +
                "3.Dismiss courier\n" +
                "4.Find courier by phone number\n" +
                "5.Show all\n" +
                "6.Back\n");
    }
}
