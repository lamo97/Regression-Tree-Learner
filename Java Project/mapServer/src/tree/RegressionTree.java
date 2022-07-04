package tree;


import data.*;
import server.UnknownValueException;

import java.io.*;
import java.util.TreeSet;


/**
 * Modella l'entita  albero di decisione come insieme di sotto-alberi.
 */
public class RegressionTree implements Serializable {

    private Node root;

    private RegressionTree childTree[];

    /**
     * Costruttore di classe.
     */
    RegressionTree() {
        //non fa niente
    }

    /**
     * Costruttore di classe. Instanzia un sotto-albero dell'intero albero e avvia
     * l'apprendimento dell'albero dagli esempi di training forniti in input.
     *
     * @param trainingSet Training set complessivo
     */
    public RegressionTree(Data trainingSet) {

        learnTree(trainingSet, 0, trainingSet.getNumberOfExamples() - 1, trainingSet.getNumberOfExamples() * 10 / 100);
    }


    /**
     * verifica se il sotto-insieme corrente puo essere coperto da un nodo foglia controllando
     * che il numero di esempi del training set compresi tra end e begin sia maggiore di numberOfExamplesPerLeaf.
     *
     * @param trainingSet             Training set complessivo
     * @param begin                   Indice di inizio del sotto-insieme di training
     * @param end                     Indice di fine del sotto-insieme di training
     * @param numberOfExamplesPerLeaf Numero minimo di esempi che devono ricadere in una foglia
     * @return esito sulle condizioni per i nodi fogliari
     */
    public boolean isLeaf(Data trainingSet, int begin, int end, int numberOfExamplesPerLeaf) {

        boolean RC = true;
        if ((end - begin) > numberOfExamplesPerLeaf) {
            RC = false;
        }
        return RC;

    }

    /**
     * Metodo che per ciascun attributo indipendente, istanzia un DiscreteAttribute o un ContinuousAttribute
     * e restituisce il nodo di split migliore (quello con varianza piu piccola).
     *
     * @param trainingSet Training set complessivo
     * @param begin       Indice di inizio del sotto-insieme di training
     * @param end         Indice di fine del sotto-insieme di training
     * @return Nodo di split migliore per il sotto-insieme di training
     */
    private SplitNode determineBestSplitNode(Data trainingSet, int begin, int end) {
        TreeSet<SplitNode> ts = new TreeSet<SplitNode>();

        SplitNode currentNode = null;

        for (int i = 0; i < trainingSet.getNumberOfExplanatoryAttributes(); i++) {

            Attribute a = trainingSet.getExplanatoryAttribute(i);
            if (a instanceof DiscreteAttribute) {
                DiscreteAttribute attribute = (DiscreteAttribute) trainingSet.getExplanatoryAttribute(i);
                currentNode = new DiscreteNode(trainingSet, begin, end, attribute);
            } else {
                ContinuousAttribute attribute = (ContinuousAttribute) trainingSet.getExplanatoryAttribute(i);
                currentNode = new ContinuousNode(trainingSet, begin, end, attribute);
            }
            ts.add(currentNode);

        }

        trainingSet.sort(ts.first().getAttribute(), begin, end);

        return ts.first();
    }


    /**
     * Metodo ricorsivo che genera un sotto-albero con il sotto-insieme di input, istanziando
     * un nodo foglia oppure un nodo di split. In quest'ultimo caso determinera  il miglior nodo
     * rispetto al sotto-insieme di input e assocerÃ  ad esso un nuoco sottoalbero. Ricorsivamente per ogni oggetto RegressionTree
     * in childTree[] sara invocato il metodo per l'apprendimento su un insieme ridotto del sotto-insieme attuale.
     *
     * @param trainingSet             Training set complessivo
     * @param begin                   Indice di inizio del sotto-insieme di training
     * @param end                     Indice di fine del sotto-insieme di training
     * @param numberOfExamplesPerLeaf Numero massimo di esempi che una foglia deve contenere
     */
    private void learnTree(Data trainingSet, int begin, int end, int numberOfExamplesPerLeaf) {
        if (isLeaf(trainingSet, begin, end, numberOfExamplesPerLeaf)) {
            //determina la classe che compare piÃ¹ frequentemente nella partizione corrente
            root = new LeafNode(trainingSet, begin, end);
        } else //split node
        {
            root = determineBestSplitNode(trainingSet, begin, end);

            if (root.getNumberOfChildren() > 1) {
                childTree = new RegressionTree[root.getNumberOfChildren()];
                for (int i = 0; i < root.getNumberOfChildren(); i++) {
                    childTree[i] = new RegressionTree();
                    childTree[i].learnTree(trainingSet, ((SplitNode) root).getSplitInfo(i).beginIndex, ((SplitNode) root).getSplitInfo(i).endIndex, numberOfExamplesPerLeaf);
                }
            } else
                root = new LeafNode(trainingSet, begin, end);

        }
    }


    /**
     * @return Stringa contenente tutte le informazioni relative all'intero albero.
     */
    @Override
    public String toString() {
        String tree = root.toString() + "\n";

        if (root instanceof LeafNode) {

        } else //split node
        {
            for (int i = 0; i < childTree.length; i++)
                tree += childTree[i];
        }
        return tree;
    }

    /**
     * Invoca il metodo di classe toString() per stampare a video l'albero generato.
     */
    public void printTree() {
        System.out.println("********* TREE **********\n");
        System.out.println(toString());
        System.out.println("*************************\n");
    }

