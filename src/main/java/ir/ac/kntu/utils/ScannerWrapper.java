package ir.ac.kntu.utils;

import java.util.Scanner;

public class ScannerWrapper {
    private static final Scanner INSTANCE = new Scanner(System.in);

    private ScannerWrapper() {
    }


    public static String nextLine() {
        return INSTANCE.nextLine();
    }

    public static String next() {
        return INSTANCE.next();
    }

    public static void close() {
        INSTANCE.close();
    }
}
