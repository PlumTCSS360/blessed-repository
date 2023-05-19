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

    private final String description;

    private final String parentFilePath;

    public Description(String parentFilePath, String description) {
        this.description = description;
        this.parentFilePath = parentFilePath;
    }

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


    public String getFilePath() {
        return this.parentFilePath + "/desc.txt";
    }

    public static void main(String[] args) {
        Description desc = new Description("data/sample", "This is a description of sample.");
        desc.writeToTXT();
        try {
            Description desc2 = Description.loadDescriptionFromTXT("data/sample/desc.txt");
            System.out.println(desc2.description);
            System.out.println(desc2.parentFilePath);
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException();
        }
    }
}
