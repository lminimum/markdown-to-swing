// Markdown to Java Swing Converter
// Matthew Vine
// CSIS 505-B01 (Liberty University)
// July 5, 2024

package parsers;

import enums.TextFormatType;

/**
 * Container for links.
 */
public class LinkPart extends TextPart {
    String address;

    /**
     * Initializes a new instance of the LinkPart class.
     * 
     * @param text    The text of the link.
     * @param address The address of the link.
     */
    public LinkPart(String text, String address) {
        super(text, TextFormatType.LINK);
        this.address = address;
    }
}
