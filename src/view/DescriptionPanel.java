package view;

import javax.swing.*;

import model.Description;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * This class will create a panel on which to display the Description for the user to view.
 * @author Devin Peevy, Cameron Gregoire
 */
public class DescriptionPanel extends JPanel implements WorkPanel, PropertyChangeListener {

    //FIELDS:

    Description description;

    // CONSTRUCTORS

    public DescriptionPanel(Description description) {
        this.description = description;
        arrangePanel();
    }

    // INSTANCE METHODS

    /**
     * This method tells the Panel what to do when it receives a PropertyChangeEvent from the EditDescriptionFrame.
     * It rearranges the text on the panel, as well as overwrites the desc.txt file to save it persistently.
     * @param pce A PropertyChangeEvent object describing the event source
     *          and the property that has changed.
     */
    @Override
    public void propertyChange(PropertyChangeEvent pce) {
        // Update the Description and save it.
        String newDesc = (String) pce.getNewValue();
        description.setDescription(newDesc);
        description.writeToTXT();
        // Edit the panel.
        removeAll();
        arrangePanel();
        revalidate();
        repaint();
        setVisible(true);
    }

    /**
     * Arranges the components in the panel.
     *
     * @author Devin Peevy, Cameron Gregoire
     */
    private void arrangePanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(BACKGROUND_COLOR);
        setForeground(FOREGROUND_COLOR);

        // Create a panel for the name label
        JPanel nameLabelPanel = new JPanel();
        nameLabelPanel.setOpaque(false);
        nameLabelPanel.setAlignmentX(CENTER_ALIGNMENT);
        JLabel nameLabel = textArea(description.getProjectName() + " : Description", CENTER_ALIGNMENT, HEADING_FONT);
        nameLabelPanel.add(nameLabel);

        // Create a panel for the description text
        JPanel descriptionPanel = new JPanel();
        descriptionPanel.setOpaque(false);
        descriptionPanel.setLayout(new BorderLayout());
        JTextArea descriptionText = new JTextArea(description.getDescription());
        descriptionText.setFont(BODY_FONT);
        descriptionText.setBackground(BACKGROUND_COLOR);
        descriptionText.setForeground(FOREGROUND_COLOR);
        descriptionText.setLineWrap(true);
        descriptionText.setEditable(false);
        descriptionText.setWrapStyleWord(true);

        // Add padding/margins to the description panel
        int padding = 15;
        descriptionPanel.setBorder(BorderFactory.createEmptyBorder(padding, padding, padding, padding));

        descriptionPanel.add(descriptionText, BorderLayout.CENTER);

        // Create a panel for the edit button
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setAlignmentX(CENTER_ALIGNMENT);
        JButton editButton = new JButton("Edit");
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editAction();
            }
        });
        buttonPanel.add(editButton);

        // Add the sub-panels to the main panel
        add(Box.createVerticalGlue());
        add(nameLabelPanel);
        add(Box.createVerticalStrut(10));
        add(descriptionPanel);
        add(Box.createVerticalStrut(10));
        add(buttonPanel);
        add(Box.createVerticalGlue());
    }

    /**
     * This method opens up a new EditDescriptionFrame for the user to update their Description.
     * @author Devin Peevy
     */
    private void editAction() {
        new EditDescriptionFrame(description, this);
    }

    /**
     * This method provides a JLabel for the panel to display.
     * @author Devin Peevy
     * @param text The text to show.
     * @param alignmentX CENTER, LEFT, or RIGHT.
     * @param font The font for the text.
     * @return the desired JLabel.
     */
    private JLabel textArea(final String text, final float alignmentX, final Font font) {
        JLabel textArea = new JLabel(text);
        textArea.setAlignmentX(alignmentX);
        textArea.setFont(font);
        textArea.setBackground(BACKGROUND_COLOR);
        textArea.setForeground(FOREGROUND_COLOR);
        return textArea;
    }

}
