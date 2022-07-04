package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.function.UnaryOperator;

/**
 * Controller per la gestione delle scene
 */
public class Controller {

    /**
     * Stringa usata per individuare e aprire la finestra corrispondente
     */
    public static String caseName = null;

    /**
     * Object RtClient che comunica col server
     */
    public static RtClient menu;

    /**
     * Attributo booleano che tiene traccia della connessione se attiva o meno
     */
    public static boolean connection = false;

    /**
     * Stringa che conterr√† l'indirizzo IP
     */
    public static String ip;

    /**
     * TextField per inserire l'indirizzo IP
     */
    @FXML
    private TextField textIp = new TextField();

    /**
     * Button dell'interfaccia grafica per la connessione con il server
     */
    @FXML
    private Button buttonConnection = new Button();

    /**
     * Button dell'interfaccia grafica per la scelta di caricare da Database
     */
    @FXML
    private Button buttonFromDB = new Button();

    /**
     * Button dell'interfaccia grafica per la scelta di caricare da File
     */
    @FXML
    private Button fromFileButton = new Button();

    /**
     * Button dell'interfaccia grafica per ritentare la connessione
     */
    @FXML
    private Button buttonRetry = new Button();

    /**
     * Label dell'interfaccia che indica se la connessione √® avvenuta
     */
    @FXML
    private Label linked = new Label();

    /**
     * Label dell'interfaccia grafica che infor
     */
    @FXML
    private Label ipControl = new Label();


    /**
     * Questo metodo formatta la textField dell'indirizzo IP per permettere
     * solo inserimento di numeri e punti e controlla che la connessione sia avvenut.
     * Quando la connessione √® gi√† avvenuta, si disattiveranno la textField per l'IP e i bottoni
     * per il load da DB e da File.
     */
    public void initialize() {
        if (connection == true) {
            System.out.println(ip);
            textIp.setText(ip);
            textIp.setDisable(true);

            linked.setVisible(true);
            buttonFromDB.setDisable(false);
            fromFileButton.setDisable(false);
            buttonFromDB.defaultButtonProperty().bind(buttonFromDB.focusedProperty());
            fromFileButton.defaultButtonProperty().bind(fromFileButton.focusedProperty());

        }
        buttonConnection.defaultButtonProperty().bind(buttonConnection.focusedProperty());
        UnaryOperator<TextFormatter.Change> ipFilter = change -> {
            String input = change.getText();
            if (input.matches("[.]*[0-9]*")) {
                ipControl.setVisible(false);
                return change;
            } else {
                ipControl.setVisible(true);
            }

            return null;
        };

        buttonConnection.defaultButtonProperty().bind(buttonConnection.focusedProperty());


        textIp.setTextFormatter(new TextFormatter<>(ipFilter));
    }


    @FXML
    /**
     * Questo metodo e richiamato quando il bottone di connessione e cliccato.
     * Se la connessione avviene correttamente si attiveranno i bottoni del Load
     * from DB e load from File.
     * Se la connessione non avviene, apparir√† il bottone Retry.
     */
    public void connectButton() {

        try {
            menu = new RtClient(ip);
            System.out.println(menu);
            buttonFromDB.setDisable(false);
            fromFileButton.setDisable(false);
            buttonConnection.setVisible(false);
            textIp.setDisable(true);
            linked.setText("CONNECTION ACCEPTED!");
            linked.setTextFill(Color.WHITE);
            linked.setVisible(true);
            connection = true;


            buttonFromDB.defaultButtonProperty().bind(buttonFromDB.focusedProperty());
            fromFileButton.defaultButtonProperty().bind(fromFileButton.focusedProperty());
            if (buttonFromDB.isFocused()) {
                buttonFromDB.setUnderline(true);
            }
            if (fromFileButton.isFocused()) {
                fromFileButton.setUnderline(true);
            }

        } catch (IOException e) {
            System.out.println(e);
            linked.setText("CONNECTION FAILED!");
            linked.setTextFill(Color.RED);
            textIp.setDisable(true);

            buttonConnection.setVisible(false);
            linked.setVisible(true);
            buttonRetry.setVisible(true);
            buttonRetry.setDisable(false);
            buttonRetry.defaultButtonProperty().bind(buttonRetry.focusedProperty());
            return;
        }

    }


    /**
     * Questo metodo verifica l'inserimento corretto dell'IP
     *
     * @param ip valore dell'IP Address
     * @return true o false a seconda che l'indirizzo sia corretto
     */


    public static boolean validate(final String ip) {
        String PATTERN = "^((0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)\\.){3}(0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)$";

        return ip.matches(PATTERN);
    }


    @FXML
    /**
     * Questo metodo verifica che il campo IP non sia vuoto e abbia il formato corretto
     */
    public void enableConnect() {

        if (!textIp.getText().trim().isEmpty() && validate(textIp.getText().trim())) {
            ip = textIp.getText();
            buttonConnection.setDisable(false);

        } else {
            buttonConnection.setDisable(true);
        }


    }

    @FXML
    /**
     * Questo metodo verra† eseguito quando il bottone Load From Data sara† cliccato.
     * Inizializza il caseName con cui sara† chiamata la finestra relativa e richiama
     * il metodo openWindow.
     *
     * @param event gestisce l'evento relativo al bottone
     * @throws IOException eccezione prodotta da un errore di I/O
     */

   public void loadFromDB(ActionEvent event) {
        caseName = "fromDb";
        openWindow(event, "Load Data From Database");
    }


    @FXML
    /**
     * Questo metodo e eseguito quando il bottone Load from File e cliccato.
     * Inizializza il caseName con cui sara† chiamata la finestra relativa e richiama
     * il metodo openWindow.
     *
     * @param event gestisce l'evento relativo al bottone
     * @throws IOException eccezione prodotta da un errore di I/O
     */

   public void loadFromFile(ActionEvent event) {
        caseName = "fromFile";
        openWindow(event, "Load From File");
    }


    /**
     * Questo metodo e richiamato da LoadDataFromDB e LoadFromFile. Inizializza e apre una finestra
     * load from col titolo indicato dal secondo parametro.
     *
     * @param event gestisce l'evento relativo al bottone
     * @param title titolo della nuova finestra
     */
    public void openWindow(ActionEvent event, String title) {
        Parent parent = null;
        try {
            parent = FXMLLoader.load(getClass().getResource("Load_From.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(parent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.setTitle(title);
        window.show();
    }

    @FXML
    /**
     * Questo metodo e eseguito quando il bottone di Retry viene cliccato.
     * Questo bottone sara† visibile solo quando la connessione fallira†.
     */
    public void retryConnection() {
        buttonRetry.setVisible(false);
        buttonConnection.setVisible(true);
        buttonConnection.setDisable(false);

        linked.setVisible(false);
        textIp.clear();
        textIp.setDisable(false);

    }


}



