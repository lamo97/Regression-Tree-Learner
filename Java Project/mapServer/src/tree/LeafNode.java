package tree;

import data.Data;

/**
 * Estende la classe Node e modella l'entita  nodo fogliare.
 */
public class LeafNode extends Node {

    private double predictedClassValue;

    /**
     * Costruttore di classe. Istanzia un oggetto invocando il costruttore della superclasse.
     *
     * @param trainingSet       Training set complessivo
     * @param beginExampleIndex Indice di inizio del sotto-insieme di training coperto dalla foglia
     * @param endExampleIndex   Indice di fine del sotto-insieme di training coperto dalla foglia
     */
    public LeafNode(Data trainingSet, int beginExampleIndex, int endExampleIndex) {
        super(trainingSet, beginExampleIndex, endExampleIndex);


        double somma = 0;
        int contatore = 0;
        for (int i = beginExampleIndex; i <= endExampleIndex; i++) {
            somma = somma + trainingSet.getClassValue(i);
            contatore++;
        }


        predictedClassValue = somma / contatore;//media valori attributo di classe che ricadono nella partizione
    }

    /**
     * Metodo che restituisce il valore del nodo foglia corrente
     *
     * @return Valore di classe del nodo foglia corrente
     */
    public Double getPredictedClassValue() {
        return predictedClassValue;
    }

    /**
     * Implementazione del metodo astratto getNumberOfChildren della classe Node.
     *
     * @return Numero di split originanti dal nodo foglia, cioe 0
     */
    @Override
    public int getNumberOfChildren() {
        return 0;
    }

    /**
     * @return Stringa contenente tutte le informazioni relative al nodo foglia
     */
    @Override
    public String toString() {
        return "LEAF : " + super.toString() + " ClassValue = " + predictedClassValue + "\n";


    }
}
