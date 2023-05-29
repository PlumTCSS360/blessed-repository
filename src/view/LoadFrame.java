package view;

import model.Project;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * The LoadFrame class represents the GUI for loading projects in Crafty Companion.
 * It displays a list of project names and provides options to cancel, delete, or load a project.
 *
 * @author Cameron Gregoire
 */
public class LoadFrame extends JFrame implements GUIFrame {
    private JList<String> projectList;

    /**
     * Constructs a new instance of LoadFrame.
     * Initializes the frame, sets its properties, and creates the components.
     *
     * @author Cameron Gregoire
     */
    public LoadFrame() {
        initializeFrame();
        createProjectListPanel();
        createButtonPanel();
        setVisible(true);
    }

    /**
     * Initializes the frame by setting its initial properties.
     *
     * @author Cameron Gregoire
     */
    private void initializeFrame() {
        // Set frame properties
        setTitle("Crafty Companion - Load");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set frame size based on screen dimensions
        final double screenWidth = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        final double screenHeight = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        final Dimension frameSize = new Dimension((int) screenWidth / 4, (int) screenHeight / 2);
        setSize(frameSize);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setBackground(BACKGROUND_COLOR);
    }

    /**
     * Creates the panel for the project list and adds it to the frame.
     *
     * @author Cameron Gregoire
     */
    private void createProjectListPanel() {
        // Create panel for the project list
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BACKGROUND_COLOR);
        add(panel, BorderLayout.CENTER);

        // Retrieve project names and budgets from the data folder
        String[][] projects = Project.getProjectList();
        List<String> projectData = new ArrayList<>();

        for (String[] project : projects) {
            String projectName = project[0];
            String budget = project[1];
            String projectInfo = projectName + " (Budget: $" + budget + ")";
            projectData.add(projectInfo);
        }
        projectList = new JList<>(projectData.toArray(new String[0]));
        projectList.setCellRenderer(new ProjectRenderer());

        projectList.setFont(BODY_FONT);
        projectList.setBackground(BACKGROUND_COLOR);
        projectList.setForeground(FOREGROUND_COLOR);

        // Create scroll pane for the project list
        JScrollPane scrollPane = new JScrollPane(projectList);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        panel.add(scrollPane, BorderLayout.CENTER);
    }

    /**
     * Creates the button panel for Cancel, Delete, and Load buttons and adds it to the frame.
     *
     * @author Cameron Gregoire
     */
    private void createButtonPanel() {
        // Create button panel for Cancel, Delete, and Load buttons
        JPanel buttonPanel = new JPanel(new GridLayout(1, 3));
        buttonPanel.setBackground(BACKGROUND_COLOR);
        add(buttonPanel, BorderLayout.SOUTH);

        // Create Cancel button
        JButton cancelButton = createButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the LoadFrame
                new WelcomeFrame(); // Open the WelcomeFrame
            }
        });
        buttonPanel.add(cancelButton);

        // Create Delete button
        JButton deleteButton = createButton("Delete");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteSelectedProject();
            }
        });
        buttonPanel.add(deleteButton);

        // Create Load button
        JButton loadButton = createButton("Load");
        loadButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loadSelectedProject();
            }
        });
        buttonPanel.add(loadButton);
    }

    /**
     * Creates a JButton.
     *
     * @param text the text to display on the button
     * @return the created JButton
     * @author Cameron Gregoire
     */
    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(BODY_FONT);
        button.setBackground(FOREGROUND_COLOR);
        button.setForeground(BACKGROUND_COLOR);
        return button;
    }

    /**
     * Deletes the selected project and updates the list.
     *
     * @author Cameron Gregoire
     */
    private void deleteSelectedProject() {
        String projectInfo = projectList.getSelectedValue();
        if (projectInfo != null) {
            String projectName = projectInfo.substring(0, projectInfo.indexOf(" (Budget: $"));
            int confirmDialogResult = JOptionPane.showConfirmDialog(null,
                    "Are you sure you want to delete the project \"" + projectName + "\"?",
                    "Confirm Delete", JOptionPane.YES_NO_OPTION);
            if (confirmDialogResult == JOptionPane.YES_OPTION) {
                Project.deleteProject(projectName);
                refreshProjectList();
                projectList.repaint(); // Redraw the separator lines
            }
        } else {
            JOptionPane.showMessageDialog(null, "Select a project to delete.",
                    "Delete Project", JOptionPane.WARNING_MESSAGE);


        }
    }

    /**
     * Loads the selected project into a new ProjectFrame.
     *
     * @author Cameron Gregoire
     */
    private void loadSelectedProject() {
        String selectedProject = (String) projectList.getClientProperty("projectName");
        if (selectedProject != null) {
            Project.loadProject(selectedProject);
            dispose();
            new ProjectFrame(selectedProject);
        } else {
            JOptionPane.showMessageDialog(null, "Please select a project to load.",
                    "Load Project", JOptionPane.WARNING_MESSAGE);

        }
    }


//     * Refreshes the project list after deletion.
//     *
//     * @author Cameron Gregoire
//     */
    private void refreshProjectList() {
        String[][] projects = Project.getProjectList();
        List<String> projectData = new ArrayList<>();

        for (String[] project : projects) {
            String projectName = project[0];
            String budget = project[1];
            String projectInfo = projectName + " (Budget: $" + budget + ")";
            projectData.add(projectInfo);
        }

        // Update the JList with the refreshed project data
        projectList.setListData(projectData.toArray(new String[0]));
    }

    /**
     * Cell renderer for the project list to seperate the budget.
     *
     * @author Cameron Gregoire
     */
    private class ProjectRenderer extends DefaultListCellRenderer {
        private final Color separatorColor = Color.WHITE;
        private final Border separatorBorder = BorderFactory.createMatteBorder(1, 0, 0, 0, separatorColor);

        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                      boolean isSelected, boolean cellHasFocus) {
            Component renderer = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

            ((JComponent) renderer).setBorder(BorderFactory.createCompoundBorder(getBorder(), separatorBorder));

            if (value instanceof String) {
                String projectInfo = (String) value;
                String projectName = projectInfo.substring(0, projectInfo.indexOf(" (Budget: $"));
                String budget = projectInfo.substring(projectInfo.indexOf(" (Budget: $") + 11, projectInfo.length() - 1);

                // Modify this line to display the project name and budget separately
                String displayText = projectName + "   Budget: $" + budget;

                setText(displayText);

                // Associate the project name with the list item
                list.putClientProperty("projectName", projectName);
            }
            return renderer;
        }
    }

    /**
     * The entry point of the application.
     * Creates and displays an instance of LoadFrame.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LoadFrame();
            }
        });
    }
}