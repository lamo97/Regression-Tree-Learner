package application;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.rmi.ServerError;

import static application.Controller.ip;
import static com.sun.javafx.application.PlatformImpl.exit;

/**
 * Client che comunica col server
 */
public class RtClient {

    /**
     * Output stream che invia informazioni al server
     */
    private ObjectOutputStream out;
    /**
     * Input stream che riceve informazioni dal server
     */
    private ObjectInputStream in;
    /**
     * Numero di porta su cui avviene la comunicazione
     */
    private final int port = 8080;

    /**
     * Costruttore di classe che inizializza la connessione col server, l'input e l'output stream
     *
     * @param ip valore dell'IP
     * @throws IOException eccezioni prodotte da errori di I/O
     */

    public RtClient(String ip) throws IOException {
        InetAddress addr = InetAddress.getByName(ip); // ip
        Socket socket = new Socket(addr, port); // Port

        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());

    }

    /**
     * Questo metodo permette di caricare l'albero da file
     *
     * @param branch intero che indica il branch con cui attraversare l'albero
     * @return String contenente il nodo dell'albero
     * @throws IOException            eccezioni prodotte da errori di I/O
     * @throws ClassNotFoundException eccezioni prodotte da Classe non trovata o errori di cast
     */
    public String learningFromFile(int branch) throws IOException, ClassNotFoundException {


        out.writeObject(branch);
        String result = in.readObject().toString();

        do {


            result = in.readObject().toString();


            while (result.equals("QUERY")) {

                result = in.readObject().toString();
                System.out.println(result);
                if (result.equals("OK")) {
                    break;
                }


                out.writeObject(1);
                result = in.readObject().toString();


            }

            if (result.equals("OK")) {
                result = in.readObject().toString();

            }
        } while (result.equals("OK"));

        return result;

    }

    /**
     * Metodo che permette di inviare al server il nome della tabella presa da file e l'indicazione
     * sulle operazioni da effettuare su di essa
     *
     * @param tableName nome della tabella
     * @return Stringa contente i nodi dell'albero
     * @throws IOException            eccezioni prodotte da errori di I/O
     * @throws ClassNotFoundException eccezioni prodotte da Classe non trovata o errori di cast
     */
    public String learningFromFile(String tableName) throws IOException, ClassNotFoundException {
        out.writeObject(2);
        out.writeObject(tableName);
        String result = in.readObject().toString();


        if (!result.equals("Table is valid!")) {
            InetAddress addr = InetAddress.getByName(ip); // ip
            Socket socket = new Socket(addr, port); // Port
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());

            throw new IOException("No such table!");
        }


        out.writeObject(3);


        result = in.readObject().toString();

        result = in.readObject().toString();
        while (result.equals("QUERY")) {


            result = in.readObject().toString();

            if (result.equals("OK")) {
                out.flush();
                out.reset();
                break;

            }
        }
        if (result.equals("OK")) { // Reading prediction
            result = in.readObject().toString();

        }


        return result;
    }


    /**
     * Metodo che permette di inviare al server il nome della tabella acquisita da
     * database e l'indicazione sulle operazioni da effetturare su di essa.
     *
     * @param tableName nome della tabella
     * @throws IOException            eccezioni prodotte da errori di I/O
     * @throws ClassNotFoundException eccezioni prodotte da Classe non trovata o errori di cast
     * @throws ServerException        eccezioni generata dalla comunicazione col server
     */
    public void storeTableFromDb(String tableName) throws IOException, ClassNotFoundException, ServerException {
        Object result = "";

        out.writeObject(0);

        out.writeObject(tableName);
        result = in.readObject();

        if (!result.equals("Table is valid!")) {

            InetAddress addr = InetAddress.getByName(ip); // ip
            Socket socket = new Socket(addr, port); // Port
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            throw new ServerException("No such table!");
        }


    }


    /**
     * Metodo utilizzato per inviare al server indicazioni sulle operazioni da eseguire una volta acquisito
     * il nome della tabella.
     *
     * @return Stringa contenente il primo nodo dell'albero
     * @throws IOException            eccezioni prodotte da errori di I/O
     * @throws ClassNotFoundException eccezioni prodotte da Classe non trovata o errori di cast
     */
    public String learningFromDbTable() throws IOException, ClassNotFoundException {
        String result;

        out.writeObject(1);


        result = in.readObject().toString();


        out.writeObject(3);

        result = in.readObject().toString();
        if (!result.equals("OK")) {
            return result;
        }
        result = in.readObject().toString();

        while (result.equals("QUERY")) {


            result = in.readObject().toString();

            if (result.equals("OK")) {
                out.flush();
                out.reset();
                break;

            }
        }
        if (result.equals("OK")) { // Reading prediction
            result = in.readObject().toString();

        }


        return result;
    }


    /**
     * Metodo per acquisire l'albero da database e discenderlo sulla base del branch value
     *
     * @param branch intero che indica verso che ramo discendere
     * @return Stringa contente il nodo dell'albero
     * @throws IOException            eccezioni prodotte da errori di I/O
     * @throws ClassNotFoundException eccezioni prodotte da Classe non trovata o errori di cast
     */
    public String learningFromDbTable(int branch) throws IOException, ClassNotFoundException {
        out.writeObject(branch);
        String result = in.readObject().toString();

        do {


            result = in.readObject().toString();


            while (result.equals("QUERY")) {
                // Formualting query, reading answer

                result = in.readObject().toString();
                if (result.equals("OK")) {
                    break;
                }


                out.writeObject(1);
                result = in.readObject().toString();


            }

            if (result.equals("OK")) {
                result = in.readObject().toString();


            }
        } while (result.equals("OK"));


        return result;
    }

}
