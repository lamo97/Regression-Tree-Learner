package databeTests;

import database.*;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class ExampleTest {
    DbAccess db = new DbAccess();
    TableSchema ts = new TableSchema(db, "provaC");
    Example ex1, ex2;
    TableData td = new TableData(db);

    ExampleTest() throws SQLException, DatabaseConnectionException {
    }

    /**
     *  Test sul funzionamento del comparator
     *  @throws EmptySetException eccezione sollevata in caso di tabella vuota
     *  @throws SQLException eccezione sollevata in caso di problemi con il nome della tabella
     *  @throws DatabaseConnectionException eccezione sollevata in caso di errore di connessione
     */
    @Test
    void compareToTest() throws EmptySetException, SQLException, DatabaseConnectionException {
        ex1 = td.getTransazioni("provaC").get(4);
        ex2 = td.getTransazioni("provaC").get(7);
        assertEquals(ex1.compareTo(ex2), 1);
    }

    /**
     *  Test sulla corretta generazione della stringa
     *  @throws EmptySetException eccezione sollevata in caso di tabella vuota
     *  @throws SQLException eccezione sollevata in caso di problemi con il nome della tabella
     *  @throws DatabaseConnectionException eccezione sollevata in caso di errore di connessione
     */
    @Test
    void toStringTest() throws EmptySetException, SQLException, DatabaseConnectionException {
        ex1 = td.getTransazioni("provaC").get(4);
        assertEquals(ex1.toString(), "A 5.0 1.5 ");
    }

    /**
     *  Test sul caso di tabella vuota
     */
    @Test
    void emptyTableTest(){
        assertThrows(EmptySetException.class, ()->{
            td.getTransazioni("emptyTest");
        });
    }

    /**
     *  Test sul corretto funzionamento della connessione
     *  @throws EmptySetException eccezione sollevata in caso di tabella vuota
     *  @throws SQLException eccezione sollevata in caso di problemi con il nome della tabella
     *  @throws DatabaseConnectionException eccezione sollevata in caso di errore di connessione
     */
    @Test
    public void connectionTest() throws EmptySetException, SQLException, DatabaseConnectionException {
        assertDoesNotThrow(() -> {
            td.getTransazioni("provaC");
        });
    }
}