package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Classe che gestisce i dati contenuti in una tabella
 */
public class TableData {

    private DbAccess db;

    /**
     * Costruttore di classe che avvalora db
     *
     * @param db istanza di DbAccess
     */
    public TableData(DbAccess db) {
        this.db = db;
    }

    /**
     * Imposta la query da effettuare su una specifica tabella
     *
     * @param table nome della tabella
     * @return list di Example presi dalla tabella
     * @throws SQLException eccezione riguardante problemi con il nome della tabella
     * @throws EmptySetException eccezione generata in caso di tabella vuota
     * @throws DatabaseConnectionException eccezione sollevata in caso di problemi di connessione al DB
     */
    public List<Example> getTransazioni(String table) throws SQLException, EmptySetException, DatabaseConnectionException {

        LinkedList<Example> transSet = new LinkedList<Example>();
        Statement statement;
        TableSchema tSchema = new TableSchema(db, table);

        String query = "select ";

        for (int i = 0; i < tSchema.getNumberOfAttributes(); i++) {
            Column c = tSchema.getColumn(i);
            if (i > 0)
                query += ",";
            query += c.getColumnName();
        }
        if (tSchema.getNumberOfAttributes() == 0)
            throw new SQLException();
        query += (" FROM " + table);

        statement = db.getConnection().createStatement();
        ResultSet rs = statement.executeQuery(query);
        boolean empty = true;
        while (rs.next()) {
            empty = false;
            Example currentTuple = new Example();
            for (int i = 0; i < tSchema.getNumberOfAttributes(); i++)
                if (tSchema.getColumn(i).isNumber())
                    currentTuple.add(rs.getDouble(i + 1));
                else
                    currentTuple.add(rs.getString(i + 1));
            transSet.add(currentTuple);
        }
        rs.close();
        statement.close();
        if (empty) throw new EmptySetException();

        return transSet;
    }

    /**
     * Restituisce i valori dell'attributo column
     *
     * @param table  Nome della tabella
     * @param column Colonna da interrogare
     * @return Lista dei valori di un attributo
     * @throws SQLException eccezione sollevata in caso di tabella non esistente
     */
    public Set<Object> getDistinctColumnValues(String table, Column column) throws SQLException {

        Set<Object> valueSet = new TreeSet<Object>();


        Statement stm = db.getConnection().createStatement();


        String query = "SELECT " + "DISTINCT " + column.getColumnName() + " " + " FROM " + table + " ORDER BY " + column.getColumnName() + " " + "ASC;";
        ResultSet rs = stm.executeQuery(query);

        if (column.isNumber())
            while (rs.next())
                valueSet.add(rs.getDouble(column.getColumnName()));
        else
            while (rs.next())
                valueSet.add(rs.getString(column.getColumnName()));

        db.closeConnection(); // chiudo la connessione (e rilascio automaticamente il ResultSet e la Statement)
        return valueSet;

    }

}



