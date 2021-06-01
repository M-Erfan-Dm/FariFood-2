package ir.ac.kntu.db;

import ir.ac.kntu.models.Owner;

import java.util.Objects;
import java.util.Set;

public class OwnersDB {
    private Set<Owner> owners;

    public OwnersDB(Set<Owner> owners) {
        this.owners = owners;
    }

    public void setOwners(Set<Owner> owners) {
        this.owners = owners;
    }

    public void addOwner(Owner owner) {
        removeOwner(owner);
        owners.add(owner);
    }

    public boolean removeOwner(Owner owner) {
        return owners.remove(owner);
    }

    public boolean isOwnerValid(String phoneNumber, String password) {
        return owners.stream().anyMatch(existingOwner -> existingOwner.getPhoneNumber().equals(phoneNumber)
                && existingOwner.getPassword().equals(password));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        OwnersDB ownersDB = (OwnersDB) o;
        return owners.equals(ownersDB.owners);
    }

    @Override
    public int hashCode() {
        return Objects.hash(owners);
    }

    @Override
    public String toString() {
        return "{" +
                "owners=" + owners +
                '}';
    }
}

