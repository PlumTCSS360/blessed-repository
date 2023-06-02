package model;

import javax.swing.*;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Represent an option in a subproject.
 *
 * @author Jiameng Li
 * @version 0.3
 */
public class Option {

    /** The text files needed to store data for the option. */
    public static final String[] OPTION_FILE = {"/cost.txt", "/desc.txt", "/website.txt",
                "/contractor.txt", "/warranty.txt"};

    /** The setup text for contractor information in a new option. */
    public static final String CONTRACTOR_SETUP = """
                Name:
                Phone:
                Email:
                
                Additional Information:""";

    /** The setup text for warranty information in a new option. */
    public static final String WARRANTY_SETUP = """
                Service Name:
                Phone:
                Email:
                Service Details:
                
                Additional Information:""";

    /** The name of the option. */
    private final String myName;

    /** The cost of the option. */
    private BigDecimal myCost;

    /** The description of the option. */
    private Description myDescription;

    /** The website of the option. */
    private URI myWebsite;

    /** The contractor information of the option. */
    private String myContractorInfo;

    /** The warranty information of the option. */
    private String myWarrantyInfo;

    /** Contents been modified since last save. */
    private final Map<String, String> myModifiedContents;

    /**
     * Create an option with given name, cost, description, website, contractor information,
     * and warranty information.
     *
     * @author Jiameng Li
     * @param theName The name of the option.
     * @param theCost The cost of the option.
     * @param theDescription The description of the option.
     * @param theWebsite The website of the option.
     * @param theContractorInfo The contractor information of the option.
     * @param theWarrantyInfo The warranty information of the option.
     * @param theLoad Whether the program is loading the option.
     */
    public Option(final String theName, final BigDecimal theCost, final Description theDescription,
                  final String theWebsite, final String theContractorInfo, final String theWarrantyInfo,
                  final boolean theLoad) {
        super();
        myName = theName;
        myCost = theCost;
        myDescription = theDescription;
        // Create a URI using the given link
        if (theWebsite.length() > 0) {
            try {
                URL link = new URL(theWebsite);
                myWebsite = link.toURI();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        myContractorInfo = theContractorInfo;
        myWarrantyInfo = theWarrantyInfo;
        myModifiedContents = new HashMap<>();
        // If the option is created instead of loaded, record the changes
        if (!theLoad) {
            myModifiedContents.put("cost", theCost.toString());
            myModifiedContents.put("website", theWebsite);
            myModifiedContents.put("contractor", theContractorInfo);
            myModifiedContents.put("warranty", theWarrantyInfo);
        }
    }


    // Getters

    /**
     * Get the name of the option.
     *
     * @return The name of the option.
     */
    public String getName() {
        return myName;
    }

    /**
     * Get the cost of the option.
     *
     * @author Jiameng Li
     * @return The cost of the option.
     */
    public BigDecimal getCost() {
        return myCost;
    }

    /**
     * Get the description of the option.
     *
     * @author Jiameng Li
     * @return The description of the option.
     */
    public Description getDescription() {
        return myDescription;
    }

    /**
     * Get the link to the website of the option.
     *
     * @author Jiameng Li
     * @return The link to the website of the option.
     */
    public URI getWebsite() {
        return myWebsite;
    }

    /**
     * Get the contractor information of the option.
     *
     * @author Jiameng Li
     * @return The contractor information of the option.
     */
    public String getContractorInfo() {
        return myContractorInfo;
    }

    /**
     * Get the warranty information of the option.
     *
     * @author Jiameng Li
     * @return The warranty information of the option.
     */
    public String getWarrantyInfo() {
        return myWarrantyInfo;
    }


    // Setter

    /**
     * Set a new cost for the option and record the change.
     *
     * @author Jiameng Li
     * @param theCost The new cost for the option.
     */
    public void setCost(final BigDecimal theCost) {
        myCost = theCost;
        myModifiedContents.put("cost", theCost.toString());
    }

    /**
     * Set a new link of the website for the option and record the change.
     * If the website is invalid, it displays an error message dialog.
     * If the website is blank, set the website to null.
     * <p>
     *     Precondition: The given url is valid or blank.
     * </p>
     *
     * @author Jiameng Li
     * @param theWebsite The new link of the website for the option.
     */
    public void setWebsite(final String theWebsite) {
        if(!InputValidator.validWebsite(theWebsite)) {
            InputValidator.displayInvalidUrlMessage();
        } else if (theWebsite.isBlank()) {
            myWebsite = null;
        } else {
            try {
                URL link = new URL(theWebsite);
                myWebsite = link.toURI();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            myModifiedContents.put("website", theWebsite);
        }
    }

    /**
     * Set a new contractor information for the option and record the change.
     *
     * @author Jiameng Li
     * @param theContractorInfo The new contractor information for the option.
     */
    public void setContractorInfo(final String theContractorInfo) {
        myContractorInfo = theContractorInfo;
        myModifiedContents.put("contractor", theContractorInfo);
    }

    /**
     * Set a new warranty information for the option and record the change.
     *
     * @author Jiameng Li
     * @param theWarrantyInfo The new warranty information for the option.
     */
    public void setWarrantyInfo(final String theWarrantyInfo) {
        myWarrantyInfo = theWarrantyInfo;
        myModifiedContents.put("warranty", theWarrantyInfo);
    }

    /**
     * Save the information about the option in the txt files
     * Use the list of modified content to identify changes.
     * Description is auto saved.
     *
     * @author Jiameng Li
     * @param theSubprojectName The name of the subproject the option belongs to.
     */
    protected void saveOption(final String theSubprojectName) {
        // Path to the folder where project information will be stored.
        String path = String.format("data/%s/%s/Options/%s", Project.getProjectName(), theSubprojectName, myName);

        // Save cost, website, contractor information, and warranty information
        for (String s : myModifiedContents.keySet()) {
            FileAccessor.writeTxtFile(String.format("%s/%s.txt", path, s), myModifiedContents.get(s));
        }

        // Clear the recorded changes
        myModifiedContents.clear();
    }

    /**
     * Return a string that represent the option.
     *
     * @author Jiameng Li
     * @return A string that represent the option.
     */
    @Override
    public String toString() {
        return myName;
    }
}
