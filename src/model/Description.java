package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * This class is used to store a description of an object. A description is just a String describing its Project or
 * Subproject. It is able to write itself to a description.txt file and there is a static method for creating a
 * description object from a .txt file.
 * @author Devin Peevy
 * @version 1.0
 */
public class Description {

    // STATIC FIELDS

    /** The name of every Description File. */
    public static final String FILE_NAME = "desc.txt";

    // INSTANCE FIELDS

    /** The text which is included in the Description. */
    private String description;

    /** The file path for the Project or Subproject to which this Description belongs. */
    private final String parentFilePath;

    // CONSTRUCTORS

    /**
     * This constructor creates a new Description object. It does NOT store it in the data folder; that is for
     * the writeToTXT() method to do.
     * @author Devin Peevy
     * @param parentFilePath The file path of the Project or Subproject to which this Description belongs.
     * @param description The text to be stored in this Description.
     */
    public Description(String parentFilePath, String description) {
        this.description = description;
        this.parentFilePath = parentFilePath;
    }

    // GETTERS

    /**
     * @author Devin Peevy
     * @return description
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * @author Devin Peevy
     * @return the file path of this Description where it is stored in the data folder.
     */
    public String getFilePath() {
        return this.parentFilePath + "/desc.txt";
    }

    /**
     * @author Devin Peevy
     * @return The name of the Project OR Subproject to which this Description belongs
     */
    public String getProjectName() {
        final char[] array = parentFilePath.toCharArray();
        int lastSlash = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i] == '/') {
                lastSlash = i;
            }
        }
        return parentFilePath.substring(lastSlash + 1);
    }

    // SETTERS

    /**
     * Sets a new String as the value for Description. This method does NOT store it in the data folder; that is for the
     * writeToTXT() method to do.
     * @author Devin Peevy
     * @param newDescription The new Description for the String.
     */
    public void setDescription(String newDescription) {
        this.description = newDescription;
    }

    // OTHER INSTANCE METHODS

    public void writeToTXT() {
        try {
            FileWriter fw = new FileWriter(getFilePath());
            fw.write(parentFilePath + "\n" + description);
            fw.close();
        }
        catch (IOException e) {
            throw new IllegalArgumentException("ParentFilePath is invalid!");
        }
    }

    // STATIC METHODS

    public static Description loadDescriptionFromTXT(String filePath) throws FileNotFoundException {
        Scanner s;
        try {
            s = new Scanner(new File(filePath));
        }
        catch (FileNotFoundException e) {
            throw new FileNotFoundException("The file does not exist!");
        }
        String thePFP = s.nextLine();
        StringBuilder sb = new StringBuilder();
        while (s.hasNextLine()) {
            sb.append(s.nextLine());
        }
        String theDesc = sb.toString();
        return new Description(thePFP, theDesc);
    }

}
