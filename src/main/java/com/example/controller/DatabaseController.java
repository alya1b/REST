package com.example.controller;

import com.example.request.MergeTableRequest;
import com.example.request.ColumnRequest;
import com.example.request.EditCellRequest;
import com.example.component.Table;
import com.example.component.TableData;
import com.example.request.TableRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.rmi.RemoteException;
import java.util.List;

import static com.example.RestApplication.dbHandler;

@Controller
@RequestMapping("/lab")
public class DatabaseController {

    @GetMapping("/tables")
    @Operation(summary = "Get list of all tables", description = "Returns a list of all tables from the database")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list of all tables",
            content = @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = TableData.class))))
    public ResponseEntity<List<TableData>> getAllTables(){
        List<TableData> tablesData = dbHandler.getTablesData();
        return ResponseEntity.ok(tablesData);
    }

    @GetMapping("/viewTable")
    @Operation(summary = "Get details of a specific table", description = "Returns details of a table including columns and rows for a given table index")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved details of the specified table",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Table.class)))
    @ApiResponse(responseCode = "404", description = "Table not found for the given index")
    public ResponseEntity<?> viewTable(@Valid @Parameter(description = "Index of the table to retrieve") int tableIndex){
        // Assuming Table is a custom DTO that encapsulates all the details of a table
        Table tableDetails = new Table(dbHandler.getTablesData().get(tableIndex).name);
        for (int i = 0; i < dbHandler.getColumns(tableIndex).size(); i++) {
            tableDetails.addColumn(dbHandler.getColumns(tableIndex).get(i));
        }
        for (int i = 0; i < dbHandler.getRows(tableIndex).size(); i++) {
            tableDetails.addRow(dbHandler.getRows(tableIndex).get(i));
        }

        return ResponseEntity.ok(tableDetails);
    }


    @PostMapping("/addTable")
    @Operation(summary = "Add a new table", description = "Creates a new table with the given name and returns the location of the newly created table")
    @ApiResponse(responseCode = "302", description = "Table successfully created and redirect to the table view",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Table.class)))
    @ApiResponse(responseCode = "400", description = "Invalid input for table name")
    public ResponseEntity<?> addTable(@Valid @RequestBody TableRequest tableRequest){
        dbHandler.createTable(tableRequest.getName());
        Table tableDetails = new Table(dbHandler.getTablesData().get(dbHandler.getTablesData().size()-1).name);
        return ResponseEntity.ok(tableDetails);
    }


    @PostMapping("/addColumn")
    @Operation(summary = "Add a new column", description = "Adds a new column to the specified table")
    @ApiResponse(responseCode = "200", description = "Column successfully added",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Table.class)))
    public ResponseEntity<?> addColumn(@Valid @RequestBody ColumnRequest columnRequest) {
        dbHandler.addColumn(columnRequest.getTableIndex(), columnRequest.getName(), columnRequest.getColumnType());

        Table tableDetails = new Table(dbHandler.getTablesData().get(columnRequest.getTableIndex()).name);
        for (int i = 0; i < dbHandler.getColumns(columnRequest.getTableIndex()).size(); i++) {
            tableDetails.addColumn(dbHandler.getColumns(columnRequest.getTableIndex()).get(i));
        }
        for (int i = 0; i < dbHandler.getRows(columnRequest.getTableIndex()).size(); i++) {
            tableDetails.addRow(dbHandler.getRows(columnRequest.getTableIndex()).get(i));
        }
        return ResponseEntity.ok(tableDetails);
    }


    @PostMapping("/addRow")
    @Operation(summary = "Add a new row", description = "Adds a new row to the specified table")
    @ApiResponse(responseCode = "200", description = "Row added successfully",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Table.class)))
    public ResponseEntity<?> addRow(@Valid int tableIndex) throws RemoteException {
        dbHandler.addRow(tableIndex);
        Table tableDetails = new Table(dbHandler.getTablesData().get(tableIndex).name);
        for (int i = 0; i < dbHandler.getColumns(tableIndex).size(); i++) {
            tableDetails.addColumn(dbHandler.getColumns(tableIndex).get(i));
        }
        for (int i = 0; i < dbHandler.getRows(tableIndex).size(); i++) {
            tableDetails.addRow(dbHandler.getRows(tableIndex).get(i));
        }
        return ResponseEntity.ok(tableDetails);
    }


    @Transactional
    @DeleteMapping("/deleteRow")
    @Operation(summary = "Delete a row", description = "Deletes a row from the specified table")
    @ApiResponse(responseCode = "200", description = "Row deleted successfully",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Table.class)))
    public ResponseEntity<?> deleteRow(@Valid int tableIndex, @Valid int rowIndex) {
        System.out.println(dbHandler.deleteRow(tableIndex,rowIndex));
        Table tableDetails = new Table(dbHandler.getTablesData().get(tableIndex).name);
        for (int i = 0; i < dbHandler.getColumns(tableIndex).size(); i++) {
            tableDetails.addColumn(dbHandler.getColumns(tableIndex).get(i));
        }
        for (int i = 0; i < dbHandler.getRows(tableIndex).size(); i++) {
            tableDetails.addRow(dbHandler.getRows(tableIndex).get(i));
        }
        return ResponseEntity.ok(tableDetails);
    }

    @Transactional
    @DeleteMapping("/deleteColumn")
    @Operation(summary = "Delete a column", description = "Deletes a column from the specified table")
    @ApiResponse(responseCode = "200", description = "Column deleted successfully",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Table.class)))
    public ResponseEntity<?> deleteColumn(@Valid int tableIndex, @Valid int columnIndex){
        System.out.println(dbHandler.deleteColumn(tableIndex,columnIndex));
        Table tableDetails = new Table(dbHandler.getTablesData().get(tableIndex).name);
        for (int i = 0; i < dbHandler.getColumns(tableIndex).size(); i++) {
            tableDetails.addColumn(dbHandler.getColumns(tableIndex).get(i));
        }
        for (int i = 0; i < dbHandler.getRows(tableIndex).size(); i++) {
            tableDetails.addRow(dbHandler.getRows(tableIndex).get(i));
        }
        return ResponseEntity.ok(tableDetails);
    }

    @Transactional
    @DeleteMapping("/deleteTable")
    @Operation(summary = "Delete a table", description = "Deletes the specified table")
    @ApiResponse(responseCode = "200", description = "Table deleted successfully",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = List.class)))
    public ResponseEntity<?> deleteTable(@Valid int tableIndex) {
        System.out.println(dbHandler.deleteTable(tableIndex));
        List<TableData> tablesData = dbHandler.getTablesData();
        return ResponseEntity.ok(tablesData);
    }


    @PostMapping("/editCell")
    @Operation(summary = "Edit a cell", description = "Edits the value of a specified cell in a table")
    @ApiResponse(responseCode = "200", description = "Cell edited successfully",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Table.class)))
    public ResponseEntity<?> editCell(@Valid @RequestBody EditCellRequest editCellRequest) {
        if (editCellRequest.getNewValue() != null && !editCellRequest.getNewValue().trim().isEmpty()) {
            // Perform the edit operation
            dbHandler.editCell(editCellRequest.getTableIndex(), editCellRequest.getRowIndex(), editCellRequest.getColumnIndex(), editCellRequest.getNewValue());
        }
        Table tableDetails = new Table(dbHandler.getTablesData().get(editCellRequest.getTableIndex()).name);
        for (int i = 0; i < dbHandler.getColumns(editCellRequest.getTableIndex()).size(); i++) {
            tableDetails.addColumn(dbHandler.getColumns(editCellRequest.getTableIndex()).get(i));
        }
        for (int i = 0; i < dbHandler.getRows(editCellRequest.getTableIndex()).size(); i++) {
            tableDetails.addRow(dbHandler.getRows(editCellRequest.getTableIndex()).get(i));
        }
        return ResponseEntity.ok(tableDetails);
    }

    @PostMapping("/mergeTables")
    @Operation(summary = "Merge two tables", description = "Merge two tables")
    @ApiResponse(responseCode = "200", description = "Merge two tables",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Table.class)))
    public ResponseEntity<?> mergeTables(
            @RequestParam("name1") String tableName1,
            @RequestParam("name2") String tableName2
    ) {
        dbHandler.mergeTables(tableName1, tableName2);
        Table tableDetails = new Table(dbHandler.getTablesData().get(dbHandler.getTablesData().size() - 1).name);
        return ResponseEntity.ok(tableDetails);
    }

}
