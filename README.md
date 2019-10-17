# Progetto di programmazione orientata agli oggetti

Benvenuti alla repository di Denis Prendi e Juan Carlos Guzman Morales, dopo l'assegnamento da parte del prof. Adriano Mancini del progetto di programmazione orientata agli oggetti, abbiamo visionato l'URL:  http://data.europa.eu/euodp/data/api/3/action/package_show?id=ef78194c-e94c-47dd-85f7-44ba98b6746c . Successivamente abbiamo realizzato il download del data-set (zip o csv) che contiene dati in formati CSV partendo dall’indirizzo fornito dopo opportuna decodifica del JSON.

Dopo averlo scaricato viene realizzato il metodo parsing, che viene utilizzata per leggere e analizzare il file CSV che contiene i dati del prezzo del mercato dei prodotti animali.

La prima riga contiene i metadati, le successive righe contengono le informazioni del prodotto.

La riga è descritta dai seguenti attributi:

1.Category.

2.SectorCode.

3.ProductCode.

4.ProductDesc.

5.ProductBriefDescription.

6.Unit.

7.Country.

8.Period.

9.MPMarketPrice.

-**Su richiesta:**  utilizzando API REST GET o POST, con rotte distinte usando Spring Boot vengono restituite:

- I metadati (formato JSON) ovvero elenco degli attributi e del tipo 

- I dati (formato JSON); 

- le statistiche sui dati (formato JSON) che specificando l’attributo sul quale effettuare la computazione (colonna dei dati) quali: 

- Numeri: o avg, min, max, dev std, sum, count 

- Stringhe o Conteggio elementi unici (per ogni elemento unico indicare il numero di occorrenze). La restituzione deve prevedere la possibilità di specificare in fase di richiesta una serie di filtri su attributi con operatori condizionali e logici.

## Dataset

Restituisce un file dati.Csv, se questo file esiste viene realizzato un metodo parsing del CSV, altrimenti importa il dataset.

Esempio: localhost:8080/data 

Nota: L'esempio è giusto ma è troppo grande da inserire nel Json (come abbiamo fatto successivamente nel controllo  http://localhost:8080/filter/{"Period":{"$gte" : 199101}}  ).

### Metadati

Istanzia gli oggetti contenenti metadati attraverso i parametri passati.

Esempio: localhost:8080/metadata

```json
[{"type":"String","alias":"Category","sourceField":"﻿\"Category\""},{"type":"String","alias":"SectorCode","sourceField":"\"Sector code\""},{"type":"String","alias":"ProductCode","sourceField":"\"Product code\""},{"type":"String","alias":"ProductDesc","sourceField":"\"Product desc\""},{"type":"String","alias":"ProductBriefDescription","sourceField":"\"Product Brief Description\""},{"type":"String","alias":"Unit","sourceField":"\"Unit\""},{"type":"String","alias":"Country","sourceField":"\"Country\""},{"type":"double","alias":"Period","sourceField":"\"Period\""},{"type":"double","alias":"MPMarketPrice","sourceField":"\"MP Market Price\""}]
```



### Statistica 

Istanzia un vettore di tipo double, se il parametro passato è di tipo double ne calcola le statistiche, mentre istanzia un vettore di tipo parola, se il parametro passato è di tipo stringa, ne calcola le occorrenze. 

```java
 public StatisticheDati(ArrayList<Object> vettore)
    {
        if(vettore.get(0) instanceof Double)
        {
            ArrayNumber=vettore.toArray(new Double[vettore.size()]);
            Somma();
            Count();
            Media();
            DeviazioneStandard();
            Minimo();
            Massimo();
        }
        if(vettore.get(0) instanceof String)
        {
            ArrayString=vettore.toArray(new String[vettore.size()]);
            Occorrenze();
        }
    }
```



- Media:  metodo che calcola la media e imposta la relativa variabile d'istanza.

  ```java
     public void Media()
      {
          media=somma/count;
      }
  ```

  

- Count: metodo che conta il numero di valori e imposta la relativa variabile d'istanza.

  ```java
      public void Count()
      {
          int i;
          for(i=0;i<ArrayNumber.length;i++);
          count=i;
      }
  ```

  

- Minimo: metodo che calcola il minimo e imposta relativa variabile d'istanza.

  ```java
  public void Minimo()
      {
          double min=ArrayNumber[0];
          for(int i=0;i<ArrayNumber.length;i++)
          {
              if(ArrayNumber[i]<min)
                  min=ArrayNumber[i];
          }
          minimo=min;
      }
  ```

  

- Massimo: metodo che calcola il massimo e imposta la relativa variabile d'istanza.

  ```java
      public void Massimo()
      {
          double max=ArrayNumber[0];
          for(int i=0;i<ArrayNumber.length;i++)
          {
              if(ArrayNumber[i]>max)
                  max=ArrayNumber[i];
          }
          massimo=max;
      }
  ```

  

- DeviazioneStandard: metodo che calcola la deviazione standard e imposta la relativa variabile d'istanza.

  ```java
   public void DeviazioneStandard()
      {
          double varianza=0;
          for(int i=0;i<ArrayNumber.length;i++)
          {
              varianza+=(ArrayNumber[i]-media)*(ArrayNumber[i]-media);
          }
          deviazione_standard=Math.sqrt(varianza/count);
      }
  ```

  

- Somma: metodo che calcola la somma e imposta la relativa variabile d'istanza.

  ```java
      public void Somma()
      {
          double somma=0;
          for(int i=0;i<ArrayNumber.length;i++)
          {
              somma+=ArrayNumber[i];
          }
          this.somma=somma;
      }
  ```

  

   Esempio Json: 

   http://localhost:8080/statistica/MPmarketPrice

  ```json
  {"media":231.5774009529025,"minimo":0.0,"massimo":6402.74,"somma":1.448424012000024E7,"count":62546,"deviazioneStandard":127.64419768505668,"occorrenze":null}
  
  ```

  

### Filtro

 Genera le condizioni necessarie per essere poi richiamate dai vari operatori.

E l'operatore logico realizza il metodo che seleziona la funziona logica adatta in relazione all'operatore(AND, NOT e OR) passato per parametro.

Funzione Logica AND, ottiene come risultato un vettore contenente l'intersezione tra i vettori passati come argomenti. Il vettore risultante contiene gli elementi in comune tra i due vettori.

```java
 private void and(Vector<Object> vettore1,Vector<Object> vettore2)
    {
        vettoreOut=new Vector<Object>();
        for(int i=0;i<vettore1.size();i++)
        {
            Object temp=vettore1.get(i);	//temp diventa un riferimento a un elemento del vettore 1
            if(contains(vettore2,temp))	//Se vettore 2 contiene temp, temp viene aggiiunto al vettore in uscita
                vettoreOut.add(temp);
        }
    }

```

Funzione logica OR, ottiene come risultato un vettore contenente l'unione tra i vettori passati come argomenti. Il vettore risultante contiene l'unione degli elementi presenti nei due vettori.

```java
 private void or(Vector<Object> vettore1,Vector<Object> vettore2)
    {
        vettoreOut=new Vector<Object>();
        for(int i=0;i<vettore1.size();i++)
        {
            Object temp=vettore1.get(i);
            vettoreOut.add(temp);	//Aggiunge gli elementi del primo vettore al vettore in uscita
        }
        for(int i=0;i<vettore2.size();i++)
        {
            Object temp=vettore2.get(i);
            if(!contains(vettoreOut,temp))	//Se l'elemento a cui fa riferimento temp non è contenuto nel vettore in uscita viene aggiunto
                vettoreOut.add(temp);
        }
    }

```

Funziona logica NOT, ottiene come risultato un vettore complementare a quello passato come argomento. Il vettore risultante contiene gli elementi del dataset che non sono presenti nel vettore passato come argomento.

```java
public void not(Vector<Object> vettore)
    {
        vettoreOut=new Vector<Object>();
        for(int i=0;i<vettore.size();i++)
        {
            Object temp=vettore.get(i);
            if(!contains(vettore,temp))	//Se il vettore non contiene il valore a cui fa riferimento temp esso viene aggiunto al vettore in uscita
                vettoreOut.add(temp);
        }
    }

```



E l'operatore Condizionale realizza il metodo che seleziona la funziona condizionale adatta in relazione al segno (<, >, <= , >=, (<= e >= ).

```java
private boolean Segno(String segno,double val, double...valore)
    {
        switch(segno)
        {
            case "$gt":
                if(val>valore[0])
                    return true;
                else
                    return false;
            case "$gte":
                if(val>=valore[0])
                    return true;
                else
                    return false;
            case "$it":
                if(val<valore[0])
                    return true;
                else
                    return false;
            case "$ite":
                if(val<=valore[0])
                    return true;
                else
                    return false;
            case "$bt":
                if((valore[0]<=val)&&(valore[1]>=val))
                    return true;
                else
                    return false;
        }
        return false;
    }

```

Esempio Json: 

 http://localhost:8080/filter/{"Period":{"$gte" : 199101}} 

