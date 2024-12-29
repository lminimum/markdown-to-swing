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

/**
 * Parses Markdown code blocks.
 */
public class CodeParser implements MarkdownParser {
    private String text;
    public String language;
    public String code;

    /**
     * Initializes a new instance of the CodeParser class.
     * 
     * @param text the code block text
     */
    public CodeParser(String text) {
        this.text = text;
    }

    /**
     * Validates the code block text.
     * 
     * @return true if the code block text is valid; otherwise, false
     */
    @Override
    public Boolean validate() {
        // Check if the text starts and ends with triple backticks with any text in
        // between
        return this.text.trim().matches("^```[\\s\\S.]*?```$");
    }

    /**
     * Parses the code block text.
     */
    @Override
    public void parse() {
        // Split the text into lines
        String[] splits = this.text.split("\n");
        // Get the language from the first line
        this.language = splits[0].replace("```", "").trim();
        // Get the remaining lines by replacing the first line with an empty string
        String textMinusFirstLine = this.text.replaceFirst("^.*?\\n", "");
        // If the text ends with a newline and triple backticks
        if (textMinusFirstLine.endsWith("\n```")) {
            // Remove the newline and triple backticks
            this.code = textMinusFirstLine.replaceAll("\\n```$", "");
        } else {
            // If the text ends with triple backticks and code, only remove the triple
            // backticks
            this.code = textMinusFirstLine.replaceAll("```$", "");
        }
    }

    /**
     * Gets the result panel name.
     * 
     * @param paragraphIndex the paragraph index
     * @return the result panel name
     */
    @Override
    public String getResultPanelName(int paragraphIndex) {
        return "codeBlock" + paragraphIndex + "ScrollPane";
    }

    /**
     * Converts the code block text to Java Swing code.
     * 
     * @param paragraphIndex the paragraph index
     * @return the Java Swing code
     */
    @Override
    public String toJavaSwingCode(int paragraphIndex) {
        // Create a unique prefix for the code block
        String prefix = "codeBlock" + paragraphIndex;
        StringBuilder code = new StringBuilder();

        // Escape double quotes and newlines in the code
        String cleanCode = this.code.replaceAll("\"", "\\\\\"").replaceAll("\n", "\\\\n");
        // Add the code using the same method as toJavaSwingComponent
        code.append(
                "JTextArea " + prefix + "TextArea = new JTextArea(\"" + cleanCode
                        + "\");\n");
        code.append(prefix + "TextArea.setFont(new Font(\"Monospaced\", Font.PLAIN, 12));\n");
//        code.append(prefix + "TextArea.setBackground(new Color(50, 50, 50));\n");
//        code.append(prefix + "TextArea.setForeground(Color.WHITE);\n");
        code.append(prefix + "TextArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));\n\n");

        code.append("JScrollPane " + prefix + "ScrollPane = new JScrollPane(" + prefix + "TextArea);\n");
//        code.append(prefix + "ScrollPane.setBackground(Color.WHITE);\n");
        code.append(prefix + "ScrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));\n");
        code.append(prefix + "ScrollPane.setPreferredSize(new Dimension(300, 150));");

        return code.toString();
    }

    /**
     * Converts the code block text to a Java Swing component.
     * 
     * @return the Java Swing component
     */
    @Override
    public JScrollPane toJavaSwingComponent() {
        // Create a JTextArea with the code
        JTextArea codeTextArea = new JTextArea(this.code);
        // Set the font, background, and foreground color
        codeTextArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
//        codeTextArea.setBackground(new Color(50, 50, 50));
//        codeTextArea.setForeground(Color.WHITE);
        codeTextArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Create a JScrollPane with the JTextArea
        JScrollPane scrollPane = new JScrollPane(codeTextArea);
//        scrollPane.setBackground(Color.WHITE);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        // Try to set the preferred size based on the number of lines and line height
        int lineCount = codeTextArea.getLineCount();
        int lineHeight = codeTextArea.getFontMetrics(codeTextArea.getFont()).getHeight();
        scrollPane.setMaximumSize(new Dimension(500, lineCount * lineHeight));

        return scrollPane;
    }
}
