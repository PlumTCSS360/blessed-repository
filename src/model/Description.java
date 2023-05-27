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
 */
public class Description {

    // STATIC FIELDS

    public static final String FILE_NAME = "desc.txt";

    // INSTANCE FIELDS

    private String description;

    private final String parentFilePath;

    // CONSTRUCTORS

    public Description(String parentFilePath, String description) {
        this.description = description;
        this.parentFilePath = parentFilePath;
    }

    // GETTERS AND SETTERS

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String newDescription) {
        this.description = newDescription;
    }

    public String getFilePath() {
        return this.parentFilePath + "/desc.txt";
    }

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

    // MAIN METHOD (FOR TESTING)

    public static void main(String[] args) {

    }
}
