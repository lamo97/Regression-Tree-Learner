package dataTest;

import data.Data;
import data.DiscreteAttribute;
import data.TrainingDataException;
import database.DatabaseConnectionException;
import database.EmptySetException;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;

class DiscreteAttributeTest{
    Data data;
    DiscreteAttribute att;

    /**
     * Test sul metodo che restituisce i diversi Values
     * @throws SQLException eccezione sollevata in caso di problemi con il nome della tabella
     * @throws TrainingDataException eccezione sollevata in caso di tabella vuota o numero di attribute errato
     */
    @Test
    void getNumberOfDistinctValues() throws TrainingDataException, SQLException {
        data = new Data("provaC");
        att = (DiscreteAttribute) data.getExplanatoryAttribute(0);
        assertEquals(att.getNumberOfDistinctValues(), 2);
        
    }
}