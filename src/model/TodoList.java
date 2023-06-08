package model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Creates the internal to-do list Object
 * Contains a List of ListItems that are then displayed in the TodoPanel
 *
 * @author Taylor Merwin
 */
public class TodoList {

    public static final String FILE_NAME = "/todo.txt";
    private final List<ListItem> listItems;
    private final String parentFilePath;

    /**
     * Constructor for the to-do list
     * @param parentFilePath The file path of the project to which this to-do list belongs
     * @param listItems      The list of to-do items to be added to the list
     * @author Taylor Merwin
     */
    public TodoList(String parentFilePath, List<ListItem> listItems) {
        this.listItems = listItems;
        this.parentFilePath = parentFilePath;
        writeToTXT();
    }

    /**
     * Getter for the file path of the to-do list
     *
     * @return The file path of the to-do list
     * @author Taylor Merwin
     */
    public String getFilePath() {
        return this.parentFilePath + FILE_NAME;
    }

    /**
     * @return The name of the Project OR Subproject to which this Description belongs
     * @author Devin Peevy
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

    /**
     * Getter for the internal list of to-do items
     * Used by the todoPanel to generate the displayed list
     *
     * @author Taylor Merwin
     */
    public List<ListItem> getListItems() {
        return listItems;
    }

    /**
     * Writes the contents of the to-do list to a .txt file
     * @author Taylor Merwin
     */
    public void writeToTXT() {
        //For each ListItem in the listItems list, write it to the todo.txt file
        try {
            FileWriter fw = new FileWriter(getFilePath(), false);
            fw.write(parentFilePath + "\n");
            for (ListItem item : listItems) {
                fw.write(item.toString() + "\n");
            }
            fw.close();
        } catch (IOException e) {
            throw new IllegalArgumentException("ParentFilePath is invalid!");
        }
    }

    /**
     * Generates the internal to-do list from a .txt file
     * @param filePath The path to the .txt file to be read
     * @return New TodoList generated from the .txt file
     * @author Taylor Merwin
     */
    public static TodoList loadTodoFromTXT(String filePath) throws FileNotFoundException {
        Scanner s;
        String parentFilePath;
        try {
            s = new Scanner(new File(filePath));
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("The todoList File not found!");
        }
        List<ListItem> theListItems = new ArrayList<>();
        //For each line in the file, add it to the to-do list as a ListItem
        parentFilePath = s.nextLine();
        while (s.hasNextLine()) {
            String name = s.nextLine();
            ListItem item = new ListItem(name);
            theListItems.add(item);
        }
        s.close();
        return new TodoList(parentFilePath, theListItems);

    }

}
