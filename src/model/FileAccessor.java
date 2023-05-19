package model;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * This class contains methods for reading from txt file and writing to both txt and png file.
 *
 * @author Jiameng Li
 * @version v0.1
 */
public class FileAccessor {

    /**
     * Read text in the txt file in the given path.
     * <p>
     *     Precondition: The txt file in the given path exists.
     * </p>
     *
     * @param thePath The path to the txt file to read.
     * @return The content in the txt file in given path.
     */
    public static String readTxtFile(final String thePath) {
        StringBuilder sb = new StringBuilder();
        if (thePath.endsWith(".txt")) {
            File file = new File(thePath);
            if (file.exists()) {
                // Open scanner
                Scanner scanner;
                try {
                    scanner = new Scanner(file);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
                // Read content
                while (scanner.hasNextLine()) {
                    sb.append(scanner.nextLine());
                    if (scanner.hasNextLine()) {
                        sb.append("\n");
                    }
                }
                scanner.close();
            }
        }
        return sb.toString();
    }

    /**
     * Write given content into the txt file in the given path.
     * <p>
     *     Precondition: The txt file in the given path exists.
     * </p>
     *
     * @param thePath The path to the txt file to be written.
     * @param theContent The content to write to the txt file.
     */
    public static void writeTxtFile(final String thePath, final String theContent) {
        if (thePath.endsWith(".txt")) {
            try {
                File file = new File(thePath);
                if (file.exists()) {
                    FileWriter writer = new FileWriter(file, false);
                    writer.write(theContent);
                    writer.close();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Write given image into the png file in the given path.
     * <p>
     *     Precondition: The png file in the given path exists.
     * </p>
     *
     * @param thePath The path to the png file to be written.
     * @param theImage The image to write to the png file.
     */
    public static void writePngFile(final String thePath, final ImageIcon theImage) {
        File file = new File(thePath);
        final Image img = theImage.getImage();
        final BufferedImage bi = new BufferedImage(img.getWidth(null),
                img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = bi.createGraphics();
        g.drawImage(img, 0, 0, null);
        g.dispose();
        try {
            ImageIO.write(bi, "png", file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
