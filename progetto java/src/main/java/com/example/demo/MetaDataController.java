package com.example.demo;

import java.io.IOException;

public class MetaDataController
{
    public String metadati() throws IOException
    {
        return OttieniJson();
    }

    private String OttieniJson() throws IOException
    {
        AnalisiDati obj=new AnalisiDati();
        obj.MarshallingJson(obj.getDataset().getMetadati());
        return obj.LeggiJson();
    }
}
