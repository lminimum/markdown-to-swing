// Markdown to Java Swing Converter
// Matthew Vine
// CSIS 505-B01 (Liberty University)
// July 5, 2024

package parsers;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class CodeParser implements MarkdownParser {
    private String text;
    public String language;
    public String code;

    public CodeParser(String text) {
        this.text = text;
    }

    @Override
    public Boolean validate() {
        return this.text.trim().matches("^```[\\s\\S.]*?```$");
    }

    @Override
    public void parse() {
        String[] splits = this.text.split("\n");
        this.language = splits[0].replace("```", "").trim();
        String textMinusFirstLine = this.text.replaceFirst("^.*?\\n", "");
        if (textMinusFirstLine.endsWith("\n```")) {
            this.code = textMinusFirstLine.replaceAll("\\n```$", "");
        } else {
            this.code = textMinusFirstLine.replaceAll("```$", "");
        }
    }

    @Override
    public String getResultPanelName(int paragraphIndex) {
        return "codeBlock" + paragraphIndex + "ScrollPane";
    }

    @Override
    public String toJavaSwingCode(int paragraphIndex) {
        String prefix = "codeBlock" + paragraphIndex;
        StringBuilder code = new StringBuilder();

        String clean_code = this.code.replaceAll("\"", "\\\\\"").replaceAll("\n", "\\\\n");
        code.append(
                "JTextArea " + prefix + "TextArea = new JTextArea(\"" + clean_code
                        + "\");\n");
        code.append(prefix + "TextArea.setFont(new Font(\"Monospaced\", Font.PLAIN, 12));\n");
        code.append(prefix + "TextArea.setBackground(new Color(50, 50, 50));\n");
        code.append(prefix + "TextArea.setForeground(Color.WHITE);\n");
        code.append(prefix + "TextArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));\n\n");

        code.append("JScrollPane " + prefix + "ScrollPane = new JScrollPane(" + prefix + "TextArea);\n");
        code.append(prefix + "ScrollPane.setBackground(java.awt.Color.WHITE);\n");
        code.append(prefix + "ScrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));\n");
        code.append(prefix + "ScrollPane.setPreferredSize(new Dimension(300, 150));");

        return code.toString();
    }

    @Override
    public JScrollPane toJavaSwingComponent() {
        JTextArea codeTextArea = new JTextArea(this.code);
        codeTextArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        codeTextArea.setBackground(new Color(50, 50, 50));
        codeTextArea.setForeground(Color.WHITE);
        codeTextArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JScrollPane scrollPane = new JScrollPane(codeTextArea);
        scrollPane.setBackground(java.awt.Color.WHITE);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        int lineCount = codeTextArea.getLineCount();
        int lineHeight = codeTextArea.getFontMetrics(codeTextArea.getFont()).getHeight();
        scrollPane.setMaximumSize(new Dimension(500, lineCount * lineHeight));

        return scrollPane;
    }
}
