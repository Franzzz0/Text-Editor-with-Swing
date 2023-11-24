package editor;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.nio.channels.FileChannel;

public class ComponentCreator {

    private final Dimension buttonSize;
    private final JCheckBox checkBox;

    public ComponentCreator() {
        this.checkBox = new JCheckBox();
        buttonSize = new Dimension(20, 20);
    }

    public JTextArea createTextArea() {
        JTextArea textArea = new JTextArea();
        textArea.setName("TextArea");
        return textArea;
    }

    public JTextField createSearchField() {
        JTextField searchField = new JTextField(10);
        searchField.setName("SearchField");
        return searchField;
    }

    public JLabel createResultLabel() {
        JLabel resultLabel = new JLabel(" ");
        resultLabel.setBorder(new EmptyBorder(new Insets(0,15,5,0)));
        return resultLabel;
    }

    public void addResetLabelMouseAdapter(JLabel label, JComponent clickedComponent) {
        clickedComponent.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                label.setText(" ");
            }
        });
    }

    public JScrollPane createScrollPane(JTextArea textArea) {
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setName("ScrollPane");
        scrollPane.setBorder(new EmptyBorder(new Insets(0, 10, 10, 10)));
        return scrollPane;
    }

    public JPanel createTopPanel(FileManager fileManager, SearchUtility searcher, JTextField searchField) {
        return createTopPanel(
                createOpenButton(fileManager),
                createSaveButton(fileManager),
                searchField,
                createSearchButton(searcher),
                createPrevMatchButton(searcher),
                createNextMatchButton(searcher),
                createRegExCheckbox(searcher)
        );
    }

    public JMenuBar createMenuBar(JFrame parent, FileManager fileManager, SearchUtility searcher) {
        JMenuBar menuBar = new JMenuBar();
        JMenu menuFile = new JMenu("File");
        menuFile.setName("MenuFile");
        JMenuItem menuLoad = new JMenuItem("Load");
        menuLoad.setName("MenuOpen");
        JMenuItem menuSave = new JMenuItem("Save");
        menuSave.setName("MenuSave");
        JMenuItem menuExit = new JMenuItem("Exit");
        menuExit.setName("MenuExit");

        menuLoad.addActionListener(a -> fileManager.read());
        menuSave.addActionListener(a -> fileManager.write());
        menuExit.addActionListener(a -> parent.dispose());

        menuFile.add(menuLoad);
        menuFile.add(menuSave);
        menuFile.addSeparator();
        menuFile.add(menuExit);

        JMenu menuSearch = new JMenu("Search");
        menuSearch.setName("MenuSearch");
        JMenuItem menuStartSearch = new JMenuItem("Start search");
        JMenuItem menuPreviousMatch = new JMenuItem("Previous match");
        JMenuItem menuNextMatch = new JMenuItem("Next match");
        JMenuItem menuRegExp = new JMenuItem("Use regular expressions");
        menuStartSearch.setName("MenuStartSearch");
        menuPreviousMatch.setName("MenuPreviousMatch");
        menuNextMatch.setName("MenuNextMatch");
        menuRegExp.setName("MenuUseRegExp");

        menuStartSearch.addActionListener(a -> searcher.search());
        menuPreviousMatch.addActionListener(a -> searcher.previousMatch());
        menuNextMatch.addActionListener(a -> searcher.nextMatch());
        menuRegExp.addActionListener(a -> checkBox.setSelected(!checkBox.isSelected()));
        
        menuSearch.add(menuStartSearch);
        menuSearch.add(menuPreviousMatch);
        menuSearch.add(menuNextMatch);
        menuSearch.add(menuRegExp);
        
        menuBar.add(menuFile);
        menuBar.add(menuSearch);
        return menuBar;
    }

    public JFileChooser createFileChooser() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setName("FileChooser");
        return fileChooser;
    }

    private JButton createSaveButton(FileManager fileManager) {
        JButton saveButton = new JButton(new ImageIcon("./icons/save.png"));
        saveButton.setName("SaveButton");
        saveButton.addActionListener(e -> fileManager.write());
        saveButton.setPreferredSize(buttonSize);
        return saveButton;
    }

    private JButton createOpenButton(FileManager fileManager) {
        JButton openButton = new JButton(new ImageIcon("./icons/open.png"));
        openButton.setName("OpenButton");
        openButton.addActionListener(e -> fileManager.read());
        openButton.setPreferredSize(buttonSize);
        return openButton;
    }

    private JPanel createTopPanel(JComponent... components) {
        JPanel topPanel = new JPanel();
        for (JComponent component : components) {
            topPanel.add(component);
        }
        topPanel.setBorder(new EmptyBorder(new Insets(10, 10, 5, 10)));
        return topPanel;
    }

    private JButton createSearchButton(SearchUtility searcher) {
        JButton searchButton = new JButton(new ImageIcon("./icons/search.png"));
        searchButton.setName("StartSearchButton");
        searchButton.addActionListener(a -> searcher.search());
        searchButton.setPreferredSize(buttonSize);
        return searchButton;
    }

    private JButton createPrevMatchButton(SearchUtility searcher) {
        JButton button = new JButton(new ImageIcon("./icons/prev.png"));
        button.setName("PreviousMatchButton");
        button.addActionListener(a -> searcher.previousMatch());
        button.setPreferredSize(buttonSize);
        return button;
    }

    private JButton createNextMatchButton(SearchUtility searcher) {
        JButton button = new JButton(new ImageIcon("./icons/next.png"));
        button.setName("NextMatchButton");
        button.addActionListener(a -> searcher.nextMatch());
        button.setPreferredSize(buttonSize);
        return button;
    }

    private JCheckBox createRegExCheckbox(SearchUtility searcher) {
        checkBox.setText("use regex");
        checkBox.setName("UseRegExCheckbox");
        checkBox.addItemListener(e -> searcher.setRegEx(e.getStateChange() == ItemEvent.SELECTED));
        return checkBox;
    }
}