```json
[{"mpmarketPrice":358.7,"country":"AT\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Male Bovines\u0000","productCode":"ALL R3\u0000","productBriefDescription":"Male Bovines - Gros bovins males. R3\u0000","sectorCode":"BOV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":301.95,"country":"BE\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Male Bovines\u0000","productCode":"ALL R3\u0000","productBriefDescription":"Male Bovines - Gros bovins males. R3\u0000","sectorCode":"BOV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":327.24,"country":"CZ\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Male Bovines\u0000","productCode":"ALL R3\u0000","productBriefDescription":"Male Bovines - Gros bovins males. R3\u0000","sectorCode":"BOV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":352.09,"country":"DE\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Male Bovines\u0000","productCode":"ALL R3\u0000","productBriefDescription":"Male Bovines - Gros bovins males. R3\u0000","sectorCode":"BOV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":335.25,"country":"DK\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Male Bovines\u0000","productCode":"ALL R3\u0000","productBriefDescription":"Male Bovines - Gros bovins males. R3\u0000","sectorCode":"BOV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":296.25,"country":"EE\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Male Bovines\u0000","productCode":"ALL R3\u0000","productBriefDescription":"Male Bovines - Gros bovins males. R3\u0000","sectorCode":"BOV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":348.32,"country":"ES\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Male Bovines\u0000","productCode":"ALL R3\u0000","productBriefDescription":"Male Bovines - Gros bovins males. R3\u0000","sectorCode":"BOV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":350.97,"country":"EU\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Male Bovines\u0000","productCode":"ALL R3\u0000","productBriefDescription":"Male Bovines - Gros bovins males. R3\u0000","sectorCode":"BOV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":399.03,"country":"FI\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Male Bovines\u0000","productCode":"ALL R3\u0000","productBriefDescription":"Male Bovines - Gros bovins males. R3\u0000","sectorCode":"BOV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":368.88,"country":"FR\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Male Bovines\u0000","productCode":"ALL R3\u0000","productBriefDescription":"Male Bovines - Gros bovins males. R3\u0000","sectorCode":"BOV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":373.87,"country":"GR\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Male Bovines\u0000","productCode":"ALL R3\u0000","productBriefDescription":"Male Bovines - Gros bovins males. R3\u0000","sectorCode":"BOV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":353.73,"country":"HR\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Male Bovines\u0000","productCode":"ALL R3\u0000","productBriefDescription":"Male Bovines - Gros bovins males. R3\u0000","sectorCode":"BOV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":344.35,"country":"IE\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Male Bovines\u0000","productCode":"ALL R3\u0000","productBriefDescription":"Male Bovines - Gros bovins males.
R3\u0000","sectorCode":"BOV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":404.28,"country":"IT\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Male Bovines\u0000","productCode":"ALL R3\u0000","productBriefDescription":"Male Bovines - Gros bovins males. R3\u0000","sectorCode":"BOV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":277.6,"country":"LT\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Male Bovines\u0000","productCode":"ALL R3\u0000","productBriefDescription":"Male Bovines - Gros bovins males. R3\u0000","sectorCode":"BOV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":351.58,"country":"LU\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Male Bovines\u0000","productCode":"ALL R3\u0000","productBriefDescription":"Male Bovines - Gros bovins males. R3\u0000","sectorCode":"BOV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":259.05,"country":"LV\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Male Bovines\u0000","productCode":"ALL R3\u0000","productBriefDescription":"Male Bovines - Gros bovins males. R3\u0000","sectorCode":"BOV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":360.87,"country":"MT\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Male Bovines\u0000","productCode":"ALL R3\u0000","productBriefDescription":"Male Bovines - Gros bovins males. R3\u0000","sectorCode":"BOV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":338.84,"country":"NL\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Male Bovines\u0000","productCode":"ALL R3\u0000","productBriefDescription":"Male Bovines - Gros bovins males. R3\u0000","sectorCode":"BOV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":288.16,"country":"PL\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Male Bovines\u0000","productCode":"ALL R3\u0000","productBriefDescription":"Male Bovines - Gros bovins males. R3\u0000","sectorCode":"BOV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":380.64,"country":"PT\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Male Bovines\u0000","productCode":"ALL R3\u0000","productBriefDescription":"Male Bovines - Gros bovins males. R3\u0000","sectorCode":"BOV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":326.63,"country":"RO\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Male Bovines\u0000","productCode":"ALL R3\u0000","productBriefDescription":"Male Bovines - Gros bovins males. R3\u0000","sectorCode":"BOV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":396.75,"country":"SE\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Male Bovines\u0000","productCode":"ALL R3\u0000","productBriefDescription":"Male Bovines - Gros bovins males. R3\u0000","sectorCode":"BOV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":338.59,"country":"SI\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Male Bovines\u0000","productCode":"ALL R3\u0000","productBriefDescription":"Male Bovines - Gros bovins males. R3\u0000","sectorCode":"BOV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":350.64,"country":"SK\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Male Bovines\u0000","productCode":"ALL R3\u0000","productBriefDescription":"Male Bovines - Gros bovins males. R3\u0000","sectorCode":"BOV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":362.07,"country":"UK\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Male Bovines\u0000","productCode":"ALL R3\u0000","productBriefDescription":"Male Bovines - Gros bovins males.
R3\u0000","sectorCode":"BOV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":358.7,"country":"AT\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Young Bulls\u0000","productCode":"A R3\u0000","productBriefDescription":"Young Bulls - Jeunes taureaux. R3\u0000","sectorCode":"BOV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":301.95,"country":"BE\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Young Bulls\u0000","productCode":"A R3\u0000","productBriefDescription":"Young Bulls - Jeunes taureaux. R3\u0000","sectorCode":"BOV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":327.24,"country":"CZ\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Young Bulls\u0000","productCode":"A R3\u0000","productBriefDescription":"Young Bulls - Jeunes taureaux. R3\u0000","sectorCode":"BOV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":352.09,"country":"DE\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Young Bulls\u0000","productCode":"A R3\u0000","productBriefDescription":"Young Bulls - Jeunes taureaux. R3\u0000","sectorCode":"BOV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":335.25,"country":"DK\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Young Bulls\u0000","productCode":"A R3\u0000","productBriefDescription":"Young Bulls - Jeunes taureaux. R3\u0000","sectorCode":"BOV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":348.32,"country":"ES\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Young Bulls\u0000","productCode":"A R3\u0000","productBriefDescription":"Young Bulls - Jeunes taureaux. R3\u0000","sectorCode":"BOV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":347.99,"country":"EU\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Young Bulls\u0000","productCode":"A R3\u0000","productBriefDescription":"Young Bulls - Jeunes taureaux. R3\u0000","sectorCode":"BOV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":399.03,"country":"FI\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Young Bulls\u0000","productCode":"A R3\u0000","productBriefDescription":"Young Bulls - Jeunes taureaux. R3\u0000","sectorCode":"BOV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":373.28,"country":"FR\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Young Bulls\u0000","productCode":"A R3\u0000","productBriefDescription":"Young Bulls - Jeunes taureaux. R3\u0000","sectorCode":"BOV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":373.87,"country":"GR\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Young Bulls\u0000","productCode":"A R3\u0000","productBriefDescription":"Young Bulls - Jeunes taureaux. R3\u0000","sectorCode":"BOV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":353.73,"country":"HR\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Young Bulls\u0000","productCode":"A R3\u0000","productBriefDescription":"Young Bulls - Jeunes taureaux. R3\u0000","sectorCode":"BOV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":332.73,"country":"IE\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Young Bulls\u0000","productCode":"A R3\u0000","productBriefDescription":"Young Bulls - Jeunes taureaux. R3\u0000","sectorCode":"BOV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":404.28,"country":"IT\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Young Bulls\u0000","productCode":"A R3\u0000","productBriefDescription":"Young Bulls - Jeunes taureaux. R3\u0000","sectorCode":"BOV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":277.6,"country":"LT\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Young Bulls\u0000","productCode":"A R3\u0000","productBriefDescription":"Young Bulls - Jeunes taureaux.
R3\u0000","sectorCode":"BOV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":351.58,"country":"LU\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Young Bulls\u0000","productCode":"A R3\u0000","productBriefDescription":"Young Bulls - Jeunes taureaux. R3\u0000","sectorCode":"BOV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":259.05,"country":"LV\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Young Bulls\u0000","productCode":"A R3\u0000","productBriefDescription":"Young Bulls - Jeunes taureaux. R3\u0000","sectorCode":"BOV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":360.87,"country":"MT\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Young Bulls\u0000","productCode":"A R3\u0000","productBriefDescription":"Young Bulls - Jeunes taureaux. R3\u0000","sectorCode":"BOV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":338.84,"country":"NL\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Young Bulls\u0000","productCode":"A R3\u0000","productBriefDescription":"Young Bulls - Jeunes taureaux. R3\u0000","sectorCode":"BOV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":288.16,"country":"PL\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Young Bulls\u0000","productCode":"A R3\u0000","productBriefDescription":"Young Bulls - Jeunes taureaux. R3\u0000","sectorCode":"BOV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":380.64,"country":"PT\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Young Bulls\u0000","productCode":"A R3\u0000","productBriefDescription":"Young Bulls - Jeunes taureaux. R3\u0000","sectorCode":"BOV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":326.63,"country":"RO\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Young Bulls\u0000","productCode":"A R3\u0000","productBriefDescription":"Young Bulls - Jeunes taureaux. R3\u0000","sectorCode":"BOV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":396.75,"country":"SE\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Young Bulls\u0000","productCode":"A R3\u0000","productBriefDescription":"Young Bulls - Jeunes taureaux. R3\u0000","sectorCode":"BOV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":338.59,"country":"SI\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Young Bulls\u0000","productCode":"A R3\u0000","productBriefDescription":"Young Bulls - Jeunes taureaux. R3\u0000","sectorCode":"BOV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":350.64,"country":"SK\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Young Bulls\u0000","productCode":"A R3\u0000","productBriefDescription":"Young Bulls - Jeunes taureaux. R3\u0000","sectorCode":"BOV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":345.01,"country":"UK\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Young Bulls\u0000","productCode":"A R3\u0000","productBriefDescription":"Young Bulls - Jeunes taureaux. R3\u0000","sectorCode":"BOV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":397.36,"country":"AT\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Steers\u0000","productCode":"C R3\u0000","productBriefDescription":"Steers - Bœufs. R3\u0000","sectorCode":"BOV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":252.45,"country":"CZ\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Steers\u0000","productCode":"C R3\u0000","productBriefDescription":"Steers - Bœufs. R3\u0000","sectorCode":"BOV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":416.6,"country":"DE\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Steers\u0000","productCode":"C R3\u0000","productBriefDescription":"Steers - Bœufs.
R3\u0000","sectorCode":"BOV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":417.62,"country":"DK\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Steers\u0000","productCode":"C R3\u0000","productBriefDescription":"Steers - Bœufs. R3\u0000","sectorCode":"BOV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":357.2,"country":"EU\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Steers\u0000","productCode":"C R3\u0000","productBriefDescription":"Steers - Bœufs. R3\u0000","sectorCode":"BOV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":364.48,"country":"FR\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Steers\u0000","productCode":"C R3\u0000","productBriefDescription":"Steers - Bœufs. R3\u0000","sectorCode":"BOV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":344.35,"country":"IE\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Steers\u0000","productCode":"C R3\u0000","productBriefDescription":"Steers - Bœufs. R3\u0000","sectorCode":"BOV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":369.33,"country":"LU\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Steers\u0000","productCode":"C R3\u0000","productBriefDescription":"Steers - Bœufs. R3\u0000","sectorCode":"BOV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":336.18,"country":"RO\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Steers\u0000","productCode":"C R3\u0000","productBriefDescription":"Steers - Bœufs. R3\u0000","sectorCode":"BOV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":400.55,"country":"SE\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Steers\u0000","productCode":"C R3\u0000","productBriefDescription":"Steers - Bœufs. R3\u0000","sectorCode":"BOV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":348.92,"country":"SK\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Steers\u0000","productCode":"C R3\u0000","productBriefDescription":"Steers - Bœufs. R3\u0000","sectorCode":"BOV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":362.07,"country":"UK\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Steers\u0000","productCode":"C R3\u0000","productBriefDescription":"Steers - Bœufs. R3\u0000","sectorCode":"BOV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":260.76,"country":"AT\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Cows\u0000","productCode":"D O3\u0000","productBriefDescription":"Cows - Vaches. O3\u0000","sectorCode":"BOV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":272.54,"country":"BE\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Cows\u0000","productCode":"D O3\u0000","productBriefDescription":"Cows - Vaches. O3\u0000","sectorCode":"BOV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":231.17,"country":"BG\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Cows\u0000","productCode":"D O3\u0000","productBriefDescription":"Cows - Vaches. O3\u0000","sectorCode":"BOV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":246.41,"country":"CZ\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Cows\u0000","productCode":"D O3\u0000","productBriefDescription":"Cows - Vaches. O3\u0000","sectorCode":"BOV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":291.24,"country":"DE\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Cows\u0000","productCode":"D O3\u0000","productBriefDescription":"Cows - Vaches. O3\u0000","sectorCode":"BOV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":287.44,"country":"DK\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Cows\u0000","productCode":"D O3\u0000","productBriefDescription":"Cows - Vaches.
O3\u0000","sectorCode":"BOV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":249.07,"country":"EE\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Cows\u0000","productCode":"D O3\u0000","productBriefDescription":"Cows - Vaches. O3\u0000","sectorCode":"BOV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":258.04,"country":"ES\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Cows\u0000","productCode":"D O3\u0000","productBriefDescription":"Cows - Vaches. O3\u0000","sectorCode":"BOV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":281.96,"country":"EU\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Cows\u0000","productCode":"D O3\u0000","productBriefDescription":"Cows - Vaches. O3\u0000","sectorCode":"BOV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":259.08,"country":"FI\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Cows\u0000","productCode":"D O3\u0000","productBriefDescription":"Cows - Vaches. O3\u0000","sectorCode":"BOV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":321.56,"country":"FR\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Cows\u0000","productCode":"D O3\u0000","productBriefDescription":"Cows - Vaches. O3\u0000","sectorCode":"BOV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":184.56,"country":"GR\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Cows\u0000","productCode":"D O3\u0000","productBriefDescription":"Cows - Vaches. O3\u0000","sectorCode":"BOV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":227.23,"country":"HR\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Cows\u0000","productCode":"D O3\u0000","productBriefDescription":"Cows - Vaches. O3\u0000","sectorCode":"BOV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":216.01,"country":"HU\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Cows\u0000","productCode":"D O3\u0000","productBriefDescription":"Cows - Vaches. O3\u0000","sectorCode":"BOV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":282.8,"country":"IE\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Cows\u0000","productCode":"D O3\u0000","productBriefDescription":"Cows - Vaches. O3\u0000","sectorCode":"BOV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":272.88,"country":"IT\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Cows\u0000","productCode":"D O3\u0000","productBriefDescription":"Cows - Vaches. O3\u0000","sectorCode":"BOV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":253.97,"country":"LT\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Cows\u0000","productCode":"D O3\u0000","productBriefDescription":"Cows - Vaches. O3\u0000","sectorCode":"BOV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":295.42,"country":"LU\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Cows\u0000","productCode":"D O3\u0000","productBriefDescription":"Cows - Vaches. O3\u0000","sectorCode":"BOV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":217.24,"country":"LV\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Cows\u0000","productCode":"D O3\u0000","productBriefDescription":"Cows - Vaches. O3\u0000","sectorCode":"BOV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":213.38,"country":"MT\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Cows\u0000","productCode":"D O3\u0000","productBriefDescription":"Cows - Vaches. O3\u0000","sectorCode":"BOV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":282.48,"country":"NL\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Cows\u0000","productCode":"D O3\u0000","productBriefDescription":"Cows - Vaches.
O3\u0000","sectorCode":"BOV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":247.15,"country":"PL\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Cows\u0000","productCode":"D O3\u0000","productBriefDescription":"Cows - Vaches. O3\u0000","sectorCode":"BOV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":213.94,"country":"PT\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Cows\u0000","productCode":"D O3\u0000","productBriefDescription":"Cows - Vaches. O3\u0000","sectorCode":"BOV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":251.85,"country":"RO\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Cows\u0000","productCode":"D O3\u0000","productBriefDescription":"Cows - Vaches. O3\u0000","sectorCode":"BOV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":359.4,"country":"SE\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Cows\u0000","productCode":"D O3\u0000","productBriefDescription":"Cows - Vaches. O3\u0000","sectorCode":"BOV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":237.03,"country":"SI\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Cows\u0000","productCode":"D O3\u0000","productBriefDescription":"Cows - Vaches. O3\u0000","sectorCode":"BOV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":275.25,"country":"UK\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Cows\u0000","productCode":"D O3\u0000","productBriefDescription":"Cows - Vaches. O3\u0000","sectorCode":"BOV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":351.84,"country":"AT\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Heifers\u0000","productCode":"E R3\u0000","productBriefDescription":"Heifers - Genisses. R3\u0000","sectorCode":"BOV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":325.29,"country":"BE\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Heifers\u0000","productCode":"E R3\u0000","productBriefDescription":"Heifers - Genisses. R3\u0000","sectorCode":"BOV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":281.63,"country":"CZ\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Heifers\u0000","productCode":"E R3\u0000","productBriefDescription":"Heifers - Genisses. R3\u0000","sectorCode":"BOV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":351.69,"country":"DE\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Heifers\u0000","productCode":"E R3\u0000","productBriefDescription":"Heifers - Genisses. R3\u0000","sectorCode":"BOV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":340.33,"country":"DK\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Heifers\u0000","productCode":"E R3\u0000","productBriefDescription":"Heifers - Genisses. R3\u0000","sectorCode":"BOV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":376.52,"country":"ES\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Heifers\u0000","productCode":"E R3\u0000","productBriefDescription":"Heifers - Genisses. R3\u0000","sectorCode":"BOV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":362.96,"country":"EU\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Heifers\u0000","productCode":"E R3\u0000","productBriefDescription":"Heifers - Genisses. R3\u0000","sectorCode":"BOV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":389.29,"country":"FI\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Heifers\u0000","productCode":"E R3\u0000","productBriefDescription":"Heifers - Genisses.
R3\u0000","sectorCode":"BOV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":390.72,"country":"FR\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Heifers\u0000","productCode":"E R3\u0000","productBriefDescription":"Heifers - Genisses. R3\u0000","sectorCode":"BOV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":366.66,"country":"HR\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Heifers\u0000","productCode":"E R3\u0000","productBriefDescription":"Heifers - Genisses. R3\u0000","sectorCode":"BOV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":210.45,"country":"HU\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Heifers\u0000","productCode":"E R3\u0000","productBriefDescription":"Heifers - Genisses. R3\u0000","sectorCode":"BOV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":354.07,"country":"IE\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Heifers\u0000","productCode":"E R3\u0000","productBriefDescription":"Heifers - Genisses. R3\u0000","sectorCode":"BOV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":368.26,"country":"IT\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Heifers\u0000","productCode":"E R3\u0000","productBriefDescription":"Heifers - Genisses. R3\u0000","sectorCode":"BOV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":269.44,"country":"LT\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Heifers\u0000","productCode":"E R3\u0000","productBriefDescription":"Heifers - Genisses. R3\u0000","sectorCode":"BOV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":402.37,"country":"LU\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Heifers\u0000","productCode":"E R3\u0000","productBriefDescription":"Heifers - Genisses. R3\u0000","sectorCode":"BOV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":229.3,"country":"LV\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Heifers\u0000","productCode":"E R3\u0000","productBriefDescription":"Heifers - Genisses. R3\u0000","sectorCode":"BOV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":311.97,"country":"MT\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Heifers\u0000","productCode":"E R3\u0000","productBriefDescription":"Heifers - Genisses. R3\u0000","sectorCode":"BOV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":298.0,"country":"NL\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Heifers\u0000","productCode":"E R3\u0000","productBriefDescription":"Heifers - Genisses. R3\u0000","sectorCode":"BOV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":305.86,"country":"PL\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Heifers\u0000","productCode":"E R3\u0000","productBriefDescription":"Heifers - Genisses. R3\u0000","sectorCode":"BOV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":376.96,"country":"PT\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Heifers\u0000","productCode":"E R3\u0000","productBriefDescription":"Heifers - Genisses. R3\u0000","sectorCode":"BOV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":304.45,"country":"RO\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Heifers\u0000","productCode":"E R3\u0000","productBriefDescription":"Heifers - Genisses. R3\u0000","sectorCode":"BOV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":391.69,"country":"SE\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Heifers\u0000","productCode":"E R3\u0000","productBriefDescription":"Heifers - Genisses.
R3\u0000","sectorCode":"BOV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":330.64,"country":"SI\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Heifers\u0000","productCode":"E R3\u0000","productBriefDescription":"Heifers - Genisses. R3\u0000","sectorCode":"BOV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":281.6,"country":"SK\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Heifers\u0000","productCode":"E R3\u0000","productBriefDescription":"Heifers - Genisses. R3\u0000","sectorCode":"BOV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":362.92,"country":"UK\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Heifers\u0000","productCode":"E R3\u0000","productBriefDescription":"Heifers - Genisses. R3\u0000","sectorCode":"BOV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":451.39,"country":"AT\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Young Bovines\u0000","productCode":"Z R3\u0000","productBriefDescription":"Young Bovines - Jeunes bovins. R3\u0000","sectorCode":"BOV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":345.73,"country":"DK\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Young Bovines\u0000","productCode":"Z R3\u0000","productBriefDescription":"Young Bovines - Jeunes bovins. R3\u0000","sectorCode":"BOV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":380.91,"country":"ES\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Young Bovines\u0000","productCode":"Z R3\u0000","productBriefDescription":"Young Bovines - Jeunes bovins. R3\u0000","sectorCode":"BOV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":359.06,"country":"EU\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Young Bovines\u0000","productCode":"Z R3\u0000","productBriefDescription":"Young Bovines - Jeunes bovins. R3\u0000","sectorCode":"BOV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":336.64,"country":"IE\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Young Bovines\u0000","productCode":"Z R3\u0000","productBriefDescription":"Young Bovines - Jeunes bovins. R3\u0000","sectorCode":"BOV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":345.37,"country":"IT\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Young Bovines\u0000","productCode":"Z R3\u0000","productBriefDescription":"Young Bovines - Jeunes bovins. R3\u0000","sectorCode":"BOV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":333.94,"country":"NL\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Young Bovines\u0000","productCode":"Z R3\u0000","productBriefDescription":"Young Bovines - Jeunes bovins. R3\u0000","sectorCode":"BOV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":382.74,"country":"PT\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Young Bovines\u0000","productCode":"Z R3\u0000","productBriefDescription":"Young Bovines - Jeunes bovins. R3\u0000","sectorCode":"BOV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":279.86,"country":"RO\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Young Bovines\u0000","productCode":"Z R3\u0000","productBriefDescription":"Young Bovines - Jeunes bovins. R3\u0000","sectorCode":"BOV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":417.61,"country":"SE\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Young Bovines\u0000","productCode":"Z R3\u0000","productBriefDescription":"Young Bovines - Jeunes bovins. R3\u0000","sectorCode":"BOV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":443.78,"country":"SK\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Young Bovines\u0000","productCode":"Z R3\u0000","productBriefDescription":"Young Bovines - Jeunes bovins.
R3\u0000","sectorCode":"BOV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":236.34,"country":"AT\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Chicken\u0000","productCode":"0207 11 30\u0000","productBriefDescription":"Broiler - Poulet. 65%\u0000","sectorCode":"OEV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":161.45,"country":"BE\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Chicken\u0000","productCode":"0207 11 30\u0000","productBriefDescription":"Broiler - Poulet. 65%\u0000","sectorCode":"OEV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":152.46,"country":"BG\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Chicken\u0000","productCode":"0207 11 30\u0000","productBriefDescription":"Broiler - Poulet. 65%\u0000","sectorCode":"OEV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":251.4,"country":"CY\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Chicken\u0000","productCode":"0207 11 30\u0000","productBriefDescription":"Broiler - Poulet. 65%\u0000","sectorCode":"OEV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":211.76,"country":"CZ\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Chicken\u0000","productCode":"0207 11 30\u0000","productBriefDescription":"Broiler - Poulet. 65%\u0000","sectorCode":"OEV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":288.0,"country":"DE\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Chicken\u0000","productCode":"0207 11 30\u0000","productBriefDescription":"Broiler - Poulet. 65%\u0000","sectorCode":"OEV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":227.11,"country":"DK\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Chicken\u0000","productCode":"0207 11 30\u0000","productBriefDescription":"Broiler - Poulet. 65%\u0000","sectorCode":"OEV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":158.44,"country":"ES\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Chicken\u0000","productCode":"0207 11 30\u0000","productBriefDescription":"Broiler - Poulet. 65%\u0000","sectorCode":"OEV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":300.62,"country":"FI\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Chicken\u0000","productCode":"0207 11 30\u0000","productBriefDescription":"Broiler - Poulet. 65%\u0000","sectorCode":"OEV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":230.0,"country":"FR\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Chicken\u0000","productCode":"0207 11 30\u0000","productBriefDescription":"Broiler - Poulet. 65%\u0000","sectorCode":"OEV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":202.55,"country":"GR\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Chicken\u0000","productCode":"0207 11 30\u0000","productBriefDescription":"Broiler - Poulet. 65%\u0000","sectorCode":"OEV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":189.77,"country":"HR\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Chicken\u0000","productCode":"0207 11 30\u0000","productBriefDescription":"Broiler - Poulet. 65%\u0000","sectorCode":"OEV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":149.98,"country":"HU\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Chicken\u0000","productCode":"0207 11 30\u0000","productBriefDescription":"Broiler - Poulet. 65%\u0000","sectorCode":"OEV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":213.46,"country":"IE\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Chicken\u0000","productCode":"0207 11 30\u0000","productBriefDescription":"Broiler - Poulet.
65%\u0000","sectorCode":"OEV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":216.25,"country":"IT\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Chicken\u0000","productCode":"0207 11 30\u0000","productBriefDescription":"Broiler - Poulet. 65%\u0000","sectorCode":"OEV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":150.53,"country":"LT\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Chicken\u0000","productCode":"0207 11 30\u0000","productBriefDescription":"Broiler - Poulet. 65%\u0000","sectorCode":"OEV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":223.75,"country":"MT\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Chicken\u0000","productCode":"0207 11 30\u0000","productBriefDescription":"Broiler - Poulet. 65%\u0000","sectorCode":"OEV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":174.0,"country":"NL\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Chicken\u0000","productCode":"0207 11 30\u0000","productBriefDescription":"Broiler - Poulet. 65%\u0000","sectorCode":"OEV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":126.83,"country":"PL\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Chicken\u0000","productCode":"0207 11 30\u0000","productBriefDescription":"Broiler - Poulet. 65%\u0000","sectorCode":"OEV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":170.29,"country":"PT\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Chicken\u0000","productCode":"0207 11 30\u0000","productBriefDescription":"Broiler - Poulet. 65%\u0000","sectorCode":"OEV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":153.32,"country":"RO\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Chicken\u0000","productCode":"0207 11 30\u0000","productBriefDescription":"Broiler - Poulet. 65%\u0000","sectorCode":"OEV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":236.84,"country":"SE\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Chicken\u0000","productCode":"0207 11 30\u0000","productBriefDescription":"Broiler - Poulet. 65%\u0000","sectorCode":"OEV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":206.51,"country":"SI\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Chicken\u0000","productCode":"0207 11 30\u0000","productBriefDescription":"Broiler - Poulet. 65%\u0000","sectorCode":"OEV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":186.54,"country":"SK\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Chicken\u0000","productCode":"0207 11 30\u0000","productBriefDescription":"Broiler - Poulet. 65%\u0000","sectorCode":"OEV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":161.87,"country":"UK\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Chicken\u0000","productCode":"0207 11 30\u0000","productBriefDescription":"Broiler - Poulet. 65%\u0000","sectorCode":"OEV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":190.09,"country":"AT\u0000","unit":"€/100 kg shell\u0000","period":201908.0,"productDesc":"Eggs\u0000","productCode":"0407 00 5LM\u0000","productBriefDescription":"Eggs - Œufs . Class A. categories L&M\u0000","sectorCode":"OEV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":108.62,"country":"BE\u0000","unit":"€/100 kg shell\u0000","period":201908.0,"productDesc":"Eggs\u0000","productCode":"0407 00 5LM\u0000","productBriefDescription":"Eggs - Œufs . Class A. categories L&M\u0000","sectorCode":"OEV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":112.54,"country":"BG\u0000","unit":"€/100 kg shell\u0000","period":201908.0,"productDesc":"Eggs\u0000","productCode":"0407 00 5LM\u0000","productBriefDescription":"Eggs - Œufs . Class A.
categories L&M\u0000","sectorCode":"OEV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":160.18,"country":"CY\u0000","unit":"€/100 kg shell\u0000","period":201908.0,"productDesc":"Eggs\u0000","productCode":"0407 00 5LM\u0000","productBriefDescription":"Eggs - Œufs . Class A. categories L&M\u0000","sectorCode":"OEV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":94.07,"country":"CZ\u0000","unit":"€/100 kg shell\u0000","period":201908.0,"productDesc":"Eggs\u0000","productCode":"0407 00 5LM\u0000","productBriefDescription":"Eggs - Œufs . Class A. categories L&M\u0000","sectorCode":"OEV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":110.38,"country":"DE\u0000","unit":"€/100 kg shell\u0000","period":201908.0,"productDesc":"Eggs\u0000","productCode":"0407 00 5LM\u0000","productBriefDescription":"Eggs - Œufs . Class A. categories L&M\u0000","sectorCode":"OEV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":171.57,"country":"DK\u0000","unit":"€/100 kg shell\u0000","period":201908.0,"productDesc":"Eggs\u0000","productCode":"0407 00 5LM\u0000","productBriefDescription":"Eggs - Œufs . Class A. categories L&M\u0000","sectorCode":"OEV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":142.55,"country":"EE\u0000","unit":"€/100 kg shell\u0000","period":201908.0,"productDesc":"Eggs\u0000","productCode":"0407 00 5LM\u0000","productBriefDescription":"Eggs - Œufs . Class A. categories L&M\u0000","sectorCode":"OEV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":85.32,"country":"ES\u0000","unit":"€/100 kg shell\u0000","period":201908.0,"productDesc":"Eggs\u0000","productCode":"0407 00 5LM\u0000","productBriefDescription":"Eggs - Œufs . Class A. categories L&M\u0000","sectorCode":"OEV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":156.42,"country":"FI\u0000","unit":"€/100 kg shell\u0000","period":201908.0,"productDesc":"Eggs\u0000","productCode":"0407 00 5LM\u0000","productBriefDescription":"Eggs - Œufs . Class A. categories L&M\u0000","sectorCode":"OEV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":116.4,"country":"FR\u0000","unit":"€/100 kg shell\u0000","period":201908.0,"productDesc":"Eggs\u0000","productCode":"0407 00 5LM\u0000","productBriefDescription":"Eggs - Œufs . Class A. categories L&M\u0000","sectorCode":"OEV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":142.75,"country":"GR\u0000","unit":"€/100 kg shell\u0000","period":201908.0,"productDesc":"Eggs\u0000","productCode":"0407 00 5LM\u0000","productBriefDescription":"Eggs - Œufs . Class A. categories L&M\u0000","sectorCode":"OEV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":138.37,"country":"HR\u0000","unit":"€/100 kg shell\u0000","period":201908.0,"productDesc":"Eggs\u0000","productCode":"0407 00 5LM\u0000","productBriefDescription":"Eggs - Œufs . Class A. categories L&M\u0000","sectorCode":"OEV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":116.22,"country":"HU\u0000","unit":"€/100 kg shell\u0000","period":201908.0,"productDesc":"Eggs\u0000","productCode":"0407 00 5LM\u0000","productBriefDescription":"Eggs - Œufs . Class A. categories L&M\u0000","sectorCode":"OEV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":139.55,"country":"IE\u0000","unit":"€/100 kg shell\u0000","period":201908.0,"productDesc":"Eggs\u0000","productCode":"0407 00 5LM\u0000","productBriefDescription":"Eggs - Œufs . Class A. categories L&M\u0000","sectorCode":"OEV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":164.28,"country":"IT\u0000","unit":"€/100 kg shell\u0000","period":201908.0,"productDesc":"Eggs\u0000","productCode":"0407 00 5LM\u0000","productBriefDescription":"Eggs - Œufs . Class A. categories L&M\u0000","sectorCode":"OEV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":106.3,"country":"LT\u0000","unit":"€/100 kg shell\u0000","period":201908.0,"productDesc":"Eggs\u0000","productCode":"0407 00 5LM\u0000","productBriefDescription":"Eggs - Œufs . Class A.
categories L&M\u0000","sectorCode":"OEV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":109.38,"country":"LV\u0000","unit":"€/100 kg shell\u0000","period":201908.0,"productDesc":"Eggs\u0000","productCode":"0407 00 5LM\u0000","productBriefDescription":"Eggs - Œufs . Class A. categories L&M\u0000","sectorCode":"OEV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":164.69,"country":"MT\u0000","unit":"€/100 kg shell\u0000","period":201908.0,"productDesc":"Eggs\u0000","productCode":"0407 00 5LM\u0000","productBriefDescription":"Eggs - Œufs . Class A. categories L&M\u0000","sectorCode":"OEV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":123.32,"country":"NL\u0000","unit":"€/100 kg shell\u0000","period":201908.0,"productDesc":"Eggs\u0000","productCode":"0407 00 5LM\u0000","productBriefDescription":"Eggs - Œufs . Class A. categories L&M\u0000","sectorCode":"OEV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":135.93,"country":"PL\u0000","unit":"€/100 kg shell\u0000","period":201908.0,"productDesc":"Eggs\u0000","productCode":"0407 00 5LM\u0000","productBriefDescription":"Eggs - Œufs . Class A. categories L&M\u0000","sectorCode":"OEV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":116.91,"country":"PT\u0000","unit":"€/100 kg shell\u0000","period":201908.0,"productDesc":"Eggs\u0000","productCode":"0407 00 5LM\u0000","productBriefDescription":"Eggs - Œufs . Class A. categories L&M\u0000","sectorCode":"OEV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":101.27,"country":"RO\u0000","unit":"€/100 kg shell\u0000","period":201908.0,"productDesc":"Eggs\u0000","productCode":"0407 00 5LM\u0000","productBriefDescription":"Eggs - Œufs . Class A. categories L&M\u0000","sectorCode":"OEV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":174.96,"country":"SE\u0000","unit":"€/100 kg shell\u0000","period":201908.0,"productDesc":"Eggs\u0000","productCode":"0407 00 5LM\u0000","productBriefDescription":"Eggs - Œufs . Class A. categories L&M\u0000","sectorCode":"OEV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":135.45,"country":"SI\u0000","unit":"€/100 kg shell\u0000","period":201908.0,"productDesc":"Eggs\u0000","productCode":"0407 00 5LM\u0000","productBriefDescription":"Eggs - Œufs . Class A. categories L&M\u0000","sectorCode":"OEV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":95.42,"country":"SK\u0000","unit":"€/100 kg shell\u0000","period":201908.0,"productDesc":"Eggs\u0000","productCode":"0407 00 5LM\u0000","productBriefDescription":"Eggs - Œufs . Class A. categories L&M\u0000","sectorCode":"OEV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":97.99,"country":"UK\u0000","unit":"€/100 kg shell\u0000","period":201908.0,"productDesc":"Eggs\u0000","productCode":"0407 00 5LM\u0000","productBriefDescription":"Eggs - Œufs . Class A. categories L&M\u0000","sectorCode":"OEV\u0000","category":"Animal products\u0000"},{"mpmarketPrice":557.48,"country":"AT\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Lamb Heavy\u0000","productCode":"EU_H\u0000","productBriefDescription":"Lamb - Agneau. class H\u0000","sectorCode":"OVI\u0000","category":"Animal products\u0000"},{"mpmarketPrice":488.16,"country":"BE\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Lamb Heavy\u0000","productCode":"EU_H\u0000","productBriefDescription":"Lamb - Agneau. class H\u0000","sectorCode":"OVI\u0000","category":"Animal products\u0000"},{"mpmarketPrice":494.16,"country":"CY\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Lamb Heavy\u0000","productCode":"EU_H\u0000","productBriefDescription":"Lamb - Agneau. class H\u0000","sectorCode":"OVI\u0000","category":"Animal products\u0000"},{"mpmarketPrice":501.27,"country":"DE\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Lamb Heavy\u0000","productCode":"EU_H\u0000","productBriefDescription":"Lamb - Agneau.
class H\u0000","sectorCode":"OVI\u0000","category":"Animal products\u0000"},{"mpmarketPrice":513.05,"country":"DK\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Lamb Heavy\u0000","productCode":"EU_H\u0000","productBriefDescription":"Lamb - Agneau. class H\u0000","sectorCode":"OVI\u0000","category":"Animal products\u0000"},{"mpmarketPrice":492.1,"country":"ES\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Lamb Heavy\u0000","productCode":"EU_H\u0000","productBriefDescription":"Lamb - Agneau. class H\u0000","sectorCode":"OVI\u0000","category":"Animal products\u0000"},{"mpmarketPrice":399.03,"country":"FI\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Lamb Heavy\u0000","productCode":"EU_H\u0000","productBriefDescription":"Lamb - Agneau. class H\u0000","sectorCode":"OVI\u0000","category":"Animal products\u0000"},{"mpmarketPrice":595.71,"country":"FR\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Lamb Heavy\u0000","productCode":"EU_H\u0000","productBriefDescription":"Lamb - Agneau. class H\u0000","sectorCode":"OVI\u0000","category":"Animal products\u0000"},{"mpmarketPrice":422.15,"country":"IE\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Lamb Heavy\u0000","productCode":"EU_H\u0000","productBriefDescription":"Lamb - Agneau. class H\u0000","sectorCode":"OVI\u0000","category":"Animal products\u0000"},{"mpmarketPrice":550.19,"country":"IT\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Lamb Heavy\u0000","productCode":"EU_H\u0000","productBriefDescription":"Lamb - Agneau. class H\u0000","sectorCode":"OVI\u0000","category":"Animal products\u0000"},{"mpmarketPrice":406.76,"country":"LV\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Lamb Heavy\u0000","productCode":"EU_H\u0000","productBriefDescription":"Lamb - Agneau. class H\u0000","sectorCode":"OVI\u0000","category":"Animal products\u0000"},{"mpmarketPrice":526.28,"country":"NL\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Lamb Heavy\u0000","productCode":"EU_H\u0000","productBriefDescription":"Lamb - Agneau. class H\u0000","sectorCode":"OVI\u0000","category":"Animal products\u0000"},{"mpmarketPrice":433.84,"country":"PL\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Lamb Heavy\u0000","productCode":"EU_H\u0000","productBriefDescription":"Lamb - Agneau. class H\u0000","sectorCode":"OVI\u0000","category":"Animal products\u0000"},{"mpmarketPrice":204.04,"country":"RO\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Lamb Heavy\u0000","productCode":"EU_H\u0000","productBriefDescription":"Lamb - Agneau. class H\u0000","sectorCode":"OVI\u0000","category":"Animal products\u0000"},{"mpmarketPrice":452.3,"country":"SE\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Lamb Heavy\u0000","productCode":"EU_H\u0000","productBriefDescription":"Lamb - Agneau. class H\u0000","sectorCode":"OVI\u0000","category":"Animal products\u0000"},{"mpmarketPrice":427.1,"country":"UK\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Lamb Heavy\u0000","productCode":"EU_H\u0000","productBriefDescription":"Lamb - Agneau. class H\u0000","sectorCode":"OVI\u0000","category":"Animal products\u0000"},{"mpmarketPrice":618.07,"country":"BG\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Lamb Light\u0000","productCode":"EU_L\u0000","productBriefDescription":"Lamb - Agneau. class L\u0000","sectorCode":"OVI\u0000","category":"Animal products\u0000"},{"mpmarketPrice":527.19,"country":"ES\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Lamb Light\u0000","productCode":"EU_L\u0000","productBriefDescription":"Lamb - Agneau.
class L\u0000","sectorCode":"OVI\u0000","category":"Animal products\u0000"},{"mpmarketPrice":469.9,"country":"GR\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Lamb Light\u0000","productCode":"EU_L\u0000","productBriefDescription":"Lamb - Agneau. class L\u0000","sectorCode":"OVI\u0000","category":"Animal products\u0000"},{"mpmarketPrice":616.58,"country":"HR\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Lamb Light\u0000","productCode":"EU_L\u0000","productBriefDescription":"Lamb - Agneau. class L\u0000","sectorCode":"OVI\u0000","category":"Animal products\u0000"},{"mpmarketPrice":588.32,"country":"HU\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Lamb Light\u0000","productCode":"EU_L\u0000","productBriefDescription":"Lamb - Agneau. class L\u0000","sectorCode":"OVI\u0000","category":"Animal products\u0000"},{"mpmarketPrice":563.58,"country":"IT\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Lamb Light\u0000","productCode":"EU_L\u0000","productBriefDescription":"Lamb - Agneau. class L\u0000","sectorCode":"OVI\u0000","category":"Animal products\u0000"},{"mpmarketPrice":403.93,"country":"LV\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Lamb Light\u0000","productCode":"EU_L\u0000","productBriefDescription":"Lamb - Agneau. class L\u0000","sectorCode":"OVI\u0000","category":"Animal products\u0000"},{"mpmarketPrice":682.43,"country":"PT\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Lamb Light\u0000","productCode":"EU_L\u0000","productBriefDescription":"Lamb - Agneau. class L\u0000","sectorCode":"OVI\u0000","category":"Animal products\u0000"},{"mpmarketPrice":502.33,"country":"RO\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Lamb Light\u0000","productCode":"EU_L\u0000","productBriefDescription":"Lamb - Agneau. class L\u0000","sectorCode":"OVI\u0000","category":"Animal products\u0000"},{"mpmarketPrice":539.62,"country":"SI\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Lamb Light\u0000","productCode":"EU_L\u0000","productBriefDescription":"Lamb - Agneau. class L\u0000","sectorCode":"OVI\u0000","category":"Animal products\u0000"},{"mpmarketPrice":427.1,"country":"UK\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Lamb Light\u0000","productCode":"EU_L\u0000","productBriefDescription":"Lamb - Agneau. class L\u0000","sectorCode":"OVI\u0000","category":"Animal products\u0000"},{"mpmarketPrice":48.25,"country":"BE\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Piglets\u0000","productCode":"0103 91\u0000","productBriefDescription":"Piglets - Porcelets. 20 kg\u0000","sectorCode":"POR\u0000","category":"Animal products\u0000"},{"mpmarketPrice":61.52,"country":"DE\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Piglets\u0000","productCode":"0103 91\u0000","productBriefDescription":"Piglets - Porcelets. 20 kg\u0000","sectorCode":"POR\u0000","category":"Animal products\u0000"},{"mpmarketPrice":59.35,"country":"DK\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Piglets\u0000","productCode":"0103 91\u0000","productBriefDescription":"Piglets - Porcelets. 20 kg\u0000","sectorCode":"POR\u0000","category":"Animal products\u0000"},{"mpmarketPrice":42.22,"country":"ES\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Piglets\u0000","productCode":"0103 91\u0000","productBriefDescription":"Piglets - Porcelets. 20 kg\u0000","sectorCode":"POR\u0000","category":"Animal products\u0000"},{"mpmarketPrice":50.56,"country":"EU\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Piglets\u0000","productCode":"0103 91\u0000","productBriefDescription":"Piglets - Porcelets.
20 kg\u0000","sectorCode":"POR\u0000","category":"Animal products\u0000"},{"mpmarketPrice":42.36,"country":"FI\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Piglets\u0000","productCode":"0103 91\u0000","productBriefDescription":"Piglets - Porcelets. 20 kg\u0000","sectorCode":"POR\u0000","category":"Animal products\u0000"},{"mpmarketPrice":35.1,"country":"FR\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Piglets\u0000","productCode":"0103 91\u0000","productBriefDescription":"Piglets - Porcelets. 20 kg\u0000","sectorCode":"POR\u0000","category":"Animal products\u0000"},{"mpmarketPrice":41.2,"country":"HR\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Piglets\u0000","productCode":"0103 91\u0000","productBriefDescription":"Piglets - Porcelets. 20 kg\u0000","sectorCode":"POR\u0000","category":"Animal products\u0000"},{"mpmarketPrice":39.17,"country":"HU\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Piglets\u0000","productCode":"0103 91\u0000","productBriefDescription":"Piglets - Porcelets. 20 kg\u0000","sectorCode":"POR\u0000","category":"Animal products\u0000"},{"mpmarketPrice":70.87,"country":"IT\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Piglets\u0000","productCode":"0103 91\u0000","productBriefDescription":"Piglets - Porcelets. 20 kg\u0000","sectorCode":"POR\u0000","category":"Animal products\u0000"},{"mpmarketPrice":91.36,"country":"MT\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Piglets\u0000","productCode":"0103 91\u0000","productBriefDescription":"Piglets - Porcelets. 20 kg\u0000","sectorCode":"POR\u0000","category":"Animal products\u0000"},{"mpmarketPrice":38.77,"country":"NL\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Piglets\u0000","productCode":"0103 91\u0000","productBriefDescription":"Piglets - Porcelets. 20 kg\u0000","sectorCode":"POR\u0000","category":"Animal products\u0000"},{"mpmarketPrice":51.32,"country":"PL\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Piglets\u0000","productCode":"0103 91\u0000","productBriefDescription":"Piglets - Porcelets. 20 kg\u0000","sectorCode":"POR\u0000","category":"Animal products\u0000"},{"mpmarketPrice":45.68,"country":"PT\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Piglets\u0000","productCode":"0103 91\u0000","productBriefDescription":"Piglets - Porcelets. 20 kg\u0000","sectorCode":"POR\u0000","category":"Animal products\u0000"},{"mpmarketPrice":64.28,"country":"SE\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Piglets\u0000","productCode":"0103 91\u0000","productBriefDescription":"Piglets - Porcelets. 20 kg\u0000","sectorCode":"POR\u0000","category":"Animal products\u0000"},{"mpmarketPrice":59.15,"country":"UK\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Piglets\u0000","productCode":"0103 91\u0000","productBriefDescription":"Piglets - Porcelets. 20 kg\u0000","sectorCode":"POR\u0000","category":"Animal products\u0000"},{"mpmarketPrice":188.05,"country":"AT\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Pigmeat Excellent\u0000","productCode":"E\u0000","productBriefDescription":"Pig carcase - Carcasse de porc. Class E\u0000","sectorCode":"POR\u0000","category":"Animal products\u0000"},{"mpmarketPrice":154.82,"country":"BE\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Pigmeat Excellent\u0000","productCode":"E\u0000","productBriefDescription":"Pig carcase - Carcasse de porc. Class E\u0000","sectorCode":"POR\u0000","category":"Animal products\u0000"},{"mpmarketPrice":196.55,"country":"BG\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Pigmeat Excellent\u0000","productCode":"E\u0000","productBriefDescription":"Pig carcase - Carcasse de porc.
Class E\u0000","sectorCode":"POR\u0000","category":"Animal products\u0000"},{"mpmarketPrice":202.7,"country":"CY\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Pigmeat Excellent\u0000","productCode":"E\u0000","productBriefDescription":"Pig carcase - Carcasse de porc. Class E\u0000","sectorCode":"POR\u0000","category":"Animal products\u0000"},{"mpmarketPrice":177.34,"country":"CZ\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Pigmeat Excellent\u0000","productCode":"E\u0000","productBriefDescription":"Pig carcase - Carcasse de porc. Class E\u0000","sectorCode":"POR\u0000","category":"Animal products\u0000"},{"mpmarketPrice":188.79,"country":"DE\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Pigmeat Excellent\u0000","productCode":"E\u0000","productBriefDescription":"Pig carcase - Carcasse de porc. Class E\u0000","sectorCode":"POR\u0000","category":"Animal products\u0000"},{"mpmarketPrice":170.38,"country":"DK\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Pigmeat Excellent\u0000","productCode":"E\u0000","productBriefDescription":"Pig carcase - Carcasse de porc. Class E\u0000","sectorCode":"POR\u0000","category":"Animal products\u0000"},{"mpmarketPrice":167.48,"country":"EE\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Pigmeat Excellent\u0000","productCode":"E\u0000","productBriefDescription":"Pig carcase - Carcasse de porc. Class E\u0000","sectorCode":"POR\u0000","category":"Animal products\u0000"},{"mpmarketPrice":180.87,"country":"ES\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Pigmeat Excellent\u0000","productCode":"E\u0000","productBriefDescription":"Pig carcase - Carcasse de porc. Class E\u0000","sectorCode":"POR\u0000","category":"Animal products\u0000"},{"mpmarketPrice":178.82,"country":"EU\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Pigmeat Excellent\u0000","productCode":"E\u0000","productBriefDescription":"Pig carcase - Carcasse de porc. Class E\u0000","sectorCode":"POR\u0000","category":"Animal products\u0000"},{"mpmarketPrice":165.33,"country":"FI\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Pigmeat Excellent\u0000","productCode":"E\u0000","productBriefDescription":"Pig carcase - Carcasse de porc. Class E\u0000","sectorCode":"POR\u0000","category":"Animal products\u0000"},{"mpmarketPrice":168.65,"country":"FR\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Pigmeat Excellent\u0000","productCode":"E\u0000","productBriefDescription":"Pig carcase - Carcasse de porc. Class E\u0000","sectorCode":"POR\u0000","category":"Animal products\u0000"},{"mpmarketPrice":204.31,"country":"GR\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Pigmeat Excellent\u0000","productCode":"E\u0000","productBriefDescription":"Pig carcase - Carcasse de porc. Class E\u0000","sectorCode":"POR\u0000","category":"Animal products\u0000"},{"mpmarketPrice":178.24,"country":"HR\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Pigmeat Excellent\u0000","productCode":"E\u0000","productBriefDescription":"Pig carcase - Carcasse de porc. Class E\u0000","sectorCode":"POR\u0000","category":"Animal products\u0000"},{"mpmarketPrice":188.09,"country":"HU\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Pigmeat Excellent\u0000","productCode":"E\u0000","productBriefDescription":"Pig carcase - Carcasse de porc. Class E\u0000","sectorCode":"POR\u0000","category":"Animal products\u0000"},{"mpmarketPrice":171.43,"country":"IE\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Pigmeat Excellent\u0000","productCode":"E\u0000","productBriefDescription":"Pig carcase - Carcasse de porc.
Class E\u0000","sectorCode":"POR\u0000","category":"Animal products\u0000"},{"mpmarketPrice":170.49,"country":"LT\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Pigmeat Excellent\u0000","productCode":"E\u0000","productBriefDescription":"Pig carcase - Carcasse de porc. Class E\u0000","sectorCode":"POR\u0000","category":"Animal products\u0000"},{"mpmarketPrice":177.01,"country":"LV\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Pigmeat Excellent\u0000","productCode":"E\u0000","productBriefDescription":"Pig carcase - Carcasse de porc. Class E\u0000","sectorCode":"POR\u0000","category":"Animal products\u0000"},{"mpmarketPrice":214.0,"country":"MT\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Pigmeat Excellent\u0000","productCode":"E\u0000","productBriefDescription":"Pig carcase - Carcasse de porc. Class E\u0000","sectorCode":"POR\u0000","category":"Animal products\u0000"},{"mpmarketPrice":168.16,"country":"NL\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Pigmeat Excellent\u0000","productCode":"E\u0000","productBriefDescription":"Pig carcase - Carcasse de porc. Class E\u0000","sectorCode":"POR\u0000","category":"Animal products\u0000"},{"mpmarketPrice":175.33,"country":"PL\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Pigmeat Excellent\u0000","productCode":"E\u0000","productBriefDescription":"Pig carcase - Carcasse de porc. Class E\u0000","sectorCode":"POR\u0000","category":"Animal products\u0000"},{"mpmarketPrice":194.42,"country":"PT\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Pigmeat Excellent\u0000","productCode":"E\u0000","productBriefDescription":"Pig carcase - Carcasse de porc. Class E\u0000","sectorCode":"POR\u0000","category":"Animal products\u0000"},{"mpmarketPrice":183.64,"country":"RO\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Pigmeat Excellent\u0000","productCode":"E\u0000","productBriefDescription":"Pig carcase - Carcasse de porc. Class E\u0000","sectorCode":"POR\u0000","category":"Animal products\u0000"},{"mpmarketPrice":165.66,"country":"SE\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Pigmeat Excellent\u0000","productCode":"E\u0000","productBriefDescription":"Pig carcase - Carcasse de porc. Class E\u0000","sectorCode":"POR\u0000","category":"Animal products\u0000"},{"mpmarketPrice":195.22,"country":"SI\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Pigmeat Excellent\u0000","productCode":"E\u0000","productBriefDescription":"Pig carcase - Carcasse de porc. Class E\u0000","sectorCode":"POR\u0000","category":"Animal products\u0000"},{"mpmarketPrice":186.32,"country":"SK\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Pigmeat Excellent\u0000","productCode":"E\u0000","productBriefDescription":"Pig carcase - Carcasse de porc. Class E\u0000","sectorCode":"POR\u0000","category":"Animal products\u0000"},{"mpmarketPrice":167.56,"country":"UK\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Pigmeat Excellent\u0000","productCode":"E\u0000","productBriefDescription":"Pig carcase - Carcasse de porc. Class E\u0000","sectorCode":"POR\u0000","category":"Animal products\u0000"},{"mpmarketPrice":157.27,"country":"DE\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Pigmeat Good\u0000","productCode":"R\u0000","productBriefDescription":"Pig carcase - Carcasse de porc. Class R\u0000","sectorCode":"POR\u0000","category":"Animal products\u0000"},{"mpmarketPrice":168.84,"country":"ES\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Pigmeat Good\u0000","productCode":"R\u0000","productBriefDescription":"Pig carcase - Carcasse de porc.
Class R\u0000","sectorCode":"POR\u0000","category":"Animal products\u0000"},{"mpmarketPrice":186.44,"country":"EU\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Pigmeat Good\u0000","productCode":"R\u0000","productBriefDescription":"Pig carcase - Carcasse de porc. Class R\u0000","sectorCode":"POR\u0000","category":"Animal products\u0000"},{"mpmarketPrice":135.75,"country":"FI\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Pigmeat Good\u0000","productCode":"R\u0000","productBriefDescription":"Pig carcase - Carcasse de porc. Class R\u0000","sectorCode":"POR\u0000","category":"Animal products\u0000"},{"mpmarketPrice":189.22,"country":"IT\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Pigmeat Good\u0000","productCode":"R\u0000","productBriefDescription":"Pig carcase - Carcasse de porc. Class R\u0000","sectorCode":"POR\u0000","category":"Animal products\u0000"},{"mpmarketPrice":154.29,"country":"LT\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Pigmeat Good\u0000","productCode":"R\u0000","productBriefDescription":"Pig carcase - Carcasse de porc. Class R\u0000","sectorCode":"POR\u0000","category":"Animal products\u0000"},{"mpmarketPrice":153.95,"country":"LV\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Pigmeat Good\u0000","productCode":"R\u0000","productBriefDescription":"Pig carcase - Carcasse de porc. Class R\u0000","sectorCode":"POR\u0000","category":"Animal products\u0000"},{"mpmarketPrice":170.44,"country":"SK\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Pigmeat Good\u0000","productCode":"R\u0000","productBriefDescription":"Pig carcase - Carcasse de porc. Class R\u0000","sectorCode":"POR\u0000","category":"Animal products\u0000"},{"mpmarketPrice":122.0,"country":"UK\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Pigmeat Good\u0000","productCode":"R\u0000","productBriefDescription":"Pig carcase - Carcasse de porc. Class R\u0000","sectorCode":"POR\u0000","category":"Animal products\u0000"},{"mpmarketPrice":199.96,"country":"AT\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Pigmeat Superior\u0000","productCode":"S\u0000","productBriefDescription":"Pig carcase - Carcasse de porc. Class S\u0000","sectorCode":"POR\u0000","category":"Animal products\u0000"},{"mpmarketPrice":162.13,"country":"BE\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Pigmeat Superior\u0000","productCode":"S\u0000","productBriefDescription":"Pig carcase - Carcasse de porc. Class S\u0000","sectorCode":"POR\u0000","category":"Animal products\u0000"},{"mpmarketPrice":182.39,"country":"CZ\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Pigmeat Superior\u0000","productCode":"S\u0000","productBriefDescription":"Pig carcase - Carcasse de porc. Class S\u0000","sectorCode":"POR\u0000","category":"Animal products\u0000"},{"mpmarketPrice":192.38,"country":"DE\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Pigmeat Superior\u0000","productCode":"S\u0000","productBriefDescription":"Pig carcase - Carcasse de porc. Class S\u0000","sectorCode":"POR\u0000","category":"Animal products\u0000"},{"mpmarketPrice":176.65,"country":"DK\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Pigmeat Superior\u0000","productCode":"S\u0000","productBriefDescription":"Pig carcase - Carcasse de porc. Class S\u0000","sectorCode":"POR\u0000","category":"Animal products\u0000"},{"mpmarketPrice":173.64,"country":"EE\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Pigmeat Superior\u0000","productCode":"S\u0000","productBriefDescription":"Pig carcase - Carcasse de porc.
Class S\u0000","sectorCode":"POR\u0000","category":"Animal products\u0000"},{"mpmarketPrice":182.98,"country":"ES\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Pigmeat Superior\u0000","productCode":"S\u0000","productBriefDescription":"Pig carcase - Carcasse de porc. Class S\u0000","sectorCode":"POR\u0000","category":"Animal products\u0000"},{"mpmarketPrice":181.44,"country":"EU\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Pigmeat Superior\u0000","productCode":"S\u0000","productBriefDescription":"Pig carcase - Carcasse de porc. Class S\u0000","sectorCode":"POR\u0000","category":"Animal products\u0000"},{"mpmarketPrice":170.53,"country":"FI\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Pigmeat Superior\u0000","productCode":"S\u0000","productBriefDescription":"Pig carcase - Carcasse de porc. Class S\u0000","sectorCode":"POR\u0000","category":"Animal products\u0000"},{"mpmarketPrice":176.87,"country":"FR\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Pigmeat Superior\u0000","productCode":"S\u0000","productBriefDescription":"Pig carcase - Carcasse de porc. Class S\u0000","sectorCode":"POR\u0000","category":"Animal products\u0000"},{"mpmarketPrice":204.31,"country":"GR\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Pigmeat Superior\u0000","productCode":"S\u0000","productBriefDescription":"Pig carcase - Carcasse de porc. Class S\u0000","sectorCode":"POR\u0000","category":"Animal products\u0000"},{"mpmarketPrice":173.07,"country":"HR\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Pigmeat Superior\u0000","productCode":"S\u0000","productBriefDescription":"Pig carcase - Carcasse de porc. Class S\u0000","sectorCode":"POR\u0000","category":"Animal products\u0000"},{"mpmarketPrice":190.95,"country":"HU\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Pigmeat Superior\u0000","productCode":"S\u0000","productBriefDescription":"Pig carcase - Carcasse de porc. Class S\u0000","sectorCode":"POR\u0000","category":"Animal products\u0000"},{"mpmarketPrice":171.66,"country":"IE\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Pigmeat Superior\u0000","productCode":"S\u0000","productBriefDescription":"Pig carcase - Carcasse de porc. Class S\u0000","sectorCode":"POR\u0000","category":"Animal products\u0000"},{"mpmarketPrice":173.6,"country":"LT\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Pigmeat Superior\u0000","productCode":"S\u0000","productBriefDescription":"Pig carcase - Carcasse de porc. Class S\u0000","sectorCode":"POR\u0000","category":"Animal products\u0000"},{"mpmarketPrice":174.77,"country":"LV\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Pigmeat Superior\u0000","productCode":"S\u0000","productBriefDescription":"Pig carcase - Carcasse de porc. Class S\u0000","sectorCode":"POR\u0000","category":"Animal products\u0000"},{"mpmarketPrice":169.3,"country":"NL\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Pigmeat Superior\u0000","productCode":"S\u0000","productBriefDescription":"Pig carcase - Carcasse de porc. Class S\u0000","sectorCode":"POR\u0000","category":"Animal products\u0000"},{"mpmarketPrice":178.07,"country":"PL\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Pigmeat Superior\u0000","productCode":"S\u0000","productBriefDescription":"Pig carcase - Carcasse de porc. Class S\u0000","sectorCode":"POR\u0000","category":"Animal products\u0000"},{"mpmarketPrice":194.42,"country":"PT\u0000","unit":"€/100 kg carcass weight\u0000","period":201908.0,"productDesc":"Pigmeat Superior\u0000","productCode":"S\u0000","productBriefDescription":"Pig carcase - Carcasse de porc.

```



