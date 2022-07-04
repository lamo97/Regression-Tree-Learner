
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import utility.Keyboard;

/**
 * Gestisce l'interazione con l'utente
 */

public class MainTest {
    private ObjectOutputStream out = null;
    private ObjectInputStream in = null;

    /**
     * Metodo main della classe
     * @param args parametri in input
     */
    public static void main(String[] args) {

        InetAddress addr;
        try {
            addr = InetAddress.getByName(args[0]);
        } catch (UnknownHostException e) {
            System.out.println(e.toString());
            return;
        }
        Socket socket = null;
        ObjectOutputStream out = null;
        ObjectInputStream in = null;
        try {
            socket = new Socket(args[0], new Integer(args[1]).intValue());
            System.out.println(socket);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            ; // stream con richieste del client

        } catch (IOException e) {
            System.out.println(e.toString());
            return;
        }


        String answer = "";

        int decision = 0;
        do {

            System.out.println("Learn Regression Tree from data [1]");
            System.out.println("Load Regression Tree from archive [2]");
            decision = Keyboard.readInt();
        } while (!(decision == 1) && !(decision == 2));

        String tableName = "";
        String response = " ";
        System.out.println("Table or File name:");
   
        try {

            if (decision == 1) {
                System.out.println("Starting data acquisition phase!");

                out.writeObject(0);
                
                response = " ";
                do {
                    tableName = Keyboard.readString();
                    out.writeObject(tableName);
                    response = (String) in.readObject();

                    if (!response.equals("Table is valid!")) {
                        System.out.println("Wrong table name. Retry!\nTable name: ");
                    }
                } while (!response.equals("Table is valid!"));

                answer = in.readObject().toString();
                if (!answer.equals("OK")) {
                    System.out.println(answer);
                    return;
                }

                System.out.println("Starting learning phase!");
                out.writeObject(1);

            } else {
                out.writeObject(2);
           
                do {
                    tableName = Keyboard.readString();
                    out.writeObject(tableName);
                    response = (String) in.readObject();

                    if (!response.equals("Table is valid!")) {
                        System.out.println("Wrong file name. Retry!\nFile name: ");
                    }
                } while (!response.equals("Table is valid!"));

            }

            answer = in.readObject().toString();
            if (!answer.equals("OK")) {
                System.out.println(answer);
                return;
            }


            char risp = 'y';

            do {
                out.writeObject(3);

                System.out.println("Starting prediction phase!");
                answer = in.readObject().toString();


                while (answer.equals("QUERY")) {
                    // Formualting query, reading answer

                    answer = in.readObject().toString();
                    if (answer.equals("OK")) {
                        break;
                    }
                    System.out.println(answer);
                    int path = Keyboard.readInt();
                    out.writeObject(path);
                    answer = in.readObject().toString();
                }

                if (answer.equals("OK")) { // Reading prediction
                    answer = in.readObject().toString();
                    System.out.println("Predicted class:" + answer);
                } else {
                    //Printing error message
                    System.out.println(answer);
                }

                System.out.println("Would you repeat ? (y/n)");
                risp = Keyboard.readChar();

            } while (Character.toUpperCase(risp) == 'Y');


        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.toString());

        }

        try {
            in.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
