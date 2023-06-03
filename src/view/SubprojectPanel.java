package view;

import model.GBC;
import model.Subproject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SubprojectPanel extends JPanel implements WorkPanel {

    final Subproject mySubproject;

    final JTree myTree;

    public SubprojectPanel() {
        super(new GridBagLayout());
        mySubproject = null;
        myTree = null;
    }

    public SubprojectPanel(final Subproject theSubproject, final JTree theTree) {
        super(new GridBagLayout());
        mySubproject = theSubproject;
        myTree = theTree;
        setupPanel();
    }

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
        // TODO Add action listener
        add(button, gbc);

        // Add "New Note" button and prompt
        gbc = new GBC(0, 2, 1, 1);
        prompt = createLabel("To create a new note:", gbc);
        add(prompt, gbc);
        gbc = new GBC(1, 2, 1, 1);
        button = new JButton("New Note");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddNoteFrame(mySubproject.getNotesList(), myTree);
            }
        });
        add(button, gbc);

        // Add "New Sketch" button and prompt
        gbc = new GBC(0, 3, 1, 1);
        prompt = createLabel("To create a new sketch or image:", gbc);
        add(prompt, gbc);
        gbc = new GBC(1, 3, 1, 1);
        button = new JButton("New Sketch");
        // TODO Add action listener
        add(button, gbc);

        // Add "Compare All Options" button and prompt
        gbc = new GBC(0, 4, 1, 1);
        prompt = createLabel("To compare all options by cost:", gbc);
        add(prompt, gbc);
        gbc = new GBC(1, 4, 1, 1);
        button = new JButton("Compare All Options");
        // TODO Add action listener
        add(button, gbc);

        // Add "Compare Two Options" button and prompt
        gbc = new GBC(0, 5, 1, 1);
        prompt = createLabel("To compare two options with all details:", gbc);
        add(prompt, gbc);
        gbc = new GBC(1, 5, 1, 1);
        button = new JButton("Compare Two Options");
        // TODO Add action listener
        add(button, gbc);
    }

    private JLabel createLabel(final String thePrompt, final GBC theGBC) {
        JLabel prompt = new JLabel(thePrompt);
        prompt.setFont(SUBHEADING_FONT);
        prompt.setForeground(FOREGROUND_COLOR);
        prompt.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 30));
        theGBC.anchor = GridBagConstraints.LINE_START;
        theGBC.weightx = 1.0;
        theGBC.weighty = 0.2;
        return prompt;
    }

}
