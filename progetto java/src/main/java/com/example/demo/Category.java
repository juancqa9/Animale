package com.example.demo;

/**
 * La classe Category modella una specifica caratteristica di un mercato di vendita all'ingrosso di animali.
 * E per ognugno di questi animali avrà una categoria e un codice di settore.
 */
public class Category
{
    private String Category;
    private String SectorCode;

    /**
     *
     * @param category param definisce il parametro di un metodo, contiene la categoria
     * @param sectorCode param definisce il parametro di un metodo, contiene il codice del settore
     */
    public Category(String category, String sectorCode)
    {
        this.Category = category;
        this.SectorCode = sectorCode;

    }
    /**
     *
     * @return Indica i valori di ritorno di un metodo,in questo caso effettua categoria
     */
    public String getCategory()

    {
        return Category;
    }

    /**
     *
     * @return Indica i valori di ritorno di un metodo,in questo caso effettua  il codice del settore
     */
    public String getSectorCode()

    {
        return SectorCode;
    }
    @Override

    public String toString()
    {
        String Descrizione;
        Descrizione="Category="+Category+";\nSectorCode="+SectorCode+";\n";
        return Descrizione;
    }
    @Override
    public boolean equals(Object obj)
    {
        Category temp=(Category) obj;
        if((Category==temp.getCategory())&&(SectorCode==temp.getSectorCode()))
            return true;
        else
            return false;
    }
}