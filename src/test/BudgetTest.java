package test;

import model.Budget;
import model.Expense;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class BudgetTest {

    @BeforeEach
    void setUp() {
        BigDecimal spendingLimit = new BigDecimal("1000.50");
        String filePath = "This is the file path";
        Budget budgetTest = new Budget(filePath, spendingLimit);
        assertEquals(new BigDecimal("1000.50").setScale(2, RoundingMode.HALF_UP), budgetTest.getSpendingLimit());
        assertEquals("This is the file path"+ "/budget.txt", budgetTest.getFilePath());
    }

    @Test
    void setSpendingLimit() {
        BigDecimal spendingLimit = new BigDecimal("1000.50");
        String filePath = "This is the file path";
        Budget budgetTest = new Budget(filePath, spendingLimit);
        budgetTest.setSpendingLimit(new BigDecimal("5000.50"));
        assertEquals(new BigDecimal("5000.50").setScale(2, RoundingMode.HALF_UP), budgetTest.getSpendingLimit());
    }

    @Test
    void getSpendingLimit() {
        BigDecimal spendingLimit = new BigDecimal("1000.50");
        String filePath = "This is the file path";
        Budget budgetTest = new Budget(filePath, spendingLimit);
        assertEquals(new BigDecimal("1000.50").setScale(2, RoundingMode.HALF_UP), budgetTest.getSpendingLimit());
    }

    @Test
    void getExpenses() {
        BigDecimal spendingLimit = new BigDecimal("1000.50");
        String filePath = "This is the file path";
        Budget budgetTest = new Budget(filePath, spendingLimit);
        LinkedList<Expense> expenses = new LinkedList<Expense>();
        Expense first = new Expense("first", new BigDecimal(100.10), true);
        Expense second = new Expense("second", new BigDecimal(200.20), false);
        Expense third = new Expense("third", new BigDecimal(300.30), true);
        expenses.add(first);
        expenses.add(second);
        expenses.add(third);
        budgetTest.addExpense("first", new BigDecimal(100.10), true);
        budgetTest.addExpense("second", new BigDecimal(200.20), false);
        budgetTest.addExpense("third", new BigDecimal(300.30), true);
        assertEquals(expenses.get(0).getName(), budgetTest.getExpenses().get(0).getName());
        assertEquals(expenses.get(1).getName(), budgetTest.getExpenses().get(1).getName());
        assertEquals(expenses.get(2).getName(), budgetTest.getExpenses().get(2).getName());
        assertEquals(expenses.get(0).getCost(), budgetTest.getExpenses().get(0).getCost());
        assertEquals(expenses.get(1).getCost(), budgetTest.getExpenses().get(1).getCost());
        assertEquals(expenses.get(2).getCost(), budgetTest.getExpenses().get(2).getCost());
        assertEquals(expenses.get(0).isChecked(), budgetTest.getExpenses().get(0).isChecked());
        assertEquals(expenses.get(1).isChecked(), budgetTest.getExpenses().get(1).isChecked());
        assertEquals(expenses.get(2).isChecked(), budgetTest.getExpenses().get(2).isChecked());

    }

    @Test
    void changeExpense() {
        BigDecimal spendingLimit = new BigDecimal("1000.50");
        String filePath = "This is the file path";
        Budget budgetTest = new Budget(filePath, spendingLimit);
        LinkedList<Expense> expenses = new LinkedList<Expense>();
        Expense first = new Expense("first", new BigDecimal(100.10), true);
        Expense second = new Expense("second", new BigDecimal(200.20), false);
        Expense third = new Expense("third", new BigDecimal(300.30), true);
        Expense forth = new Expense("forth", new BigDecimal(400.40), true);

        budgetTest.addExpense("first", new BigDecimal(100.10), true);
        budgetTest.addExpense("second", new BigDecimal(200.20), false);
        budgetTest.addExpense("third", new BigDecimal(300.30), true);

        assertThrows(IllegalArgumentException.class, () -> {
            budgetTest.changeExpense(-1, forth);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            budgetTest.changeExpense(4, forth);
        });

        budgetTest.changeExpense(2, forth);
        expenses.add(first);
        expenses.add(second);
        expenses.add(forth);

        budgetTest.addExpense("first", new BigDecimal(100.10), true);
        budgetTest.addExpense("second", new BigDecimal(200.20), false);
        budgetTest.addExpense("third", new BigDecimal(300.30), true);
        assertEquals(expenses.get(0).getName(), budgetTest.getExpenses().get(0).getName());
        assertEquals(expenses.get(1).getName(), budgetTest.getExpenses().get(1).getName());
        assertEquals(expenses.get(2).getName(), budgetTest.getExpenses().get(2).getName());
        assertEquals(expenses.get(0).getCost(), budgetTest.getExpenses().get(0).getCost());
        assertEquals(expenses.get(1).getCost(), budgetTest.getExpenses().get(1).getCost());
        assertEquals(expenses.get(2).getCost(), budgetTest.getExpenses().get(2).getCost());
        assertEquals(expenses.get(0).isChecked(), budgetTest.getExpenses().get(0).isChecked());
        assertEquals(expenses.get(1).isChecked(), budgetTest.getExpenses().get(1).isChecked());
        assertEquals(expenses.get(2).isChecked(), budgetTest.getExpenses().get(2).isChecked());
    }

    @Test
    void addExpense() {
        BigDecimal spendingLimit = new BigDecimal("1000.50");
        String filePath = "This is the file path";
        Budget budgetTest = new Budget(filePath, spendingLimit);
        LinkedList<Expense> expenses = new LinkedList<Expense>();
        Expense first = new Expense("first", new BigDecimal(100.10), true);
        Expense second = new Expense("second", new BigDecimal(200.20), false);
        Expense third = new Expense("third", new BigDecimal(300.30), true);

        budgetTest.addExpense("first", new BigDecimal(100.10), true);
        budgetTest.addExpense("second", new BigDecimal(200.20), false);
        budgetTest.addExpense("third", new BigDecimal(300.30), true);

        expenses.add(first);
        expenses.add(second);
        expenses.add(third);

        assertEquals(expenses.get(0).getName(), budgetTest.getExpenses().get(0).getName());
        assertEquals(expenses.get(1).getName(), budgetTest.getExpenses().get(1).getName());
        assertEquals(expenses.get(2).getName(), budgetTest.getExpenses().get(2).getName());
        assertEquals(expenses.get(0).getCost(), budgetTest.getExpenses().get(0).getCost());
        assertEquals(expenses.get(1).getCost(), budgetTest.getExpenses().get(1).getCost());
        assertEquals(expenses.get(2).getCost(), budgetTest.getExpenses().get(2).getCost());
        assertEquals(expenses.get(0).isChecked(), budgetTest.getExpenses().get(0).isChecked());
        assertEquals(expenses.get(1).isChecked(), budgetTest.getExpenses().get(1).isChecked());
        assertEquals(expenses.get(2).isChecked(), budgetTest.getExpenses().get(2).isChecked());
    }

    @Test
    void removeExpense() {
        BigDecimal spendingLimit = new BigDecimal("1000.50");
        String filePath = "This is the file path";
        Budget budgetTest = new Budget(filePath, spendingLimit);
        LinkedList<Expense> expenses = new LinkedList<Expense>();
        Expense first = new Expense("first", new BigDecimal(100.10), true);
        Expense second = new Expense("second", new BigDecimal(200.20), false);
        Expense third = new Expense("third", new BigDecimal(300.30), true);
        Expense forth = new Expense("forth", new BigDecimal(400.40), true);

        budgetTest.addExpense("first", new BigDecimal(100.10), true);
        budgetTest.addExpense("second", new BigDecimal(200.20), false);
        budgetTest.addExpense("third", new BigDecimal(300.30), true);
        budgetTest.addExpense("forth", new BigDecimal(400.40), true);

        expenses.add(first);
        expenses.add(second);
        expenses.add(third);
        expenses.add(forth);

        budgetTest.removeExpense(3);
        expenses.remove(3);

        assertEquals(expenses.get(0).getName(), budgetTest.getExpenses().get(0).getName());
        assertEquals(expenses.get(1).getName(), budgetTest.getExpenses().get(1).getName());
        assertEquals(expenses.get(2).getName(), budgetTest.getExpenses().get(2).getName());
        assertEquals(expenses.get(0).getCost(), budgetTest.getExpenses().get(0).getCost());
        assertEquals(expenses.get(1).getCost(), budgetTest.getExpenses().get(1).getCost());
        assertEquals(expenses.get(2).getCost(), budgetTest.getExpenses().get(2).getCost());
        assertEquals(expenses.get(0).isChecked(), budgetTest.getExpenses().get(0).isChecked());
        assertEquals(expenses.get(1).isChecked(), budgetTest.getExpenses().get(1).isChecked());
        assertEquals(expenses.get(2).isChecked(), budgetTest.getExpenses().get(2).isChecked());
    }

    @Test
    void getRemainingAmount() {
        BigDecimal spendingLimit = new BigDecimal("1000.50");
        String filePath = "This is the file path";
        Budget budgetTest = new Budget(filePath, spendingLimit);

        budgetTest.addExpense("first", new BigDecimal(100.00), true);
        budgetTest.addExpense("second", new BigDecimal(200.00), false);
        budgetTest.addExpense("third", new BigDecimal(300.00), true);
        budgetTest.addExpense("forth", new BigDecimal(400.00), true);

        assertEquals(new BigDecimal("200.50").setScale(2, RoundingMode.HALF_UP), budgetTest.getRemainingAmount());
    }

//    @Test
//    void writeToTXT() {
//    }
//
//    @Test
//    void loadBudgetFromTXT() {
//    }

    @Test
    void getFilePath() {
        BigDecimal spendingLimit = new BigDecimal("1000.50");
        String filePath = "This is the file path";
        Budget budgetTest = new Budget(filePath, spendingLimit);
        assertEquals("This is the file path"+ "/budget.txt", budgetTest.getFilePath());
    }

    @Test
    void getCheckedExpenses() {
        BigDecimal spendingLimit = new BigDecimal("1000.50");
        String filePath = "This is the file path";
        Budget budgetTest = new Budget(filePath, spendingLimit);
        LinkedList<Expense> checkedExpenses = new LinkedList<Expense>();
        Expense first = new Expense("first", new BigDecimal(100.10), true);
        Expense second = new Expense("second", new BigDecimal(200.20), false);
        Expense third = new Expense("third", new BigDecimal(300.30), true);
        Expense forth = new Expense("forth", new BigDecimal(400.40), true);

        budgetTest.addExpense("first", new BigDecimal(100.10), true);
        budgetTest.addExpense("second", new BigDecimal(200.20), false);
        budgetTest.addExpense("third", new BigDecimal(300.30), true);
        budgetTest.addExpense("forth", new BigDecimal(400.40), true);

        checkedExpenses.add(first);
        checkedExpenses.add(third);
        checkedExpenses.add(forth);

        LinkedList<Expense> checkedList = budgetTest.getCheckedExpenses();

        assertEquals(checkedExpenses.get(0).getName(), checkedList.get(0).getName());
        assertEquals(checkedExpenses.get(1).getName(), checkedList.get(1).getName());
        assertEquals(checkedExpenses.get(2).getName(), checkedList.get(2).getName());
        assertEquals(checkedExpenses.get(0).getCost(), checkedList.get(0).getCost());
        assertEquals(checkedExpenses.get(1).getCost(), checkedList.get(1).getCost());
        assertEquals(checkedExpenses.get(2).getCost(), checkedList.get(2).getCost());
        assertEquals(checkedExpenses.get(0).isChecked(), checkedList.get(0).isChecked());
        assertEquals(checkedExpenses.get(1).isChecked(), checkedList.get(1).isChecked());
        assertEquals(checkedExpenses.get(2).isChecked(), checkedList.get(2).isChecked());
    }

    @Test
    void getUncheckedExpenses() {
        BigDecimal spendingLimit = new BigDecimal("1000.50");
        String filePath = "This is the file path";
        Budget budgetTest = new Budget(filePath, spendingLimit);
        LinkedList<Expense> unCheckedExpenses = new LinkedList<Expense>();
        Expense first = new Expense("first", new BigDecimal(100.10), true);
        Expense second = new Expense("second", new BigDecimal(200.20), false);
        Expense third = new Expense("third", new BigDecimal(300.30), true);
        Expense forth = new Expense("forth", new BigDecimal(400.40), true);

        budgetTest.addExpense("first", new BigDecimal(100.10), true);
        budgetTest.addExpense("second", new BigDecimal(200.20), false);
        budgetTest.addExpense("third", new BigDecimal(300.30), true);
        budgetTest.addExpense("forth", new BigDecimal(400.40), true);

        unCheckedExpenses.add(second);

        LinkedList<Expense> unCheckedList = budgetTest.getUncheckedExpenses();

        assertEquals(unCheckedExpenses.get(0).getName(), unCheckedList.get(0).getName());
        assertEquals(unCheckedExpenses.get(0).getCost(), unCheckedList.get(0).getCost());
        assertEquals(unCheckedExpenses.get(0).isChecked(), unCheckedList.get(0).isChecked());

    }
}