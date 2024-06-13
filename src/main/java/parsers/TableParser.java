package parsers;

import java.awt.Dimension;
import java.lang.reflect.Array;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class TableParser implements MarkdownParser {
    String text;
    String[] columns;
    String[][] rows;

    public TableParser(String text) {
        this.text = text;
    }

    @Override
    public void parse() {
        String[] lines = this.text.split("\n");

        ArrayList<String> final_columns = new ArrayList<String>();
        String[] tmp_columns = lines[0].split("\\|");
        for (int i = 0; i < tmp_columns.length; i++) {
            if (i == 0 && tmp_columns[i].trim().isEmpty()) {
                continue;
            }
            final_columns.add(tmp_columns[i].trim());
        }
        columns = final_columns.toArray(new String[final_columns.size()]);

        ArrayList<String[]> final_rows = new ArrayList<String[]>();
        for (int i = 2; i < lines.length; i++) {
            String[] tmp_row = lines[i].split("\\|");
            ArrayList<String> final_row = new ArrayList<String>();
            for (int j = 0; j < tmp_row.length; j++) {
                if (j == 0 && tmp_row[j].trim().isEmpty()) {
                    continue;
                }
                final_row.add(tmp_row[j].trim());
            }
            final_rows.add(final_row.toArray(new String[final_row.size()]));
        }
        rows = final_rows.toArray(new String[final_rows.size()][]);
        printTable();
    }

    public void printTable() {
        System.out.println("Columns:");
        for (String column : columns) {
            System.out.println(column);
        }

        System.out.println("Rows:");
        for (String[] row : rows) {
            for (String cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println();
        }
    }

    @Override
    public String getResultPanelName(int paragraphCount) {
        return "table" + paragraphCount + "ScrollPane";
    }

    @Override
    public String toJavaSwingCode(int paragraphCount) {
        String prefix = "table" + paragraphCount;
        StringBuilder code = new StringBuilder();

        code.append("String [] " + prefix + "Columns = {");
        for (int i = 0; i < columns.length; i++) {
            code.append("\"" + columns[i] + "\"");
            if (i != columns.length - 1) {
                code.append(", ");
            }
        }
        code.append("};\n");

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

    @Override
    public JScrollPane toJavaSwingComponent() {
        JTable table = new JTable(rows, columns) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table.getTableHeader().setReorderingAllowed(false);
        table.setRowHeight(25);
        JScrollPane scrollPane = new JScrollPane(table);
        int height = 25 * (rows.length + 1);
        scrollPane.setPreferredSize(new Dimension(400, height));
        return scrollPane;
    }
}
