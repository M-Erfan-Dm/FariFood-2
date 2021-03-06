package ir.ac.kntu.menu;

import ir.ac.kntu.db.OwnersDB;
import ir.ac.kntu.models.*;
import ir.ac.kntu.utils.ItemPrinter;
import ir.ac.kntu.utils.ListPagePrinting;
import ir.ac.kntu.utils.ScannerWrapper;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class Menu {

    public abstract void show();

    public <T extends Enum<T>> T getOption(Class<T> tEnum) {
        int choice = Integer.parseInt(ScannerWrapper.nextLine()) - 1;
        T[] options = tEnum.getEnumConstants();
        if (choice >= 0 && choice < options.length) {
            return options[choice];
        }
        System.out.println("Wrong choice!");
        return null;
    }

    public int getId() {
        System.out.println("Enter id : ");
        return Integer.parseInt(ScannerWrapper.nextLine());
    }

    public String getUsername() {
        System.out.println("Enter your username : ");
        return ScannerWrapper.nextLine();
    }

    public String getPassword() {
        System.out.println("Enter your password : ");
        return ScannerWrapper.nextLine();
    }

    public String getPhoneNumber() {
        System.out.println("Enter your phone number : ");
        return ScannerWrapper.nextLine();
    }

    public String getName() {
        System.out.println("Enter name : ");
        return ScannerWrapper.nextLine();
    }

    public String getAddress() {
        System.out.println("Enter address : ");
        return ScannerWrapper.nextLine();
    }

    public Integer getPrice() {
        System.out.println("Enter price : ");
        int price = Integer.parseInt(ScannerWrapper.nextLine());
        if (price < 0) {
            System.out.println("Invalid price");
            return null;
        }
        return price;
    }

    public LocalTime getTime() {
        System.out.print("Enter hour : ");
        int hour = Integer.parseInt(ScannerWrapper.nextLine());
        if (hour < 0 || hour > 23) {
            System.out.println("Wrong hour!");
            return null;
        }
        System.out.print("Enter minute : ");
        int minute = Integer.parseInt(ScannerWrapper.nextLine());
        if (minute < 0 || minute > 59) {
            System.out.println("Wrong minute!");
            return null;
        }
        return LocalTime.of(hour, minute);
    }

    public Set<Day> getDays() {
        printEnumOptions(Day.class);
        System.out.println("Enter the days (or 0 to stop) :");
        Set<Day> days = new HashSet<>();
        int day = Integer.parseInt(ScannerWrapper.nextLine()) - 1;
        Day[] daysNames = Day.values();
        while (day >= 0 && day < 8) {
            if (day == 7) {
                days.addAll(Arrays.asList(daysNames).subList(0, 7));
                break;
            }
            days.add(daysNames[day]);
            if (days.size() == 7) {
                break;
            }
            day = Integer.parseInt(ScannerWrapper.nextLine()) - 1;
        }
        return days;
    }

    public Schedule getSchedule() {
        System.out.println("Enter start time : ");
        LocalTime startTime = getTime();
        if (startTime == null) {
            return null;
        }
        System.out.println("Enter end time : ");
        LocalTime endTime = getTime();
        if (endTime == null) {
            return null;
        }
        if (startTime.isAfter(endTime)) {
            System.out.println("start time is after end time!");
            return null;
        }
        System.out.println("Working days :");
        Set<Day> days = getDays();
        if (days.size() == 0) {
            System.out.println("No days entered!");
            return null;
        }
        return new Schedule(startTime, endTime, days);
    }

    public ShopPriceType getShopPriceType() {
        printEnumOptions(ShopPriceType.class);
        System.out.println("Enter restaurant price type : ");
        return getOption(ShopPriceType.class);
    }


    public Feedback getFeedback() {
        System.out.println("Enter rating :");
        Rating.printOptions();
        Rating rating = getOption(Rating.class);
        if (rating == null) {
            rating = Rating.FIVE;
        }
        System.out.println("Enter comment :");
        String comment = ScannerWrapper.nextLine();
        return new Feedback(rating, comment);
    }

    public Integer getCount(int min) {
        System.out.println("Enter count :");
        int count = Integer.parseInt(ScannerWrapper.nextLine());
        if (count >= min) {
            return count;
        }
        return null;
    }

    private Owner getOwner(OwnersDB ownersDB) {
        System.out.println("Owner :");
        String phoneNumber = getPhoneNumber();
        Owner owner = ownersDB.getOwnerByPhoneNumber(phoneNumber);
        if (owner == null) {
            System.out.println("Owner not found");
            return null;
        }
        return owner;
    }

    public Restaurant getRestaurantGeneralInfo(OwnersDB ownersDB) {
        Owner owner = getOwner(ownersDB);
        if (owner == null) {
            return null;
        }
        String name = getName();
        String address = getAddress();
        Schedule schedule = getSchedule();
        if (schedule == null) {
            return null;
        }
        ShopPriceType restaurantPriceType = getShopPriceType();
        if (restaurantPriceType == null) {
            return null;
        }
        System.out.println("Delivery price :");
        Integer deliveryPrice = getPrice();
        if (deliveryPrice == null) {
            return null;
        }
        return new Restaurant(owner, name, address, schedule, restaurantPriceType, deliveryPrice);
    }

    public Supermarket getSupermarketGeneralInfo(OwnersDB ownersDB) {
        Owner owner = getOwner(ownersDB);
        if (owner == null) {
            return null;
        }
        String name = getName();
        String address = getAddress();
        Schedule schedule = getSchedule();
        if (schedule == null) {
            return null;
        }
        ShopPriceType priceType = getShopPriceType();
        if (priceType == null) {
            return null;
        }
        System.out.println("Delivery price :");
        Integer deliveryPrice = getPrice();
        if (deliveryPrice == null) {
            return null;
        }
        System.out.println("Period base price :");
        Integer periodBasePrice = getPrice();
        if (periodBasePrice == null) {
            return null;
        }
        return new Supermarket(owner, name, address, schedule, deliveryPrice, periodBasePrice, priceType);
    }

    public FruitShop getFruitShopGeneralInfo(OwnersDB ownersDB) {
        Owner owner = getOwner(ownersDB);
        if (owner == null) {
            return null;
        }
        String name = getName();
        String address = getAddress();
        Schedule schedule = getSchedule();
        if (schedule == null) {
            return null;
        }
        ShopPriceType priceType = getShopPriceType();
        if (priceType == null) {
            return null;
        }
        System.out.println("Delivery price :");
        Integer deliveryPrice = getPrice();
        if (deliveryPrice == null) {
            return null;
        }
        System.out.println("Period base price :");
        Integer periodBasePrice = getPrice();
        if (periodBasePrice == null) {
            return null;
        }
        System.out.println("Fruit KG limit :");
        Integer fruitLimit = getFruitKGLimit();
        if (fruitLimit == null) {
            return null;
        }
        return new FruitShop(owner, name, address, schedule, deliveryPrice, periodBasePrice, fruitLimit, priceType);
    }

    private Integer getFruitKGLimit() {
        int limit = Integer.parseInt(ScannerWrapper.nextLine());
        if (limit <= 0) {
            System.out.println("Invalid limit");
            return null;
        }
        return limit;
    }

    public <T extends Enum<T>> T printMenuOptions(String title, Class<T> tEnum) {
        System.out.println("----------" + title + "----------");
        printEnumOptions(tEnum);
        System.out.print("Enter your choice : ");
        return getOption(tEnum);
    }

    public <T extends Enum<T>> void printEnumOptions(Class<T> tEnum) {
        T[] constants = tEnum.getEnumConstants();
        for (int i = 0; i < constants.length; i++) {
            String constant = constants[i].name();
            String capitalizedConstant = constant.substring(0, 1).toUpperCase() + constant.substring(1).toLowerCase();
            capitalizedConstant = capitalizedConstant.replace("_", " ");
            System.out.println((i + 1) + "." + capitalizedConstant);
        }
    }

    public <T> void printList(List<T> list, String objectsName) {
        printList(list, objectsName, (t, count) -> "No." + count + " " + t);
    }

    public <T> void printList(List<T> list, String objectsName, ItemPrinter<T> printer) {
        System.out.println("---" + list.size() + " " + objectsName + " found---");
        ListPagePrinting.printList(list, printer);
    }
}
