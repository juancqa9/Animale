package com.example.demo;


import net.minidev.json.JSONValue;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;

public class Dataset implements Indice
{

    private String link;
    static String URL;
    private Metadati Metadati[];
    private Vector<Object> Dataset=new Vector<Object>();

         public Dataset(String URL) throws IOException
         {
        File file = new File("dati.csv");
         this.URL = URL;
        if (file.exists())
        {
            ParsingCsv();
            SaveToFile();
        } else
            ImportaDataset();
        SaveToFile();
         }




    private  void ImportaDataset() throws MalformedURLException, IOException
    {

        URL = SetUrlDataset(DownloadJson());
        DownloadDataset();
        ParsingCsv();
    }
    /**
     *
     * @return Viene restituita una stringa in fomato json contenente l'url da cui ricavare i dati
     */
    private  String DownloadJson()
    {
        String dati = new String();
        try {
            //Viene aperta una connessione con l'indirizzo specificato
            URLConnection openConnection = new URL(URL).openConnection();
            openConnection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");
            InputStream in = openConnection.getInputStream();    //Viene aperto un flusso di input per ottenere i dati json dalla pagina web
            String riga = "";
            try {
                InputStreamReader inR = new InputStreamReader(in);
                BufferedReader buffer = new BufferedReader(inR);
                while ((riga = buffer.readLine()) != null) //Attraverso il ciclo i dati nel flusso sono copiai in una stringa
                {
                    dati += riga;
                }
            } finally {
                in.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dati;
    }
    //Il metodo effettua il parsing del json per ottenere l'URL da cui effettuare il download

    private  String SetUrlDataset(String info)
    {
        String URL=new String();
        try
        {

            JSONObject json = new JSONObject(info);
            link =  (String) json.getJSONObject("result").getJSONArray("resources").getJSONObject(1).get("url");
        } catch(JSONException e)	//Cattura un'eventuale eccezione durante il parsing
        {
            e.printStackTrace();
        }
        return URL;
    }
   // Il metodo effettua il download del file CSV contenente i record del dataset
    private void DownloadDataset() throws MalformedURLException,IOException
    {
        try (InputStream in = URI.create(link).toURL().openStream())
        {
            Files.copy(in, Paths.get("dati.csv"));
        }
    }
    // Il metodo effettua il parsing del file in formato CSV e dispone i records all'interno delle apposite classi
    private  void ParsingCsv() throws IOException
    {

        FileReader file=new FileReader("dati.csv");	//Viene creato un riferimento al file csv
        String riga;
        String Dati[];
        String SourceField[];	//Vettore di stringhe contenente i nomi dei vari attributi dei records
        char StringaIn[];
        char StringaOut[];
        try
        {
            BufferedReader buffer=new BufferedReader(file);
            riga = buffer.readLine();
            SourceField=riga.split(",");	//La prima riga del file contiene il nome degli attributi separati da virgole
            while ( ( riga = buffer.readLine() ) != null )
            {
                StringaIn=riga.toCharArray();			//Otteniamo un array di caratteri dalla stringa contenente una riga di records
                StringaOut=new char[riga.length()];
                for(int i=0,j=0;i<riga.length()-1;i++,j++)	//Con questo ciclo for il programma sostituisce le virgole contenute nei numeri decimali con dei punti
                {											//ed elimina le virgolette che delimitano i vari records. Il risultato dell'operazione è contenuto
                    if(StringaIn[i]=='\"')					//nell'array di caratteri StringaOut
                    {
                        i++;
                        do
                        {
                            if(StringaIn[i]==',')
                            {
                                StringaOut[j++]='.';
                                i++;
                            }
                            else
                                StringaOut[j++]=StringaIn[i++];
                        } while(StringaIn[i]!='\"');

                    }
                    else
                    {
                        StringaOut[j]=StringaIn[i];
                    }
                }
                riga=new String(StringaOut);
                Dati=riga.split(",");	//I vari records delimitati da virgole vengono separati e inseriti in datistring


                MarketPrice elemento=DisponiDati(Dati);  //Il metodo dispone i records all'interno dell'oggetto

                Dataset.add(elemento);	//L'oggetto viene aggiunto a un vettore
            }
        }
        finally
        {
            file.close();
        }
        file.close();
        CreaMetadati(SourceField);	//Vengono creati i metadati
    }

    private MarketPrice DisponiDati(String[] Dati)
    {
      //  System.out.println("ciao");
        MarketPrice elemento=new MarketPrice(Dati[Category],Dati[Sector_Code]);
        try
        {
            elemento.setProductCode(Dati[Product_Code]);
        }
        catch(NumberFormatException e)	//Se la conversione della stringa in numero fallisce viene lanciata un'eccezione
        {
            elemento.setProductCode("");	//L'eccezione viene catturata dal catch e si imposta il valore a 0 di default
            e.printStackTrace();	//Stampa la traccia di stack
        }
        try
        {
            elemento.setProductDesc(Dati[Product_desc]);

        }
        catch(NumberFormatException e)
        {
            elemento.setProductDesc("");
            e.printStackTrace();
        }
        try
        {
            elemento.setProductBriefDescription(Dati[Product_Brief_Description]);
        }
        catch(NumberFormatException e)
        {
            elemento.setProductBriefDescription("");
            e.printStackTrace();
        }
        try
        {
            elemento.setUnit(Dati[Unit]);
        }
        catch(NumberFormatException e)
        {
            elemento.setUnit("");
            e.printStackTrace();
        }
        try
        {
            elemento.setCountry(Dati[Country]);
        }
        catch(NumberFormatException e)
        {
            elemento.setCountry("");
            e.printStackTrace();
        }
        try
        {
            elemento.setPeriod(Double.parseDouble(Dati[Period]));
        }
        catch(NumberFormatException e)	//Se la conversione della stringa in numero fallisce viene lanciata un'eccezione
        {
            elemento.setPeriod(0);	//L'eccezione   viene catturata dal catch e si imposta il valore a 0 di default
            e.printStackTrace();	//Stampa la traccia di stack
        }
        try
        {
            elemento.setMPMarketPrice(Double.parseDouble(Dati[MP_Market_Price]));
        }
        catch(NumberFormatException e)	//Se la conversione della stringa in numero fallisce viene lanciata un'eccezione
        {
            elemento.setMPMarketPrice(0);	//L'eccezione   viene catturata dal catch e si imposta il valore a 0 di default
            e.printStackTrace();	//Stampa la traccia di stack
        }

        return elemento;
    }

    private void CreaMetadati(String[] SourceField)
    {

        ArrayList<Field> Alias;	//Vettore contenente i campi utilizzati all'interno del programma per riferirsi ai vari attributi dei records
        Alias=new ArrayList<Field>(Arrays.asList(Category.class.getDeclaredFields()));	//ArrayList contenente i campi di ospedale
        ArrayList<Field> temp=new ArrayList<Field>(Arrays.asList(Product.class.getDeclaredFields()));	//ArrayList contenente i campi di prontosoccorso
        Alias.addAll(temp);	//Aggiunge temp ad alias
        temp=new ArrayList<Field>(Arrays.asList(CountryProduct.class.getDeclaredFields()));	//ArrayList contenente i campi di permanenza
        Alias.addAll(temp);	//Aggiunge temp ad alias
        temp=new ArrayList<Field>(Arrays.asList(MarketPrice.class.getDeclaredFields()));	//ArrayList contenente i campi di osservazionebreveintensiva
        Alias.addAll(temp);	//Aggiunge temp ad alias
        Object AliasName[]=Alias.toArray();
        Metadati=new Metadati[SourceField.length];
        for(int i=0;i<SourceField.length;i++)
        {
            Metadati[i]=new Metadati((Field)AliasName[i],SourceField[i]);	//Crea un vettore di metadati
        }
    }

    private  void SaveToFile() throws IOException
    {
        FileWriter file=new FileWriter("dati.txt");
        BufferedWriter buffer=new BufferedWriter(file);
        buffer.write(toString());
        file.close();
    }
    public Metadati[] getMetadati()
    {
        return Metadati;
    }
    public Vector<Object> getData()
    {
        return Dataset;
    }
    public String toString()
    {
        String Stringa=new String();
        for(Object o:Dataset)
        {
            Stringa+=o;
            Stringa+="\n\n";
        }
        return Stringa;
    }

}
