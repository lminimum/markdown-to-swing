// Markdown to Java Swing Converter
// Matthew Vine
// CSIS 505-B01 (Liberty University)
// July 5, 2024

package gui;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;
import java.net.URI;

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

        // Paragraph 1 - Paragraph
        JTextPane paragraph1 = new JTextPane();
        paragraph1.setContentType("text/html");
        paragraph1.setEditable(false);
        StyledDocument paragraph1Doc = paragraph1.getStyledDocument();
        paragraph1.setMargin(new Insets(10, 10, 10, 10));

        Style regular1 = paragraph1Doc.addStyle("regular", null);
        StyleConstants.setFontFamily(regular1, "SansSerif");

        try {
            paragraph1Doc.insertString(paragraph1Doc.getLength(), "This is a sixth paragraph", regular1);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }

        paragraph1.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainPanel.add(paragraph1);

        // Paragraph 2 - Paragraph
        JTextPane paragraph2 = new JTextPane();
        paragraph2.setContentType("text/html");
        paragraph2.setEditable(false);
        StyledDocument paragraph2Doc = paragraph2.getStyledDocument();
        paragraph2.setMargin(new Insets(10, 10, 10, 10));

        Map<Integer, String> paragraph2LinkMap = new HashMap<>();
        Style regular2 = paragraph2Doc.addStyle("regular", null);
        StyleConstants.setFontFamily(regular2, "SansSerif");
        Style linkStyle2 = paragraph2Doc.addStyle("link", regular2);
        StyleConstants.setForeground(linkStyle2, Color.BLUE);
        StyleConstants.setUnderline(linkStyle2, true);

        try {
            paragraph2Doc.insertString(paragraph2Doc.getLength(), "This is a paragraph two different links: ",
                    regular2);
            paragraph2LinkMap.put(paragraph2Doc.getLength(), "https://www.example.com");
            paragraph2Doc.insertString(paragraph2Doc.getLength(), "example", linkStyle2);
            paragraph2Doc.insertString(paragraph2Doc.getLength(), " and ", regular2);
            paragraph2LinkMap.put(paragraph2Doc.getLength(), "https://www.google.com");
            paragraph2Doc.insertString(paragraph2Doc.getLength(), "google", linkStyle2);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }

        paragraph2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    int offset = paragraph2.viewToModel2D(e.getPoint());
                    if (offset >= 0) {
                        Element element = paragraph2Doc.getCharacterElement(offset);
                        AttributeSet as = element.getAttributes();
                        if (StyleConstants.isUnderline(as)) {
                            int start = element.getStartOffset();
                            String url = paragraph2LinkMap.get(start);
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

        paragraph2.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                int offset = paragraph2.viewToModel2D(e.getPoint());
                if (offset >= 0) {
                    Element element = paragraph2Doc.getCharacterElement(offset);
                    AttributeSet as = element.getAttributes();
                    if (StyleConstants.isUnderline(as)) {
                        paragraph2.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                    } else {
                        paragraph2.setCursor(Cursor.getDefaultCursor());
                    }
                } else {
                    paragraph2.setCursor(Cursor.getDefaultCursor());
                }
            }
        });

        paragraph2.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainPanel.add(paragraph2);

        add(mainScrollPane);
        setTitle("Test");
        setSize(1800, 900);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }
}