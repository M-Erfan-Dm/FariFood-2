package ir.ac.kntu.menu.courier;

import ir.ac.kntu.db.CouriersDB;
import ir.ac.kntu.db.RestaurantsDB;
import ir.ac.kntu.menu.Menu;
import ir.ac.kntu.models.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CouriersMenu extends Menu {


    private final CouriersDB couriersDB;

    private final ShopsDBReference shopsDBReference;

    public CouriersMenu(CouriersDB couriersDB, ShopsDBReference shopsDBReference) {
        this.couriersDB = couriersDB;
        this.shopsDBReference = shopsDBReference;
    }

    @Override
    public void show() {
        CouriersOption couriersOption = printMenuOptions();
        while (couriersOption != CouriersOption.BACK) {
            if (couriersOption != null) {
                switch (couriersOption) {
                    case REGISTER:
                        registerCourier();
                        break;
                    case UPDATE:
                        updateCourier();
                        break;
                    case FIND_BY_PHONE_NUMBER:
                        findByPhoneNumber();
                        break;
                    case SHOW_ALL:
                        showAll();
                        break;
                    case REMOVE:
                        removeCourier();
                        break;
                    default:
                        break;
                }

            }
            couriersOption = printMenuOptions();
        }
    }


    private CouriersOption printMenuOptions() {
        System.out.println("----------Couriers Menu----------");
        CouriersOption.printOptions();
        System.out.print("Enter your choice : ");
        return getOption(CouriersOption.class);
    }

    private void registerCourier() {
        Courier courier = getCourierInfo();
        if (courier == null) {
            return;
        }
        if (couriersDB.containsCourier(courier)) {
            System.out.println("Courier with this phone number already exists!");
            return;
        }
        couriersDB.addCourier(courier);
        System.out.println("Courier is registered");
    }

    private void updateCourier() {
        Courier courier = getCourierInfo();
        if (courier == null) {
            return;
        }
        if (!couriersDB.containsCourier(courier)) {
            System.out.println("Courier not found");
            return;
        }
        couriersDB.addCourier(courier);
        System.out.println("Courier is updated");
    }

    private void findByPhoneNumber() {
        String phoneNumber = getPhoneNumber();
        Courier courier = couriersDB.getCourierByPhoneNumber(phoneNumber);
        if (courier == null) {
            System.out.println("Courier not found");
            return;
        }
        CourierInfoOption.printOptions();
        System.out.println("Enter your choice :");
        CourierInfoOption courierInfoOption = getOption(CourierInfoOption.class);
        if (courierInfoOption == null) {
            return;
        }
        switch (courierInfoOption) {
            case GENERAL:
                showGeneralInfoOfCourier(courier);
                break;
            case FEEDBACKS:
                showFeedbacksOfCourier(courier);
                break;
            case ORDERS_HISTORY:
                showOrdersHistoryOfCourier(courier);
                break;
            default:
                break;
        }

    }


    private void showAll() {
        printList(new ArrayList<>(couriersDB.getCouriers()),"couriers");
    }

    private void removeCourier() {
        String phoneNumber = getPhoneNumber();
        Courier courier = couriersDB.getCourierByPhoneNumber(phoneNumber);
        if (courier == null) {
            System.out.println("Courier not found");
            return;
        }
        for (CourierJobInfo courierJobInfo : courier.getJobsInfo()) {
            if (courierJobInfo != null) {
                courierJobInfo.getRestaurant().dismissCourier(phoneNumber);
            }
        }
        couriersDB.removeCourier(courier);
        System.out.println("Courier is removed");
    }

    private Courier getCourierInfo() {
        String phoneNumber = getPhoneNumber();
        String name = getName();
        VehicleType.printOptions();
        System.out.println("Enter your vehicle type :");
        VehicleType vehicleType = getOption(VehicleType.class);
        if (vehicleType == null) {
            return null;
        }
        return new Courier(phoneNumber, name, vehicleType);
    }

    private void showGeneralInfoOfCourier(Courier courier) {
        System.out.println("General info of courier : " + courier);
    }

    private void showFeedbacksOfCourier(Courier courier) {
        OrdersService<Order> ordersService = new OrdersService<>(couriersDB.
                getOrdersOfCourier(courier.getPhoneNumber(), shopsDBReference));
        List<Feedback> feedbacks = ordersService.getAllFeedbacks();
        printList(feedbacks,"feedbacks");
    }

    private void showOrdersHistoryOfCourier(Courier courier) {
        List<Order> orders = new ArrayList<>(couriersDB.getOrdersOfCourier(courier.getPhoneNumber(), shopsDBReference));
        printList(orders,"orders");
    }
}
