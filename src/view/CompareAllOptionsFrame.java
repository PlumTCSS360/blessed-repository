package view;

import model.GBC;
import model.Option;
import model.Subproject;

import javax.swing.*;
import java.awt.*;
import java.util.*;

/**
 * The frame display all options with their cost for the user to compare.
 *
 * @author Jiameng Li
 * @version 0.3
 */
public class CompareAllOptionsFrame extends JFrame implements GUIFrame {

    /** The subproject the options belong to. */
    private final Subproject mySubproject;

    /**
     * Construct a frame to compare all options
     *
     * @author Jiameng Li
     * @param theSubproject The subproject the new note belongs to.
     */
    public CompareAllOptionsFrame(final Subproject theSubproject) {
        super("Compare all options");
        mySubproject = theSubproject;
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

        // Add title
        JLabel title = new JLabel(mySubproject.getName() + " - Options");
        title.setForeground(FOREGROUND_COLOR);
        title.setFont(HEADING_FONT);
        add(title, gbc);

        // Sort the options by cost
        ArrayList<Option> options = new ArrayList<>(mySubproject.getOptionsList().values());
        options.sort(Comparator.comparing(Option::getCost));
        // Add label for options
        for (Option op : options) {
            gbc.gridy++;
            add(createLabel(op.getName()), gbc);
            gbc.gridx++;
            add(createLabel(op.getCost().toString()), gbc);
            gbc.gridx--;
        }

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

}
