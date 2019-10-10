package com.example.demo;

import java.io.IOException;

public class DataController
{
    public String dati() throws IOException
    {
        return OttieniJson();
    }
    public String OttieniJson() throws IOException
    {
        AnalisiDati obj=new AnalisiDati();
        obj.MarshallingJson(obj.getDataset().getData());
        return obj.LeggiJson();
    }
}
