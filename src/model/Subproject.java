package model;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

/**
 * Represent a subproject in a project.
 *
 * @author Jiameng Li
 * @version v0.1
 */
public class Subproject {

    /** Folder name of the sub-folders in the subproject. */
    public static final String[] SUBPROJECT_FOLDERS = {"/Options", "/Notes", "/Sketches"};

    /** Name of the subproject. */
    private final String myName;

    /** The budget of the subproject. */
    private BigDecimal myBudget;

    /** The description of the subproject. */
    private String myDescription;

    /** The list of options in subproject. */
    private final Map<String,Option> myOptions;

    /** The list of notes in subproject. */
    private final Map<String, String> myNotes;

    /** The list of sketches in subproject. */
    private final Map<String, ImageIcon> mySketches;

    /**
     * Construct a subproject with given name, budget, and description.
     *
     * @param theName The name of the subproject.
     * @param theBudget The budget of the subproject.
     * @param theDescription The description of the subproject.
     */
    public Subproject(final String theName, final BigDecimal theBudget, final String theDescription) {
        super();
        myName = theName;
        myBudget = theBudget;
        myDescription = theDescription;
        myOptions = new LinkedHashMap<>();
        myNotes = new LinkedHashMap<>();
        mySketches = new LinkedHashMap<>();
    }


    // Getters

    /**
     * Get the name of the subproject.
     *
     * @return The name of the subproject.
     */
    public String getName() {
        return myName;
    }

    /**
     * Get the budget of the subproject.
     *
     * @return The budget of the subproject.
     */
    public BigDecimal getBudget() {
        return myBudget;
    }

    /**
     * Get the description of the subproject.
     *
     * @return The description of the subproject.
     */
    public String getDescription() {
        return myDescription;
    }

    /**
     * Get an option by name.
     *
     * @param theName The name of the option.
     * @return The option with given name, if any.
     */
    public Option getOption(final String theName) {
        return myOptions.get(theName);
    }

    /**
     * Get a copy of the list of options in the subproject.
     *
     * @return A copy of the list of options in the subproject.
     */
    public Map<String, Option> getOptionsList() {
        return Map.copyOf(myOptions);
    }

    /**
     * Get the content of a note by name.
     *
     * @param theName The name of the note.
     * @return The content of the note with given name, if any.
     */
    public String getNote(final String theName) {
        return myNotes.get(theName);
    }

    /**
     * Get a copy of the list of notes in the subproject.
     *
     * @return A copy of the list of notes in the subproject.
     */
    public Map<String, String> getNotesList() {
        return Map.copyOf(myNotes);
    }

    /**
     * Get a sketch by name.
     *
     * @param theName The name of the sketch.
     * @return The sketch with given name.
     */
    public ImageIcon getSketch(final String theName) {
        return mySketches.get(theName);
    }

    /**
     * Get a copy of the list of sketches in the subproject.
     *
     * @return A copy of the list of sketches in the subproject.
     */
    public Map<String, ImageIcon> getSketchesList() {
        return Map.copyOf(mySketches);
    }


    // Setters

    // TODO Add setter method for budget

    /**
     * Set a new subproject description.
     *
     * @param theDescription The new subproject description.
     */
    public void setDescription(final String theDescription) {
        myDescription = theDescription;
    }

    /**
     * Replace the old content in a note with the new content.
     *
     * @param theName The name of the note.
     * @param theNote The new content in the note.
     */
    public void setNoteContent(final String theName, final String theNote) {
        myNotes.replace(theName, theNote);
    }

