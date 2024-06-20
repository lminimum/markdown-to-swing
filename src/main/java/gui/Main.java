// Markdown to Java Swing Converter
// Matthew Vine
// CSIS 505-B01 (Liberty University)
// July 5, 2024

package gui;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

import parsers.DocumentParser;
import parsers.TextLineNumber;

/**
 * Main class for the Markdown to Java Swing Converter GUI.
 */
public class Main extends JFrame {
    // Class level GUI components
    JTextArea textArea, displayArea;
    JPanel emptyPanel;
    JScrollPane scrollPane1, scrollPane2, scrollPane3;

    /**
     * Main method to run the application.
     * 
     * @param args Unused command-line arguments.
     */
    public static void main(String[] args) {
        new Main();
    }

    /**
     * Constructor for the Main class.
     */
    public Main() {
        // Create the main panel with spacing
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // LEFT PANEL
        textArea = new JTextArea();
        textArea.setBorder(new EmptyBorder(10, 10, 10, 10));
        textArea.setText(getExample()); // Load example text
        scrollPane1 = new JScrollPane(textArea);
        scrollPane1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        TextLineNumber tln1 = new TextLineNumber(textArea);
        scrollPane1.setRowHeaderView(tln1);

        // CENTER PANEL
        displayArea = new JTextArea();
        displayArea.setBorder(new EmptyBorder(10, 10, 10, 10));
        displayArea.setEditable(false);
        scrollPane2 = new JScrollPane(displayArea);
        scrollPane2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane2.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        TextLineNumber tln2 = new TextLineNumber(displayArea);
        scrollPane2.setRowHeaderView(tln2);

        // RIGHT PANEL
        emptyPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        emptyPanel.setBackground(Color.WHITE);
        scrollPane3 = new JScrollPane(emptyPanel);
        scrollPane3.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane3.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        // Panel to hold all three panels
        JPanel containerPanel = new JPanel();
        containerPanel.setLayout(new GridLayout(1, 3, 10, 10));
        containerPanel.add(scrollPane1);
        containerPanel.add(scrollPane2);
        containerPanel.add(scrollPane3);

        // Create buttons
        JButton convertButton = new JButton("Convert");
        convertButton.addActionListener(e -> convertButtonClicked());

        JButton loadButton = new JButton("Load File");
        loadButton.addActionListener(e -> loadButtonClicked());

        JButton copyButton = new JButton("Copy Code");
        copyButton.addActionListener(e -> copyButtonClicked());

        JButton exportButton = new JButton("Export Code");
        exportButton.addActionListener(e -> exportButtonClicked());

        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> exitButtonClicked());

        // Add buttons to a panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.add(convertButton);
        buttonPanel.add(loadButton);
        buttonPanel.add(copyButton);
        buttonPanel.add(exportButton);
        buttonPanel.add(exitButton);

