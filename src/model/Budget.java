package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * A Budget object consists of a spending limit and a list of expenses. It is able to do calculations on itself, and
 * update its list of expenses and spending limit. It can also write itself to a .txt file, and has a static method for
 * creating a Budget from an existing .txt file (for persistence).
 * @author Devin Peevy
 * @version 1.0
 */
public final class Budget {

    // STATIC FIELDS

    public static final String FILE_NAME = "/budget.txt";

    // INSTANCE FIELDS

    /** This is the total amount that you would like to spend on your Project. */
    private BigDecimal spendingLimit;

    /** This is the list of Expenses which you have. */
    private final LinkedList<Expense> expenses;

    /** This is the path of the parent Project or Subproject. */
    final private String parentFilePath;

    // CONSTRUCTOR

    /**
     * This constructor creates a new Budget with a custom spendingLimit, which has acquired no Expenses yet.
     * @author Devin Peevy
     * @param spendingLimit The total amount which you wish to spend on this item.
     */
    public Budget(String parentFilePath, BigDecimal spendingLimit) {
        this.spendingLimit = spendingLimit.setScale(2, RoundingMode.HALF_UP);
        this.expenses = new LinkedList<>();
        this.parentFilePath = parentFilePath;
        writeToTXT();
    }

    // GETTERS

    /**
     * @author Devin Peevy
     * @return spendingLimit
     */
    public BigDecimal getSpendingLimit() {
        return spendingLimit;
    }

    /**
     * @author Devin Peevy
     * @return expenses
     */
    public LinkedList<Expense> getExpenses() {
        return expenses;
    }

    /**
     * @author Devin Peevy
     * @return the file path to get to this budget.txt file.
     */
    public String getFilePath() {
        return parentFilePath + "/budget.txt";
    }

    public LinkedList<Expense> getCheckedExpenses() {
        LinkedList<Expense> checkedExpenses = new LinkedList<>();
        for (Expense e : expenses) {
            if (e.isChecked()) {
                checkedExpenses.add(e);
            }
        }
        return checkedExpenses;
    }

    public LinkedList<Expense> getUncheckedExpenses() {
        LinkedList<Expense> uncheckedExpenses = new LinkedList<>();
        for (Expense e : expenses) {
            if (!e.isChecked()) {
                uncheckedExpenses.add(e);
            }
        }
        return uncheckedExpenses;
    }

    /**
     * This method will subtract the expenses which are checked from the spending limit to get the
     * amount of available funds.
     * @author Devin Peevy
     * @return The amount of available funds (A negative means your expenses exceed your spending limit).
     */
    public BigDecimal getRemainingAmount() {
        BigDecimal remaining = spendingLimit;
        for (Expense e : expenses) {
            if (e.isChecked()) {
                remaining = remaining.subtract(e.getCost());
            }
        }
        return remaining;
    }

