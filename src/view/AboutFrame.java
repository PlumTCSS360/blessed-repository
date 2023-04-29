package view;

import model.About;
import model.Person;

import javax.swing.*;
import java.awt.*;

public class AboutFrame {

    private final About about;

    private final JFrame frame;


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
        final Dimension frameSize = new Dimension( (int) screenWidth / 3,
                (int) screenHeight / 3);
        frame.setSize(frameSize);
        frame.setLocationRelativeTo(null);
        displayInfo();
        //Make it show up.
        frame.setVisible(true);
    }

    private void displayInfo() {
        JPanel panel = new JPanel();
        String text = compileText();
        JTextArea textBlock = new JTextArea(text);
        textBlock.setEditable(false);
        panel.add(textBlock);
        frame.add(panel);
    }

    private String compileText() {
        StringBuilder sb = new StringBuilder();
        sb.append("Version number: ").append(about.getVersion()).append("\n");
        sb.append("\n");
        sb.append("User information:\n");
        sb.append("Name: ").append(about.getUser().getName()).append("\n");
        sb.append("Email: ").append(about.getUser().getEmail()).append("\n");
        sb.append("\n");
        sb.append("Developer information:\n");
        for (Person dev : about.getDevelopers()) {
            sb.append(dev.getName()).append(", ").append(dev.getEmail()).append("\n");
        }
        return sb.toString();
    }
}
