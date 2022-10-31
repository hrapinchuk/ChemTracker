package controller;

import dao.DBChemStock;
import dao.DBChemical;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.ChemStock;
import model.TextInputInfo;
import utility.Utility;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * This controller class applies to the ChemStockDetailScreen.
 * @author Heather Rapinchuk
 */
public class ChemStockDetailScreen implements Initializable {
    // Variable declarations
    @FXML
    private Label stockAmountLabel;
    @FXML
    private TextField stockAmountText;
    @FXML
    private DatePicker stockAddDatePicker;
    @FXML
    private Label stockAmountError;
    @FXML
    private Label stockAddDateError;
    @FXML
    private Button saveStockBtn;
    private int chemID = -1;
    private int chemStockID = -1;
    private final TextInputInfo stockAmountInputInfo = new TextInputInfo(
            true,
            10,
            "(^\\d*\\.?\\d*[1-9]+\\d*$)|(^[1-9]+\\d*\\.\\d*$)",
            "Must contain only digits and a decimal"
    );
    private final TextInputInfo stockAddDateInputInfo = new TextInputInfo(
            true,
            10,
            "^[0,1]?\\d{1}\\/(([0-2]?\\d{1})|([3][0,1]{1}))\\/(([1]{1}[9]{1}[9]{1}\\d{1})|([2-9]{1}\\d{3}))$",
            "Must follow the format of MM/DD/YYYY"
    );

    // Methods
    /**
     * This method is called when the ChemStockDetailScreen is initialized.
     * This method adds change listeners to form inputs, which perform data validations as the user
     * types.
     * @param url An object used to resolve the location of the root object
     * @param resourceBundle A bundle of objects that localize the root object
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Add change listeners to validate text input values on change
        Utility.validateTextInputOnChange(
                stockAmountText,
                stockAmountInputInfo,
                stockAmountError
        );
        Utility.validateTextInputOnChange(
                stockAddDatePicker.getEditor(),
                stockAddDateInputInfo,
                stockAddDateError
        );
    }

    /**
     * This method receives data and populates the form as needed.
     * This method receives data that is passed from the ChemicalDetailScreen. Using received data,
     * this method performs UI changes, assigns values to variables, and populates form inputs
     * as needed.
     * @param chemStock The ChemStock object to be updated
     * @param chemID An int representing the ID of the chemical whose stock is being updated
     */
    public void showChemStockData(ChemStock chemStock, int chemID) {
        // Get and show the unit used to measure the chemical whose stock is being updated
        String unit = Objects.requireNonNull(DBChemical.getChemical(chemID)).getUnit();
        stockAmountLabel.setText("Amount (" + unit + ")*");

        // Assign value to chemID
        this.chemID = chemID;

        // If existing chemical stock is being updated, populate inputs, update chemStockID, and
        // make UI change
        if (chemStock != null) {
            // Populate text inputs with appropriate data
            stockAmountText.setText(Double.toString(chemStock.getAmount()));
            stockAddDatePicker.setValue(chemStock.getAddDate());

            // Assign value to chemStockID
            chemStockID = chemStock.getId();

            // Make UI change
            saveStockBtn.setText("Update");
        }
    }

    /**
     * This event handler adds or updates a chemical stock entry.
     * When the Add/Update button is clicked, this method first validates data in the form. If all
     * data is valid, this method then adds or updates the stock entry and closes the modal.
     * @param actionEvent The click event
     */
    public void saveStock(ActionEvent actionEvent) {
        try {
            // Perform data validations
            boolean validAmount = Utility.validateTextInput(
                    stockAmountText,
                    stockAmountInputInfo,
                    stockAmountError
            );
            boolean validDate = Utility.validateTextInput(
                    stockAddDatePicker.getEditor(),
                    stockAddDateInputInfo,
                    stockAddDateError
            );

            // If there are invalid fields, display an error alert
            if (!validAmount || !validDate) {
                Utility.showErrorAlert(
                        "Invalid fields must be corrected before this form can be submitted."
                );

            // Otherwise, proceed with adding/updating stock entry
            } else {
                // Create ChemStock object
                ChemStock chemStock = new ChemStock(
                        chemStockID,
                        chemID,
                        Double.parseDouble(stockAmountText.getText()),
                        stockAddDatePicker.getValue()
                );

                // If updating an existing stock entry, perform update
                if (chemStockID != -1) {
                    DBChemStock.updateChemStock(chemStock);

                // Otherwise, insert new stock entry into DB
                } else {
                    DBChemStock.createChemStock(chemStock);
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
     * adding or updating a stock entry. Upon confirmation, this method closes the modal.
     * @param actionEvent The click event
     * @throws IOException If an input/output exception occurs
     */
    public void cancelSaveStock(ActionEvent actionEvent) throws IOException {
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
