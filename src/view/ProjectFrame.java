package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
The main UI for the project.
@author Taylor Merwin
 */
public class ProjectFrame implements GUIFrame{

    JFrame projectFrame;


    //menubar to display at the top of the frame
    JMenuBar menuBar;

    //panels to display in the frame
    JPanel projectListPanel;
    JPanel cardsPanel;
    JPanel buttonsPanel;

    //buttons to display in the buttons panel
    JButton exitProjectButton;
    JButton saveProjectButton;
    JButton undoActionButton;

    //card layout to switch between panels
    CardLayout myCardLayout = new CardLayout();


    //private final JPanel[] activityPanels = new JPanel[4];

    public ProjectFrame() {

        //Initialize all the top level objects in the Class
        //And call methods to set them up

        // Initialize ProjectFame and set frame title
        projectFrame = new JFrame("Crafty Companion - ");

        //initialize panels
        projectListPanel = new JPanel();
        cardsPanel = new JPanel();
        buttonsPanel = new JPanel();

        //initialize menu bar
        menuBar = new JMenuBar();





        // Create menu bar
        createMenuBar(menuBar);

        //create panels
        createListPanel();
        createCardsPanel();
        createButtonsPanel();

        //create buttons
        createExitButton();
        createUndoButton();
        createSaveButton();


        // Set frame properties
        setUpProjectFrame();
    }

    /*
    This method will create the menu bar for the frame.
     */
    private void createMenuBar( JMenuBar menuBar) {

        final JMenu fileMenu1 = new JMenu("Menu1");
        final JMenu fileMenu2 = new JMenu("Menu2");
        final JMenu fileMenu3 = new JMenu("Menu3");
        menuBar.add(fileMenu1);
        menuBar.add(fileMenu2);
        menuBar.add(fileMenu3);

    }

    private void createActivityPanels() {
        final JPanel budgetPanel = new JPanel(myCardLayout);
        final JPanel descriptionPanel = new JPanel(myCardLayout);
        final JPanel listPanel = new JPanel(myCardLayout);
        final JPanel imagePanel = new JPanel(myCardLayout);
    }

    private void createListPanel() {
        projectListPanel.setBackground(Color.green);
        projectListPanel.setVisible(true);
    }

    private void createCardsPanel() {
        cardsPanel.setBackground(Color.blue);
        cardsPanel.setVisible(true);
    }

    private void createButtonsPanel() {
        buttonsPanel.setBackground(Color.red);
        buttonsPanel.setVisible(true);
    }

    private void createExitButton() {
        exitProjectButton = new JButton("Exit Project");

        //add action listener to button
        exitProjectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Exit Project Button Pressed");
            }
        });

        //add button to buttons panel
        buttonsPanel.add(exitProjectButton);
    }

    private void createUndoButton() {
        undoActionButton = new JButton("Undo Action");

        //add action listener to button
        undoActionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Undo Action Button Pressed");
            }
        });

        //add button to buttons panel
        buttonsPanel.add(undoActionButton);
    }

    private void createSaveButton() {
        saveProjectButton = new JButton("Save Project");

        //add action listener to button
        saveProjectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Save Project Button Pressed");
            }
        });

        //add button to buttons panel
        buttonsPanel.add(saveProjectButton);
    }



    private void setUpProjectFrame() {

        projectFrame.setLayout(new BorderLayout());

        //Add menu bar to frame
        projectFrame.setJMenuBar(menuBar);

        //Add panels to frame
        projectFrame.add(buttonsPanel, BorderLayout.SOUTH);
        projectFrame.add(cardsPanel, BorderLayout.CENTER);
        projectFrame.add(projectListPanel, BorderLayout.WEST);




        // Set frame properties
        double screenWidth = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double screenHeight = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        Dimension frameSize = new Dimension((int) screenWidth, (int) screenHeight);
        projectFrame.setSize(frameSize);
        projectFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        projectFrame.setLocationRelativeTo(null);
        projectFrame.setVisible(true);
    }

}
