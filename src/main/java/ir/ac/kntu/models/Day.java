package ir.ac.kntu.models;

public enum Day {
    SUNDAY,
    MONDAY,
    TUESDAY,
    WEDNESDAY,
    THURSDAY,
    FRIDAY,
    SATURDAY,
    ALL;

    public static void printOptions() {
        System.out.println(
                "\n1.Sunday\n" +
                        "2.Monday\n" +
                        "3.Tuesday\n" +
                        "4.Wednesday\n" +
                        "5.Thursday\n" +
                        "6.Friday\n" +
                        "7.Saturday\n" +
                        "8.All\n");
    }
}
