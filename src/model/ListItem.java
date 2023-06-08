package model;

/**
 * Creates the internal ListItem Object used for the to-do list
 * Contains a String name that is then displayed in the TodoPanel
 * Currently only has one param, but opens possibility for more in the future such as Priority, Due Date, etc.
 * @author Taylor Merwin
 */
public class ListItem {

    private final String name;

    /**
     * Constructor for the ListItem
     * Currently only has one param, but opens possibility for more in the future such as Priority, Due Date, etc.
     * @param name
     * @author Taylor Merwin
     */
    public ListItem(String name) {
        this.name = name;
    }

    /**
    * ToString method for ListItem
    * @return String representation of the ListItem
     * @author Taylor Merwin
     */
    @Override
    public String toString() {
        return name;
    }

}
