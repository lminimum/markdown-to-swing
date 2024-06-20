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

/**
 * Parses Markdown images.
 */
public class ImageParser implements MarkdownParser {
    String text;
    String altText;
    String link;

    /**
     * Initializes a new instance of the ImageParser class.
     * 
     * @param text the image text
     */
    public ImageParser(String text) {
        this.text = text;
    }

    /**
     * Validates the image.
     * 
     * @return true if the image text is valid; otherwise, false
     */
    @Override
    public Boolean validate() {
        return this.text.trim().matches("^!\\[.*\\]\\(.*\\)$");
    }

    /**
     * Parses the image.
     */
    @Override
    public void parse() {
        String[] parts = this.text.split("\\]\\(");
        this.altText = parts[0].substring(2);
        this.link = parts[1].substring(0, parts[1].length() - 1);
    }

    /**
     * Gets the name of the result panel.
     * 
     * @param paragraphIndex The index of the paragraph.
     */
    @Override
    public String getResultPanelName(int paragraphIndex) {
        return "image" + paragraphIndex;
    }

    /**
     * Converts the image to Java Swing code.
     * 
     * @param paragraphIndex The index of the paragraph.
     */
    @Override
    public String toJavaSwingCode(int paragraphIndex) {
        // Create a unique prefix for the image
        String prefix = "image" + paragraphIndex;
        StringBuilder code = new StringBuilder();

        // Add the code using the same method as toJavaSwingComponent
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

    /**
     * Converts the image to a Java Swing component.
     * 
     * @return The Java Swing component.
     */
    @Override
    public JLabel toJavaSwingComponent() {
        // Get the URL or file path for the image
        String url;
        try {
            url = new URL(this.link).toString();
        } catch (MalformedURLException e) {
            url = new File(this.link).getAbsolutePath();
        }

        // Create the image icon
        ImageIcon imageIcon = new ImageIcon(url, this.altText);
        int originalWidth = imageIcon.getIconWidth();
        // Scale the image if it is wider than 400 pixels
        if (originalWidth > 400) {
            int originalHeight = imageIcon.getIconHeight();
            int newHeight = (400 * originalHeight) / originalWidth; // This makes it maintain aspect ratio
            Image scaledImage = imageIcon.getImage().getScaledInstance(400, newHeight, Image.SCALE_SMOOTH);
            imageIcon = new ImageIcon(scaledImage, this.altText);
        }
        // Create and return the JLabel
        JLabel label = new JLabel(imageIcon);
        return label;
    }
}
