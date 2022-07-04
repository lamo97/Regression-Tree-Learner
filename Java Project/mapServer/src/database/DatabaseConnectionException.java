package database;

/**
 * Eccezione per gestire il caso di fallimento della connessione al DB
 */
public class DatabaseConnectionException extends Exception {

    /**
     * Invoca il costruttore della classe madre
     */
    public DatabaseConnectionException(){
        super();
    }
}
