package ir.ac.kntu.menu.shop;

import ir.ac.kntu.db.CouriersDB;
import ir.ac.kntu.menu.Menu;
import ir.ac.kntu.models.*;
import ir.ac.kntu.utils.ScannerWrapper;

import java.util.ArrayList;

public class ShopCourierMenu extends Menu {

    private final CouriersDB couriersDB;

    private final Shop<? extends Order,? extends OrdersService<? extends Order>> shop;

    public <T extends Order> ShopCourierMenu(CouriersDB couriersDB, Shop<T, ? extends OrdersService<T>> shop) {
        this.couriersDB = couriersDB;
        this.shop = shop;
    }

    @Override
    public void show() {
        ShopCourierOption option;
        while ((option = printMenuOptions("Shop Courier Menu",ShopCourierOption.class))
                != ShopCourierOption.BACK) {
            if (option != null) {
                switch (option) {
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
        }
    }

    private void hireCourier() {
        String phoneNumber = getPhoneNumber();
        Courier courier = couriersDB.getCourierByPhoneNumber(phoneNumber);
        if (courier == null) {
            System.out.println("Courier hasn't registered");
            return;
        }
        if (shop.getHiredCouriers().containsCourier(courier)) {
            System.out.println("Courier is already hired");
            return;
        }
        CourierJobInfo courierJobInfo = getCourierJobInfo();
        if (courierJobInfo == null) {
            return;
        }
        boolean isHired = shop.hireCourier(courier, courierJobInfo);
        if (isHired) {
            System.out.println("Courier is hired");
        } else {
            System.out.println("Courier couldn't be hired");
        }
    }

    private void updateCourierJobInfo() {
        String phoneNumber = getPhoneNumber();
        Courier courier = shop.getHiredCouriers().getCourierByPhoneNumber(phoneNumber);
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
        Courier courier = shop.getHiredCouriers().getCourierByPhoneNumber(phoneNumber);
        if (courier == null) {
            System.out.println("Courier isn't hired");
            return;
        }
        boolean isDismissed = shop.dismissCourier(phoneNumber);
        if (isDismissed) {
            System.out.println("Courier is dismissed");
        } else {
            System.out.println("Couldn't dismiss courier");
        }
    }

    private void findByPhoneNumber() {
        String phoneNumber = getPhoneNumber();
        Courier courier = shop.getHiredCouriers().getCourierByPhoneNumber(phoneNumber);
        if (courier == null) {
            System.out.println("Courier isn't hired");
            return;
        }
        System.out.println(courier);
    }

    private void showAll() {
        printList(new ArrayList<>(shop.getHiredCouriers().getCouriers()),"couriers");
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
        return new CourierJobInfo(shop, schedule, salary);
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
