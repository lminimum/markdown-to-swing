// Markdown to Java Swing Converter
// Matthew Vine
// CSIS 505-B01 (Liberty University)
// July 5, 2024

package parsers;

import java.awt.Image;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class ImageParser implements MarkdownParser {
    String text;
    String altText;
    String link;

    public ImageParser(String text) {
        this.text = text;
    }

    @Override
    public Boolean validate() {
        return this.text.trim().matches("^!\\[.*\\]\\(.*\\)$");
    }

    @Override
    public void parse() {
        String[] parts = this.text.split("\\]\\(");
        this.altText = parts[0].substring(2);
        this.link = parts[1].substring(0, parts[1].length() - 1);
    }

    @Override
    public String getResultPanelName(int paragraphIndex) {
        return "image" + paragraphIndex;
    }

    @Override
    public String toJavaSwingCode(int paragraphIndex) {
        String prefix = "image" + paragraphIndex;
        StringBuilder code = new StringBuilder();

        code.append("String " + prefix + "Url;\n");
        code.append("try {\n");
        code.append("    " + prefix + "Url = new URL(\"" + this.link + "\").toString();\n");
        code.append("} catch (MalformedURLException e) {\n");
        code.append("    " + prefix + "Url = new File(\"" + this.link + "\").getAbsolutePath();\n");
        code.append("}\n");

        code.append(
                "ImageIcon " + prefix + "ImageIcon = new ImageIcon(" + prefix + "Url, \"" + this.altText + "\");\n");
        code.append("int " + prefix + "OriginalWidth = " + prefix + "ImageIcon.getIconWidth();\n");
        code.append("if (" + prefix + "OriginalWidth > 400) {\n");
        code.append("    int " + prefix + "OriginalHeight = " + prefix + "ImageIcon.getIconHeight();\n");
        code.append("    int " + prefix + "NewHeight = (400 * " + prefix + "OriginalHeight) / " + prefix
                + "OriginalWidth;\n");
        code.append("    Image " + prefix + "ScaledImage = " + prefix + "ImageIcon.getImage().getScaledInstance(400, "
                + prefix + "NewHeight, Image.SCALE_SMOOTH);\n");
        code.append(
                "    " + prefix + "ImageIcon = new ImageIcon(" + prefix + "ScaledImage, \"" + this.altText + "\");\n");
        code.append("}\n");
        code.append("JLabel " + prefix + " = new JLabel(" + prefix + "ImageIcon);\n");

        return code.toString();
    }

    @Override
    public JLabel toJavaSwingComponent() {
        String url;
        try {
            url = new URL(this.link).toString();
        } catch (MalformedURLException e) {
            url = new File(this.link).getAbsolutePath();
        }

        ImageIcon imageIcon = new ImageIcon(url, this.altText);
        int originalWidth = imageIcon.getIconWidth();
        if (originalWidth > 400) {
            int originalHeight = imageIcon.getIconHeight();
            int newHeight = (400 * originalHeight) / originalWidth;
            Image scaledImage = imageIcon.getImage().getScaledInstance(400, newHeight, Image.SCALE_SMOOTH);
            imageIcon = new ImageIcon(scaledImage, this.altText);
        }
        JLabel label = new JLabel(imageIcon);
        return label;
    }
}
