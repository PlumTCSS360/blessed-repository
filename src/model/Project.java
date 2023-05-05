package model;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.Scanner;

/**
 * This class represent the Project in to application.
 * It contains the methods for creating, saving, loading, deleting the project.
 *
 * @author Jiameng Li
 * @version v0.1
 */
public final class Project {

    // These fields will be null if there's no opened project.

    /** The name of the currently opened project. */
    private static String myName;

    /** The total budget of the currently opened project. */
    private static BigDecimal myBudget;

    /** The total expense of the currently opened project. */
    private static BigDecimal myExpense;

    /** The description of the currently opened project. */
    private static String myDescription;

    // TODO Add a field for the list of subproject

    /**
     * Private constructor that prevent initialization of this class.
     */
    private Project() {
    }


    // Getters

    /**
     * Get the project name.
     *
     * @return The project name.
     */
    public static String getProjectName() {
        return myName;
    }

    /**
     * Get the total budget of the project.
     *
     * @return The total budget of the project.
     */
    public static BigDecimal getProjectBudget() {
        return myBudget;
    }

    /**
     * Get the total expense of the project.
     *
     * @return The total expense of the project.
     */
    public static BigDecimal getProjectExpense() {
        return myExpense;
    }

    /**
     * Get the project description.
     *
     * @return The project description.
     */
    public static String getProjectDescription() {
        return myDescription;
    }

    /**
     * Get a list of all existing projects with project name and budget and expense.
     * The list will be return as a 2D array that looks like this
     * [project name 1][expense / budget]
     * [project name 2][expense / budget]
     * [project name 3][expense / budget]
     * ...
     * If there's no existing project, it returns an array with zero row and zero column.
     * The expense and budget will be returned as a string in the format "expense / budget".
     *
     * @return A 2D array of string that represent the list of all existing projects.
     */
    public static String[][] getProjectList() {
        String[][] projectList = new String[0][0];
        File file = new File("src/data");

        if (file.exists()) {
            // Only accept directories (projects) in the data folder
            File[] projects = file.listFiles(File::isDirectory);

            if (projects != null && projects.length > 0) {
                // Goes through all the project and read their name, budget and expense.
                projectList = new String[projects.length][2];
                for (int i = 0; i < projects.length; i++) {
                    if (projects[i].isDirectory()) {
                        // Read the project name
                        projectList[i][0] = projects[i].getName();
                        // Read the budget and expense in format "expense / budget"
                        try {
                            Scanner scanner = new Scanner(new File(projects[i].getPath() + "/Budget.txt"));
                            projectList[i][1] = scanner.nextLine();
                            scanner.close();
                        } catch (FileNotFoundException e) {
                            System.out.println("Can't find file: " + e);
                        }

                    }
                }
            }
        }

        return projectList;
    }


    // Setters

    /**
     * Set a new budget for the project.
     *
     * @param theBudget The new budget for the project.
     */
    public static void setProjectBudget(final BigDecimal theBudget) {
        myBudget = theBudget;
    }

    /**
     * Set a new expense for the project.
     *
     * @param theExpense The new expense for the project.
     */
    public static void setProjectExpense(final BigDecimal theExpense) {
        myExpense = theExpense;
    }

    /**
     * Set a new project description.
     *
     * @param theDescription The new project description.
     */
    public static void setProjectDescription(final String theDescription) {
        myDescription = theDescription;
    }


    // Methods for creating, deleting, loading, and saving project

