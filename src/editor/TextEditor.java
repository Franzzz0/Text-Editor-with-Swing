package editor;

import javax.swing.*;
import java.awt.*;

public class TextEditor extends JFrame {

    public TextEditor() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(380, 300);
        setTitle("Text Editor");
        initializeComponents();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initializeComponents() {
        ComponentCreator creator = new ComponentCreator();
        JTextArea textArea = creator.createTextArea();
        JTextField searchField = creator.createSearchField();
        JLabel resultLabel = creator.createResultLabel();

        creator.addResetLabelMouseAdapter(resultLabel, textArea);
        creator.addResetLabelMouseAdapter(resultLabel, searchField);

        JScrollPane scrollPane = creator.createScrollPane(textArea);

        JFileChooser fileChooser = creator.createFileChooser();
        FileManager fileManager = new FileManager(textArea, resultLabel, fileChooser);
        SearchUtility searcher = new SearchUtility(textArea, searchField);

        JPanel topPanel = creator.createTopPanel(fileManager, searcher, searchField);

        setJMenuBar(creator.createMenuBar(this, fileManager, searcher));
        add(fileChooser);
        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(resultLabel, BorderLayout.SOUTH);
    }
}
