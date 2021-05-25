package ir.ac.kntu.menu.restaurant;

import ir.ac.kntu.db.CouriersDB;
import ir.ac.kntu.menu.Menu;
import ir.ac.kntu.models.*;
import ir.ac.kntu.utils.ScannerWrapper;

public class RestaurantCourierMenu extends Menu {

    private final CouriersDB couriersDB;

    private final Restaurant restaurant;

    public RestaurantCourierMenu(CouriersDB couriersDB, Restaurant restaurant) {
        this.couriersDB = couriersDB;
        this.restaurant = restaurant;
    }

    @Override
    public void show() {
        RestaurantCourierOption restaurantCourierOption = printMenuOption();
        while (restaurantCourierOption != RestaurantCourierOption.BACK) {
            if (restaurantCourierOption != null) {
                switch (restaurantCourierOption) {
                    case HIRE:
                        hireCourier();
                        break;
                    case UPDATE_JOB_INFO:
                        updateCourierJobInfo();
                        break;
                    case DISMISS:
                        dismissCourier();
                        break;
                    case FIND_BY_PHONE_NUMBER:
                        findByPhoneNumber();
                        break;
                    case SHOW_ALL:
                        showAll();
                        break;
                    default:
                        break;
                }
            }
            restaurantCourierOption = printMenuOption();
        }
    }

    private RestaurantCourierOption printMenuOption() {
        System.out.println("----------Restaurant Courier Menu----------");
        RestaurantCourierOption.printOptions();
        System.out.print("Enter your choice : ");
        return getOption(RestaurantCourierOption.class);
    }

    private void hireCourier() {
        String phoneNumber = getPhoneNumber();
        Courier courier = couriersDB.getCourierByPhoneNumber(phoneNumber);
        if (courier == null) {
            System.out.println("Courier hasn't registered");
            return;
        }
        if (restaurant.getHiredCouriers().containsCourier(courier)) {
            System.out.println("Courier is already hired");
            return;
        }
        CourierJobInfo courierJobInfo = getCourierJobInfo();
        if (courierJobInfo == null) {
            return;
        }
        boolean isHired = restaurant.hireCourier(courier, courierJobInfo);
        if (isHired) {
            System.out.println("Courier is hired");
        } else {
            System.out.println("Courier couldn't be hired");
        }
    }

    private void updateCourierJobInfo() {
        String phoneNumber = getPhoneNumber();
        Courier courier = restaurant.getHiredCouriers().getCourierByPhoneNumber(phoneNumber);
        if (courier == null) {
            System.out.println("Courier isn't hired");
            return;
        }
        CourierJobInfo newCourierJobInfo = getCourierJobInfo();
        if (newCourierJobInfo == null) {
            return;
        }
        boolean isUpdated = courier.updateJobInfo(newCourierJobInfo);
        if (isUpdated) {
            System.out.println("Courier job info is updated");
        } else {
            System.out.println("Failure updating courier job info");
        }
    }

    private void dismissCourier() {
        String phoneNumber = getPhoneNumber();
        Courier courier = restaurant.getHiredCouriers().getCourierByPhoneNumber(phoneNumber);
        if (courier == null) {
            System.out.println("Courier isn't hired");
            return;
        }
        boolean isDismissed = restaurant.dismissCourier(phoneNumber);
        if (isDismissed) {
            System.out.println("Courier is dismissed");
        } else {
            System.out.println("Couldn't dismiss courier");
        }
    }

    private void findByPhoneNumber() {
        String phoneNumber = getPhoneNumber();
        Courier courier = restaurant.getHiredCouriers().getCourierByPhoneNumber(phoneNumber);
        if (courier == null) {
            System.out.println("Courier isn't hired");
            return;
        }
        System.out.println(courier);
    }

    private void showAll() {
        restaurant.getHiredCouriers().printAllCouriers();
    }

    private CourierJobInfo getCourierJobInfo() {
        Schedule schedule = getSchedule();
        if (schedule == null) {
            return null;
        }
        Salary salary = getSalaryInfo();
        if (salary == null) {
            return null;
        }
        return new CourierJobInfo(restaurant, schedule, salary);
    }

    private Salary getSalaryInfo() {
        SalaryType.printOptions();
        System.out.println("Choose salary type :");
        SalaryType salaryType = getOption(SalaryType.class);
        if (salaryType == null) {
            return null;
        }
        System.out.println("Enter amount :");
        int amount = Integer.parseInt(ScannerWrapper.nextLine());
        if (amount < 0) {
            System.out.println("Invalid amount");
            return null;
        }
        return new Salary(salaryType, amount);
    }
}
