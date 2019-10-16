package com.example.demo.model;

/**
 * la classe parola viene utilizzata per contare le occorrenze di una stringa.
 */
public class Parola
{
    private String parola;
    private int occorrenze;

    /**
     *
     * @param parola param definisce il parametro  di un metodo, contenente parola nel costruttore.
     */
    public Parola(String parola)
    {
        this.parola=parola;
        occorrenze=1;
    }

    /**
     *
     * @return Indica i valori di ritorno di un metodo,in questo caso effettua parola.
     */
    public String getParola()
    {
        return parola;
    }

    /**
     *
     * @return  Indica i valori di ritorno di un metodo,in questo caso effettua coccorrenze.
     */
    int getOccorrenze()
    {
        return occorrenze;
    }

    public void incrementaOccorrenze()
    {
        this.occorrenze++;
    }
}