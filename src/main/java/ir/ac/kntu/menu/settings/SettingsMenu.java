package ir.ac.kntu.menu.settings;

import ir.ac.kntu.menu.Menu;
import ir.ac.kntu.models.ShopsFilteringStrategy;
import ir.ac.kntu.models.Settings;

public class SettingsMenu extends Menu {

    private final Settings settings;

    public SettingsMenu(Settings settings) {
        this.settings = settings;
    }

    @Override
    public void show() {
        SettingsOption settingsOption ;
        while ((settingsOption= printMenuOptions("Settings Menu",SettingsOption.class))
                != SettingsOption.BACK) {
            if (settingsOption != null) {
                switch (settingsOption) {
                    case RESTAURANTS_FILTERING:
                        filterRestaurants();
                        break;
                    case SHOW_SETTINGS:
                        showSettings();
                        break;
                    default:
                        break;
                }
            }
        }
    }


    private void filterRestaurants() {
        printEnumOptions(ShopsFilteringStrategy.class);
        System.out.println("Enter your choice of filtering restaurants :");
        ShopsFilteringStrategy shopsFilteringStrategy = getOption(ShopsFilteringStrategy.class);
        if (shopsFilteringStrategy == null) {
            return;
        }
        settings.setRestaurantsFilteringStrategy(shopsFilteringStrategy);
    }

    private void showSettings() {
        System.out.println(settings);
    }
}
