package com.example.demo;

import java.io.IOException;

public class FilterController
{
    public String dati(String filtro)throws  IOException
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
