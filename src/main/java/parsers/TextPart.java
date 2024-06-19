// Markdown to Java Swing Converter
// Matthew Vine
// CSIS 505-B01 (Liberty University)
// July 5, 2024

package parsers;

import enums.TextFormatType;

public class TextPart {
    public TextFormatType type;
    public String content;

    public TextPart(String content, TextFormatType type) {
        this.content = content;
        this.type = type;
    }
}
