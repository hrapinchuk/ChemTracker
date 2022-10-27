package utility;

import javafx.animation.FadeTransition;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import model.TextInputInfo;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
     * This method closes a modal screen.
     * @param actionEvent The event prompting this method call
     * @throws IOException If an input/output exception occurs
     */
    public static void closeModal(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    /**
     * This method fires a WINDOW_SHOWN event in the current stage's parent stage.
     * @param actionEvent The event prompting this method call
     */
    public static void fireParentWindowShownEvent(ActionEvent actionEvent) {
        Stage currentStage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Stage parentStage = (Stage)currentStage.getOwner();
        parentStage.fireEvent(new WindowEvent(parentStage, WindowEvent.WINDOW_SHOWN));
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
     * This method performs validations on a user-entered value and determines whether it is valid.
     * This method checks whether a TextInputControl's value is not empty, does not exceed its
     * maxLength, and/or matches a provided pattern. If any of the checks return false, an error
     * message is displayed and the method returns false.
     * @param inputControl The TextInputControl whose value is being validated
     * @param inputInfo A TextInputInfo object containing information about the TextInputControl
     *                  that is needed to perform the data validation
     * @param errorLabel A Label where an error message may be displayed, if needed
     * @return A boolean representing whether the input is valid (true) or not (false)
     */
    public static boolean validateTextInput(TextInputControl inputControl,
                                            TextInputInfo inputInfo,
                                            Label errorLabel) {
        // Variable declarations
        String inputString = inputControl.getText();
        boolean validInput = true;
        StringBuilder errorSB = new StringBuilder();

        // If input is required, validate that input is not null
        if (inputInfo.isRequired() && !validateTextInputNotNull(inputString)) {
            validInput = false;
            appendToStringBuilder("Required", errorSB);
        }

        // Validate that input does not exceed maxLength
        if (!validateTextInputLength(inputString, inputInfo.getMaxLength())) {
            validInput = false;
            String lengthError = "Cannot exceed " + inputInfo.getMaxLength() + " characters";
            appendToStringBuilder(lengthError, errorSB);

            // Shorten input to maximum allowable length
            String maxString = inputString.substring(0, inputInfo.getMaxLength());
            inputControl.setText(maxString);
        }

        // If pattern is provided, validate that input matches pattern
        if (inputInfo.getPattern() != null && !validateTextInputPattern(inputString, inputInfo.getPattern())) {
            validInput = false;
            appendToStringBuilder(inputInfo.getPatternError(), errorSB);
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
     * This method adds a ChangeListener to a TextInputControl to perform data validations on change.
     * @param inputControl The TextInputControl whose value is being validated
     * @param inputInfo A TextInputInfo object containing information about the TextInputControl
     *                  that is needed to perform the data validation
     * @param errorLabel A Label where an error message may be displayed, if needed
     */
    public static void validateTextInputOnChange(TextInputControl inputControl,
                                                 TextInputInfo inputInfo,
                                                 Label errorLabel) {
        // Implement ChangeListener to validate text input on change
        ChangeListener<String> listener = ((observableValue, oldValue, newValue) -> {
            validateTextInput(inputControl, inputInfo, errorLabel);
        });

        // Add change listener to inputControl
        inputControl.textProperty().addListener(listener);
    }

    /**
     * This method converts a time from a 12-hour clock to a 24-hour clock.
     * This method receives a date, time, and whether the time occurs in AM or PM. Using the
     * received information, this method creates and returns a LocalDateTime on a 24-hour clock.
     * @param timeString A String representing a time on a 12-hour clock
     * @param isAM A boolean representing whether the timeString is in AM or PM
     * @param localDate A LocalDate object representing the date to associate with the time
     * @return A LocalDateTime object representing the localDate and timeString on a 24-hour clock
     */
    public static LocalDateTime convertTo24Hr(String timeString, boolean isAM, LocalDate localDate) {
        // Variable declarations
        LocalTime hrTwelve = LocalTime.of(12, 00);
        LocalTime hrThirteen = LocalTime.of(13, 00);
        LocalTime hrOne = LocalTime.of(01, 00);

        // If timeString needs a preceding 0, prepend it
        if (timeString.length() <= 4) {
            timeString = "0" + timeString;
        }

        // Convert timeString to LocalTime
        LocalTime timeLT = LocalTime.parse(timeString);

        // If time is between 12:00 and 12:59 AM, subtract 12 hours
        if (isAM && (timeLT.equals(hrTwelve) || (timeLT.isAfter(hrTwelve) && timeLT.isBefore(hrThirteen)))) {
            timeLT = timeLT.minusHours(12);

        // If time is between 1:00 and 11:59 PM, add twelve hours
        } else if (!isAM && (timeLT.equals(hrOne) || (timeLT.isAfter(hrOne) && timeLT.isBefore(hrTwelve)))) {
            timeLT = timeLT.plusHours(12);
        }

        // Return LocalDateTime
        return LocalDateTime.of(localDate, timeLT);
    }

    /**
     * This method determines whether a provided time occurs in AM or PM.
     * @param localTime The LocalTime object to be evaluated
     * @return A boolean indicating whether localTime occurs in AM (true) or PM (false)
     */
    public static boolean isAM(LocalTime localTime) {
        // Variable declarations
        LocalTime hrZero = LocalTime.of(0, 0, 0);
        LocalTime hrTwelve = LocalTime.of(12, 0, 0);

        // Determine whether localTime occurs in AM or PM, and return accordingly
        return (localTime.equals(hrZero) || localTime.isAfter(hrZero)) && localTime.isBefore(hrTwelve);
    }
}
