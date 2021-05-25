package ir.ac.kntu.models;

import java.util.Objects;

public class Settings {
    private RestaurantsFilteringStrategy restaurantsFilteringStrategy;

    public Settings(RestaurantsFilteringStrategy restaurantsFilteringStrategy) {
        this.restaurantsFilteringStrategy = restaurantsFilteringStrategy;
    }

    public RestaurantsFilteringStrategy getRestaurantsFilteringStrategy() {
        return restaurantsFilteringStrategy;
    }

    public void setRestaurantsFilteringStrategy(RestaurantsFilteringStrategy restaurantsFilteringStrategy) {
        this.restaurantsFilteringStrategy = restaurantsFilteringStrategy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Settings settings = (Settings) o;
        return restaurantsFilteringStrategy == settings.restaurantsFilteringStrategy;
    }

    @Override
    public int hashCode() {
        return Objects.hash(restaurantsFilteringStrategy);
    }

    @Override
    public String toString() {
        return "{restaurants listing strategy = " + restaurantsFilteringStrategy + "}";
    }
}
