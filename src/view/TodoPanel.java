package view;

import model.ListItem;
import model.Project;
import model.TodoList;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;

/**
 * Displays a to-do list
 * Uses the TodoList class to generate the list
 *
 * @author Taylor Merwin
 */
public class TodoPanel extends JPanel implements WorkPanel {

    TodoList todoList;
    JPanel titlePanel;
    JPanel listPanel;
    JPanel inputPanel;

    private DefaultListModel<ListItem> listModel;
    private JList<ListItem> list;
    private JTextField nameField;
    private JButton addButton;
    private JButton removeButton;

    private static final double screenHeight = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
    int fontSize = (int) (screenHeight / 50);

    /**
     * Constructor for the to-do list panel
     *
     * @param todoList
     * @author Taylor Merwin
     */
    public TodoPanel(TodoList todoList) {
        this.todoList = todoList;
        setLayout(new GridLayout(3, 1));
        setupTitle();
        setupListPanel();
        setupInputPanel();
        setupButtons();
    }

    /**
     * Generates the title area for the to-do list panel
     *
     * @author Taylor Merwin
     */
    private void setupTitle() {
        setBackground(BACKGROUND_COLOR);
        setForeground(FOREGROUND_COLOR);
        titlePanel = new JPanel();
        titlePanel.setBackground(BACKGROUND_COLOR);
        titlePanel.setForeground(FOREGROUND_COLOR);
        JLabel titleLabel = new JLabel(Project.getProjectName() + " Todo List");
        titleLabel.setFont(TITLE_FONT);
        titleLabel.setForeground(FOREGROUND_COLOR);
        titleLabel.setAlignmentX(CENTER_ALIGNMENT);
        add(titlePanel);
        titlePanel.add(titleLabel, BorderLayout.NORTH);
    }

    /**
     * Generates the list for the to-do list
     *
     * @author Taylor Merwin
     */
    private void setupListPanel() {
        listPanel = new JPanel();
        listPanel.setAlignmentX(CENTER_ALIGNMENT);
        listPanel.setBackground(BACKGROUND_COLOR);
        listModel = new DefaultListModel<>();
        list = new JList<>(listModel);
        list.setBackground(BACKGROUND_COLOR);
        list.setForeground(FOREGROUND_COLOR);
        list.setFont(new Font("Arial", Font.PLAIN, fontSize));
        for (ListItem item : todoList.getListItems()) {
            listModel.addElement(item);
        }
        add(listPanel);
        listPanel.add(list, BorderLayout.CENTER);
    }

    /**
     * Generates the input panel for the to-do list panel
     *
     * @author Taylor Merwin
     */
    private void setupInputPanel() {
        nameField = new JTextField(20);
        addButton = new JButton("Add");
        removeButton = new JButton("Remove");
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setFont(new Font("Arial", Font.PLAIN, fontSize));
        nameLabel.setForeground(FOREGROUND_COLOR);
        inputPanel = new JPanel();
        inputPanel.setAlignmentX(CENTER_ALIGNMENT);
        inputPanel.setBackground(BACKGROUND_COLOR);
        inputPanel.add(nameLabel);
        inputPanel.add(nameField);
        inputPanel.add(addButton);
        inputPanel.add(removeButton);
        add(inputPanel);
    }


    /**
     * Generates the actions performed by the add and remove buttons.
     * Adds and removes items from the list and writes the changes to the todo.txt file
     * Updates both the display list and the internal to-do list
     *
     * @author Taylor Merwin
     */
    private void setupButtons() {

        addButton.addActionListener(e -> {
            String name = nameField.getText();
            ListItem newItem = new ListItem(name);
            listModel.addElement(new ListItem(name));
            todoList.getListItems().add(new ListItem(name));
            todoList.writeToTXT();
            nameField.setText("");
        });

        removeButton.addActionListener(e -> {
            listModel.removeElement(list.getSelectedValue());
            todoList.getListItems().clear();
            todoList.getListItems().addAll(Collections.list(listModel.elements()));
            todoList.writeToTXT();
        });
    }
}
