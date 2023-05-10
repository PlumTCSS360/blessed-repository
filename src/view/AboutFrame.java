package view;

import model.About;
import model.Person;

import javax.swing.*;
import java.awt.*;

/**
 * This class will show a Frame with the user's information (name and email) as well as the information of the
 * developers.
 * @author Devin Peevy
 * @version 0.1
 */
public final class AboutFrame {

    /** This holds all the information which is presented on the screen. */
    private final About about;

    /** This is the frame on which everything is presented. */
    private final JFrame frame;

    /**
     * This constructor creates a new instance of an AboutFrame.
     */
    public AboutFrame() {
        //Initialize parameters
        about = new About();
        frame = new JFrame("Crafty Companion v" + about.getVersion() + " - About");
        //Display the frame.
        start();
    }

    /**
     * This method sets the close operation and size, centers and displays the frame.
     */
    public void start() {
        //Set the size.
        final double screenWidth = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        final double screenHeight = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        final Dimension frameSize = new Dimension( (int) screenWidth/3,  (int) screenHeight/3);
        frame.setSize(frameSize);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        displayInfo();
        //Make it show up.
        frame.setVisible(true);
    }

    /**
     * This private method builds the content onto the frame.
     */
    private void displayInfo() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.DARK_GRAY);
        panel.setForeground(Color.LIGHT_GRAY);
        String text = compileText();
        JLabel textBlock = new JLabel(text);
        panel.add(textBlock);
        frame.add(panel);
    }

    /**
     * This private method assembles the text to be presented on the frame.
     * @return A string (with many newline characters) with all the necessary information.
     */
    private String compileText() {
        StringBuilder sb = new StringBuilder();
        sb.append("Version number: ").append(about.getVersion()).append("\n");
        sb.append("\n");
        sb.append("User information:\n");
        sb.append(about.getUser()).append("\n");
        sb.append("\n");
        sb.append("Developer information:\n");
        for (Person dev : about.getDevelopers()) {
            sb.append(dev).append("\n");
        }
        return sb.toString();
    }

    public static void main(String[] args) {

    }
}
