package com.example.demo;

public class Product extends  Category
{
    private String ProductCode;
    private String ProductDesc;
    private String ProductBriefDescription;
    Product(String Category,String SectorCode)
    {
        super(Category,SectorCode);
    }

    public String getProductCode()
    {
        return ProductCode;
    }
    public void setProductCode(String productCode)
    {
        this.ProductCode=productCode;
    }
    public  String getProductDesc()
    {
        return ProductDesc;
    }
    public void setProductDesc(String productDesc)
    {
        this.ProductDesc=productDesc;
    }
    public String getProductBriefDescription()
    {
        return ProductBriefDescription;
    }
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
