package parsers;

import javax.swing.JComponent;

public interface MarkdownParser {
    public Boolean validate();

    public void parse();

    public String getResultPanelName(int paragraphCount);

    public String toJavaSwingCode(int paragraphCount);

    public JComponent toJavaSwingComponent();
}
