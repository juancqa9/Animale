package com.example.demo.model;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Vector;

/**
 * La classe calcola le statistiche degli elementi contenuti nel Vector passato come parametro.
 */
public class StatisticheDataset
{
    private Vector<?> vettore;

    /**
     *
     * @param vettore param definisce il parametro di un metodo, contiene il vettore di cui si vogliono calcolate
     * le statistiche.
     */
    public StatisticheDataset(Vector<?> vettore)
    {
        this.vettore=vettore;
    }

    /**
     *
     * @param attributo param definisce il parametro di un metodo, contiene il record del dataset da cui calcolare
     * le statistiche
     * @return Indica i valori di ritorno di un metodo,in questo caso effettua un oggetto di tipo StatisticheDati
     * contenente le statistiche dei vari attributi del vettore passato come parametro durante l'istanziamento della classe.
     */
    public StatisticheDati getStatistiche(String attributo)
    {
        StatisticheDati obj=new StatisticheDati(toArray(attributo));	/*Costruisce un oggetto di tipo statistiche dati passando
		come parametro il vettore contenente i valori della colonna corrispondendente all'attributo specificato*/
        return obj;
    }

    /**
     *
     * @param attributo param definisce il parametro di un metodo, contiene la colonna del dataset
     * corrispondente all'attributo
     * @return Indica i valori di ritorno di un metodo,in questo caso effettua un vettore contenente i
     * valori contenuti nella colonna.
     */
    private ArrayList<Object> toArray(String attributo)
    {
        ArrayList<Object> valori=new ArrayList<Object>();
        Method m=null;
        try
        {
            m=this.vettore.get(0).getClass().getMethod("get"+attributo);	//Crea un oggetto di tipo metodo.Tale metodo restituisce l'attributo specificato
        }
        catch (NoSuchMethodException e)
        {
            e.printStackTrace();
        }
        catch (SecurityException e)
        {
            e.printStackTrace();
        }
        for(int i=0;i<this.vettore.size();i++)
        {
            try
            {
                valori.add(m.invoke(this.vettore.get(i)));	//Aggiunge i valori della colonna in un vettore
            }
            catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
            {
                e.printStackTrace();
            }
        }
        return valori;
    }
}