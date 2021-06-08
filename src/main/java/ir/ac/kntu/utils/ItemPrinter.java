package ir.ac.kntu.utils;

@FunctionalInterface
public interface ItemPrinter<T> {
    String getText(T t, int count);
}
