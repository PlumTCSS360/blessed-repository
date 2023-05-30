package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * This class contains the information to be stored in the AboutFrame.
 * This includes a Person object for the user, as well as for each developer of the application.
 * @author Devin Peevy
 * @version 0.1
 */
public final class About {

    // STATIC FIELDS

    /** This is a path to the file containing the user's name and email. */
    public static final String USER_INFO_FILE_PATH = "data/user_info.txt";

    // INSTANCE FIELDS

    /** This is the Person who is using the application. */
    private final Person user;

    private final Person[] developers = new Person[5];


    /** Scanner used to parse the user_info.txt file. */
    private Scanner fileScanner;

    /** This is the version number of the program. */
    private final double version = 0.3;

    // CONSTRUCTOR

    /**
     * This constructor constructs a new About object.
     * @author Devin Peevy
     */
    public About() {
        user = determineUser();
        fillDevelopers();
    }

    // GETTERS

    /**
     * @author Devin Peevy
     * @return user.
     */
    public Person getUser() {
        return user;
    }


    /**
     * @return Developers of project.
     * @author Devin Peevy
     */
    public Person[] getDevelopers() {
        return developers;
    }

    /**
     * @author Devin Peevy
     * @return version.
     */
    public double getVersion() {
        return version;
    }

    // PRIVATE METHODS

    /**
     * This method will fill the developers array with the developers of the program.
     * @author Devin Peevy
     * @author Junghyon Jo
     * @author Taylor Merwin
     * @author Jiameng Li
     * @author Cameron Gregoire
     */
    private void fillDevelopers() {
        developers[0] = new Person("Devin Peevy", "dpeevy@uw.edu");
        developers[1] = new Person("Junghyon Jo", "whwheoeo2015@gmail.com");
        developers[2] = new Person("Taylor Merwin", "tmerwin@uw.edu");
        developers[3] = new Person("Jiameng Li", "jli39@uw.edu");
        developers[4] = new Person("Cameron Gregoire", "cgrego2@uw.edu");
    }

    /**
     * This method will parse user_info.txt for the name and email of the user.
     * @author Devin Peevy
     * @return a Person object representing the application's user.
     */
     private Person determineUser() {
        File userInfoFile = new File(USER_INFO_FILE_PATH);

         //Catch Exception if userInfoFile doesn't exist.
         try {
             fileScanner = new Scanner(userInfoFile);
         } catch (FileNotFoundException e) {
             throw new RuntimeException(e);
         }
         //Skip the first line, because it will be a header.
         fileScanner.nextLine();
         fileScanner.useDelimiter(",");
         String userName = fileScanner.next();
         String userEmail = fileScanner.next();
         return new Person(userName, userEmail);
     }

}
