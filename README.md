# Regression Tree Learner
Progetto di Metodi Avanzati di Programmazione (2020)
  
### Prerequisiti
Per il corretto funzionamento è necessario avere un computer con Windows e i due applicativi (client e server). 
Il client è anche dotato di un’interfaccia grafica per l’utente.  
Al fine di eseguire l’applicazione, si rende necessaria l’installazione (se non sono già presenti nel sistema) di MySQL e la JRE 8.  
  
### Server
Prima dell’esecuzione del server è necessario creare database, utente e popolare e tabelle del db attraverso uno script SQL chiamato “MAPDB.SQL” contenuto nel
percorso \Regression Tree Learner\Distribution\server  
  
### Client
Il client necessita della JRE 8 installata sulla macchina e del Server già avviato e in attesa di una connessione.  

### GUI-Client  
E possibile usare il client anche tramite GUI; sarà sempre necessaria la JRE 8.  
  
N.B. Per il corretto funzionamento dell'applicazione server e client devono essere
collegati sotto la stessa rete ed il server deve essere avviato per primo.
  
### Funzionamento Server
Il server viene avviato usando il file batch server.bat, situato in \Regression Tree Learner\Distribution\server.
Una volta avviato il server rimarrà in attesa di connessione da parte di un client. 
Il server mostrerà a video un messaggio “Server started” e una volta connesso, mostrerà informazioni sulla connessione avvenuta.  
  
### Funzionamento Client
Il client viene avviato da un file batch denominato “client.bat” che si trova in \Regression Tree Learner\Distribution\client.  
Di default il client viene avviato con determinati parametri specificati nel file batch.
