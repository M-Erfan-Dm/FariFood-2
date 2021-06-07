package ir.ac.kntu.menu.shop.shop_info;

import ir.ac.kntu.db.CouriersDB;
import ir.ac.kntu.db.CustomersDB;
import ir.ac.kntu.db.OwnersDB;
import ir.ac.kntu.menu.shop.ShopCountableFoodMenu;
import ir.ac.kntu.menu.shop.premium_customers.PremiumCustomersMenu;
import ir.ac.kntu.models.Supermarket;

public class SupermarketInfoMenu extends ShopInfoMenu<Supermarket> {

    private final CustomersDB customersDB;

    public SupermarketInfoMenu(Supermarket shop, CouriersDB couriersDB, OwnersDB ownersDB, CustomersDB customersDB) {
        super(shop, couriersDB, ownersDB);
        this.customersDB = customersDB;
    }

    @Override
    public void show() {
        SupermarketInfoMenuOption option;
        while ((option = printMenuOptions("Supermarket Info", SupermarketInfoMenuOption.class))
                != SupermarketInfoMenuOption.BACK) {
            if (option != null) {
                switch (option) {
                    case UPDATE:
                        updateShop();
                        break;
                    case GENERAL_INFO:
                        showShopGeneralInfo();
                        break;
                    case FOOD:
                        showShopFoodMenu();
                        break;
                    case COURIER:
                        showShopCouriers();
                        break;
                    case ORDER:
                        showOrders();
                        break;
                    case FEEDBACK:
                        showFeedbacks();
                        break;
                    case PREMIUM_CUSTOMER:
                        showPremiumCustomers();
                        break;
                    default:
                        break;
                }
            }
        }
    }

    @Override
    public void showShopFoodMenu() {
        ShopCountableFoodMenu shopCountableFoodMenu = new ShopCountableFoodMenu(getShop().getFoodMenu());
        shopCountableFoodMenu.show();
    }

    @Override
    public void updateShop() {
        Supermarket newSupermarketInfo = getSupermarketGeneralInfo(getOwnersDB());
        if (newSupermarketInfo == null) {
            return;
        }
        getShop().setOwner(newSupermarketInfo.getOwner());
        getShop().setName(newSupermarketInfo.getName());
        getShop().setAddress(newSupermarketInfo.getAddress());
        getShop().setSchedule(newSupermarketInfo.getSchedule());
        getShop().setPriceType(newSupermarketInfo.getPriceType());
        getShop().setDeliveryPrice(newSupermarketInfo.getDeliveryPrice());
        getShop().getPeriodsService().setPeriodBasePrice(newSupermarketInfo.getPeriodsService().getPeriodBasePrice());
        System.out.println("Supermarket is updated");
    }

    private void showPremiumCustomers() {
        PremiumCustomersMenu premiumCustomersMenu = new PremiumCustomersMenu(
                getShop().getPremiumCustomersService(), customersDB);
        premiumCustomersMenu.show();
    }
}
