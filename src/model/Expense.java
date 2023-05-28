package model;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * An expense has a name and a cost, as well as a boolean whether it is active or not. It is used by the Budget class
 * to see where the money is going. An Expense is an immutable Object - to override one, you must simply construct a new
 * one over it.
 * @author Devin Peevy
 * @version 1.0
 */
public final class Expense {

    // INSTANCE FIELDS

    /** The name of the Expense. */
    final private String name;

    /** How much the Expense costs. */
    final private BigDecimal cost;

    /** Whether the expense should be counted when doing budget calculations. */
    final private boolean isChecked;

    // CONSTRUCTOR

    /**
     * This constructor creates a new Expense.
     * @author Devin Peevy
     * @param name The name of the expense. Should be a short description.
     * @param cost How much the Expense will cost.
     * @param isChecked Whether the Expense should be counted in the Budget.
     */
    public Expense(String name, BigDecimal cost, boolean isChecked) {
        this.name = name;
        this.cost = cost.setScale(2, RoundingMode.HALF_UP);
        this.isChecked = isChecked;
    }

    // GETTERS

    /**
     * @author Devin Peevy
     * @return the name of this Expense.
     */
    public String getName() {
        return this.name;
    }

    /**
     * @author Devin Peevy
     * @return the cost of this Expense.
     */
    public BigDecimal getCost() {
        return this.cost;
    }

    /**
     * @author Devin Peevy
     * @return true if this expense should be counted in the Budget, else false.
     */
    public boolean isChecked() {
        return this.isChecked;
    }

    // OTHER INSTANCE METHODS

    /**
     * @author Devin Peevy
     */
    @Override
    public String toString() {
        return name + " : " + cost;
    }
}
