package view;

import model.GBC;
import model.Subproject;

import javax.swing.*;
import java.awt.*;

/**
 * The frame that allow the user to add a sketch by entering name of the sketch
 * and select a file with file chooser.
 *
 * @author Jiameng Li
 * @version 0.3
 */
public class AddSketchFrame extends JFrame implements GUIFrame {

    /** The subproject the new sketch belongs to. */
    private final Subproject mySubproject;

    /** The project frame. */
    private final ProjectFrame myFrame;

    /** Text field to enter the name of the sketch. */
    private final JTextField myNameField;

    /** Path to the image file to be added. */
    private String myFilePath;

    /** Name of the image file to be added. */
    private final JLabel myFileName;

    /**
     * Construct a frame to add a sketch.
     *
     * @author Jiameng Li
     * @param theSubproject The subproject the new sketch belongs to.
     * @param theFrame The project frame.
     */
    public AddSketchFrame(final Subproject theSubproject, final ProjectFrame theFrame) {
        super("Add a new sketch");
        mySubproject = theSubproject;
        myFrame = theFrame;
        myNameField = new JTextField(35);
        myFileName = new JLabel();
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

        // Label for file chooser
        gbc.gridy = 2;
        label = createLabel("Choose a file:");
        add(label, gbc);

        // Button to open a file chooser
        gbc.gridwidth = 1;
        gbc.gridy = 3;
        JButton button = new JButton("Select");
        button.addActionListener(e -> selectAction());
        add(button, gbc);

        // Label for name of the selected file
        gbc.gridx = 1;
        myFileName.setFont(BODY_FONT);
        myFileName.setForeground(FOREGROUND_COLOR);

        // Add a button to cancel
        gbc.gridx = 0;
        gbc.gridy = 4;
        button = new JButton("Cancel");
        button.addActionListener(e -> cancelAction());
        add(button, gbc);

        // Add a button to confirm
        gbc.weightx = 1.0;
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
     * Called if the user click "Select" button.
     * Open a file chooser and get the selected file path. Display selected name on the frame.
     *
     * @author Jiameng Li
     */
    private void selectAction() {
        JFileChooser fc = new JFileChooser("Select an image file");
        if (fc.showDialog(null, "Select") == JFileChooser.APPROVE_OPTION) {
            myFilePath = fc.getSelectedFile().getPath();
            // Set file name to the label and repaint the frame
            myFileName.setText(fc.getSelectedFile().getName());
            remove(myFileName);
            GBC gbc = new GBC(1, 3);
            gbc.anchor = GridBagConstraints.LINE_START;
            add(myFileName, gbc);
            revalidate();
            repaint();
        }
    }

    /**
     * Called if the user click "Confirm" button.
     * Add the sketch (image file) to subproject.
     *
     * @author Jiameng Li
     */
    private void confirmAction() {
        final String name = myNameField.getText();
        if (mySubproject.createSketch(name, myFilePath)) {
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
