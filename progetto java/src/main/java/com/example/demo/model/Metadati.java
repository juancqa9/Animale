package com.example.demo.model;


import java.lang.reflect.Field;

public class Metadati
{
    private String Alias;
    private String SourceField;
    private String Type;
    /**
     * Il costruttore istanzia un oggetto contenente metadati attraverso i parametri passati
     */
    public Metadati(Field Alias, String SourceField)
    {
        this.Alias=Alias.getName();	//Alias diventa una stringa contenente il nome del campo passato per parametro
        this.SourceField=SourceField;	//SourceField va a memorizzare il nome passato per parametro
        this.Type=Alias.getType().getSimpleName();	//Type va a memorizzare il tipo del campo passato per parametro
    }
    /**
     * restituisce il nome del campo
     */
    public String getAlias()
    {
        return Alias;
    }
    /**
     * restituisce il nome originale del record
     */
    public String getSourceField()
    {
        return SourceField;
    }
    /**
     *  restituisce il tipo del campo
     */
    public String getType()
    {
        return Type;
    }
}

