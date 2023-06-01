package view;

import model.Budget;
import model.GBC;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeSupport;
import java.math.BigDecimal;

public class AddExpenseFrame extends JFrame implements GUIFrame {

    final private BudgetPanel listener;

    final private Budget budget;

    final private PropertyChangeSupport pcs;

    final private JTextField nameField;

    final private JTextField costField;

    final private JCheckBox checkBox;

    public AddExpenseFrame(BudgetPanel listener, Budget budget) {
        super("Crafty Companion - " + budget.getProjectName() + " > Budget > Add Expense");
        this.listener = listener;
        this.budget = budget;
        this.pcs = new PropertyChangeSupport(this);
        this.nameField = new JTextField(15);
        this.costField = new JTextField(15);
        this.checkBox = new JCheckBox();
        pcs.addPropertyChangeListener(listener);
        setUp();
    }

    private void setUp() {
        setBackground(BACKGROUND_COLOR);
        arrangePanel();
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void arrangePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(BACKGROUND_COLOR);

        JLabel projectName = new JLabel("Add an expense to " + budget.getProjectName());
        projectName.setFont(HEADING_FONT);
        projectName.setBackground(BACKGROUND_COLOR);
        projectName.setForeground(FOREGROUND_COLOR);
        GBC gbc = new GBC(0, 0, 3, 1);
        panel.add(projectName, gbc);

        JLabel expenseNamePrompt = new JLabel("Name: ");
        expenseNamePrompt.setFont(BODY_FONT);
        expenseNamePrompt.setBackground(BACKGROUND_COLOR);
        expenseNamePrompt.setForeground(FOREGROUND_COLOR);
        gbc = new GBC(0, 1);
        panel.add(expenseNamePrompt, gbc);


        JLabel expenseCostPrompt = new JLabel("Cost: ");
        expenseCostPrompt.setFont(BODY_FONT);
        expenseCostPrompt.setBackground(BACKGROUND_COLOR);
        expenseCostPrompt.setForeground(FOREGROUND_COLOR);
        gbc = new GBC(0, 2);
        panel.add(expenseCostPrompt, gbc);

        gbc = new GBC(1, 1);
        nameField.setFont(BODY_FONT);
        panel.add(nameField, gbc);

        gbc = new GBC(1, 2);
        costField.setFont(BODY_FONT);
        panel.add(costField, gbc);

        gbc = new GBC(0, 3);
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setFont(BODY_FONT);
        cancelButton.setBackground(FOREGROUND_COLOR);
        cancelButton.setForeground(BACKGROUND_COLOR);
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        panel.add(cancelButton, gbc);

        gbc = new GBC(2, 3);
        JButton saveButton = new JButton("Save");
        saveButton.setForeground(BACKGROUND_COLOR);
        saveButton.setBackground(FOREGROUND_COLOR);
        saveButton.setFont(BODY_FONT);
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addAction();
            }
        });
        panel.add(saveButton, gbc);
        add(panel);
    }




    private void addAction() {
        Budget oldBudget = this.budget;
        String expenseName = nameField.getText();
        String costString = costField.getText();
        BigDecimal expenseCost = new BigDecimal(costString);
        boolean isChecked = checkBox.isSelected();
        budget.addExpense(expenseName, expenseCost, isChecked);
        pcs.firePropertyChange("addExpense", null, budget);
        System.out.println("PCS FIRED");
        dispose();
    }
}
