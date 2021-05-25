package ir.ac.kntu.models;

public enum Rating {
    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5);
    private final int value;

    Rating(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static void printOptions() {
        System.out.println("1.One 2.Two 3.Three 4.Four 5.Five");
    }
}
