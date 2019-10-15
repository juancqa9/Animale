package com.example.demo.csv;

import com.example.demo.csv.Dataset;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;

import java.io.*;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AnalisiDati
{

    //Attributi

    private Dataset animale;	//Questa variabile andrà a contenere il dataset

    //Metodi


     // Costruttore della classe AnalisiDati. Quando viene istanziato un oggett il programma effettua il download
     // del dataset dall'URL di riferimento e crea un oggetto di tipo Dataset.
     /**
     * @throws IOException La clausola throws genera un’eccezione di tipo IOException, per il  download del file
     * in formato CSV, da cui si ottiene il Dataset
     */
    public AnalisiDati() throws IOException
    {
        String URL="http://data.europa.eu/euodp/data/api/3/action/package_show?id=ef78194c-e94c-47dd-85f7-44ba98b6746c";	//Url di riferimento
        animale = new Dataset(URL);  //Viene istanziato il dataset
    }
    // La procedura effettua il marshalling in Json dell'oggetto passato come parametro
    /**
     * @param obj param definisce il parametro di un metodo, obj deve essere trasformato in formato Json
     * @throws IOException La clausola throws genera un’eccezione di tipo IOException, per la scrittura de Json
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
     // Il metodo legge il file "temp.json" e restituisce una stringa contenete il Json
     /**
     * @return Indica i valori di ritorno di un metodo,in questo caso una stringa contenete il Json
     * @throws FileNotFoundException Eccezione lanciata in caso il file non venisse trovato
     * @throws IOException La clausola throws genera un’eccezione di tipo IOException, per la lettura del Json
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


     // Restituisce il dataset importato istanziando la classe AnalisiDati

    /**
     *
     * @return Indica i valori di ritorno di un metodo,in questo caso animale che conterrà il Dataset
     */

    public Dataset getDataset()
    {
        return animale;
    }
}
