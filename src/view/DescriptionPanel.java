package view;

import javax.swing.*;

import model.Description;
import model.GBC;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * This class will create a panel on which to display the Description for the user to view.
 * @author Devin Peevy
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

    // PRIVATE HELPER METHODS

    private void arrangePanel() {
        setBackground(BACKGROUND_COLOR);
        setForeground(FOREGROUND_COLOR);
        setLayout(new GridBagLayout());
        GBC gbc = new GBC(0, 0);
        add(textArea(description.getProjectName() + " : Description", CENTER_ALIGNMENT, HEADING_FONT), gbc);
        gbc = new GBC(0, 1);
        gbc.fill = GBC.HORIZONTAL;
        JTextArea descriptionText = new JTextArea(description.getDescription());
        descriptionText.setFont(BODY_FONT);
        descriptionText.setBackground(BACKGROUND_COLOR);
        descriptionText.setForeground(FOREGROUND_COLOR);
        descriptionText.setLineWrap(true);
        descriptionText.setEditable(false);
        descriptionText.setWrapStyleWord(true);
        descriptionText.setSize(this.getWidth(), this.getHeight());
        add(descriptionText, gbc);
        gbc = new GBC(0, 2);
        JButton editButton = new JButton("Edit");
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editAction();
            }
        });
        add(editButton, gbc);

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

    // MAIN METHOD (FOR TESTING)

    /*
    public static void main(final String[] args) throws FileNotFoundException {
        JFrame frame = new JFrame();
        Description desc = Description.loadDescriptionFromTXT("data/Kitchen Remodel/desc.txt");
        //desc.writeToTXT();
        frame.add(new DescriptionPanel(desc));
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                frame.setSize(700, 700);
                frame.setBackground(BACKGROUND_COLOR);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
                frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            }
        });
    }
    */

}
