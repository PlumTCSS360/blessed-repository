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
import java.util.*;

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
    NotesPanel notesPanel;
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
        super();
        this.projectName = projectName;

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
        todoListPanel = new TodoPanel(Project.getTodoList());
        imagePanel = new JPanel();
        subprojectPanel = new SubprojectPanel();
        notesPanel = new NotesPanel(null, null);

        //set up array of activity panels
        activityPanels = new JPanel[6];
        activityPanels[0] = budgetPanel;
        activityPanels[1] = descriptionPanel;
        activityPanels[2] = todoListPanel;
        activityPanels[3] = imagePanel;
        activityPanels[4] = subprojectPanel;
        activityPanels[5] = notesPanel;

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

        budgetPanel.setVisible(true);
    }

    /**
     * @author Taylor Merwin
     */
    private void createDescriptionPanel() {

        descriptionPanel.setVisible(true);
    }

    /**
     * @author Taylor Merwin
     */
    private void createTodoListPanel() {

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
     * @author Taylor Merwin, Cameron Gregoire, Jiameng Li
     */
    private void createTreePanel(String projectName) {
        //Create the root node from the project directory in the data folder
        //we will use the literal project name for now
        DefaultMutableTreeNode root = new DefaultMutableTreeNode(projectName);
        root.add(new DefaultMutableTreeNode("Description"));
        root.add(new DefaultMutableTreeNode("Budget"));
        root.add(new DefaultMutableTreeNode("Todo List"));
        Map<String, Subproject> subprojects = new TreeMap<>(Project.getSubprojectsList());
        //for each subproject add nodes for description, budget, options, notes, and sketches
        for (Subproject sp : subprojects.values()) {
            // Description and budget
            DefaultMutableTreeNode spNode = new DefaultMutableTreeNode(sp);
            spNode.add(new DefaultMutableTreeNode("Description"));
            spNode.add(new DefaultMutableTreeNode("Budget"));
            // Options
            DefaultMutableTreeNode options = new DefaultMutableTreeNode("Options");
            for (Option op : sp.getOptionsList().values()) {
                DefaultMutableTreeNode opNode = new DefaultMutableTreeNode(op);
                opNode.add(new DefaultMutableTreeNode("Description"));
                opNode.add(new DefaultMutableTreeNode("Cost"));
                // TODO Add another node for option information
                options.add(opNode);
            }
            // Notes
            DefaultMutableTreeNode notes = new DefaultMutableTreeNode("Notes");
            Set<String> noteList = new TreeSet<>(sp.getNotesList().keySet());
            for (String n : noteList) {
                notes.add(new DefaultMutableTreeNode(n, false));
            }
            // Sketches
            DefaultMutableTreeNode sketches = new DefaultMutableTreeNode("Sketches");
            Set<String> sketchList = new TreeSet<>(sp.getSketchesList().keySet());
            for (String s : sketchList) {
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
        final ProjectFrame frame = this;
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
                        if (selectedNode.getLevel() == 1) {     // Project budget
                            budgetPanel = new BudgetPanel(Project.getBudget());
                        } else if (selectedNode.getLevel() == 2) {        // Subproject budget
                            final Subproject sp = Project.getSubproject(selectedNode.getParent().toString());
                            budgetPanel = new BudgetPanel(sp.getBudget());
                        }
                        refreshActivityPanel(budgetPanel, "0");
                        myCardLayout.show(activityContainerPanel, "0");
                    }
                    //open description panel
                    else if (selectedNode.getUserObject().equals("Description")) {
                        if (selectedNode.getLevel() == 1) {     // Project description
                            descriptionPanel = new DescriptionPanel(Project.getProjectDescription());
                        } else if (selectedNode.getLevel() == 2) {      // Subproject description
                            final Subproject sp = Project.getSubproject(selectedNode.getParent().toString());
                            descriptionPanel = new DescriptionPanel(sp.getDescription());
                        } else if (selectedNode.getLevel() == 4) {      // Option description
                            final String s = selectedNode.getParent().getParent().getParent().toString();
                            final Subproject sp = Project.getSubproject(s);
                            final Option op = sp.getOption(selectedNode.getParent().toString());
                            descriptionPanel = new DescriptionPanel(op.getDescription());
                        }
                        refreshActivityPanel(descriptionPanel, "1");
                        myCardLayout.show(activityContainerPanel, "1");
                    }

                    //open to-do list panel
                    else if (selectedNode.getUserObject().equals("Todo List")) {
                        if (selectedNode.getLevel() == 1) {     // Project to-do list
                            todoListPanel = new TodoPanel(Project.getTodoList());
                        } else if (selectedNode.getLevel() == 2) {      // Subproject to-do list
                            final Subproject sp = Project.getSubproject(selectedNode.getParent().toString());
                            todoListPanel = new TodoPanel(sp.getTodoList());


                    }
                        refreshActivityPanel(todoListPanel, "2");
                        myCardLayout.show(activityContainerPanel, "2");
                    }

                    //open NotesPanel
                    else if (selectedNode.getLevel() == 3 && ((DefaultMutableTreeNode) selectedNode.getParent()).getUserObject().equals("Notes")) {
                        String noteName = selectedNode.getUserObject().toString();
                        final Subproject sp = Project.getSubproject(selectedNode.getParent().getParent().toString());
                        notesPanel = new NotesPanel(sp, noteName);
                        refreshActivityPanel(notesPanel, "5");
                        myCardLayout.show(activityContainerPanel, "5");
                    }

                    // Open Sketch panel
                    else if (selectedNode.getLevel() == 3 && selectedNode.getParent().toString().equals("Sketches")) {
                        final Subproject sp = Project.getSubproject(selectedNode.getParent().getParent().toString());
                        imagePanel = new SketchPanel(sp, selectedNode.getUserObject().toString());
                        refreshActivityPanel(imagePanel, "3");
                        myCardLayout.show(activityContainerPanel, "3");
                    }

                    // TODO Add code to open option info
                } else if (selectedNode != null && selectedNode.getLevel() == 1) {
                    // Open subproject panel
                    Subproject sp = Project.getSubproject(selectedNode.getUserObject().toString());
                    subprojectPanel = new SubprojectPanel(sp, frame);
                    refreshActivityPanel(subprojectPanel, "4");
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

        final JPanel panel = new JPanel(new BorderLayout());
        createSubprojectButton(panel);
        createDeleteItemButton(panel);
        projectTreePanel.add(panel, BorderLayout.SOUTH);

        projectTreePanel.setVisible(true);
    }

    private void refreshActivityPanel(final JPanel thePanel, final String theIdx) {
        activityContainerPanel.remove(thePanel);
        activityContainerPanel.add(thePanel, theIdx);
        activityContainerPanel.revalidate();
        activityContainerPanel.repaint();
    }

    protected void refreshTreePanel() {
        projectTreePanel.removeAll();
        createTreePanel(projectName);
        projectTreePanel.revalidate();
        projectTreePanel.repaint();
    }


    /**
     * Creates a button that allows the user to choose a new item to create
     * Opens a dialog to choose the item type
     * @author Taylor Merwin
     */
    private void createSubprojectButton(final JPanel thePanel){
        JButton createSubprojectButton = new JButton("Create Subproject");
        createSubprojectButton.setBackground(Color.LIGHT_GRAY);
        thePanel.add(createSubprojectButton, BorderLayout.SOUTH);

        //Create a new subproject when the button is clicked
        createSubprojectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Save project
                Project.saveProject();
                // Create a new subproject screen
                projectFrame.dispose();
                new CreateSubprojectFrame(projectName);
                refreshTreePanel();
            }
        });
    }

    /**
     * Create a button that delete the currently selected from the project and refresh the tree panel.
     *
     * @author Jiameng Li
     * @param thePanel The panel it belongs to.
     */
    private void createDeleteItemButton(final JPanel thePanel) {
        JButton delete = new JButton("Delete Item");
        delete.setBackground(Color.LIGHT_GRAY);
        thePanel.add(delete, BorderLayout.NORTH);

        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) projectTree.getLastSelectedPathComponent();

                // No item selected
                if (selectedNode == null) {
                    JOptionPane.showMessageDialog(null, "Please select an item to delete.",
                            "No item selected", JOptionPane.INFORMATION_MESSAGE);
                // Project is selected
                } else if (selectedNode.getLevel() == 0) {
                    JOptionPane.showMessageDialog(null, "Can't delete project in this frame.",
                    "Can't delete project", JOptionPane.INFORMATION_MESSAGE);
                // Budget, Description, or List is selected
                } else if (selectedNode.getUserObject().toString().equals("Budget") ||
                        selectedNode.getUserObject().toString().equals("Description") ||
                        selectedNode.getUserObject().toString().equals("Todo List")) {
                    JOptionPane.showMessageDialog(null, "Can't delete Budget, Description, or Todo List.",
                            "Can't delete Item", JOptionPane.INFORMATION_MESSAGE);
                // A subproject is selected
                } else if (selectedNode.getLevel() == 1) {
                    Project.deleteSubproject(selectedNode.getUserObject().toString());
                    refreshTreePanel();
                // Notes, Sketches, or Options folder in a subproject is selected
                } else if (selectedNode.getLevel() == 2) {
                    JOptionPane.showMessageDialog(null, "Can't delete Notes, Sketches, or Options folder.",
                            "Can't delete folder", JOptionPane.INFORMATION_MESSAGE);
                // A note, sketch, or option is selected
                } else if (selectedNode.getLevel() == 3) {
                    final String folder = selectedNode.getParent().toString();
                    final String name = selectedNode.getUserObject().toString();
                    final Subproject sp = Project.getSubproject(selectedNode.getParent().getParent().toString());
                    switch (folder) {
                        case "Notes" -> sp.deleteNote(name);
                        case "Sketches" -> sp.deleteSketch(name);
                        case "Options" -> sp.deleteOption(name);
                    }
                    refreshTreePanel();
                // Information about an option is selected
                } else if (selectedNode.getLevel() == 4) {
                    JOptionPane.showMessageDialog(null, "Can't delete necessary information about option.",
                            "Can't delete item", JOptionPane.INFORMATION_MESSAGE);
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

}
