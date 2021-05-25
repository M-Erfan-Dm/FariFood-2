package ir.ac.kntu.models;

import java.util.Objects;

public class Salary {
    private SalaryType salaryType;

    private int amount;

    public Salary(SalaryType salaryType, int amount) {
        this.salaryType = salaryType;
        this.amount = amount;
    }

    public SalaryType getSalaryType() {
        return salaryType;
    }

    public void setSalaryType(SalaryType salaryType) {
        this.salaryType = salaryType;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Salary salary = (Salary) o;
        return amount == salary.amount && salaryType == salary.salaryType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(salaryType, amount);
    }

    @Override
    public String toString() {
        return "{salaryType=" + salaryType +
                ", amount=" + amount + "}";
    }
}
