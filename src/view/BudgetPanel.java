package view;

import model.Budget;
import model.Expense;
import model.GBC;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedList;

/**
 * The BudgetPanel class has a Budget object and displays the information in a clear way for the user to understand.
 * @author Devin Peevy
 */
public class BudgetPanel extends JPanel implements WorkPanel, PropertyChangeListener {

    /** The budget which is being displayed. */
    private Budget budget;

    /** Whether the panel is just displaying information, or able to be edited. */
    private boolean isEditable;

    private JTextField spendingLimitField;

    private LinkedList<JTextField> expenseNameFields;

    private LinkedList<JTextField> expenseCostFields;

    private LinkedList<JCheckBox> expenseCheckBoxes;

    /**
     * This constructor creates a new BudgetPanel and assembles the information on it.
     * @author Devin Peevy
     * @param budget The budget which is being displayed.
     */
    public BudgetPanel(final Budget budget) {
        super();
        this.budget = budget;
        this.isEditable = false;
        //this.isEditable = true;
        this.spendingLimitField = new JTextField();
        this.expenseNameFields = new LinkedList<>();
        this.expenseCostFields = new LinkedList<>();
        this.expenseCheckBoxes = new LinkedList<>();
        initializeCustomizableFields();
        arrangePanel();
    }


    private void initializeCustomizableFields() {
        spendingLimitField.setFont(BODY_FONT);
        spendingLimitField.setColumns(15);
        spendingLimitField.setText(budget.getSpendingLimit().toString());

        for (int i = 0; i < budget.getExpenses().size(); i++) {
            Expense e = budget.getExpenses().get(i);

            JTextField newNameField = new JTextField(e.getName(), 15);
            newNameField.setColumns(15);
            newNameField.setFont(BODY_FONT);
            expenseNameFields.add(newNameField);

            JTextField newCostField = new JTextField(e.getCost().toString(), 7);
            newCostField.setFont(BODY_FONT);
            expenseCostFields.add(newCostField);

            JCheckBox newCheckBox = new JCheckBox();
            if (e.isChecked()) {
                newCheckBox.setSelected(true);
            }
            else {
                newCheckBox.setSelected(false);
            }
            expenseCheckBoxes.add(newCheckBox);
        }
    }

    private void resetCustomizableFields() {
        expenseNameFields.clear();
        expenseCostFields.clear();
        expenseCheckBoxes.clear();
        initializeCustomizableFields();
    }

    /**
     * This private helper method will arrange all the information on the panel. It is called by the constructor.
     */
    private void arrangePanel() {
        setBackground(BACKGROUND_COLOR);
        setForeground(FOREGROUND_COLOR);
        setLayout(new GridBagLayout());

        GBC gbc = new GBC(0, 0);
        add(title(), gbc);

        gbc = new GBC(0, 1);
        add(spendingLimit(), gbc);

        gbc = new GBC(0, 2);
        add(checkedExpenses(), gbc);

        gbc = new GBC(0, 3);
        add(uncheckedExpenses(), gbc);

        if (!isEditable) {
            gbc = new GBC(0, 4);
            add(totalRemaining(), gbc);
        }

        gbc = new GBC(0, 5);
        add(buttons(), gbc);
    }

    private JLabel title() {
        JLabel title = new JLabel(budget.getProjectName() + " - Budget");
        title.setFont(TITLE_FONT);
        title.setForeground(FOREGROUND_COLOR);
        return title;
    }

    private JPanel spendingLimit() {
        if (!isEditable) {
            JPanel spendingLimit = new JPanel();
            spendingLimit.setBackground(BACKGROUND_COLOR);
            JLabel text = new JLabel("Spending Limit: " + budget.getSpendingLimit());
            text.setFont(HEADING_FONT);
            text.setForeground(FOREGROUND_COLOR);
            text.setBackground(BACKGROUND_COLOR);
            spendingLimit.add(text);
            return spendingLimit;
        }
        else {
            JPanel spendingLimit = new JPanel();
            spendingLimit.setLayout(new GridLayout(1, 2));
            spendingLimit.setBackground(BACKGROUND_COLOR);

            JLabel text = new JLabel("Spending Limit: ");
            text.setForeground(FOREGROUND_COLOR);
            text.setFont(HEADING_FONT);
            text.setBackground(BACKGROUND_COLOR);
            spendingLimit.add(text);

            spendingLimit.add(spendingLimitField);

            return spendingLimit;
        }
    }

