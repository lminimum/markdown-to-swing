package parsers;

import java.util.Arrays;
import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JPanel;

public class DocumentParser implements MarkdownParser {
    private String text;
    public List<MarkdownParser> paragraphs = new ArrayList<MarkdownParser>();

    public DocumentParser() {
    }

    public DocumentParser(String text) {
        this.text = text;
    }

    public static DocumentParser fromFile(String path) {
        return new DocumentParser();
    }

    @Override
    public Boolean validate() {
        return true;
    }

    @Override
    public void parse() {
        List<String> paragraphsAndHeadings = parseParagraphsAndHeadings();
        for (String paragraphOrHeading : paragraphsAndHeadings) {
            // System.out.println("\"" + paragraphOrHeading + "\"");
            if (paragraphOrHeading.trim().matches("^#+\\s.*")) { // Starts with one or more # followed by a space
                // HEADING
                HeadingParser heading = new HeadingParser(paragraphOrHeading);
                heading.parse();
                paragraphs.add(heading);
            } else if (paragraphOrHeading.trim().split("\n")[0].matches("^([-\\*\\+]|\\d[\\.|\\)])\\s.*")) {
                // LIST
                // Starts with a dash or a number followed by a period or a parenthesis
                ListParser list = new ListParser(paragraphOrHeading);
                list.parse();
                paragraphs.add(list);
            } else if (paragraphOrHeading.startsWith("    ") || paragraphOrHeading.startsWith("```")) {
                // CODE BLOCK
                String text = paragraphOrHeading;
                if (text.startsWith("    ")) {
                    String[] splits = text.split("\n");
                    for (int i = 0; i < splits.length; i++) {
                        splits[i] = splits[i].replaceFirst("    ", "");
                    }
                    text = "```\n" + String.join("\n", splits) + "\n```";
                }

                CodeParser codeBlock = new CodeParser(text);
                codeBlock.parse();
                paragraphs.add(codeBlock);
            } else if (paragraphOrHeading.trim().split("\n")[0].matches("^\\s*\\|.*\\|\\s*$")) {
                // TABLE
                TableParser table = new TableParser(paragraphOrHeading);
                table.parse();
                paragraphs.add(table);
            } else {
                // PARAGRAPH
                ParagraphParser paragraph = new ParagraphParser(paragraphOrHeading);
                paragraph.parse();
                paragraphs.add(paragraph);
            }
        }
    }

    public List<String> parseParagraphsAndHeadings() {
        // Split by two or more newlines, by a newline followed by a hash, or by a hash
        // followed by a newline
        String[] splits = this.text.split("(?m)(?=^#)|(?<=\\n#.*\\n)|(?<=^#.*\\n)|(?<=\\n\\n+)");
        return Arrays.asList(splits).stream() // Create a stream
                .map(s -> s.replaceAll("^\\n+|\\n+$", "")) // Remove leading and trailing newlines
                .filter(s -> !s.isEmpty())// Remove empty strings
                .toList(); // Convert stream to list
    }

    @Override
    public String getResultPanelName(int paragraphCount) {
        return "mainScrollPane";
    }

    @Override
    public String toJavaSwingCode(int paragraphCount) {
        StringBuilder code = new StringBuilder();
        code.append("// Main Panel\n");
        code.append("JPanel mainPanel = new JPanel();\n");
        code.append("mainPanel.setBackground(Color.WHITE);\n");
        code.append("mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));\n\n");

        code.append("// Main Scroll Pane\n");
        code.append("JScrollPane mainScrollPane = new JScrollPane(mainPanel);\n");
        code.append("mainScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);\n");
        code.append("mainScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);\n\n");

        for (MarkdownParser paragraph : paragraphs) {
            String className = paragraph.getClass().getSimpleName().replace("Parser", "");
            String paragraphCode = paragraph.toJavaSwingCode(paragraphCount);
            String panelName = paragraph.getResultPanelName(paragraphCount);

            code.append("// Paragraph " + paragraphCount + " - " + className + "\n");
            code.append(paragraphCode + "\n\n");

            code.append(panelName + ".setAlignmentX(Component.LEFT_ALIGNMENT);\n");
            code.append("mainPanel.add(" + panelName + ");\n\n");

            paragraphCount++;
        }

        return code.toString().replaceAll("\\n+$", "") + "\n";
    }

    @Override
    public JPanel toJavaSwingComponent() {
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        // mainPanel.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));

        for (MarkdownParser paragraph : paragraphs) {
            JComponent component = paragraph.toJavaSwingComponent();
            component.setAlignmentX(Component.LEFT_ALIGNMENT);
            mainPanel.add(component);
        }
        return mainPanel;
    }
}
