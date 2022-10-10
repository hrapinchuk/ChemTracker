package utility;

import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

/**
 * This class represents utilities used by other classes.
 * @author Heather Rapinchuk
 */
public class Utility {
    /**
     * This method shows a new screen in the existing window.
     * @param event The event prompting this method call
     * @param fileName A String containing the name of the file to display
     * @throws IOException If an input/output exception occurs
     */
    public static void showScreen(Event event, String fileName) throws IOException {
        URL viewURL = Objects.requireNonNull(Utility.class.getResource("/view/" + fileName));
        URL styleURL = Objects.requireNonNull(Utility.class.getResource("/style/Style.css"));
        Parent root = FXMLLoader.load(viewURL);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(styleURL.toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This method opens a new modal window that blocks input to its parent window.
     * @param event The event prompting this method call
     * @param fileName A String containing the name of the file to display
     * @param screenTitle A string containing the title to give the modal window
     * @throws IOException If an input/output exception occurs
     */
    public static void showModal(Event event,
                                 String fileName,
                                 String screenTitle) throws IOException {
        URL viewURL = Objects.requireNonNull(Utility.class.getResource("/view/" + fileName));
        URL styleURL = Objects.requireNonNull(Utility.class.getResource("/style/Style.css"));
        Parent root = FXMLLoader.load(viewURL);
        Stage primaryStage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(styleURL.toExternalForm());
        stage.setTitle(screenTitle);
        stage.setScene(scene);
        // Adapted from https://www.tabnine.com/code/java/methods/javafx.stage.Stage/initModality
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(primaryStage);
        stage.show();
    }
}
