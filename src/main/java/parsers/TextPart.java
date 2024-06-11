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
