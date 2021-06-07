package ir.ac.kntu.menu.order;

import ir.ac.kntu.db.ShopsDB;
import ir.ac.kntu.menu.Menu;
import ir.ac.kntu.models.*;
import ir.ac.kntu.utils.ScannerWrapper;

import java.util.List;
import java.util.Map;

public abstract class AddOrderMenu<T extends Shop<? extends OrdersService<? extends Order>>,
        D extends ShopsDB<T>> extends Menu {

    private Customer customer;

    private final D shopsDB;

    private final Settings settings;

    public AddOrderMenu(D shopsDB, Settings settings) {
        this.shopsDB = shopsDB;
        this.settings = settings;
    }

    public AddOrderMenu(Customer customer, D shopsDB, Settings settings) {
        this.customer = customer;
        this.shopsDB = shopsDB;
        this.settings = settings;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public D getShopsDB() {
        return shopsDB;
    }

    public T chooseShop() {
        printEnumOptions(OrdersShopOption.class);
        System.out.println("Enter your choice :");
        OrdersShopOption option = getOption(OrdersShopOption.class);
        if (option == null) {
            return null;
        }
        switch (option) {
            case SHOW_ACTIVE_RESTAURANTS:
                return showActiveRestaurants();
            case SHOW_THREE_BEST_RESTAURANTS:
                return showThreeBestShops();
            case SHOW_RESTAURANTS_BY_NAME:
                return showShopsByName();
            case SHOW_RESTAURANTS_BY_PRICE_TYPE:
                return showShopsByPriceType();
            case SHOW_FIVE_BEST_RESTAURANTS_BY_FOOD:
                return showFiveBestShopsByFood();
            default:
                break;
        }
        return null;
    }

    private OrdersFoodOption printOrderFoodOptions() {
        printEnumOptions(OrdersFoodOption.class);
        System.out.println("Enter your choice :");
        return getOption(OrdersFoodOption.class);
    }

    public boolean showFoods(T shop) {
        OrdersFoodOption option;
        while ((option = printOrderFoodOptions())
                != OrdersFoodOption.BACK) {
            if (option != null) {
                boolean shouldContinueSubmittingFoods = false;
                switch (option) {
                    case SHOW_ALL:
                        showAllFoods(shop);
                        shouldContinueSubmittingFoods = continueSubmittingFood();
                        break;
                    case SHOW_THREE_BEST:
                        showThreeBestFoods(shop);
                        shouldContinueSubmittingFoods = continueSubmittingFood();
                        break;
                    default:
                        break;
                }
                if (shouldContinueSubmittingFoods) {
                    return true;
                }
            }
        }
        return false;
    }


    private T showActiveRestaurants() {
        List<T> shops = new ShopsDB<>(shopsDB.getActiveShops()).getOrderedListOfShops(settings);
        printList(shops, "shops");
        if (shops.size() == 0) {
            return null;
        }
        return getShopById();
    }

    private T showThreeBestShops() {
        List<T> shops = shopsDB.getBestShops(3);
        printList(shops, "shops");
        if (shops.size() == 0) {
            return null;
        }
        return getShopById();
    }

    private T showShopsByName() {
        String name = getName();
        List<T> foundShopsByName = new ShopsDB<>(shopsDB.getShopsByName(name)).getOrderedListOfShops(settings);
        printList(foundShopsByName, "shops");
        if (foundShopsByName.size() == 0) {
            return null;
        }
        return getShopById();
    }

    private T showShopsByPriceType() {
        ShopPriceType shopPriceType = getShopPriceType();
        if (shopPriceType == null) {
            return null;
        }
        List<T> foundShopsByPriceType = new ShopsDB<>(shopsDB.
                getShopsByPriceType(shopPriceType)).getOrderedListOfShops(settings);
        printList(foundShopsByPriceType, "shops");
        if (foundShopsByPriceType.size() == 0) {
            return null;
        }
        return getShopById();
    }

    private T showFiveBestShopsByFood() {
        String foodName = getName();
        List<T> shops = getFiveBestShopsByFood(new Food(foodName));
        printList(shops, "shops");
        if (shops.size() == 0) {
            return null;
        }
        return getShopById();
    }

    private T getShopById() {
        int id = getId();
        T shop = shopsDB.getShopById(id);
        if (shop == null) {
            System.out.println("Shop not found");
            return null;
        }
        if (!shop.isActive()) {
            System.out.println("Shop is closed!");
            return null;
        }
        return shop;
    }

    private boolean continueSubmittingFood() {
        System.out.println("Continue ?\n1.Yes 2.No");
        int choice = Integer.parseInt(ScannerWrapper.nextLine()) - 1;
        return choice == 0;
    }

    abstract public Map<Food, Integer> chooseFoods(T shop);

    abstract public void showAllFoods(T shop);

    abstract public void showThreeBestFoods(T shop);

    abstract public List<T> getFiveBestShopsByFood(Food food);
}
