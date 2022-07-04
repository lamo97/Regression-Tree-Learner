package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Gestisce la connessione al database MYSQL
 */
public class DbAccess {

    private final static String DRIVER_CLASS_NAME = "com.mysql.cj.jdbc.Driver";
    private final String DBMS = "jdbc:mysql";
    private final String SERVER = "localhost";
    private final String DATABASE = "MapDB";
    private final String PORT = "3306";
    private final String USER_ID = "MapUser";
    private final String PASSWORD = "map";

    private Connection conn;

    /**
     * Interfaccia il driver di MySQL con il DriverManager di JDBC e inizializza la connessione, se non e gia avvenuta
     *
     * @throws DatabaseConnectionException eccezione generata quando si verificano problemi di connessione
     */
    public void initConnection() throws DatabaseConnectionException {

        String connectionString = DBMS + "://" + SERVER + ":" + PORT + "/" + DATABASE
                + "?user=" + USER_ID + "&password=" + PASSWORD + "&serverTimezone=UTC";
        try {
            Class.forName(DRIVER_CLASS_NAME);
            conn = DriverManager.getConnection(connectionString);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("[!] Driver not found: " + e.getMessage());
            throw new DatabaseConnectionException();
        }

        System.out.println("Connection's String: " + connectionString);
    }

    /**
     * Restituisce un'istanza di Connection
     * @return Istanza di Connection
     */
    public Connection getConnection() {
        return conn;
    }

    /**
     * Chiude la connessione nel caso sia ancora aperta
     * @throws SQLException eccezione generata in caso la chiusura della connessione non vada a buon fine
     */
    public void closeConnection() throws SQLException {
        conn.close();
    }
}
