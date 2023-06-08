package view;

import model.FileAccessor;
import model.Project;
import model.Subproject;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

/**
 * A JPanel that displays notes for a project.
 * @author Cameron Gregoire
 */
public class NotesPanel extends JPanel {
    private final JTextArea notesTextArea;
    private final String filePath;

    /**
     * Constructs a NotesPanel object.
     * @author Cameron Gregoire
     */
    public NotesPanel(Subproject subProject, String noteName) {
        setLayout(new BorderLayout());

        // Set the file path
        if (subProject != null) {
            filePath = "data/" + Project.getProjectName() + "/" + subProject.getName() + "/Notes" + "/" + noteName + ".txt";
        } else {
            filePath = "data/" + Project.getProjectName() + "/Notes.txt";
        }

        // Create a label for project name and notes
        JLabel titleLabel = new JLabel(noteName + " Notes:");
        titleLabel.setForeground(Color.LIGHT_GRAY);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(titleLabel, BorderLayout.NORTH);

        int margin = 5;

        // Create the notes text area
        notesTextArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(notesTextArea);
        add(scrollPane, BorderLayout.CENTER);
        notesTextArea.setLineWrap(true);
        notesTextArea.setWrapStyleWord(true);

        // Set the background color of the panel and text area to dark gray
        setBackground(Color.DARK_GRAY);
        setForeground(Color.LIGHT_GRAY);
        notesTextArea.setBackground(Color.DARK_GRAY);
        notesTextArea.setForeground(Color.LIGHT_GRAY);
        notesTextArea.setCaretColor(Color.WHITE);
        Font font = new Font("Arial", Font.PLAIN, 16);
        notesTextArea.setFont(font);

        notesTextArea.setBorder(BorderFactory.createEmptyBorder(margin, margin, margin, margin));

        // Attach a document listener to the notes text area to detect changes
        notesTextArea.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                // Save the notes when text is inserted
                saveNotes();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                // Save the notes when text is removed
                saveNotes();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                // Save the notes when text is changed (e.g., style change)
                saveNotes();
            }
        });

        // Load the notes from file if it exists
        String notesText = FileAccessor.readTxtFile(filePath);
        notesTextArea.setText(notesText);
    }

    /**
     * Saves the notes to a file.
     * @author Cameron Gregoire
     */
    private void saveNotes() {
        String notesText = notesTextArea.getText();
        FileAccessor.writeTxtFile(filePath, notesText);
    }

}