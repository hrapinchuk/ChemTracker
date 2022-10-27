package controller;

import dao.DBChemUse;
import dao.DBChemical;
import dao.DBMethod;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import model.*;
import utility.Utility;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

/**
 * This controller class applies to the ChemUseDetailScreen.
 * @author Heather Rapinchuk
 */
public class ChemUseDetailScreen implements Initializable {
    // Variable declarations
    @FXML
    private Label useAmountLabel;
    @FXML
    private DatePicker useDatePicker;
    @FXML
    private TextField useTimeText;
    @FXML
    private ComboBox<Chemical> useChemCombo;
    @FXML
    private TextField useAmountText;
    @FXML
    private ComboBox<Method> useMethodCombo;
    @FXML
    private TextField useDilutionText;
    @FXML
    private TextField useAreaText;
    @FXML
    private TextArea useAreaDescText;
    @FXML
    private Label useDateTimeError;
    @FXML
    private Label useChemError;
    @FXML
    private Label useAmountError;
    @FXML
    private Label useMethodError;
    @FXML
    private Label useDilutionError;
    @FXML
    private Label useAreaError;
    @FXML
    private Label useAreaDescError;
    @FXML
    private ToggleGroup timeToggle;
    @FXML
    private ToggleButton useTimeAMBtn;
    @FXML
    private ToggleButton useTimePMBtn;
    @FXML
    private Button saveUseBtn;
    @FXML
    private Button cancelSaveUseBtn;
    private int chemUseID = -1;
    private DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm");
    private final TextInputInfo useDateInputInfo = new TextInputInfo(
            true,
            10,
            "^[0,1]?\\d{1}\\/(([0-2]?\\d{1})|([3][0,1]{1}))\\/(([1]{1}[9]{1}[9]{1}\\d{1})|([2-9]{1}\\d{3}))$",
            "Date must be formatted as MM/DD/YYYY"
    );
    private final TextInputInfo useTimeInputInfo = new TextInputInfo(
            true,
            5,
            "^(([0][0-9]|[1][0-2])|[0-9]):([0-5][0-9])$",
            "Time must be formatted as HH:MM"
    );
    private final TextInputInfo useAmountInputInfo = new TextInputInfo(
            true,
            10,
            "(^\\d*\\.?\\d*[1-9]+\\d*$)|(^[1-9]+\\d*\\.\\d*$)",
            "Must contain only digits and a decimal"
    );
    private final TextInputInfo useDilutionInputInfo = new TextInputInfo(
            false,
            10,
            "(^\\d*\\.?\\d*[1-9]+\\d*$)|(^[1-9]+\\d*\\.\\d*$)",
            "Must contain only digits and a decimal"
    );
    private final TextInputInfo useAreaInputInfo = new TextInputInfo(
            true,
            10,
            "(^\\d*\\.?\\d*[1-9]+\\d*$)|(^[1-9]+\\d*\\.\\d*$)",
            "Must contain only digits and a decimal"
    );
    private final TextInputInfo useAreaDescInputInfo = new TextInputInfo(
            false,
            200,
            null,
            null
    );

    /**
     * This method is called when the ChemUseDetailScreen is initialized.
     * This method populates ComboBoxes and adds change listeners to form inputs, which perform data
     * validations as the user types.
     * @param url An object used to resolve the location of the root object
     * @param resourceBundle A bundle of objects that localize the root object
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Variable declarations
        ObservableList<Chemical> allChemicals = DBChemical.getAllChemicals();
        ObservableList<Method> allMethods = DBMethod.getAllMethods();

        // Populate ComboBoxes
        useChemCombo.setItems(allChemicals);
        useChemCombo.setVisibleRowCount(5);
        useChemCombo.setPromptText("Select Chemical");

        useMethodCombo.setItems(allMethods);
        useMethodCombo.setVisibleRowCount(5);
        useMethodCombo.setPromptText("Select Application Method");

        // Add change listeners to perform data validations
        Utility.validateTextInputOnChange(useDatePicker.getEditor(), useDateInputInfo, useDateTimeError);
        Utility.validateTextInputOnChange(useTimeText, useTimeInputInfo, useDateTimeError);
        Utility.validateTextInputOnChange(useAmountText, useAmountInputInfo, useAmountError);
        Utility.validateTextInputOnChange(useDilutionText, useDilutionInputInfo, useDilutionError);
        Utility.validateTextInputOnChange(useAreaText, useAreaInputInfo, useAreaError);
        Utility.validateTextInputOnChange(useAreaDescText, useAreaInputInfo, useAreaDescError);
    }

    /**
     * This method receives data and populates the form as needed.
     * This method receives data that is passed from the ChemUseScreen. Using received data, this
     * method performs UI changes, assigns values to variables, and populates form inputs as needed.
     * @param chemUse The ChemUse object to be updated
     */
    public void showChemUseData(ChemUse chemUse) {
        // Populate text inputs with chemUse data
        useDatePicker.setValue(chemUse.getUseDateTime().toLocalDate());
        useTimeText.setText(chemUse.getUseDateTime().toLocalTime().format(timeFormatter));
        useAmountText.setText(Double.toString(chemUse.getAmount()));
        useDilutionText.setText(Double.toString(chemUse.getDilution()));
        useAreaText.setText(Double.toString(chemUse.getArea()));
        useAreaDescText.setText(chemUse.getAreaDesc());

        // Select appropriate AM/PM toggle button
        if (Utility.isAM(chemUse.getUseDateTime().toLocalTime())) {
            useTimeAMBtn.setSelected(true);
        } else {
            useTimePMBtn.setSelected(true);
        }

        // Make selections from ComboBoxes
        for (Chemical c : useChemCombo.getItems()) {
            if (c.getId() == chemUse.getChemID()) {
                useChemCombo.setValue(c);
            }
        }

        for (Method m : useMethodCombo.getItems()) {
            if (m.getId() == chemUse.getMethodID()) {
                useMethodCombo.setValue(m);
            }
        }

        // Assign value to chemUseID
        chemUseID = chemUse.getId();

        // Make UI changes
        useAmountLabel.setText("Amount (" + chemUse.getUnit() + ")*");
        saveUseBtn.setText("Update");
    }

