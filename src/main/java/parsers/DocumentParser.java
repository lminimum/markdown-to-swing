// Markdown to Java Swing Converter
// Matthew Vine
// CSIS 505-B01 (Liberty University)
// July 5, 2024

package parsers;

import java.util.Arrays;
import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JPanel;

/**
 * Parses full Markdown documents.
 */
public class DocumentParser implements MarkdownParser {
    private String text;
    // List of parsers
    public List<MarkdownParser> paragraphs = new ArrayList<MarkdownParser>();

    /**
     * Initializes a new instance of the DocumentParser class.
     */
    public DocumentParser(String text) {
        this.text = text;
    }

    /**
     * Validates the document text.
     * 
     * @return always true
     */
    @Override
    public Boolean validate() {
        return true;
    }

    /**
     * Parses the document text.
     */
    @Override
    public void parse() {
        // Parse paragraphs and headings out of the document text
        List<String> paragraphsAndHeadings = parseParagraphsAndHeadings();
        // Loop through each paragraph or heading, determine the type, parse it, and add
        // it to the list of paragraphs.
        for (String paragraphOrHeading : paragraphsAndHeadings) {
            if (paragraphOrHeading.trim().matches("^[-*_]+$")) {
                // HORIZONTAL RULE - Only contains one or more hyphens, asterisks, or
                // underscores
                HorizontalRuleParser hr = new HorizontalRuleParser(paragraphOrHeading);
                hr.parse();
                paragraphs.add(hr);
            } else if (paragraphOrHeading.trim().matches("^#+\\s.*")) {
                // HEADING - Starts with one or more # followed by a space
                HeadingParser heading = new HeadingParser(paragraphOrHeading);
                heading.parse();
                paragraphs.add(heading);
            } else if (paragraphOrHeading.trim().split("\n")[0].matches("^([-\\*\\+]|\\d[\\.|\\)])\\s.*")) {
                // LIST - Starts with a dash or a number followed by a period or a closing
                // parenthesis
                ListParser list = new ListParser(paragraphOrHeading);
                list.parse();
                paragraphs.add(list);
            } else if (paragraphOrHeading.startsWith("    ") || paragraphOrHeading.startsWith("```")) {
                // CODE BLOCK - Starts with four spaces or triple backticks
                String text = paragraphOrHeading;
                // If the text starts with four spaces, remove the spaces from each line
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
                // TABLE - Starts and ends with a pipe character
                TableParser table = new TableParser(paragraphOrHeading);
                table.parse();
                paragraphs.add(table);
            } else if (paragraphOrHeading.trim().matches("^\\!\\[(.*?)\\]\\((.*?)\\)$")) {
                // IMAGE - Starts with an exclamation point, square brackets, then parentheses
                ImageParser image = new ImageParser(paragraphOrHeading);
                image.parse();
                paragraphs.add(image);
            } else {
                // PARAGRAPH - Default to a paragraph
                ParagraphParser paragraph = new ParagraphParser(paragraphOrHeading);
                paragraph.parse();
                paragraphs.add(paragraph);
            }
        }
    }

    /**
     * Parses the document text into paragraphs and headings.
     * 
     * @return the list of paragraphs and headings
     */
    public List<String> parseParagraphsAndHeadings() {
        // Split by two or more newlines, by a newline followed by a hash, or by a hash
        // followed by a newline
        String[] splits = this.text.split("(?m)(?=^#)|(?<=\\n#.*\\n)|(?<=^#.*\\n)|(?<=\\n\\n+)");
        return Arrays.asList(splits).stream() // Create a stream
                .map(s -> s.replaceAll("^\\n+|\\n+$", "")) // Remove leading and trailing newlines
                .filter(s -> !s.isEmpty())// Remove empty strings
                .toList(); // Convert stream to list
    }

    /**
     * Gets the result panel name.
     * 
     * @param paragraphIndex the paragraph index
     * @return the result panel name
     */
    @Override
    public String getResultPanelName(int paragraphIndex) {
        return "mainScrollPane";
    }

    /**
     * Converts the document text to Java Swing code.
     * 
     * @param paragraphIndex the paragraph index
     * @return the Java Swing code
     */
    @Override
    public String toJavaSwingCode(int paragraphIndex) {
        StringBuilder code = new StringBuilder();

        // Main Panel and Scroll Pane
        code.append("// Main Panel\n");
        code.append("JPanel mainPanel = new JPanel();\n");
//        code.append("mainPanel.setBackground(Color.WHITE);\n");
        code.append("mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));\n\n");

        code.append("// Main Scroll Pane\n");
        code.append("JScrollPane mainScrollPane = new JScrollPane(mainPanel);\n");
        code.append("mainScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);\n");
        code.append("mainScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);\n\n");

        // Add each paragraph to the main panel
        for (MarkdownParser paragraph : paragraphs) {
            // Get the class name, Java Swing code, and panel name
            String className = paragraph.getClass().getSimpleName().replace("Parser", "");
            String paragraphCode = paragraph.toJavaSwingCode(paragraphIndex);
            String panelName = paragraph.getResultPanelName(paragraphIndex);

            // Add the paragraph code to the main panel
            code.append("// Paragraph " + paragraphIndex + " - " + className + "\n");
            code.append(paragraphCode + "\n\n");

            // Add the panel to the main panel
            code.append(panelName + ".setAlignmentX(Component.LEFT_ALIGNMENT);\n");
            code.append("mainPanel.add(" + panelName + ");\n\n");

            paragraphIndex++;
        }

        // Return the Java Swing code as a string with trailing newlines removed
        return code.toString().replaceAll("\\n+$", "") + "\n";
    }

    /**
     * Converts the document text to a Java Swing component.
     * 
     * @return the Java Swing component
     */
    @Override
    public JPanel toJavaSwingComponent() {
        // Create a main panel
        JPanel mainPanel = new JPanel();
//        mainPanel.setBackground(Color.WHITE);
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        // Add each paragraph to the main panel with left alignment
        for (MarkdownParser paragraph : paragraphs) {
            JComponent component = paragraph.toJavaSwingComponent();
            component.setAlignmentX(Component.LEFT_ALIGNMENT);
            mainPanel.add(component);
        }
        return mainPanel;
    }
}
