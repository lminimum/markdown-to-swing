// Markdown to Java Swing Converter
// Matthew Vine
// CSIS 505-B01 (Liberty University)
// July 5, 2024

package parsers;

import javax.swing.JComponent;

public interface MarkdownParser {
    public Boolean validate();

    public void parse();

    public String getResultPanelName(int paragraphIndex);

    public String toJavaSwingCode(int paragraphIndex);

    public JComponent toJavaSwingComponent();
}
