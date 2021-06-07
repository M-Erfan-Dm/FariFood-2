package ir.ac.kntu.db;

import ir.ac.kntu.models.*;
import ir.ac.kntu.utils.IdGenerator;
import ir.ac.kntu.utils.ListSorting;

import java.util.*;
import java.util.stream.Collectors;

public class ShopsDB<T extends Shop<? extends OrdersService< ? extends Order>>> {
    private Set<T> shops;

    public ShopsDB(Set<T> shops) {
        this.shops = shops;
    }

    public Set<T> getShops() {
        return new HashSet<>(shops);
    }

    public void setShops(Set<T> shops) {
        this.shops = shops;
    }

    public boolean add(T shop) {
        if (contains(shop.getName(), shop.getAddress())) {
            return false;
        }
        shop.setId(IdGenerator.generateNewId());
        shops.add(shop);
        return true;
    }

    public boolean remove(T shop) {
        return shops.remove(shop);
    }

    public boolean contains(T shop) {
        return shops.contains(shop);
    }

    public boolean contains(String name, String address) {
        return shops.stream().anyMatch(shop->shop.getName().equals(name)
                && shop.getAddress().equals(address));
    }

    public Set<T> getShopsByName(String name) {
        return shops.stream().filter(shop -> shop.getName().equals(name)).collect(Collectors.toSet());
    }

    public Set<T> getShopsByOwner(Owner owner){
        return shops.stream().filter(shop->shop.getOwner().equals(owner)).collect(Collectors.toSet());
    }

    public Set<T> getActiveShops() {
        return shops.stream().filter(shop -> shop.isActive()).collect(Collectors.toSet());
    }

    public T getShopById(int id) {
        return shops.stream().filter(shop -> shop.getId() == id).findFirst().orElse(null);
    }

    public Set<Order> getAllOrders() {
        Set<Order> orders = new HashSet<>();
        shops.forEach(shop -> orders.addAll(shop.getOrdersService().getOrders()));
        return orders;
    }

    public List<Feedback> getAllFeedbacksOfFood(Food food) {
        List<Feedback> feedbacks = new ArrayList<>();
        shops.forEach(shop -> feedbacks.addAll(shop.getOrdersService().getFeedbacksOfFood(food)));
        return feedbacks;
    }

    public Set<T> getShopsByPriceType(ShopPriceType priceType) {
        return getShops().stream().filter(shop->shop.getPriceType().equals(priceType)).collect(Collectors.toSet());
    }

    public List<T> getBestShops(int count) {
        return ListSorting.sortList(new ArrayList<>(shops), count, false,
                shop -> shop.getRating());
    }

    public T getShopByOrderId(int id){
        return shops.stream().filter(shop->shop.getOrdersService().containsOrder(id))
                .findFirst().orElse(null);
    }

    private List<T> getOrderedListOfShopsByRating(boolean isAscending){
        return ListSorting.sortList(new ArrayList<>(shops),shops.size(),
                isAscending,shop->shop.getRating());
    }

    private List<T> getOrderedListOfShopsByFeedbacksCount(boolean isAscending) {
        return ListSorting.sortList(new ArrayList<>(shops),shops.size(), isAscending,
                shop-> Double.valueOf(shop.getOrdersService().getCountOfAllFeedbacks()));
    }

    private List<T> getWeakerShops(){
        return ListSorting.sortList(new ArrayList<>(shops),shops.size(),false,
                shop->shop.getAlphaScore());
    }

    public List<T> getOrderedListOfShops(Settings settings) {
        switch (settings.getRestaurantsFilteringStrategy()) {
            case BY_RATING_ASCENDING:
                return getOrderedListOfShopsByRating(true);
            case BY_RATING_DESCENDING:
                return getOrderedListOfShopsByRating(false);
            case BY_FEEDBACKS_COUNT_ASCENDING:
                return getOrderedListOfShopsByFeedbacksCount(true);
            case BY_FEEDBACKS_COUNT_DESCENDING:
                return getOrderedListOfShopsByFeedbacksCount(false);
            case BY_ALPHA_SCORE:
                return getWeakerShops();
            default:
                break;
        }
        return getOrderedListOfShopsByRating(false);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }
        if (o == null || getClass() != o.getClass()){
            return false;
        }
        ShopsDB<?> shopsDB = (ShopsDB<?>) o;
        return shops.equals(shopsDB.shops);
    }

    @Override
    public int hashCode() {
        return Objects.hash(shops);
    }

    @Override
    public String toString() {
        return "{" +
                "shops=" + shops +
                '}';
    }
}