    /**
     * @author Devin Peevy
     * @return The name of the Project OR Subproject to which this Budget belongs.
     */
    public String getProjectName() {
        final char[] array = parentFilePath.toCharArray();
        int lastSlash = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i] == '/') {
                lastSlash = i;
            }
        }
        return parentFilePath.substring(lastSlash + 1);
    }

    public String getParentFilePath() {
        return parentFilePath;
    }

    // SETTERS

    /**
     * @author Devin Peevy
     * @param spendingLimit the new spending limit.
     */
    public void setSpendingLimit(BigDecimal spendingLimit) {
        this.spendingLimit = spendingLimit.setScale(2, RoundingMode.HALF_UP);
    }

    // INSTANCE METHODS

    /**
     * This method updates an Expense within the Budget, replacing it with a new Expense.
     * @param expenseIndex The index in expenses which is being updated.
     * @param newExpense The replacement Expense.
     * @return the old Expense which has been replaced.
     */
    public Expense changeExpense(int expenseIndex, Expense newExpense) {
        if (expenseIndex > expenses.size() - 1 || expenseIndex < 0) {
            throw new IllegalArgumentException("This index does not found in this Budget's expenses.");
        }
        Expense oldExpense = expenses.remove(expenseIndex);
        expenses.add(expenseIndex, newExpense);
        return oldExpense;
    }

    /**
     * This method will add a new Expense to the list of expenses.
     * @author Devin Peevy
     * @param name The name of the new Expense.
     * @param cost The cost of the new Expense. Automatically changed to 2 decimal places.
     * @param isChecked Whether the expense should be counted when calculating remaining balance.
     */
    public void addExpense(String name, BigDecimal cost, boolean isChecked) {
        cost = cost.setScale(2, RoundingMode.HALF_UP);
        Expense e = new Expense(name, cost, isChecked);
        expenses.add(e);
    }

    /**
     * This method removes an expense from the budgets list of expenses.
     * @author Devin Peevy
     * @param index The index of the expense which should be removed.
     * @throws IllegalArgumentException if the expense does not exist.
     * @return The deleted expense.
     */
    public Expense removeExpense(int index) {
        if (index < 0 || index > expenses.size() - 1) {
            throw new IllegalArgumentException("This index position does not exist in this Budget's expenses.");
        }
        return expenses.remove(index);
    }

    /**
     * This method will write a Budget to a txt file located at its filePath
     * @author Devin Peevy
     */
    public void writeToTXT() {
        try {
            FileWriter fw = new FileWriter(getFilePath());
            fw.write(toTXT(this));
            fw.close();
        } catch (IOException ioe){
            throw new IllegalArgumentException("Invalid Parent File Path. Project/Subproject does not exist.");
        }

    }

    // STATIC METHODS

    /**
     * This method is used to create a new Budget object from an existing txt file.
     * @author Devin Peevy
     * @param filePath The location of the budget.txt file in the directory.
     * @return The Budget object created from the .txt file.
     */
    public static Budget loadBudgetFromTXT(String filePath) {
        Budget theBudget = null;
        try {
            Scanner s = new Scanner(new File(filePath));
            //First line is the project name.
            String theFilePath = s.nextLine();
            String theSL = s.nextLine();
            theSL = theSL.substring(15, theSL.length()-1);
            BigDecimal theSpendingLimit = BigDecimal.valueOf(Double.parseDouble(theSL)).setScale(2, RoundingMode.HALF_UP);
            theBudget = new Budget(theFilePath, theSpendingLimit);
            String thisExpense = s.nextLine();
            while (!thisExpense.equals("end")) {
                Scanner q = new Scanner(thisExpense);
                q.useDelimiter(",");
                String theName = q.next();
                BigDecimal theCost = BigDecimal.valueOf(Double.parseDouble(q.next()));
                theCost = theCost.setScale(2, RoundingMode.HALF_UP);
                boolean theChecked = Boolean.parseBoolean(q.next());
                theBudget.addExpense(theName, theCost, theChecked);
                thisExpense = s.nextLine();
            }
            s.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("Invalid file path!");
        }
        return theBudget;
    }

    // PRIVATE METHODS

    /**
     * This private method is used by writeToTXT in order to turn a Budget into a String.
     * @author Devin Peevy
     * @param budget The budget which is being transformed.
     * @return The String which can be written interpreted as a Budget
     */
    private static String toTXT(Budget budget) {

        /*
        A budget.txt File is written in the following format:

        <parentFilePath>
        spending limit:<spendingLimit>
        <expense1Name,expense1Cost,expense1IsChecked>
        ...
        <expenseNName,expenseNCost,expenseNIsChecked>
        end
         */

        StringBuilder sb = new StringBuilder(100);
        // Write the parent file path
        sb.append(budget.parentFilePath).append("\n");
        // Write the spending limit
        sb.append("spending limit").append(":").append(budget.spendingLimit).append("\n");
        // Write each of the expenses.
        for (Expense e : budget.expenses) {
            sb.append(e.getName());
            sb.append(",");
            sb.append(e.getCost());
            sb.append(",");
            sb.append(e.isChecked());
            sb.append("\n");
        }
        // End the document.
        sb.append("end");
        return sb.toString();
    }

}
