package ir.ac.kntu.menu;

import ir.ac.kntu.models.*;
import ir.ac.kntu.utils.ScannerWrapper;

import java.util.Arrays;
import java.util.HashSet;
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

    public Time getTime() {
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
        return new Time(hour, minute);
    }

    public Set<Day> getDays() {
        Day.printOptions();
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
        Time startTime = getTime();
        if (startTime == null) {
            return null;
        }
        System.out.println("Enter end time : ");
        Time endTime = getTime();
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

    public RestaurantPriceType getRestaurantPriceType() {
        RestaurantPriceType.printOptions();
        System.out.println("Enter restaurant price type : ");
        return getOption(RestaurantPriceType.class);
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

    public Integer getCount(int min){
        System.out.println("Enter count :");
        int count = Integer.parseInt(ScannerWrapper.nextLine());
        if (count>= min) {
            return count;
        }
        return null;
    }
}
