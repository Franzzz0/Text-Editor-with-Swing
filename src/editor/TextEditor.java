package editor;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TextEditor extends JFrame {

    private JButton saveButton;
    private JButton loadButton;
    private FileManager fileManager;

    public TextEditor() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 300);
        setTitle("Text Editor");
        initializeComponents();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initializeComponents() {
        JTextArea textArea = new JTextArea();
        textArea.setName("TextArea");
        JTextField filenameField = new JTextField(10);
        filenameField.setName("FilenameField");
        JLabel resultLabel = new JLabel(" ");
        resultLabel.setBorder(new EmptyBorder(new Insets(0,15,5,0)));

        MouseAdapter resetLabel = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                resultLabel.setText(" ");
            }
        };

        textArea.addMouseListener(resetLabel);
        filenameField.addMouseListener(resetLabel);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setName("ScrollPane");
        scrollPane.setBorder(new EmptyBorder(new Insets(0, 10, 10, 10)));
        fileManager = new FileManager(filenameField, textArea, resultLabel);
        initializeButtons();

        JPanel topPanel = new JPanel();
        topPanel.add(filenameField);
        topPanel.add(saveButton);
        topPanel.add(loadButton);
        topPanel.setBorder(new EmptyBorder(new Insets(10, 10, 5, 10)));

        setJMenuBar(getMenu());
        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(resultLabel, BorderLayout.SOUTH);
    }

    private JMenuBar getMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menuFile = new JMenu("File");
        menuFile.setName("MenuFile");
        JMenuItem menuLoad = new JMenuItem("Load");
        menuLoad.setName("MenuLoad");
        JMenuItem menuSave = new JMenuItem("Save");
        menuSave.setName("MenuSave");
        JMenuItem menuExit = new JMenuItem("Exit");
        menuExit.setName("MenuExit");

        menuLoad.addActionListener(a -> fileManager.read());
        menuSave.addActionListener(a -> fileManager.write());
        menuExit.addActionListener(a -> this.dispose());

        menuFile.add(menuLoad);
        menuFile.add(menuSave);
        menuFile.add(menuExit);
        menuBar.add(menuFile);
        return menuBar;
    }

    private void initializeButtons() {
        saveButton = new JButton("save");
        saveButton.setName("SaveButton");
        saveButton.addActionListener(e -> fileManager.write());
        loadButton = new JButton("load");
        loadButton.setName("LoadButton");
        loadButton.addActionListener(e -> fileManager.read());
    }
}
