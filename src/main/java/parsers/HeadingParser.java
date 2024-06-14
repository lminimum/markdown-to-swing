package parsers;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

public class HeadingParser implements MarkdownParser {
    public static final float[] FONT_SIZES = { 28.0f, 24.0f, 20.0f, 16.0f, 14.0f, 12.0f, 10.0f };

    public String text;
    public int level;
    public String title;

    public HeadingParser(String text) {
        this.text = text;
    }

    @Override
    public Boolean validate() {
        return this.text != null && !this.text.isEmpty();
    }

    @Override
    public void parse() {
        String[] splits = this.text.split(" ");
        this.level = splits[0].length();
        this.title = this.text.substring(this.level + 1).trim();
    }

    private float getFontSize() {
        return FONT_SIZES[Math.min(this.level - 1, FONT_SIZES.length - 1)];
    }

    @Override
    public String getResultPanelName(int paragraphIndex) {
        return "heading" + paragraphIndex + "Label";
    }

    @Override
    public String toJavaSwingCode(int paragraphIndex) {
        String prefix = "heading" + paragraphIndex;
        StringBuilder code = new StringBuilder();

        code.append(
                "String " + prefix + "Html = \"<html><body style='width: %1spx'>%1s</body></html>\";");
        code.append("JLabel " + prefix + "Label = new JLabel(String.format(" + prefix + "Html, 420, \"" + this.title
                + "\"));\n");
        code.append(prefix + "Label.setFont(" + prefix + "Label.getFont().deriveFont(" + getFontSize() + "f));");

        if (this.level <= 2) {
            code.append(
                    prefix + "Label.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10),");
            code.append("        BorderFactory.createMatteBorder(0, 0, 1, 0, java.awt.Color.GRAY)));");
        } else {
            code.append(prefix + "Label.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));");
        }

        return code.toString();
    }

    @Override
    public JLabel toJavaSwingComponent() {
        // https://stackoverflow.com/questions/7861724/is-there-a-word-wrap-property-for-jlabel
        String html = "<html><body style='width: %1spx'>%1s</body></html>";
        JLabel label = new JLabel(String.format(html, 400, this.title));
        label.setFont(label.getFont().deriveFont(getFontSize()));
        if (this.level <= 2) {
            label.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10),
                    BorderFactory.createMatteBorder(0, 0, 1, 0, java.awt.Color.GRAY)));
        } else {
            label.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        }
        return label;
    }
}
