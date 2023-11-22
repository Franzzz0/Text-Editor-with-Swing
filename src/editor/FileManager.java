package editor;

import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileManager {
    private final JTextField fileName;
    private final JLabel result;
    private final JTextArea text;

    public FileManager(JTextField fileName, JTextArea text, JLabel result) {
        this.fileName = fileName;
        this.result = result;
        this.text = text;
    }

    public void write() {
        File file = new File("./" + fileName.getText());
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(text.getText());
            result.setText("File saved successfully.");
        } catch (Exception e) {
            result.setText("Error. File not saved.");
        }
    }

    public void read() {
        try {
            var filePath = Paths.get("./" + fileName.getText());
            String text = new String(Files.readAllBytes(filePath));
            this.text.setText(text);
            result.setText("File opened successfully.");
        } catch (Exception e) {
            text.setText("");
            result.setText("Error. Cannot read file.");
        }
    }
}
