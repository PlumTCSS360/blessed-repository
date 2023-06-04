package view;

import model.GBC;
import model.Option;
import model.Subproject;

import javax.swing.*;
import java.awt.*;
import java.util.*;

/**
 * The frame that allows the user to select two options to compare
 *
 * @author Jiameng Li
 * @version 0.3
 */
public class CompareTwoOptionsFrame extends JFrame implements GUIFrame {

    /** The dropdown menu for the first selected option. */
    private final JComboBox<Option> myOptionOne;

    /** The dropdown menu for the second selected option. */
    private final JComboBox<Option> myOptionTwo;

    /** The scroll pane that display details about selected options. */
    private final JScrollPane myDetailPane;

    /**
     * Construct a frame to compare two options.
     *
     * @param theSubproject The subproject those options belong to.
     */
    public CompareTwoOptionsFrame(final Subproject theSubproject) {
        super("Compare two options");
        // The list of options sorted by name
        Option[] myOptions = theSubproject.getOptionsList().values().toArray(new Option[0]);
        Arrays.sort(myOptions, Comparator.comparing(Option::getName));
        myOptionOne = new JComboBox<>(myOptions);
        myOptionTwo = new JComboBox<>(myOptions);
        myDetailPane = new JScrollPane();
        setup();
    }

    /**
     * Set up the frame.
     *
     * @author Jiameng Li
     */
    private void setup() {
        setLayout(new GridBagLayout());
        GBC gbc = new GBC(0, 0, 3, 1);

        // Add label
        JLabel label = new JLabel("Select two options to compare:");
        label.setForeground(FOREGROUND_COLOR);
        label.setFont(HEADING_FONT);
        add(label, gbc);

        // Add dropdown menu to select options
        gbc = new GBC(0, 1);
        gbc.weightx = 0.5;
        gbc.anchor = GridBagConstraints.CENTER;
        add(myOptionOne, gbc);
        gbc.gridx++;
        add(myOptionTwo, gbc);

        // Add "Apply" button
        gbc = new GBC(2, 1);
        JButton button = new JButton("Apply");
        button.addActionListener(e -> applyAction());
        add(button, gbc);

        // Set up and add the scroll pane to display details
        gbc = new GBC(0, 2, 3, 1);
        myDetailPane.setPreferredSize(new Dimension(1030, 500));
        myDetailPane.getVerticalScrollBar().setUnitIncrement(10);
        myDetailPane.setBorder(BorderFactory.createEmptyBorder());
        final JPanel temp = new JPanel();   // Temporary placeholder
        temp.setBackground(BACKGROUND_COLOR);
        myDetailPane.setViewportView(temp);
        add(myDetailPane, gbc);

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
     * Called when "Apply" button is clicked.
     * Updated the details of selected options to the scroll pane.
     */
    private void applyAction() {
        myDetailPane.setViewportView(createPanel((Option) Objects.requireNonNull(myOptionOne.getSelectedItem()),
                (Option) Objects.requireNonNull(myOptionTwo.getSelectedItem())));
    }

    /**
     * Create a panel to be updated to the scroll pane based on selected options.
     *
     * @param theOptionOne The first selected option.
     * @param theOptionTwo The second selected option.
     * @return A panel that contains details about selected options
     */
    private JPanel createPanel(final Option theOptionOne, final Option theOptionTwo) {
        // Get information about each option
        final String[][] details = new String[4][2];
        details[0][0] = "Name: " + theOptionOne.getName();
        details[0][1] = "Name: " + theOptionTwo.getName();
        details[1][0] = "Cost: " + theOptionOne.getCost().toString();
        details[1][1] = "Cost: " + theOptionTwo.getCost().toString();
        details[2][0] = "Warranty:\n" + theOptionOne.getWarrantyInfo();
        details[2][1] = "Warranty:\n" + theOptionTwo.getWarrantyInfo();
        details[3][0] = "Contractor:\n" + theOptionOne.getContractorInfo();
        details[3][1] = "Contractor:\n" + theOptionTwo.getContractorInfo();

        // Set up panel
        final JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(BACKGROUND_COLOR);
        GBC gbc = new GBC(0, 0);
        gbc.anchor = GridBagConstraints.PAGE_START;
        for (int i = 0; i < details.length; i++) {
            for (int j = 0; j < 2; j++) {
                gbc.gridx = j;
                gbc.gridy = i;
                // Use text area to wrap lines
                JTextArea text = new JTextArea(details[i][j] + "\n", 1, 45);
                text.setLineWrap(true);
                text.setWrapStyleWord(true);
                text.setEditable(false);
                text.setFont(BODY_FONT);
                text.setBackground(BACKGROUND_COLOR);
                text.setForeground(FOREGROUND_COLOR);
                panel.add(text, gbc);
            }
        }

        // Fill remaining empty space
        gbc.gridy++;
        gbc.weighty = 1.0;
        final JPanel space = new JPanel();
        space.setBackground(BACKGROUND_COLOR);
        panel.add(space, gbc);

        return panel;
    }

}
