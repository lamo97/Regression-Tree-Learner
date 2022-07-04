package data;

import database.*;

import java.sql.SQLException;
import java.util.*;

/**
 * Modella l'insieme di esempi di training
 */

public class Data {

	private List<Example> data=new ArrayList<Example>();
	private int numberOfExamples;
	private List<Attribute> explanatorySet = new LinkedList<Attribute>();
	private ContinuousAttribute classAttribute;


	/**
	 * Costruttore di classe
	 * @param table Nome della tabella del database MapDB su MySQL che contiene i valori per il Training Set
	 * @throws TrainingDataException eccezione sollevata in caso di tabella vuota o numero di attribute errato
	 * @throws SQLException eccezione sollevata in caso di tabella non esistente
	 */

	public Data(String table) throws TrainingDataException, SQLException{

		TableSchema ts = null;
		TableData td = null;

		DbAccess db= new DbAccess();

		try{
			td = new TableData(db);
			ts = new TableSchema(db,table);

			data= new ArrayList<>(td.getTransazioni(table));

			numberOfExamples = data.size();

			if(numberOfExamples == 0)
				throw new TrainingDataException();
			if(ts.getNumberOfAttributes()<2)
				throw new TrainingDataException();

			for(int i=0; i<ts.getNumberOfAttributes()-1; i++)
				if(ts.getColumn(i).isNumber())
					explanatorySet.add( new ContinuousAttribute(ts.getColumn(i).getColumnName(), i) );
				else
					explanatorySet.add( new DiscreteAttribute(ts.getColumn(i).getColumnName(),i, (Set) td.getDistinctColumnValues(table, ts.getColumn(i)) )); // distinct values

			if(ts.getColumn(ts.getNumberOfAttributes()-1).isNumber())
				classAttribute = new ContinuousAttribute( ts.getColumn(ts.getNumberOfAttributes()-1).getColumnName(), // name
						ts.getNumberOfAttributes()-1 );// distinct values

			else
				throw new TrainingDataException(); // attributo di classe discreto

		} catch (EmptySetException e) {
			throw new TrainingDataException();
		}  catch (DatabaseConnectionException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Restituisce il valore di numberOfExamples
	 * @return Cardinalita  dell'insieme di esempi
	 */
	public int getNumberOfExamples(){
		return numberOfExamples;
	}

	/**
	 * Restituisce la lunghezza dell'array explanatorySet[]
	 * @return Cardinalita  dell'insieme di attributi indipendenti
	 */
	public int getNumberOfExplanatoryAttributes(){
		return explanatorySet.size();
	}

	/**
	 * Restituisce il valore dell'attributo di classe per l'esempio exampleIndex
	 * @param exampleIndex  Indice di riga dell'array di record data[] per un determinato esempio
	 * @return Valore dell'attributo di classe per l'esempio exampleIndex
	 */
	public Double getClassValue(int exampleIndex){
		return (Double) data.get(exampleIndex).get(getNumberOfExplanatoryAttributes());
	}

	/**
	 * Restituisce il valore dell'attributo con indice di riga data da exampleIndex e indice di colonna
	 * dato da attributeIndex
	 * @param exampleIndex Indice di riga per l'array data[] per uno specifico esempio
	 * @param attributeIndex Indice di colonna per un determinato elemento sulla riga determinata da exampleIndex
	 * @return Object associato all'attributo indipendente per l'esempio indicizzato in input
	 */
	public Object getExplanatoryValue(int exampleIndex, int attributeIndex){
		return data.get(exampleIndex).get(attributeIndex);
	}

	/**
	 * Restituisce l'attributo indicizzato da index in explanatorySet[]
	 * @param index indice per uno spefico esempio
	 * @return Oggetto attributo associato ad index
	 */
	public Attribute getExplanatoryAttribute(int index){
		return explanatorySet.get(index);
	}

	/**
	 * Restituisce l'attributo di classe
	 * @return Attributo di classe continuo
	 */
	protected ContinuousAttribute getClassAttribute(){
		return classAttribute;
	}

	/**
	 * @return Rappresentazione del trainingSet sottoforma di stringa
	 */
	@Override
	public String toString(){
		String value="";
		for(int i=0;i<numberOfExamples;i++){
			for(int j=0;j<explanatorySet.size();j++)
				value+=data.get(i)+",";

			value+=data.get(i).get(explanatorySet.size())+"\n";
		}
		return value;
	}

	/**
	 * Sorting del trainingSet mediante il metodo Arrays.sort per attributo
	 * @param attribute Criterio d'ordinamento
	 * @param beginExampleIndex Indice iniziale del sottoinsieme del trainingSet
	 * @param endExampleIndex Indice finale del sottoinsieme del trainingSet
	 */
	public void sort(Attribute attribute, int beginExampleIndex, int endExampleIndex){
		quicksort(attribute, beginExampleIndex, endExampleIndex);
	}

	/**
	 * scambio degli esempi i e j
	 * @param i indice
	 * @param j indice
 	 */
	private void swap(int i,int j){
		Collections.swap(data, i,j);
	}

	/**
	 * Partiziona il vettore in base all'elemento x e restiutisce il punto di separazione
	 * @param attribute attributo Discreto
	 * @param inf indice inferiore
	 * @param sup indice superiore
	 * @return intero che rappresenta il punto di separazione
	 */
	private  int partition(DiscreteAttribute attribute, int inf, int sup){
		int i,j;

		i=inf;
		j=sup;
		int	med=(inf+sup)/2;
		String x=(String)getExplanatoryValue(med, attribute.getIndex());
		swap(inf,med);

		while (true)
		{
			while(i<=sup && ((String)getExplanatoryValue(i, attribute.getIndex())).compareTo(x)<=0){
				i++;

			}

			while(((String)getExplanatoryValue(j, attribute.getIndex())).compareTo(x)>0) {
				j--;

			}

			if(i<j) {
				swap(i,j);
			}
			else break;
		}
		swap(inf,j);
		return j;
	}

	/**
	 * Partiziona il vettore rispetto all'elemento x e restiutisce il punto di separazione
	 * @param attribute attributo Continuo
	 * @param inf indice inferiore
	 * @param sup indice superiore
	 * @return intero che rappresenta il punto di separazione
	 */
	private  int partition(ContinuousAttribute attribute, int inf, int sup){
		int i,j;

		i=inf;
		j=sup;
		int	med=(inf+sup)/2;
		Double x=(Double)getExplanatoryValue(med, attribute.getIndex());
		swap(inf,med);

		while (true)
		{

			while(i<=sup && ((Double)getExplanatoryValue(i, attribute.getIndex())).compareTo(x)<=0){
				i++;

			}

			while(((Double)getExplanatoryValue(j, attribute.getIndex())).compareTo(x)>0) {
				j--;

			}

			if(i<j) {
				swap(i,j);
			}
			else break;
		}
		swap(inf,j);
		return j;

	}
	/**
	 * Algoritmo quicksort per l'ordinamento di un array di interi A usando come relazione d'ordine totale "minore o uguale"
	 * @param attribute discreto o continuo
	 * @param inf indice inferiore
	 * @param sup indice superiore
	 */
	private void quicksort(Attribute attribute, int inf, int sup){

		if(sup>=inf){

			int pos;
			if(attribute instanceof DiscreteAttribute)
				pos=partition((DiscreteAttribute)attribute, inf, sup);
			else
				pos=partition((ContinuousAttribute)attribute, inf, sup);

			if ((pos-inf) < (sup-pos+1)) {
				quicksort(attribute, inf, pos-1);
				quicksort(attribute, pos+1,sup);
			}
			else
			{
				quicksort(attribute, pos+1, sup);
				quicksort(attribute, inf, pos-1);
			}
		}
	}



}
