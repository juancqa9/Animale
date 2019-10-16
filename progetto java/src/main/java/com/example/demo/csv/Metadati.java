package com.example.demo.csv;


import java.lang.reflect.Field;

/*
* La classe contiene il tipo, nome e alias uttilizzato nel programma di un oggetto
*/
public class Metadati
{
    private String Alias;
    private String SourceField;
    private String Type;

     // Il costruttore istanzia un oggetto contenente metadati attraverso i parametri passati

    /**
     *
     * @param Alias param definisce il parametro di un metodo, che rappresenta un campo di un oggetto
     * @param SourceField param definisce il parametro di un metodo, che rappresenta il nome di un oggetto
     */
    public Metadati(Field Alias, String SourceField)
    {
        this.Alias=Alias.getName();	//Alias diventa una stringa contenente il nome del campo passato per parametro
        this.SourceField=SourceField;	//SourceField va a memorizzare il nome passato per parametro
        this.Type=Alias.getType().getSimpleName();	//Type va a memorizzare il tipo del campo passato per parametro
    }

    /**
     *
     * @return  Indica i valori di ritorno di un metodo,in questo caso il nome del campo
     */
    public String getAlias()
    {
        return Alias;
    }

    /**
     *
     * @return  Indica i valori di ritorno di un metodo,in questo caso il nome del record
     */
    public String getSourceField()
    {
        return SourceField;
    }

    /**
     *
     * @return  Indica i valori di ritorno di un metodo,in questo caso il tipo del campo
     */
    public String getType()
    {
        return Type;
    }
}

