package view;

import model.Option;
import model.Project;
import model.Subproject;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

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
    BudgetPanel budgetPanel;
    DescriptionPanel descriptionPanel;
    JPanel todoListPanel;
    JPanel imagePanel;

    JPanel subprojectPanel;

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
     * @param projectName the name of the project
     * @author Taylor Merwin
     */
    public ProjectFrame(String projectName) {

        // Initialize ProjectFame and set frame title
        projectFrame = new JFrame("Crafty Companion - " + projectName);

        //Initialize the layout manager for the activities
        myCardLayout = new CardLayout();



        //create project tree panel with border layout
        projectTreePanel = new JPanel(new BorderLayout());
        activityContainerPanel = new JPanel();
        buttonsPanel = new JPanel();
        //initialize menu bar
        menuBar = new JMenuBar();

        // Create menu bar
        createMenuBar(menuBar);
        //create panels
        //createProjectPanel();
        //createListPanel();
        createTreePanel(projectName);
        createActivityPanels();
        createButtonsPanel();
        //create buttons for bottom panel
        createExitButton();
        createSaveButton();
        // Set frame properties
        setUpProjectFrame();

        //Used to test what values are initialized in Project
        setUpProjectFiles();
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
//        menuBar.add(menuItem1);
////        menuBar.add(menuItem2);
////        menuBar.add(menuItem3);

        menuBar.setBackground(Color.LIGHT_GRAY);
    }

    /**
     * @author Taylor Merwin
     */
    private void createActivityPanels() {

        //set up activity panel
        activityContainerPanel.setLayout(myCardLayout);
        activityContainerPanel.setVisible(true);

        //initialize activity panels
        budgetPanel = new BudgetPanel(Project.getBudget());
        descriptionPanel = new DescriptionPanel(Project.getProjectDescription());
        todoListPanel = new JPanel();
        imagePanel = new JPanel();
        subprojectPanel = new SubprojectPanel();

        //set up array of activity panels
        activityPanels = new JPanel[5];
        activityPanels[0] = budgetPanel;
        activityPanels[1] = descriptionPanel;
        activityPanels[2] = todoListPanel;
        activityPanels[3] = imagePanel;
        activityPanels[4] = subprojectPanel;

        //create activity panels
//        createBudgetPanel();
//        createDescriptionPanel();
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
        budgetPanel.setVisible(true);
    }

    /**
     * @author Taylor Merwin
     */
    private void createDescriptionPanel() {
        descriptionPanel.add(new JLabel("Description Panel"));
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
     * Creates the tree panel showing the project directory
     * TODO: Use the project class methods to read the project directory
     * @author Taylor Merwin
     */
    private void createTreePanel(String projectName) {
        //Create the root node from the project directory in the data folder
        //we will use the literal project name for now
        DefaultMutableTreeNode root = new DefaultMutableTreeNode(projectName);
        root.add(new DefaultMutableTreeNode("Description"));
        root.add(new DefaultMutableTreeNode("Budget"));
        Map<String, Subproject> subprojects = Project.getSubprojectsList();
        //for each subproject add nodes for description, budget, options, notes, and sketches
        for (Subproject sp : subprojects.values()) {
            DefaultMutableTreeNode spNode = new DefaultMutableTreeNode(sp);
            spNode.add(new DefaultMutableTreeNode("Description"));
            spNode.add(new DefaultMutableTreeNode("Budget"));
            DefaultMutableTreeNode options = new DefaultMutableTreeNode("Options");
            for (Option op : sp.getOptionsList().values()) {
                options.add(new DefaultMutableTreeNode(op));
            }
            DefaultMutableTreeNode notes = new DefaultMutableTreeNode("Notes");
            for (String n : sp.getNotesList().keySet()) {
                notes.add(new DefaultMutableTreeNode(n, false));
            }
            DefaultMutableTreeNode sketches = new DefaultMutableTreeNode("Sketches");
            for (String s : sp.getSketchesList().keySet()) {
                sketches.add(new DefaultMutableTreeNode(s, false));
            }
            spNode.add(options);
            spNode.add(notes);
            spNode.add(sketches);
            root.add(spNode);
        }
        //create the tree by passing in the root node
        projectTree = new JTree(root);
        projectTree.setBackground(Color.DARK_GRAY);
        projectTree.setForeground(Color.RED);
        //projectTree.
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
                        if (selectedNode.getLevel() == 1) {
                            budgetPanel = new BudgetPanel(Project.getBudget());
                        } else {
                            Subproject sp = Project.getSubproject(selectedNode.getParent().toString());
                            budgetPanel = new BudgetPanel(sp.getBudget());
                        }
                        refreshPanel(budgetPanel, "0");
                        myCardLayout.show(activityContainerPanel, "0");
                    }
                    //open description panel
                    else if (selectedNode.getUserObject().equals("Description")) {
                        if (selectedNode.getLevel() == 1) {
                            descriptionPanel = new DescriptionPanel(Project.getProjectDescription());
                        } else {
                            Subproject sp = Project.getSubproject(selectedNode.getParent().toString());
                            descriptionPanel = new DescriptionPanel(sp.getDescription());
                        }
                        refreshPanel(descriptionPanel, "1");
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
                } else if (selectedNode != null && selectedNode.getLevel() == 1) {
                    Subproject sp = Project.getSubproject(selectedNode.getUserObject().toString());
                    subprojectPanel = new SubprojectPanel(sp);
                    refreshPanel(subprojectPanel, "4");
                    myCardLayout.show(activityContainerPanel, "4");
                }
            }
        });

        DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer();
        renderer.setBackgroundNonSelectionColor(Color.DARK_GRAY);
        renderer.setTextNonSelectionColor(Color.WHITE);
        renderer.setTextSelectionColor(Color.BLUE);
        projectTree.setCellRenderer(renderer);
        //initialize the scroll pane
        projectTreeScrollPane = new JScrollPane(projectTree);
        projectTreeScrollPane.setBackground(Color.LIGHT_GRAY);
        //add the scroll pane to the projectListPanel
        projectTreePanel.setBackground(Color.LIGHT_GRAY);
        projectTreePanel.add(projectTreeScrollPane, BorderLayout.CENTER);

        createNewItemButton();

        projectTreePanel.setVisible(true);
    }

    private void refreshPanel(final JPanel thePanel, final String theIdx) {
        activityContainerPanel.remove(thePanel);
        activityContainerPanel.add(thePanel, theIdx);
        activityContainerPanel.revalidate();
        activityContainerPanel.repaint();
    }


    /**
     * Creates a button that allows the user to choose a new item to create
     * Opens a dialog to choose the item type
     * @author Taylor Merwin
     */
    private void createNewItemButton(){
        JButton newItemButton = new JButton("New Item");
        newItemButton.setBackground(Color.LIGHT_GRAY);
        projectTreePanel.add(newItemButton, BorderLayout.SOUTH);

        //Create a new item when the button is clicked
        newItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] options = {"Sub Project", "Item"};
                int choice = JOptionPane.showOptionDialog(projectFrame, "What would you like to create?",
                        "New Item", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

                if (choice == JOptionPane.YES_OPTION) {
                    //System.out.println("LOL");
                    //TODO: Create a new subproject
                }

                else if (choice == JOptionPane.NO_OPTION) {
                    //System.out.println("ROFL");
                    //Open a new dialog giving a list of item types to create
                    String[] itemOptions = {"Note", "Sketch", "Option"};
                    int itemChoice = JOptionPane.showOptionDialog(projectFrame, "What type of item would you like to create?",
                            "New Item", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, itemOptions, itemOptions[0]);
                }

            }
        });
    }


    /**
     * @author Taylor Merwin
     */
    private void createButtonsPanel() {
        buttonsPanel.setBackground(Color.DARK_GRAY);
        buttonsPanel.setVisible(true);
    }

    /**
     * @author Taylor Merwin
     */
    private void createExitButton() {
        exitProjectButton = new JButton("Exit Project");
        exitProjectButton.setBackground(Color.LIGHT_GRAY);

        //add Action listener to button
        //When clicked, the Project is saved, ProjectFrame is disposed and returned to welcome frame
        exitProjectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //Save project
                Project.saveProject();
                //dispose of project frame
                projectFrame.dispose();
                //create a new welcome frame
                new WelcomeFrame();
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
        saveProjectButton.setBackground(Color.LIGHT_GRAY);

        //add action listener to button
        saveProjectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Project.saveProject();
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


    //Let's print out all the values that are initialized in the Project class lol
    public void setUpProjectFiles() {
        System.out.println("The project name: " + Project.getProjectName());
        System.out.println("The project desc: " + Project.getProjectDescription());
        System.out.println("The project budget: " + Project.getBudget());
    }

}
