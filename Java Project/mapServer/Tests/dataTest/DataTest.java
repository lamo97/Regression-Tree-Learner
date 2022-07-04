package dataTest;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;

import org.junit.Rule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;

import data.Attribute;
import data.ContinuousAttribute;
import data.Data;
import data.TrainingDataException;
import database.DatabaseConnectionException;
import database.EmptySetException;


public class DataTest {
    String fullTable = "provaC";
    String emptyTable = "emptyTest";
    String inexistentTable = "nonExistentTable";
    String testTable = "testTable";

    static Data data;
    ContinuousAttribute att = new ContinuousAttribute("Y", 1);

    @BeforeEach
    public void setUp() throws TrainingDataException, SQLException {
        data = new Data(fullTable);
    }

    /**
     * Test sul metodo che restituisce il numero di Example
     */
    @Test
    public void getNumberOfExamplesTest() {
        assertEquals(data.getNumberOfExamples(), 15);
    }

    /**
     * Test sul metodo che restituisce il numero di Explanatory Values
     */
    @Test
    public void getNumberOfExplanatoryValuesTest() {
        assertEquals(data.getNumberOfExplanatoryAttributes(), 2);
    }

    /**
     * Test sul metodo che restituisce il Class Value
     */
    @Test
    public void getClassValueTest() {
        assertEquals(data.getClassValue(1), (Double) 1.0);
    }

    /**
     * Test sul metodo che restituisce l'Explanatory Attribute
     */
    @Test
    public void getExplanatoryAttributeTest() {
        assertEquals(data.getExplanatoryAttribute(1).getIndex(), att.getIndex());
        assertEquals(data.getExplanatoryAttribute(1).getName(), att.getName());
    }

    /**
     * Test sul metodo che restituisce l'Explanatory Value
     */
    @Test
    public void getExplanatoryValueTest() {
        assertEquals(data.getExplanatoryValue(1, 1), 2.0);
    }

    /**
     * Test nel caso di tabella vuota
     */
    @Test
    public void emptyTableTest() {
        assertThrows(TrainingDataException.class, () -> {
            new Data(emptyTable);
        });
    }
    

    /**
     * Test sul caso di un numero errato di attribute
     */
    @Test
    public void wrongNumberOfAttributesTest() {
        assertThrows(TrainingDataException.class, () -> {
            Data data = new Data("wrongTable");
        });
    }
    
    /**
     * Testa il caso nel quale venga inserito un nome di tabella errato
     * @throws SQLException eccezione sollevata in caso di problemi con il nome della tabella
     * @throws TrainingDataException eccezione sollevata in caso di tabella vuota o numero di attribute errato
     */
    @Test
    public void notExistentTable() throws TrainingDataException, SQLException {
    	assertThrows(SQLException.class, () -> {
    		Data data = new Data("ciao");
    	});
    	
    }

    /**
     * Test sulla correttezza della stringa generata
     */
    @Test
    public void toStringTest() {
        String output = "A 2.0 1.0 ,A 2.0 1.0 ,1.0\n" +
                "A 2.0 1.0 ,A 2.0 1.0 ,1.0\n" +
                "A 1.0 1.0 ,A 1.0 1.0 ,1.0\n" +
                "A 2.0 1.0 ,A 2.0 1.0 ,1.0\n" +
                "A 5.0 1.5 ,A 5.0 1.5 ,1.5\n" +
                "A 5.0 1.5 ,A 5.0 1.5 ,1.5\n" +
                "A 6.0 1.5 ,A 6.0 1.5 ,1.5\n" +
                "B 6.0 10.0 ,B 6.0 10.0 ,10.0\n" +
                "A 6.0 1.5 ,A 6.0 1.5 ,1.5\n" +
                "A 6.0 1.5 ,A 6.0 1.5 ,1.5\n" +
                "B 10.0 10.0 ,B 10.0 10.0 ,10.0\n" +
                "B 5.0 10.0 ,B 5.0 10.0 ,10.0\n" +
                "B 12.0 10.0 ,B 12.0 10.0 ,10.0\n" +
                "B 14.0 10.0 ,B 14.0 10.0 ,10.0\n" +
                "A 1.0 1.0 ,A 1.0 1.0 ,1.0\n";
        assertEquals(data.toString(), output);
    }
}