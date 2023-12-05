package com.example.db;

import com.example.component.Column;
import com.example.component.Row;
import com.example.component.Table;
import com.example.component.TableData;
import com.example.component.column.TypeColumn;

import java.rmi.Remote;
import java.util.List;

public interface DBHandler extends Remote {

  public List<Row> getRows(int tableIndex);

  public List<Column> getColumns(int tableIndex);

  public List<TableData> getTablesData();

  public Boolean createTable(String name);

  public Boolean addRow(int tableIndex);

  public Boolean addColumn(int tableIndex, String name, TypeColumn columnType);

  public Boolean deleteTable(int tableIndex);

  public Boolean deleteColumn(int tableIndex, int columnIndex);

  public Boolean deleteRow(int tableIndex, int rowIndex);

  public Boolean editCell(int tableIndex, int rowIndex, int columnIndex, String value);
  public void createTestTable();
  public Table mergeTables(String tableName1, String tableName2);


}
