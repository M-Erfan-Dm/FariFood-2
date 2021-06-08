package ir.ac.kntu.utils;

import java.util.List;

public class ListPagePrinting {
    private static final int ITEM_LIMIT = 2;

    public static <T> void printList(List<T> list, ItemPrinter<T> printer) {
        if (list.size()==0){
            return;
        }
        int page = 0;
        int maxPage = (int) Math.ceil(((double) list.size()) / ITEM_LIMIT);
        while (page >= 0) {
            int firstIndex = page * ITEM_LIMIT;
            int lastIndex = Math.min(firstIndex + ITEM_LIMIT, list.size());
            for (int i = firstIndex; i < lastIndex; i++) {
                System.out.println(printer.getText(list.get(i),i+1));
            }
            page = movePage(page, maxPage);
        }
    }

    private static Integer getChoice() {
        System.out.print("1.Next    2.Previous    3.Exit: ");
        int choice = Integer.parseInt(ScannerWrapper.nextLine()) - 1;
        if (choice >= 0 && choice<3) {
            return choice;
        }
        return null;
    }

    private static Integer movePage(int page, int maxPage) {
        Integer choice = getChoice();
        if (choice == null) {
            return page;
        }
        if (choice == 0 && page + 1 < maxPage) {
            return page + 1;
        }
        if (choice == 1) {
            return page - 1;
        }
        if (choice==2){
            return -1;
        }
        return page;
    }
}
