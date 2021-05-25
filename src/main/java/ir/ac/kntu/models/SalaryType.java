package ir.ac.kntu.models;

public enum SalaryType {
    PER_HOUR,
    PER_ORDER;

    public static void printOptions() {
        System.out.println(
                "\n1.Per hour\n" +
                        "2.Per order\n"
        );
    }

}
