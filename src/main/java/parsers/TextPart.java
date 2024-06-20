// Markdown to Java Swing Converter
// Matthew Vine
// CSIS 505-B01 (Liberty University)
// July 5, 2024

package parsers;

import enums.TextFormatType;

/**
 * Container for text parts.
 */
public class TextPart {
    public TextFormatType type;
    public String content;

    /**
     * Initializes a new instance of the TextPart class.
     * 
     * @param content The content of the text part.
     * @param type    The type of the text part.
     */
    public TextPart(String content, TextFormatType type) {
        this.content = content;
        this.type = type;
    }
}
