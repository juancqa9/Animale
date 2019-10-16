package com.example.demo.csv;


import com.example.demo.*;
import com.example.demo.model.Indice;
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
    //Attributi
    private String link;
    static String URL;
    private com.example.demo.csv.Metadati Metadati[];
    private Vector<Object> Dataset=new Vector<Object>();

    /**
     *
     * @param URL param definisce il parametro di un metodo, effettua il download del dataset
     * @throws IOException La clausola throws genera un’eccezione di tipo IOException, per la scrittura del csv
     */
         public Dataset(String URL) throws IOException
         {
        File file = new File("dati.csv");
         this.URL = URL;
        if (file.exists())
            ParsingCsv();
        else
            ImportaDataset();
         }

// ricava l'url da cui scaricare il dataset effettuando il parsing di un file Json, ottenuto il link effettuo il download
    /**
     *
     * @throws MalformedURLException l'eccezione viene lanciata se l'URL non è specificata in modo corretto
     * @throws IOException La clausola throws genera un’eccezione di tipo IOException, per la scrittura del csv
     */
    private  void ImportaDataset() throws MalformedURLException, IOException
    {

        URL = SetUrlDataset(DownloadJson());
        DownloadDataset();
        ParsingCsv();
    }

    /**
     *
     * @return Indica i valori di ritorno di un metodo,in questo caso contiene l'url in formato Json da cui ricavare i dati
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
            } finally
            {
                in.close();
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return dati;
    }
    //Il metodo effettua il parsing del json per ottenere l'URL da cui effettuare il download
    /**
     *
     * @param info param definisce il parametro di un metodo, contiene i dati in formato Json
     * @return Indica i valori di ritorno di un metodo,in questo caso effettua il download de dataset
     */
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
    /**
     *
     * @throws MalformedURLException l'eccezione viene lanciata se l'URL non è specificata in modo corretto
     * @throws IOException La clausola throws genera un’eccezione di tipo IOException, per la scrittura del file scaricato
     */
    private void DownloadDataset() throws MalformedURLException,IOException
    {
        try (InputStream in = URI.create(link).toURL().openStream())
        {
            Files.copy(in, Paths.get("dati.csv"));
        }
    }
    // Il metodo effettua il parsing del file in formato CSV e dispone i records all'interno delle apposite classi
    /**
     *
     * @throws IOException La clausola throws genera un’eccezione di tipo IOException, per la lettura del file scaricatto
     */
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
                        i++; //ciao
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
                if(Dati.length!=1) {
                    MarketPrice elemento=DisponiDati(Dati);  //Il metodo dispone i records all'interno dell'oggetto
                    Dataset.add(elemento);    //L'oggetto viene aggiunto a un vettore
                }

            }

        }
        finally
        {
            file.close();
        }
        file.close();
        CreaMetadati(SourceField);	//Vengono creati i metadati
    }
//Disponi i records all'interno dei vari campi dell'oggeto
    /**
     *
     * @param Dati param definisce il parametro di un metodo, Dati[] contiene i vari records
     * @return Indica i valori di ritorno di un metodo,in questo caso effettua l'elemento
     */
    private MarketPrice DisponiDati(String Dati[])
    {
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
//Vettore contenete i metadati del dataset
    /**
     *
     * @param SourceField param definisce il parametro di un metodo, contiene i nomi originali del records
     */
    private void CreaMetadati(String[] SourceField)
    {

        ArrayList<Field> Alias;	//Vettore contenente i campi utilizzati all'interno del programma per riferirsi ai vari attributi dei records
        Alias=new ArrayList<Field>(Arrays.asList(com.example.demo.Category.class.getDeclaredFields()));	//ArrayList contenente i campi di ospedale
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

    /**
     *
     * @return Indica i valori di ritorno di un metodo,in questo caso effettua  un vector contenente i metadati del dataset
     */
    public Metadati[] getMetadati()
    {
        return Metadati;
    }

    /**
     *
     * @return Indica i valori di ritorno di un metodo,in questo caso effettua  un vector contenente gli oggetti del dataset
     */
    public Vector<Object> getData()
    {
        return Dataset;
    }
    @Override
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