### UML

1. [UML Diagramma dei casi d'usi] ( [https://github.com/juancqa9/Animale/blob/master/diagrammi/casi%20d'uso.PNG](https://github.com/juancqa9/Animale/blob/master/diagrammi/casi d'uso.PNG) )
2. [UML Diagramma delle classi] ( [https://github.com/juancqa9/Animale/blob/master/diagrammi/diagramma%20classi.png](https://github.com/juancqa9/Animale/blob/master/diagrammi/diagramma classi.png) )
3. [UML Diagramma di sequenze]
   - DataController: ( https://github.com/juancqa9/Animale/blob/master/diagrammi/SequenzaDataController.PNG )
   - MetaDataController:  ( [https://github.com/juancqa9/Animale/blob/master/diagrammi/Sequenza%20MetaDataController.PNG](https://github.com/juancqa9/Animale/blob/master/diagrammi/Sequenza MetaDataController.PNG) )
   - FilterController: ( [https://github.com/juancqa9/Animale/blob/master/diagrammi/Sequenza%20FilterController.PNG](https://github.com/juancqa9/Animale/blob/master/diagrammi/Sequenza FilterController.PNG) )
   - StatisticaController: ( [https://github.com/juancqa9/Animale/blob/master/diagrammi/Sequenza%20StatisticaController.PNG](https://github.com/juancqa9/Animale/blob/master/diagrammi/Sequenza StatisticaController.PNG) )



### L'intero lavoro è stato svolto da:

- Prendi Denis.
- Juan Carlos Guzman Morales.























































































































































