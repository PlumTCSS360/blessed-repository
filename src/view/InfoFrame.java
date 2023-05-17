package view;

import model.About;
import model.GBC;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * This class will create a screen which will solicit from the user their name and email address.
 * It will store this info in a file called user_info.txt so that it does not pop up every time the program is opened.
 * @author Devin Peevy
 * @version 0.1
 */
public class InfoFrame implements GUIFrame {

    private final JFrame frame;

    private final JTextField nameBox;

    private final JTextField emailBox;

    private String userName;

    private String userEmail;

    /**
     * This constructor will initiate a new InfoFrame. It will call start() in order to load the necessary components
     * onto the frame and then display it.
     */
    public InfoFrame() {
        frame = new JFrame("Crafty Companion - Info");
        userName = null;
        userEmail = null;
        nameBox = new JTextField(20);
        emailBox = new JTextField(20);
        start();
    }

    /**
     * This method will set up the InfoFrame how I want it atn
     */
    private void start() {
        //Set the size.
        final double screenWidth = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        final double screenHeight = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        final Dimension frameSize = new Dimension((int) screenWidth / 4, (int) screenWidth / 8);
        frame.setSize(frameSize);
        frame.setResizable(false);
        //Center it.
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //Show everything I want it to show.
        displayText();
        //Make it show up.
        frame.setVisible(true);
    }

    private void displayText() {
        //Initiate the panel
        JPanel panel = new JPanel();
        panel.setSize(frame.getSize());
        panel.setBackground(BACKGROUND_COLOR);
        LayoutManager layout = new GridBagLayout();
        panel.setLayout(layout);
        //Put in the greeting.
        GBC gbc = new GBC(0, 0, 3, 1);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GBC.NORTH;
        gbc.ipady = 20;
        panel.add(greeting(), gbc);

        gbc = new GBC(0, 1);
        gbc.ipady = 20;
        gbc.ipadx = 20;
        panel.add(nameQ(), gbc);

        gbc = new GBC(0, 2);
        gbc.ipady = 20;
        gbc.ipadx = 20;
        panel.add(emailQ(), gbc);

        gbc = new GBC(1, 1);
        nameBox.setFont(BODY_FONT);
        panel.add(nameBox, gbc);

        gbc = new GBC(1, 2);
        emailBox.setFont(BODY_FONT);
        panel.add(emailBox, gbc);

        gbc = new GBC(1, 3);
        gbc.ipady = 20;
        panel.add(okButton(), gbc);
        //Add the panel to the frame
        frame.add(panel);
    }

    private JLabel greeting() {
        JLabel greeting = new JLabel("Welcome to Crafty Companion!");
        //greeting.setAlignmentX(Component.CENTER_ALIGNMENT);
        greeting.setFont(HEADING_FONT);
        greeting.setBackground(BACKGROUND_COLOR);
        greeting.setForeground(FOREGROUND_COLOR);
        return greeting;
    }

    private JLabel nameQ() {
        JLabel question = textArea("What is your name?", JLabel.LEFT_ALIGNMENT,
                BODY_FONT);
        return question;
    }

    private JLabel emailQ() {
        JLabel emailQ = textArea("What is your email address?", JTextArea.LEFT_ALIGNMENT,
                BODY_FONT);
        return emailQ;
    }

    private JButton okButton() {
        JButton ok = new JButton("Okay");
        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                okButtonAction();
            }
        });
        ok.setBackground(FOREGROUND_COLOR);
        ok.setForeground(BACKGROUND_COLOR);
        return ok;
    }

    private void okButtonAction() {
        boolean hasFailed = false;
        userName = nameBox.getText();
        userEmail = emailBox.getText();
        if (userName.contains(",")) {
            JOptionPane.showMessageDialog(null, "The name field may not contain any commas!");
            hasFailed = true;
        }
        else if (userEmail.contains(",")) {
            JOptionPane.showMessageDialog(null, "The email field may not contain any commas!");
            hasFailed = true;
        }
        else if (userName.isEmpty()) {
            JOptionPane.showMessageDialog(null, "The name field cannot be left blank!");
            hasFailed = true;
        }
        else if (userEmail.isEmpty()) {
            JOptionPane.showMessageDialog(null, "The email field cannot be left blank!");
            hasFailed = true;
        }
        else if (!isEmail(userEmail)) {
            JOptionPane.showMessageDialog(null, "That email doesn't look right. Try again!");
            hasFailed = true;
        }

        if (!hasFailed) {
            try {
                FileWriter fw = new FileWriter(About.USER_INFO_FILE_PATH);
                String data = "name,email\n" + userName + "," + userEmail;
                fw.write(data);
                fw.close();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            frame.dispose();
            new WelcomeFrame();
        }
        else {
            hasFailed = false;
        }
    }

    private boolean isEmail(String string) {
        char[] strArray = string.toCharArray();
        boolean hasHitAt = false;
        boolean hasHitLastDot = false;
        int atLocation = 0;
        int lastDotLocation = 0;
        for (int i = 0; i < strArray.length; i++) {
            if (strArray[i] == '@') {
                atLocation = i;
                if (hasHitAt) {
                    return false;
                }
                hasHitAt = true;
            }
            if (strArray[i] == '.') {
                if (hasHitAt) {
                    hasHitLastDot = true;
                    lastDotLocation = i;
                }
            }
        }
        if (hasHitLastDot) {
            if (atLocation != 0 && lastDotLocation - atLocation > 1 && lastDotLocation != strArray.length - 1) {
                return true;
            }
            return false;
        }
        return false;
    }

    private JLabel textArea(String text, float alignmentX, Font font) {
        JLabel textArea = new JLabel(text);
        textArea.setAlignmentX(alignmentX);
        textArea.setFont(font);
        textArea.setBackground(BACKGROUND_COLOR);
        textArea.setForeground(FOREGROUND_COLOR);
        return textArea;
    }
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new InfoFrame();
            }
        });
    }
}
