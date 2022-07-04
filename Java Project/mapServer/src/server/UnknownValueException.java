package server;

/**
 * Si verifica quando si sceglie un valore sconosciuto/inesistente
 * per navigare il regression tree
 */
public class UnknownValueException extends Exception{
	
	/**
	 * Richiama il costruttore della superclasse
	 * @param s Stringa da mostrare
	 */
    public UnknownValueException(String s){
        super(s);
    }

}
