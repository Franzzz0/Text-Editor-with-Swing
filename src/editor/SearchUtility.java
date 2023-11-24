package editor;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SearchUtility {
    private boolean regExFlag;
    private final JTextArea text;
    private final JTextField searchField;
    private final List<Match> matches;
    private int currentMatchIndex;

    public SearchUtility(JTextArea text, JTextField searchField) {
        this.matches = new ArrayList<>();
        this.regExFlag = false;
        this.text = text;
        this.searchField = searchField;
    }

    public void search() {
        matches.clear();
        currentMatchIndex = -1;

        String text = this.text.getText();
        String searched = this.searchField.getText();
        if (regExFlag) {
            Pattern pattern = Pattern.compile(searched);
            Matcher matcher = pattern.matcher(text);
            while (matcher.find()) {
                matches.add(new Match(matcher.start(), matcher.end()));
            }
        } else {
            int lastMatchEndIndex = 0;
            while (true) {
                int startIndex = text.indexOf(searched, lastMatchEndIndex);
                if (startIndex == -1) break;
                int endIndex = startIndex + searched.length();
                matches.add(new Match(startIndex, endIndex));
                lastMatchEndIndex = endIndex;
            }
        }
        nextMatch();
    }

    public void nextMatch() {
        if (matches.isEmpty()) return;
        currentMatchIndex++;
        if (currentMatchIndex == matches.size()) {
            currentMatchIndex = 0;
        }
        selectCurrentMatch();
    }

    public void previousMatch() {
        if (matches.isEmpty()) return;
        currentMatchIndex--;
        if (currentMatchIndex < 0) {
            currentMatchIndex = matches.size() - 1;
        }
        selectCurrentMatch();
    }

    private void selectCurrentMatch() {
        Match currentMatch = matches.get(currentMatchIndex);
        int startIndex = currentMatch.startIndex();
        int endIndex = currentMatch.endIndex();
        text.setCaretPosition(endIndex);
        text.select(startIndex, endIndex);
        text.grabFocus();
    }

    public void setRegEx(boolean regEx) {
        this.regExFlag = regEx;
    }
}
