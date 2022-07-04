package treeTest;

import data.Data;
import data.TrainingDataException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tree.LeafNode;
import tree.RegressionTree;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;

class LeafNodeTest {
    Data data;
    LeafNode lf;

    
    @BeforeEach
    public void setUp() throws TrainingDataException, SQLException {
       	data = new Data("provaC");
    	lf = new LeafNode(data, 0, 2);
    }
    
    /**
     * Test sul metodo che restituisce il Class Value
     * @throws SQLException eccezione sollevata in caso di problemi con il nome della tabella
     * @throws TrainingDataException eccezione sollevata in caso di tabella vuota o numero di attribute errato
     */
    @Test
    void getPredictedClassValue() throws TrainingDataException, SQLException {
    	
    	data.toString();
       
        assertEquals(lf.getPredictedClassValue(), 1.0);
    }

    /**
     * Test sul metodo che restituisce l'Explanatory Value
     */
    @Test
    void getNumberOfChildren() {
        assertEquals(lf.getNumberOfChildren(), 0);
    }

    /**
     * Test sul metodo che restituisce costruisce la stringa contenente il nodo
     */
    @Test
    void testToString() {
        String output = "LEAF : [Examples: 0-2] Variance: 0.0;  ClassValue = 1.0\n";
        assertEquals(lf.toString(), output);
    }
}