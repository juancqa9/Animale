package com.example.demo;

/**
 *La classe Product modella una specifica caratteristica di un certo prodotto.
 * E per ognugno di questi prodotti si avrà un codice identificativo, una descrizone sulle caratteristiche
 * dell'animale e una descrizione breve del prodotto.
 */
public class Product extends com.example.demo.Category
{
    private String ProductCode;
    private String ProductDesc;
    private String ProductBriefDescription;
// Costruttore della super classe
    /**
     *
     * @param Category param definisce il parametro di un metodo, contiene la categoria
     * @param SectorCode param definisce il parametro di un metodo, contiene il codigo del settore
     */
    Product(String Category,String SectorCode)
    {
        super(Category,SectorCode);
    }

    /**
     *
     * @return Indica i valori di ritorno di un metodo,in questo caso codice prodotto.
     */
    public String getProductCode()
    {
        return ProductCode;
    }

    /**
     *
     * @param productCode param definisce il parametro di un metodo, che contiene il codice del prodotto
     */
    public void setProductCode(String productCode)
    {
        this.ProductCode=productCode;
    }
    /**
     *
     * @return Indica i valori di ritorno di un metodo,in questo caso descrizione del prodotto.
     */
    public  String getProductDesc()
    {
        return ProductDesc;
    }

    /**
     *
     * @param productDesc param definisce il parametro di un metodo, che contiene la descrizione del prodotto
     */
    public void setProductDesc(String productDesc)
    {
        this.ProductDesc=productDesc;
    }

    /**
     *
     * @return Indica i valori di ritorno di un metodo,in questo caso una breve descrizione del prodotto.
     */
    public String getProductBriefDescription()
    {
        return ProductBriefDescription;
    }

    /**
     *
     * @param productBriefDescription param definisce il parametro di un metodo, che contiene una breve descrizione del prodotto
     */
    public void setProductBriefDescription(String productBriefDescription)
    {
        this.ProductBriefDescription=productBriefDescription;
    }
    @Override
    public String toString()
    {
        String Descrizione;
        Descrizione=super.toString()+"Codice Prodotto="+ProductCode+
                ";\nDescrizione Prodotto="+ProductDesc+";\nBreve descrizione del Prodotto="+ProductBriefDescription+";\n";
        return Descrizione;
    }
    @Override
    public boolean equals(Object obj)
    {
        Product temp=(Product) obj;
        if((ProductCode==temp.getProductCode())&&(ProductDesc==temp.getProductDesc())
                &&(ProductBriefDescription==temp.getProductBriefDescription()))
            return true;
        else
            return false;
    }
}
