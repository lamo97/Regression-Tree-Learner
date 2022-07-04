package server;

import data.*;
import tree.*;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.sql.SQLException;

/**
 * Classe che si occupa della comunicazione di un singolo client
 */
public class ServerOneClient extends Thread {

    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    RegressionTree tree = null;
    private double splitVariance;
    private String tableName = null;
    private Data data = null;

    /**
     * Costruttore di classe
     *
     * @param s Socket instaurato tra multiserver e client corrente
     * @throws IOException eccezione generata da errori di I/O
     */

    public ServerOneClient(Socket s) throws IOException {

        this.socket = s;
        // con in riceve le richieste dal client
        in = new ObjectInputStream(socket.getInputStream());
        // passa le richieste al client
        out = new ObjectOutputStream(socket.getOutputStream());

        // avvio del thread
        this.start();
    }

    /**
     * Override del metodo run() per permettere al server di comunicare col  client corrente
     */
    @Override
    public void run() {

        Data trainingSet = null;
        RegressionTree tree = null;

        boolean cycle = true;

        while (cycle) {
            try {
                int command = (int) in.readObject();
                switch (command) {
                    case 0:
                        try {                           
                            while (true) {
                                try {
                                    tableName = (String) in.readObject();
                                    trainingSet = new Data(tableName);
                                    out.writeObject("Table is valid!");
                                    break;
                                } catch (SQLException e) {
                                    out.writeObject("No such table!");
                                }catch(SocketException e){
                                   out.close();
                                   in.close();

                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }

                        try {
                            out.writeObject("OK");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;

                    case 1: // LEARNING A DECISION TREE

                        tree = new RegressionTree(trainingSet);

                        try {
                            tree.salva(tableName + ".dmp");
                            out.writeObject("OK");


                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;


                    case 2:
                        try {
                            while (true) {
                                try {
                                    tableName = (String) in.readObject();
                                    tree = RegressionTree.carica(tableName + ".dmp");
                                    out.writeObject("Table is valid!");
                                    break;
                                } catch (IOException e) {
                                    File dump = new File(tableName + ".dmp");
                                    dump.delete();
                                    out.writeObject("No such table!");
                                }
                            }
                            out.writeObject("OK");

                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                        break;

                    case 3:
                        try {
                            out.writeObject("QUERY");

                            Double classValue = tree.predictClass(in, out);
                            out.writeObject("OK");
                            out.writeObject(classValue);

                        } catch (IOException e) {
                            in.close();
                            out.close();
                        } catch (UnknownValueException e) {
                            e.printStackTrace();
                        }
                        break;
                }
            } catch (Exception e) {
                cycle = false;
                try {
                    out.writeObject(e);
                } catch (IOException ex) {

                }

            }
        } // END SWITCH

        try {
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {

        }

    }
}




