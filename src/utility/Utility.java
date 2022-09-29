package utility;

import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
}
