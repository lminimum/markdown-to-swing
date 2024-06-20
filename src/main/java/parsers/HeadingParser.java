// Markdown to Java Swing Converter
// Matthew Vine
// CSIS 505-B01 (Liberty University)
// July 5, 2024

package parsers;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

/**
 * Parses Markdown headings.
 */
public class HeadingParser implements MarkdownParser {
    // Constant array of font sizes for each heading level
    public static final float[] FONT_SIZES = { 28.0f, 24.0f, 20.0f, 16.0f, 14.0f, 12.0f, 10.0f };

    public String text;
    public int level;
    public String title;

    /**
     * Initializes a new instance of the HeadingParser class.
     * 
     * @param text the heading text
     */
    public HeadingParser(String text) {
        this.text = text;
    }

    /**
     * Validates the heading text.
     * 
     * @return true if the heading text is valid; otherwise, false
     */
    @Override
    public Boolean validate() {
        return this.text != null && !this.text.isEmpty();
    }

    /**
     * Parses the heading text.
     */
    @Override
    public void parse() {
        String[] splits = this.text.split(" ");
        this.level = splits[0].length();
        this.title = this.text.substring(this.level + 1).trim();
    }

    /**
     * Gets the font size for the heading level.
     * 
     * @return the font size
     */
    private float getFontSize() {
        // Return the font size for the heading level, or the last font size if the
        // level is greater than the number of font sizes.
        return FONT_SIZES[Math.min(this.level - 1, FONT_SIZES.length - 1)];
    }

    /**
     * Gets the name of the result panel for the heading.
     * 
     * @param paragraphIndex the index of the paragraph
     * @return the result panel name
     */
    @Override
    public String getResultPanelName(int paragraphIndex) {
        return "heading" + paragraphIndex + "Label";
    }

    /**
     * Converts the heading to Java Swing code.
     * 
     * @param paragraphIndex the index of the paragraph
     * @return the Java Swing code
     */
    @Override
    public String toJavaSwingCode(int paragraphIndex) {
        // Create a unique prefix for the heading
        String prefix = "heading" + paragraphIndex;
        StringBuilder code = new StringBuilder();

        // Add the code using the same method as toJavaSwingComponent
        code.append(
                "String " + prefix + "Html = \"<html><body style='width: %1spx'>%1s</body></html>\";");
        code.append("JLabel " + prefix + "Label = new JLabel(String.format(" + prefix + "Html, 420, \"" + this.title
                + "\"));\n");
        code.append(prefix + "Label.setFont(" + prefix + "Label.getFont().deriveFont(" + getFontSize() + "f));");

        if (this.level <= 2) {
            code.append(
                    prefix + "Label.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10),");
            code.append("        BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY)));");
        } else {
            code.append(prefix + "Label.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));");
        }

        return code.toString();
    }

    /**
     * Converts the heading to a Java Swing component.
     * 
     * @return the Java Swing component
     */
    @Override
    public JLabel toJavaSwingComponent() {
        // This is used to wrap the text in the JLabel.
        // Reference:
        // https://stackoverflow.com/questions/7861724/is-there-a-word-wrap-property-for-jlabel
        String html = "<html><body style='width: %1spx'>%1s</body></html>";
        String wrappedText = String.format(html, 400, this.title);
        // Create a JLabel and set the font size
        JLabel label = new JLabel(wrappedText);
        label.setFont(label.getFont().deriveFont(getFontSize()));

        // Set the border based on the heading level
        if (this.level <= 2) {
            label.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createEmptyBorder(10, 10, 10, 10),
                    BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY)));
        } else {
            label.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        }
        return label;
    }
}