    /**
     * Scandisce ciascun ramo dell'albero completo dalla radice alla foglia concatenando le
     * informazioni dei nodi di split fino al nodo foglia.
     * stampa a video la Stringa contenente tutte le regole relative al RegressionTree considerato
     */
    public void printRules() {
        System.out.println("********* RULES **********\n");

        String acc = new String();
        String rule = "If(";
        if (root instanceof LeafNode)
            acc += "Class = " + ((LeafNode) root).getPredictedClassValue() + "\n";

        else { // radice dell'albero
            if (root instanceof DiscreteNode) {
                rule += ((SplitNode) root).getAttribute().getName() + "=";
                String current = null;
                for (int i = 0; i < root.getNumberOfChildren(); i++) {
                    current = rule + ((SplitNode) root).mapSplit.get(i).splitValue + ")";
                    childTree[i].printRules(current);
                }
            } else { // instanceof ContinuosNode
                rule += ((SplitNode) root).getAttribute().getName();
                String current = null;
                for (int i = 0; i < ((SplitNode) root).mapSplit.size(); i++) {
                    current = rule + ((SplitNode) root).mapSplit.get(i).getComparator() + ((SplitNode) root).mapSplit.get(i).splitValue + ")"; // Aggiungo a "If(Outlook=Sunny) AND (Temperature [comparator di quell'arco] "l'etichetta dell'arco +")"
                    childTree[i].printRules(current);
                }

            }
        }
        System.out.println(acc);
    }

    /**
     * Metodo ricorsivo che supporta il metodo public void printRules().
     * Concatena alle informazioni in current del precedente nodo quelle del nodo root del
     * corrente sotto-albero.
     *
     * @param current Stringa contenente tutte le informazioni relative al nodo precedente
     */
    public void printRules(String current) {
        String acc = new String();
        if (root instanceof LeafNode)
            acc += current + "==> Class = " + ((LeafNode) root).getPredictedClassValue() + "\n";
        else {
            if (root instanceof DiscreteNode) {
                current += " AND (" + ((SplitNode) root).getAttribute().getName() + "=";
                String tmp = null;
                for (int i = 0; i < ((SplitNode) root).getNumberOfChildren(); i++) {
                    tmp = current + ((SplitNode) root).mapSplit.get(i).splitValue + ")";
                    childTree[i].printRules(tmp);
                }
            } else { // instanceof ContinuosNode
                current += " AND (" + ((SplitNode) root).getAttribute().getName();
                String tmp;
                for (int i = 0; i < ((SplitNode) root).mapSplit.size(); i++) {
                    tmp = current + ((SplitNode) root).mapSplit.get(i).getComparator() + ((SplitNode) root).mapSplit.get(i).splitValue + ")";
                    childTree[i].printRules(tmp);
                }

            }
        }
        System.out.println(acc);
    }

    /**
     * Visualizza le informazioni di ciascuno split dell'albero e
     * per il corrispondente attributo acquisisce il valore dell'esempio da predire da tastiera, tramite il client.
     * Se il nodo root corrente e leaf termina l'acquisizione e visualizza la predizione, altrimenti invia la risposta "QUERY"
     * e invoca ricorsivamente sul figlio di root in childTree[] individuato dal valore acquisito da tastiera.
     * Il metodo sollevera  l'eccezione UnknownValueException se non e possibile
     * selezionare una ramo valido del nodo di split.
     *
     * @param in  inputStream
     * @param out outputStream
     * @return oggetto Double contenente il valore di classe predetto per l'esempio acquisito
     * @throws UnknownValueException eccezione sollevata se si sceglie un valore sconosciuto/inesistenteper navigare il regression tree
     * @throws IOException eccezione generata da problemi di I/O
     * @throws ClassNotFoundException eccezione sollevata in caso di problemi riguardanti il Class Loader
     */
    public Double predictClass(ObjectInputStream in, ObjectOutputStream out) throws UnknownValueException, IOException, ClassNotFoundException {

        if (root instanceof LeafNode) {                             
            return ((LeafNode) root).getPredictedClassValue();      
        } else {
            int risp = 0;
            out.writeObject(((SplitNode) root).formulateQuery());
            risp = (int) in.readObject();                           

            if (risp == -1 || risp >= root.getNumberOfChildren()) {
                out.writeObject("The answer should be an integer  between 0 and " + (root.getNumberOfChildren() - 1) + "!");
                throw new UnknownValueException("The answer should be an integer  between 0 and " + (root.getNumberOfChildren() - 1) + "!");
            } else {
                out.writeObject("QUERY");       
                return childTree[risp].predictClass(in, out);
            }
        }
    }

    /**
     * Serializza l'albero in un file definito da nomeFile
     *
     * @param nomeFile nome del file in cui sara  salvato l'albero
     * @throws FileNotFoundException eccezione sollevata nel caso in cui il file sia inesistente
     * @throws IOException eccezione generata da problemi di I/O
     */
    public void salva(String nomeFile) throws FileNotFoundException, IOException {
        ObjectOutputStream outFile = new ObjectOutputStream(new FileOutputStream(nomeFile));
        outFile.writeObject(this);
        outFile.close();
    }

    /**
     * Deserializza dal file nomeFile l'albero
     *
     * @param nomeFile nome del file da cui sara  caricato l'albero
     * @throws FileNotFoundException eccezione sollevata nel caso in cui il file sia inesistente
     * @throws IOException eccezione generata da problemi di I/O
     * @throws ClassNotFoundException eccezione sollevata in caso di problemi riguardanti il Class Loader
     * @return albero deserializzato
     */
    public static RegressionTree carica(String nomeFile) throws FileNotFoundException, IOException, ClassNotFoundException {

        ObjectInputStream inStream = new ObjectInputStream(new FileInputStream(nomeFile));
        RegressionTree childTree = (RegressionTree) inStream.readObject();
        inStream.close();
        return childTree;
    }
}









