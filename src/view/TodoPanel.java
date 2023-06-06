package view;

import model.ListItem;
import model.Project;
import model.TodoList;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.io.FileNotFoundException;
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
    private JButton addButton;
    private JButton removeButton;
    private JButton markAsDoneButton;
    private JLabel nameLabel;



    private static final double screenHeight = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
    int fontSize = (int) (screenHeight / 50);

    public TodoPanel(TodoList todoList) {
        this.todoList = todoList;

        setLayout(new GridLayout(3, 1));

        setupTitle();
        setupListPanel();
        setupInputPanel();
        setupButtons();
    }

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

        for (ListItem item : todoList.getListItems()) {
            listModel.addElement(item);
        }

        add(listPanel);
        listPanel.add(list, BorderLayout.CENTER);

    }

    private void setupInputPanel() {
        // Set up the text fields and buttons
        nameField = new JTextField(20);

        addButton = new JButton("Add");
        removeButton = new JButton("Remove");
        markAsDoneButton = new JButton("Mark as done");
        nameLabel = new JLabel("Name:");

        nameLabel.setFont(new Font("Arial", Font.PLAIN, fontSize));

        nameLabel.setForeground(FOREGROUND_COLOR);



        // Set up the panel for the textfield and buttons
        JPanel inputPanel = new JPanel();
        inputPanel.setAlignmentX(CENTER_ALIGNMENT);
        inputPanel.setBackground(BACKGROUND_COLOR);
        inputPanel.add(nameLabel);
        inputPanel.add(nameField);
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
               ListItem newItem = new ListItem(name);
                listModel.addElement(new ListItem(name));
                todoList.getListItems().add(new ListItem(name));
                todoList.writeToTXT();

                nameField.setText("");


                // For testing
                for (ListItem item : todoList.getListItems()) {
                    System.out.println(item.toString() + "\n");
                }
            }
        });


        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                todoList.removeFromFile(list.getSelectedValue().toString());
                listModel.removeElement(list.getSelectedValue());

                //remove the selected item from the todo.txt file


                todoList.getListItems().remove(list.getSelectedIndex());
               // todoList.writeToTXT();



                // For testing
                for (ListItem item : todoList.getListItems()) {
                    System.out.println(item.toString() + "\n");
                }
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
