package parsers;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import enums.ListType;

public class ListParser implements MarkdownParser {
    private String text;
    public ListType type;
    public String[] items;

    public ListParser(String text) {
        this.text = text;
    }

    @Override
    public Boolean validate() {
        return this.text.trim().split("\n")[0].matches("^([-|\\*|\\+]|\\d[\\.|\\)])\\s.*");
    }

    @Override
    public void parse() {
        String[] lines = this.text.split("\n");

        Character firstChar = lines[0].trim().charAt(0);
        this.type = firstChar == '-' || firstChar == '*' || firstChar == '+' ? ListType.UNORDERED : ListType.ORDERED;

        this.items = new String[lines.length];
        for (int i = 0; i < lines.length; i++) {
            this.items[i] = lines[i].replaceFirst("^([-|\\*|\\+]|\\d[\\.|\\)])\\s", "");
        }
    }

    @Override
    public String toJavaSwingCode() {
        StringBuilder code = new StringBuilder();
        code.append("JPanel listPanel = new JPanel();\n");
        code.append("listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));\n");

        for (String item : this.items) {
            code.append("\nJLabel label = new JLabel(\"\\u2022 " + item + "\");\n");
            code.append("label.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 20));\n");
            code.append("listPanel.add(label);");
        }
        return code.toString();
    }

    @Override
    public JPanel toJavaSwingComponent() {
        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));

        for (String item : this.items) {
            JLabel label = new JLabel("\u2022 " + item);
            label.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 20));
            listPanel.add(label);
        }
        return listPanel;
    }
}
