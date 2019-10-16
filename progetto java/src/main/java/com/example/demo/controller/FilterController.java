package com.example.demo.controller;

import com.example.demo.csv.AnalisiDati;
import com.example.demo.service.Filtro;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@Controller
/**
 * La classe FilterController gestisce i filtri che restituiscono i dati in formato json.
 */
public class FilterController
{
    @RequestMapping("filter/{filtro}")
    @ResponseBody
    /**
     * Il metodo dati mi restituisce la stringa contenente il file in formato json richiesto.
     */
    public String dati(@PathVariable String filtro)throws  IOException
    {
        return OttieniJson(filtro);
    }

    /**
     *
     * @param filtro param definisce il parametro di un metodo, contiene il tipo di filtro passato con la richiesta GET.
     * @return Indica i valori di ritorno di un metodo,in questo caso viene creato un oggetto contenente i dati filtrato
     * presi dal dataset. I dati vengono letti e viene restituita una stringa contente i dati filtrati in formato json.
     * @throws IOException La clausola throws genera un’eccezione di tipo IOException, per la lettura dei dati nel json.
     */
    private String OttieniJson(String filtro) throws IOException {
        AnalisiDati obj = new AnalisiDati();
        Filtro filter = new Filtro(obj.getDataset().getData(),filtro);
        obj.MarshallingJson(filter.getData());
        return obj.LeggiJson();
    }
}