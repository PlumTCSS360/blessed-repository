package view;

import model.Subproject;

import javax.swing.*;

public class SubprojectPanel extends JPanel implements WorkPanel {

    final Subproject mySubproject;



    public SubprojectPanel() {
        super();
        mySubproject = null;
    }

    public SubprojectPanel(final Subproject theSubproject) {
        super();
        mySubproject = theSubproject;
        setupPanel();
    }

    private void setupPanel() {
        setBackground(BACKGROUND_COLOR);
        setForeground(FOREGROUND_COLOR);

        JLabel title = new JLabel(mySubproject.getName());
        title.setFont(TITLE_FONT);
        title.setForeground(FOREGROUND_COLOR);

        add(title);
    }

}
