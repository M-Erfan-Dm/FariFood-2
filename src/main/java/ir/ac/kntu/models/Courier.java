package ir.ac.kntu.models;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Objects;

public class Courier {
    private String phoneNumber;

    private String name;

    private VehicleType vehicleType;

    private final CourierJobInfo[] jobsInfo = new CourierJobInfo[2];

    public Courier(String phoneNumber, String name, VehicleType vehicleType) {
        this.phoneNumber = phoneNumber;
        this.name = name;
        this.vehicleType = vehicleType;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }

    public CourierJobInfo[] getJobsInfo() {
        return jobsInfo.clone();
    }

    public CourierJobInfo getJobInfoByRestaurantId(int id) {
        for (CourierJobInfo courierJobInfo : jobsInfo) {
            if (courierJobInfo.getRestaurant().getId() == id) {
                return courierJobInfo;
            }
        }
        return null;
    }

    public boolean updateJobInfo(CourierJobInfo newJobInfo) {
        CourierJobInfo jobInfo = getJobInfoByRestaurantId(newJobInfo.getRestaurant().getId());
        if (jobInfo == null) {
            return false;
        }
        jobInfo.setSchedule(newJobInfo.getSchedule());
        jobInfo.setSalary(newJobInfo.getSalary());
        return true;
    }


    public boolean addJob(CourierJobInfo newJob) {
        for (int i = 0; i < jobsInfo.length; i++) {
            if (jobsInfo[i] == null) {
                jobsInfo[i] = newJob;
                return true;
            }
        }
        return false;
    }

    public void quitJob(int restaurantId) {
        for (int i = 0; i < jobsInfo.length; i++) {
            CourierJobInfo courierJobInfo = jobsInfo[i];
            if (courierJobInfo != null && courierJobInfo.getRestaurant().getId() == restaurantId) {
                jobsInfo[i] = null;
                break;
            }
        }
    }

    public boolean isAvailable(int restaurantId) {
        CourierJobInfo jobInfo = getJobInfoByRestaurantId(restaurantId);
        if (jobInfo == null) {
            return false;
        }
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        return jobInfo.getSchedule().isTimeInInterval(LocalTime.of(hour,minute));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Courier courier = (Courier) o;
        return phoneNumber.equals(courier.phoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(phoneNumber);
    }

    @Override
    public String toString() {
        return "{phoneNumber='" + phoneNumber + '\'' +
                ", name='" + name + '\'' +
                ", vehicleType=" + vehicleType +
                ", courierWorksInfo=" + Arrays.toString(jobsInfo) + "}";
    }
}
