package view;

import model.About;
import model.Person;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Display's the user's information and system information on the screen.
 * @author Taylor Merwin, Devin Peevy
 * @version 0.3
 */
public final class AboutFrame {

   private final About about;
    private final JFrame frame;
    private final CardLayout cardLayout;
    private final JPanel cardPanel;
    private JPanel userPanel;
    private JPanel devPanel;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy");
    private final LocalDate today = LocalDate.now();
    private final String dateString = today.format(formatter);

    /**
     * This constructor creates a new instance of an AboutFrame.
     * @author Taylor Merwin
     */
    public AboutFrame() {
        about = new About();
        frame = new JFrame("Crafty Companion - About");
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        setUpFrame();
        createCardPanels();
    }

    /**
     * Sets up the JFrame to hold all the panels.
     * @author Taylor Merwin
     */
    private void setUpFrame() {
        frame.add(cardPanel, BorderLayout.CENTER);
        frame.setSize(getFrameSize());
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    /**
     * Creates the Card Panels to switch between user and developer info
     * @author Taylor Merwin
     */
    private void createCardPanels() {

        cardPanel.setLayout(cardLayout);
        userPanel = new JPanel(new GridLayout(6, 1));
        devPanel = new JPanel();
        JPanel[] panelArray = new JPanel[2];
        panelArray[0] = userPanel;
        panelArray[1] = devPanel;
        createUserPanel();
        createDevPanel();
        for (int i = 0; i < panelArray.length; i++) {
            cardPanel.add(panelArray[i], Integer.toString(i));
        }
        //Show the User Panel
        cardLayout.show(cardPanel, "0");
    }

    /**
     * Creates the UserPanel to display the user's information.
     * @author Taylor Merwin
     */
    private void createUserPanel() {

        userPanel.setBackground(Color.DARK_GRAY);
        userPanel.setForeground(Color.LIGHT_GRAY);
        //User labels
        JLabel userLabel = new JLabel("User:");
        JLabel nameLabel = new JLabel(getName());
        JLabel emailLabel = new JLabel(getEmail());
        String version = "Version: 0.2";
        JLabel versionLabel = new JLabel(version);
        JLabel dateLabel = new JLabel(dateString);
        // Set the font and color of the labels.
        JLabel[] userLabels = {userLabel, nameLabel, emailLabel, versionLabel, dateLabel};
        for (JLabel label : userLabels) {
           // label.setFont(new Font("Arial", Font.BOLD, (int) screenHeight/ 30));
            label.setForeground(Color.WHITE);
            label.setHorizontalAlignment(JLabel.CENTER);
            label.setFont(new Font("Arial", Font.BOLD, getFrameSize().height / 15));
            userPanel.add(label);
        }
        // Add button to switch panels
        JButton devButton = new JButton("Developers");
        devButton.addActionListener(e -> cardLayout.show(cardPanel, "1"));
        devButton.setBackground(Color.LIGHT_GRAY);
        devButton.setForeground(Color.DARK_GRAY);
        devButton.setFont(new Font("Arial", Font.BOLD, getFrameSize().height / 15));
        userPanel.add(devButton);
    }

    /**
     * Sets up the Developer Panel to display creator's information.
     * @author Taylor Merwin
     */
    private void createDevPanel() {

        devPanel.setBackground(Color.DARK_GRAY);
        devPanel.setForeground(Color.LIGHT_GRAY);
        JList<Person> devList = new JList<Person>(about.getDevelopers());
        devList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        devList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value,
                                                          int index, boolean isSelected,
                                                          boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index,
                        isSelected, cellHasFocus);
                Person person = (Person) value;
                label.setText(person.getName() + " " + person.getEmail());
                label.setFont(new Font("Arial", Font.BOLD, getFrameSize().height / 15));
                label.setForeground(Color.WHITE);
                label.setBackground(Color.DARK_GRAY);
                label.setHorizontalAlignment(JLabel.CENTER);
                //increase space between cells
                list.setFixedCellHeight(50);
                return label;
            }
        });
        //Add JList to the panel
        devPanel.add(devList, BorderLayout.CENTER);
        // Add button to switch panels
        JButton userButton = new JButton("User Info");
        userButton.addActionListener(e -> cardLayout.show(cardPanel, "0"));
        userButton.setBackground(Color.LIGHT_GRAY);
        userButton.setForeground(Color.DARK_GRAY);
        userButton.setFont(new Font("Arial", Font.BOLD, getFrameSize().height / 15));
        devPanel.add(userButton, BorderLayout.SOUTH);
    }

    /**
     * @return the name of the user.
     * @author Taylor Merwin
     */
    private String getName() {
        return about.getUser().getName();
    }

    /**
     * @return the email of the user.
     * @author Taylor Merwin
     */
    private String getEmail() {
        return about.getUser().getEmail();
    }

    /**
     * Returns the chosen frame size based on the screen size.
     * To be used for consistency in font sizing.
     * @author Taylor Merwin
     */
    private Dimension getFrameSize() {

        final double screenWidth = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        final double screenHeight = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        return new Dimension((int) screenWidth / 3, (int) screenHeight / 3);

    }
}