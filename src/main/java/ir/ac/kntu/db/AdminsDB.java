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
        return admins.add(admin);
    }

    public boolean removeAdmin(Admin admin) {
        return admins.remove(admin);
    }

    public boolean containsUsername(String username) {
        return admins.contains(new Admin(username, ""));
    }

    public boolean isAdminValid(Admin admin) {
        return admins.stream().anyMatch(existingAdmin -> existingAdmin.getPhoneNumber()
                .equals(admin.getPhoneNumber()) && existingAdmin.getPassword().equals(admin.getPassword()));
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
