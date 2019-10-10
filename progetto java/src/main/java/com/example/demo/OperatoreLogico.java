package com.example.demo;

import java.util.Vector;

public class OperatoreLogico
{
    private Vector<Object> vettoreOut;
    /**
     * Il metodo seleziona la funzione logica adatta in relazione all'operatore passato per parametro
     */
    public Vector<Object> Confronto(String Operatore, Vector<Object> vettore1, Vector<Object> vettore2)
    {
        switch(Operatore)
        {
            case "$and":
                and(vettore1,vettore2);
                break;
            case "$or":
                or(vettore1,vettore2);
                break;
        }
        return vettoreOut;
    }
    /**
     * Il metodo seleziona la funzione logica adatta in relazione all'operatore passato per parametro
     */
    public Vector<Object> Confronto(String Operatore,Vector<Object> vettore)
    {
        if(Operatore=="$not")
            not(vettore);
        return vettoreOut;
    }
    /**
     * Funzione logica and. Ottiene come risultato un vettore contenente l'intersezione tra i vettori passati come argomenti.
     * Il vettore risultante contiene gli elementi in comune tra i i due vettori
     */
    private void and(Vector<Object> vettore1,Vector<Object> vettore2)
    {
        vettoreOut=new Vector<Object>();
        for(int i=0;i<vettore1.size();i++)
        {
            Object temp=vettore1.get(i);	//temp diventa un riferimento a un elemento del vettore 1
            if(contains(vettore2,temp))	//Se vettore 2 contiene temp, temp viene aggiiunto al vettore in uscita
                vettoreOut.add(temp);
        }
    }
    /**
     * Funzione logica or. Ottiene come risultato un vettore contenente l'unione tra i vettori passati come argomenti.
     * Il vettore risultante contiene l'unione degli elementi presenti nei due vettori
     */
    private void or(Vector<Object> vettore1,Vector<Object> vettore2)
    {
        vettoreOut=new Vector<Object>();
        for(int i=0;i<vettore1.size();i++)
        {
            Object temp=vettore1.get(i);
            vettoreOut.add(temp);	//Aggiunge gli elementi del primo vettore al vettore in uscita
        }
        for(int i=0;i<vettore2.size();i++)
        {
            Object temp=vettore2.get(i);
            if(!contains(vettoreOut,temp))	//Se l'elemento a cui fa riferimento temp non è contenuto nel vettore in uscita viene aggiunto
                vettoreOut.add(temp);
        }
    }
    /**
     * Funzione logica not. Ottiene come risultato un vettore complementare a quello passato come argomento.
     * Il vettore risultante contiene gli elementi del dataset che non sono presenti nel vettore passato per argomento
     */
    public void not(Vector<Object> vettore)
    {
        vettoreOut=new Vector<Object>();
        for(int i=0;i<vettore.size();i++)
        {
            Object temp=vettore.get(i);
            if(!contains(vettore,temp))	//Se il vettore non contiene il valore a cui fa riferimento temp esso viene aggiunto al vettore in uscita
                vettoreOut.add(temp);
        }
    }
    /**
     * La funzione verifica se l'oggetto obj è contenuto nel vettore
     */
    private boolean contains(Vector<Object> vettore, Object obj)
    {
        if((obj instanceof Category)&&!(obj instanceof Product)&&!(obj instanceof CountryProduct)&&!(obj instanceof MarketPrice))
        {
            Category temp=(Category) obj;
            for(int i=0;i<vettore.size();i++)
            {
                if(vettore.get(i)==temp)
                    return true;
            }
            return false;
        }
        if((obj instanceof Product)&&!(obj instanceof CountryProduct)&&!(obj instanceof MarketPrice))
        {
            Product temp=(Product) obj;
            for(int i=0;i<vettore.size();i++)
            {
                if(vettore.get(i)==temp)
                    return true;
            }
            return false;
        }
        if((obj instanceof CountryProduct)&&!(obj instanceof MarketPrice))
        {
            CountryProduct temp=(CountryProduct) obj;
            for(int i=0;i<vettore.size();i++)
            {
                if(temp==vettore.get(i))
                    return true;
            }
            return false;
        }
        if(obj instanceof MarketPrice)
        {
            MarketPrice temp=(MarketPrice) obj;
            for(int i=0;i<vettore.size();i++)
            {
                if(vettore.get(i)==temp)
                    return true;
            }
            return false;
        }
        return false;
    }
}