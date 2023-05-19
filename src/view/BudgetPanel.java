package view;

import model.Budget;
import model.Expense;
import model.GBC;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The BudgetPanel class has a Budget object and displays the information in a clear way for the user to understand.
 * @author Devin Peevy
 */
public class BudgetPanel extends JPanel implements WorkPanel {

    /** The budget which is being displayed. */
    private final Budget budget;

    /** Whether the panel is just displaying information, or able to be edited. */
    private boolean isEditable;

    /**
     * This constructor creates a new BudgetPanel and assembles the information on it.
     * @author Devin Peevy
     * @param budget The budget which is being displayed.
     */
    public BudgetPanel(final Budget budget) {
        this.budget = budget;
        isEditable = false;
        arrangePanel();
    }

    /**
     * This private helper method will arrange all the information on the panel. It is called by the constructor.
     */
    private void arrangePanel() {
        if (isEditable) {
            //arrangeEditablePanel();
        }
        else {
            arrangeUneditablePanel();
        }
    }

    /**
     * This method will arrange the panel so that it is made to display useful information about the budget.
     * @author Devin Peevy
     */
    private void arrangeUneditablePanel() {
        setBackground(BACKGROUND_COLOR);
        GBC gbc = new GBC(0, 0, 2, 1);
        GridBagLayout layout = new GridBagLayout();
        setLayout(layout);

        add(textArea("Budget", JPanel.CENTER_ALIGNMENT, TITLE_FONT), gbc);

        gbc = new GBC(0, 1, 2, 1);
        add(textArea("Spending Limit: " + budget.getSpendingLimit(), JPanel.CENTER_ALIGNMENT, HEADING_FONT), gbc);

        gbc = new GBC(0, 2, 2, 1);
        add(textArea("Checked Expenses", JPanel.CENTER_ALIGNMENT, SUBHEADING_FONT), gbc);

        int currentY = 3;
        for (Expense e : budget.getCheckedExpenses()) {
            gbc = new GBC(0, currentY);
            add(textArea(e.getName(), JPanel.LEFT_ALIGNMENT, BODY_FONT), gbc);

            gbc = new GBC(1, currentY);
            add(textArea(e.getCost().toString(), JPanel.LEFT_ALIGNMENT, BODY_FONT), gbc);
            currentY++;
        }
        gbc = new GBC(0, currentY, 2, 1);
        add(textArea("Unchecked Expenses", JPanel.CENTER_ALIGNMENT, SUBHEADING_FONT), gbc);
        currentY++;
        for (Expense e : budget.getUncheckedExpenses()) {
            gbc = new GBC(0, currentY);
            add(textArea(e.getName(), JPanel.LEFT_ALIGNMENT, BODY_FONT), gbc);

            gbc = new GBC(1, currentY);
            add(textArea(e.getCost().toString(), JPanel.LEFT_ALIGNMENT, BODY_FONT), gbc);
            currentY++;
        }
        gbc = new GBC(0, currentY, 2, 1);
        add(textArea("Remaining Budget: " + budget.getRemainingAmount().toString(), CENTER_ALIGNMENT, HEADING_FONT), gbc);

        currentY++;
        gbc = new GBC(0, currentY, 2, 1);
        JButton editButton = new JButton("Edit Budget");
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editAction();
            }
        });
        add(editButton, gbc);
    }

    private void arrangeEditablePanel() {
        setBackground(BACKGROUND_COLOR);
        setLayout(new GridBagLayout());

        GBC gbc = new GBC(0, 0, 2, 1);
        add(textArea("Budget", JPanel.CENTER_ALIGNMENT, TITLE_FONT), gbc);

        gbc = new GBC(0, 1, 1, 1);
        add(textArea("Spending Limit: ", JPanel.CENTER_ALIGNMENT, HEADING_FONT), gbc);

        gbc = new GBC(1, 1, 1, 1);
        add(new JTextField(budget.getSpendingLimit().toString(), 20));
    }

    public void editAction() {
        isEditable = true;
        arrangeEditablePanel();
    }

    /**
     * This method is used to add a new Label to panel.
     * @param text
     * @param alignmentX
     * @param font
     * @return
     */
    private JLabel textArea(final String text, final float alignmentX, final Font font) {
        JLabel textArea = new JLabel(text);
        textArea.setAlignmentX(alignmentX);
        textArea.setFont(font);
        textArea.setBackground(BACKGROUND_COLOR);
        textArea.setForeground(FOREGROUND_COLOR);
        return textArea;
    }


    public static void main(final String[] args) {
        JFrame frame = new JFrame();
        frame.add(new BudgetPanel(Budget.loadBudgetFromTXT("data/sample/budget.txt")));
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
