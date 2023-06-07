package view;

import model.FileAccessor;
import model.GBC;
import model.Project;
import model.Subproject;

import javax.swing.*;
import java.awt.*;

/**
 * The frame that allow the user to add a note by entering name and content of the note.
 *
 * @author Jiameng Li
 * @version 0.3
 */
public class AddNoteFrame extends JFrame implements GUIFrame {

    /** The subproject the new note belongs to. */
    private final Subproject mySubproject;

    /** The project frame. */
    private final ProjectFrame myFrame;

    /** Text field to enter the name of the note. */
    private final JTextField myNameField;

    /** Text area to enter the content of the note. */
    private final JTextArea myContentArea;

    /**
     * Construct a frame to add a note.
     *
     * @author Jiameng Li
     * @param theSubproject The subproject the new note belongs to.
     * @param theFrame The project frame.
     */
    public AddNoteFrame(final Subproject theSubproject, final ProjectFrame theFrame) {
        super("Add a new note");
        mySubproject = theSubproject;
        myFrame = theFrame;
        myNameField = new JTextField(35);
        myContentArea = new JTextArea(20, 60);
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

        // Label for note content text area
        gbc.gridy = 2;
        label = createLabel("Note Content:");
        add(label, gbc);

        // Note content text area
        gbc.gridy = 3;
        myContentArea.setLineWrap(true);
        JScrollPane pane = new JScrollPane(myContentArea);
        add(pane, gbc);

        // Add a button to cancel
        gbc.gridwidth = 1;
        gbc.gridy = 4;
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
     * Add a note to the subproject.
     *
     * @author Jiameng Li, Cameron Gregoire
     */
    private void confirmAction() {
        final String name = myNameField.getText();
        final String content = myContentArea.getText();
        if (mySubproject.createNote(name, content)) {
            // Save content to a text file
            String filePath = "Data/" + Project.getProjectName() + "/" + mySubproject.getName() + "/Notes/" + name + ".txt";
            FileAccessor.writeTxtFile(filePath, content);
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
