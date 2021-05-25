package ir.ac.kntu.utils;

public class IdGenerator {
    private static int id = 101;

    private IdGenerator() {
    }


    public static int generateNewId() {
        return id++;
    }
}
