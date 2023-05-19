package view;

import model.FileAccessor;

import javax.swing.*;
import javax.swing.plaf.metal.MetalButtonUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * This class will create a new WelcomeFrame, the initial GUI.
 */

//TODO: Make the font size dynamic


public class WelcomeFrame extends JFrame {

    /**
     * This constructor will create a new WelcomeFrame.
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

        // Create components
        JLabel welcomeLabel = new JLabel("Welcome to Crafty Companion!", SwingConstants.CENTER);
        JButton newButton = new JButton("New");
        JButton loadButton = new JButton("Load");

        Font buttonFont = new Font("Segue", Font.PLAIN, (int) (frameSize.getHeight() / 30));
        Font labelFont = new Font("Segue", Font.PLAIN, (int) (frameSize.getHeight() / 20));

        welcomeLabel.setFont(labelFont);
        newButton.setFont(buttonFont);
        loadButton.setFont(buttonFont);

        // Set layout and add components
        setLayout(new GridBagLayout());

        // Create constraints for components
        GridBagConstraints gbc = new GridBagConstraints();

        //Add welcome label with dynamic font size
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 0.5;
        gbc.insets = new Insets(10, 10, 10, 10);
        add(welcomeLabel, gbc);

        // Add New button with dynamic resizing
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        //makes the buttons fill their areas
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 0.5;
        gbc.weighty = 0.5;
        gbc.anchor = GridBagConstraints.SOUTHWEST;
        add(newButton, gbc);

        // Add Load button with dynamic resizing
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 0.5;
        gbc.weighty = 0.5;
        gbc.anchor = GridBagConstraints.SOUTHEAST;
        add(loadButton, gbc);

        //add event listeners to buttons
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
