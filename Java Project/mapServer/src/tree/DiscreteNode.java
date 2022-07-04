
package tree;


import data.Attribute;
import data.DiscreteAttribute;
import data.Data;
import server.UnknownValueException;

/**
 * Estende la classe SplitNode e rappresenta un nodo corrispondente ad un attributo discreto.
 */

public class DiscreteNode extends SplitNode {

    /**
     * Costruttore di classe. Istanzia un oggetto invocando il costruttore della superclasse.
     * @param trainingSet Training set complessivo
     * @param beginExampleIndex Indice di inizio del sotto-insieme di training
     * @param endExampleIndex Indice di fine del sotto-insieme di training
     * @param attribute Attributo indipendente sul quale si definisce lo split
     */
    public DiscreteNode(Data trainingSet, int beginExampleIndex, int endExampleIndex, DiscreteAttribute attribute){
        super(trainingSet, beginExampleIndex, endExampleIndex, attribute);
    }



    /**
     * Implementazione del metodo astratto definito nella classe SplitNode.
     * Istanzia oggetti SplitInfo con ciascuno dei valori discreti del sotto-insieme di training
     * e popola l'array mapSplit[].
     * @param trainingSet Training set complessivo
     * @param beginExampleIndex Indice di inizio del sotto-insieme di training
     * @param endExampleIndex Indice di fine del sotto-insieme di training
     * @param attribute Attributo indipendente sul quale si definisce lo split
     */
    @Override
    void setSplitInfo(Data trainingSet, int beginExampleIndex, int endExampleIndex, Attribute attribute){

        Object currentSplitValue = trainingSet.getExplanatoryValue(beginExampleIndex, attribute.getIndex());
        int beginSplit = beginExampleIndex;
        int child = 0;
        for(int i = beginExampleIndex+1; i<=endExampleIndex; i++){
            if(currentSplitValue.equals(trainingSet.getExplanatoryValue(i, attribute.getIndex())) == false){// determina quando varia il valore in 'attribute'


                mapSplit.add(child, new SplitInfo(currentSplitValue, beginSplit, i-1, child ));
                currentSplitValue = trainingSet.getExplanatoryValue(i, attribute.getIndex());
                beginSplit = i;
                child++;
            }
        }
        mapSplit.add(child, new SplitInfo(currentSplitValue, beginSplit, endExampleIndex, child));

    }

    /**
     * @return Stringa contenente tutte le informazioni relative al nodo discreto
     */
    @Override
    public String toString(){
        return "DISCRETE " + super.toString();

    }
}
