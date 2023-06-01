package view;

import model.Budget;
import model.Expense;
import model.GBC;

import javax.swing.*;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeSupport;

public class DeleteExpensesFrame extends JFrame implements GUIFrame{

    private BudgetPanel listener;

    private Budget budget;

    private JCheckBox[] checkBoxes;

    private PropertyChangeSupport pcs;

    public DeleteExpensesFrame(BudgetPanel listener, Budget budget) {
        this.listener = listener;
        this.budget = budget;
        this.checkBoxes = new JCheckBox[this.budget.getExpenses().size()];
        this.pcs = new PropertyChangeSupport(this);
        pcs.addPropertyChangeListener(listener);
        setUp();
    }

    private void setUp() {
        initializeCheckBoxes();
        arrangePanel();
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initializeCheckBoxes() {
        for (int i = 0; i < checkBoxes.length; i++) {
            Expense e = budget.getExpenses().get(i);
            JCheckBox checkBox = new JCheckBox(e.getName() + " - " + e.getCost());
            checkBoxes[i] = checkBox;
        }
    }

    private void arrangePanel() {
        JPanel panel = new JPanel();
        panel.setBackground(BACKGROUND_COLOR);
        panel.setForeground(FOREGROUND_COLOR);
        panel.setFont(BODY_FONT);
        panel.setLayout(new GridBagLayout());

        GBC gbc = new GBC(0, 0);
        panel.add(checkBoxPanel(), gbc);

        gbc = new GBC(0, 1);
        panel.add(buttonsPanel());

        add(panel);
    }

    private JPanel checkBoxPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(BACKGROUND_COLOR);
        panel.setForeground(FOREGROUND_COLOR);
        panel.setFont(BODY_FONT);
        panel.setLayout(new GridBagLayout());

        int yPos = 0;
        GBC gbc;
        for (int i = 0; i < checkBoxes.length; i++) {
            gbc = new GBC(0, yPos);
            JCheckBox checkBox = checkBoxes[i];
            checkBox.setBackground(BACKGROUND_COLOR);
            checkBox.setForeground(FOREGROUND_COLOR);
            checkBox.setFont(BODY_FONT);
            panel.add(checkBox, gbc);
            yPos++;
        }
        return panel;
    }

    private JPanel buttonsPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(BACKGROUND_COLOR);
        panel.setLayout(new GridBagLayout());

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setBackground(FOREGROUND_COLOR);
        cancelButton.setForeground(BACKGROUND_COLOR);
        cancelButton.setFont(BODY_FONT);
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        JButton deleteSelectedButton = new JButton("Delete Selected");
        deleteSelectedButton.setBackground(FOREGROUND_COLOR);
        deleteSelectedButton.setForeground(BACKGROUND_COLOR);
        deleteSelectedButton.setFont(BODY_FONT);
        deleteSelectedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteAction();
            }
        });

        GBC gbc = new GBC(0, 0);
        gbc.ipadx = 10;
        panel.add(cancelButton, gbc);
        gbc = new GBC(1, 0);
        gbc.ipadx = 10;
        panel.add(deleteSelectedButton);
        return panel;
    }

    private void deleteAction() {
        for (int i = checkBoxes.length - 1; i >= 0; i--) {
            if (checkBoxes[i].isSelected()) {
                budget.removeExpense(i);
            }
        }
        pcs.firePropertyChange("deleteExpenses", null, budget);
        dispose();
    }
}
