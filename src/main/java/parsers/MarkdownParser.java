package parsers;

import javax.swing.JComponent;

public interface MarkdownParser {
    public Boolean validate();

    public void parse();

    public String getResultPanelName(int paragraphIndex);

    public String toJavaSwingCode(int paragraphIndex);

    public JComponent toJavaSwingComponent();
}
