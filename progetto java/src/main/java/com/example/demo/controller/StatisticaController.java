package com.example.demo.controller;

import com.example.demo.csv.AnalisiDati;
import com.example.demo.model.StatisticheDataset;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
@Controller
/**
 * La classe StatisticaController gestisce le richirste GET restituendo le statistiche in formato json.
 */
public class StatisticaController
{
    @RequestMapping("/statistica/{attributo}")
    @ResponseBody
/**
 * Il metodo dati mi restituisce la stringa contenente il file in formato json richiesto.
 */
    public String dati(@PathVariable String attributo)throws IOException
    {
        return OttieniJson(attributo);
    }

    /**
     *
     * @param attributo param definisce il parametro di un metodo, contiene il tipo di attributo passato con la richiesta GET.
     * @return Indica i valori di ritorno di un metodo,in questo caso viene creato un oggetto contenente i dati
     * presi dal dataset. I dati vengono letti e viene restituita una stringa contente le statistiche in formato json.
     * @throws IOException La clausola throws genera un’eccezione di tipo IOException, per la lettura dei dati nel json.
     */
    private String OttieniJson(String attributo) throws IOException
    {
        AnalisiDati obj=new AnalisiDati();
        StatisticheDataset statistica=new StatisticheDataset(obj.getDataset().getData());
        obj.MarshallingJson(statistica.getStatistiche(attributo));
        return obj.LeggiJson();
    }
}
