package database;

/**
 * Classe che rappresenta le colonne del database
 */
public class Column{

    private String name;
    private String type;

    /**
     * Costruttore di classe
     * @param name Nome della nuova colonna
     * @param type Tipo della nuova colonna
     */
    Column(String name,String type){
        this.name=name;
        this.type=type;
    }

    /**
     * Restituisce il nome della colonna
     * @return Nome della colonna
     */
    public String getColumnName(){
        return name;
    }

    /**
     * Restituisce 'true' se la colonna e una colonna di valori numerici
     * @return true se e una colonna di valori numerici; false altrimenti;
     */
    public boolean isNumber(){
        return type.equals("number");
    }

    /**
     * @return Stringa in forma: "nome:tipo"
     */
    public String toString(){
        return name+":"+type;
    }
}