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

    /** This is a path to the file containing the user's name and email. */
    public static final String USER_INFO_FILE_PATH = "src/model/data/user_info.txt";

    /** This is the Person who is using the application. */
    private final Person user;

    /** This is the team of developers who created the application. */
    private final Person[] developers;

    private final double version;

    /**
     * This constructor constructs a new About object.
     */
    public About() {
        user = determineUser();
        developers = new Person[5];
        fillDevelopers();
        version = 0.1;
    }

    /**
     * @return user.
     */
    public Person getUser() {
        return user;
    }

    /**
     * @return developers.
     */
    public Person[] getDevelopers() {
        return developers;
    }

    /**
     * @return version.
     */
    public double getVersion() {
        return version;
    }

    /**
     * This method will fill the developers array with the developers of the program.
     */
    private void fillDevelopers() {
        developers[0] = new Person("Devin Peevy", "dpeevy@uw.edu");
        //TODO: Everybody add your name to the developers array!!
        developers[1] = new Person("Junghyon Jo", "whwheoeo2015@gmail.com");
        developers[2] = new Person("Taylor Merwin", "tmerwin@uw.edu");
        developers[3] = new Person("Jiameng Li", "jli39@uw.edu");
        developers[4] = new Person("Cameron Gregoire", "cgrego2@uw.edu");
    }

    /**
     * This method will parse user_info.txt for the name and email of the user.
     * @return a Person object representing the application's user.
     */
     private Person determineUser() {
        File userInfoFile = new File(USER_INFO_FILE_PATH);
        //Declare a new Scanner.
         Scanner s;
         //Catch Exception if userInfoFile doesn't exist.
         try {
             s = new Scanner(userInfoFile);
         } catch (FileNotFoundException e) {
             throw new RuntimeException(e);
         }
         //Skip the first line, because it will be a header.
         s.nextLine();
         s.useDelimiter(",");
         String userName = s.next();
         String userEmail = s.next();
         return new Person(userName, userEmail);
     }
}
