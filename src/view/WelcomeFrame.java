package view;

import javax.swing.*;
import javax.swing.plaf.metal.MetalButtonUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;

/**
 * This class will create a new WelcomeFrame, the initial GUI.
 * @author Taylor Merwin
 */

//TODO: Make the font size dynamic


public class WelcomeFrame extends JFrame  implements GUIFrame {

    /**
     * This constructor will create a new WelcomeFrame.
     * @author Taylor Merwin
     */
    public WelcomeFrame() {

        final double screenWidth = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        final double screenHeight = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        final Dimension frameSize = new Dimension((int) screenWidth / 2, (int) screenHeight / 2);

        // Set frame properties
        setTitle("Crafty Companion");
        setSize(frameSize);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame

        //Create center panel and add it to the frame
        JPanel centerPanel = new JPanel();
        add(centerPanel, BorderLayout.CENTER);
        //Create south panel and add it to the frame
        JPanel southPanel = new JPanel();
        add(southPanel, BorderLayout.SOUTH);

        //Create components
        JLabel welcomeLabel = new JLabel("Welcome to Crafty Companion!", SwingConstants.CENTER);
        JButton newButton = new JButton("New");
        JButton loadButton = new JButton("Load");
        JButton importButton = new JButton("Import");
        JButton exportButton = new JButton("Export");

        Font buttonFont = new Font("Arial", Font.PLAIN, (int) (frameSize.getHeight() / 30));
        Font labelFont = new Font("Arial", Font.BOLD, (int) (frameSize.getHeight() / 20));

        //Set fonts
        welcomeLabel.setFont(labelFont);
        newButton.setFont(buttonFont);
        loadButton.setFont(buttonFont);
        importButton.setFont(buttonFont);
        exportButton.setFont(buttonFont);

        //Set colors
        centerPanel.setBackground(BACKGROUND_COLOR);
        centerPanel.setForeground(FOREGROUND_COLOR);
        southPanel.setBackground(BACKGROUND_COLOR);
        southPanel.setForeground(FOREGROUND_COLOR);
        welcomeLabel.setForeground(FOREGROUND_COLOR);
        newButton.setBackground(FOREGROUND_COLOR);
        newButton.setForeground(BACKGROUND_COLOR);
        loadButton.setBackground(FOREGROUND_COLOR);
        loadButton.setForeground(BACKGROUND_COLOR);
        importButton.setBackground(FOREGROUND_COLOR);
        importButton.setForeground(BACKGROUND_COLOR);
        exportButton.setBackground(FOREGROUND_COLOR);
        exportButton.setForeground(BACKGROUND_COLOR);

        // Add components to panels

        // Add welcome label with dynamic font size
        centerPanel.add(welcomeLabel, BorderLayout.CENTER);
        // Add New button with dynamic resizing
        southPanel.add(newButton);
        // Add Load button with dynamic resizing
        southPanel.add(loadButton);
        // Add Import button with dynamic resizing
        southPanel.add(importButton);
        // Add Export button with dynamic resizing
        southPanel.add(exportButton);

        // Add event listeners to buttons
        newButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new NewFrame();
                dispose();
            }
        });

        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LoadFrame();
                dispose();
            }
        });

        importButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String destinationDir = "data/";
                JFileChooser myFolderChooser = new JFileChooser();
                myFolderChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

                final int option = myFolderChooser.showOpenDialog(null);
                if (option == JFileChooser.APPROVE_OPTION) {
                    File selectedDir = myFolderChooser.getSelectedFile();
                    File newDir = new File(destinationDir + selectedDir.getName());
                    newDir.mkdir();

                    //loop through selectedDir and copy all files to newDir
                    File[] files = selectedDir.listFiles();
                    for (File file : files) {
                        if (file.isFile()) {
                            try {
                                FileReader in = new FileReader(file);
                                FileWriter out = new FileWriter(newDir + "/" + file.getName());
                                int c;
                                while ((c = in.read()) != -1) {
                                    out.write(c);
                                }
                                in.close();
                                out.close();
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                    }
                    }
            }
        });



        // Make the frame visible
        setVisible(true);
    }


}
