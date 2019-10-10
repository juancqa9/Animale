package com.example.demo;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;

import java.io.*;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AnalisiDati
{

    //Attributi

    private Dataset animale;	//Questa variabile andrà a contenere il dataset

    //Metodi

    /**
     * Costruttore della classe AnalisiDati. Quando viene istanziato un oggetto
     * il programma effettua il download del dataset dall'URL di riferimento e
     * crea un oggetto di tipo Dataset.
     */
    public AnalisiDati() throws IOException
    {
        String URL="http://data.europa.eu/euodp/data/api/3/action/package_show?id=ef78194c-e94c-47dd-85f7-44ba98b6746c";	//Url di riferimento
        animale = new Dataset(URL);  //Viene istanziato il dataset
    }

    /**
     * La procedura effettua il marshalling in Json dell'oggetto passato come parametro
     * @param obj Oggetto da convertire in formato Json
     * @throws IOException Eccezione lanciata in caso di errori nella scrittura del Json
     */
    public void MarshallingJson(Object obj) throws IOException
    {
        FileWriter file=new FileWriter("temp.json");	//Crea un file per memorizzare l'oggetto obj in formato Json
        ObjectMapper mapper= new ObjectMapper();
        try
        {
            mapper.writeValue(file, obj);	//Scrive l'oggetto sul file
        }
        catch (JsonGenerationException e)	//Eccezione lanciata in caso di errore nella conversione di obj in formato Json
        {
            e.printStackTrace();
        }
        catch (JsonMappingException e)	//Eccezione lanciata in caso di errore nel mapping dell'oggetto
        {
            e.printStackTrace();
        }
        catch (IOException e)	//Eccezione lanciata in caso di errori nella scrittura del Json
        {
            e.printStackTrace();
            file.close();
        }
        file.close();

    }

     /**
     * Il metodo legge il file "temp.json" e restituisce una stringa contenete il Json
     */
    public String LeggiJson() throws FileNotFoundException, IOException
    {
        FileReader file=new FileReader("temp.json");	//Crea un riferimento al file temp.json
        BufferedReader reader = new BufferedReader(file);	//Lettura del file Json
        String line = reader.readLine();
        String Json=new String();
        while(line!=null)
        {
            Json+=line;
            line = reader.readLine();
        }
        file.close();
        return Json;
    }
    /**
     *
     * @return Restituisce il dataset importato istanziando la classe AnalisiDati
     */
    public Dataset getDataset()
    {
        return animale;
    }
}