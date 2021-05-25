package ir.ac.kntu.models;

public enum OrderState {
    PROCESSING,
    SENDING,
    DELIVERED;

    public static void printOptions() {
        System.out.println(
                "\n1.Processing\n" +
                        "2.Sending\n" +
                        "3.Delivered\n");
    }
}
