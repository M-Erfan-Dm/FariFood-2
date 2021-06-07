package ir.ac.kntu.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class ListSorting {

    public static <T> List<T> sortList(List<T> list, int count, boolean isAscending, Function<T, Double> mapper) {
        List<T> sortedList = new ArrayList<>(list);
        sortedList.sort((o1, o2) -> {
            if (isAscending) {
                return compareNumbers(mapper.apply(o1), mapper.apply(o2));
            } else {
                return compareNumbers(mapper.apply(o2), mapper.apply(o1));
            }
        });
        if (sortedList.size() > count) {
            sortedList = sortedList.subList(0, count);
        }
        return sortedList;
    }

    private static int compareNumbers(double one, double two) {
        if (one < two) {
            return -1;
        } else if (one > two) {
            return 1;
        }
        return 0;
    }
}
