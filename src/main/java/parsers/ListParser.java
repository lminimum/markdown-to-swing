// Markdown to Java Swing Converter
// Matthew Vine
// CSIS 505-B01 (Liberty University)
// July 5, 2024

package parsers;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import enums.ListType;

/**
 * Parses Markdown lists.
 */
public class ListParser implements MarkdownParser {
    private String text;
    public ListType type;
    public String[] items;

    /**
     * Initializes a new instance of the ListParser class.
     * 
     * @param text The list text.
     */
    public ListParser(String text) {
        this.text = text;
    }

    /**
     * Validates the list text.
     * 
     * @return true if the list text is valid; otherwise, false
     */
    @Override
    public Boolean validate() {
        // The first list item starts with a hyphen, asterisk, plus sign, or number
        // followed by a period and a space
        return this.text.trim().split("\n")[0].matches("^([-|\\*|\\+]|\\d[\\.|\\)])\\s.*");
    }

    /**
     * Parses the list text.
     */
    @Override
    public void parse() {
        // Split the list text into lines
        String[] lines = this.text.split("\n");

        // Determine the list type based on the first character of the first line
        Character firstChar = lines[0].trim().charAt(0);
        this.type = firstChar == '-' || firstChar == '*' || firstChar == '+' ? ListType.UNORDERED : ListType.ORDERED;

        // Create a list of items by removing the list item markers from each line
        this.items = new String[lines.length];
        for (int i = 0; i < lines.length; i++) {
            this.items[i] = lines[i].replaceFirst("^([-|\\*|\\+]|\\d[\\.|\\)])\\s", "");
        }
    }

    /**
     * Gets the bullet for the list item.
     * 
     * @param index The index of the list item.
     * @return The bullet for the list item.
     */
    private String getBullet(int index) {
        // Return the bullet based on the list type
        switch (this.type) {
            case ORDERED:
                return index + ". ";
            case UNORDERED:
                return "\u2022  ";
            default:
                return "";
        }
    }

    /**
     * Gets the name of the result panel for the list.
     * 
     * @param paragraphIndex The index of the paragraph.
     * @return The result panel name.
     */
    @Override
    public String getResultPanelName(int paragraphIndex) {
        return "list" + paragraphIndex + "Panel";
    }

    /**
     * Converts the list to Java Swing code.
     * 
     * @param paragraphIndex The index of the paragraph.
     * @return The Java Swing code.
     */
    @Override
    public String toJavaSwingCode(int paragraphIndex) {
        // Create a unique prefix for the list
        String prefix = "list" + paragraphIndex;
        StringBuilder code = new StringBuilder();

        // Add the code using the same method as toJavaSwingComponent
        code.append("JPanel " + prefix + "Panel = new JPanel();\n");
//        code.append(prefix + "Panel.setBackground(Color.WHITE);\n");
        code.append(prefix + "Panel.setLayout(new BoxLayout(" + prefix + "Panel, BoxLayout.Y_AXIS));\n\n");

        int index = 1;
        for (String item : this.items) {
            code.append("JLabel " + prefix + "Label" + index + " = new JLabel(\"" + getBullet(index) + item + "\");\n");
            code.append(prefix + "Label" + index + ".setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 10));\n");
            code.append(prefix + "Panel.add(" + prefix + "Label" + index + ");\n");
            index++;
        }

        return code.toString().replaceAll("\\n+$", "");
    }

    /**
     * Converts the list to a Java Swing component.
     * 
     * @return The Java Swing component.
     */
    @Override
    public JPanel toJavaSwingComponent() {
        // Create a panel for the list
        JPanel listPanel = new JPanel();
//        listPanel.setBackground(Color.WHITE);
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));

        // Add each list item to the panel as a label with padding
        int index = 1;
        for (String item : this.items) {
            JLabel label = new JLabel(getBullet(index) + " " + item);
            label.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 10));
            listPanel.add(label);
            index++;
        }
        return listPanel;
    }
}
