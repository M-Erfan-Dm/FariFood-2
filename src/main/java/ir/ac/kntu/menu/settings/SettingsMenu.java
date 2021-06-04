package ir.ac.kntu.menu.settings;

import ir.ac.kntu.menu.Menu;
import ir.ac.kntu.models.RestaurantsFilteringStrategy;
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
        RestaurantsFilteringStrategy.printOptions();
        System.out.println("Enter your choice of filtering restaurants :");
        RestaurantsFilteringStrategy restaurantsFilteringStrategy = getOption(RestaurantsFilteringStrategy.class);
        if (restaurantsFilteringStrategy == null) {
            return;
        }
        settings.setRestaurantsFilteringStrategy(restaurantsFilteringStrategy);
    }

    private void showSettings() {
        System.out.println(settings);
    }
}
