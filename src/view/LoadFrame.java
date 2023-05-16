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

        final double screenWidth = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        final double screenHeight = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        final Dimension frameSize = new Dimension((int) screenWidth / 4, (int) screenHeight / 2);
        setSize(frameSize);
        setResizable(false);
        //Center it.
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setLayout(new GridBagLayout());
        createCancelButton();
        createLoadButton();

        // Create the main panel and add the button panel
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(BACKGROUND_COLOR);
        mainPanel.setForeground(FOREGROUND_COLOR);

        // Add the main panel to the frame
        add(mainPanel);

        setVisible(true);
    }

    private void createLoadButton(){
        JButton loadButton = new JButton("Load");
        loadButton.setPreferredSize(new Dimension(100, 25));
        loadButton.setFont(BODY_FONT);
        loadButton.setBackground(FOREGROUND_COLOR);
        loadButton.setForeground(BACKGROUND_COLOR);

        GridBagConstraints gbc2 = new GridBagConstraints();
        gbc2.gridx = GridBagConstraints.RELATIVE;
        gbc2.gridy = GridBagConstraints.RELATIVE;
        gbc2.anchor = GridBagConstraints.SOUTHEAST;
        gbc2.weightx = 1.0;
        gbc2.weighty = 1.0;
        gbc2.insets = new Insets(10, 10, 10, 10);

        add(loadButton, gbc2);

    }

    private void createCancelButton(){
        // Create the Cancel and Load buttons
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setPreferredSize(new Dimension(100, 25));
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
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.anchor = GridBagConstraints.SOUTHWEST;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.insets = new Insets(10, 10, 10, 10);
        add(cancelButton, gbc);
    }

    public static void main(String[] args) {
        LoadFrame frame = new LoadFrame();
    }
}
