package com.example.db;

import com.example.component.Column;
import com.example.component.Database;
import com.example.component.Row;
import com.example.component.Table;
import com.example.component.column.*;

public class DatabaseManager {
    private static DatabaseManager instance;
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/db";
//    public static DBMS instanceCSW;

    private DatabaseManager(){
    }

    public static DatabaseManager getInstance(){
        if (instance == null){
            instance = new DatabaseManager();
            database = new Database("Data Base");
        }
        return instance;
    }
    public static boolean isImporting;
    public static Database database;

    public static void populateTable() {
        Table table = new Table("Table1");
        table.addColumn(new TypeInteger("column1"));
        table.addColumn(new TypeCharInvl("column2"));
        table.addColumn(new TypeStringInvl("column3"));
        Row row1 = new Row();
        row1.values.add("10");
        row1.values.add("a, b");
        row1.values.add("some text");
        table.addRow(row1);
        Row row2 = new Row();
        row2.values.add("20");
        row2.values.add("c, d");
        row2.values.add("some text 2");
        table.addRow(row2);
        database.addTable(table);

        Table table2 = new Table("Table2");
        table2.addColumn(new TypeInteger("column1"));
        table2.addColumn(new TypeCharInvl("column2"));
        table2.addColumn(new TypeStringInvl("column3"));
        Row row12 = new Row();
        row12.values.add("100");
        row12.values.add("1, 2");
        row12.values.add("dif text");
        table2.addRow(row12);
        Row row22 = new Row();
        row22.values.add("15");
        row22.values.add("3, 4");
        row22.values.add("dif text 2");
        table2.addRow(row22);
        database.addTable(table2);

    }



    public void createDB(String name) {
        database = new Database(name);
    }

    public Boolean addTable(String name){
        if (name != null && !name.isEmpty()) {
            Table table = new Table(name);
            database.addTable(table);
            return true;
        }
        else {
            return false;
        }
    }

    public Boolean deleteTable(int tableIndex){

        if (tableIndex != -1) {
            database.deleteTable(tableIndex);
            return true;
        }
        else {
            return false;
        }
    }

    public static Boolean addColumn(int tableIndex, String columnName, TypeColumn typeColumn) {
        if (columnName != null && !columnName.isEmpty()) {
            if (tableIndex != -1) {

                switch (typeColumn) {
                    case INT -> {
                        Column columnInt = new TypeInteger(columnName);
                        database.tables.get(tableIndex).addColumn(columnInt);
                    }
                    case REAL -> {
                        Column columnReal = new TypeReal(columnName);
                        database.tables.get(tableIndex).addColumn(columnReal);
                    }
                    case STRING -> {
                        Column columnStr = new TypeString(columnName);
                        database.tables.get(tableIndex).addColumn(columnStr);
                    }
                    case CHAR -> {
                        Column columnChar = new TypeChar(columnName);
                        database.tables.get(tableIndex).addColumn(columnChar);
                    }
                    case CHARINVL -> {
                        Column typeHTML = new TypeCharInvl(columnName);
                        database.tables.get(tableIndex).addColumn(typeHTML);
                    }
                    case STRINGINVL -> {
                        Column typeStringInvl = new TypeStringInvl(columnName);
                        database.tables.get(tableIndex).addColumn(typeStringInvl);
                    }
                }
                for (Row row : database.tables.get(tableIndex).rows) {
                    row.values.add("");
                }
                return true;
            }
            else {
                return false;
            }
        }
        else {
            return false;
        }
    }

    public Boolean deleteColumn(int tableIndex, int columnIndex/*, CustomTableModel tableModel*/){
        if (columnIndex != -1) {
//            tableModel.removeColumn(columnIndex);
            database.tables.get(tableIndex).deleteColumn(columnIndex);
            return true;
        } else {
            return false;
        }
    }

    public Boolean addRow(int tableIndex, Row row){
        if (tableIndex != -1) {
            for (int i = row.values.size(); i < database.tables.get(tableIndex).columns.size(); i++) {
                row.values.add("");
            }
            database.tables.get(tableIndex).addRow(row);
            System.out.println(row.values);
            System.out.println(database.tables.get(tableIndex).rows.size());
            return true;
        }
        else {
            return false;
        }
    }

    public Boolean deleteRow(int tableIndex, int rowIndex/*, CustomTableModel tableModel*/){
        if (rowIndex != -1) {
//            tableModel.removeRow(rowIndex);
            database.tables.get(tableIndex).deleteRow(rowIndex);
            return true;
        }
        else {
            return false;
        }
    }

    public Boolean updateCellValue(String value, int tableIndex, int columnIndex, int rowIndex/*, CustomTable table*/){
        if (database.tables.get(tableIndex).columns.get(columnIndex).validate(value)){
            database.tables.get(tableIndex).rows.get(rowIndex).setAt(columnIndex,value.trim());
            return true;
        }
        return false;
    }

    public static Table mergeTables(String tableName1, String tableName2) {
        // Find tables by their names
        Table table1 = null;
        Table table2 = null;
        for (Table table : database.tables) {
            if (table.name.equals(tableName1)) {
                table1 = table;
            } else if (table.name.equals(tableName2)) {
                table2 = table;
            }
        }

        if (table1 == null || table2 == null) {
            // Return null if either table is not found
            return null;
        }

        // Check if tables have the same number of columns
        if (table1.columns.size() != table2.columns.size()) {
            // Return null if columns count doesn't match
            return null;
        }

        // Check if columns match by name and type
        for (int i = 0; i < table1.columns.size(); i++) {
            Column column1 = table1.columns.get(i);
            Column column2 = table2.columns.get(i);
            if (!column1.name.equals(column2.name) ||
                    !column1.getType().equals(column2.getType())) {
                // Return null if any column doesn't match
                return null;
            }
        }

        // Create a new merged table
        Table mergedTable = new Table("MergedTable");

        // Copy columns from table1 to mergedTable
        for (Column column : table1.columns) {
            mergedTable.addColumn(column);
        }

        // Merge rows by stacking them
        mergedTable.rows.addAll(table1.rows);
        mergedTable.rows.addAll(table2.rows);
        database.tables.add(mergedTable);
        return mergedTable;
    }
}
