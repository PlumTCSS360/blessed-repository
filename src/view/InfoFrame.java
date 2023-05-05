package view;

import model.About;
import model.GBC;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;

/**
 * This class will create a screen which will solicit from the user their name and email address.
 * It will store this info in a file called user_info.txt so that it does not pop up every time the program is opened.
 * @author Devin Peevy
 * @version 0.1
 */
public class InfoFrame {

    private final static Color BACKGROUND = Color.DARK_GRAY;

    private final static Color FOREGROUND = Color.lightGray;

    private final static String FONT_NAME = "Courier New";

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
     * This method will set up the InfoFrame how I want it and
     */
    private void start() {
        //Set the size.
        final double screenWidth = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        final double screenHeight = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        final Dimension frameSize = new Dimension( (int) screenWidth / 3,
                (int) screenHeight / 3);
        frame.setSize(frameSize);
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
        panel.setBackground(BACKGROUND);
        LayoutManager layout = new GridBagLayout();
        panel.setLayout(layout);
        //Put in the greeting.
        GridBagConstraints gbc = new GridBagConstraints();
        //gbc.ipady = 30;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(greeting(), gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.NONE;
        panel.add(nameQ(), gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(emailQ(), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(nameBox, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(emailBox, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        panel.add(okButton(), gbc);
        //Add the panel to the frame
        frame.add(panel);
    }

    private JLabel greeting() {
        JLabel greeting = new JLabel("Welcome to Crafty Companion!");
        //greeting.setAlignmentX(Component.CENTER_ALIGNMENT);
        greeting.setFont(new Font(FONT_NAME, Font.BOLD, 36));
        greeting.setBackground(BACKGROUND);
        greeting.setForeground(FOREGROUND);
        return greeting;
    }

    private JLabel nameQ() {
        JLabel question = textArea("What is your name?", JLabel.LEFT_ALIGNMENT,
                new Font(FONT_NAME, Font.BOLD, 24));
        return question;
    }

    private JLabel emailQ() {
        JLabel emailQ = textArea("What is your email address?", JTextArea.LEFT_ALIGNMENT,
                new Font(FONT_NAME, Font.BOLD, 24));
        return emailQ;
    }

    private JButton okButton() {
        JButton ok = new JButton("Okay");
        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                userName = nameBox.getText();
                userEmail = emailBox.getText();
                try {
                    FileWriter fw = new FileWriter(About.USER_INFO_FILE_PATH);
                    String data = "name, email\n" + userName + "," + userEmail;
                    fw.write(data);
                    fw.close();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                System.out.println(userName);
            }
        });
        return ok;
    }

    private JLabel textArea(String text, float alignmentX, Font font) {
        JLabel textArea = new JLabel(text);
        textArea.setAlignmentX(alignmentX);
        textArea.setFont(font);
        textArea.setBackground(BACKGROUND);
        textArea.setForeground(FOREGROUND);
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
