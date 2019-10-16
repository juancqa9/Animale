package com.example.demo.controller;

import com.example.demo.csv.AnalisiDati;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
@Controller

/**
 * La classe MetaDataController gestisce le richieste GET che
 *  * restituiscono i metadati in formato json.
 */
public class MetaDataController
{
    @RequestMapping("/metadata")
    @ResponseBody
    /**
     * Il metodo metadati mi restituisce la stringa contenente il file in formato json richiesto.
     */
    public String metadati() throws IOException
    {
        return OttieniJson();
    }

    /**
     *
     * @return Indica i valori di ritorno di un metodo,in questo caso viene creato un oggetto contenente i metadati
     * presi dal dataset. I metadati vengono letti e viene restituita una stringa contenente i metadati in formato json.
     * @throws IOException La clausola throws genera un’eccezione di tipo IOException, per la lettura dei metadati nel json.
     */
    private String OttieniJson() throws IOException
    {
        AnalisiDati obj=new AnalisiDati();
        obj.MarshallingJson(obj.getDataset().getMetadati());
        return obj.LeggiJson();
    }
}