    /**
     * Replace the old sketch with the new sketch.
     * <p>
     *     Precondition:
     *     1. The file in the path exists.
     *     2. The file in the given path must is an image.
     * </p>
     *
     * @param theName The name of the sketch.
     * @param thePath The path to the new sketch.
     */
    public void setSketchContent(final String theName, final String thePath) {
        // Check if the file exists and is an image
        if (!isValidImageFile(thePath)) {
            JOptionPane.showMessageDialog(null,
                    "The file you choose is not an image.", "Wrong File Format",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        mySketches.replace(theName, new ImageIcon(thePath));
    }


    // Methods to add options, notes, and sketches

    // TODO Validate link address in GUI when creating option

    // TODO Check valid name in GUI when creating option, note, or sketch

    /**
     * Create a new option by creating necessary folders and files for the option and add the option to the list.
     * The text file remain empty until the saveProject() method is called.
     * <p>
     *     Precondition:
     *     1. The option doesn't exist in current subproject.
     *     2. The name of the option must not be empty or contains \ or .
     * </p>
     *
     * @param theName The name of the new option.
     * @param theCost The cost of the new option.
     * @param theDescription The description of the new option.
     * @param theWebsite The website of the new option.
     * @return The option just been created.
     */
    public Option createOption(final String theName, final BigDecimal theCost, final String theDescription,
                             final String theWebsite) {
        Option op = null;
        if (myOptions.containsKey(theName)) {   // Check for duplicate name
            JOptionPane.showMessageDialog(null,
                    "Option \" " + theName + "\" already existed.", "Name duplicate",
                    JOptionPane.WARNING_MESSAGE);
        } else {
            // Create subfolder for this option
            String path = String.format("data/%s/%s/Options/%s", Project.getProjectName(), myName, theName);
            File file = new File(path);
            file.mkdirs();

            // Create necessary text files to store information for the option
            for (int i = 0; i < Option.OPTION_FILE.length; i++) {
                file = new File(path + Option.OPTION_FILE[i]);
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            op = new Option(theName, theCost, theDescription, theWebsite, Option.CONTRACTOR_SETUP,
                    Option.WARRANTY_SETUP);
            myOptions.put(theName, op);
        }
        return op;
    }

    /**
     * Add a new note by adding the name and content to the list.
     * This creates an empty txt file for the note, but the content won't be saved until the saveProject()
     * method is called.
     * <p>
     *     Precondition:
     *     1. The note doesn't exist in current subproject.
     *     2. The name of the note must not be empty or contains \ or .
     * </p>
     *
     * @param theName The name of the new note.
     * @param theNote The content of the note.
     */
    public void createNote(final String theName, final String theNote) {
        if (myNotes.containsKey(theName)) {   // Check for duplicate name
            JOptionPane.showMessageDialog(null,
                    "Note \" " + theName + "\" already existed.", "Name duplicate",
                    JOptionPane.WARNING_MESSAGE);
        } else {
            myNotes.put(theName, theNote);
            String path = String.format("data/%s/%s/Notes/%s.txt", Project.getProjectName(), myName, theName);
            File file = new File(path);
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Add a new sketch by adding the name and image to the list.
     * This creates an empty png file for the sketch, but the content won't be saved until the saveProject()
     * method is called.
     * <p>
     *     Precondition:
     *     1. The sketch doesn't exist in current subproject.
     *     2. The file in the given path must is an image.
     *     3. The name of the sketch must not be empty or contains \ or .
     * </p>
     *
     * @param theName The name of the new note.
     * @param thePath The path to the image file to be added.
     */
    public void createSketch(final String theName, final String thePath) {
        // Check if the file exists and is an image
        if (!isValidImageFile(thePath)) {
            JOptionPane.showMessageDialog(null,
                    "The file you choose is not an image.", "Wrong File Format",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        // Create an empty file for the image and add the image to the list
        if (mySketches.containsKey(theName)) {   // Check for duplicate name
            JOptionPane.showMessageDialog(null,
                    "Sketch \" " + theName + "\" already existed.", "Name duplicate",
                    JOptionPane.WARNING_MESSAGE);
        } else {
            mySketches.put(theName, new ImageIcon(thePath));
            String path = String.format("data/%s/%s/Sketches/%s.png", Project.getProjectName(), myName, theName);
            File file = new File(path);
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Check if the file in the given path is an image.
     *
     * @param thePath The path to the file.
     * @return Whether the file exists and is an image.
     */
    private boolean isValidImageFile(final String thePath) {
        File file = new File(thePath);
        boolean valid = file.exists();
        try {
            if (ImageIO.read(file) == null) {
                valid = false;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return valid;
    }


    // Methods to delete options, notes, and sketches

    /**
     * Delete an option by deleting all folders and files that store its data and remove it from the list.
     * <p>
     *     Precondition: The option exists in current subproject.
     * </p>
     *
     * @param theName The name of the option to be deleted.
     */
    public void deleteOption(final String theName) {
        if (myOptions.containsKey(theName)) {
            myOptions.remove(theName);
            Project.deleteProject(String.format("/%s/%s/Options/%s", Project.getProjectName(), myName, theName));
        }
    }

    /**
     * Delete a note by deleting the text file that store its data and remove it from the list.
     * <p>
     *     Precondition: The note exists in current subproject.
     * </p>
     *
     * @param theName The name of the note to be deleted.
     */
    public void deleteNote(final String theName) {
        if (myNotes.containsKey(theName)) {
            myNotes.remove(theName);
            String path = String.format("data/%s/%s/Notes/%s.txt", Project.getProjectName(), myName, theName);
            File file = new File(path);
            file.delete();
        }
    }

    /**
     * Delete a sketch by deleting the image file that store its data and remove it from the list.
     * <p>
     *     Precondition: The sketch exists in current subproject.
     * </p>
     *
     * @param theName The name of the sketch to be deleted.
     */
    public void deleteSketch(final String theName) {
        if (mySketches.containsKey(theName)) {
            mySketches.remove(theName);
            String path = String.format("data/%s/%s/Sketches/%s.png", Project.getProjectName(), myName, theName);
            File file = new File(path);
            file.delete();
        }
    }

    /**
     * Load the notes and sketches in the subproject by reading the contents in their text files
     * and add them to the list.
     * It calls another method to load options since different options are store in different folders.
     * This method should only be called in the loadSubproject() method in the Project class.
     */
    protected void loadOptionsNotesSketches() {
        // Path to the subproject
        String path = String.format("data/%s/%s", Project.getProjectName(), myName);

        // Load the list of options
        File file = new File(path + "/Options");
        for (File option : Objects.requireNonNull(file.listFiles())) {
            loadOption(option);
        }

        // Load the list of notes
        file = new File(path + "/Notes");
        for (File subFiles : Objects.requireNonNull(file.listFiles())) {
            String name = subFiles.getName();
            name = name.substring(0, name.length() - 4);
            final String note = FileAccessor.readTxtFile(subFiles.getPath());
            myNotes.put(name, note);
        }

        // Load the list of sketches
        file = new File(path + "/Sketches");
        for (File subFiles : Objects.requireNonNull(file.listFiles())) {
            String name = subFiles.getName();
            name = name.substring(0, name.length() - 4);
            mySketches.put(name, new ImageIcon(subFiles.getPath()));
        }
    }

    /**
     * Load an option from the folder that store its data and add it to the list.
     *
     * @param theOption The folder that store the data of the option.
     */
    private void loadOption(final File theOption) {
        try {
            String path = theOption.getPath();

            // Load the option name
            final String name = theOption.getName();

            // Load the option cost
            File file = new File(path + "/Cost.txt");
            Scanner scanner = new Scanner(file);
            final BigDecimal cost = scanner.nextBigDecimal();
            scanner.close();

            // Load the description, website, contractor information, and warranty information for the option
            final String description = FileAccessor.readTxtFile(path + "/Description.txt");
            final String website = FileAccessor.readTxtFile(path + "/Website.txt");
            final String contractor = FileAccessor.readTxtFile(path + "/Contractor.txt");
            final String warranty = FileAccessor.readTxtFile(path + "/Warranty.txt");

            myOptions.put(name, new Option(name, cost, description, website, contractor, warranty));
        } catch (IOException e) {
            System.out.println("Can't load subproject :" + e);
        }
    }


    // Method to save subproject

    /**
     * Save the information of the subproject in the file associated with it,
     * include saving the current budget, description, options, notes, and sketches.
     *
     * @param theProjectName The name of the project the subproject belongs to.
     */
    protected void saveSubproject(final String theProjectName) {
        // Path to the folder where project information will be stored.
        String path = String.format("data/%s/%s", theProjectName, myName);

        // Save budget and expense
        FileAccessor.writeTxtFile(path + "/Budget.txt", myBudget.toString());

        // Save project description
        FileAccessor.writeTxtFile(path + "/Description.txt", myDescription);

        // Save options
        for (Option op : myOptions.values()) {
            op.saveOption(theProjectName, myName);
        }

        // Save notes
        for (String noteName : myNotes.keySet()) {
            final String filepath = String.format("%s/Notes/%s.txt", path, noteName);
            FileAccessor.writeTxtFile(filepath, myNotes.get(noteName));
        }

        // Save sketches
        for (String sketchName : mySketches.keySet()) {
            final String filepath = String.format("%s/Sketches/%s.png", path, sketchName);
            FileAccessor.writePngFile(filepath, mySketches.get(sketchName));
        }
    }

}
