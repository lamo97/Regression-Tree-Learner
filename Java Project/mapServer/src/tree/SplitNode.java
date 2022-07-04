package tree;

import data.Attribute;
import data.Data;
import server.UnknownValueException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Modella l'astrazione dell'entita  nodo di split (continuo o discreto).
 * Estende la classe Node
 */
public abstract class SplitNode extends Node implements Comparable<SplitNode> {

    /**
     * Classe interna che contiene tutte le informazioni riguardanti un nodo di split
     */
    class SplitInfo implements Serializable {

        Object splitValue;
        int beginIndex;
        int endIndex;
        int numberChild;
        String comparator="=";


        /**
         * Costruttore che avvalora gli attributi di classe per split a valori discreti.
         * @param splitValue Valore che definisce lo split
         * @param beginIndex Indice di inizio del sotto-insieme di training
         * @param endIndex Indice di fine del sotto-insieme di training
         * @param numberChild Identificatore dello split corrente
         */
        public SplitInfo(Object splitValue,int beginIndex,int endIndex,int numberChild){
            this.splitValue=splitValue;
            this.beginIndex=beginIndex;
            this.endIndex=endIndex;
            this.numberChild=numberChild;
        }

        /**
         * Costruttore che avvalora gli attributi di classe per split a valori continui.
         * @param splitValue Valore che definisce lo split
         * @param beginIndex Indice di inizio del sotto-insieme di training
         * @param endIndex Indice di fine del sotto-insieme di training
         * @param numberChild Identificatore dello split corrente
         * @param comparator Operatore matematico che definisce il test nel nodo corrente
         */
        public SplitInfo(Object splitValue,int beginIndex,int endIndex,int numberChild, String comparator){
            this.splitValue=splitValue;
            this.beginIndex=beginIndex;
            this.endIndex=endIndex;
            this.numberChild=numberChild;
            this.comparator=comparator;
        }


        /**
         * @return Indice di inizio del sotto-insieme di training
         */
        int getBeginindex(){
            return beginIndex;
        }

        /**
         * @return Indice di fine del sotto-insieme di training
         */
        int getEndIndex(){
            return endIndex;
        }

        /**
         * @return un Object contenente il valore dello split corrente
         */
        Object getSplitValue(){
            return splitValue;
        }


        /**
         * @return Stringa contenente i valori degli attributi di classe
         */

        @Override
        public String toString(){
            return "child " + numberChild +" split value"+comparator+splitValue + "[Examples:"+beginIndex+"-"+endIndex+"]";
        }


        /**
         * @return Stringa contenente l'operatore matematico che definisce il test
         */
        public String getComparator(){
            return comparator;
        }


    }

    private Attribute attribute;

    public List<SplitInfo> mapSplit = new ArrayList<SplitInfo>();

    double splitVariance;


    /**
     * Metodo abstract per generare le informazioni necessarie per ciascuno degli split candidati.
     * @param trainingSet Training set complessivo
     * @param beginExampleIndex Indice di inizio del sotto-insieme di training
     * @param endExampleIndex Indice di fine del sotto-insieme di training
     * @param attribute Attributo generico sul quale si definisce lo split
     */
    abstract void setSplitInfo(Data trainingSet, int beginExampleIndex, int endExampleIndex, Attribute attribute);

    /**
     * Costruttore di classe. Invoca il costruttore della superclasse, determina
     * i possibili split, calcola la varianza per l'attributo usato nello split.
     *
     * @param trainingSet Training set complessivo
     * @param beginExampleIndex Indice di inizio del sotto-insieme di training
     * @param endExampleIndex Indice di fine del sotto-insieme di training
     * @param attribute Attributo indipendente sul quale si definisce lo split
     */
    SplitNode(Data trainingSet, int beginExampleIndex, int endExampleIndex, Attribute attribute) {
        super(trainingSet, beginExampleIndex, endExampleIndex);
        this.attribute = attribute;
        trainingSet.sort(attribute, beginExampleIndex, endExampleIndex); // order by attribute
        setSplitInfo(trainingSet, beginExampleIndex, endExampleIndex, attribute);

        //compute variance
        splitVariance = 0;
        for (int i = 0; i < mapSplit.size(); i++) {
            SplitInfo splitInfo = mapSplit.get(i);
            double localVariance = new LeafNode(trainingSet, splitInfo.getBeginindex(), splitInfo.getEndIndex()).getVariance();
            splitVariance += (localVariance);
        }
    }



    /**
     * @return Attributo del nodo di split
     */
    public Attribute getAttribute(){
        return attribute;
    }

    /**
     * @return Varianza dello split corrente
     */
    public double getVariance(){
        return splitVariance;
    }


    /**
     * Implementazione da class abstract Node.
     * Ritorna il numero dei rami originanti nel nodo corrente
     *
     * @return la size di mapSplit
     */
    @Override
    public int getNumberOfChildren(){

        return mapSplit.size();
    }


    /**
     * Metodo che prende in input l'indice child e ritorna un'istanza di SplitInfo per il ramo considerato
     *
     * @param child Indice del ramo in mapSplit
     * @return Informazioni per il ramo in mapSplit indicizzato da child
     */
    SplitInfo getSplitInfo(int child){
        return mapSplit.get(child);
    }



    /**
     * Necessario per la predizione di nuovi esempi.
     * @return  stringa che riporta le informazioni concatenate relative ad ogni oggetto SplitInfo in mapsplit
     */
    public String formulateQuery(){
        String query = "";
        for(int i=0;i<mapSplit.size();i++)
            query+= (i + ":" + attribute + mapSplit.get(i).getComparator() + mapSplit.get(i).getSplitValue()) + "\n";


        return query;
    }

    /**
     * Overriding del metodo compareTo che confronta la varianza dello SplitNode o passato in input
     * con la varianza del nodo corrente
     * @param o istanze di SplitNode usata come termine di confronto
     * @return 0, 1 o -1 a seconda dell'esito del confronto
     */
    @Override
    public int compareTo(SplitNode o){
        if( this.getVariance() == (o.getVariance()) )
            return 0;
        else
        if( this.getVariance() < (o.getVariance()) )
            return -1;
        else// >
            return 1;

    }

    /**
     * @return Stringa contenente tutte le informazioni relative al nodo di split
     */
    @Override
    public String toString(){
        String v= "SPLIT : attribute=" +attribute +" "+ super.toString()+  " Split Variance: " + getVariance()+ "\n" ;

        for(int i=0;i<mapSplit.size();i++){
            v+= "\t"+mapSplit.get(i)+"\n";
        }

        return v;
    }
}
