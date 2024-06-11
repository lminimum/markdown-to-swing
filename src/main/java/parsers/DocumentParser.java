package parsers;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
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
            System.out.println("\"" + paragraphOrHeading + "\"");
            if (paragraphOrHeading.trim().matches("^#+\\s.*")) { // Starts with one or more # followed by a space
                // HEADING
                HeadingParser heading = new HeadingParser(paragraphOrHeading);
                heading.parse();
                paragraphs.add(heading);
            } else if (paragraphOrHeading.trim().split("\n")[0].matches("^([-|\\*|\\+]|\\d[\\.|\\)])\\s.*")) {
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

                CodeParser codeBlock = new CodeParser(text, false);
                codeBlock.parse();
                paragraphs.add(codeBlock);
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
        String[] splits = this.text.split("(?m)(?=^#)|(?<=\\n#.*\\n)|(?<=\\n\\n+)");
        return Arrays.asList(splits).stream() // Create a stream
                .map(s -> s.replaceAll("^\\n+|\\n+$", "")) // Remove leading and trailing newlines
                .filter(s -> !s.isEmpty())// Remove empty strings
                .toList(); // Convert stream to list
    }

    @Override
    public String toJavaSwingCode() {
        StringBuilder code = new StringBuilder();
        for (MarkdownParser paragraph : paragraphs) {
            String paragraphCode = paragraph.toJavaSwingCode();
            String className = paragraph.getClass().getSimpleName().replace("Parser", "");

            code.append("# " + className + "\n");
            code.append(paragraphCode + "\n\n");
        }
        return code.toString();
    }

    @Override
    public JPanel toJavaSwingComponent() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        for (MarkdownParser paragraph : paragraphs) {
            panel.add(paragraph.toJavaSwingComponent());
        }
        return panel;
    }
}
