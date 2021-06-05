package ir.ac.kntu.models;

import java.util.Objects;

public class Settings {
    private ShopsFilteringStrategy shopsFilteringStrategy = ShopsFilteringStrategy.BY_RATING_DESCENDING;

    public Settings(){}

    public Settings(ShopsFilteringStrategy shopsFilteringStrategy) {
        this.shopsFilteringStrategy = shopsFilteringStrategy;
    }

    public ShopsFilteringStrategy getRestaurantsFilteringStrategy() {
        return shopsFilteringStrategy;
    }

    public void setRestaurantsFilteringStrategy(ShopsFilteringStrategy shopsFilteringStrategy) {
        this.shopsFilteringStrategy = shopsFilteringStrategy;
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
        return shopsFilteringStrategy == settings.shopsFilteringStrategy;
    }

    @Override
    public int hashCode() {
        return Objects.hash(shopsFilteringStrategy);
    }

    @Override
    public String toString() {
        return "{restaurants listing strategy = " + shopsFilteringStrategy + "}";
    }
}
