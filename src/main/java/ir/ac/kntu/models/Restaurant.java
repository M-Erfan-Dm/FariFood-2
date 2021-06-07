package ir.ac.kntu.models;

import ir.ac.kntu.db.CouriersDB;

public class Restaurant extends Shop<OrdersService<Order>> {

    private FoodMenu foodMenu;

    public Restaurant(int id, Owner owner, String name, String address, Schedule schedule,
                      CouriersDB hiredCouriers, OrdersService<Order> ordersService, int deliveryPrice,
                      FoodMenu foodMenu, ShopPriceType priceType) {
        super(id, owner, name, address, schedule, hiredCouriers, ordersService, deliveryPrice, priceType);
        this.foodMenu = foodMenu;
    }

    public Restaurant(Owner owner, String name, String address, Schedule schedule, ShopPriceType priceType, int deliveryPrice) {
        super(owner, name, address, schedule, deliveryPrice, priceType);
    }

    public FoodMenu getFoodMenu() {
        return foodMenu;
    }

    public void setFoodMenu(FoodMenu foodMenu) {
        this.foodMenu = foodMenu;
    }


    @Override
    public int getAdditionalPrices(Order order) {
        return getDeliveryPrice();
    }

}
