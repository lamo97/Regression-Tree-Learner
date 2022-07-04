package databeTests;

import database.Column;
import database.DatabaseConnectionException;
import database.DbAccess;
import database.TableSchema;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class TableSchemaTest {
    DbAccess db = new DbAccess();
    TableSchema tb = new TableSchema(db, "provaC");

    TableSchemaTest() throws SQLException, DatabaseConnectionException {
    }

    /**
     *  Test sul metodo che restituisce in numero di atrributi
     */
    @Test
    void getNumberOfAttributes() {
        assertEquals(tb.getNumberOfAttributes(),3);
    }

    /**
     * Test sul metodo che restituisce la colonna
     */
    @Test
    void getColumn() {
        assertEquals(tb.getColumn(0).getColumnName(), "X");
        assertEquals(tb.getColumn(0).toString(), "X:string");
    }
}