package ir.ac.kntu.models;

import ir.ac.kntu.db.CouriersDB;

public class Restaurant extends Shop<Order,OrdersService<Order>> {

    private FoodMenu foodMenu;

    private RestaurantPriceType priceType;

    public Restaurant(int id, String name, String address, Schedule schedule,
                      CouriersDB hiredCouriers, OrdersService<Order> ordersService, int deliveryPrice,
                      FoodMenu foodMenu, RestaurantPriceType priceType) {
        super(id, name, address, schedule, hiredCouriers, ordersService,deliveryPrice);
        this.foodMenu = foodMenu;
        this.priceType = priceType;
    }

    public Restaurant(String name, String address, Schedule schedule, RestaurantPriceType priceType, int deliveryPrice) {
        super(name, address, schedule,deliveryPrice);
        this.priceType = priceType;
    }

    public FoodMenu getFoodMenu() {
        return foodMenu;
    }

    public void setFoodMenu(FoodMenu foodMenu) {
        this.foodMenu = foodMenu;
    }

    public RestaurantPriceType getPriceType() {
        return priceType;
    }

    public void setPriceType(RestaurantPriceType priceType) {
        this.priceType = priceType;
    }


    @Override
    public int getAdditionalPrices(Order order) {
        return getDeliveryPrice();
    }

    @Override
    public String toString() {
        String parentString = super.toString().substring(0, super.toString().lastIndexOf("}"));
        return parentString +
                ", priceType=" + priceType +
                '}';    }
}
