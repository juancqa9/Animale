package com.example.demo;

public class Category
{
    private String Category;
    private String SectorCode;
    public Category(String category, String sectorCode)
    {
        this.Category = category;
        this.SectorCode = sectorCode;

    }
    public String getCategory()
    {
        return Category;
    }
    public String getSectorCode()
    {
        return SectorCode;
    }
    public String toString()
    {
        String Descrizione;
        Descrizione="Category="+Category+";\nSectorCode="+SectorCode+";\n";
        return Descrizione;
    }
    public boolean equals(Object obj)
    {
        Category temp=(Category) obj;
        if((Category==temp.getCategory())&&(SectorCode==temp.getSectorCode()))
            return true;
        else
            return false;
    }
}