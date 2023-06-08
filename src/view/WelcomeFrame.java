package view;

import model.FileAccessor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * This class will create a new WelcomeFrame, the initial GUI.
 * @author Taylor Merwin
 */
public class WelcomeFrame extends JFrame  implements GUIFrame {

    /**
     * This constructor will create a new WelcomeFrame.
     * @author Taylor Merwin
     */
    public WelcomeFrame() {

        final double screenWidth = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        final double screenHeight = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        final Dimension frameSize = new Dimension((int) screenWidth / 2, (int) screenHeight / 2);
        // Set frame properties
        setTitle("Crafty Companion");
        setSize(frameSize);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        //Create center panel and add it to the frame
        JPanel centerPanel = new JPanel(new GridBagLayout());
        getContentPane().add(centerPanel);
        //Create south panel and add it to the frame
        JPanel southPanel = new JPanel(new GridLayout(1, 5));
        add(southPanel, BorderLayout.SOUTH);
        //Create components
        //welcome label should be centered in the center panel and span the entire width of the frame
        JLabel welcomeLabel = new JLabel("Welcome to Crafty Companion!");
        JButton newButton = new JButton("New");
        JButton loadButton = new JButton("Load");
        JButton importButton = new JButton("Import");
        JButton exportButton = new JButton("Export");
        JButton aboutButton = new JButton("About");
        //buttons will be placed in JButton array
        JButton[] buttons = {newButton, loadButton, importButton, exportButton, aboutButton};

        Font buttonFont = new Font("Arial", Font.PLAIN, (int) (frameSize.getHeight() / 30));
        Font labelFont = new Font("Arial", Font.BOLD, (int) (frameSize.getHeight() / 12));
        //Set label fonts
        welcomeLabel.setFont(labelFont);
        //Set button fonts
        for (JButton button : buttons) {
            button.setFont(buttonFont);
        }
        //Set colors
        centerPanel.setBackground(BACKGROUND_COLOR);
        centerPanel.setForeground(FOREGROUND_COLOR);
        southPanel.setBackground(BACKGROUND_COLOR);
        southPanel.setForeground(FOREGROUND_COLOR);
        welcomeLabel.setForeground(FOREGROUND_COLOR);

        //set button colors
        for (JButton button : buttons) {
            button.setBackground(FOREGROUND_COLOR);
            button.setForeground(BACKGROUND_COLOR);
        }
        // Add welcome label with dynamic font size
        centerPanel.add(welcomeLabel);
        //Add each button to the south panel
        for (JButton button : buttons) {
            southPanel.add(button);
        }

        // Add event listeners to buttons
        newButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new NewFrame();
                dispose();
            }
        });

        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new LoadFrame();
            }
        });

        importButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //open a dialog box to choose whether to import a project folder or user info file
                String[] options = {"Project Folder", "User Info File"};
                int choice = JOptionPane.showOptionDialog(null, "What would you like to import?",
                        "Import", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

                //User chooses to import a project folder
                if (choice == JOptionPane.YES_OPTION) {
                    importProject();
                }

                //Else if chooses to import a user info file
                else if (choice == JOptionPane.NO_OPTION) {
                    importUserInfo();
                }
            }
        });

        exportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //open a dialog box to choose whether to export a project folder or user info file
                String[] options = {"Project Folder", "User Info File"};
                int choice = JOptionPane.showOptionDialog(null, "What would you like to export?",
                        "Export", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

                //User chooses to export a project folder
                if (choice == JOptionPane.YES_OPTION) {
                    exportProject();
                }

                //Else if chooses to export a user info file
                else if (choice == JOptionPane.NO_OPTION) {
                    exportUserInfo();
                }
            }
        });

        aboutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AboutFrame();
            }
        });

        // Make the frame visible
        setVisible(true);
    }

    public static void importUserInfo(){
        JFileChooser myUserChooser = new JFileChooser();
        myUserChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        final int option = myUserChooser.showOpenDialog(null);
        if (option == JFileChooser.APPROVE_OPTION) {
            File selectedFile = myUserChooser.getSelectedFile();
            File newFile = new File("data/" + selectedFile.getName());
            try {
                FileReader in = new FileReader(selectedFile);
                FileWriter out = new FileWriter(newFile);
                int c;
                while ((c = in.read()) != -1) {
                    out.write(c);

                }
                in.close();
                out.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

    }

    /**
     * Allows the user to choose a folder that contains a project to import.
     *
     * @author Taylor Merwin
     * @author Jiameng Li
     */
    public static void importProject() {
        // Display a file chooser
        JFileChooser myFolderChooser = new JFileChooser();
        myFolderChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        final int option = myFolderChooser.showOpenDialog(null);
        if (option == JFileChooser.APPROVE_OPTION) {
            File selectedDir = myFolderChooser.getSelectedFile();
            // Check if the selected folder is a valid project to import
            if (isValidProject(selectedDir.getPath())) {
                // Create a folder in database for the imported project
                File newDir = new File("data/" + selectedDir.getName());
                newDir.mkdir();
                importProjectHelper(selectedDir, newDir);
            } else {
                // Invalid project folder selected
                JOptionPane.showMessageDialog(null,
                        "The folder you selected is not a valid project", "Invalid Project",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * A helper method that recursively read the data in a project folder to import that project.
     * Copy the contents of the selected directory to the new directory.
     *
     * @author Taylor Merwin
     * @author Jiameng Li
     * @param theSelected The file or folder to be imported.
     * @param theDest The destination where the imported file or folder will be stored.
     */
    public static void importProjectHelper(final File theSelected, final File theDest) {
        try {
            // Goes through each folder and file in the selected folder.
            File[] files = theSelected.listFiles();
            for (File file : Objects.requireNonNull(files)) {
                // If the file is a directory, create a new folder in destination
                // and call this helper method again
                if (file.isDirectory()) {
                    File newSubDir = new File(theDest.getAbsolutePath() + "/" + file.getName());
                    newSubDir.mkdir();
                    importProjectHelper(file, newSubDir);
                } else {
                    // If it's a normal file, copy the file to the destination
                    File newFile = new File(theDest.getAbsolutePath() + "/" + file.getName());
                    newFile.createNewFile();
                    FileInputStream in = new FileInputStream(file);
                    FileOutputStream out = new FileOutputStream(newFile);
                    int c;
                    while ((c = in.read()) != -1) {
                        out.write(c);
                    }
                    in.close();
                    out.close();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Allows the user to select a location to export the user info as txt file.
     *
     * @author Jiameng Li
     */
    public static void exportUserInfo() {
        // Get content from the user info file
        final String s = FileAccessor.readTxtFile("data/user_info.txt");
        JFileChooser fc = new JFileChooser();
        // Get a destination to export user info file
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int opt = fc.showDialog(null, "Export");
        if (opt == JFileChooser.APPROVE_OPTION) {
            final String path = String.format("%s\\user_info.txt", fc.getSelectedFile().getPath());
            File file = new File(path);
            // Create file to write user info
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            FileAccessor.writeTxtFile(path, s);
        }
    }

    /**
     * Opens a file chooser that allows the user to select a project to export,
     * then opens another file chooser that allows the user to select a location
     * to export the selected project.
     * If the user selected a project and a location, the project will be exported
     * to the location as a zip file.
     *
     * @author Jiameng Li
     */
    public static void exportProject() {
        // Select a project to export
        String name;
        String path;
        JFileChooser fc = new JFileChooser("data");
        fc.setDialogTitle("Choose a project");
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int projectSelected = fc.showDialog(null, "Select");
        if (projectSelected == JFileChooser.APPROVE_OPTION) {
            path = fc.getSelectedFile().getAbsolutePath();
            name = fc.getSelectedFile().getName();
        } else {
            return;
        }

        // Select the destination to export the project
        String destination;
        fc = new JFileChooser();
        fc.setDialogTitle("Choose a location to export project");
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        projectSelected = fc.showDialog(null, "Export");
        if (projectSelected == JFileChooser.APPROVE_OPTION) {
            destination= fc.getSelectedFile().getAbsolutePath();
        } else {
            return;
        }

        // Create zip file
        try {
            FileOutputStream fos = new FileOutputStream(destination + "\\" + name + ".zip");
            ZipOutputStream zos = new ZipOutputStream(fos);
            zipProject(zos, new File(path), null);
            zos.flush();
            fos.flush();
            zos.close();
            fos.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * A helper method that zip the project.
     *
     * @author Jiameng Li
     * @param theZos Output stream that write to the zip file.
     * @param theFile The file that contains the data to write to the zip file.
     * @param theParent The parent directory.
     */
    private static void zipProject(final ZipOutputStream theZos, final File theFile, final String theParent) {
        String name = theFile.getName();
        // Set file path
        if (theParent != null && !theParent.isEmpty()) {
            name = theParent + "\\" + name;
        }
        if (theFile.isDirectory()) {
            for (File subfile : Objects.requireNonNull(theFile.listFiles())) {
                zipProject(theZos, subfile, name);
            }
        } else {
            try {
                // Write data to the files in the zip
                FileInputStream fis = new FileInputStream(theFile);
                theZos.putNextEntry(new ZipEntry(name));
                byte[] data = new byte[1024];
                int length = fis.read(data);
                while (length > -1) {
                    theZos.write(data, 0, length);
                    length = fis.read(data);
                }
                theZos.closeEntry();
                fis.close();
            } catch (Exception e) {
                throw new RuntimeException();
            }
        }
    }

    /**
     * Check if the folder in the given path is a valid project to import.
     * <p>
     *     A valid project should contains budget.txt and desc.txt files.
     *     It's subprojects should also contains these two files as well as Notes, Sketches, and Options folders.
     *     Options folder should only contains other folder that store information about each option.
     *     In each option folder, there should be five txt files: desc.txt, cost.txt, contractor.txt, warranty.txt,
     *     and website.txt.
     * </p>
     *
     * @author Jiameng Li
     * @param thePath The path to the project folder.
     * @return The folder in the given path is a valid project to import.
     */
    private static boolean isValidProject(final String thePath) {
        boolean isValid = true;
        // Name of data files for budget and description
        final String[] filesNeeded = {"/budget.txt", "/desc.txt"};
        // Check if the project contains data files for budget and description
        for (int i = 0; i < filesNeeded.length && isValid; i++) {
            File file = new File(thePath + filesNeeded[i]);
            isValid = file.exists() && file.length() > 0;
        }
        // Check whether each subproject contains necessary folders and files
        if (isValid) {
            File file = new File(thePath);
            for (File subproject : Objects.requireNonNull(file.listFiles(File::isDirectory))) {
                String path = subproject.getPath();
                // Check if the subproject contains data files for budget and description
                for (int i = 0; i < filesNeeded.length && isValid; i++) {
                    file = new File(path + filesNeeded[i]);
                    isValid = file.exists() && file.length() > 0;
                }
                // Check if the subproject contains "Notes" folder that only contains text files
                file = new File(path + "/Notes");
                if (isValid && file.exists()) {
                    File[] invalidFiles = file.listFiles((dir, name) -> !name.endsWith(".txt"));
                    isValid = Objects.requireNonNull(invalidFiles).length == 0;
                }
                // Check if the subproject contains "Sketches" folder that only contains png files
                file = new File(path + "/Sketches");
                if (isValid && file.exists()) {
                    File[] invalidFiles = file.listFiles((dir, name) -> !name.endsWith(".png"));
                    isValid = Objects.requireNonNull(invalidFiles).length == 0;
                }
                // Check if the subproject contains "Options" folder
                file = new File(path + "/Options");
                if (isValid && file.exists()) {
                    final String[] necessaryFiles = {"/desc.txt", "/cost.txt", "/contractor.txt", "/warranty.txt",
                            "/website.txt"};
                    File[] options = file.listFiles();      // All options in the Options folder
                    // Check if each option contains necessary files for the option
                    for (File op : Objects.requireNonNull(options)) {
                        path = op.getPath();
                        for (int i = 0; i < necessaryFiles.length && isValid; i++) {
                            file = new File(path + necessaryFiles[i]);
                            isValid = file.exists();
                        }
                    }
                }
            }
        }
        return isValid;
    }

}
