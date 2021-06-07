package ir.ac.kntu.menu.shop.shop_info;

import ir.ac.kntu.db.CouriersDB;
import ir.ac.kntu.db.OwnersDB;
import ir.ac.kntu.menu.shop.ShopCountableFoodMenu;
import ir.ac.kntu.models.FruitShop;

public class FruitShopInfoMenu extends ShopInfoMenu<FruitShop> {
    public FruitShopInfoMenu(FruitShop shop, CouriersDB couriersDB, OwnersDB ownersDB) {
        super(shop, couriersDB, ownersDB);
    }

    @Override
    public void show() {
        FruitShopInfoMenuOption option;
        while ((option = printMenuOptions("Fruit Shop Info", FruitShopInfoMenuOption.class))
                != FruitShopInfoMenuOption.BACK) {
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
        FruitShop newFruitShop = getFruitShopGeneralInfo(getOwnersDB());
        if (newFruitShop == null) {
            return;
        }
        getShop().setOwner(newFruitShop.getOwner());
        getShop().setName(newFruitShop.getName());
        getShop().setAddress(newFruitShop.getAddress());
        getShop().setSchedule(newFruitShop.getSchedule());
        getShop().setDeliveryPrice(newFruitShop.getDeliveryPrice());
        getShop().getPeriodsService().setPeriodBasePrice(newFruitShop.getPeriodsService().getPeriodBasePrice());
        getShop().getPeriodsService().setFruitKGLimit(newFruitShop.getPeriodsService().getFruitKGLimit());
        getShop().setPriceType(newFruitShop.getPriceType());
        System.out.println("Fruit shop is updated");
    }
}
