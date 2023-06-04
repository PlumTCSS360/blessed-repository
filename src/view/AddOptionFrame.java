package view;

import model.GBC;
import model.Subproject;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;

/**
 * The frame that allow the user to add an option by entering option name, cost, description,
 * and website.
 *
 * @author Jiameng Li
 * @version 0.3
 */
public class AddOptionFrame extends JFrame implements GUIFrame  {

    /** The pattern of valid input for cost. */
    public static final String DECIMAL_REGEX = "[0-9]+['.']?[0-9]*";

    /** The subproject the new option belongs to. */
    private final Subproject mySubproject;

    /** The project frame. */
    private final ProjectFrame myFrame;

    /** Text field to enter the name of the option. */
    private final JTextField myNameField;

    /** Text field to enter the cost of the option. */
    private final JTextField myCostField;

    /** Text field to enter the website of the option. */
    private final JTextField myWebsiteField;

    /** Text area to enter the description of the option. */
    private final JTextArea myDescArea;

    /**
     * Construct a frame to add an option.
     *
     * @author Jiameng Li
     * @param theSubproject The subproject the new option belongs to.
     * @param theFrame The project frame.
     */
    public AddOptionFrame(final Subproject theSubproject, final ProjectFrame theFrame) {
        super("Add a new option");
        mySubproject = theSubproject;
        myFrame = theFrame;
        myNameField = new JTextField(35);
        myCostField = new JTextField(35);
        myWebsiteField = new JTextField(60);
        myDescArea = new JTextArea(15, 60);
        setup();
    }

    /**
     * Set up the frame.
     *
     * @author Jiameng Li
     */
    private void setup() {
        setLayout(new GridBagLayout());
        GBC gbc = new GBC(0, 0);
        gbc.anchor = GridBagConstraints.LINE_START;

        // Label for text field for name
        gbc.gridwidth = 2;
        JLabel label = createLabel("Name:");
        add(label, gbc);

        // Text field for name
        gbc.gridy = 1;
        add(myNameField, gbc);

        // Label for text field for cost
        gbc.gridy = 2;
        label = createLabel("Cost:");
        add(label, gbc);

        // Text field for cost
        gbc.gridy = 3;
        add(myCostField, gbc);

        // Label for text field for website
        gbc.gridy = 4;
        label = createLabel("Website (Optional):");
        add(label, gbc);

        // Text field for website
        gbc.gridy = 5;
        add(myWebsiteField, gbc);

        // Label for text field for description
        gbc.gridy = 6;
        label = createLabel("Description (Optional):");
        add(label, gbc);

        // Text area for description
        gbc.gridy = 7;
        add(myDescArea, gbc);

        // Add a button to cancel
        gbc.gridwidth = 1;
        gbc.gridy = 8;
        JButton button = new JButton("Cancel");
        button.addActionListener(e -> cancelAction());
        add(button, gbc);

        // Add a button to confirm
        gbc.gridx = 1;
        button = new JButton("Confirm");
        button.addActionListener(e -> confirmAction());
        add(button, gbc);

        // Other setup
        getContentPane().setBackground(BACKGROUND_COLOR);
        getContentPane().setForeground(FOREGROUND_COLOR);
        pack();
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    /**
     * Create a label based on the given string.
     *
     * @author Jiameng Li
     * @param theLabel The string to be used to create label.
     * @return A new label with given string.
     */
    private JLabel createLabel(final String theLabel) {
        JLabel label = new JLabel(theLabel);
        label.setForeground(FOREGROUND_COLOR);
        label.setFont(BODY_FONT);
        return label;
    }

    /**
     * Called if the user click "Confirm" button.
     * Check if the entered cost is a valid input.
     * Add an option to the subproject.
     *
     * @author Jiameng Li
     */
    private void confirmAction() {
        // Get all inputs from text fields and area
        final String name = myNameField.getText();
        final String cost = myCostField.getText();
        final String website = myWebsiteField.getText();
        final String desc = myDescArea.getText();
        // Check for valid input for cost
        if (cost.endsWith(".") || !cost.matches(DECIMAL_REGEX)) {
            JOptionPane.showMessageDialog(null, "You have entered an invalid input for cost",
                    "Invalid input", JOptionPane.WARNING_MESSAGE);
            return;
        }
        // Add option to the subproject
        if (mySubproject.createOption(name, new BigDecimal(cost), desc, website) != null) {
            myFrame.refreshTreePanel();
            dispose();
        }
    }

    /**
     * Called if the user click "Cancel" button.
     * Close this frame.
     *
     * @author Jiameng Li
     */
    private void cancelAction() {
        dispose();
    }

}
