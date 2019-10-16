package com.example.demo.service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Vector;

/**
 * La classe OperatoreCondizionale si occupa di effettuare operazioni condizionali tra vettori.
 */
public class OperatoreCondizionale
{
    private Vector<?> vettore;

    /**
     *
     * @param vettore param definisce il parametro di un metodo, contiene il vettore del costruttore.
     */
    public OperatoreCondizionale(Vector<Object> vettore)
    {
        this.vettore=vettore;
    }

    /**
     *
     * @param segno param definisce il parametro di un metodo, contiene il segno.
     * @param attributo param definisce il parametro di un metodo, definisce l'attributo.
     * @param valore param definisce il parametro di un metodo, contiene il valore.
     * @return Indica i valori di ritorno di un metodo,in questo caso confronta l'oggetto nel vettore.
     */
    public Vector<Object> Confronto(String segno, String attributo, double...valore)
    {
        Vector<Object> vettoreOut=new Vector<Object>();
        Method m=null;
        try
        {
            m=this.vettore.get(0).getClass().getMethod("get"+attributo);
        }
        catch (NoSuchMethodException | SecurityException e)
        {
            e.printStackTrace();
        }
        for (int i=0; i<vettore.size(); i++)
        {
            double temp=0;
            try
            {
                temp = (double) m.invoke(vettore.get(i));
            }
            catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
            {
                e.printStackTrace();
            }
            if (Segno(segno,temp,valore))
                vettoreOut.add(vettore.get(i));
        }
        return vettoreOut;
    }

    /**
     *
     * @param segno param definisce il parametro di un metodo, contiene il segno.
     * @param val param definisce il parametro di un metodo, definisce il valore.
     * @param valore param definisce il parametro di un metodo, definisce più valori.
     * @return Indica i valori di ritorno di un metodo,in questo caso effettua un confronto tra più condizioni
     * e restituisce il risultato.
     */
    private boolean Segno(String segno,double val, double...valore)
    {
        switch(segno)
        {
            case "$gt":
                if(val>valore[0])
                    return true;
                else
                    return false;
            case "$gte":
                if(val>=valore[0])
                    return true;
                else
                    return false;
            case "$it":
                if(val<valore[0])
                    return true;
                else
                    return false;
            case "$ite":
                if(val<=valore[0])
                    return true;
                else
                    return false;
            case "$bt":
                if((valore[0]<=val)&&(valore[1]>=val))
                    return true;
                else
                    return false;
        }
        return false;
    }
}


