package model;

import java.awt.*;

/**
 * This class is used to make GridBagConstraints a little more efficient. This will allow the most common fields to be
 * set using a single constructor.
 * @author Devin Peevy
 */
public final class GBC extends GridBagConstraints {

    // CONSTRUCTORS

    /**
     * This constructor creates a new GBC. The default gridwidth and gridheight are 1.
     * @param gridx The x coordinate of your component in the grid.
     * @param gridy The y coordinate of your component in the grid.
     */
    public GBC(int gridx, int gridy) {
        super();
        this.gridx = gridx;
        this.gridy = gridy;
    }

    /**
     * This constructor creates a new GBC with a custom width and height.
     * @param gridx The leftmost x coordinate of your component in the grid.
     * @param gridy The topmost y coordinate of your component in the grid.
     * @param gridwidth The width of your component, in cells of the grid.
     * @param gridheight The height of your component, in cells of the grid.
     */
    public GBC(int gridx, int gridy, int gridwidth, int gridheight) {
        super();
        this.gridx = gridx;
        this.gridy = gridy;
        this.gridwidth = gridwidth;
        this.gridheight = gridheight;
    }
}