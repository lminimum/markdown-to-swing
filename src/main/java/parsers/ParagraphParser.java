// Markdown to Java Swing Converter
// Matthew Vine
// CSIS 505-B01 (Liberty University)
// July 5, 2024

package parsers;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import javax.swing.text.AttributeSet;
import javax.swing.text.Element;

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
        if (this.text.trim().matches("^[-*_]+$")) {
            this.type = ParagraphType.HORIZONTAL_RULE;
        } else if (this.text.trim().startsWith("> ")) {
            this.type = ParagraphType.BLOCK_QUOTE;
        } else {
            this.type = ParagraphType.NORMAL;
        }
        this.content = String.join(" ", this.text.split("\n"));
        if (this.type == ParagraphType.BLOCK_QUOTE) {
            this.content = this.content.replaceFirst("> ", "    ");
        }

        Pattern pattern = Pattern.compile(
                "(`)(.*?)\\1|" + // Code block
                        "(\\*\\*|__)(.*?)\\3|" + // Bold
                        "\\*(.*?)\\*|" + // Italic
                        "\\[(.*?)\\]\\((.*?)\\)|" + // Link
                        "(~~)(.*?)\\8|" + // Strikethrough
                        "(~)([^~]*)\\10|" + // Subscript
                        "(\\^)([^\\^]*)\\12|" + // Superscript
                        "([^\\*_\\[\\]\\~\\^`]+)"); // Normal text
        Matcher matcher = pattern.matcher(this.content);

        while (matcher.find()) {
            if (matcher.group(1) != null) {
                parts.add(new TextPart(matcher.group(2), TextFormatType.CODE));
            } else if (matcher.group(3) != null) {
                parts.add(new TextPart(matcher.group(4), TextFormatType.BOLD));
            } else if (matcher.group(5) != null) {
                parts.add(new TextPart(matcher.group(5), TextFormatType.ITALIC));
            } else if (matcher.group(6) != null) {
                parts.add(new LinkPart(matcher.group(6), matcher.group(7)));
            } else if (matcher.group(8) != null) {
                parts.add(new TextPart(matcher.group(9), TextFormatType.STRIKETHROUGH));
            } else if (matcher.group(10) != null) {
                parts.add(new TextPart(matcher.group(11), TextFormatType.SUBSCRIPT));
            } else if (matcher.group(12) != null) {
                parts.add(new TextPart(matcher.group(13), TextFormatType.SUPERSCRIPT));
            } else if (matcher.group(14) != null) {
                parts.add(new TextPart(matcher.group(14), TextFormatType.NORMAL));
            }
        }
    }

    private boolean anyPartHasType(TextFormatType type) {
        for (TextPart part : parts) {
            if (part.type == type) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getResultPanelName(int paragraphIndex) {
        return "paragraph" + paragraphIndex;
    }

    @Override
    public String toJavaSwingCode(int paragraphIndex) {
        String prefix = "paragraph" + paragraphIndex;
        StringBuilder code = new StringBuilder();

        if (this.type == ParagraphType.HORIZONTAL_RULE) {
            code.append("String " + prefix + "Html = \"<html><body style='width: %1spx'>%1s</body></html>\";");
            code.append("JLabel " + prefix + " = new JLabel(String.format(" + prefix + "Html, 400, \"\"));");
            code.append(prefix
                    + ".setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10),");
            code.append("        BorderFactory.createMatteBorder(0, 0, 1, 0, java.awt.Color.GRAY)));");
        } else {
            code.append("JTextPane " + prefix + " = new JTextPane();\n");
            code.append(prefix + ".setContentType(\"text/html\");\n");
            code.append(prefix + ".setEditable(false);\n");
            code.append("StyledDocument " + prefix + "Doc = " + prefix + ".getStyledDocument();\n");
            code.append(prefix + ".setMargin(new Insets(10, 10, 10, 10));\n\n");

            if (this.type == ParagraphType.BLOCK_QUOTE) {
                code.append(prefix
                        + ".setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10),");
                code.append("        BorderFactory.createMatteBorder(0, 3, 0, 0, Color.DARK_GRAY)));\n");
            }

            if (anyPartHasType(TextFormatType.LINK)) {
                code.append("Map<Integer, String> " + prefix + "LinkMap = new HashMap<>();");
            }

            if (anyPartHasType(TextFormatType.NORMAL)) {
                code.append("Style regular" + paragraphIndex + " = " + prefix + "Doc.addStyle(\"regular\", null);\n");
                code.append("StyleConstants.setFontFamily(regular" + paragraphIndex + ", \"SansSerif\");\n");
            }

            if (anyPartHasType(TextFormatType.BOLD)) {
                code.append("Style bold" + paragraphIndex + " = " + prefix + "Doc.addStyle(\"bold\", regular"
                        + paragraphIndex + ");\n");
                code.append("StyleConstants.setBold(bold" + paragraphIndex + ", true);\n");
            }

            if (anyPartHasType(TextFormatType.ITALIC)) {
                code.append("Style italic" + paragraphIndex + " = " + prefix + "Doc.addStyle(\"italic\", regular"
                        + paragraphIndex + ");\n");
                code.append("StyleConstants.setItalic(italic" + paragraphIndex + ", true);\n");
            }

            if (anyPartHasType(TextFormatType.LINK)) {
                code.append("Style linkStyle" + paragraphIndex + " = " + prefix + "Doc.addStyle(\"link\", regular"
                        + paragraphIndex + ");\n");
                code.append("StyleConstants.setForeground(linkStyle" + paragraphIndex + ", Color.BLUE);\n");
                code.append("StyleConstants.setUnderline(linkStyle" + paragraphIndex + ", true);\n");
            }

            if (anyPartHasType(TextFormatType.STRIKETHROUGH)) {
                code.append("Style strikethrough" + paragraphIndex + " = " + prefix
                        + "Doc.addStyle(\"strikethrough\", regular" + paragraphIndex + ");\n");
                code.append("StyleConstants.setStrikeThrough(strikethrough" + paragraphIndex + ", true);\n");
            }

            if (anyPartHasType(TextFormatType.SUBSCRIPT)) {
                code.append(
                        "Style subscript" + paragraphIndex + " = " + prefix + "Doc.addStyle(\"subscript\", regular"
                                + paragraphIndex + ");\n");
                code.append("StyleConstants.setSubscript(subscript" + paragraphIndex + ", true);\n");
            }

            if (anyPartHasType(TextFormatType.SUPERSCRIPT)) {
                code.append(
                        "Style superscript" + paragraphIndex + " = " + prefix + "Doc.addStyle(\"superscript\", regular"
                                + paragraphIndex + ");\n");
                code.append("StyleConstants.setSuperscript(superscript" + paragraphIndex + ", true);\n");
            }

            if (anyPartHasType(TextFormatType.CODE)) {
                code.append("Style code" + paragraphIndex + " = " + prefix + "Doc.addStyle(\"code\", regular"
                        + paragraphIndex + ");\n");
                code.append("StyleConstants.setFontFamily(code" + paragraphIndex + ", \"Monospaced\");\n");
                code.append("StyleConstants.setBackground(code" + paragraphIndex + ", new Color(240, 240, 240));\n");
            }

            code.append("\ntry {\n");
            for (TextPart part : parts) {
                switch (part.type) {
                    case BOLD:
                        code.append("    " + prefix + "Doc.insertString(" + prefix + "Doc.getLength(), \""
                                + part.content + "\", bold" + paragraphIndex + ");\n");
                        break;
                    case ITALIC:
                        code.append("    " + prefix + "Doc.insertString(" + prefix + "Doc.getLength(), \""
                                + part.content + "\", italic" + paragraphIndex + ");\n");
                        break;
                    case LINK:
                        if (part instanceof LinkPart) {
                            LinkPart linkPart = (LinkPart) part;
                            code.append("    " + prefix + "LinkMap.put(" + prefix + "Doc.getLength(), \""
                                    + linkPart.address + "\");\n");
                            code.append("    " + prefix + "Doc.insertString(" + prefix + "Doc.getLength(), \""
                                    + linkPart.content + "\", linkStyle" + paragraphIndex + ");\n");
                        }
                        break;
                    case STRIKETHROUGH:
                        code.append("    " + prefix + "Doc.insertString(" + prefix + "Doc.getLength(), \""
                                + part.content + "\", strikethrough" + paragraphIndex + ");\n");
                        break;
                    case SUBSCRIPT:
                        code.append("    " + prefix + "Doc.insertString(" + prefix + "Doc.getLength(), \""
                                + part.content + "\", subscript" + paragraphIndex + ");\n");
                        break;
                    case SUPERSCRIPT:
                        code.append("    " + prefix + "Doc.insertString(" + prefix + "Doc.getLength(), \""
                                + part.content + "\", superscript" + paragraphIndex + ");\n");
                        break;
                    case CODE:
                        code.append("    " + prefix + "Doc.insertString(" + prefix + "Doc.getLength(), \""
                                + part.content + "\", code" + paragraphIndex + ");\n");
                        break;
                    default:
                        code.append("    " + prefix + "Doc.insertString(" + prefix + "Doc.getLength(), \""
                                + part.content + "\", regular" + paragraphIndex + ");\n");
                        break;
                }
            }
            code.append("} catch (BadLocationException e) {\n");
            code.append("    e.printStackTrace();\n");
            code.append("}");

            if (anyPartHasType(TextFormatType.LINK)) {
                code.append("\n\n" + prefix + ".addMouseListener(new MouseAdapter() {\n");
                code.append("    @Override\n");
                code.append("    public void mouseClicked(MouseEvent e) {\n");
                code.append("        try {\n");
                code.append("            int offset = " + prefix + ".viewToModel2D(e.getPoint());\n");
                code.append("            if (offset >= 0) {\n");
                code.append("                Element element = " + prefix + "Doc.getCharacterElement(offset);\n");
                code.append("                AttributeSet as = element.getAttributes();\n");
                code.append("                if (StyleConstants.isUnderline(as)) {\n");
                code.append("                    int start = element.getStartOffset();\n");
                code.append("                    String url = " + prefix + "LinkMap.get(start);\n");
                code.append("                    if (url != null) {\n");
                code.append("                        Desktop.getDesktop().browse(new URI(url));\n");
                code.append("                    }\n");
                code.append("                }\n");
                code.append("            }\n");
                code.append("        } catch (Exception ex) {\n");
                code.append("            ex.printStackTrace();\n");
                code.append("        }\n");
                code.append("    }\n");
                code.append("});\n");

                code.append("\n" + prefix + ".addMouseMotionListener(new MouseMotionAdapter() {\n");
                code.append("    @Override\n");
                code.append("    public void mouseMoved(MouseEvent e) {\n");
                code.append("        int offset = " + prefix + ".viewToModel2D(e.getPoint());\n");
                code.append("        if (offset >= 0) {\n");
                code.append("            Element element = " + prefix + "Doc.getCharacterElement(offset);\n");
                code.append("            AttributeSet as = element.getAttributes();\n");
                code.append("            if (StyleConstants.isUnderline(as)) {\n");
                code.append(
                        "                " + prefix + ".setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));\n");
                code.append("            } else {\n");
                code.append("                " + prefix + ".setCursor(Cursor.getDefaultCursor());\n");
                code.append("            }\n");
                code.append("        } else {\n");
                code.append("            " + prefix + ".setCursor(Cursor.getDefaultCursor());\n");
                code.append("        }\n");
                code.append("    }\n");
                code.append("});\n");
            }
        }

        return code.toString();
    }

    @Override
    public JComponent toJavaSwingComponent() {
        // TODO: move to a separate class
        if (this.type == ParagraphType.HORIZONTAL_RULE) {
            String html = "<html><body style='width: %1spx'>%1s</body></html>";
            JLabel label = new JLabel(String.format(html, 400, ""));
            label.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createEmptyBorder(10, 10, 10, 10),
                    BorderFactory.createMatteBorder(0, 0, 1, 0, java.awt.Color.GRAY)));
            return label;
        } else {
            JTextPane textPane = new JTextPane();
            textPane.setContentType("text/html");
            textPane.setEditable(false);
            StyledDocument doc = textPane.getStyledDocument();
            textPane.setMargin(new Insets(10, 10, 10, 10));

            if (this.type == ParagraphType.BLOCK_QUOTE) {
                textPane.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createEmptyBorder(10, 10, 10, 10),
                        BorderFactory.createMatteBorder(0, 3, 0, 0, Color.DARK_GRAY)));
            }

            Map<Integer, String> linkMap = new HashMap<>();

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

            Style code = doc.addStyle("code", regular);
            StyleConstants.setFontFamily(code, "Monospaced");
            StyleConstants.setBackground(code, new Color(240, 240, 240));

            for (TextPart part : parts) {
                try {
                    switch (part.type) {
                        case BOLD:
                            doc.insertString(doc.getLength(), part.content, bold);
                            break;
                        case ITALIC:
                            doc.insertString(doc.getLength(), part.content, italic);
                            break;
                        case LINK:
                            if (part instanceof LinkPart) {
                                LinkPart linkPart = (LinkPart) part;
                                linkMap.put(doc.getLength(), linkPart.address);
                                doc.insertString(doc.getLength(), linkPart.content, linkStyle);
                            }
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
                        case CODE:
                            doc.insertString(doc.getLength(), part.content, code);
                            break;
                        default:
                            doc.insertString(doc.getLength(), part.content, regular);
                            break;
                    }
                } catch (BadLocationException e) {
                    e.printStackTrace();
                }
            }

            textPane.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    try {
                        int offset = textPane.viewToModel2D(e.getPoint());
                        if (offset >= 0) {
                            Element element = doc.getCharacterElement(offset);
                            AttributeSet as = element.getAttributes();
                            if (StyleConstants.isUnderline(as)) {
                                int start = element.getStartOffset();
                                String url = linkMap.get(start);
                                if (url != null) {
                                    Desktop.getDesktop().browse(new URI(url));
                                }
                            }
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });

            textPane.addMouseMotionListener(new MouseMotionAdapter() {
                @Override
                public void mouseMoved(MouseEvent e) {
                    int offset = textPane.viewToModel2D(e.getPoint());
                    if (offset >= 0) {
                        Element element = doc.getCharacterElement(offset);
                        AttributeSet as = element.getAttributes();
                        if (StyleConstants.isUnderline(as)) {
                            textPane.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                        } else {
                            textPane.setCursor(Cursor.getDefaultCursor());
                        }
                    } else {
                        textPane.setCursor(Cursor.getDefaultCursor());
                    }
                }
            });

            return textPane;
        }
    }
}
