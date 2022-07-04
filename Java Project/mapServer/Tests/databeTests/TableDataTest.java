package databeTests;

import data.Data;
import data.TrainingDataException;
import database.DatabaseConnectionException;
import database.DbAccess;
import database.EmptySetException;
import database.TableData;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class TableDataTest {

    /**
     * Test nel caso di tabella vuota
     * @throws EmptySetException eccezione sollevata in caso di result set vuoto
     * @throws SQLException eccezione sollevata in caso di problemi con il nome della tabella
     * @throws DatabaseConnectionException eccezione sollevata in caso di errore di connessione
     * @throws TrainingDataException eccezione sollevata in caso di tabella vuota o numero di attribute errato
     */
    @Test
    void emptyTableTest() throws TrainingDataException, EmptySetException, SQLException, DatabaseConnectionException {
        assertThrows(EmptySetException.class, () -> {
            DbAccess db = new DbAccess();
            TableData td = new TableData(db);
            td.getTransazioni("emptyTest");
        });
    }

    /**
     * Testa il caso di tabella inesistente
     * @throws EmptySetException eccezione sollevata in caso di result set vuoto
     * @throws SQLException eccezione sollevata in caso di problemi con il nome della tabella
     * @throws DatabaseConnectionException eccezione sollevata in caso di errore di connessione
     * @throws TrainingDataException eccezione sollevata in caso di tabella vuota o numero di attribute errato
     */
    @Test
    void nonExistentTableTest() throws TrainingDataException, EmptySetException, SQLException, DatabaseConnectionException {
        assertThrows(SQLException.class, () -> {
            DbAccess db = new DbAccess();
            TableData td = new TableData(db);
            td.getTransazioni("noTableTest");
        });
    }

    /**
     * Testa il caso nel quale la connessione al DB venga chiusa
     * @throws EmptySetException eccezione sollevata in caso di result set vuoto
     * @throws SQLException eccezione sollevata in caso di problemi con il nome della tabella
     * @throws DatabaseConnectionException eccezione sollevata in caso di errore di connessione
     * @throws TrainingDataException eccezione sollevata in caso di tabella vuota o numero di attribute errato
     */
    @Test
    void connectionFailTest() throws TrainingDataException, EmptySetException, SQLException, DatabaseConnectionException {
        assertThrows(NullPointerException.class, () -> {
            DbAccess db = new DbAccess();
            TableData td = new TableData(db);
            db.closeConnection();
            td.getTransazioni("provaC");
        });
    }
      
}