    private JPanel checkedExpenses() {
        if (isEditable) {
            return editableCheckedExpenses();
        }
        else {
            return uneditableCheckedExpenses();
        }
    }

    private JPanel editableCheckedExpenses() {
        JPanel checkedExpenses = new JPanel();
        checkedExpenses.setLayout(new GridBagLayout());
        checkedExpenses.setBackground(BACKGROUND_COLOR);

        int yPos = 0;
        GBC gbc = new GBC(0, yPos, 3, 1);
        JLabel title = new JLabel("Checked Expenses");
        title.setFont(SUBHEADING_FONT);
        title.setForeground(FOREGROUND_COLOR);
        checkedExpenses.add(title, gbc);
        yPos++;

        for (int i = 0; i < budget.getExpenses().size(); i++) {
            Expense e = budget.getExpenses().get(i);
            // Place the checked fields where I want them.
            if (e.isChecked()) {
                // Name Fields on the left.
                gbc = new GBC(0, yPos);
                gbc.ipady = 2;
                checkedExpenses.add(expenseNameFields.get(i), gbc);
                // Cost Fields in the middle.
                gbc = new GBC(1, yPos);
                gbc.ipady = 2;
                checkedExpenses.add(expenseCostFields.get(i), gbc);
                // CheckBoxes on the right.
                gbc = new GBC(2, yPos);
                checkedExpenses.add(expenseCheckBoxes.get(i), gbc);
                yPos++;
            } // If unchecked, do nothing in this method.
        }

        return checkedExpenses;
    }

    private JPanel uneditableCheckedExpenses() {
        JPanel checkedExpenses = new JPanel();
        checkedExpenses.setLayout(new GridBagLayout());
        checkedExpenses.setBackground(BACKGROUND_COLOR);

        int yPos = 0;
        GBC gbc = new GBC(0, yPos, 2, 1);
        JLabel title = new JLabel("Checked Expenses");
        title.setFont(SUBHEADING_FONT);
        title.setForeground(FOREGROUND_COLOR);
        checkedExpenses.add(title, gbc);
        yPos++;

        for (int i = 0; i < budget.getExpenses().size(); i++) {
            Expense e = budget.getExpenses().get(i);

            // Place the checked fields where I want them.
            if (e.isChecked()) {

                // Name Fields on the left.
                gbc = new GBC(0, yPos);
                gbc.ipady = 2;
                JLabel nameLabel = new JLabel(e.getName());
                nameLabel.setForeground(FOREGROUND_COLOR);
                nameLabel.setFont(BODY_FONT);
                checkedExpenses.add(nameLabel, gbc);

                // Cost Fields on the right.
                gbc = new GBC(1, yPos);
                gbc.ipady = 2;
                JLabel costLabel = new JLabel("$" + e.getCost().toString());
                costLabel.setForeground(FOREGROUND_COLOR);
                costLabel.setFont(BODY_FONT);
                checkedExpenses.add(costLabel, gbc);
                yPos++;
            } // If unchecked, do nothing in this method.
        }

        return checkedExpenses;
    }

    private JPanel uncheckedExpenses() {
        if (isEditable) {
            return editableUncheckedExpenses();
        }
        else {
            return uneditableUncheckedExpenses();
        }
    }

