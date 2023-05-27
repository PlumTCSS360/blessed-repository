package view;

import javax.swing.*;

import model.Budget;
import model.Description;
import model.GBC;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.FileNotFoundException;

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

    // PRIVATE HELPER METHODS

    private void arrangePanel() {
        setBackground(BACKGROUND_COLOR);
        setForeground(FOREGROUND_COLOR);
        setLayout(new GridBagLayout());
        GBC gbc = new GBC(0, 0);
        add(textArea(description.getProjectName() + " : Description", CENTER_ALIGNMENT, HEADING_FONT), gbc);
        gbc = new GBC(0, 1);
        add(textArea(description.getDescription(), LEFT_ALIGNMENT, BODY_FONT), gbc);
        gbc = new GBC(0, 2);
        JButton editButton = new JButton("Edit");
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editAction();
            }
        });
        gbc = new GBC(0, 3);
        add(editButton, gbc);

    }

    private void editAction() {
        new EditDescriptionFrame(description, this);
    }

    private JLabel textArea(final String text, final float alignmentX, final Font font) {
        JLabel textArea = new JLabel(text);
        textArea.setAlignmentX(alignmentX);
        textArea.setFont(font);
        textArea.setBackground(BACKGROUND_COLOR);
        textArea.setForeground(FOREGROUND_COLOR);
        return textArea;
    }

    // MAIN METHOD (FOR TESTING)

//    public static void main(final String[] args) throws FileNotFoundException {
//        JFrame frame = new JFrame();
//        Description desc = new Description("data/Kitchen Remodel", "This is my long description.");
//        desc.writeToTXT();
//        frame.add(new DescriptionPanel(Description.loadDescriptionFromTXT("data/Kitchen Remodel/desc.txt")));
//        EventQueue.invokeLater(new Runnable() {
//            @Override
//            public void run() {
////                new WelcomeFrame();
////                new AboutFrame();
//                frame.setSize(700, 700);
//                frame.setBackground(BACKGROUND_COLOR);
//                frame.setLocationRelativeTo(null);
//                frame.setVisible(true);
//                frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//            }
//        });
//    }

    @Override
    public void propertyChange(PropertyChangeEvent pce) {
        String newDesc = (String) pce.getNewValue();
        description.setDescription(newDesc);
        description.writeToTXT();
        removeAll();
        arrangePanel();
        revalidate();
        repaint();
        setVisible(true);
    }
}
