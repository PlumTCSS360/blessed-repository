package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedList;
import java.util.Scanner;

public class Budget {

    /** This is the total amount that you would like to spend on your Project. */
    private BigDecimal spendingLimit;

    /** This is the list of Expenses which you have. */
    private LinkedList<Expense> expenses;

    /** This is the path of the parent Project or Subproject. */
    private String parentFilePath;

    /**
     * This constructor creates a new Budget with a custom spendingLimit, which has acquired no Expenses yet.
     * @author Devin Peevy
     * @param spendingLimit The total amount which you wish to spend on this item.
     */
    public Budget(String parentFilePath, BigDecimal spendingLimit) {
        this.spendingLimit = spendingLimit.setScale(2, RoundingMode.HALF_UP);
        this.expenses = new LinkedList<>();
        this.parentFilePath = parentFilePath;
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
     * @param expense The expense which should be removed.
     * @throws IllegalArgumentException if the expense does not exist.
     * @return The deleted expense.
     */
    public Expense removeExpense(Expense expense) {
        boolean exists = false;
        for (Expense e : expenses) {
            if (e.equals(expense)) {
                exists = true;
                expenses.remove(e);
                break;
            }
        }
        if (!exists) {
            throw new IllegalArgumentException("This expense exists nowhere in this Budget!");
        }
        return expense;
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
     * This method will write a Budget to a txt file.
     * @author Devin Peevy
     */
    public void writeToTXT() {
        try {
            System.out.println(getFilePath());
            FileWriter fw = new FileWriter(getFilePath());
            fw.write(toTXT(this));
            fw.close();
        } catch (IOException ioe){
            throw new IllegalArgumentException("Path exists as a folder.");
        }

    }

    /**
     * This private method is used by writeToTXT in order to turn a Budget into a String.
     * @author Devin Peevy
     * @param budget The budget which is being transformed.
     * @return The String which can be written interpreted as a Budget
     */
    private static String toTXT(Budget budget) {
        StringBuilder sb = new StringBuilder(100);
        sb.append(budget.getFilePath()).append("\n");
        sb.append("spending limit").append(":").append(budget.spendingLimit).append("\n");
        for (Expense e : budget.expenses) {
            sb.append(e.getName());
            sb.append(",");
            sb.append(e.getCost());
            sb.append(",");
            sb.append(e.isChecked());
            sb.append("\n");
        }
        sb.append("end");
        return sb.toString();
    }

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
            String theProjectName = s.nextLine();
            String theSL = s.nextLine();
            theSL = theSL.substring(15, theSL.length()-1);
            BigDecimal theSpendingLimit = BigDecimal.valueOf(Double.parseDouble(theSL)).setScale(2, RoundingMode.HALF_UP);
            theBudget = new Budget(theProjectName, theSpendingLimit);
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
        }
        catch (FileNotFoundException e) {
            System.out.println("Invalid file path!");
        }
        return theBudget;
    }

    public static void main(String[] args) {
        Budget b = new Budget("data/sample", BigDecimal.valueOf(1200.3294));
        b.addExpense("1", BigDecimal.valueOf(100.75349), true);
        b.addExpense("2", BigDecimal.valueOf(459.3), false);
        b.writeToTXT();
        Budget c = Budget.loadBudgetFromTXT(b.getFilePath());
        System.out.println(c.getFilePath());
        System.out.println(c.spendingLimit);
        System.out.println(c.expenses);
        System.out.println(b.getFilePath());
        System.out.println(b.spendingLimit);
        System.out.println(b.expenses);
        System.out.println(c.getRemainingAmount());
    }

    /**
     * @author Devin Peevy
     * @return the file path to get to this budget.txt file.
     */
    public String getFilePath() {
        return parentFilePath + "/budget.txt";
    }
}
