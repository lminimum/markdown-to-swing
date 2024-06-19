// Markdown to Java Swing Converter
// Matthew Vine
// CSIS 505-B01 (Liberty University)
// July 5, 2024
package parsers;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

public class HorizontalRuleParser implements MarkdownParser {
    private String text;

    public HorizontalRuleParser(String text) {
        this.text = text;
    }

    @Override
    public Boolean validate() {
        return this.text.trim().matches("^[-*_]+$");
    }

    @Override
    public void parse() {
    }

    @Override
    public String getResultPanelName(int paragraphIndex) {
        return "horizontalRule" + paragraphIndex;
    }

    @Override
    public String toJavaSwingCode(int paragraphIndex) {
        String prefix = "horizontalRule" + paragraphIndex;
        StringBuilder code = new StringBuilder();

        code.append("String " + prefix + "Html = \"<html><body style='width: %1spx'>%1s</body></html>\";");
        code.append("JLabel " + prefix + " = new JLabel(String.format(" + prefix + "Html, 400, \"\"));");
        code.append(prefix
                + ".setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10),");
        code.append("        BorderFactory.createMatteBorder(0, 0, 1, 0, java.awt.Color.GRAY)));");

        return code.toString();
    }

    @Override
    public JLabel toJavaSwingComponent() {
        String html = "<html><body style='width: %1spx'>%1s</body></html>";
        JLabel label = new JLabel(String.format(html, 400, ""));
        label.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(10, 10, 10, 10),
                BorderFactory.createMatteBorder(0, 0, 1, 0, java.awt.Color.GRAY)));
        return label;
    }
}
