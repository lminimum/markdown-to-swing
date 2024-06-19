// Markdown to Java Swing Converter
// Matthew Vine
// CSIS 505-B01 (Liberty University)
// July 5, 2024

package gui;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.*;
import java.net.*;

public class Test extends JFrame {

    public static void main(String[] args) {
        new Test();
    }

    public Test() {
        // Main Panel
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        // Main Scroll Pane
        JScrollPane mainScrollPane = new JScrollPane(mainPanel);
        mainScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        mainScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        // Paragraph 1 - Heading
        String heading1Html = "<html><body style='width: %1spx'>%1s</body></html>";
        JLabel heading1Label = new JLabel(String.format(heading1Html, 420, "Heading 1"));
        heading1Label.setFont(heading1Label.getFont().deriveFont(28.0f));
        heading1Label.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10),
                BorderFactory.createMatteBorder(0, 0, 1, 0, java.awt.Color.GRAY)));

        heading1Label.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainPanel.add(heading1Label);

        // Paragraph 2 - Paragraph
        JTextPane paragraph2 = new JTextPane();
        paragraph2.setContentType("text/html");
        paragraph2.setEditable(false);
        StyledDocument paragraph2Doc = paragraph2.getStyledDocument();
        paragraph2.setMargin(new Insets(10, 10, 10, 10));

        Style regular2 = paragraph2Doc.addStyle("regular", null);
        StyleConstants.setFontFamily(regular2, "SansSerif");

        try {
            paragraph2Doc.insertString(paragraph2Doc.getLength(), "This is a paragraph", regular2);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }

        paragraph2.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainPanel.add(paragraph2);

        // Paragraph 3 - Heading
        String heading3Html = "<html><body style='width: %1spx'>%1s</body></html>";
        JLabel heading3Label = new JLabel(String.format(heading3Html, 420, "Heading 2"));
        heading3Label.setFont(heading3Label.getFont().deriveFont(24.0f));
        heading3Label.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10),
                BorderFactory.createMatteBorder(0, 0, 1, 0, java.awt.Color.GRAY)));

        heading3Label.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainPanel.add(heading3Label);

        // Paragraph 4 - Paragraph
        JTextPane paragraph4 = new JTextPane();
        paragraph4.setContentType("text/html");
        paragraph4.setEditable(false);
        StyledDocument paragraph4Doc = paragraph4.getStyledDocument();
        paragraph4.setMargin(new Insets(10, 10, 10, 10));

        Style regular4 = paragraph4Doc.addStyle("regular", null);
        StyleConstants.setFontFamily(regular4, "SansSerif");

        try {
            paragraph4Doc.insertString(paragraph4Doc.getLength(), "This is another paragraph", regular4);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }

        paragraph4.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainPanel.add(paragraph4);

        // Paragraph 5 - Heading
        String heading5Html = "<html><body style='width: %1spx'>%1s</body></html>";
        JLabel heading5Label = new JLabel(String.format(heading5Html, 420, "Heading 3"));
        heading5Label.setFont(heading5Label.getFont().deriveFont(20.0f));
        heading5Label.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        heading5Label.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainPanel.add(heading5Label);

        // Paragraph 6 - Paragraph
        JTextPane paragraph6 = new JTextPane();
        paragraph6.setContentType("text/html");
        paragraph6.setEditable(false);
        StyledDocument paragraph6Doc = paragraph6.getStyledDocument();
        paragraph6.setMargin(new Insets(10, 10, 10, 10));

        Style regular6 = paragraph6Doc.addStyle("regular", null);
        StyleConstants.setFontFamily(regular6, "SansSerif");

        try {
            paragraph6Doc.insertString(paragraph6Doc.getLength(), "This is a third paragraph", regular6);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }

        paragraph6.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainPanel.add(paragraph6);

        // Paragraph 7 - Heading
        String heading7Html = "<html><body style='width: %1spx'>%1s</body></html>";
        JLabel heading7Label = new JLabel(String.format(heading7Html, 420, "Heading 4"));
        heading7Label.setFont(heading7Label.getFont().deriveFont(16.0f));
        heading7Label.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        heading7Label.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainPanel.add(heading7Label);

        // Paragraph 8 - Paragraph
        JTextPane paragraph8 = new JTextPane();
        paragraph8.setContentType("text/html");
        paragraph8.setEditable(false);
        StyledDocument paragraph8Doc = paragraph8.getStyledDocument();
        paragraph8.setMargin(new Insets(10, 10, 10, 10));

        Style regular8 = paragraph8Doc.addStyle("regular", null);
        StyleConstants.setFontFamily(regular8, "SansSerif");

        try {
            paragraph8Doc.insertString(paragraph8Doc.getLength(), "This is a fourth paragraph", regular8);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }

        paragraph8.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainPanel.add(paragraph8);

        // Paragraph 9 - Heading
        String heading9Html = "<html><body style='width: %1spx'>%1s</body></html>";
        JLabel heading9Label = new JLabel(String.format(heading9Html, 420, "Heading 5"));
        heading9Label.setFont(heading9Label.getFont().deriveFont(14.0f));
        heading9Label.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        heading9Label.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainPanel.add(heading9Label);

        // Paragraph 10 - Paragraph
        JTextPane paragraph10 = new JTextPane();
        paragraph10.setContentType("text/html");
        paragraph10.setEditable(false);
        StyledDocument paragraph10Doc = paragraph10.getStyledDocument();
        paragraph10.setMargin(new Insets(10, 10, 10, 10));

        Style regular10 = paragraph10Doc.addStyle("regular", null);
        StyleConstants.setFontFamily(regular10, "SansSerif");

        try {
            paragraph10Doc.insertString(paragraph10Doc.getLength(), "This is a fifth paragraph", regular10);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }

        paragraph10.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainPanel.add(paragraph10);

        // Paragraph 11 - Heading
        String heading11Html = "<html><body style='width: %1spx'>%1s</body></html>";
        JLabel heading11Label = new JLabel(String.format(heading11Html, 420, "Heading 6"));
        heading11Label.setFont(heading11Label.getFont().deriveFont(12.0f));
        heading11Label.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        heading11Label.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainPanel.add(heading11Label);

        // Paragraph 12 - Paragraph
        JTextPane paragraph12 = new JTextPane();
        paragraph12.setContentType("text/html");
        paragraph12.setEditable(false);
        StyledDocument paragraph12Doc = paragraph12.getStyledDocument();
        paragraph12.setMargin(new Insets(10, 10, 10, 10));

        Style regular12 = paragraph12Doc.addStyle("regular", null);
        StyleConstants.setFontFamily(regular12, "SansSerif");

        try {
            paragraph12Doc.insertString(paragraph12Doc.getLength(), "This is a sixth paragraph", regular12);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }

        paragraph12.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainPanel.add(paragraph12);

        // Paragraph 13 - Paragraph
        JTextPane paragraph13 = new JTextPane();
        paragraph13.setContentType("text/html");
        paragraph13.setEditable(false);
        StyledDocument paragraph13Doc = paragraph13.getStyledDocument();
        paragraph13.setMargin(new Insets(10, 10, 10, 10));

        Style regular13 = paragraph13Doc.addStyle("regular", null);
        StyleConstants.setFontFamily(regular13, "SansSerif");
        Style bold13 = paragraph13Doc.addStyle("bold", regular13);
        StyleConstants.setBold(bold13, true);
        Style italic13 = paragraph13Doc.addStyle("italic", regular13);
        StyleConstants.setItalic(italic13, true);

        try {
            paragraph13Doc.insertString(paragraph13Doc.getLength(), "This is a paragraph with ", regular13);
            paragraph13Doc.insertString(paragraph13Doc.getLength(), "bold", bold13);
            paragraph13Doc.insertString(paragraph13Doc.getLength(), " and ", regular13);
            paragraph13Doc.insertString(paragraph13Doc.getLength(), "italic", italic13);
            paragraph13Doc.insertString(paragraph13Doc.getLength(), " text", regular13);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }

        paragraph13.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainPanel.add(paragraph13);

        // Paragraph 14 - Paragraph
        JTextPane paragraph14 = new JTextPane();
        paragraph14.setContentType("text/html");
        paragraph14.setEditable(false);
        StyledDocument paragraph14Doc = paragraph14.getStyledDocument();
        paragraph14.setMargin(new Insets(10, 10, 10, 10));

        Map<Integer, String> paragraph14LinkMap = new HashMap<>();
        Style regular14 = paragraph14Doc.addStyle("regular", null);
        StyleConstants.setFontFamily(regular14, "SansSerif");
        Style linkStyle14 = paragraph14Doc.addStyle("link", regular14);
        StyleConstants.setForeground(linkStyle14, Color.BLUE);
        StyleConstants.setUnderline(linkStyle14, true);

        try {
            paragraph14Doc.insertString(paragraph14Doc.getLength(), "This is a paragraph two different links: ",
                    regular14);
            paragraph14LinkMap.put(paragraph14Doc.getLength(), "https://www.example.com");
            paragraph14Doc.insertString(paragraph14Doc.getLength(), "example", linkStyle14);
            paragraph14Doc.insertString(paragraph14Doc.getLength(), " and ", regular14);
            paragraph14LinkMap.put(paragraph14Doc.getLength(), "https://www.google.com");
            paragraph14Doc.insertString(paragraph14Doc.getLength(), "google", linkStyle14);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }

        paragraph14.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    int offset = paragraph14.viewToModel2D(e.getPoint());
                    if (offset >= 0) {
                        Element element = paragraph14Doc.getCharacterElement(offset);
                        AttributeSet as = element.getAttributes();
                        if (StyleConstants.isUnderline(as)) {
                            int start = element.getStartOffset();
                            String url = paragraph14LinkMap.get(start);
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

        paragraph14.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                int offset = paragraph14.viewToModel2D(e.getPoint());
                if (offset >= 0) {
                    Element element = paragraph14Doc.getCharacterElement(offset);
                    AttributeSet as = element.getAttributes();
                    if (StyleConstants.isUnderline(as)) {
                        paragraph14.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                    } else {
                        paragraph14.setCursor(Cursor.getDefaultCursor());
                    }
                } else {
                    paragraph14.setCursor(Cursor.getDefaultCursor());
                }
            }
        });

        paragraph14.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainPanel.add(paragraph14);

        // Paragraph 15 - Paragraph
        JTextPane paragraph15 = new JTextPane();
        paragraph15.setContentType("text/html");
        paragraph15.setEditable(false);
        StyledDocument paragraph15Doc = paragraph15.getStyledDocument();
        paragraph15.setMargin(new Insets(10, 10, 10, 10));

        Style regular15 = paragraph15Doc.addStyle("regular", null);
        StyleConstants.setFontFamily(regular15, "SansSerif");
        Style code15 = paragraph15Doc.addStyle("code", regular15);
        StyleConstants.setFontFamily(code15, "Monospaced");
        StyleConstants.setBackground(code15, new Color(240, 240, 240));

        try {
            paragraph15Doc.insertString(paragraph15Doc.getLength(), "This is a paragraph with ", regular15);
            paragraph15Doc.insertString(paragraph15Doc.getLength(), "code", code15);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }

        paragraph15.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainPanel.add(paragraph15);

        // Paragraph 16 - Paragraph
        JTextPane paragraph16 = new JTextPane();
        paragraph16.setContentType("text/html");
        paragraph16.setEditable(false);
        StyledDocument paragraph16Doc = paragraph16.getStyledDocument();
        paragraph16.setMargin(new Insets(10, 10, 10, 10));

        Style regular16 = paragraph16Doc.addStyle("regular", null);
        StyleConstants.setFontFamily(regular16, "SansSerif");
        Style strikethrough16 = paragraph16Doc.addStyle("strikethrough", regular16);
        StyleConstants.setStrikeThrough(strikethrough16, true);

        try {
            paragraph16Doc.insertString(paragraph16Doc.getLength(), "This is a paragraph with ", regular16);
            paragraph16Doc.insertString(paragraph16Doc.getLength(), "strikethrough", strikethrough16);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }

        paragraph16.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainPanel.add(paragraph16);

        // Paragraph 17 - Paragraph
        JTextPane paragraph17 = new JTextPane();
        paragraph17.setContentType("text/html");
        paragraph17.setEditable(false);
        StyledDocument paragraph17Doc = paragraph17.getStyledDocument();
        paragraph17.setMargin(new Insets(10, 10, 10, 10));

        Style regular17 = paragraph17Doc.addStyle("regular", null);
        StyleConstants.setFontFamily(regular17, "SansSerif");
        Style subscript17 = paragraph17Doc.addStyle("subscript", regular17);
        StyleConstants.setSubscript(subscript17, true);

        try {
            paragraph17Doc.insertString(paragraph17Doc.getLength(), "This is a paragraph with ", regular17);
            paragraph17Doc.insertString(paragraph17Doc.getLength(), "subscript", subscript17);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }

        paragraph17.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainPanel.add(paragraph17);

        // Paragraph 18 - Paragraph
        JTextPane paragraph18 = new JTextPane();
        paragraph18.setContentType("text/html");
        paragraph18.setEditable(false);
        StyledDocument paragraph18Doc = paragraph18.getStyledDocument();
        paragraph18.setMargin(new Insets(10, 10, 10, 10));

        Style regular18 = paragraph18Doc.addStyle("regular", null);
        StyleConstants.setFontFamily(regular18, "SansSerif");
        Style superscript18 = paragraph18Doc.addStyle("superscript", regular18);
        StyleConstants.setSuperscript(superscript18, true);

        try {
            paragraph18Doc.insertString(paragraph18Doc.getLength(), "This is a paragraph with ", regular18);
            paragraph18Doc.insertString(paragraph18Doc.getLength(), "superscript", superscript18);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }

        paragraph18.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainPanel.add(paragraph18);

        // Paragraph 19 - Paragraph
        JTextPane paragraph19 = new JTextPane();
        paragraph19.setContentType("text/html");
        paragraph19.setEditable(false);
        StyledDocument paragraph19Doc = paragraph19.getStyledDocument();
        paragraph19.setMargin(new Insets(10, 10, 10, 10));

        Style regular19 = paragraph19Doc.addStyle("regular", null);
        StyleConstants.setFontFamily(regular19, "SansSerif");

        try {
            paragraph19Doc.insertString(paragraph19Doc.getLength(), "This is a paragraph with a list:", regular19);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }

        paragraph19.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainPanel.add(paragraph19);

        // Paragraph 20 - List
        JPanel list20Panel = new JPanel();
        list20Panel.setBackground(java.awt.Color.WHITE);
        list20Panel.setLayout(new BoxLayout(list20Panel, BoxLayout.Y_AXIS));

        JLabel list20Label1 = new JLabel("•  Item 1");
        list20Label1.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 10));
        list20Panel.add(list20Label1);
        JLabel list20Label2 = new JLabel("•  Item 2");
        list20Label2.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 10));
        list20Panel.add(list20Label2);
        JLabel list20Label3 = new JLabel("•  Item 3");
        list20Label3.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 10));
        list20Panel.add(list20Label3);

        list20Panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainPanel.add(list20Panel);

        // Paragraph 21 - Paragraph
        JTextPane paragraph21 = new JTextPane();
        paragraph21.setContentType("text/html");
        paragraph21.setEditable(false);
        StyledDocument paragraph21Doc = paragraph21.getStyledDocument();
        paragraph21.setMargin(new Insets(10, 10, 10, 10));

        Style regular21 = paragraph21Doc.addStyle("regular", null);
        StyleConstants.setFontFamily(regular21, "SansSerif");

        try {
            paragraph21Doc.insertString(paragraph21Doc.getLength(), "This is a paragraph with a numbered list:",
                    regular21);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }

        paragraph21.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainPanel.add(paragraph21);

        // Paragraph 22 - List
        JPanel list22Panel = new JPanel();
        list22Panel.setBackground(java.awt.Color.WHITE);
        list22Panel.setLayout(new BoxLayout(list22Panel, BoxLayout.Y_AXIS));

        JLabel list22Label1 = new JLabel("1. Item 1");
        list22Label1.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 10));
        list22Panel.add(list22Label1);
        JLabel list22Label2 = new JLabel("2. Item 2");
        list22Label2.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 10));
        list22Panel.add(list22Label2);
        JLabel list22Label3 = new JLabel("3. Item 3");
        list22Label3.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 10));
        list22Panel.add(list22Label3);

        list22Panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainPanel.add(list22Panel);

        // Paragraph 23 - Paragraph
        JTextPane paragraph23 = new JTextPane();
        paragraph23.setContentType("text/html");
        paragraph23.setEditable(false);
        StyledDocument paragraph23Doc = paragraph23.getStyledDocument();
        paragraph23.setMargin(new Insets(10, 10, 10, 10));

        Style regular23 = paragraph23Doc.addStyle("regular", null);
        StyleConstants.setFontFamily(regular23, "SansSerif");

        try {
            paragraph23Doc.insertString(paragraph23Doc.getLength(), "This is a horizontal line:", regular23);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }

        paragraph23.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainPanel.add(paragraph23);

        // Paragraph 24 - HorizontalRule
        String horizontalRule24Html = "<html><body style='width: %1spx'>%1s</body></html>";
        JLabel horizontalRule24 = new JLabel(String.format(horizontalRule24Html, 400, ""));
        horizontalRule24.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10),
                BorderFactory.createMatteBorder(0, 0, 1, 0, java.awt.Color.GRAY)));

        horizontalRule24.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainPanel.add(horizontalRule24);

        // Paragraph 25 - Paragraph
        JTextPane paragraph25 = new JTextPane();
        paragraph25.setContentType("text/html");
        paragraph25.setEditable(false);
        StyledDocument paragraph25Doc = paragraph25.getStyledDocument();
        paragraph25.setMargin(new Insets(10, 10, 10, 10));

        paragraph25.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10),
                BorderFactory.createMatteBorder(0, 3, 0, 0, Color.DARK_GRAY)));
        Style regular25 = paragraph25Doc.addStyle("regular", null);
        StyleConstants.setFontFamily(regular25, "SansSerif");

        try {
            paragraph25Doc.insertString(paragraph25Doc.getLength(), "    This is a block quote", regular25);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }

        paragraph25.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainPanel.add(paragraph25);

        // Paragraph 26 - Paragraph
        JTextPane paragraph26 = new JTextPane();
        paragraph26.setContentType("text/html");
        paragraph26.setEditable(false);
        StyledDocument paragraph26Doc = paragraph26.getStyledDocument();
        paragraph26.setMargin(new Insets(10, 10, 10, 10));

        Style regular26 = paragraph26Doc.addStyle("regular", null);
        StyleConstants.setFontFamily(regular26, "SansSerif");

        try {
            paragraph26Doc.insertString(paragraph26Doc.getLength(), "This is a paragraph with a table:", regular26);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }

        paragraph26.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainPanel.add(paragraph26);

        // Paragraph 27 - Table
        String[] table27Columns = { "Header 1", "Header 2", "Header 3" };
        String[][] table27Rows = { { "Row 1", "Row 1", "Row 1" }, { "Row 2", "Row 2", "Row 2" },
                { "Row 3", "Row 3", "Row 3" } };
        JTable table27 = new JTable(table27Rows, table27Columns) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table27.getTableHeader().setReorderingAllowed(false);
        table27.setRowHeight(25);
        JScrollPane table27ScrollPane = new JScrollPane(table27);
        table27ScrollPane.setPreferredSize(new Dimension(400, 100));

        table27ScrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainPanel.add(table27ScrollPane);

        // Paragraph 28 - Code
        JTextArea codeBlock28TextArea = new JTextArea(
                "public class Main {\n    public static void main(String[] args) {\n        System.out.println(\"Hello, World!\");\n    }\n}");
        codeBlock28TextArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        codeBlock28TextArea.setBackground(new Color(50, 50, 50));
        codeBlock28TextArea.setForeground(Color.WHITE);
        codeBlock28TextArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JScrollPane codeBlock28ScrollPane = new JScrollPane(codeBlock28TextArea);
        codeBlock28ScrollPane.setBackground(java.awt.Color.WHITE);
        codeBlock28ScrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        codeBlock28ScrollPane.setPreferredSize(new Dimension(300, 150));

        codeBlock28ScrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainPanel.add(codeBlock28ScrollPane);

        // Paragraph 29 - Paragraph
        JTextPane paragraph29 = new JTextPane();
        paragraph29.setContentType("text/html");
        paragraph29.setEditable(false);
        StyledDocument paragraph29Doc = paragraph29.getStyledDocument();
        paragraph29.setMargin(new Insets(10, 10, 10, 10));

        Style regular29 = paragraph29Doc.addStyle("regular", null);
        StyleConstants.setFontFamily(regular29, "SansSerif");

        try {
            paragraph29Doc.insertString(paragraph29Doc.getLength(), "This is an image:", regular29);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }

        paragraph29.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainPanel.add(paragraph29);

        // Paragraph 30 - Image
        String image30Url;
        try {
            image30Url = new URL("src/main/resources/markdown.png").toString();
        } catch (MalformedURLException e) {
            image30Url = new File("src/main/resources/markdown.png").getAbsolutePath();
        }
        ImageIcon image30ImageIcon = new ImageIcon(image30Url, "small");
        int image30OriginalWidth = image30ImageIcon.getIconWidth();
        if (image30OriginalWidth > 400) {
            int image30OriginalHeight = image30ImageIcon.getIconHeight();
            int image30NewHeight = (400 * image30OriginalHeight) / image30OriginalWidth;
            Image image30ScaledImage = image30ImageIcon.getImage().getScaledInstance(400, image30NewHeight,
                    Image.SCALE_SMOOTH);
            image30ImageIcon = new ImageIcon(image30ScaledImage, "small");
        }
        JLabel image30 = new JLabel(image30ImageIcon);

        image30.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainPanel.add(image30);

        add(mainScrollPane);
        setTitle("Test");
        setSize(1800, 900);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }
}