package tree;

import java.util.ArrayList;
import java.util.List;

import data.Attribute;
import data.ContinuousAttribute;
import data.Data;


/**
 * Estende la classe SplitNode e rappresenta un nodo corrispondente ad un attributo continuo.
 */

public class ContinuousNode extends SplitNode {


    /**
     * Costruttore di classe. Istanzia un oggetto invocando il costruttore della superclasse.
     *
     * @param trainingSet       Training set complessivo
     * @param beginExampleIndex Indice di inizio del sotto-insieme di training
     * @param endExampleIndex   Indice di fine del sotto-insieme di training
     * @param attribute         Attributo indipendente sul quale si definisce lo split
     */
    public ContinuousNode(Data trainingSet, int beginExampleIndex, int endExampleIndex, ContinuousAttribute attribute) {
        super(trainingSet, beginExampleIndex, endExampleIndex, attribute);
    }

    /**
     * Implementazione del metodo astratto definito nella classe SplitNode.
     * Genera le informazioni necessarie per ciascuno degli split candidati.
     *
     * @param trainingSet       Training set complessivo
     * @param beginExampleIndex Indice di inizio del sotto-insieme di training
     * @param endExampleIndex   Indice di fine del sotto-insieme di training
     * @param attribute         Attributo indipendente sul quale si definisce lo split
     */
    @Override
    void setSplitInfo(Data trainingSet, int beginExampleIndex, int endExampleIndex, Attribute attribute) {
        //Update mapSplit defined in SplitNode -- contiene gli indici del partizionamento
        Double currentSplitValue = (Double) trainingSet.getExplanatoryValue(beginExampleIndex, attribute.getIndex());
        double bestInfoVariance = 0;
        List<SplitInfo> bestMapSplit = null;

        for (int i = beginExampleIndex + 1; i <= endExampleIndex; i++) {
            Double value = (Double) trainingSet.getExplanatoryValue(i, attribute.getIndex());
            if (value.doubleValue() != currentSplitValue.doubleValue()) {
                //	System.out.print(currentSplitValue +" var ");
                double localVariance = new LeafNode(trainingSet, beginExampleIndex, i - 1).getVariance();
                double candidateSplitVariance = localVariance;
                localVariance = new LeafNode(trainingSet, i, endExampleIndex).getVariance();
                candidateSplitVariance += localVariance;
                //System.out.println(candidateSplitVariance);
                if (bestMapSplit == null) {
                    bestMapSplit = new ArrayList<SplitInfo>();
                    bestMapSplit.add(new SplitInfo(currentSplitValue, beginExampleIndex, i - 1, 0, "<="));
                    bestMapSplit.add(new SplitInfo(currentSplitValue, i, endExampleIndex, 1, ">"));
                    bestInfoVariance = candidateSplitVariance;
                } else {

                    if (candidateSplitVariance < bestInfoVariance) {
                        bestInfoVariance = candidateSplitVariance;
                        bestMapSplit.set(0, new SplitInfo(currentSplitValue, beginExampleIndex, i - 1, 0, "<="));
                        bestMapSplit.set(1, new SplitInfo(currentSplitValue, i, endExampleIndex, 1, ">"));
                    }
                }
                currentSplitValue = value;
            }
        }
        mapSplit = bestMapSplit;
        //rimuovo split inutili (che includono tutti gli esempi nella stessa partizione)

        if ((mapSplit.get(1).beginIndex == mapSplit.get(1).getEndIndex())) {
            mapSplit.remove(1);

        }
    }


    /**
     * @return Rappresentazione del nodo continuo di split
     */
    @Override
    public String toString() {
        return "CONTINUOUS " + super.toString();
    }


}
