package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Classe che gestisce la logica multiclient concorrenziale del server
 */
public class MultiServer {

    private int PORT = 8080;

    /**
     * Costruttore di classe
     *
     * @param	port numero di porta sulla quale il server sara  in ascolto
     * @throws	IOException	eccezione generata da errori di I/O
     */
    public MultiServer(int port) throws IOException {
        this.PORT = port;
        run();
    }

    
    /**
     * Metodo main della classe
     * @param args eventuale input da tastiera
     * @throws IOException eccezione generata da errori di I/O
     */
    public static void main(String[] args) throws IOException {

        int port = 8080;
        MultiServer mS = new MultiServer(port);

    }
    
    /**
     * Inizializza la ServerSocket e rimane in ascolto
     *
     * @throws IOException eccezione generata da errori di I/O
     */
    private void run() throws IOException {

        ServerSocket server = new ServerSocket(8080);
        System.out.println("Server Started");
        try {
            while (true) {
                Socket socketServer = server.accept();
                ServerOneClient connection = new ServerOneClient(socketServer);

            }
        } finally {
            server.close();

        }
    }
}
