package ir.ac.kntu.menu.shop.shop_info;

import ir.ac.kntu.db.CouriersDB;
import ir.ac.kntu.db.OwnersDB;
import ir.ac.kntu.menu.Menu;
import ir.ac.kntu.menu.shop.ShopCourierMenu;
import ir.ac.kntu.models.Feedback;
import ir.ac.kntu.models.Order;
import ir.ac.kntu.models.OrdersService;
import ir.ac.kntu.models.Shop;

import java.util.ArrayList;
import java.util.List;

public abstract class ShopInfoMenu<T extends Shop<? extends OrdersService<? extends Order>>> extends Menu {

    private final T shop;

    private final CouriersDB couriersDB;

    private final OwnersDB ownersDB;

    public ShopInfoMenu(T shop, CouriersDB couriersDB, OwnersDB ownersDB) {
        this.shop = shop;
        this.couriersDB = couriersDB;
        this.ownersDB = ownersDB;
    }

    public T getShop() {
        return shop;
    }

    public CouriersDB getCouriersDB() {
        return couriersDB;
    }

    public OwnersDB getOwnersDB() {
        return ownersDB;
    }

    public void showShopGeneralInfo() {
        System.out.println(shop);
    }

    public void showShopCouriers() {
        ShopCourierMenu shopCourierMenu = new ShopCourierMenu(couriersDB, shop);
        shopCourierMenu.show();
    }

    public void showOrders() {
        printList(new ArrayList<>(shop.getOrdersService().getOrders()), "orders");
    }

    public void showFeedbacks() {
        List<Feedback> feedbacks = shop.getOrdersService().getAllFeedbacks();
        printList(feedbacks, "feedbacks");
    }

    abstract public void showShopFoodMenu();

    abstract public void updateShop();
}
