package model;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

/**
 * This class represent the Project in to application.
 * It contains the methods for creating, saving, loading, deleting, and closing the project.
 * It also contains the necessary methods for creating, deleting, and loading subprojects.
 *
 * @author Jiameng Li
 * @version 0.3
 */
public final class Project {

    // These name, budget, and description will be null if there's no opened project.

    /** The name of the currently opened project. */
    private static String myName;

    /** The total budget of the currently opened project. */
    private static Budget myBudget;

    /** The description of the currently opened project. */
    private static Description myDescription;

    /** The subprojects in the currently opened project. */
    private static final Map<String, Subproject> mySubprojects = new TreeMap<>();

    /**
     * Private constructor that prevent initialization of this class.
     */
    private Project() {
    }


    // Getters

    /**
     * Get the project name.
     *
     * @author Jiameng Li
     * @return The project name.
     */
    public static String getProjectName() {
        return myName;
    }

    /**
     * Get the project budget.
     *
     * @author Jiameng Li
     * @return The project budget.
     */
    public static Budget getBudget() {
        return myBudget;
    }

    /**
     * Get the project description.
     *
     * @author Jiameng Li
     * @return The project description.
     */
    public static Description getProjectDescription() {
        return myDescription;
    }

    /**
     * Get a subproject from the list by the subproject name.
     *
     * @author Jiameng Li
     * @param theName The name of the subproject to get.
     * @return A subproject with given name, if there's such subproject.
     */
    public static Subproject getSubproject(final String theName) {
        return mySubprojects.get(theName);
    }

    /**
     * Get a copy of the list of subproject.
     *
     * @author Jiameng Li
     * @return A copy of the list of subproject.
     */
    public static Map<String, Subproject> getSubprojectsList() {
        return Map.copyOf(mySubprojects);
    }

    // Methods for creating, deleting, loading, saving, and closing project

