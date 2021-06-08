package ir.ac.kntu.utils;

import java.util.List;
import java.util.function.Function;

public class ListPagePrinting {
    private static final int PAGE_LIMIT = 15;

    public static <T> void printList(List<T> list, ItemPrinter<T> function) {
        int pageCount = 0;
        int maxPageCount = (int) Math.ceil(((double) list.size()) / ((double) PAGE_LIMIT));
        while (pageCount >= 0) {
            int firstIndex = pageCount * PAGE_LIMIT;
            int lastIndex = Math.min(firstIndex + PAGE_LIMIT, list.size());
            for (int i = firstIndex; i < lastIndex; i++) {
                System.out.println(function.print(list.get(i),i+1));
            }
            pageCount = movePage(pageCount, maxPageCount);
        }
    }

    private static Integer getChoice() {
        System.out.print("1.Next 2.Previous : ");
        int choice = Integer.parseInt(ScannerWrapper.nextLine()) - 1;
        if (choice < 0) {
            return null;
        }
        return choice;
    }

    private static Integer movePage(int page, int maxPage) {
        Integer choice = getChoice();
        if (choice == null) {
            return page;
        }
        if (choice == 0 && page + 1 <= maxPage) {
            return page + 1;
        }
        if (choice == 1) {
            return page - 1;
        }
        return page;
    }
}
