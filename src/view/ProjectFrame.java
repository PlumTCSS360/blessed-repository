package view;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
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
    JButton undoActionButton;
    //card layout to switch between panels
    CardLayout myCardLayout;

    //List for the projectListPanel
    JList<String> projectList;

    //Scroll pane for the projectList
    JScrollPane projectListScrollPane;

    //JTree for the projectList panel
    JTree projectTree;

    //Scroll pane for the projectTree
    JScrollPane projectTreeScrollPane;

    public ProjectFrame() {

        //Initialize all the top level objects in the Class
        //And call methods to set them up

        // Initialize ProjectFame and set frame title
        projectFrame = new JFrame("Crafty Companion - <Project Name>");

        //Initialize the layout manager for the activities
        myCardLayout = new CardLayout();

        //initialize panels
        projectTreePanel = new JPanel();
        activityContainerPanel = new JPanel();
        buttonsPanel = new JPanel();

        //initialize menu bar
        menuBar = new JMenuBar();

        // Create menu bar
        createMenuBar(menuBar);

        //create panels
        createProjectTree();
        //createListPanel();
        createActivityPanels();
        createButtonsPanel();

        //create buttons for bottom panel
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

        final JMenuItem openBudget = new JMenuItem("Open Budget");
        final JMenuItem openDescription = new JMenuItem("Open Description");
        final JMenuItem openTodoList = new JMenuItem("Open Todo List");
        final JMenuItem openImage = new JMenuItem("Open Image");

        menuBar.add(openBudget);
        menuBar.add(openDescription);
        menuBar.add(openTodoList);
        menuBar.add(openImage);

        //add action listeners to menu items
        openBudget.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                myCardLayout.show(activityContainerPanel, "0");
            }
        });
        openDescription.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                myCardLayout.show(activityContainerPanel, "1");
            }
        });
        openTodoList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                myCardLayout.show(activityContainerPanel, "2");
            }
        });
        openImage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                myCardLayout.show(activityContainerPanel, "3");
            }
        });
    }

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
        //show first activity panel (budget)
        myCardLayout.show(activityContainerPanel, "0");
    }

    //create budget panel
    private void createBudgetPanel() {
        budgetPanel.add(new JLabel("Budget Panel"));
        budgetPanel.setBackground(Color.cyan);
        budgetPanel.setVisible(true);
    }

    //create description panel
    private void createDescriptionPanel() {
        descriptionPanel.add(new JLabel("Description Panel"));
        descriptionPanel.setBackground(Color.yellow);
        descriptionPanel.setVisible(true);
    }

    //create todo list panel
    private void createTodoListPanel() {
        todoListPanel.add(new JLabel("Todo List Panel"));
        todoListPanel.setBackground(Color.orange);
        todoListPanel.setVisible(true);
    }

    //create image panel
    private void createImagePanel() {
        imagePanel.add(new JLabel("Image Panel"));
        //imagePanel.setBackground(Color.pink);
        imagePanel.setVisible(true);
    }


    private void createProjectTree() {
        //create root node
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Project");

        //create child nodes
        //This will certainly change once it retrieves data from the other classes
        DefaultMutableTreeNode budget = new DefaultMutableTreeNode("Budget");
        DefaultMutableTreeNode description = new DefaultMutableTreeNode("Description");
        DefaultMutableTreeNode todoList = new DefaultMutableTreeNode("Todo List");
        DefaultMutableTreeNode image = new DefaultMutableTreeNode("Image");

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
                    //simply prints the name of the selected node
                    //Later will open the appropriate activity panel
                    System.out.println("You selected " + selectedNode.getUserObject());
                }
            }
        });

        //initialize the scroll pane
        projectTreeScrollPane = new JScrollPane(projectTree);

        //add the scroll pane to the projectListPanel
        projectTreePanel.add(projectTreeScrollPane);
        projectTreePanel.setVisible(true);
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
