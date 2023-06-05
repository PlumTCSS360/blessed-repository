package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Creates the to-do list
 * @author Taylor Merwin
 */
public class TodoList {


    public static final String FILE_NAME = "/todo.txt";

    /** The text which is included in the to-do?? */


    private List<ListItem> listItems = new ArrayList<>();

    private final String parentFilePath;

    public TodoList(String parentFilePath, List<ListItem> listItems) {
        this.listItems = listItems;
        this.parentFilePath = parentFilePath;
        //writeToTXT();
    }


    public String getFilePath() {
        return this.parentFilePath + FILE_NAME;
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



    public void writeToTXT() {

        //For each ListItem in the listItems list, add it to the todo string
        try {
            FileWriter fw = new FileWriter(getFilePath());
            fw.write(parentFilePath + "\n");
            for (ListItem item : listItems) {
                fw.write(item.toString() + "\n");
            }
            fw.close();
        }
        catch (IOException e) {
            throw new IllegalArgumentException("ParentFilePath is invalid!");
        }
    }

    //Builds a TodoList from a .txt file
    public static TodoList loadTodoFromTXT(String filePath) throws FileNotFoundException {
        Scanner s;
        String parentFilePath;
        try {
            s = new Scanner(new File(filePath));
        }
        catch (FileNotFoundException e) {
            throw new FileNotFoundException("File not found!");
        }

        List<ListItem> theListItems = new ArrayList<>();

      if (s.hasNextLine()) {
            //For each line in the file, add it to the to-do list as a ListItem
            parentFilePath = s.nextLine();


            while (s.hasNextLine()) {
                String line = s.nextLine();
                String[] splitLine = line.split(" ");
                String name = splitLine[0];
                int priority = Integer.parseInt(splitLine[1]);

                ListItem item = new ListItem(name, priority);
                theListItems.add(item);
            }
        }
        else {
            parentFilePath = "RIP";
        }
        s.close();
        return new TodoList(parentFilePath, theListItems);

    }




}
