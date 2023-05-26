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

    public static void importProject() {
        JFileChooser myFolderChooser = new JFileChooser();
        myFolderChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        final int option = myFolderChooser.showOpenDialog(null);
        if (option == JFileChooser.APPROVE_OPTION) {
            File selectedDir = myFolderChooser.getSelectedFile();
            File newDir = new File("data/" + selectedDir.getName());
            newDir.mkdir();

            //copy the contents of the selected directory to the new directory
            try {
                File[] files = selectedDir.listFiles();
                for (File file : files) {
                    if (file.isDirectory()) {
                        File newSubDir = new File(newDir.getAbsolutePath() + "/" + file.getName());
                        newSubDir.mkdir();
                        File[] subFiles = file.listFiles();
                        for (File subFile : subFiles) {
                            File newSubFile = new File(newSubDir.getAbsolutePath() + "/" + subFile.getName());
                            newSubFile.createNewFile();
                            FileReader in = new FileReader(subFile);
                            FileWriter out = new FileWriter(newSubFile);
                            int c;
                            while ((c = in.read()) != -1) {
                                out.write(c);
                            }
                            in.close();
                            out.close();
                        }
                    } else {
                        File newFile = new File(newDir.getAbsolutePath() + "/" + file.getName());
                        newFile.createNewFile();
                        FileReader in = new FileReader(file);
                        FileWriter out = new FileWriter(newFile);
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
    }

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


}
