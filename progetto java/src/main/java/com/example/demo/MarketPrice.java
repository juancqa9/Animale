package com.example.demo;

/**
 * La classe MarketPrice definisce il prezzo del prodotto nel mercato.
 */
public class MarketPrice extends CountryProduct
{
    private double MPMarketPrice;

    /**
     *
     * @param Category param definisce il parametro di un metodo, contiene la categoria
     * @param SectorCode param definisce il parametro di un metodo, contiene il codice del settore
     */
    public MarketPrice(String Category, String SectorCode)
    {
        super(Category,SectorCode);
    }

    /**
     *
     * @return Indica i valori di ritorno di un metodo,in questo caso effettua il prezzo del prodotto nel mercato.
     */
    public double getMPMarketPrice()
    {
        return MPMarketPrice;
    }

    /**
     *
     * @param mpMarketPrice param definisce il parametro di un metodo, che contiene il prezzo del prodotto nel mercato.
     */
    public void setMPMarketPrice(double mpMarketPrice)
    {
        this.MPMarketPrice=mpMarketPrice;
    }
    @Override
    public String toString()
    {
        String Descrizione;
        Descrizione=super.toString()+"Prezzo DI Mercato="+MPMarketPrice+";\n";
        return Descrizione;
    }
    @Override
    public boolean equals(Object obj)
    {
        MarketPrice temp=(MarketPrice) obj;
        if(MPMarketPrice==temp.getMPMarketPrice())
            return true;
        else
            return false;
    }
}
