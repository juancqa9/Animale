package com.example.demo;

public class MarketPrice extends CountryProduct
{
    private double MPMarketPrice;
    MarketPrice(String Category,String SectorCode)
    {
        super(Category,SectorCode);
    }
    public double getMPMarketPrice()
    {
        return MPMarketPrice;
    }
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
