package data;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

/**
 * Estende la classe Attribute e modella un attributo discreto
 */

public class DiscreteAttribute extends Attribute implements Iterable<String>{

	private Set<String> values=new TreeSet<>(); // order by asc

	/**
	 * Invoca il costruttore della superclasse e avvalora l'array values[] con valori discreti forniti in input
	 * @param name  Nome dell'attributo
	 * @param index Indice dell'attributo
	 * @param values Valori discreti
	 */
	public DiscreteAttribute(String name, int index, Set<String> values) {
		super(name,index);
		this.values=values;
	}

	/**
	 * Restituisce la cardinalita  di values
	 * @return  Numero di valori discreti dell'attributo
	 */
	public int getNumberOfDistinctValues(){
		return values.size();
	}

	/**
	 * @return Stringa contenente il nome dell'attributo
	 */
	@Override
	public Iterator<String> iterator() {
		// TODO Auto-generated method stub
		return values.iterator();
	}
}
