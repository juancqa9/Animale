package com.example.demo;

public class CountryProduct extends Product
{
    private String Unit;
    private String Country;
    private double Period;
    CountryProduct(String Category,String SectorCode)
    {
        super(Category,SectorCode);
    }
    public String getUnit()
    {
        return Unit;
    }
    public void setUnit(String unit)
    {
        this.Unit=unit;
    }
    public String getCountry()
    {
        return Country;
    }
    public void setCountry(String country)
    {
        this.Country=country;
    }
    public double getPeriod()
    {
        return Period;
    }
    public void setPeriod(double period)
    {
        this.Period=period;
    }
    public String toString()
    {
        String Descrizione;
        Descrizione=super.toString()+"Unita="+Unit+
                ";\nPaese="+Country+";\nPeriodo="+Period+";\n";
        return Descrizione;
    }
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