    /**
     * Create a new project with given name, budget, and description.
     * If the project already existed, display an error message dialog.
     *<p>
     * Steps:
     * 1. Assign name, budget, expense, and description to the field since the project will be opened.
     * 2. Create a folder for the project and a budget and a description text file inside that folder.
     * 3. Write given budget and expense in the budget file in the format: expense / budget.
     * 4. Write given description in the description file.
     * </p>
     * <p>
     *      Precondition: The project doesn't exist. In other words, the project name hasn't been used.
     * </p>
     *
     * @param theName The name of the new project.
     * @param theBudget The total budget of the new project.
     * @param theDescription The description of the new project.
     */
    public static void createProject(final String theName, final BigDecimal theBudget,
                                     final String theDescription) {
        try {
            // Path to the folder where project information will be stored.
            String path = "src/data/" + theName;
            File file = new File(path);

            if (!file.exists()) {
                // Assigning project name, budget, expense, and description
                myName = theName;
                myBudget = theBudget;
                myExpense = new BigDecimal("0");
                myDescription = theDescription;

                // If successfully created a directory for the project
                if (file.mkdirs()) {
                    // Create a budget file and write budget and expense in it
                    file = new File(path + "/Budget.txt");
                    if (file.createNewFile()) {
                        FileWriter writer = new FileWriter(file, false);
                        writer.write(myExpense.toString() + " / " + myBudget.toString());
                        writer.close();
                    }

                    // Create a description file and write description in it
                    file = new File(path + "/Description.txt");
                    if (file.createNewFile()) {
                        FileWriter writer = new FileWriter(file, false);
                        writer.write(myDescription);
                        writer.close();
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null,
                        "The project \"" + theName + "\" already existed.",
                        "Can't create project", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            System.err.println("Can't create project. " + e);
        }
    }

    /**
     * Delete the project and files that store its data.
     * If the project doesn't exist, display an error message dialog.
     * <p>
     *      Precondition: The selected project exists.
     * </p>
     * @param theName The name of the project to be deleted.
     */
    public static void deleteProject(final String theName) {
        try {
            // Path to the project to be deleted.
            String path = "src/data/" + theName;
            File file = new File(path);

            if (file.exists()) {
                // Recursively delete files in the project folder so the project folder can be deleted.
                for (File subFiles : Objects.requireNonNull(file.listFiles())) {
                    if (subFiles.isDirectory()) {
                        deleteProject(theName + "/" + subFiles.getName());
                    } else {
                        subFiles.delete();
                    }
                }
                // Delete the project folder.
                file.delete();
            } else {
                JOptionPane.showMessageDialog(null,
                        "The project \"" + theName + "\" doesn't exist.",
                        "Can't delete project", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            System.err.println("Can't find or delete project. " + e);
        }
    }

    /**
     * Save the currently opened project.
     * This method should not be called when there's no project opened. No data will be store into the
     * data file before this method is called.
     * <p>
     *     Precondition: The project to be saved is currently opened.
     * </p>
     */
    public static void saveProject() {
        try {
            // Path to the folder where project information will be stored.
            String path = "src/data/" + myName;

            // Save budget and expense
            File file = new File(path + "/Budget.txt");
            FileWriter writer = new FileWriter(file, false);
            writer.write(myExpense.toString() + " / " + myBudget.toString());
            writer.close();

            // Save project description
            file = new File(path + "/Description.txt");
            writer = new FileWriter(file, false);
            writer.write(myDescription);
            writer.close();

            // TODO Add code to save subproject
        } catch (Exception e) {
            System.err.println("Can't save project. " + e);
        }
    }

    /**
     * Load the project with given project name.
     * If the project doesn't exist, display an error message dialog.
     * <p>
     *     Precondition: The selected project exists.
     * </p>
     * @param theName The name of the project to be loaded.
     */
    public static void loadProject(final String theName) {
        try {
            // Path to the project to be deleted.
            String path = "src/data/" + theName;
            File file = new File(path);

            if (file.exists()) {
                // Load project name
                myName = theName;

                // Load project budget and expense
                file = new File(path + "/Budget.txt");
                Scanner scanner = new Scanner(file);
                myExpense = scanner.nextBigDecimal();
                scanner.next();     // Skip the / between budget and expense
                myBudget = scanner.nextBigDecimal();
                scanner.close();

                // Load project description
                file = new File(path + "/Description.txt");
                scanner = new Scanner(file);
                StringBuilder sb = new StringBuilder();
                while(scanner.hasNextLine()) {
                    sb.append(scanner.nextLine());
                    sb.append("\n");
                }
                myDescription = sb.toString();
                scanner.close();

                // TODO Add code to load subproject
            } else {
                JOptionPane.showMessageDialog(null,
                        "The project \"" + theName + "\" doesn't exist.",
                        "Can't load project", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            System.err.println("Can't load project. " + e);
        }
    }

}