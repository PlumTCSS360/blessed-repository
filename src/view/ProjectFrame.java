package view;

import model.Project;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The main UI for the project.
 * Currently, a skeleton of the UI, but will be updated as the project progresses.
 * @author Taylor Merwin
 */

public class ProjectFrame implements GUIFrame{

    //The name of current project
    String projectName;

    JFrame projectFrame;
    //menubar to display at the top of the frame
    JMenuBar menuBar;
    //panels to display in the frame
    JPanel projectTreePanel;
    JPanel activityContainerPanel;
    JPanel buttonsPanel;
    //Activity panels to display in the activity panel
    JPanel budgetPanel;
    JPanel descriptionPanel;
    JPanel todoListPanel;
    JPanel imagePanel;
    //array of activity panels
    JPanel[] activityPanels;
    //buttons to display in the buttons panel
    JButton exitProjectButton;
    JButton saveProjectButton;
    //card layout to switch between panels
    CardLayout myCardLayout;
    //JTree for the projectList panel
    JTree projectTree;
    //Scroll pane for the projectTree
    JScrollPane projectTreeScrollPane;

    /**
     * Constructor for the ProjectFrame class
     * Initializes all the top level objects in the Class
     * Call methods to set the GUI
     * @author Taylor Merwin
     */
    public ProjectFrame(Project theProject) {

        //Set the project name
        projectName = theProject.getProjectName();

        // Initialize ProjectFame and set frame title
        projectFrame = new JFrame("Crafty Companion - " + projectName);

        //Initialize the layout manager for the activities
        myCardLayout = new CardLayout();

        //initialize panels

        //create project tree panel with border layout
        projectTreePanel = new JPanel(new BorderLayout());
        activityContainerPanel = new JPanel();
        buttonsPanel = new JPanel();
        //initialize menu bar
        menuBar = new JMenuBar();

        // Create menu bar
        createMenuBar(menuBar);
        //create panels
        createProjectPanel();
        //createListPanel();
        createActivityPanels();
        createButtonsPanel();
        //create buttons for bottom panel
        createExitButton();
        createSaveButton();
        // Set frame properties
        setUpProjectFrame();
    }

    /**
     @author Taylor Merwin
    Creates the menu bar for the frame.
     */
    private void createMenuBar( JMenuBar menuBar) {

        //create menu items
        final JMenuItem menuItem1 = new JMenuItem("Item 1");
        final JMenuItem menuItem2 = new JMenuItem("Item 2");
        final JMenuItem menuItem3 = new JMenuItem("Item 3");

        //add menu items to menu
        menuBar.add(menuItem1);
        menuBar.add(menuItem2);
        menuBar.add(menuItem3);
    }

    /**
     * @author Taylor Merwin
     */
    private void createActivityPanels() {

        //set up activity panel
        activityContainerPanel.setLayout(myCardLayout);
        activityContainerPanel.setVisible(true);

        //initialize activity panels
        budgetPanel = new JPanel();
        descriptionPanel = new JPanel();
        todoListPanel = new JPanel();
        imagePanel = new JPanel();

        //set up array of activity panels
        activityPanels = new JPanel[4];
        activityPanels[0] = budgetPanel;
        activityPanels[1] = descriptionPanel;
        activityPanels[2] = todoListPanel;
        activityPanels[3] = imagePanel;

        //create activity panels
        createBudgetPanel();
        createDescriptionPanel();
        createTodoListPanel();
        createImagePanel();

        //add activity panels to activity panel
        for (int i = 0; i < activityPanels.length; i++) {
            activityContainerPanel.add(activityPanels[i], Integer.toString(i));
        }
        //show the description panel
        myCardLayout.show(activityContainerPanel, "1");
    }

    /**
     * @author Taylor Merwin
     */
    private void createBudgetPanel() {
        budgetPanel.add(new JLabel("Budget Panel"));
        budgetPanel.setBackground(Color.cyan);
        budgetPanel.setVisible(true);
    }

    /**
     * @author Taylor Merwin
     */
    private void createDescriptionPanel() {
        descriptionPanel.add(new JLabel("Description Panel"));
        descriptionPanel.setBackground(Color.yellow);
        descriptionPanel.setVisible(true);
    }

