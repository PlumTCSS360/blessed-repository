package view;

import model.ListItem;
import model.TodoList;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.util.Collections;

/**
 * Displays a todo list
 * @author Taylor Merwin
 */
public class TodoPanel extends JPanel implements WorkPanel{

    TodoList todoList;

    JPanel titlePanel;
    JPanel listPanel;
    JPanel inputPanel;

    private DefaultListModel<ListItem> listModel;
    private JList<ListItem> list;
    private JTextField nameField;
    private JTextField priorityField;
    private JButton addButton;
    private JButton removeButton;
    private JButton markAsDoneButton;
    private JLabel nameLabel;
    private JLabel priorityLabel;

    private static final double screenWidth = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
    private static final double screenHeight = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
    int fontSize = (int) (screenHeight / 50);

    public TodoPanel(String projectName) {

        setLayout(new GridLayout(3, 1));

        setupTitle(projectName);
        setupListPanel();
        setupInputPanel();
        setupButtons();
    }

    private void setupTitle(String projectName) {
        setBackground(BACKGROUND_COLOR);
        setForeground(FOREGROUND_COLOR);

        titlePanel = new JPanel();
        titlePanel.setBackground(BACKGROUND_COLOR);
        titlePanel.setForeground(FOREGROUND_COLOR);
        JLabel titleLabel = new JLabel(projectName + " Todo List");
        titleLabel.setFont(TITLE_FONT);
        titleLabel.setForeground(FOREGROUND_COLOR);
        titleLabel.setAlignmentX(CENTER_ALIGNMENT);
        add(titlePanel);
        titlePanel.add(titleLabel, BorderLayout.NORTH);



    }

    private void setupListPanel() {
        listPanel = new JPanel();
        listPanel.setAlignmentX(CENTER_ALIGNMENT);
        listPanel.setBackground(BACKGROUND_COLOR);

        listModel = new DefaultListModel<>();
        list = new JList<>(listModel);
        list.setBackground(BACKGROUND_COLOR);
        list.setForeground(FOREGROUND_COLOR);
        //Set list items to appear on left side of list
        list.setAlignmentX(LEFT_ALIGNMENT);

        list.setFont(new Font("Arial", Font.PLAIN, fontSize));


        //JScrollPane scrollPane = new JScrollPane(list);
        add(listPanel);
        listPanel.add(list, BorderLayout.CENTER);

    }

    private void setupInputPanel() {
        // Set up the text fields and buttons
        nameField = new JTextField(20);
        priorityField = new JTextField(2);
        addButton = new JButton("Add");
        removeButton = new JButton("Remove");
        markAsDoneButton = new JButton("Mark as done");
        nameLabel = new JLabel("Name:");
        priorityLabel = new JLabel("Priority:");
        nameLabel.setFont(new Font("Arial", Font.PLAIN, fontSize));
        priorityLabel.setFont(new Font("Arial", Font.PLAIN, fontSize));
        nameLabel.setForeground(FOREGROUND_COLOR);
        priorityLabel.setForeground(FOREGROUND_COLOR);


        // Set up the panel for the textfield and buttons
        JPanel inputPanel = new JPanel();
        inputPanel.setAlignmentX(CENTER_ALIGNMENT);
        inputPanel.setBackground(BACKGROUND_COLOR);
        inputPanel.add(nameLabel);
        inputPanel.add(nameField);
        inputPanel.add(priorityLabel);
        inputPanel.add(priorityField);
        inputPanel.add(addButton);
        inputPanel.add(removeButton);
        inputPanel.add(markAsDoneButton);
        add(inputPanel);
    }

    private void setupButtons() {

        // Set up the button actions
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                Integer priority = Integer.parseInt(priorityField.getText());
                listModel.addElement(new ListItem(name, priority));
                Collections.sort(Collections.list(listModel.elements()));
                nameField.setText("");
                priorityField.setText("");
            }
        });


        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listModel.removeElement(list.getSelectedValue());
            }
        });


        markAsDoneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ListItem selectedValue = list.getSelectedValue();
                if (selectedValue != null) {
                    selectedValue.markAsDone();
                    list.repaint();
                }
            }
        });



    }
}