    private JPanel editableUncheckedExpenses() {
        JPanel uncheckedExpenses = new JPanel();
        uncheckedExpenses.setLayout(new GridBagLayout());
        uncheckedExpenses.setBackground(BACKGROUND_COLOR);

        int yPos = 0;
        GBC gbc = new GBC(0, yPos, 3, 1);
        JLabel title = new JLabel("Unchecked Expenses");
        title.setFont(SUBHEADING_FONT);
        title.setForeground(FOREGROUND_COLOR);
        uncheckedExpenses.add(title, gbc);
        yPos++;

        for (int i = 0; i < budget.getExpenses().size(); i++) {
            Expense e = budget.getExpenses().get(i);
            // Place the checked fields where I want them.
            if (!e.isChecked()) {
                // Name Fields on the left.
                gbc = new GBC(0, yPos);
                gbc.ipady = 2;
                uncheckedExpenses.add(expenseNameFields.get(i), gbc);
                // Cost Fields in the middle.
                gbc = new GBC(1, yPos);
                gbc.ipady = 2;
                uncheckedExpenses.add(expenseCostFields.get(i), gbc);
                // CheckBoxes on the right.
                gbc = new GBC(2, yPos);
                gbc.ipady = 2;
                uncheckedExpenses.add(expenseCheckBoxes.get(i), gbc);
                yPos++;
            } // If unchecked, do nothing in this method.
        }
        yPos = 0;

        return uncheckedExpenses;
    }

    private JPanel uneditableUncheckedExpenses() {
        JPanel uncheckedExpenses = new JPanel();
        uncheckedExpenses.setLayout(new GridBagLayout());
        uncheckedExpenses.setBackground(BACKGROUND_COLOR);

        int yPos = 0;
        GBC gbc = new GBC(0, yPos, 2, 1);
        JLabel title = new JLabel("Unchecked Expenses");
        title.setFont(SUBHEADING_FONT);
        title.setForeground(FOREGROUND_COLOR);
        uncheckedExpenses.add(title, gbc);
        yPos++;

        for (int i = 0; i < budget.getExpenses().size(); i++) {
            Expense e = budget.getExpenses().get(i);

            // Place the checked fields where I want them.
            if (!e.isChecked()) {

                // Name Fields on the left.
                gbc = new GBC(0, yPos);
                gbc.ipady = 2;
                JLabel nameLabel = new JLabel(e.getName());
                nameLabel.setForeground(FOREGROUND_COLOR);
                nameLabel.setFont(BODY_FONT);
                uncheckedExpenses.add(nameLabel, gbc);

                // Cost Fields on the right.
                gbc = new GBC(1, yPos);
                gbc.ipady = 2;
                JLabel costLabel = new JLabel("$" + e.getCost().toString());
                costLabel.setForeground(FOREGROUND_COLOR);
                costLabel.setFont(BODY_FONT);
                uncheckedExpenses.add(costLabel, gbc);
                yPos++;
            } // If unchecked, do nothing in this method.
        }

        return uncheckedExpenses;
    }

    private JPanel totalRemaining() {
        JPanel totalRemaining = new JPanel();
        totalRemaining.setBackground(BACKGROUND_COLOR);

        JLabel text = new JLabel("Total Remaining: " + budget.getRemainingAmount());
        text.setFont(HEADING_FONT);
        text.setForeground(FOREGROUND_COLOR);
        totalRemaining.add(text);
        return totalRemaining;
    }

    private JPanel buttons() {
        if (isEditable) {
            return editableButtonsPanel();
        }
        else {
            return uneditableButtonsPanel();
        }
    }

    private JPanel editableButtonsPanel() {
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridBagLayout());

        GBC gbc = new GBC(0, 0);
        gbc.ipadx = 10;
        buttonsPanel.add(cancelButton(), gbc);

        gbc = new GBC(1, 0);
        gbc.ipadx = 10;
        buttonsPanel.add(saveButton(), gbc);

