package parsers;

import java.awt.Color;
import java.awt.Font;
import java.util.Arrays;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class CodeParser implements MarkdownParser {
    private String text;
    public String language;
    public String code;
    public boolean isInline;

    public CodeParser(String text, boolean isInline) {
        this.text = text;
        this.isInline = isInline;
    }

    @Override
    public Boolean validate() {
        if (this.isInline) {
            return this.text.trim().matches("^`.*`$");
        } else {
            return this.text.trim().matches("^```[\\s\\S.]*?```$");
        }
    }

    @Override
    public void parse() {
        if (this.isInline) {
            this.code = this.text.replaceAll("^`|`$", "");
        } else {
            String[] splits = this.text.split("\n");
            this.language = splits[0].replace("```", "").trim();
            this.code = String.join("\n", Arrays.copyOfRange(splits, 1, splits.length - 1));
        }
    }

    @Override
    public String toJavaSwingCode() {
        if (this.isInline) {
            return "new JLabel(\"" + this.code + "\")";
        } else {
            return "new JScrollPane(new JTextArea(\"" + this.code + "\"))";
        }
    }

    @Override
    public JScrollPane toJavaSwingComponent() {
        JTextArea codeTextArea = new JTextArea(this.code);
        codeTextArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        codeTextArea.setBackground(new Color(50, 50, 50));
        codeTextArea.setForeground(Color.WHITE);
        codeTextArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        codeTextArea.setLineWrap(true);
        codeTextArea.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(codeTextArea);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        return scrollPane;
    }
}
