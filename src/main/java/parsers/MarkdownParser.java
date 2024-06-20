// Markdown to Java Swing Converter
// Matthew Vine
// CSIS 505-B01 (Liberty University)
// July 5, 2024

package parsers;

import javax.swing.JComponent;

/**
 * Interface for MarkdownParser classes.
 */
public interface MarkdownParser {
    /**
     * Validates the text.
     * 
     * @return true if the text is valid, false otherwise
     */
    public Boolean validate();

    /**
     * Parses the text.
     */
    public void parse();

    /**
     * Gets the result panel name.
     * 
     * @param paragraphIndex the paragraph index
     * @return the result panel name
     */
    public String getResultPanelName(int paragraphIndex);

    /**
     * Converts the text to Java Swing code.
     * 
     * @param paragraphIndex the paragraph index
     * @return the Java Swing code
     */
    public String toJavaSwingCode(int paragraphIndex);

    /**
     * Converts the text to a Java Swing component.
     * 
     * @return the Java Swing component
     */
    public JComponent toJavaSwingComponent();
}