        // Add panels to the main panel
        mainPanel.add(containerPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Add main panel to the main frame
        add(mainPanel);

        // Set the main frame properties
        setTitle("Markdown to Java Swing Converter");
        setSize(1800, 900);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    /**
     * Callback to convert the text in the text area to Java Swing code.
     */
    private void convertButtonClicked() {
        // Parse the text in the text area
        DocumentParser document = new DocumentParser(textArea.getText());
        document.validate();
        document.parse();

        // Convert the parsed text to Java Swing code
        String code = document.toJavaSwingCode(1);
        displayArea.setText(code);

        // Convert the parsed text to Java Swing components
        JPanel resultPanel = document.toJavaSwingComponent();
        System.out.println("Component count: " + resultPanel.getComponentCount());

        // Replace the preview and repaint to update the display
        emptyPanel.removeAll();
        emptyPanel.add(resultPanel, BorderLayout.CENTER);
        revalidate();
        repaint();

        // Scroll to the top of the text area and display area
        // Reference: https://stackoverflow.com/a/3548638/9725459
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                scrollPane2.getVerticalScrollBar().setValue(0);
                scrollPane3.getVerticalScrollBar().setValue(0);
            }
        });
    }

    /**
     * Callback to load a new file into the text area.
     */
    private void loadButtonClicked() {
        // Check if the text area is empty
        if (!textArea.getText().isEmpty()) {
            // Ask the user if they want to load a new file
            int result = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to load a new file? This will overwrite the current text.", "Load new file",
                    JOptionPane.YES_NO_OPTION);
            // If the user selects "No", return
            if (result == JOptionPane.NO_OPTION) {
                return;
            }
        }

        // Open a file chooser dialog
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);
        // Once a file is selected
        if (result == JFileChooser.APPROVE_OPTION) {
            // Get the selected file
            File file = fileChooser.getSelectedFile();
            try {
                // Read the file and set the text area
                Scanner scanner = new Scanner(file);
                StringBuilder text = new StringBuilder();
                while (scanner.hasNextLine()) {
                    text.append(scanner.nextLine()).append("\n");
                }
                textArea.setText(text.toString());
                scanner.close();

                // Scroll to the top of the text area
                // Reference: https://stackoverflow.com/a/3548638/9725459
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        scrollPane1.getVerticalScrollBar().setValue(0);
                    }
                });

                // Convert the code and update the preview
                convertButtonClicked();
            } catch (FileNotFoundException e) {
                // Display an error message if the file is not found
                JOptionPane.showMessageDialog(this, "File not found", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Callback to copy the code in the display area to the clipboard.
     */
    private void copyButtonClicked() {
        // Copy the text in the display area to the clipboard
        StringSelection stringSelection = new StringSelection(displayArea.getText());
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, stringSelection);
    }

    /**
     * Callback to export the code in the display area to a file.
     */
    private void exportButtonClicked() {
        // Open a file chooser dialog
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showSaveDialog(this);
        // Once a file is selected
        if (result == JFileChooser.APPROVE_OPTION) {
            // Get the selected file
            File file = fileChooser.getSelectedFile();
            try {
                // Write the text in the display area to the specified file
                PrintWriter writer = new PrintWriter(file);
                writer.print(displayArea.getText());
                writer.close();
            } catch (FileNotFoundException e) {
                // Display an error message if the file is not found
                JOptionPane.showMessageDialog(this, "File not found", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Callback to exit the application.
     */
    private void exitButtonClicked() {
        // Ask the user if they want to exit the application if there is text in the
        // text area
        if (!textArea.getText().isEmpty()) {
            int result = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want exit the application? You will lose any unsaved changes.", "Load new file",
                    JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.NO_OPTION) {
                // If the user selects "No", don't exit
                return;
            }
        }
        System.exit(0);
    }

    /**
     * Get an example Markdown text.
     * 
     * @return Example Markdown text.
     */
    public String getExample() {
        // Create an example Markdown document
        StringBuilder example = new StringBuilder();
        example.append("# Heading 1\n");
        example.append("This is a paragraph\n\n");
        example.append("## Heading 2\n");
        example.append("This is another paragraph\n\n");
        example.append("### Heading 3\n");
        example.append("This is a third paragraph\n\n");
        example.append("#### Heading 4\n");
        example.append("This is a fourth paragraph\n\n");
        example.append("##### Heading 5\n");
        example.append("This is a fifth paragraph\n\n");
        example.append("###### Heading 6\n");
        example.append("This is a sixth paragraph\n\n");
        example.append("This is a paragraph with **bold** and *italic* text\n\n");
        example.append(
                "This is a paragraph two different links: [example](https://www.example.com) and [google](https://www.google.com)\n\n");
        example.append("This is a paragraph with `code`\n\n");
        example.append("This is a paragraph with ~~strikethrough~~\n\n");
        example.append("This is a paragraph with ~subscript~\n\n");
        example.append("This is a paragraph with ^superscript^\n\n");
        example.append("This is a paragraph with a list:\n\n");
        example.append("- Item 1\n");
        example.append("- Item 2\n");
        example.append("- Item 3\n\n");
        example.append("This is a paragraph with a numbered list:\n\n");
        example.append("1. Item 1\n");
        example.append("2. Item 2\n");
        example.append("3. Item 3\n\n");
        example.append("This is a horizontal line:\n\n");
        example.append("---\n\n");
        example.append("> This is a block quote\n\n");
        example.append("This is a paragraph with a table:\n\n");
        example.append("| Header 1 | Header 2 | Header 3 |\n");
        example.append("|----------|----------|----------|\n");
        example.append("| Row 1 | Row 1 | Row 1 |\n");
        example.append("| Row 2 | Row 2 | Row 2 |\n");
        example.append("| Row 3 | Row 3 | Row 3 |\n\n");
        example.append("```java\n");
        example.append("public class Main {\n");
        example.append("    public static void main(String[] args) {\n");
        example.append("        System.out.println(\"Hello, World!\");\n");
        example.append("    }\n");
        example.append("}\n");
        example.append("```\n\n");
        example.append("This is an image:\n\n");
        example.append("![small](src/main/resources/markdown.png)");
        return example.toString();
    }
}
