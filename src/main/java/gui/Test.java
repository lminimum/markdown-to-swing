package gui;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;

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

        Style regular14 = paragraph14Doc.addStyle("regular", null);
        StyleConstants.setFontFamily(regular14, "SansSerif");
        Style code14 = paragraph14Doc.addStyle("code", regular14);
        StyleConstants.setFontFamily(code14, "Monospaced");
        StyleConstants.setBackground(code14, new Color(240, 240, 240));

        try {
            paragraph14Doc.insertString(paragraph14Doc.getLength(), "This is a paragraph with ", regular14);
            paragraph14Doc.insertString(paragraph14Doc.getLength(), "code", code14);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }

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
        Style strikethrough15 = paragraph15Doc.addStyle("strikethrough", regular15);
        StyleConstants.setStrikeThrough(strikethrough15, true);

        try {
            paragraph15Doc.insertString(paragraph15Doc.getLength(), "This is a paragraph with ", regular15);
            paragraph15Doc.insertString(paragraph15Doc.getLength(), "strikethrough", strikethrough15);
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
        Style subscript16 = paragraph16Doc.addStyle("subscript", regular16);
        StyleConstants.setSubscript(subscript16, true);

        try {
            paragraph16Doc.insertString(paragraph16Doc.getLength(), "This is a paragraph with ", regular16);
            paragraph16Doc.insertString(paragraph16Doc.getLength(), "subscript", subscript16);
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
        Style superscript17 = paragraph17Doc.addStyle("superscript", regular17);
        StyleConstants.setSuperscript(superscript17, true);

        try {
            paragraph17Doc.insertString(paragraph17Doc.getLength(), "This is a paragraph with ", regular17);
            paragraph17Doc.insertString(paragraph17Doc.getLength(), "superscript", superscript17);
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

        try {
            paragraph18Doc.insertString(paragraph18Doc.getLength(), "This is a paragraph with a list:", regular18);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }

        paragraph18.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainPanel.add(paragraph18);

        // Paragraph 19 - List
        JPanel list19Panel = new JPanel();
        list19Panel.setBackground(java.awt.Color.WHITE);
        list19Panel.setLayout(new BoxLayout(list19Panel, BoxLayout.Y_AXIS));

        JLabel list19Label1 = new JLabel("•  Item 1");
        list19Label1.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 10));
        list19Panel.add(list19Label1);
        JLabel list19Label2 = new JLabel("•  Item 2");
        list19Label2.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 10));
        list19Panel.add(list19Label2);
        JLabel list19Label3 = new JLabel("•  Item 3");
        list19Label3.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 10));
        list19Panel.add(list19Label3);

        list19Panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainPanel.add(list19Panel);

        // Paragraph 20 - Paragraph
        JTextPane paragraph20 = new JTextPane();
        paragraph20.setContentType("text/html");
        paragraph20.setEditable(false);
        StyledDocument paragraph20Doc = paragraph20.getStyledDocument();
        paragraph20.setMargin(new Insets(10, 10, 10, 10));

        Style regular20 = paragraph20Doc.addStyle("regular", null);
        StyleConstants.setFontFamily(regular20, "SansSerif");

        try {
            paragraph20Doc.insertString(paragraph20Doc.getLength(), "This is a paragraph with a numbered list:",
                    regular20);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }

        paragraph20.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainPanel.add(paragraph20);

        // Paragraph 21 - List
        JPanel list21Panel = new JPanel();
        list21Panel.setBackground(java.awt.Color.WHITE);
        list21Panel.setLayout(new BoxLayout(list21Panel, BoxLayout.Y_AXIS));

        JLabel list21Label1 = new JLabel("1. Item 1");
        list21Label1.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 10));
        list21Panel.add(list21Label1);
        JLabel list21Label2 = new JLabel("2. Item 2");
        list21Label2.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 10));
        list21Panel.add(list21Label2);
        JLabel list21Label3 = new JLabel("3. Item 3");
        list21Label3.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 10));
        list21Panel.add(list21Label3);

        list21Panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainPanel.add(list21Panel);

        // Paragraph 22 - Code
        JTextArea codeBlock22TextArea = new JTextArea(
                "public class Main {\n    public static void main(String[] args) {\n        System.out.println(\"Hello, World!\");\n    }\n}");
        codeBlock22TextArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        codeBlock22TextArea.setBackground(new Color(50, 50, 50));
        codeBlock22TextArea.setForeground(Color.WHITE);
        codeBlock22TextArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JScrollPane codeBlock22ScrollPane = new JScrollPane(codeBlock22TextArea);
        codeBlock22ScrollPane.setBackground(java.awt.Color.WHITE);
        codeBlock22ScrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        codeBlock22ScrollPane.setPreferredSize(new Dimension(300, 150));

        codeBlock22ScrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainPanel.add(codeBlock22ScrollPane);

        add(mainScrollPane);
        setTitle("Test");
        setSize(1800, 900);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }
}