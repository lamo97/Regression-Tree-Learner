package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.EOFException;
import java.io.IOException;
import java.util.function.UnaryOperator;

import static application.Controller.ip;
import static java.lang.System.exit;
import static java.lang.System.out;


/**
 * Classe che controlla il load da Database o da File
 */
public class Load_From_Controller {


    @FXML
    /**
     * Nome della tabella
     */
    private String tableName;

    @FXML
    /**
     * TextField per l'inserimento della tabella
     */
    private TextField tableNameValue = new TextField();

    @FXML
    /**
     * TextField per l'inserimento del branch
     */
    private TextField branchValue = new TextField();

    @FXML
    /**
     * TextArea dove saranno visibile l'albero e le scelte utente
     */
    private TextArea output = new TextArea();

    @FXML
    /**
     * Button per avviare l'esecuzione
     */
    private Button executionButton = new Button();

    @FXML
    /**
     * Button per eseguire una nuova esecuzione
     */
    private Button newExecutionButton = new Button();

    @FXML
    /**
     * Button per uscire dalla finestra
     */
    private Button exitButton = new Button();


    @FXML
    /**
     * Button per inserire il branchValue
     */
    private Button okButton = new Button();

    @FXML
    /**
     * Button per tornare alla finestra precedente
     */
    private Button menuButton = new Button();


    /**
     * Questo metodo controlla che il valore del branch sia solamente numerico
     * In base al caseName della classe Controller (quindi se da File o da DB)
     * disattiva e attiva il bottone New Execution.
     */
    public void initialize() {


        newExecutionButton.setDisable(true);

        menuButton.defaultButtonProperty().bind(menuButton.focusedProperty());

        exitButton.defaultButtonProperty().bind(exitButton.focusedProperty());
        executionButton.defaultButtonProperty().bind(executionButton.focusedProperty());
        newExecutionButton.defaultButtonProperty().bind(newExecutionButton.focusedProperty());
        okButton.defaultButtonProperty().bind(okButton.focusedProperty());
        UnaryOperator<TextFormatter.Change> integerFilter = change -> {
            String input = change.getText();
            if (input.matches("[-]*[0-9]*")) {
                return change;
            }
            return null;
        };
        branchValue.setTextFormatter(new TextFormatter<>(integerFilter));
    }


    @FXML
    /**
     * Disabilita il bottone di esecuzione
     */
    public void enableExecute() {

        executionButton.setDisable(false);

    }

    @FXML
    /**
     * Inizializza il valore di tableName
     */
    public void setTableName() {
        tableName = tableNameValue.getText();


    }


    /**
     * Questo metodo viene eseguito quando il bottone Execute √® cliccato.
     * In base al contenuto di caseName eseguira† operazioni diverse.
     *
     * @throws ClassNotFoundException Classe non trovata o errore di cast
     * @throws IOException            eccezione prodotta da errori di I/O
     * @throws ServerException		  eccezione sollevata dal server
     */
    public void execute() throws ClassNotFoundException, IOException, ServerException {


        setTableName();
        String str;
        tableNameValue.setDisable(true);

        System.out.println("table name: " + tableName);
        branchValue.setDisable(false);
        okButton.setDisable(false);
        branchValue.requestFocus();


        if (Controller.caseName.equals("fromDb")) {


            try {


                Controller.menu.storeTableFromDb(tableName);


                output.setText("GENERATE DECISION TREE\n");
                output.appendText("================\n");
                str = Controller.menu.learningFromDbTable();
                output.appendText(str);
                executionButton.setDisable(true);

            } catch (ServerException e) {


                output.appendText(e.getMessage());
                executionButton.setDisable(true);
                okButton.setDisable(true);
                branchValue.setDisable(true);
                tableNameValue.clear();
                newExecutionButton.setDisable(false);
                newExecutionButton.requestFocus();


            }

        } else {

            menuButton.setDisable(false);

            try {
                str = Controller.menu.learningFromFile(tableName);
                output.setText("GENERATE DECISION TREE\n");
                output.appendText("================\n");
                output.appendText("Start prediction phase\n");
                output.appendText("\n" + str);
                executionButton.setDisable(true);


            } catch (IOException e) {
                output.appendText(e.getMessage());
                executionButton.setDisable(true);
                okButton.setDisable(true);
                branchValue.setDisable(true);
                tableNameValue.clear();
                newExecutionButton.setDisable(false);
                newExecutionButton.requestFocus();
            }


        }

    }


    @FXML
    /**
     * Questo metodo e eseguito quando il bottone NewExecution e cliccato.
     * Il bottone diventa visibile solo quando si effettua il Load from DB ed e attivo
     * quando l'esecuzione termina (correttamente o con errore).
     * Esso permette di inserire nuovamente il nome della tabella nel caso in cui
     * il primo inserimento non sia andato a buon fine o l'esecuzione sia terminata.
     */
    public void newExecution() {

        tableNameValue.clear();
        branchValue.clear();
        output.clear();

        tableNameValue.setDisable(false);


        executionButton.setDisable(false);

        newExecutionButton.setDisable(true);
        tableNameValue.requestFocus();
        branchValue.setDisable(true);

    }


    @FXML
    /**
     * Questo metodo e eseguito quando il bottone Exit e premuto e permette la chiusura
     * della finestra
     */
    public void exitFrame() {

        exit(0);

    }

    @FXML
    /**
     * Questo metodo e eseguito quando il bottone OK e cliccato.
     * Legge l'inserimento nel TextField del branch e verifica che il branch inserito
     * non sia diverso da 0 o 1.
     * Nel caso in cui l'inserimento vada a buon fine, il valore del branch sar√† passato
     * come parametro a due metodi richiamati a seconda del contenuto di caseName.
     * Il metodo quindi mostra nella TextArea la discesa dell'albero sulla base del branch
     * inserito dall'utente.
     * Quando l'albero sara† terminato il metodo mostrera† la classe predetta.
     */
    public void goAction() throws IOException, ClassNotFoundException {


        branchValue.requestFocus();
        String branch = branchValue.getText();
        if (!(branch.equals("0") || branch.equals("1"))) {
            output.appendText("Invalid branch choice");
            branchValue.clear();
            output.appendText("\nPlease insert a number between 0 and 1\n");
        } else {
            output.appendText("\n\n" + branch);
            String str;
            if (Controller.caseName.equals("fromDb")) {
                str = Controller.menu.learningFromDbTable(Integer.parseInt(branch));

            } else {
                str = Controller.menu.learningFromFile(Integer.parseInt(branch));
            }


            if (str.matches(".*\\d+.*")) {
                output.appendText("\nPredicted class Value : " + str);
                branchValue.setDisable(true);
                newExecutionButton.setDisable(false);
                okButton.setDisable(true);

            } else {

                output.appendText("\n" + str);
            }
            branchValue.clear();

        }


    }

    @FXML

    /**
     *QUesto metodo e eseguito quando il bottone Menu e cliccato. Riporta
     * l'utente alla finestra precedente.
     * @param event  gestisce l'azione del bottone
     * @throws IOException eccezione causata da errori di I/O
     */

    public void menu(ActionEvent event) throws IOException {

        Parent parent = FXMLLoader.load(getClass().getResource("Menu.fxml"));
        Scene scene = new Scene(parent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.setTitle("Menu");
        window.show();

    }


}
