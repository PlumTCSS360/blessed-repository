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

//TODO: Make the font size dynamic


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
        setLocationRelativeTo(null); // Center the frame

        //Create center panel and add it to the frame
        JPanel centerPanel = new JPanel();
        add(centerPanel, BorderLayout.CENTER);
        //Create south panel and add it to the frame
        JPanel southPanel = new JPanel();
        add(southPanel, BorderLayout.SOUTH);

        //Create components
        JLabel welcomeLabel = new JLabel("Welcome to Crafty Companion!", SwingConstants.CENTER);
        JButton newButton = new JButton("New");
        JButton loadButton = new JButton("Load");
        JButton importButton = new JButton("Import");
        JButton exportButton = new JButton("Export");

        Font buttonFont = new Font("Arial", Font.PLAIN, (int) (frameSize.getHeight() / 30));
        Font labelFont = new Font("Arial", Font.BOLD, (int) (frameSize.getHeight() / 20));

        //Set fonts
        welcomeLabel.setFont(labelFont);
        newButton.setFont(buttonFont);
        loadButton.setFont(buttonFont);
        importButton.setFont(buttonFont);
        exportButton.setFont(buttonFont);

        //Set colors
        centerPanel.setBackground(BACKGROUND_COLOR);
        centerPanel.setForeground(FOREGROUND_COLOR);
        southPanel.setBackground(BACKGROUND_COLOR);
        southPanel.setForeground(FOREGROUND_COLOR);
        welcomeLabel.setForeground(FOREGROUND_COLOR);
        newButton.setBackground(FOREGROUND_COLOR);
        newButton.setForeground(BACKGROUND_COLOR);
        loadButton.setBackground(FOREGROUND_COLOR);
        loadButton.setForeground(BACKGROUND_COLOR);
        importButton.setBackground(FOREGROUND_COLOR);
        importButton.setForeground(BACKGROUND_COLOR);
        exportButton.setBackground(FOREGROUND_COLOR);
        exportButton.setForeground(BACKGROUND_COLOR);

        // Add components to panels

        // Add welcome label with dynamic font size
        centerPanel.add(welcomeLabel, BorderLayout.CENTER);
        // Add New button with dynamic resizing
        southPanel.add(newButton);
        // Add Load button with dynamic resizing
        southPanel.add(loadButton);
        // Add Import button with dynamic resizing
        southPanel.add(importButton);
        // Add Export button with dynamic resizing
        southPanel.add(exportButton);

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
                new LoadFrame();
                dispose();
            }
        });


        importButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String destinationDir = "data/";


                //open a dialog box to choose whether to import a project folder or user info file
                String[] options = {"Project Folder", "User Info File"};
                int choice = JOptionPane.showOptionDialog(null, "What would you like to import?",
                        "Import", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

                //User chooses to import a project folder
                if (choice == JOptionPane.YES_OPTION) {
                    JFileChooser myFolderChooser = new JFileChooser();
                    myFolderChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

                    final int option = myFolderChooser.showOpenDialog(null);
                    if (option == JFileChooser.APPROVE_OPTION) {
                        File selectedDir = myFolderChooser.getSelectedFile();
                        File newDir = new File(destinationDir + selectedDir.getName());
                        newDir.mkdir();

                        //loop through selectedDir and copy all files to newDir
                        File[] files = selectedDir.listFiles();
                        for (File file : files) {
                            if (file.isFile()) {
                                try {
                                    FileReader in = new FileReader(file);
                                    FileWriter out = new FileWriter(newDir + "/" + file.getName());
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
                    }
                }

                //Else if chooses to import a user info file
                else if (choice == JOptionPane.NO_OPTION) {
                    System.out.println("Clicked!");
                    JFileChooser myUserChooser = new JFileChooser();
                    myUserChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                    final int option = myUserChooser.showOpenDialog(null);
                    if (option == JFileChooser.APPROVE_OPTION) {
                        File selectedFile = myUserChooser.getSelectedFile();
                        File newFile = new File(destinationDir + selectedFile.getName());
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



            }
        });

        //export button lets the user choose a filer from the data folder to export
        //Then lets the user choose where to export it to
//        exportButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                String sourceDir = "data/";
//
//
//            }
//        });

        // Make the frame visible
        setVisible(true);
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
        String zipPath = destination + "\\" + name;
        try {
            FileOutputStream fos = new FileOutputStream(zipPath + ".zip");
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
