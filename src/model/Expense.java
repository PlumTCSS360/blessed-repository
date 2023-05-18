package model;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Expense {

    private String name;

    private BigDecimal cost;

    private boolean isChecked;

    public Expense(String name, BigDecimal cost, boolean isChecked) {
        this.name = name;
        this.cost = cost.setScale(2, RoundingMode.HALF_UP);
        this.isChecked = isChecked;
    }

    public void setName(String newName) {
        name = newName;
    }

    public void setCost(BigDecimal newCost) {
        this.cost = newCost.setScale(2, RoundingMode.HALF_UP);
    }

    public String getName() {
        return this.name;
    }

    public BigDecimal getCost() {
        return this.cost;
    }

    public boolean isChecked() {
        return this.isChecked;
    }

    public void setChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }

    public String toString() {
        return name + " : " + cost;
    }
}
