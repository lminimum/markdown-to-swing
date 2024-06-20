// Markdown to Java Swing Converter
// Matthew Vine
// CSIS 505-B01 (Liberty University)
// July 5, 2024

package parsers;

import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JScrollPane;
import javax.swing.JTable;

/**
 * Parses Markdown tables.
 */
public class TableParser implements MarkdownParser {
    String text;
    String[] columns;
    String[][] rows;

    /**
     * Initializes a new instance of the TableParser class.
     * 
     * @param text The table text.
     */
    public TableParser(String text) {
        this.text = text;
    }

    /**
     * Validates the table text.
     * 
     * @return true if the table text is valid; otherwise, false
     */
    @Override
    public Boolean validate() {
        return this.text.trim().matches("^\\|.*\\|\\s*\\n\\|.*\\|\\s*\\n\\|.*\\|\\s*\\n$");
    }

    /**
     * Parses the table text.
     */
    @Override
    public void parse() {
        // Split the table text into lines
        String[] lines = this.text.split("\n");

        // COLUMNS
        // Initialize the columns array
        ArrayList<String> final_columns = new ArrayList<String>();
        // Split the first line by the pipe character
        String[] tmp_columns = lines[0].split("\\|");
        // Add each column to the final columns array
        for (int i = 0; i < tmp_columns.length; i++) {
            // Skip the first column if it is empty (due to leading pipe character)
            if (i == 0 && tmp_columns[i].trim().isEmpty()) {
                continue;
            }
            final_columns.add(tmp_columns[i].trim());
        }
        // Convert the final columns array to a string array
        columns = final_columns.toArray(new String[final_columns.size()]);

        // ROWS
        // Initialize the rows array
        ArrayList<String[]> final_rows = new ArrayList<String[]>();
        // Iterate over each row
        for (int i = 2; i < lines.length; i++) {
            // Split the row by the pipe character
            String[] tmp_row = lines[i].split("\\|");
            // Initialize the final row array
            ArrayList<String> final_row = new ArrayList<String>();
            // Add each cell to the final row array
            for (int j = 0; j < tmp_row.length; j++) {
                // Skip the first cell if it is empty (due to leading pipe character)
                if (j == 0 && tmp_row[j].trim().isEmpty()) {
                    continue;
                }
                final_row.add(tmp_row[j].trim());
            }
            final_rows.add(final_row.toArray(new String[final_row.size()]));
        }
        // Convert the final rows array to a 2D string array
        rows = final_rows.toArray(new String[final_rows.size()][]);
    }

    /**
     * Prints the table.
     */
    public void printTable() {
        // Print the columns
        System.out.println("Columns:");
        for (String column : columns) {
            System.out.println(column);
        }

        // Print the rows
        System.out.println("Rows:");
        for (String[] row : rows) {
            for (String cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println();
        }
    }

    /**
     * Gets the name of the result panel.
     * 
     * @param paragraphIndex The index of the paragraph.
     */
    @Override
    public String getResultPanelName(int paragraphIndex) {
        return "table" + paragraphIndex + "ScrollPane";
    }

    /**
     * Converts the table to Java Swing code.
     * 
     * @param paragraphIndex The index of the paragraph.
     */
    @Override
    public String toJavaSwingCode(int paragraphIndex) {
        // Create a unique prefix for the table
        String prefix = "table" + paragraphIndex;
        StringBuilder code = new StringBuilder();

        // Add the code to create the columns array
        code.append("String [] " + prefix + "Columns = {");
        for (int i = 0; i < columns.length; i++) {
            code.append("\"" + columns[i] + "\"");
            if (i != columns.length - 1) {
                code.append(", ");
            }
        }
        code.append("};\n");

        // Add the code to create the rows array
        code.append("String [][] " + prefix + "Rows = {");
        for (int i = 0; i < rows.length; i++) {
            code.append("{");
            for (int j = 0; j < rows[i].length; j++) {
                code.append("\"" + rows[i][j] + "\"");
                if (j != rows[i].length - 1) {
                    code.append(", ");
                }
            }
            code.append("}");
            if (i != rows.length - 1) {
                code.append(", ");
            }
        }
        code.append("};\n");

        // Add the code using the same method as toJavaSwingComponent
        code.append("JTable " + prefix + " = new JTable(" + prefix + "Rows, " + prefix + "Columns) {\n");
        code.append("    public boolean isCellEditable(int row, int column) {\n");
        code.append("        return false;\n");
        code.append("    }\n");
        code.append("};\n");
        code.append(prefix + ".getTableHeader().setReorderingAllowed(false);\n");
        code.append(prefix + ".setRowHeight(25);\n");
        code.append("JScrollPane " + prefix + "ScrollPane = new JScrollPane(" + prefix + ");\n");
        int height = 25 * (rows.length + 1);
        code.append(prefix + "ScrollPane.setPreferredSize(new Dimension(400, " + height + "));\n");

        return code.toString();
    }

    /**
     * Converts the table to a Java Swing component.
     * 
     * @return The Java Swing component.
     */
    @Override
    public JScrollPane toJavaSwingComponent() {
        // Create a JTable and disable editing
        JTable table = new JTable(rows, columns) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        // Disable column reordering and set the row height
        table.getTableHeader().setReorderingAllowed(false);
        table.setRowHeight(25);
        // Create a JScrollPane for the table
        JScrollPane scrollPane = new JScrollPane(table);
        // Try to set the preferred size of the scroll pane
        int height = 25 * (rows.length + 1);
        scrollPane.setPreferredSize(new Dimension(400, height));
        return scrollPane;
    }
}
