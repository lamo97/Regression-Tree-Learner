package data;
import java.io.Serializable;

/**
 * Classe astratta che modella un generico attributo discreto o continuo
 */
public abstract class Attribute implements Serializable{

    protected String name;
    protected int index;

    /**
     * Costruttore di Attribute. Inizializza i valori degli attributi.
     * @param name  nome simbolico dell'Attribute
     * @param index identificativo numerico dell'attributo
     */
    Attribute(String name, int index){
        this.name = name;
        this.index = index;
    }

    /**
     * Restituisce il nome dell'Attribute
     * @return  Nome simbolico dell'Attribute
     */
    public String getName(){
        return name;
    }

    /**
     * Restituisce il valore di index
     * @return  Identificativo numerico dell'Attribute
     */
    public int getIndex(){
        return index;
    }


    /**
     * @return Stringa contenente il nome dell'attributo
     */
    @Override
    public String toString(){
        return getName();
    }

}
