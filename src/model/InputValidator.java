package model;

import org.apache.commons.validator.routines.UrlValidator;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.File;
import java.io.IOException;

/**
 * Input validator that determine whether the name, website, or image file is valid.
 * This class also contains method to display error message dialog for invalid input.
 *
 * @author Jiameng Li
 * @version v0.3
 */
public class InputValidator {

    /** Url validator used to validate link address for the website. */
    private static final UrlValidator DEFAULT_URL_VALIDATOR = new UrlValidator();

    /** Illegal characters that an invalid name contains. */
    private static final String[] ILLEGAL_CHAR = {"`", "~", "!", "@", "#", "$", "%", "^", "&", "*", "(", ")",
                                                  "+", "=", "{", "}", "|", "\\", "/", ":", ";", "\"", "<", ">",
                                                  ",", ".", "?", "[", "]"};

    /**
     * Determine whether the name is valid as a directory or filename.
     * A valid name should be within 25 characters and should not contain any illegal characters.
     * It also should not be blank.
     *
     * @author Jiameng Li
     * @param theName The name to be validated.
     * @return Whether the name is valid.
     */
    public static boolean validName(final String theName) {
        boolean valid = !theName.isBlank() && theName.length() < 25;;
        for (int i = 0; i < ILLEGAL_CHAR.length && valid; i++) {
            valid = !theName.contains(ILLEGAL_CHAR[i]);
        }
        if (valid) {
            valid = !theName.equals("budget") && !theName.equals("desc")
                    && !theName.equals("Budget") && !theName.equals("Description");
        }
        return valid;
    }

    /**
     * Determine whether the given link for the website is valid.
     * Be aware that it only check whether the link is in right format, but not
     * whether the link leads to a working website.
     * It also allows a blank string to be a valid link.
     *
     * @author Jiameng Li
     * @param theWebsite The link for the website.
     * @return True if the link is valid or blank.
     */
    public static boolean validWebsite(final String theWebsite) {
        return DEFAULT_URL_VALIDATOR.isValid(theWebsite) || theWebsite.isBlank();
    }

    /**
     * Check if the file in the given path is an image.
     *
     * @author Jiameng Li
     * @param thePath The path to the file.
     * @return Whether the file exists and is an image.
     */
    public static boolean validImageFile(final String thePath) {
        File file = new File(thePath);
        boolean valid = true;
        try {
            if (!file.exists() || ImageIO.read(file) == null) {
                valid = false;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return valid;
    }

    /**
     * Display an error message dialog for invalid name.
     *
     * @author Jiameng Li
     */
    public static void displayInvalidNameMessage() {
        JOptionPane.showMessageDialog(null,
                """
                The name you enter is blank, longer than 25 characters,
                or contains illegal characters. The name cannot be
                \"budget\" or \"desc\" or \"Budget\" or \"Description\".""",
                "Invalid Name", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Display an error message dialog for invalid link.
     *
     * @author Jiameng Li
     */
    public static void displayInvalidUrlMessage() {
        JOptionPane.showMessageDialog(null,
                "The link address you enter is invalid.",
                "Invalid Link", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Display an error message dialog for invalid file for image.
     *
     * @author Jiameng Li
     */
    public static void displayInvalidImageFileMessage() {
        JOptionPane.showMessageDialog(null,
                "The file you choose is not an image.", "Invalid File",
                JOptionPane.ERROR_MESSAGE);
    }

}
