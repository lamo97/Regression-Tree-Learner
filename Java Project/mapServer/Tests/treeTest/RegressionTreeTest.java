package treeTest;

import data.Data;
import data.TrainingDataException;
import database.DatabaseConnectionException;
import database.EmptySetException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.*;
import server.UnknownValueException;
import tree.RegressionTree;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class RegressionTreeTest {
    RegressionTree tree;
    Data data;

    /**
     * Test sul metodo che definisce se un nodo è nodo foglia
     * @throws SQLException eccezione sollevata in caso di problemi con il nome della tabella
     * @throws TrainingDataException eccezione sollevata in caso di tabella vuota o numero di attribute errato
     */
    @Test
    void isLeafTest() throws TrainingDataException, SQLException {
        data = new Data("provaC");
        tree = new RegressionTree(data);
        assertTrue(tree.isLeaf(data, 0, 1, 1));
    }

    /**
     * Test sul metodo costruisce la stringa che rappresenta l'albero
     * @throws SQLException eccezione sollevata in caso di problemi con il nome della tabella
     * @throws TrainingDataException eccezione sollevata in caso di tabella vuota o numero di attribute errato
     */
    @Test
    void testToString() throws TrainingDataException, SQLException {
        data = new Data("provaC");
        tree = new RegressionTree(data);
        String output = "DISCRETE SPLIT : attribute=X [Examples: 0-14] Variance: 255.83333333333331;  Split Variance: 0.625\n" +
                "\tchild 0 split value=A[Examples:0-9]\n" +
                "\tchild 1 split value=B[Examples:10-14]\n\n" +
                "CONTINUOUS SPLIT : attribute=Y [Examples: 0-9] Variance: 0.625;  Split Variance: 0.0\n" +
                "\tchild 0 split value<=2.0[Examples:0-4]\n" +
                "\tchild 1 split value>2.0[Examples:5-9]\n\n" +
                "LEAF : [Examples: 0-4] Variance: 0.0;  ClassValue = 1.0\n\n" +
                "LEAF : [Examples: 5-9] Variance: 0.0;  ClassValue = 1.5\n\n" +
                "LEAF : [Examples: 10-14] Variance: 0.0;  ClassValue = 10.0\n\n";
        assertEquals(tree.toString(), output);
    }

    /**
     * Test sul metodo che mostra l'output a video
     * @throws SQLException eccezione sollevata in caso di problemi con il nome della tabella
     * @throws TrainingDataException eccezione sollevata in caso di tabella vuota o numero di attribute errato 
     */
    @Test
    public void printTreeTest() throws TrainingDataException, SQLException {
        data = new Data("provaC");
        tree = new RegressionTree(data);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        PrintStream old = System.out;
        System.setOut(ps);

        tree.printTree();

        System.out.flush();
        System.setOut(old);

        String output = "********* TREE **********\n\n" +
                "DISCRETE SPLIT : attribute=X [Examples: 0-14] Variance: 255.83333333333331;  Split Variance: 0.625\n" +
                "\tchild 0 split value=A[Examples:0-9]\n" +
                "\tchild 1 split value=B[Examples:10-14]\n\n" +
                "CONTINUOUS SPLIT : attribute=Y [Examples: 0-9] Variance: 0.625;  Split Variance: 0.0\n" +
                "\tchild 0 split value<=2.0[Examples:0-4]\n" +
                "\tchild 1 split value>2.0[Examples:5-9]\n\n" +
                "LEAF : [Examples: 0-4] Variance: 0.0;  ClassValue = 1.0\n\n" +
                "LEAF : [Examples: 5-9] Variance: 0.0;  ClassValue = 1.5\n\n" +
                "LEAF : [Examples: 10-14] Variance: 0.0;  ClassValue = 10.0\n\n\n" +
                "*************************\n\n";
        String cmp = baos.toString();

        cmp = cmp.replace("\r\n", "\n");
        assertEquals(cmp, output);

    }

    
    /**
     * Test sul metodo che serializza e salva su file
     * @throws SQLException eccezione sollevata in caso di problemi con il nome della tabella
     * @throws IOException eccezione generata da problemi di I/O
     * @throws TrainingDataException eccezione sollevata in caso di tabella vuota o numero di attribute errato
     */
    @Test
    void salva() throws IOException, TrainingDataException, SQLException {
        data = new Data("provaC");
        tree = new RegressionTree(data);
        assertDoesNotThrow(() -> {
            tree.salva("test");
        });
    }

    /**
     * Test sul metodo che carica da file
     * @throws IOException eccezione generata da problemi di I/O
     * @throws SQLException eccezione sollevata in caso di problemi con il nome della tabella
     * @throws ClassNotFoundException eccezione sollevata quando si verificano problemi con il Class Loader
     * @throws TrainingDataException eccezione sollevata in caso di tabella vuota o numero di attribute errato
     */
    @Test
    void caricaTest() throws IOException, ClassNotFoundException, TrainingDataException, SQLException {
        data = new Data("provaC");
        tree = new RegressionTree(data);
        tree.salva("test");
        assertNotNull((RegressionTree) tree.carica("test"));
    }

    /**
     * Test sul metodo che stampa l'albero in base al Node passato
     * @throws SQLException eccezione sollevata in caso di problemi con il nome della tabella
     * @throws TrainingDataException eccezione sollevata in caso di tabella vuota o numero di attribute errato
     */
    @Test
    public void printTreeTest2() throws TrainingDataException, SQLException {
        data = new Data("provaC");
        tree = new RegressionTree(data);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        PrintStream old = System.out;
        System.setOut(ps);

        tree.printTree();

        System.out.flush();
        System.setOut(old);

        String node = "LEAF : [Examples: 0-4] Variance: 0.0;  ClassValue = 1.0\n\n";
        String output = "********* TREE **********\n\n" +
                "DISCRETE SPLIT : attribute=X [Examples: 0-14] Variance: 255.83333333333331;  Split Variance: 0.625\n" +
                "\tchild 0 split value=A[Examples:0-9]\n" +
                "\tchild 1 split value=B[Examples:10-14]\n\n" +
                "CONTINUOUS SPLIT : attribute=Y [Examples: 0-9] Variance: 0.625;  Split Variance: 0.0\n" +
                "\tchild 0 split value<=2.0[Examples:0-4]\n" +
                "\tchild 1 split value>2.0[Examples:5-9]\n\n" +
                "LEAF : [Examples: 0-4] Variance: 0.0;  ClassValue = 1.0\n\n" +
                "LEAF : [Examples: 5-9] Variance: 0.0;  ClassValue = 1.5\n\n" +
                "LEAF : [Examples: 10-14] Variance: 0.0;  ClassValue = 10.0\n\n\n" +
                "*************************\n\n";

        tree.printRules(node);

        String cmp = baos.toString();

        cmp = cmp.replace("\r\n", "\n");
        assertEquals(cmp, output);
    }
    
    /**
     * Test sulla correttezza dell'output mostrato a video dalla funzione che stampa le rules
     * @throws SQLException eccezione sollevata in caso di problemi con il nome della tabella
     * @throws TrainingDataException eccezione sollevata in caso di tabella vuota o numero di attribute errato
     */
    @Test
    public void printRulesTest() throws TrainingDataException, SQLException
    {
        data = new Data("provaC");
        tree = new RegressionTree(data);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        PrintStream old = System.out;
        System.setOut(ps);

        tree.printRules();

        System.out.flush();
        System.setOut(old);

        String output = "********* RULES **********\n" + 
        		"\n" + 
        		"If(X=A) AND (Y<=2.0)==> Class = 1.0\n" + 
        		"\n" + 
        		"If(X=A) AND (Y>2.0)==> Class = 1.5\n" + 
        		"\n" + 
        		"\n" + 
        		"If(X=B)==> Class = 10.0\n" + 
        		"\n\n";

        //System.out.println("==============================");
        
        String cmp = baos.toString();
        cmp = cmp.replace("\r\n", "\n");
        assertEquals(cmp, output);   
    } 
}