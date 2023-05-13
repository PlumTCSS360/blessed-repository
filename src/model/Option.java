package model;

import java.math.BigDecimal;
import java.net.URI;
import java.net.URL;

/**
 * Represent an option in a subproject.
 *
 * @author Jiameng Li
 * @version v0.1
 */
public class Option {

    /** The text files needed to store data for the option. */
    public static final String[] OPTION_FILE = {"/Cost.txt", "/Description.txt", "/Website.txt",
                "/Contractor.txt", "/Warranty.txt"};

    /** The setup text for contractor information in a new option. */
    public static final String CONTRACTOR_SETUP = """
                Name:
                Phone:
                Email:
                
                Additional Information:
                """;

    /** The setup text for warranty information in a new option. */
    public static final String WARRANTY_SETUP = """
                Service Name:
                Phone:
                Email:
                Service Details:
                
                Additional Information:
                """;

    /** The name of the option. */
    private final String myName;

    /** The cost of the option. */
    private BigDecimal myCost;

    /** The description of the option. */
    private String myDescription;

    /** The website of the option. */
    private URI myWebsite;

    /** The contractor information of the option. */
    private String myContractorInfo;

    /** The warranty information of the option. */
    private String myWarrantyInfo;

    /**
     * Create an option with given name, cost, description, website, contractor information, and warranty information.
     *
     * @param theName The name of the option.
     * @param theCost The cost of the option.
     * @param theDescription The description of the option.
     * @param theWebsite The website of the option.
     * @param theContractorInfo The contractor information of the option.
     * @param theWarrantyInfo The warranty information of the option.
     */
    public Option(final String theName, final BigDecimal theCost, final String theDescription,
                  final String theWebsite, final String theContractorInfo, final String theWarrantyInfo) {
        super();
        myName = theName;
        myCost = theCost;
        myDescription = theDescription;
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
     * @return The cost of the option.
     */
    public BigDecimal getCost() {
        return myCost;
    }

    /**
     * Get the description of the option.
     *
     * @return The description of the option.
     */
    public String getDescription() {
        return myDescription;
    }

    /**
     * Get the link to the website of the option.
     *
     * @return The link to the website of the option.
     */
    public URI getWebsite() {
        return myWebsite;
    }

    /**
     * Get the contractor information of the option.
     *
     * @return The contractor information of the option.
     */
    public String getContractorInfo() {
        return myContractorInfo;
    }

    /**
     * Get the warranty information of the option.
     *
     * @return The warranty information of the option.
     */
    public String getWarrantyInfo() {
        return myWarrantyInfo;
    }


    // Setter

    /**
     * Set a new cost for the option.
     *
     * @param theCost The new cost for the option.
     */
    public void setCost(final BigDecimal theCost) {
        myCost = theCost;
    }

    /**
     * Set a new description for the option.
     *
     * @param theDescription The new description for the option.
     */
    public void setDescription(final String theDescription) {
        myDescription = theDescription;
    }

    /**
     * Set a new link of the website for the option.
     *
     * @param theWebsite The new link of the website for the option.
     */
    public void setWebsite(final String theWebsite) {
        if (theWebsite.length() > 0) {
            try {
                URL link = new URL(theWebsite);
                myWebsite = link.toURI();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Set a new contractor information for the option.
     *
     * @param theContractorInfo The new contractor information for the option.
     */
    public void setContractorInfo(final String theContractorInfo) {
        myContractorInfo = theContractorInfo;
    }

    /**
     * Set a new warranty information for the option.
     *
     * @param theWarrantyInfo The new warranty information for the option.
     */
    public void setWarrantyInfo(final String theWarrantyInfo) {
        myWarrantyInfo = theWarrantyInfo;
    }

    /**
     * Save the information of the option in the txt files associated with it.
     *
     * @param theProjectName The name of the project the option belongs to.
     * @param theSubprojectName The name of the subproject the option belongs to.
     */
    protected void saveOption(final String theProjectName, final String theSubprojectName) {
        // Path to the folder where project information will be stored.
        String path = String.format("data/%s/%s/Options/%s", theProjectName, theSubprojectName, myName);

        // Save cost, description, website, contractor information, and warranty information
        FileAccessor.writeTxtFile(path + "/Cost.txt", myCost.toString());
        FileAccessor.writeTxtFile(path + "/Description.txt", myDescription);
        if (myWebsite == null) {        // If there's no website, store empty string
            FileAccessor.writeTxtFile(path + "/Website.txt", "");
        } else {
            FileAccessor.writeTxtFile(path + "/Website.txt", myWebsite.toString());
        }
        FileAccessor.writeTxtFile(path + "/Contractor.txt", myContractorInfo);
        FileAccessor.writeTxtFile(path + "/Warranty.txt", myWarrantyInfo);
    }
}
