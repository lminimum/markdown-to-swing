package parsers;

import enums.ListType;

public class ListParser implements MarkdownParser {
    String text;
    ListType type;
    String[] items;
}
