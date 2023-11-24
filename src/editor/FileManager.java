package editor;

import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;

public class FileManager {
    private final JFileChooser fileChooser;
    private final JLabel result;
    private final JTextArea text;

    public FileManager(JTextArea text, JLabel result, JFileChooser fileChooser) {
        this.fileChooser = fileChooser;
        this.result = result;
        this.text = text;
    }

    public void write() {
        int returnValue = fileChooser.showSaveDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try (FileWriter writer = new FileWriter(file)) {
                writer.write(text.getText());
                result.setText("File saved successfully.");
            } catch (Exception e) {
                result.setText("Error. File not saved.");
            }
        }
    }

    public void read() {
        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            try {
                var filePath = fileChooser.getSelectedFile().toPath();
                String text = new String(Files.readAllBytes(filePath));
                this.text.setText(text);
                result.setText("File opened successfully.");
            } catch (Exception e) {
                text.setText("");
                result.setText("Error. Cannot read file.");
            }
        }
    }
}
