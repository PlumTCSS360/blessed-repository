package view;

import model.Project;
import model.Subproject;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;

/**
 * The panel that displays the sketch/image.
 *
 * @author Jiameng Li
 * @version 0.3
 */
public class SketchPanel extends JPanel implements WorkPanel {

    /** The subproject the sketch belongs to. */
    private final Subproject mySubproject;

    /** The name of the image. */
    private final String myName;

    /** The label used to display image. */
    private final JLabel myImage;

    /**
     * Construct a panel to display sketch/image.
     *
     * @author Jiameng Li
     * @param theSubproject The subproject the sketch belongs to.
     * @param theName The name of the sketch.
     */
    public SketchPanel(final Subproject theSubproject, final String theName) {
        super(new BorderLayout());
        mySubproject = theSubproject;
        myName = theName;
        myImage = new JLabel();
        setup();
    }

    /**
     * Set up the panel.
     *
     * @author Jiameng Li
     */
    private void setup() {
        // Set background color
        setBackground(BACKGROUND_COLOR);
        setForeground(FOREGROUND_COLOR);

        // Add a title
        final JLabel title = new JLabel(mySubproject.getName() + " - " + myName);
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));
        title.setFont(HEADING_FONT);
        title.setBackground(BACKGROUND_COLOR);
        title.setForeground(FOREGROUND_COLOR);
        add(title, BorderLayout.NORTH);

        // Add image to the panel
        myImage.setHorizontalAlignment(JLabel.CENTER);
        setNewImage();
        add(myImage, BorderLayout.CENTER);

        // Add "Edit" button to the panel
        final JButton edit = new JButton("Edit");
        edit.addActionListener(e -> editAction());
        add(edit, BorderLayout.SOUTH);
    }

    /**
     * Called when the "Edit" button is click.
     * Open a file chooser that allows the user to select a image file.
     *
     * @author Jiameng Li
     */
    private void editAction() {
        final JFileChooser fc = new JFileChooser("Choose an image file");
        final int approve = fc.showDialog(null, "Select");
        if (approve == JFileChooser.APPROVE_OPTION) {
            final String path = fc.getSelectedFile().getPath();
            mySubproject.setSketchContent(myName, path);
            removeAll();
            setup();
            revalidate();
            repaint();
        }
    }

    /**
     * Get the image from the subproject by name and display it.
     *
     * @author Jiameng Li
     */
    private void setNewImage() {
        final ImageIcon imageIcon = mySubproject.getSketch(myName);
        final int maxHeight = Toolkit.getDefaultToolkit().getScreenSize().height - 220;
        Image image;
        if (imageIcon.getIconHeight() < maxHeight) {
            final double scale = maxHeight / imageIcon.getIconHeight();
            image = imageIcon.getImage().getScaledInstance((int) (imageIcon.getIconWidth() * scale),
                    (int) (imageIcon.getIconHeight() * scale), Image.SCALE_SMOOTH);
        } else {
            final double scale = imageIcon.getIconHeight() / maxHeight;
            image = imageIcon.getImage().getScaledInstance((int) (imageIcon.getIconWidth() / scale),
                    (int) (imageIcon.getIconHeight() / scale), Image.SCALE_SMOOTH);
        }
        myImage.setIcon(new ImageIcon(image));
    }

}
