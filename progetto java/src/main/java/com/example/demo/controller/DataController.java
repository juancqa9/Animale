package com.example.demo.controller;

import com.example.demo.csv.AnalisiDati;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@Controller
/**
 * La classe DataController gestisce le richieste GET che
 * restituiscono i dati in formato json.
 */
public class DataController
{
    @RequestMapping("/data")
    @ResponseBody
/**
 * Il metodo dati mi restituisce la stringa contenente il file in formato json richiesto.
 */
    public String dati() throws IOException
    {
       return OttieniJson();

    }

    /**
     *
     * @return Indica i valori di ritorno di un metodo,in questo caso viene creato un oggetto contenente i dati
     * presi dal dataset. I dati vengono letti e viene restituita una stringa contente i dati in formato json.
     * @throws IOException La clausola throws genera un’eccezione di tipo IOException, per la lettura dei dati nel json.
     */
    public String OttieniJson() throws IOException
    {
        AnalisiDati obj=new AnalisiDati();
        obj.MarshallingJson(obj.getDataset().getData());
        return obj.LeggiJson();
    }
}
