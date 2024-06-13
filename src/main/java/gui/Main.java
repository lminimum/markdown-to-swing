package gui;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import parsers.DocumentParser;
import parsers.TextLineNumber;

public class Main extends JFrame {
    JTextArea textArea, displayArea;
    JPanel emptyPanel;
    JScrollPane scrollPane1, scrollPane2, scrollPane3;

    public static void main(String[] args) {
        new Main();
    }

    public Main() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        textArea = new JTextArea();
        textArea.setBorder(new EmptyBorder(10, 10, 10, 10));
        textArea.setText(getExample());
        scrollPane1 = new JScrollPane(textArea);
        scrollPane1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        TextLineNumber tln1 = new TextLineNumber(textArea);
        scrollPane1.setRowHeaderView(tln1);

        displayArea = new JTextArea();
        displayArea.setBorder(new EmptyBorder(10, 10, 10, 10));
        displayArea.setEditable(false);
        scrollPane2 = new JScrollPane(displayArea);
        scrollPane2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane2.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        TextLineNumber tln2 = new TextLineNumber(displayArea);
        scrollPane2.setRowHeaderView(tln2);

        emptyPanel = new JPanel();
        emptyPanel.setBackground(java.awt.Color.WHITE);
        scrollPane3 = new JScrollPane(emptyPanel);
        scrollPane3.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane3.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        JPanel containerPanel = new JPanel();
        containerPanel.setLayout(new GridLayout(1, 3, 10, 10));
        containerPanel.add(scrollPane1);
        containerPanel.add(scrollPane2);
        containerPanel.add(scrollPane3);

        JButton convertButton = new JButton("Convert to Java Swing");
        convertButton.addActionListener(e -> convertButtonClicked());

        JButton loadButton = new JButton("Load File");
        loadButton.addActionListener(e -> loadButtonClicked());

        JButton copyButton = new JButton("Copy Code");
        copyButton.addActionListener(e -> copyButtonClicked());

        JButton exportButton = new JButton("Export Code");
        exportButton.addActionListener(e -> exportButtonClicked());

        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> exitButtonClicked());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.add(convertButton);
        buttonPanel.add(loadButton);
        buttonPanel.add(copyButton);
        buttonPanel.add(exportButton);
        buttonPanel.add(exitButton);

        mainPanel.add(containerPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);

        setTitle("Markdown to Java Swing Converter");
        setSize(1800, 900);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void convertButtonClicked() {
        DocumentParser document = new DocumentParser(textArea.getText());
        document.validate();
        document.parse();

        String code = document.toJavaSwingCode(1);
        displayArea.setText(code);

        JPanel resultPanel = document.toJavaSwingComponent();
        System.out.println("Component count: " + resultPanel.getComponentCount());
        emptyPanel.removeAll();
        emptyPanel.add(resultPanel, BorderLayout.CENTER);
        revalidate();
        repaint();
        // https://stackoverflow.com/a/3548638/9725459
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                scrollPane2.getVerticalScrollBar().setValue(0);
                scrollPane3.getVerticalScrollBar().setValue(0);
            }
        });
    }

    private void loadButtonClicked() {
        if (!textArea.getText().isEmpty()) {
            int result = javax.swing.JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to load a new file? This will overwrite the current text.",
                    "Load new file", javax.swing.JOptionPane.YES_NO_OPTION);
            if (result == javax.swing.JOptionPane.NO_OPTION) {
                return;
            }
        }

        javax.swing.JFileChooser fileChooser = new javax.swing.JFileChooser();
        int result = fileChooser.showOpenDialog(this);
        if (result == javax.swing.JFileChooser.APPROVE_OPTION) {
            java.io.File file = fileChooser.getSelectedFile();
            try {
                java.util.Scanner scanner = new java.util.Scanner(file);
                StringBuilder text = new StringBuilder();
                while (scanner.hasNextLine()) {
                    text.append(scanner.nextLine()).append("\n");
                }
                textArea.setText(text.toString());
                scanner.close();
                // https://stackoverflow.com/a/3548638/9725459
                javax.swing.SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        scrollPane1.getVerticalScrollBar().setValue(0);
                    }
                });
                convertButtonClicked();
            } catch (java.io.FileNotFoundException e) {
                javax.swing.JOptionPane.showMessageDialog(this, "File not found", "Error",
                        javax.swing.JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void copyButtonClicked() {
        java.awt.datatransfer.StringSelection stringSelection = new java.awt.datatransfer.StringSelection(
                displayArea.getText());
        java.awt.datatransfer.Clipboard clipboard = java.awt.Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
    }

    private void exportButtonClicked() {
        javax.swing.JFileChooser fileChooser = new javax.swing.JFileChooser();
        int result = fileChooser.showSaveDialog(this);
        if (result == javax.swing.JFileChooser.APPROVE_OPTION) {
            java.io.File file = fileChooser.getSelectedFile();
            try {
                java.io.PrintWriter writer = new java.io.PrintWriter(file);
                writer.print(displayArea.getText());
                writer.close();
            } catch (java.io.FileNotFoundException e) {
                javax.swing.JOptionPane.showMessageDialog(this, "File not found", "Error",
                        javax.swing.JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void exitButtonClicked() {
        if (!textArea.getText().isEmpty()) {
            int result = javax.swing.JOptionPane.showConfirmDialog(this,
                    "Are you sure you want exit the application? You will lose any unsaved changes.",
                    "Load new file", javax.swing.JOptionPane.YES_NO_OPTION);
            if (result == javax.swing.JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        } else {
            System.exit(0);
        }
    }

    public String getExample() {
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
        example.append("This is a paragraph with a table:\n\n");
        // example.append("| Header 1 | Header 2 | Header 3 |\n");
        // example.append("|----------|----------|----------|\n");
        // example.append("| Row 1 | Row 1 | Row 1 |\n");
        // example.append("| Row 2 | Row 2 | Row 2 |\n");
        // example.append("| Row 3 | Row 3 | Row 3 |\n\n");
        example.append("```java\n");
        example.append("public class Main {\n");
        example.append("    public static void main(String[] args) {\n");
        example.append("        System.out.println(\"Hello, World!\");\n");
        example.append("    }\n");
        example.append("}\n");
        example.append("```\n");
        return example.toString();
    }
}
