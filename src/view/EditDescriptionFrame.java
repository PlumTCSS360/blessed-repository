package view;

import model.Description;
import model.GBC;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeSupport;

public class EditDescriptionFrame extends JFrame implements GUIFrame {

    // FIELDS

    private Description description;

    private DescriptionPanel listener;

    private JTextArea descriptionArea;

    private PropertyChangeSupport pcs;

    // CONSTRUCTOR

    public EditDescriptionFrame(Description description, DescriptionPanel listener) {
        super(description.getProjectName() + " - Edit Description");
        this.description = description;
        this.listener = listener;
        this.descriptionArea = new JTextArea(description.getDescription(), 10, 20);
        this.pcs = new PropertyChangeSupport(this);

        // Enable line wrap and word wrap
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);

        start();
    }

    // PRIVATE METHODS

    private void start() {
        int width = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 4;
        int height = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2;
        setSize(new Dimension(width, height));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setBackground(BACKGROUND_COLOR);
        setForeground(FOREGROUND_COLOR);
        arrangePanel();
        setVisible(true);
    }

    private void arrangePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(BACKGROUND_COLOR);
        panel.setForeground(FOREGROUND_COLOR);
        GBC gbc = new GBC(0, 0, 2, 1);
        String title = description.getProjectName() + ": Description";
        panel.add(textArea(title, CENTER_ALIGNMENT, HEADING_FONT), gbc);
        gbc = new GBC(0, 1, 2, 1);
        panel.add(new JScrollPane(descriptionArea), gbc); // Use a JScrollPane for the text area

        JButton cancel = new JButton("Cancel");
        JButton apply = new JButton("Apply");

        Dimension buttonSize = new Dimension(100, 30);
        cancel.setPreferredSize(buttonSize);
        apply.setPreferredSize(buttonSize);

        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        apply.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                applyAction();
            }
        });
        gbc = new GBC(0, 3);
        panel.add(cancel, gbc);
        gbc = new GBC(1, 3);
        panel.add(apply, gbc);
        add(panel);
    }

    private void applyAction() {
        pcs.firePropertyChange("editDescription", description.getDescription(), descriptionArea.getText()); // Change: Use descriptionArea.getText()
        dispose();
    }

    private JLabel textArea(final String text, final float alignmentX, final Font font) {
        JLabel textArea = new JLabel(text);
        textArea.setAlignmentX(alignmentX);
        textArea.setFont(font);
        textArea.setBackground(BACKGROUND_COLOR);
        textArea.setForeground(FOREGROUND_COLOR);
        return textArea;
    }
}