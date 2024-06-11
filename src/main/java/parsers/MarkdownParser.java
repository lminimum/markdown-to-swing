package parsers;

import javax.swing.JComponent;

public interface MarkdownParser {
    public Boolean validate();

    public void parse();

    public String toJavaSwingCode();

    public JComponent toJavaSwingComponent();
}