    /**
     * Create a new project with given name, budget, and description.
     * If the given name is invalid or the project already existed, it displays an error/warning message dialog.
     * This method only creates necessary text files for a new project. These text files will remain
     * empty until the saveProject() method is called.
     * <p>
     *      Precondition:
     *      1. The project doesn't exist. In other words, the project name hasn't been used.
     *      2. The name of the project must not be blank, longer than 25 characters, or contains illegal characters.
     * </p>
     *
     * @author Jiameng Li
     * @param theName The name of the new project.
     * @param theBudget The total budget of the new project.
     * @param theDescription The description of the new project.
     * @return Whether the project is created successfully.
     */
    public static boolean createProject(final String theName, final BigDecimal theBudget,
                                     final String theDescription) {
        boolean projectCreated = false;

        if (!InputValidator.validName(theName)) {       // If the project name is invalid
            InputValidator.displayInvalidNameMessage();
        } else {
            // Path to the folder where project will be stored.
            String path = "data/" + theName;
            File file = new File(path);

            if (file.exists()) {        // If the project already exists
                JOptionPane.showMessageDialog(null,
                        "The project \"" + theName + "\" already existed.",
                        "Fail to Create Project", JOptionPane.WARNING_MESSAGE);
            } else {
                projectCreated = file.mkdirs();
                // If successfully created a directory for the project, then create text files
                try {
                    file = new File(path + Budget.FILE_NAME);
                    file.createNewFile();
                    file = new File(path + Description.FILE_NAME);
                    file.createNewFile();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                // Assigning project name, budget, expense, and description
                myName = theName;
                myBudget = new Budget("data/" + theName, theBudget);
                myBudget.writeToTXT();
                myDescription = new Description("data/" + theName, theDescription);
                myDescription.writeToTXT();
            }
        }
        return projectCreated;
    }

    /**
     * Get a list of all existing projects with project name and budget.
     * The list will be return as a 2D array that looks like this
     * [project name 1][budget]
     * [project name 2][budget]
     * [project name 3][budget]
     * ...
     * If there's no existing project, it returns an array with zero row and zero column.
     *
     * @author Jiameng Li
     * @return A 2D array of string that represent the list of all existing projects.
     */
    public static String[][] getProjectList() {
        String[][] projectList = new String[0][0];
        File file = new File("data");

        // Only accept directories (projects) in the data folder
        File[] projects = file.listFiles(File::isDirectory);

        if (projects != null && projects.length > 0) {
            // Goes through all the project and read their name, budget, and expense.
            projectList = new String[projects.length][2];
            for (int i = 0; i < projects.length; i++) {
                if (projects[i].isDirectory()) {
                    // Read the project name
                    projectList[i][0] = projects[i].getName();
                    // Read the budget
                    String budgetFilePath = projects[i].getPath() + Budget.FILE_NAME;
                    Budget budget = Budget.loadBudgetFromTXT(budgetFilePath);
                    projectList[i][1] = budget.getSpendingLimit().toString();
                }
            }
        }

        return projectList;
    }

    /**
     * Delete the project and the folder that store its data.
     *
     * @author Jiameng Li
     * @param theName The name of the project to be deleted.
     */
    public static void deleteProject(final String theName) {
        // Path to the project to be deleted.
        String path = "data/" + theName;
        File file = new File(path);

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
    }

    /**
     * Save the changes in the currently opened project since last save.
     * This method should not be called when there's no project opened. No data will be store into the
     * data file before this method is called.
     * <p>
     *     Precondition: The project to be saved is currently opened.
     * </p>
     *
     * @author Jiameng Li
     */
    public static void saveProject() {
        // Path to the folder where project information will be stored.
        String path = "data/" + myName;

        // Save subprojects
        for (Subproject sp : mySubprojects.values()) {
            sp.saveSubproject();
        }
    }

    /**
     * Load the project with given project name.
     * <p>
     *     Precondition: The selected project exists.
     * </p>
     *
     * @author Jiameng Li
     * @param theName The name of the project to be loaded.
     */
    public static void loadProject(final String theName) {
        try {
            // Path to the project to be loaded
            String path = "data/" + theName;

            // Load project name
            myName = theName;

            // Load project budget and expense
            myBudget = Budget.loadBudgetFromTXT(path + Budget.FILE_NAME);

            // Load project description
            myDescription = Description.loadDescriptionFromTXT(path + Description.FILE_NAME);

            // Load subprojects
            File file = new File(path);
            File[] subprojects = file.listFiles(File::isDirectory);
            for (File subproject : Objects.requireNonNull(subprojects)) {
                loadSubproject(subproject);
            }
        } catch (IOException e) {
            System.err.println("Can't load project. " + e);
        }
    }

    /**
     * Close the project by setting project name, budget, and description to null,
     * and clear the list of subprojects.
     * This method auto save the project before it close.
     *
     * @author Jiameng Li
     */
    public static void closeProject() {
        saveProject();
        myName = null;
        myBudget = null;
        myDescription = null;
        mySubprojects.clear();
    }


    // Methods for creating, deleting, and loading subproject

    /**
     * Create a subproject and necessary files and folders to store its data.
     * If the given name is invalid or the subproject already existed, it displays an error/warning message dialog.
     * These text files will remain empty until the saveProject() method is called.
     * This method display a warning massage dialog when the subproject already exists.
     * <p>
     *     Precondition:
     *     1. The subproject doesn't exist in currently opened project.
     *     2. The name of the subproject must not be blank, longer than 25 characters, or contains illegal characters.
     * </p>
     *
     * @author Jiameng Li
     * @param theName The name of the new subproject.
     * @param theBudget The budget of the new subproject.
     * @param theDescription The description of the new subproject.
     * @return The subproject that just been created.
     */
    public static Subproject createSubproject(final String theName, final BigDecimal theBudget,
                                        final String theDescription) {
        Subproject sp = null;
        if (!InputValidator.validName(theName)) {       // If the subproject name is invalid
            InputValidator.displayInvalidNameMessage();
        } else if (mySubprojects.containsKey(theName)) {        // If the subproject already existed
            JOptionPane.showMessageDialog(null, "The subproject \"" +
                    theName + "\" already existed.", "Fail to Create Subproject.",
                    JOptionPane.WARNING_MESSAGE);
        } else {
            // Create folder for subproject
            String path = String.format("data/%s/%s", myName, theName);
            File file = new File(path);
            file.mkdirs();

            // Create text files for budget and description
            try {
                file = new File(path + Budget.FILE_NAME);
                file.createNewFile();
                file = new File(path + Description.FILE_NAME);
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            // Create subfolder for option, notes, and sketches
            for (String s : Subproject.SUBPROJECT_FOLDERS) {
                file = new File(path + s);
                file.mkdirs();
            }

            final Budget budget = new Budget(path, theBudget);
            budget.writeToTXT();
            final Description desc = new Description(path, theDescription);
            budget.writeToTXT();
            sp = new Subproject(theName, budget, desc);
            mySubprojects.put(theName, sp);
        }
        return sp;
    }

    /**
     * Delete the subproject and the folder that stores its data.
     *
     * @author Jiameng Li
     * @param theName The name of the subproject to be deleted.
     */
    public static void deleteSubproject(final String theName) {
        mySubprojects.remove(theName);
        deleteProject(myName + "/" + theName);
    }

    /**
     * Load the subproject stored in given directory (File).
     * This method is declared private because it should only be called within the
     * loadProject() method. The program should not load a subproject without loading a project.
     *
     * @author Jiameng Li
     * @param theSubproject The directory where the subproject is saved.
     */
    private static void loadSubproject(final File theSubproject) {
        try {
            String path = theSubproject.getPath();

            // Load the subproject name
            final String name = theSubproject.getName();

            // Load the subproject budget
            final Budget budget = Budget.loadBudgetFromTXT(path + Budget.FILE_NAME);

            // Load subproject description
            final Description description = Description.loadDescriptionFromTXT(path + Description.FILE_NAME);

            // Load other information and add subproject to the list
            final Subproject subproject = new Subproject(name, budget, description);
            subproject.loadOptionsNotesSketches();
            mySubprojects.put(name, subproject);

        } catch (IOException e) {
            System.out.println("Can't load subproject :" + e);
        }
    }

}