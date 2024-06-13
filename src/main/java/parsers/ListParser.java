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

    private String getBullet(int index) {
        switch (this.type) {
            case ORDERED:
                return index + ". ";
            case UNORDERED:
                return "\u2022  ";
            default:
                return "";
        }
    }

    @Override
    public String getResultPanelName(int paragraphCount) {
        return "list" + paragraphCount + "Panel";
    }

    @Override
    public String toJavaSwingCode(int paragraphCount) {
        String prefix = "list" + paragraphCount;
        StringBuilder code = new StringBuilder();

        code.append("JPanel " + prefix + "Panel = new JPanel();\n");
        code.append(prefix + "Panel.setBackground(java.awt.Color.WHITE);\n");
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

    @Override
    public JPanel toJavaSwingComponent() {
        JPanel listPanel = new JPanel();
        listPanel.setBackground(java.awt.Color.WHITE);
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));

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
