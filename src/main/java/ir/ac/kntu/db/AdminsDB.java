package ir.ac.kntu.db;

import ir.ac.kntu.models.Admin;

import java.util.Objects;
import java.util.Set;

public class AdminsDB {

    private Set<Admin> admins;

    public AdminsDB(Set<Admin> admins) {
        this.admins = admins;
    }

    public void setAdmins(Set<Admin> admins) {
        this.admins = admins;
    }

    public boolean addAdmin(Admin admin) {
        removeAdmin(admin);
        return admins.add(admin);
    }

    public boolean removeAdmin(Admin admin) {
        return admins.remove(admin);
    }

    public boolean containsAdmin(Admin admin) {
        return admins.contains(admin);
    }

    public boolean isAdminValid(String phoneNumber, String password) {
        return admins.stream().anyMatch(admin -> admin.getPhoneNumber()
                .equals(phoneNumber) && admin.getPassword().equals(password));
    }

    public Admin getAdminByPhoneNumber(String phoneNumber){
        return admins.stream().filter(admin -> admin.getPhoneNumber().equals(phoneNumber))
                .findFirst().orElse(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AdminsDB adminsDB = (AdminsDB) o;
        return admins.equals(adminsDB.admins);
    }

    @Override
    public int hashCode() {
        return Objects.hash(admins);
    }

    @Override
    public String toString() {
        return "{admins=" + admins + "}";
    }
}
