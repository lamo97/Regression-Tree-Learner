
package data;

/**
 * Eccezione per gestire il caso di acquisizione errata del Training set (file inesistente, schema mancante,
 * training set vuoto o training set privo di variabile target numerica)
 */
public class TrainingDataException extends Exception{
    /**
     * Invoca il costruttore della classe madre
     */
    public TrainingDataException()
    {
        super();
    }
}
