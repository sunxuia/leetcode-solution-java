package util.debughelper;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class TablePrinter {

    private String[] leftAixsNames, topAixsNames;
    private int cellWidth = 0;
    private String message;
    private int textAlign = -1, verticalAlign = 1;
    private String nullStr = "null";
    private String cellInterval = " ", tableIndent = "";
    private boolean useNo = false;
    private List<Object[]> pointers = new ArrayList<>();

    public TablePrinter leftAxis(String... leftAixsNames) {
        this.leftAixsNames = leftAixsNames;
        return this;
    }

    public TablePrinter leftAxisChars(String str) {
        this.leftAixsNames = string2CharStrings(str);
        return this;
    }

    private String[] string2CharStrings(String str) {
        String[] res = new String[str.length()];
        for (int i = 0; i < res.length; i++) {
            res[i] = String.valueOf(str.charAt(i));
        }
        return res;
    }

    public TablePrinter topAxis(String... topAixsNames) {
        this.topAixsNames = topAixsNames;
        return this;
    }

    public TablePrinter topAxisChars(String str) {
        this.topAixsNames = string2CharStrings(str);
        return this;
    }

    public TablePrinter cellWidth(int cellWidth) {
        this.cellWidth = cellWidth;
        return this;
    }

    public TablePrinter textAlignLeft() {
        this.textAlign = -1;
        return this;
    }

    public TablePrinter textAlignCenter() {
        this.textAlign = 0;
        return this;
    }

    public TablePrinter textAlignRight() {
        this.textAlign = 1;
        return this;
    }

    public TablePrinter verticalAlignTop() {
        this.verticalAlign = 1;
        return this;
    }

    public TablePrinter verticalAlignMiddle() {
        this.verticalAlign = 0;
        return this;
    }

    public TablePrinter verticalAlignBottom() {
        this.verticalAlign = -1;
        return this;
    }

    public TablePrinter nullStr(String nullStr) {
        this.nullStr = nullStr;
        return this;
    }

    public TablePrinter cellInterval(int spaceCount) {
        this.cellInterval = repeatSpace(spaceCount);
        return this;
    }

    private String repeatSpace(int count) {
        StringBuilder sb = new StringBuilder(count);
        while (count-- > 0) {
            sb.append(' ');
        }
        return sb.toString();
    }

    public TablePrinter tableIndent(int spaceCount) {
        this.tableIndent = repeatSpace(spaceCount);
        return this;
    }

    public TablePrinter useNo(boolean useNo) {
        this.useNo = useNo;
        return this;
    }

    public TablePrinter setMessage(String message, Object... paras) {
        this.message = paras.length > 0 ? String.format(message, paras) : message;
        return this;
    }

    public TablePrinter addPointer(int rowIndex, int columnIndex, String name) {
        pointers.add(new Object[]{rowIndex, columnIndex, name});
        return this;
    }

    public void print(String[][] table) {
        List<List<String>> rows = makeTable(table);
        int columnCount = getColumnCount(rows);
        int[] columnWidths = new int[columnCount];
        for (int i = 0; i < columnCount; i++) {
            columnWidths[i] = formatColumn(rows, i);
        }
        List<List<String>> newRows = new ArrayList<>(rows.size());
        for (int i = 0; i < rows.size(); i++) {
            newRows.addAll(formatRow(rows.get(i), columnWidths, i));
        }

        StringBuilder sb = new StringBuilder();
        if (this.message != null) {
            sb.append(message).append("\n");
        }
        for (List<String> row : newRows) {
            sb.append(this.tableIndent);
            for (String str : row) {
                sb.append(str).append(this.cellInterval);
            }
            if (!row.isEmpty()) {
                sb.setLength(sb.length() - this.cellInterval.length());
            }
            sb.append('\n');
        }

        System.out.print(sb.toString());
    }

    private List<List<String>> makeTable(String[][] table) {
        List<List<String>> res = new ArrayList<>();
        boolean hasLeftAix = this.leftAixsNames != null;
        boolean hasTopAix = this.topAixsNames != null;
        String[] topAixs = this.topAixsNames;
        String[] leftAixs = this.leftAixsNames;
        if (!hasTopAix && this.useNo) {
            int columnCount = 0;
            for (String[] row : table) {
                columnCount = Math.max(row.length, columnCount);
            }
            topAixs = new String[columnCount];
            for (int i = 0; i < columnCount; i++) {
                topAixs[i] = String.valueOf(i);
            }
            hasTopAix = true;
        }
        if (!hasLeftAix && this.useNo) {
            leftAixs = new String[table.length];
            for (int i = 0; i < table.length; i++) {
                leftAixs[i] = String.valueOf(i);
            }
            hasLeftAix = true;
        }
        if (hasTopAix) {
            if (hasLeftAix) {
                res.add(mergeRowCells(-1, new String[]{""}, topAixs));
            } else {
                res.add(mergeRowCells(-1, topAixs));
            }
        }
        int dataRowCount = table.length;
        if (hasLeftAix) {
            dataRowCount = Math.max(dataRowCount, leftAixs.length);
        }
        for (int i = 0; i < dataRowCount; i++) {
            if (hasLeftAix) {
                String leftAixValue = i < leftAixs.length ? leftAixs[i] : "";
                res.add(mergeRowCells(i, new String[]{leftAixValue}, table[i]));
            } else {
                res.add(mergeRowCells(i, table[i]));
            }
        }
        return res;
    }

    private List<String> mergeRowCells(int rowIndex, String[]... rows) {
        List<String> res = new ArrayList<>();
        int columnIndex = hasLeftAixs() ? -2 : -1;
        for (String[] row : rows) {
            for (int i = 0; i < row.length; i++) {
                columnIndex++;
                String str = row[i];
                if (str == null) {
                    str = nullStr;
                }
                for (Object[] pointer : pointers) {
                    int x = (int) pointer[0];
                    int y = (int) pointer[1];
                    String name = (String) pointer[2];
                    if (rowIndex == x && columnIndex == y) {
                        str = str + "\n↑\n" + name;
                    }
                }
                res.add(str);
            }
        }
        return res;
    }

    private int formatColumn(List<List<String>> rows, int columnIndex) {
        int width = 1;
        if (cellWidth <= 0) {
            for (int i = 0; i < rows.size(); i++) {
                if (columnIndex < rows.get(i).size()) {
                    for (String s : rows.get(i).get(columnIndex).split("\r?\n")) {
                        width = Math.max(width, s.length());
                    }
                }
            }
        } else {
            width = cellWidth;
        }
        for (int i = 0; i < rows.size(); i++) {
            List<String> row = rows.get(i);
            if (columnIndex < row.size()) {
                // 调整坐标标题的排列方式
                int align = columnIndex == 0 && hasLeftAixs() ? -1
                        : i == 0 && hasTopAxis() ? 0 : this.textAlign;
                String cell = fitWidth(row.get(columnIndex), width, align);
                row.set(columnIndex, cell);
            }
        }
        return width;
    }

    private boolean hasLeftAixs() {
        return this.leftAixsNames != null || this.useNo;
    }

    private boolean hasTopAxis() {
        return this.topAixsNames != null || this.useNo;
    }

    private String fitWidth(String str, int width, int align) {
        if (str.isEmpty()) {
            return repeatSpace(width);
        }
        StringBuilder sb = new StringBuilder(str.length());
        for (String s : str.split("\r?\n")) {
            int repeatTimes = s.length() / width + (s.length() % width == 0 ? 0 : 1);
            for (int i = 0; i < repeatTimes; i++) {
                String sub = (i + 1) * width < s.length()
                        ? s.substring(i * width, (i + 1) * width)
                        : s.substring(i * width);
                if (align == -1) {
                    sb.append(sub).append(repeatSpace(width - sub.length()));
                } else if (align == 0) {
                    sb.append(repeatSpace((width - sub.length()) / 2))
                            .append(sub)
                            .append(repeatSpace((width - sub.length() + 1) / 2));
                } else {
                    sb.append(repeatSpace(width - sub.length())).append(sub);
                }
                sb.append("\n");
            }
        }
        if (sb.length() > 0) {
            sb.setLength(sb.length() - 1);
        }
        return sb.toString();
    }

    private int getColumnCount(List<List<String>> table) {
        int res = 0;
        for (List<String> t : table) {
            res = Math.max(res, t.size());
        }
        return res;
    }

    private List<List<String>> formatRow(List<String> row, int[] columnWidths, int rowNo) {
        int[] heights = new int[row.size()];
        int maxHeight = 1;
        for (int i = 0; i < row.size(); i++) {
            heights[i] = lines(row.get(i));
            maxHeight = Math.max(maxHeight, heights[i]);
        }
        String[][] rows = new String[maxHeight][row.size()];
        for (int i = 0; i < row.size(); i++) {
            int align = i == 0 && hasLeftAixs() || rowNo == 0 && hasTopAxis()
                    ? 0 : this.verticalAlign;
            List<String> newRows = fitRow(row.get(i), maxHeight, align, columnWidths[i]);
            for (int j = 0; j < maxHeight; j++) {
                rows[j][i] = newRows.get(j);
            }
        }
        List<List<String>> res = new ArrayList<>(maxHeight);
        for (String[] strs : rows) {
            List<String> arr = new ArrayList<>(strs.length);
            for (String str : strs) {
                arr.add(str);
            }
            res.add(arr);
        }
        return res;
    }

    private int lines(String str) {
        int rowCount = 1;
        for (int j = 0; j < str.length(); j++) {
            if (str.charAt(j) == '\n') {
                rowCount++;
            }
        }
        return rowCount;
    }

    private List<String> fitRow(String cell, int height, int align, int columnWidth) {
        String[] lines = cell.split("\n");
        List<String> res = new ArrayList<>(height);
        int topLines = 0, bottomLines = 0;
        if (this.verticalAlign == 1) {
            bottomLines = height - lines.length;
        } else if (this.verticalAlign == 0) {
            topLines = (height - lines.length) / 2;
            bottomLines = height - topLines - lines.length;
        } else {
            topLines = height - lines.length;
        }
        String spaceLine = repeatSpace(columnWidth);
        for (int i = 0; i < topLines; i++) {
            res.add(spaceLine);
        }
        for (int i = 0; i < lines.length; i++) {
            res.add(lines[i]);
        }
        for (int i = 0; i < bottomLines; i++) {
            res.add(spaceLine);
        }
        return res;
    }

    public TablePrinter printLine() {
        System.out.println();
        return this;
    }

    public TablePrinter print(boolean[][] table, Function<Boolean, String> converter) {
        String[][] strTable = new String[table.length][];
        for (int i = 0; i < table.length; i++) {
            strTable[i] = new String[table[i].length];
            for (int j = 0; j < table[i].length; j++) {
                strTable[i][j] = converter.apply(table[i][j]);
            }
        }

        print(strTable);
        return this;
    }

    public TablePrinter print(int[][] table) {
        String[][] strTable = new String[table.length][];
        for (int i = 0; i < table.length; i++) {
            strTable[i] = new String[table[i].length];
            for (int j = 0; j < table[i].length; j++) {
                strTable[i][j] = String.valueOf(table[i][j]);
            }
        }

        print(strTable);
        return this;
    }

    public TablePrinter print(char[][] table) {
        String[][] strTable = new String[table.length][];
        for (int i = 0; i < table.length; i++) {
            strTable[i] = new String[table[i].length];
            for (int j = 0; j < table[i].length; j++) {
                strTable[i][j] = String.valueOf(table[i][j]);
            }
        }

        print(strTable);
        return this;
    }

    public static TablePrinter printArray(int[] array, Object... pointersIndexString) {
        System.out.println();
        TablePrinter printer = createTablePrinterForArray(pointersIndexString);
        printer.print(new int[][]{array});
        return printer;
    }

    private static TablePrinter createTablePrinterForArray(Object... pointersIndexString) {
        TablePrinter printer = new TablePrinter();
        printer.useNo(true).textAlignCenter().leftAxisChars(" ");
        for (int i = 0; i < pointersIndexString.length; i += 2) {
            printer.addPointer(0, (int) pointersIndexString[i], (String) pointersIndexString[i + 1]);
        }
        return printer;
    }

    public static TablePrinter printString(String str, Object... pointers) {
        System.out.println();
        TablePrinter printer = createTablePrinterForArray(pointers);
        printer.print(new char[][]{str.toCharArray()});
        return printer;
    }

}
