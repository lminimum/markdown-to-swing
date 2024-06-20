// Markdown to Java Swing Converter
// Matthew Vine
// CSIS 505-B01 (Liberty University)
// July 5, 2024
package parsers;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

/**
 * Parses horizontal rules.
 */
public class HorizontalRuleParser implements MarkdownParser {
    private String text;

    /**
     * Initializes a new instance of the HorizontalRuleParser class.
     * 
     * @param text The text to parse.
     */
    public HorizontalRuleParser(String text) {
        this.text = text;
    }

    /**
     * Validates the horizontal rule text.
     * 
     * @return true if the horizontal rule text is valid; otherwise, false
     */
    @Override
    public Boolean validate() {
        // A horizontal rule only contains one or more hyphens, asterisks, or
        // underscores
        return this.text.trim().matches("^[-*_]+$");
    }

    /**
     * Parses the horizontal rule text.
     */
    @Override
    public void parse() {
        // No parsing is needed for a horizontal rule
    }

    /**
     * Gets the name of the result panel.
     * 
     * @param paragraphIndex The index of the paragraph.
     */
    @Override
    public String getResultPanelName(int paragraphIndex) {
        return "horizontalRule" + paragraphIndex;
    }

    /**
     * Converts the horizontal rule to Java Swing code.
     * 
     * @param paragraphIndex The index of the paragraph.
     */
    @Override
    public String toJavaSwingCode(int paragraphIndex) {
        // Create a unique prefix for the horizontal rule
        String prefix = "horizontalRule" + paragraphIndex;
        StringBuilder code = new StringBuilder();

        // Add the code using the same method as toJavaSwingComponent
        code.append("String " + prefix + "Html = \"<html><body style='width: %1spx'>%1s</body></html>\";");
        code.append("JLabel " + prefix + " = new JLabel(String.format(" + prefix + "Html, 400, \"\"));");
        code.append(prefix
                + ".setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10),");
        code.append("        BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY)));");

        return code.toString();
    }

    /**
     * Converts the horizontal rule to a Java Swing component.
     * 
     * @return The Java Swing component.
     */
    @Override
    public JLabel toJavaSwingComponent() {
        // This is used to set the width of the JLabel
        String html = "<html><body style='width: %1spx'>%1s</body></html>";
        String fullLengthString = String.format(html, 400, "");
        // Create a JLabel as a gray line
        JLabel label = new JLabel(fullLengthString);
        label.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(10, 10, 10, 10),
                BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY)));
        return label;
    }
}
