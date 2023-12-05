package com.example.db;

import com.example.component.Column;
import com.example.component.Row;
import com.example.component.Table;
import com.example.component.TableData;
import com.example.component.column.TypeColumn;

import java.util.ArrayList;
import java.util.List;

public class DBHandlerImpl implements DBHandler {

  @Override
  public List<Row> getRows(int tableIndex){
    return DatabaseManager.database.tables.get(tableIndex).rows;
  }

  @Override
  public List<Column> getColumns(int tableIndex){
    return DatabaseManager.database.tables.get(tableIndex).columns;
  }

  @Override
  public List<TableData> getTablesData(){
    List<Table> tables = DatabaseManager.database.tables;
    List<TableData> names = new ArrayList<>();
    for (int i = 0; i < tables.size(); i++) {
      names.add(new TableData(tables.get(i).name,i));
    }
    return names;
  }

  @Override
  public Boolean createTable(String name){
    return DatabaseManager.getInstance().addTable(name);
  }

  @Override
  public Boolean addRow(int tableIndex){
    return DatabaseManager.getInstance().addRow(tableIndex,new Row());
  }

  @Override
  public Boolean addColumn(int tableIndex, String name, TypeColumn columnType){

    return DatabaseManager.getInstance().addColumn(tableIndex,name,columnType);
  }
  @Override
  public Boolean deleteTable(int tableIndex){
    return DatabaseManager.getInstance().deleteTable(tableIndex);
  }

  @Override
  public Boolean deleteColumn(int tableIndex, int columnIndex){
    return DatabaseManager.getInstance().deleteColumn(tableIndex,columnIndex);
  }

  @Override
  public Boolean deleteRow(int tableIndex, int rowIndex){
    return DatabaseManager.getInstance().deleteRow(tableIndex,rowIndex);
  }

  @Override
  public Boolean editCell(int tableIndex, int rowIndex, int columnIndex, String value){
    return DatabaseManager.getInstance().updateCellValue(value, tableIndex, columnIndex, rowIndex);
  }

  @Override
  public void createTestTable(){
    try {
      DatabaseManager dbManager = DatabaseManager.getInstance();
      dbManager.populateTable();
    }
    catch (Exception e){
      System.out.println(e);
    }
    System.out.println("Table created");
  }

  @Override
  public Table mergeTables(String tableName1, String tableName2){
      DatabaseManager dbManager = DatabaseManager.getInstance();
      return dbManager.mergeTables(tableName1, tableName2);
  }
}
