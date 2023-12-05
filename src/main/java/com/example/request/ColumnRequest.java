package com.example.request;

import com.example.component.column.TypeColumn;

public class ColumnRequest {
    private String name;
    private TypeColumn columnType;
    private int tableIndex;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TypeColumn getColumnType() {
        return columnType;
    }

    public void setColumnType(TypeColumn columnType) {
        this.columnType = columnType;
    }

    public int getTableIndex() {
        return tableIndex;
    }

    public void setTableIndex(int tableIndex) {
        this.tableIndex = tableIndex;
    }

}
