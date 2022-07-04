package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Classe Main
 */
public class Main extends Application {

    /**
     * Questo metodo apre la finestra prescelta
     *
     * @param primaryStage prima scena da mostrare
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("Menu.fxml"));
        primaryStage.setTitle("Menu");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


    /**
     * main
     *
     * @param args string
     */
    public static void main(String[] args) {
        launch(args);
    }
}
