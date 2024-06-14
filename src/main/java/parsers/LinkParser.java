package parsers;

import javax.swing.JLabel;

public class LinkParser implements MarkdownParser {
    String text;
    String title;
    String link;

    public LinkParser(String text) {
        this.text = text;
    }

    public LinkParser(String title, String link) {
        this.title = title;
        this.link = link;
    }

    @Override
    public Boolean validate() {
        return text.startsWith("[") && text.endsWith(")") && text.contains("](");
    }

    @Override
    public void parse() {
        String[] parts = text.split("]\\(");
        title = parts[0].substring(1);
        link = parts[1].substring(0, parts[1].length() - 1);
    }

    @Override
    public String toJavaSwingCode(int paragraphIndex) {
        return "new JLabel(\"" + title + "\")";
    }

    @Override
    public JLabel toJavaSwingComponent() {
        return new JLabel(title);
    }
}
