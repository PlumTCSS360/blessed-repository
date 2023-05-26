package view;

import model.About;
import model.Person;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Display's the user's information and system information on the screen.
 * @author Taylor Merwin
 * @version 0.2
 */
public final class AboutFrame {

    /** This holds all the information which is presented on the screen. */
   private final About about;

    /**
     * This constructor creates a new instance of an AboutFrame.
     * @author Taylor Merwin
     */
    public AboutFrame() {

        about = new About();
        JFrame frame = new JFrame("Crafty Companion - About");
        JPanel panel = new JPanel(new GridLayout(4, 1));
        frame.add(panel);

        String os = System.getProperty("os.name");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy");
        LocalDate today = LocalDate.now();
        String dateString = today.format(formatter);
        final double screenWidth = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        final double screenHeight = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        final Dimension frameSize = new Dimension( (int) screenWidth/3,  (int) screenHeight/3);

        frame.setSize(frameSize);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        panel.setBackground(Color.DARK_GRAY);
        panel.setForeground(Color.LIGHT_GRAY);

        JLabel nameLabel = new JLabel(getName());
        JLabel emailLabel = new JLabel(getEmail());
        JLabel osLabel = new JLabel(os);
        JLabel dateLabel = new JLabel(dateString);

        JLabel[] labels = {nameLabel, emailLabel, osLabel, dateLabel};
        for (JLabel label : labels) {
        	label.setFont(new Font("Arial", Font.BOLD, (int) screenHeight/ 30));
            label.setForeground(Color.WHITE);
            label.setHorizontalAlignment(JLabel.CENTER);
            panel.add(label);
        }
    }

    /**
     * @return the name of the user.
     * @author Taylor Merwin
     */
    private String getName() {
        return about.getUser().getName();
    }

    /**
     * @return the email of the user.
     * @author Taylor Merwin
     */
    private String getEmail() {
        return about.getUser().getEmail();
    }


}
