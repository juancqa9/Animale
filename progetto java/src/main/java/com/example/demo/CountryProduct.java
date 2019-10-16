package com.example.demo;

/**
 * La classe Product modella una specifica caratteristica del mercato di un paese.
 * Con ogni prodotto conterrà l'unità, il paese e il periodo.
 */
public class CountryProduct extends Product
{
    private String Unit;
    private String Country;
    private double Period;

    /**
     *
     * @param Category param definisce il parametro di un metodo, contiene la categoria
     * @param SectorCode  param definisce il parametro di un metodo, contiene il codice del settore
     */
    CountryProduct(String Category,String SectorCode)
    {
        super(Category,SectorCode);
    }

    /**
     *
     * @return Indica i valori di ritorno di un metodo,in questo caso effettua  l'unità.
     */
    public String getUnit()
    {
        return Unit;
    }

    /**
     *
     * @param unit param definisce il parametro di un metodo, che contiene l'unità.
     */
    public void setUnit(String unit)
    {
        this.Unit=unit;
    }

    /**
     *
     * @return indica i valori di ritorno di un metodo,in questo caso effettua  paese.
     */
    public String getCountry()
    {
        return Country;
    }

    /**
     *
     * @param country param definisce il parametro di un metodo, che contiene paese.
     */
    public void setCountry(String country)
    {
        this.Country=country;
    }

    /**
     *
     * @return indica i valori di ritorno di un metodo,in questo caso effettua  il periodo.
     */
    public double getPeriod()
    {
        return Period;
    }

    /**
     *
     * @param period param definisce il parametro di un metodo, che contiene il periodo.
     */
    public void setPeriod(double period)
    {
        this.Period=period;
    }
    @Override
    public String toString()
    {
        String Descrizione;
        Descrizione=super.toString()+"Unita="+Unit+
                ";\nPaese="+Country+";\nPeriodo="+Period+";\n";
        return Descrizione;
    }
    @Override
    public boolean equals(Object obj)
    {
        CountryProduct temp=(CountryProduct) obj;
        if((Unit==temp.getUnit())&&(Country==temp.getCountry())
                &&(Period==temp.getPeriod()))
            return true;
        else
            return false;
    }
}
