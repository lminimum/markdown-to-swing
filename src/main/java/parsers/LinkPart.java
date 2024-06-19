// Markdown to Java Swing Converter
// Matthew Vine
// CSIS 505-B01 (Liberty University)
// July 5, 2024

package parsers;

import enums.TextFormatType;

public class LinkPart extends TextPart {
    String address;

    public LinkPart(String text, String address) {
        super(text, TextFormatType.LINK);
        this.address = address;
    }
}
