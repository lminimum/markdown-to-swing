package parsers;

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

    @Override
    public String toJavaSwingCode() {
        StringBuilder code = new StringBuilder();
        code.append("JLabel label = new JLabel(\"" + this.title + "\")\n");
        float fontSize = FONT_SIZES[Math.min(this.level - 1, FONT_SIZES.length - 1)];
        code.append("label.setFont(label.getFont().deriveFont(" + fontSize + "));");
        return code.toString();
    }

    @Override
    public JLabel toJavaSwingComponent() {
        JLabel label = new JLabel(this.title);
        float fontSize = FONT_SIZES[Math.min(this.level - 1, FONT_SIZES.length - 1)];
        label.setFont(label.getFont().deriveFont(fontSize));
        return label;
    }
}
