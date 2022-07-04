package database;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Classe che definisce l'entita  Example che modella una transazione letta da una base di dati
 */

public class Example implements Comparable<Example>{

    private List<Object> example=new ArrayList<Object>();

    /**
     * @param o oggetto aggiunto all'ArrayList example
     */
    public void add(Object o){
        example.add(o);
    }

    /**
     * Metodo che ritorna un Object
     * @param i indice che indica l'elemento da restituire nell'ArrayList
     * @return l'Object in posizione i
     */
    public Object get(int i){
        return example.get(i);
    }

    /**
     * Implementazione del metodo compareTo
     * @param ex elemento con cui comparare il valore
     * @return esito della comparazione
     */
    @Override
    public int compareTo(Example ex) {

        int i=0;
        for(Object o:ex.example){
            if(!o.equals(this.example.get(i)))
                return ((Comparable)o).compareTo(example.get(i));
            i++;
        }
        return 0;
    }

    /**
     * Rappresentazione sotto forma di stringa dell'arrayList
     * @return stringa che contiene le info sull'arrayList example
     */
    @Override
    public String toString(){
        String str="";
        for(Object o:example)
            str+=o.toString()+ " ";
        return str;
    }
}