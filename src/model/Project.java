package model;

import java.io.File;
import java.io.FileWriter;
import java.math.BigDecimal;

/**
 * This class represent the Project in to application.
 *
 * @author Jiameng Li
 * @version v0.1
 */
public final class Project {

    // These fields will be null if there's no opened project.

    /** The name of the project. */
    private static String myName;

    /** The total budget of the project. */
    private static BigDecimal myBudget;

    /** The total expense of the project. */
    private static BigDecimal myExpense;

    /** The project description. */
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


    // Setters

    /**
     * Set a new project name.
     *
     * @param theName The new project name.
     */
    public static void setProjectName(final String theName) {
        myName = theName;
    }

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
     *
     * Steps:
     * 1. Assign name, budget, expense, and description to the field since the project will be opened.
     * 2. Create a folder for the project and a budget and a description text file inside that folder.
     * 3. Write given budget and expense in the budget file in the format: expense/budget.
     * 4. Write given description in the description file.
     *
     * Precondition: The project doesn't exist. In other words, the project name hasn't been used.
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

                // Create a directory for the project
                file.mkdirs();

                // Create a budget file and write budget and expense in it
                file = new File(path + "/Budget.txt");
                file.createNewFile();
                FileWriter writer = new FileWriter(file, false);
                writer.write(myExpense.toString() + "/" + myBudget.toString());
                writer.close();

                // Create a description file and write description in it
                file = new File(path + "/Description.txt");
                file.createNewFile();
                writer = new FileWriter(file, false);
                writer.write(myDescription);
                writer.close();
            } else {
                // TODO Change this to a dialog that tells the user the project already existed
                System.out.println("Project existed");
            }
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    /**
     * Delete the project and files that store its data.
     *
     * Precondition: The selected project existed.
     *
     * @param theName The name of the project to be deleted.
     */
    public static void deleteProject(final String theName) {
        try {
            // Path to the project to be deleted.
            String path = "src/data/" + theName;
            File file = new File(path);

            if (file.exists()) {
                // Recursively delete files in the project folder so the project folder can be deleted.
                for (File subFiles : file.listFiles()) {
                    if (subFiles.isDirectory()) {
                        deleteProject(theName + "/" + subFiles.getName());
                    } else {
                        subFiles.delete();
                    }
                }
                // Delete the project folder.
                file.delete();
            } else {
                // TODO Change this to a dialog that tells the user the project doesn't exist
                System.out.println("Project doesn't exist");
            }
        } catch (Exception e) {
            System.err.println(e);
        }
    }

}