        return buttonsPanel;
    }

    private JPanel uneditableButtonsPanel() {
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridBagLayout());
        buttonsPanel.setBackground(BACKGROUND_COLOR);

        GBC gbc = new GBC(0, 0);
        buttonsPanel.add(addExpenseButton(), gbc);

        gbc = new GBC(1, 0);
        buttonsPanel.add(deleteExpensesButton(), gbc);

        gbc = new GBC(0, 1, 2, 1);
        buttonsPanel.add(editExpensesButton(), gbc);

        return buttonsPanel;
    }

    private JButton addExpenseButton() {
        JButton addExpenseButton = new JButton("Add Expense");
        addExpenseButton.setSize(deleteExpensesButton().getWidth(), addExpenseButton.getHeight());
        addExpenseButton.setBackground(FOREGROUND_COLOR);
        addExpenseButton.setForeground(BACKGROUND_COLOR);
        addExpenseButton.setFont(BODY_FONT);
        addExpenseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addExpenseAction();
            }
        });
        return addExpenseButton;
    }

    private void addExpenseAction() {
        new AddExpenseFrame(this, budget);
    }

    private JButton deleteExpensesButton() {
        JButton deleteExpensesButton = new JButton("Delete Expenses");
        deleteExpensesButton.setForeground(BACKGROUND_COLOR);
        deleteExpensesButton.setBackground(FOREGROUND_COLOR);
        deleteExpensesButton.setFont(BODY_FONT);
        deleteExpensesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteAction();
            }
        });
        return deleteExpensesButton;
    }

    private void deleteAction() {
        new DeleteExpensesFrame(this, this.budget);
    }

    private JButton editExpensesButton() {
        JButton editExpensesButton = new JButton("Edit Expenses");
        editExpensesButton.setForeground(BACKGROUND_COLOR);
        editExpensesButton.setBackground(FOREGROUND_COLOR);
        editExpensesButton.setFont(BODY_FONT);
        editExpensesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isEditable = true;
                // Edit the panel.
                resetPanel();
            }
        });
        return editExpensesButton;
    }

    private JButton cancelButton() {
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setFont(BODY_FONT);
        cancelButton.setForeground(BACKGROUND_COLOR);
        cancelButton.setBackground(FOREGROUND_COLOR);
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isEditable = false;
                // Edit the panel.
                resetPanel();
            }
        });
        return cancelButton;
    }

    private JButton saveButton() {
        JButton saveButton = new JButton("Save");
        saveButton.setForeground(BACKGROUND_COLOR);
        saveButton.setBackground(FOREGROUND_COLOR);
        saveButton.setFont(BODY_FONT);
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                budget = configureEditedBudget();
                budget.writeToTXT();
                isEditable = false;
                // Edit the panel.
                resetPanel();
            }
        });
        return saveButton;
    }

    private Budget configureEditedBudget() {
        String limitString = spendingLimitField.getText();
        BigDecimal spendingLimit = new BigDecimal(limitString);
        Budget editedBudget = new Budget(budget.getParentFilePath(), spendingLimit);
        for (int i = 0; i < budget.getExpenses().size(); i++) {
            String name = expenseNameFields.get(i).getText();
            String costString = expenseCostFields.get(i).getText();
            BigDecimal cost = new BigDecimal(costString);
            boolean isChecked = expenseCheckBoxes.get(i).isSelected();
            editedBudget.addExpense(name, cost, isChecked);
        }
        return editedBudget;
    }

    private void resetPanel() {
        removeAll();
        arrangePanel();
        revalidate();
        repaint();
        setVisible(true);
    }

    @Override
    public void propertyChange(PropertyChangeEvent pce) {
        budget = (Budget) pce.getNewValue();
        budget.writeToTXT();
        resetCustomizableFields();
        resetPanel();
    }

    public static void main(final String[] args) {
        JFrame frame = new JFrame();
        frame.add(new BudgetPanel(Budget.loadBudgetFromTXT("data/Devin's Second Project/budget.txt")));
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
//                new WelcomeFrame();
//                new AboutFrame();
                frame.setSize(700, 700);
                frame.setBackground(BACKGROUND_COLOR);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
                frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            }
        });
    }
}
