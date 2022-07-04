package tree;


import data.Data;

import java.io.Serializable;
/**
 * Classe che modella un generico nodo (fogliare o non) dell'albero di regressione.
 */
public abstract class Node implements Serializable {

    static int idNodeCount =0;
    int idNode;
    int beginExampleIndex;
    int endExampleIndex;
    double variance;


    /**
     * Costruttore di classe. Avvalora gli attributi di classe e calcola la varianza rispetto
     * all'attributo di classe nel sotto-insieme di training ricoperto dal nodo corrente
     *
     * @param trainingSet       Oggetto di classe Data contenente il training set complessivo
     * @param beginExampleIndex Indice di inizio del sotto-insieme di training
     * @param endExampleIndex   Indice di fine del sotto-insieme di training
     */
    Node(Data trainingSet, int beginExampleIndex, int endExampleIndex){
        this.beginExampleIndex= beginExampleIndex;
        this.endExampleIndex= endExampleIndex;
        idNode= idNodeCount++;


        double somma=0;
        int contatore=0;
        double sommaScartiQuad= 0;

        for (int i=beginExampleIndex; i<=endExampleIndex; i++){
            somma = somma + trainingSet.getClassValue(i);
            double quad;
            quad=(trainingSet.getClassValue(i))*(trainingSet.getClassValue(i));
            sommaScartiQuad += quad;
            contatore++;
        }

        double quadratoSomma= somma*somma;
        double m= quadratoSomma/contatore;
        variance= sommaScartiQuad -m;


    }
    /**
     * Metodo che restituisce l'indice del nodo corrente
     *
     * @return Identificativo del nodo
     */
    public int getIdNode(){
        return idNode;
    }

    /**
     * Metodo che restituisce l'indice di inizio del sotto-insieme di training
     *
     * @return Indice del primo esempio del sotto-insieme
     */
    public int getBeginExampleIndex(){
        return beginExampleIndex;
    }

    /**
     * Metodo che restituisce l'indice di fine del sotto-insieme di training
     *
     * @return Indice dell'ultimo esempio del sotto-insieme
     */
    public int getEndExampleIndex(){
        return endExampleIndex;
    }


    /**
     * Metodo che restituisce il valore della varianza
     *
     * @return Valore della varianza rispetto al nodo corrente
     */

    public double getVariance(){
        return variance;
    }

    /**
     * Metodo astratto.
     *
     * @return Numero di figli del nodo corrente
     */
    abstract int getNumberOfChildren();

    /**
     * @return Stringa contenente tutte le informazioni relative al nodo.
     */
    @Override
    public String toString(){

        return "[Examples: " + beginExampleIndex + "-"+ endExampleIndex + "] Variance: " + variance +"; ";

    }




}
