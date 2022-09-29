package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;
import java.util.*;

/**
 * This class creates the ChemTracker Application.
 * @author Heather Rapinchuk
 */
public class Main extends Application {
    /**
     * This is the first method that is called when running this program.
     * @param args Java command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * This method shows the MainScreen.
     * @param stage A Stage object representing the primary window of this application
     * @throws Exception If an exception occurs
     */
    @Override
    public void start(Stage stage) throws Exception {
        URL viewURL = Objects.requireNonNull(getClass().getResource("/view/MainScreen.fxml"));
        URL styleURL = Objects.requireNonNull(getClass().getResource("/style/Style.css"));
        Parent root = FXMLLoader.load(viewURL);
        Scene scene = new Scene(root);
        scene.getStylesheets().add(styleURL.toExternalForm());
        stage.setScene(scene);
        stage.setTitle("ChemTracker");
        stage.show();
    }
}
