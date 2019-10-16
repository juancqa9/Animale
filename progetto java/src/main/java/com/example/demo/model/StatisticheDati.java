package com.example.demo.model;

import java.util.ArrayList;
import java.util.Vector;

/**
 * La classe calcola le statistiche del vettore passato al costruttore.
 */
public class StatisticheDati
{
    private Double ArrayNumber[];
    private String ArrayString[];
    private double media;
    private double minimo;
    private double massimo;
    private double deviazione_standard;
    private double somma;
    private int count;
    private Vector<Parola> parole;

    /**
     *
     * @param vettore  param definisce il parametro di un metodo, contiene il
     *vettore di cui vengono calcolate le statistiche.
     */
    public StatisticheDati(ArrayList<Object> vettore)
    {
        if(vettore.get(0) instanceof Double)
        {
            ArrayNumber=vettore.toArray(new Double[vettore.size()]);
            Somma();
            Count();
            Media();
            DeviazioneStandard();
            Minimo();
            Massimo();
        }
        if(vettore.get(0) instanceof String)
        {
            ArrayString=vettore.toArray(new String[vettore.size()]);
            Occorrenze();
        }
    }

    /**
     * Metodo che calcola la media e imposta la relativa variabile d'istanza.
     */
    public void Media()
    {
        media=somma/count;
    }

    /**
     * Metodo che calcola il minimo e imposta la relativa variabile d'istanza.
     */
    public void Minimo()
    {
        double min=ArrayNumber[0];
        for(int i=0;i<ArrayNumber.length;i++)
        {
            if(ArrayNumber[i]<min)
                min=ArrayNumber[i];
        }
        minimo=min;
    }

    /**.
     * Metodo che calcola il massimo e imposta la relativa variabile d'istanza
     */
        public void Massimo()
    {
        double max=ArrayNumber[0];
        for(int i=0;i<ArrayNumber.length;i++)
        {
            if(ArrayNumber[i]>max)
                max=ArrayNumber[i];
        }
        massimo=max;
    }

    /**
     * Metodo che calcola la deviazione standard e imposta la relativa variabile d'istanza.
     */
    public void DeviazioneStandard()
    {
        double varianza=0;
        for(int i=0;i<ArrayNumber.length;i++)
        {
            varianza+=(ArrayNumber[i]-media)*(ArrayNumber[i]-media);
        }
        deviazione_standard=Math.sqrt(varianza/count);
    }

    /**
     * Metodo che calcola la somma e imposta la relativa variabile d'istanza.
     */
    public void Somma()
    {
        double somma=0;
        for(int i=0;i<ArrayNumber.length;i++)
        {
            somma+=ArrayNumber[i];
        }
        this.somma=somma;
    }

    /**
     * Metodo che conta il numero di valori e imposta la relativa variabile d'istanza.
     */
    public void Count()
    {
        int i;
        for(i=0;i<ArrayNumber.length;i++);
        count=i;
    }

    /**
     * Metodo che conta le occorrenze e imposta la relativa variabile d'istanza
     */
    public void Occorrenze()
    {
        boolean flag;
        Vector<Parola> parole = new Vector<Parola>();
        parole.add(new Parola(ArrayString[0]));
        for(int i=1,j;i<ArrayString.length;i++)
        {
            flag=false;
            j=0;
            while(!flag)
            {
                if(ArrayString[i].equals(parole.get(j).getParola()))
                {
                    parole.get(j).incrementaOccorrenze();
                    flag=true;
                }
                j++;
                if((j==parole.size())&&!flag)
                {
                    parole.add(new Parola(ArrayString[i]));
                    flag=true;
                }
            }
        }
        this.parole=parole;
    }

    /**
     *
     * @return Indica i valori di ritorno di un metodo,in questo caso effettua la media.
     */
    public double getMedia()
    {
        return media;
    }

    /**
     *
     * @return Indica i valori di ritorno di un metodo,in questo caso effettua il minimo.
     */
    public double getMinimo()
    {
        return minimo;
    }

    /**
     *
     * @return Indica i valori di ritorno di un metodo,in questo caso effettua il massimo.
     */
    public double getMassimo()
    {
        return massimo;
    }

    /**
     *
     * @return Indica i valori di ritorno di un metodo,in questo caso effettua la deviazione standard.
     */
    public double getDeviazioneStandard()
    {
        return deviazione_standard;
    }

    /**
     *
     * @return Indica i valori di ritorno di un metodo,in questo caso effettua la somma.
     */
    public double getSomma()
    {
        return somma;
    }

    /**
     *
     * @return Indica i valori di ritorno di un metodo,in questo caso effettua il numero degli elementi.
     */
    public int getCount()
    {
        return count;
    }

    /**
     *
     * @return Indica i valori di ritorno di un metodo,in questo caso effettua
     * il numero delle occorrenze della parola.
     */
    public Vector<Parola> getOccorrenze()
    {
        return parole;
    }
}