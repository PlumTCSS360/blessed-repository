package view;

import model.Project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * The LoadFrame class represents the GUI for loading projects in Crafty Companion.
 * It displays a list of project names and provides options to cancel or load a project.
 *
 * @author Cameron Gregoire
 */
public class LoadFrame extends JFrame implements GUIFrame {
    private JList<String> projectList;

    /**
     * Constructs a new instance of LoadFrame.
     * Initializes the frame, sets its properties, and creates the components.
     */
    public LoadFrame() {
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

        // Create panel for the project list
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BACKGROUND_COLOR);
        add(panel, BorderLayout.CENTER);

        // Retrieve project names from the 'data' folder
        List<String> projects = retrieveProjectNames();
        projectList = new JList<>(projects.toArray(new String[0]));
        projectList.setFont(BODY_FONT);
        projectList.setBackground(BACKGROUND_COLOR);
        projectList.setForeground(FOREGROUND_COLOR);

        // Create scroll pane for the project list
        JScrollPane scrollPane = new JScrollPane(projectList);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        panel.add(scrollPane, BorderLayout.CENTER);

        // Create button panel for Cancel and Load buttons
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
        buttonPanel.setBackground(BACKGROUND_COLOR);
        add(buttonPanel, BorderLayout.SOUTH);

        // Create Cancel button
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setFont(BODY_FONT);
        cancelButton.setBackground(FOREGROUND_COLOR);
        cancelButton.setForeground(BACKGROUND_COLOR);
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();  // Close the LoadFrame
                new WelcomeFrame();  // Open the WelcomeFrame
            }
        });
        buttonPanel.add(cancelButton);

        //Create Delete Button
        JButton deleteButton = new JButton("Delete");
        deleteButton.setFont(BODY_FONT);
        deleteButton.setBackground(FOREGROUND_COLOR);
        deleteButton.setForeground(BACKGROUND_COLOR);
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String projectName = projectList.getSelectedValue(); // Implement this method to get the selected project name from the list
                if (projectName != null) {
                    int confirmDialogResult = JOptionPane.showConfirmDialog(null,
                            "Are you sure you want to delete the project \"" + projectName + "\"?",
                            "Confirm Delete", JOptionPane.YES_NO_OPTION);
                    if (confirmDialogResult == JOptionPane.YES_OPTION) {
                        Project.deleteProject(projectName);
                        refreshProjectList();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please select a project to delete.",
                            "Delete Project", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        buttonPanel.add(deleteButton);

        // Create Load button
        JButton loadButton = new JButton("Load");
        loadButton.setFont(BODY_FONT);
        loadButton.setBackground(FOREGROUND_COLOR);
        loadButton.setForeground(BACKGROUND_COLOR);
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedFolder = projectList.getSelectedValue();
                //TODO call loadProject method
                dispose();  // Close the LoadFrame
            }
        });
        buttonPanel.add(loadButton);

        setVisible(true);
    }

    /**
     * Retrieves the project names.
     *
     * @return a list of project names
     */
    private List<String> retrieveProjectNames() {
        List<String> projectNames = new ArrayList<>();
        File dataFolder = new File("data");
        if (dataFolder.exists() && dataFolder.isDirectory()) {
            File[] projects = dataFolder.listFiles(File::isDirectory);
            if (projects != null) {
                for (File project : projects) {
                    projectNames.add(project.getName());
                }
            }
        }
        return projectNames;
    }

    /**
     * Refreshes the displayed list of projects (for after deletion.)
     */
    private void refreshProjectList() {
        List<String> projects = retrieveProjectNames();
        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (String project : projects) {
            listModel.addElement(project);
        }
        projectList.setModel(listModel);
    }

    /**
     * The entry point of the application.
     * Creates and displays an instance of LoadFrame.
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

