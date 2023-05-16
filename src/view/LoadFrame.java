package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class LoadFrame extends JFrame implements GUIFrame{

    public LoadFrame() {
        super("Crafty Companion - Load");
        start();
    }

    private void start() {
        JPanel mainPanel;
        JButton loadButton;
        JButton cancelButton;

        final double screenWidth = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        final double screenHeight = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        final Dimension frameSize = new Dimension((int) screenWidth / 4, (int) screenHeight / 2);
        setSize(frameSize);
        setResizable(false);
        //Center it.
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // Create the Cancel and Load buttons
        cancelButton = new JButton("Cancel");
        cancelButton.setFont(BODY_FONT);
        cancelButton.setBackground(FOREGROUND_COLOR);
        cancelButton.setForeground(BACKGROUND_COLOR);
        //Add Cancel button ActionListener of closing frame an opening WelcomeFrame
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Create and show the new frame
                WelcomeFrame welcomeFrame = new WelcomeFrame();
                welcomeFrame.setVisible(true);
                // Close the current frame
                dispose();
            }
        });

        loadButton = new JButton("Load");
        loadButton.setFont(BODY_FONT);
        loadButton.setBackground(FOREGROUND_COLOR);
        loadButton.setForeground(BACKGROUND_COLOR);

        // Create a panel for the buttons
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 15, 5));
        buttonPanel.setBackground(BACKGROUND_COLOR);
        buttonPanel.setForeground(FOREGROUND_COLOR);
        buttonPanel.add(cancelButton);
        buttonPanel.add(loadButton);

        // Create the main panel and add the button panel
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(BACKGROUND_COLOR);
        mainPanel.setForeground(FOREGROUND_COLOR);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Add the main panel to the frame
        add(mainPanel);

        setVisible(true);
    }

    public static void main(String[] args) {
        LoadFrame frame = new LoadFrame();
    }
}
