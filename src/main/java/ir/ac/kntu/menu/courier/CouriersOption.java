package ir.ac.kntu.menu.courier;

public enum CouriersOption {
    REGISTER,
    UPDATE,
    FIND_BY_PHONE_NUMBER,
    SHOW_ALL,
    REMOVE,
    BACK;

    public static void printOptions() {
        System.out.println("\n1.Register new courier\n" +
                "2.Update existing courier\n" +
                "3.Find courier by phone number\n" +
                "4.Show all\n" +
                "5.Remove courier\n" +
                "6.Back\n");
    }
}
