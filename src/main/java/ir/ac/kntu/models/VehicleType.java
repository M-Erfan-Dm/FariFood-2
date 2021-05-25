package ir.ac.kntu.models;

public enum VehicleType {
    CAR,
    MOTORCYCLE;

    public static void printOptions() {
        System.out.println("\n1.Car\n" +
                "2.Motorcycle\n");
    }
}
