package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@Controller
public class FilterController
{
    @RequestMapping("filter/{filtro}")
    @ResponseBody
    public String dati(@PathVariable String filtro)throws  IOException
    {
        return OttieniJson(filtro);
    }

    private String OttieniJson(String filtro) throws IOException {
        AnalisiDati obj = new AnalisiDati();
        Filtro filter = new Filtro(obj.getDataset().getData(),filtro);
        obj.MarshallingJson(filter.getData());
        return obj.LeggiJson();
    }
}