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
 * @version v0.1
 */
public final class Project {

    /** File name for the necessary files for the project and subproject. */
    public static final String[] BASIC_INFO_FILE = {"/Budget.txt", "/Description.txt"};

    // These name, budget, and description will be null if there's no opened project.

    /** The name of the currently opened project. */
    private static String myName;

    /** The total budget of the currently opened project. */
    private static BigDecimal myBudget;

    /** The description of the currently opened project. */
    private static String myDescription;

    /** The subprojects in the currently opened project. */
    private static final Map<String, Subproject> mySubprojects = new TreeMap<>();

    /** Contents (budget and description) been modified since last save. */
    private static final Map<String, String> myModifiedContents = new HashMap<>();

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
     * Get the project budget.
     *
     * @return The project budget.
     */
    public static BigDecimal getBudget() {
        return myBudget;
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
     * Get a subproject from the list by the subproject name.
     *
     * @param theName The name of the subproject to get.
     * @return A subproject with given name, if there's such subproject.
     */
    public static Subproject getSubproject(final String theName) {
        return mySubprojects.get(theName);
    }

    /**
     * Get a copy of the list of subproject.
     *
     * @return A copy of the list of subproject.
     */
    public static Map<String, Subproject> getSubprojectsList() {
        return Map.copyOf(mySubprojects);
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
        File file = new File("data");

        // Only accept directories (projects) in the data folder
        File[] projects = file.listFiles(File::isDirectory);

        if (projects != null && projects.length > 0) {
            // Goes through all the project and read their name, budget and expense.
            projectList = new String[projects.length][2];
            for (int i = 0; i < projects.length; i++) {
                if (projects[i].isDirectory()) {
                    // Read the project name
                    projectList[i][0] = projects[i].getName();
                    // Read the budget
                    projectList[i][1] = FileAccessor.readTxtFile(projects[i].getPath() + "/Budget.txt");

                }
            }
        }

        return projectList;
    }


    // Setters

    // TODO Create a method to set the budget and expense

    /**
     * Set a new project description and record change.
     *
     * @param theDescription The new project description.
     */
    public static void setProjectDescription(final String theDescription) {
        myDescription = theDescription;
        myModifiedContents.put("Description", theDescription);
    }


    // Methods for creating, deleting, loading, saving, and closing project

    // TODO Check valid project name in NewFrame
    /**
     * Create a new project with given name, budget, and description.
     * If the project already existed, display a warning message dialog.
     * This method only creates necessary text files for a new project. These text files will remain
     * empty until the saveProject() method is called.
     * <p>
     *      Precondition:
     *      1. The project doesn't exist. In other words, the project name hasn't been used.
     *      2. The name of the project must not be empty or contains \ or .
     * </p>
     *
     * @param theName The name of the new project.
     * @param theBudget The total budget of the new project.
     * @param theDescription The description of the new project.
     * @return Whether the project is created successfully.
     */
    public static boolean createProject(final String theName, final BigDecimal theBudget,
                                     final String theDescription) {
        boolean projectCreated = false;

        // Path to the folder where project will be stored.
        String path = "data/" + theName;
        File file = new File(path);

        if (!file.exists()) {
            // Assigning project name, budget, expense, and description
            myName = theName;
            myBudget = theBudget;
            myDescription = theDescription;
            // Record changes
            myModifiedContents.put("Budget", theBudget.toString());
            myModifiedContents.put("Description", theDescription);

            projectCreated = file.mkdirs();
            // If successfully created a directory for the project, then create text files
            for (int i = 0; i < BASIC_INFO_FILE.length && projectCreated; i++) {
                file = new File(path + BASIC_INFO_FILE[i]);
                try {
                    projectCreated = file.createNewFile();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        } else {
            JOptionPane.showMessageDialog(null,
                    "The project \"" + theName + "\" already existed.",
                    "Can't create project", JOptionPane.WARNING_MESSAGE);
        }
        return projectCreated;
    }

    /**
     * Delete the project and the folder that store its data.
     *
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
     * It only save changes recorded in the list of modified content and deleting a subproject doesn't count
     * as a change.
     * This method should not be called when there's no project opened. No data will be store into the
     * data file before this method is called.
     * <p>
     *     Precondition: The project to be saved is currently opened.
     * </p>
     */
    public static void saveProject() {
        // Path to the folder where project information will be stored.
        String path = "data/" + myName;

        // Save budget and description
        for (String s : myModifiedContents.keySet()) {
            FileAccessor.writeTxtFile(String.format("%s/%s.txt", path, s), myModifiedContents.get(s));
        }

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
     * @param theName The name of the project to be loaded.
     */
    public static void loadProject(final String theName) {
        try {
            // Path to the project to be loaded
            String path = "data/" + theName;

            // Load project name
            myName = theName;

            // Load project budget and expense
            File file = new File(path + "/Budget.txt");
            Scanner scanner = new Scanner(file);
            myBudget = scanner.nextBigDecimal();
            scanner.close();

            // Load project description
            myDescription = FileAccessor.readTxtFile(path + "/Description.txt");

            // Load subprojects
            file = new File(path);
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
     */
    public static void closeProject() {
        saveProject();
        myName = null;
        myBudget = null;
        myDescription = null;
        mySubprojects.clear();
    }


    // Methods for creating, deleting, and loading subproject

    // TODO Check valid subproject name in GUI
    /**
     * Create a subproject and necessary files and folders to store its data.
     * These text files will remain empty until the saveProject() method is called.
     * This method display a warning massage dialog when the subproject already exists.
     * <p>
     *     Precondition:
     *     1. The subproject doesn't exist in currently opened project.
     *     2. The name of the subproject must not be empty or contains \ or .
     * </p>
     *
     * @param theName The name of the new subproject.
     * @param theBudget The budget of the new subproject.
     * @param theDescription The description of the new subproject.
     * @return The subproject that just been created.
     */
    public static Subproject createSubproject(final String theName, final BigDecimal theBudget,
                                        final String theDescription) {
        Subproject sp = null;
        // Check for duplicate name
        if (mySubprojects.containsKey(theName)) {
            JOptionPane.showMessageDialog(null, "The subproject \"" +
                    theName + "\" already existed.", "Can't create subproject.",
                    JOptionPane.WARNING_MESSAGE);
        } else {
            // Create folder for subproject
            String path = String.format("data/%s/%s", myName, theName);
            File file = new File(path);
            file.mkdirs();

            // Create text files for budget and description
            for (String s : BASIC_INFO_FILE) {
                file = new File(path + s);
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            // Create subfolder for option, notes, and sketches
            for (String s : Subproject.SUBPROJECT_FOLDERS) {
                file = new File(path + s);
                file.mkdirs();
            }

            sp = new Subproject(theName, theBudget, theDescription, false);
            mySubprojects.put(theName, sp);
        }
        return sp;
    }

    /**
     * Delete the subproject and the folder that stores its data.
     *
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
     * @param theSubproject The directory where the subproject is saved.
     */
    private static void loadSubproject(final File theSubproject) {
        try {
            String path = theSubproject.getPath();

            // Load the subproject name
            final String name = theSubproject.getName();

            // Load the subproject budget
            File file = new File(path + "/Budget.txt");
            Scanner scanner = new Scanner(file);
            final BigDecimal budget = scanner.nextBigDecimal();
            scanner.close();

            // Load subproject description
            final String description = FileAccessor.readTxtFile(path + "/Description.txt");

            // Load other information and add subproject to the list
            final Subproject subproject = new Subproject(name, budget, description, true);
            subproject.loadOptionsNotesSketches();
            mySubprojects.put(name, subproject);

        } catch (IOException e) {
            System.out.println("Can't load subproject :" + e);
        }
    }

}