package view;

import javax.swing.*;
import javax.swing.plaf.metal.MetalButtonUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This is the initial screen when a user starts this application.
 * It prompts the user to either create a new project or load an existing one.
 */

//Taylor Merwin
//public class WelcomeFrame {
//
//    private static final Dimension BUTTON_SIZE = new Dimension(200, 100);
//
//    final private JFrame frame;
//    final private JButton newButton;
//    final private JButton loadButton;
//
//    public WelcomeFrame() {
//        //Initialize parameters.
//        frame = new JFrame("Crafty Companion - Welcome");
//        newButton = new JButton("Start a new project.");
//        loadButton = new JButton("Load an existing project.");
//        //Load Dimensions, ActionListeners into buttons.
//        setUpButtons();
//        //Display the frame.
//        start();
//    }
//
//    /**
//     * This method sets the close operation and size, centers and displays the frame.
//     */
//    public void start() {
//        //Terminate program upon pushing the X.
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        //Set the size.
//        final double screenWidth = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
//        final double screenHeight = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
//        final Dimension frameSize = new Dimension( (int) screenWidth * 2 / 3,
//                (int) screenHeight * 2 / 3);
//        frame.setSize(frameSize);
//        //Properly place the buttons on the frame.
//        frame.setLayout(new GridLayout(1, 2));
//        placeButtons();
//        //Center the Frame on the screen.
//        frame.setLocationRelativeTo(null);
//        //Make it show up.
//        frame.setVisible(true);
//    }
//
//    /**
//     * This method sets sizes and adds ActionListeners to the buttons.
//     */
//    private void setUpButtons() {
//        newButton.setSize(BUTTON_SIZE);
//        loadButton.setSize(BUTTON_SIZE);
//
//        newButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                new NewFrame();
//                frame.dispose();
//            }
//        });
//
//        loadButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                new LoadFrame();
//                frame.dispose();
//            }
//        });
//    }
//
//    private void placeButtons() {
//        JPanel lp = new JPanel();
//        JPanel rp = new JPanel();
//        frame.add(lp);
//        frame.add(rp);
//        lp.add(newButton);
//        rp.add(loadButton);
//    }
//}

public class WelcomeFrame extends JFrame {

    private JButton newButton;
    private JButton loadButton;

    public WelcomeFrame() {

        final double screenWidth = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        final double screenHeight = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        final Dimension frameSize = new Dimension((int) screenWidth * 2 / 3, (int) screenHeight * 2 / 3);



        // Set frame properties
        setTitle("Crafty Companion");
        setSize(frameSize);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame

        // Create components
        JLabel welcomeLabel = new JLabel("Welcome to Crafty Companion!", SwingConstants.CENTER);
        JButton newButton = new JButton("New");
        JButton loadButton = new JButton("Load");

        // Set layout and add components
        setLayout(new GridBagLayout());

        // Create constraints for components
        GridBagConstraints gbc = new GridBagConstraints();


        //Add welcome label with dynamic font size
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 0.5;
        gbc.insets = new Insets(10, 10, 10, 10);
        add(welcomeLabel, gbc);

        // Add New button with dynamic resizing
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 0.5;
        gbc.weighty = 0.5;
        gbc.anchor = GridBagConstraints.SOUTHWEST;
        add(newButton, gbc);

        // Add Load button with dynamic resizing
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 0.5;
        gbc.weighty = 0.5;
        gbc.anchor = GridBagConstraints.SOUTHEAST;
        add(loadButton, gbc);

        // Make the frame visible
        setVisible(true);
    }


}
