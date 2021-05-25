package ir.ac.kntu.models;

public enum RestaurantsFilteringStrategy {
    BY_RATING_ASCENDING,
    BY_RATING_DESCENDING,
    BY_FEEDBACKS_COUNT_ASCENDING,
    BY_FEEDBACKS_COUNT_DESCENDING,
    BY_ALPHA_SCORE;

    public static void printOptions() {
        System.out.println("\n1.Ascending by rating\n" +
                "2.Descending by rating\n" +
                "3.Ascending by feedbacks count\n" +
                "4.Descending by feedbacks count\n" +
                "5.By alpha score\n");
    }

}
