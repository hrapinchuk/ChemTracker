package utility;

import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;

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

    /**
     * This method displays an alert to the user to confirm whether to perform an action.
     * @param content A String representing the message to display in the alert
     * @return A boolean representing whether the user has confirmed an action (true) or not (false)
     */
    public static boolean showConfAlert(String content) {
        // Display a message to confirm that the user wants to perform an action
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText(content);

        // Assign user's input to result
        Optional<ButtonType> result = alert.showAndWait();

        // Return result
        return result.isPresent() && result.get() == ButtonType.OK;
    }

    /**
     * This method displays an informational alert to the user.
     * @param content A String representing the message to display in the alert
     */
    public static void showInfoAlert(String content) {
        // Display a message to the user
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(content);
        alert.show();
    }
}
