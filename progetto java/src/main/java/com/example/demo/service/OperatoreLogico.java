package com.example.demo.service;

import com.example.demo.Category;
import com.example.demo.CountryProduct;
import com.example.demo.MarketPrice;
import com.example.demo.Product;

import java.util.Vector;

/**
 * La classe OperatoreLogico si occupa di effettuare operazioni logiche tra vettori.
 */
public class OperatoreLogico
{
    private Vector<Object> vettoreOut;

    /**
     *
     * @param Operatore param definisce il parametro di un metodo, contiene un operatore logico
     * @param vettore1 param definisce il parametro di un metodo, contiene un argomento della funzione logica
     * @param vettore2 param definisce il parametro di un metodo, contiene un argomento della funzione logica
     * @return Indica i valori di ritorno di un metodo,in questo caso restituisce il risultato dell'operazione logica
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
     *
     * @param Operatore param definisce il parametro di un metodo, contiene un operatore logico
     * @param vettore param definisce il parametro di un metodo, contiene un argomento della funzione logica
     * @return Indica i valori di ritorno di un metodo,in questo caso restituisce il risultato dell'operazione logica
     */
    public Vector<Object> Confronto(String Operatore,Vector<Object> vettore)
    {
        if(Operatore=="$not")
            not(vettore);
        return vettoreOut;
    }

    /**
     * @param vettore1 param definisce il parametro di un metodo, contiene un argomento della funzione logica
     * @param vettore2 param definisce il parametro di un metodo, contiene un argomento della funzione logica
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
     * @param vettore1 param definisce il parametro di un metodo, contiene un argomento della funzione logica
     * @param vettore2 param definisce il parametro di un metodo, contiene un argomento della funzione logica
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
     *
     * @param vettore param definisce il parametro di un metodo, contiene un argomento della funzione logica
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
     *
     * @param vettore param definisce il parametro di un metodo, contiene il vettore
     * @param obj param definisce il parametro di un metodo, contiene un oggetto di cui verifica la presenza nel vettore.
     * @return Indica i valori di ritorno di un metodo,in questo caso conta l'oggetto nel vettore contenuto nelle classi.
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