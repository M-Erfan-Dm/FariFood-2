package ir.ac.kntu.models;

import java.util.Objects;

public class Admin {
    private String phoneNumber;

    private String password;

    private Settings settings;

    public Admin(String phoneNumber, String password, Settings settings) {
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.settings = settings;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Settings getSettings() {
        return settings;
    }

    public void setSettings(Settings settings) {
        this.settings = settings;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Admin admin = (Admin) o;
        return phoneNumber.equals(admin.phoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(phoneNumber);
    }

    @Override
    public String toString() {
        return "{username='" + phoneNumber + '\'' +
                ", password='" + password + '\'' + "}";
    }
}
