package utility;

import javafx.animation.FadeTransition;
import javafx.beans.value.ChangeListener;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;

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
     * This method shows a screen whose object hierarchy has already been loaded.
     * @param event The event prompting this method call
     * @param loader An FXMLLoader that has already loaded the view to be displayed in the screen
     */
    public static void showLoaderScreen(Event event, FXMLLoader loader){
        URL styleURL = Objects.requireNonNull(Utility.class.getResource("/style/Style.css"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Parent root = loader.getRoot();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(styleURL.toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This method shows a modal whose object hierarchy has already been loaded.
     * @param event The event prompting this method call
     * @param loader An FXMLLoader that has already loaded the view to be displayed in the modal
     * @param screenTitle A String containing the title to give the modal window
     */
    public static void showLoaderModal(Event event, FXMLLoader loader, String screenTitle){
        URL styleURL = Objects.requireNonNull(Utility.class.getResource("/style/Style.css"));
        Stage primaryStage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Stage stage = new Stage();
        Parent root = loader.getRoot();
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

    /**
     * This method displays an error alert to the user.
     * @param content A String representing the message to display in the alert
     */
    public static void showErrorAlert(String content) {
        // Display a message to the user
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(content);
        alert.show();
    }

    /**
     * This method displays a message to the user within a Label, and then fades it out.
     * @param msgLabel A Label where the message will be displayed
     * @param content A String representing the content of the message
     */
    public static void showAndFadeMessage(Label msgLabel, String content) {
        // Set message text
        msgLabel.setText(content);

        // Fade message in
        FadeTransition fadeIn = new FadeTransition(Duration.millis(0), msgLabel);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
        fadeIn.play();

        // Fade message out
        FadeTransition fadeOut = new FadeTransition(Duration.millis(1000), msgLabel);
        fadeOut.setDelay(Duration.millis(2000));
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);
        fadeOut.play();
    }

    /**
     * This method appends a String to a StringBuilder, adding a comma if necessary.
     * @param item The String to be appended
     * @param stringBuilder The StringBuilder to which the String will be appended
     */
    public static void appendToStringBuilder(String item, StringBuilder stringBuilder) {
        if (stringBuilder.isEmpty()) {
            stringBuilder.append(item);
        } else {
            stringBuilder.append(", ").append(item);
        }
    }

    /**
     * This method validates whether a String has been given a value.
     * @param inputString The String being validated
     * @return A boolean representing whether the String has been given a value (true) or not (false)
     */
    public static boolean validateTextInputNotNull(String inputString) {
        return !inputString.isEmpty();
    }

    /**
     * This method validates whether a String's length has exceeded its maximum allotted value.
     * @param inputString The String being validated
     * @param maxLength An int representing the maximum length of the String
     * @return A boolean representing whether the String's maxLength has been exceeded (false) or
     * not (true)
     */
    public static boolean validateTextInputLength(String inputString, int maxLength) {
        return inputString.length() <= maxLength;
    }

    /**
     * This method validates whether a String matches a provided pattern.
     * @param inputString The String being validated
     * @param pattern A String containing a regular expression, representing an expected pattern
     * @return A boolean representing whether the String matches the pattern (true) or not (false)
     */
    public static boolean validateTextInputPattern(String inputString, String pattern) {
        return Pattern.matches(pattern, inputString);
    }

    /**
     * This method performs several validations on a String input and determines whether it is valid.
     * This method checks whether a user-entered String contains a value, does not exceed its
     * maxLength, and/or matches a provided pattern. If any of the checks return false, an error
     * message is displayed and the method returns false.
     * @param inputString The String being validated
     * @param required A boolean indicating whether the String must contain a value
     * @param maxLength An int representing the maximum length of the String
     * @param pattern A String containing a regular expression, representing an expected pattern
     * @param errorLabel A Label where an error message will be displayed, if needed
     * @return A boolean representing whether the input is valid (true) or not (false)
     */
    public static boolean validateTextInput(String inputString,
                                            boolean required,
                                            int maxLength,
                                            String pattern,
                                            Label errorLabel) {
        // Variable declarations
        boolean validInput = true;
        StringBuilder errorSB = new StringBuilder();

        // If input is required, validate that input is not null
        if (required && !validateTextInputNotNull(inputString)) {
            validInput = false;
            errorSB.append("Required");
        }

        // Validate that input does not exceed maxLength
        if (!validateTextInputLength(inputString, maxLength)) {
            validInput = false;
            String lengthError = "Cannot exceed " + maxLength + " characters";
            appendToStringBuilder(lengthError, errorSB);
        }

        // If pattern is provided, validate that input matches pattern
        if (pattern != null && !validateTextInputPattern(inputString, pattern)) {
            validInput = false;
            String patternError = "Invalid pattern";
            appendToStringBuilder(patternError, errorSB);
        }

        // Display error message and return boolean indicating whether input is valid
        errorLabel.setText(errorSB.toString());
        return validInput;
    }

    /**
     * This method validates whether an item has been selected from a ComboBox.
     * @param comboBox The ComboBox whose value is being validated
     * @param errorLabel A Label where an error message will be displayed, if needed
     * @return A boolean representing whether an item has been selected (true) or not (false)
     */
    public static boolean validateComboBoxInput(ComboBox comboBox, Label errorLabel) {
        // Variable declaration
        boolean validInput = true;
        String errorMsg = "";

        // If an item hasn't been selected from the ComboBox, mark as invalid and create error message
        if (comboBox.getSelectionModel().getSelectedItem() == null) {
            validInput = false;
            errorMsg = "Required";
        }

        // Display error message, and return boolean indicating whether input is valid
        errorLabel.setText(errorMsg);
        return  validInput;
    }

    /**
     * This method adds a ChangeListener to a TextInput to enforce its maxLength as a user types.
     * @param textInput The TextInputControl whose maxLength is being enforced
     * @param maxLength An int representing the max number of characters allowed in the textInput
     * @param errorLabel A Label where an error message may be displayed
     */
    public static void enforceMaxLength(TextInputControl textInput, int maxLength, Label errorLabel) {
        // Implement ChangeListener to shorten text input if user enters input that is greater than
        // the allowable length
        ChangeListener<String> listener = ((observableValue, oldValue, newValue) -> {
            if (!validateTextInputLength(textInput.getText(), maxLength)) {
                String maxString = textInput.getText(0, maxLength);
                textInput.setText(maxString);
                showAndFadeMessage(errorLabel, "Cannot exceed " + maxLength + " characters");
            }
        });

        // Add change listener to textInput
        textInput.textProperty().addListener(listener);
    }
}
