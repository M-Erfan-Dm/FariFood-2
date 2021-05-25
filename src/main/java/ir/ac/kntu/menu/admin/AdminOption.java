package ir.ac.kntu.menu.admin;

public enum AdminOption {
    LOGIN,
    REGISTER,
    EXIT;

    public static void printOptions() {
        System.out.println("\n1.Login\n" +
                "2.Register\n" +
                "3.Exit\n");
    }
}
