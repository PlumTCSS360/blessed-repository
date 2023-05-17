package view;

import model.GBC;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.awt.BorderLayout;

/**
 * This class creates a window allowing the user to create a new project from scratch.
 */
public class NewFrame implements GUIFrame {

    private final JFrame frame;

    private final JTextField nameBox;

    private final JTextField budgetBox;

    private final JTextArea descriptionBox;

    private String userName;

    private String userBudget;

    private String userExpense;

    private String userDescription;

    public NewFrame() {
        frame = new JFrame("Crafty Companion - New");
        userBudget = null;
        userDescription = null;
        userName = null;
        nameBox = new JTextField(20);
        budgetBox = new JTextField(20);
        descriptionBox = new JTextArea(10,20);
        descriptionBox.setLineWrap(true);
        start();
    }

    /**
     * This method will set up the InfoFrame how I want it atn
     */
    private void start() {
        //Set the size.
        final double screenWidth = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        final double screenHeight = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        final Dimension frameSize = new Dimension((int) screenWidth / 4, (int) screenHeight / 2);
        frame.setSize(frameSize);
        frame.setResizable(false);
        //Center it.
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //Show everything I want it to show.
        frame.setLayout(new BorderLayout());
        frame.add(displayText(),BorderLayout.CENTER);
        frame.add(buttonCreator(),BorderLayout.SOUTH);
        frame.add(greetingPanel(), BorderLayout.NORTH);
        //Make it show up.
        frame.setVisible(true);
    }
    private JPanel buttonCreator() {
        JPanel panel = new JPanel();
        panel.setSize(frame.getSize());
        panel.setBackground(BACKGROUND_COLOR);
        panel.setLayout(new FlowLayout());
        panel.add(createButton());
        panel.add(cancelButton());

        return panel;
    }
    private JPanel displayText() {
        //Initiate the panel
        JPanel panel = new JPanel();
        panel.setSize(frame.getSize());
        panel.setBackground(BACKGROUND_COLOR);
        LayoutManager layout = new GridBagLayout();
        panel.setLayout(layout);
        //Put in the greeting.
        GBC gbc = new GBC(1, 0, 3, 1);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GBC.NORTH;

        gbc = new GBC(1, 1);
        gbc.ipady = 20;
        gbc.ipadx = 20;
        panel.add(nameQ(), gbc);

        gbc = new GBC(1, 3);
        gbc.ipady = 20;
        gbc.ipadx = 20;
        panel.add(budgetQ(), gbc);

        gbc = new GBC(1, 5);
        gbc.ipady = 40;
        gbc.ipadx = 20;
        panel.add(descriptionM(), gbc);

        gbc = new GBC(1, 2);
        budgetBox.setFont(BODY_FONT);
        panel.add(nameBox, gbc);

        gbc = new GBC(1, 4);
        budgetBox.setFont(BODY_FONT);
        panel.add(budgetBox, gbc);

        gbc = new GBC(1, 6);
        descriptionBox.setFont(BODY_FONT);
        panel.add(descriptionBox, gbc);

        return panel;
    }

    private JPanel greetingPanel() {

        JPanel panel = new JPanel();
        panel.setSize(frame.getSize());
        panel.setBackground(BACKGROUND_COLOR);
        panel.add(greeting());

        return panel;
    }
    private JLabel greeting() {
        JLabel greeting = new JLabel("You are creating your new project!");
        greeting.setAlignmentX(Component.CENTER_ALIGNMENT);
        greeting.setFont(HEADING_FONT);
        greeting.setBackground(BACKGROUND_COLOR);
        greeting.setForeground(FOREGROUND_COLOR);
        return greeting;
    }

    private JLabel nameQ() {
        JLabel nameQ = textArea("What is your name of your project?", JLabel.LEFT_ALIGNMENT,
                BODY_FONT);
        return nameQ;
    }

    private JLabel budgetQ() {
        JLabel budgetQ = textArea("What is your spending limit?", JLabel.LEFT_ALIGNMENT,
                BODY_FONT);
        return budgetQ;
    }

    private JLabel descriptionM() {
        JLabel descriptionM = textArea("Write any description of your project please.", JTextArea.LEFT_ALIGNMENT,
                BODY_FONT);
        return descriptionM;
    }

    private JButton createButton() {
        JButton create = new JButton("Create");
        create.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createAction();
            }
        });
        create.setBackground(FOREGROUND_COLOR);
        create.setForeground(BACKGROUND_COLOR);
        return create;
    }

    private JButton cancelButton() {
        JButton cancel = new JButton("cancel");
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    cancelAction();
            }
        });
        cancel.setBackground(FOREGROUND_COLOR);
        cancel.setForeground(BACKGROUND_COLOR);
        return cancel;
    }
    private void cancelAction(){
            int result = JOptionPane.showConfirmDialog(null, "Do you want to cancel?");

            if(result == JOptionPane.YES_OPTION){
                frame.dispose();
                new WelcomeFrame();
            }

    }
    private void createAction() {
        boolean hasFailed = false;
        userName = nameBox.getText();
        userBudget = budgetBox.getText();
        userDescription = descriptionBox.getText();

        int idx = userBudget.indexOf(".");
        int countDot = 0;
        String afterDot = userBudget.substring(idx+1);

        if(afterDot.length() > 2){
            JOptionPane.showMessageDialog(null, "Budget Error: Your Budget can't have more than 2 floating point");
            hasFailed = true;
        }

        for(int i = 0; i < userBudget.length(); i++){
            if((userBudget.charAt(i) <48 && userBudget.charAt(i) != 46) || (userBudget.charAt(i) > 57 && userBudget.charAt(i) != 46)){
                JOptionPane.showMessageDialog(null, "Budget Error: You entered invalid budget!");
                hasFailed = true;
            } else if (userBudget.charAt(i) == 46){
                countDot++;
            }

            if(countDot > 1){
                JOptionPane.showMessageDialog(null, "Budget Error: You can't have more than 1 dot!");
                hasFailed = true;
            }
        }

        idx = userExpense.indexOf(".");
        countDot = 0;
        afterDot = userExpense.substring(idx+1);

        if(afterDot.length() > 2){
            JOptionPane.showMessageDialog(null, "Expense Error: Your expense can't have more than 2 floating point");
            hasFailed = true;
        }

        for(int i = 0; i < userExpense.length(); i++){
            if((userExpense.charAt(i) <48 && userExpense.charAt(i) != 46) || (userExpense.charAt(i) > 57 && userExpense.charAt(i) != 46)){
                JOptionPane.showMessageDialog(null, "Expense Error: You entered invalid expense!");
                hasFailed = true;
            } else if (userExpense.charAt(i) == 46){
                countDot++;
            }

            if(countDot > 1){
                JOptionPane.showMessageDialog(null, "Expense Error: You can't have more than 1 dot!");
                hasFailed = true;
            }
        }

        if (!hasFailed) {
            File file = new File("C:/Users/Owner/blessed-repository/src/data/" + userName +".txt");

            try {
                if (file.createNewFile()) {

                } else {
                    JOptionPane.showMessageDialog(null, "Create Error: Same Name Found!");
                    hasFailed = true;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                FileWriter fw = new FileWriter("C:/Users/Owner/blessed-repository/src/data/" + userName +".txt");
                String data = "Name: " + userName + "\nBudget: " +userBudget + "\nExpense: "+ userBudget + "\nDescription: " + userDescription;
                fw.write(data);
                fw.close();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            frame.dispose();
//            new WelcomeFrame();
        }
        else {
            hasFailed = false;
        }
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
                new NewFrame();
            }
        });
    }
}

