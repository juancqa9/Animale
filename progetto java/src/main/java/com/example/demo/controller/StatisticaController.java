package com.example.demo.controller;

import com.example.demo.AnalisiDati;
import com.example.demo.StatisticheDataset;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
@Controller
public class StatisticaController
{
    @RequestMapping("/statistica/{attributo}")
    @ResponseBody
    public String dati(@PathVariable String attributo)throws IOException
    {
        return OttieniJson(attributo);
    }

    private String OttieniJson(String attributo) throws IOException
    {
        AnalisiDati obj=new AnalisiDati();
        StatisticheDataset statistica=new StatisticheDataset(obj.getDataset().getData());
        obj.MarshallingJson(statistica.getStatistiche(attributo));
        return obj.LeggiJson();
    }
}
