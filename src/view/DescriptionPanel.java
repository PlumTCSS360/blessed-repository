package view;

import javax.swing.*;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import model.Description;

import java.awt.*;


/**
 * The DescriptionPanel class extends JPanel and represents a panel that displays a description of a project.
 * It provides a text area where the description can be edited, and automatically saves the changes to a text file.
 * @author Devin Peevy, Cameron Gregoire
 */
public class DescriptionPanel extends JPanel {

    Description description; // The description object associated with this panel
    JTextArea descriptionTextArea; // The text area for editing the description

    /**
     * Constructs a DescriptionPanel object with the given description.
     *
     * @param description The Description object representing the project description
     * @author Cameron Gregoire
     */
    public DescriptionPanel(Description description) {
        this.description = description;
        arrangePanel();
    }

    /**
     * Sets up the layout and components of the panel.
     * @author Devin Peevy, Cameron Gregoire
     */
    private void arrangePanel() {
        setLayout(new BorderLayout());
        setBackground(Color.DARK_GRAY);
        setForeground(Color.LIGHT_GRAY);

        JPanel titleLabelPanel = new JPanel();
        titleLabelPanel.setOpaque(false);
        titleLabelPanel.setAlignmentX(CENTER_ALIGNMENT);
        titleLabelPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        JLabel titleLabel = new JLabel(description.getProjectName() + " : Description");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(Color.LIGHT_GRAY);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabelPanel.add(titleLabel);

        JPanel descriptionPanel = new JPanel();
        descriptionPanel.setOpaque(false);
        descriptionPanel.setLayout(new BorderLayout());

        descriptionTextArea = new JTextArea(description.getDescription());
        JScrollPane scrollPane = new JScrollPane(descriptionTextArea);
        descriptionTextArea.setFont(new Font("Arial", Font.PLAIN, 16));
        descriptionTextArea.setBackground(Color.DARK_GRAY);
        descriptionTextArea.setForeground(Color.LIGHT_GRAY);
        descriptionTextArea.setLineWrap(true);
        descriptionTextArea.setWrapStyleWord(true);

        descriptionPanel.add(scrollPane, BorderLayout.CENTER);

        add(titleLabelPanel, BorderLayout.NORTH);
        add(descriptionPanel, BorderLayout.CENTER);

        descriptionTextArea.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                saveDescription(); // Call the saveDescription() method whenever text is inserted
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                saveDescription(); // Call the saveDescription() method whenever text is removed
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                saveDescription(); // Call the saveDescription() method whenever text is changed
            }
        });
    }

    /**
     * Saves the modified description to the associated Description object and writes it to a text file.
     * @author Cameron Gregoire
     */
    private void saveDescription() {
        String newDesc = descriptionTextArea.getText();
        description.setDescription(newDesc);
        description.writeToTXT();
    }
}
