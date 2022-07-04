package database;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Descrive lo schema di una tabella di un database
 */
public class TableSchema implements Iterable<Column> {

    private List<Column> tableSchema = new ArrayList<Column>();

    /**
     * Costruttore della classe
     * @param db istanza di DbAccess
     * @param tableName nome della tabella contente i valori
     * @throws SQLException eccezione sollevata in caso di problemi con la tabella
     * @throws DatabaseConnectionException eccezione sollevata in caso di errore di connessione
     */
    public TableSchema(DbAccess db, String tableName) throws SQLException, DatabaseConnectionException {

        HashMap<String, String> mapSQL_JAVATypes = new HashMap<String, String>();

        mapSQL_JAVATypes.put("CHAR", "string");
        mapSQL_JAVATypes.put("VARCHAR", "string");
        mapSQL_JAVATypes.put("LONGVARCHAR", "string");
        mapSQL_JAVATypes.put("BIT", "string");
        mapSQL_JAVATypes.put("SHORT", "number");
        mapSQL_JAVATypes.put("INT", "number");
        mapSQL_JAVATypes.put("LONG", "number");
        mapSQL_JAVATypes.put("FLOAT", "number");
        mapSQL_JAVATypes.put("DOUBLE", "number");


        db.initConnection();
        Connection con = db.getConnection();
        DatabaseMetaData meta = con.getMetaData();
        ResultSet res = meta.getColumns(null, null, tableName, null);

        while (res.next()) {

            if (mapSQL_JAVATypes.containsKey(res.getString("TYPE_NAME")))
                tableSchema.add(new Column(
                        res.getString("COLUMN_NAME"),
                        mapSQL_JAVATypes.get(res.getString("TYPE_NAME")))
                );
        }
        res.close();
    }

    /**
     * Restituisce il numero di Attribute contenuti in tableSchema
     * @return size di tableSchema
     */
    public int getNumberOfAttributes() {
        return tableSchema.size();
    }

    /**
     * Restituisce la colonna specificata da index
     * @param index indice relativo alla colonna
     * @return istanza di Column
     */
    public Column getColumn(int index) {
        return tableSchema.get(index);
    }

    /**
     * Iterator di Column
     * @return iteratore
     */
    @Override
    public Iterator<Column> iterator() {
        // TODO Auto-generated method stub
        return tableSchema.iterator();
    }

}









