package model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Creates the to-do list
 * @author Taylor Merwin
 */
public class TodoList {


    public static final String FILE_NAME = "/todo.txt";

    private List<ListItem> listItems;

    private final String parentFilePath;

    /**
     * @author Taylor Merwin
     */
    public TodoList(String parentFilePath, List<ListItem> listItems) {
        this.listItems = listItems;
        this.parentFilePath = parentFilePath;
        writeToTXT();
    }


    /**
     * @author Taylor Merwin
     */
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

    /**
     * @author Taylor Merwin
     */
    public List<ListItem> getListItems() {
        return listItems;
    }


    /**
     * @author Taylor Merwin
     */
    public void writeToTXT() {

        //For each ListItem in the listItems list, add it to the todo string
        try {
            FileWriter fw = new FileWriter(getFilePath(), false);
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

    public void removeFromFile(String item) {
        File inputFile = new File(getFilePath());
        File tempFile = new File("temp.txt");

        BufferedReader reader = null;
        BufferedWriter writer = null;
        String lineToRemove = item;
        String currentLine;

        try {
            reader = new BufferedReader(new FileReader(inputFile));
            writer = new BufferedWriter(new FileWriter(tempFile));

            while((currentLine = reader.readLine()) != null) {
                // trim newline when comparing with lineToRemove
                String trimmedLine = currentLine.trim();
                if(trimmedLine.equals(lineToRemove)) continue;
                writer.write(currentLine + System.getProperty("line.separator"));
            }
        } catch(IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (reader != null) reader.close();
                if (writer != null) writer.close();
            } catch(IOException ex) {
                ex.printStackTrace();
            }
        }

        if (!inputFile.delete()) {
            System.out.println("Could not delete original file");
            return;
        }

        // Rename the new file to the filename the original file had.
        if (!tempFile.renameTo(inputFile))
            System.out.println("Could not rename temp file");
    }




    /**
     * Builds a TodoList from a .txt file
     * @author Taylor Merwin
     */

    public static TodoList loadTodoFromTXT(String filePath) throws FileNotFoundException {
        Scanner s;
        String parentFilePath;
        try {
            s = new Scanner(new File(filePath));
        }
        catch (FileNotFoundException e) {
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
