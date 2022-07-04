package treeTest;

import data.ContinuousAttribute;
import data.Data;
import data.TrainingDataException;
import org.junit.jupiter.api.Test;
import tree.ContinuousNode;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;

class ContinuousNodeTest {
    Data data;
    ContinuousNode node;
    ContinuousAttribute attribute;
    
    /**
     * Test sul metodo che restituisce l'ID del nodo
     * @throws SQLException eccezione sollevata in caso di problemi con il nome della tabella
     * @throws TrainingDataException eccezione sollevata in caso di tabella vuota o numero di attribute errato
     */
    @Test
    void getIdNode() throws TrainingDataException, SQLException {
        data = new Data("provaC");
        attribute = (ContinuousAttribute) data.getExplanatoryAttribute(1);
        node = new ContinuousNode(data, 0, 14, attribute);
        assertEquals(node.getIdNode(), 30);
    }

    /**
     * Test sul metodo che restituisce l'indice iniziale
     * @throws SQLException eccezione sollevata in caso di problemi con il nome della tabella
     * @throws TrainingDataException eccezione sollevata in caso di tabella vuota o numero di attribute errato
     */
    @Test
    void getBeginExampleIndex() throws TrainingDataException, SQLException {
        data = new Data("provaC");
        attribute = (ContinuousAttribute) data.getExplanatoryAttribute(1);
        node = new ContinuousNode(data, 0, 14, attribute);
        assertEquals(node.getBeginExampleIndex(), 0);
    }
    
    /**
     * Test sul metodo che restituisce l'indice finale
     * @throws SQLException eccezione sollevata in caso di problemi con il nome della tabella
     * @throws TrainingDataException eccezione sollevata in caso di tabella vuota o numero di attribute errato
     */
    @Test
    void getEndExampleIndex() throws TrainingDataException, SQLException {
        data = new Data("provaC");
        attribute = (ContinuousAttribute) data.getExplanatoryAttribute(1);
        node = new ContinuousNode(data, 0, 14, attribute);
        assertEquals(node.getEndExampleIndex(), 14);
    }
    
    /**
     * Test sul metodo che restituisce la varianza
     * @throws SQLException eccezione sollevata in caso di problemi con il nome della tabella
     * @throws TrainingDataException eccezione sollevata in caso di tabella vuota o numero di attribute errato
     */
    @Test
    void getVariance() throws TrainingDataException, SQLException {
        data = new Data("provaC");
        attribute = (ContinuousAttribute) data.getExplanatoryAttribute(1);
        node = new ContinuousNode(data, 0, 14, attribute);
        assertEquals(node.getVariance(), 128.22916666666669);
    }

    /**
     * Test sul metodo che costruisce la stringa con info sul nodo
     * @throws SQLException eccezione sollevata in caso di problemi con il nome della tabella
     * @throws TrainingDataException eccezione sollevata in caso di tabella vuota o numero di attribute errato
     */
    @Test
    void testToString() throws TrainingDataException, SQLException {
        data = new Data("provaC");
        attribute = (ContinuousAttribute) data.getExplanatoryAttribute(1);
        node = new ContinuousNode(data, 0, 14, attribute);
        String out = "CONTINUOUS SPLIT : attribute=Y [Examples: 0-14] Variance: 255.83333333333331;  Split Variance: 128.22916666666669\n" +
                "\tchild 0 split value<=6.0[Examples:0-11]\n" +
                "\tchild 1 split value>6.0[Examples:12-14]\n";

        assertEquals(node.toString(), out);
    }

}