    /**
     * This event handler adds or updates a chemical use entry.
     * When the Add/Update button is clicked, this method first validates data in the form. If all
     * data is valid, this method then adds or updates the use entry and closes the modal.
     * @param actionEvent The click event
     */
    public void saveUse(ActionEvent actionEvent) {
        try {
            // Variable declaration
            boolean toggleSelected = false;

            // Perform data validations
            boolean validDate = Utility.validateTextInput(
                    useDatePicker.getEditor(),
                    useDateInputInfo,
                    useDateTimeError
            );
            boolean validTime = Utility.validateTextInput(
                    useTimeText,
                    useTimeInputInfo,
                    useDateTimeError
            );
            boolean validChem = Utility.validateComboBoxInput(
                    useChemCombo,
                    useChemError
            );
            boolean validAmount = Utility.validateTextInput(
                    useAmountText,
                    useAmountInputInfo,
                    useAmountError
            );
            boolean validMethod = Utility.validateComboBoxInput(
                    useMethodCombo,
                    useMethodError
            );
            boolean validDilution = Utility.validateTextInput(
                    useDilutionText,
                    useDilutionInputInfo,
                    useDilutionError
            );
            boolean validArea = Utility.validateTextInput(
                    useAreaText,
                    useAreaInputInfo,
                    useAreaError
            );
            boolean validAreaDesc = Utility.validateTextInput(
                    useAreaDescText,
                    useAreaDescInputInfo,
                    useAreaDescError
            );

            // Ensure that AM or PM has been selected
            if (timeToggle.getSelectedToggle() == null) {
                StringBuilder errorSB = new StringBuilder(useDateTimeError.getText());
                Utility.appendToStringBuilder(
                        "Must select AM or PM",
                        errorSB
                );
                useDateTimeError.setText(errorSB.toString());
            } else {
                toggleSelected = true;
            }

            // If there are invalid fields, display an error alert
            if (!validDate ||
                    !validTime ||
                    !toggleSelected ||
                    !validChem ||
                    !validAmount ||
                    !validMethod ||
                    !validDilution ||
                    !validArea ||
                    !validAreaDesc) {
                Utility.showErrorAlert(
                        "Invalid fields must be corrected before this form can be submitted."
                );

            // Otherwise, proceed with adding/updating use entry
            } else {
                // Create useDateTime from user-entered data
                LocalDateTime useDateTime = Utility.convertTo24Hr(
                        useTimeText.getText(),
                        useTimeAMBtn.isSelected(),
                        useDatePicker.getValue()
                );

                // Create a ChemUse object
                ChemUse chemUse = new ChemUse(
                        chemUseID,
                        useDateTime,
                        useChemCombo.getSelectionModel().getSelectedItem().getId(),
                        useChemCombo.getSelectionModel().getSelectedItem().getName(),
                        Double.parseDouble(useAmountText.getText()),
                        -1,
                        null,
                        useMethodCombo.getSelectionModel().getSelectedItem().getId(),
                        useMethodCombo.getSelectionModel().getSelectedItem().getName(),
                        Double.parseDouble(useDilutionText.getText()),
                        Double.parseDouble(useAreaText.getText()),
                        useAreaDescText.getText()
                );

                // If updating an existing use entry, perform update
                if (chemUseID != -1) {
                    DBChemUse.updateChemUse(chemUse);

                // Otherwise, insert new use entry into DB
                } else {
                    DBChemUse.createChemUse(chemUse);
                }

                // Fire event to refresh data in the parent window and close modal
                Utility.fireParentWindowShownEvent(actionEvent);
                Utility.closeModal(actionEvent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This event handler closes the modal when the Cancel button is clicked.
     * When the Cancel button is clicked, this method first confirms that a user wants to cancel
     * adding or updating a use entry. Upon confirmation, this method closes the modal.
     * @param actionEvent The click event
     * @throws IOException If an input/output exception occurs
     */
    public void cancelSaveUse(ActionEvent actionEvent) throws IOException {
        // Display alert to confirm that user wants to cancel
        boolean confirmCancel = Utility.showConfAlert(
                "Are you sure that you want to cancel? All unsaved changes will be lost."
        );

        // Upon confirmation, close modal
        if (confirmCancel) {
            Utility.closeModal(actionEvent);
        }
    }
}
