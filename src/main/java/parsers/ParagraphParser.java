package parsers;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import enums.ParagraphType;
import enums.TextFormatType;

public class ParagraphParser implements MarkdownParser {
    private String text;
    public ParagraphType type;
    public String content;
    public List<TextPart> parts = new ArrayList<>();

    public ParagraphParser(String text) {
        this.text = text;
    }

    @Override
    public Boolean validate() {
        return this.text != null && !this.text.isEmpty();
    }

    @Override
    public void parse() {
        this.type = ParagraphType.NORMAL;
        this.content = String.join(" ", this.text.split("\n"));

        Pattern pattern = Pattern.compile(
                "(\\*\\*|__)(.*?)\\1|" + // Bold
                        "\\*(.*?)\\*|" + // Italic
                        "\\[(.*?)\\]\\((.*?)\\)|" + // Link
                        "(~~)(.*?)\\6|" + // Strikethrough
                        "(~)([^~]*)\\8|" + // Subscript
                        "(\\^)([^\\^]*)\\10|" + // Superscript
                        "([^\\*_\\[\\]\\~\\^]+)"); // Normal text
        Matcher matcher = pattern.matcher(this.content);

        while (matcher.find()) {
            if (matcher.group(1) != null) {
                parts.add(new TextPart(matcher.group(2), TextFormatType.BOLD));
            } else if (matcher.group(3) != null) {
                parts.add(new TextPart(matcher.group(3), TextFormatType.ITALIC));
            } else if (matcher.group(4) != null) {
            } else if (matcher.group(6) != null) {
                parts.add(new TextPart(matcher.group(7), TextFormatType.STRIKETHROUGH));
            } else if (matcher.group(8) != null) {
                parts.add(new TextPart(matcher.group(9), TextFormatType.SUBSCRIPT));
            } else if (matcher.group(10) != null) {
                parts.add(new TextPart(matcher.group(11), TextFormatType.SUPERSCRIPT));
            } else if (matcher.group(12) != null) {
                parts.add(new TextPart(matcher.group(12), TextFormatType.NORMAL));
            }
        }
    }

    @Override
    public String toJavaSwingCode() {
        return "JLabel label = new JLabel(\"" + this.content + "\");";
    }

    @Override
    public JScrollPane toJavaSwingComponent() {
        JTextPane textPane = new JTextPane();
        textPane.setContentType("text/html");
        textPane.setEditable(false);
        StyledDocument doc = textPane.getStyledDocument();

        Style regular = doc.addStyle("regular", null);
        StyleConstants.setFontFamily(regular, "SansSerif");

        Style bold = doc.addStyle("bold", regular);
        StyleConstants.setBold(bold, true);

        Style italic = doc.addStyle("italic", regular);
        StyleConstants.setItalic(italic, true);

        Style linkStyle = doc.addStyle("link", regular);
        StyleConstants.setForeground(linkStyle, Color.BLUE);
        StyleConstants.setUnderline(linkStyle, true);

        Style strikethrough = doc.addStyle("strikethrough", regular);
        StyleConstants.setStrikeThrough(strikethrough, true);

        Style subscript = doc.addStyle("subscript", regular);
        StyleConstants.setSubscript(subscript, true);

        Style superscript = doc.addStyle("superscript", regular);
        StyleConstants.setSuperscript(superscript, true);

        for (TextPart part : parts) {
            try {
                switch (part.type) {
                    case BOLD:
                        doc.insertString(doc.getLength(), part.content, bold);
                        break;
                    case ITALIC:
                        doc.insertString(doc.getLength(), part.content, italic);
                        break;
                    case STRIKETHROUGH:
                        doc.insertString(doc.getLength(), part.content, strikethrough);
                        break;
                    case SUBSCRIPT:
                        doc.insertString(doc.getLength(), part.content, subscript);
                        break;
                    case SUPERSCRIPT:
                        doc.insertString(doc.getLength(), part.content, superscript);
                        break;
                    case NORMAL:
                        doc.insertString(doc.getLength(), part.content, regular);
                        break;
                    default:
                        doc.insertString(doc.getLength(), part.content, regular);
                        break;
                }
            } catch (BadLocationException e) {
                e.printStackTrace();
            }
        }

        return new JScrollPane(textPane);
    }
}
