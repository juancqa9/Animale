package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
@Controller


public class MetaDataController
{
    @RequestMapping("/metadata")
    @ResponseBody
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