    /**
     * @author Taylor Merwin
     */
    private void createTodoListPanel() {
        todoListPanel.add(new JLabel("Todo List Panel"));
        todoListPanel.setBackground(Color.orange);
        todoListPanel.setVisible(true);
    }

    /**
     * @author Taylor Merwin
     */
    private void createImagePanel() {
        imagePanel.add(new JLabel("Image Panel"));
        //imagePanel.setBackground(Color.pink);
        imagePanel.setVisible(true);
    }


    /**
     * @author Taylor Merwin
     */
    private void createProjectPanel() {
        //create root node
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Project Name");

        //create child nodes
        //This will certainly change once it retrieves data from the other classes
        DefaultMutableTreeNode budget = new DefaultMutableTreeNode("Budget");
        DefaultMutableTreeNode description = new DefaultMutableTreeNode("Description");
        DefaultMutableTreeNode todoList = new DefaultMutableTreeNode("Todo List");
        DefaultMutableTreeNode image = new DefaultMutableTreeNode("Image");

        //Create

        //add child nodes to root node
        root.add(budget);
        root.add(description);
        root.add(todoList);
        root.add(image);

        //create the tree by passing in the root node
        projectTree = new JTree(root);

        // Add a TreeSelectionListener to the JTree
        projectTree.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) projectTree.getLastSelectedPathComponent();

                // If the selected node exists and is a leaf node (i.e., it has no children)
                if (selectedNode != null && selectedNode.isLeaf()) {
                    //currently uses a very naive approach to determine which panel to open
                    //Will need to change this to support subprojects and other activities

                    //open budget panel
                    if (selectedNode.getUserObject().equals("Budget")) {
                        myCardLayout.show(activityContainerPanel, "0");
                    }
                    //open description panel
                    else if (selectedNode.getUserObject().equals("Description")) {
                        myCardLayout.show(activityContainerPanel, "1");
                    }
                    //open todo list panel
                    else if (selectedNode.getUserObject().equals("Todo List")) {
                        myCardLayout.show(activityContainerPanel, "2");
                    }
                    //open image panel
                    else if (selectedNode.getUserObject().equals("Image")) {
                        myCardLayout.show(activityContainerPanel, "3");
                    }
                }
            }
        });

        //initialize the scroll pane
        projectTreeScrollPane = new JScrollPane(projectTree);

        //add the scroll pane to the projectListPanel
        projectTreePanel.add(projectTreeScrollPane, BorderLayout.CENTER);

        JButton newItemButton = new JButton("New Item");
        projectTreePanel.add(newItemButton, BorderLayout.SOUTH);
        projectTreePanel.setVisible(true);

    }

    /**
     * @author Taylor Merwin
     */
    private void createButtonsPanel() {
        buttonsPanel.setVisible(true);
    }

    /**
     * @author Taylor Merwin
     */
    private void createExitButton() {
        exitProjectButton = new JButton("Exit Project");

        //add Action listener to button
        //When clicked, the ProjectFrame is disposed and returned to welcome frame
        exitProjectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int response = JOptionPane.showConfirmDialog(projectFrame,
                        "Unsaved Changes will be lost, Are you sure you want to exit the project?",
                        "Exit Project", JOptionPane.YES_NO_OPTION);

                if (response == JOptionPane.OK_OPTION) {
                    projectFrame.dispose();
                    new WelcomeFrame();
                }
            }
        });

        //add button to buttons panel
        buttonsPanel.add(exitProjectButton);
    }

    /**
     * @author Taylor Merwin
     */
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

    /**
     * @author Taylor Merwin
     */
    private void setUpProjectFrame() {

        projectFrame.setLayout(new BorderLayout());
        //Add menu bar to frame
        projectFrame.setJMenuBar(menuBar);
        //Add panels to frame
        projectFrame.add(buttonsPanel, BorderLayout.SOUTH);
        projectFrame.add(activityContainerPanel, BorderLayout.CENTER);
        projectFrame.add(projectTreePanel, BorderLayout.WEST);

        // Set frame properties
        double screenWidth = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double screenHeight = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        Dimension frameSize = new Dimension((int) screenWidth * 2 / 3, (int) screenHeight * 2 / 3);
        projectFrame.setSize(frameSize);
        projectFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        projectFrame.setLocationRelativeTo(null);
        projectFrame.setVisible(true);
    }

}
