package view;

import model.GBC;
import model.Subproject;

import javax.swing.*;
import java.awt.*;

/**
 * The subproject panel that contains the buttons for create options, notes, and sketches.
 * Ite also allows the user to compare all options by cost or compare two options with all details.
 *
 * @author Jiameng Li
 * @version 0.3
 */
public class SubprojectPanel extends JPanel implements WorkPanel {

    /** The subproject associated with it this panel. */
    private final Subproject mySubproject;

    /** The project frame. */
    private final ProjectFrame myFrame;

    /**
     * Construct a subproject panel without specific subproject and project frame.
     *
     * @author Jiameng Li
     */
    public SubprojectPanel() {
        super(new GridBagLayout());
        mySubproject = null;
        myFrame = null;
    }

    /**
     * Construct a subproject panel with given subproject and project frame.
     *
     * @author Jiameng Li
     * @param theSubproject The subproject associated with it.
     * @param theFrame The project frame.
     */
    public SubprojectPanel(final Subproject theSubproject, final ProjectFrame theFrame) {
        super(new GridBagLayout());
        mySubproject = theSubproject;
        myFrame = theFrame;
        setupPanel();
    }

    /**
     * Set up the panel.
     *
     * @author Jiameng Li
     */
    private void setupPanel() {
        // Set background color
        setBackground(BACKGROUND_COLOR);
        setForeground(FOREGROUND_COLOR);

        // Add title
        GBC gbc = new GBC(0, 0, 2, 1);
        JLabel title = new JLabel(mySubproject.getName());
        title.setFont(TITLE_FONT);
        title.setForeground(FOREGROUND_COLOR);
        title.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        add(title, gbc);

        // Add "New Option" button and prompt
        gbc = new GBC(0, 1, 1, 1);
        JLabel prompt = createLabel("To create a new option for purchase:", gbc);
        add(prompt, gbc);
        gbc = new GBC(1, 1, 1, 1);
        JButton button = new JButton("New Option");
        button.addActionListener(e -> new AddOptionFrame(mySubproject, myFrame));
        add(button, gbc);

        // Add "New Note" button and prompt
        gbc = new GBC(0, 2, 1, 1);
        prompt = createLabel("To create a new note:", gbc);
        add(prompt, gbc);
        gbc = new GBC(1, 2, 1, 1);
        button = new JButton("New Note");
        button.addActionListener(e -> new AddNoteFrame(mySubproject, myFrame));
        add(button, gbc);

        // Add "New Sketch" button and prompt
        gbc = new GBC(0, 3, 1, 1);
        prompt = createLabel("To create a new sketch or image:", gbc);
        add(prompt, gbc);
        gbc = new GBC(1, 3, 1, 1);
        button = new JButton("New Sketch");
        button.addActionListener(e -> new AddSketchFrame(mySubproject, myFrame));
        add(button, gbc);

        // Add "Compare All Options" button and prompt
        gbc = new GBC(0, 4, 1, 1);
        prompt = createLabel("To compare all options by cost:", gbc);
        add(prompt, gbc);
        gbc = new GBC(1, 4, 1, 1);
        button = new JButton("Compare All Options");
        button.addActionListener(e -> new CompareAllOptionsFrame(mySubproject));
        add(button, gbc);

        // Add "Compare Two Options" button and prompt
        gbc = new GBC(0, 5, 1, 1);
        prompt = createLabel("To compare two options with all details:", gbc);
        add(prompt, gbc);
        gbc = new GBC(1, 5, 1, 1);
        button = new JButton("Compare Two Options");
        button.addActionListener(e -> new CompareTwoOptionsFrame(mySubproject));
        add(button, gbc);
    }

    /**
     * Create a label with given prompt and grid bag constraint.
     *
     * @param thePrompt The prompt for the label.
     * @param theGBC The grid bag constraint.
     * @return The label with given prompt and grid bag constraint.
     */
    private JLabel createLabel(final String thePrompt, final GBC theGBC) {
        JLabel label = new JLabel(thePrompt);
        label.setFont(SUBHEADING_FONT);
        label.setForeground(FOREGROUND_COLOR);
        label.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 30));
        theGBC.anchor = GridBagConstraints.LINE_START;
        theGBC.weightx = 1.0;
        theGBC.weighty = 0.2;
        return label;
    }

}
