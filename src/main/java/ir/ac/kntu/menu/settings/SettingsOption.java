package ir.ac.kntu.menu.settings;

public enum SettingsOption {
    RESTAURANTS_FILTERING,
    SHOW_SETTINGS,
    BACK;

    public static void printOptions() {
        System.out.println("\n1.Restaurants filtering\n" +
                "2.Show current settings\n" +
                "3.Back\n");
    }
}
