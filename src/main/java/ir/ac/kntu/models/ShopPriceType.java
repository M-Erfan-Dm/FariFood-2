package ir.ac.kntu.models;

public enum ShopPriceType {
    ECONOMICAL,
    MIDDLE,
    LUXURIOUS;

    public static void printOptions() {
        System.out.println(
                "\n1.Economical\n" +
                        "2.Middle\n" +
                        "3.Luxurious\n");
    }
}
