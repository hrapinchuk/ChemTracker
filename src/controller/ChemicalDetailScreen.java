package controller;

import dao.DBChemStock;
import dao.DBChemUse;
import dao.DBChemical;
import dao.DBUnit;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.ChemStock;
import model.ChemUse;
import model.Chemical;
import model.Unit;
import utility.Utility;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

/**
 * This controller class applies to the ChemicalDetailScreen.
 * @author Heather Rapinchuk
 */
public class ChemicalDetailScreen implements Initializable {
    // Variable declarations
    @FXML
    private TextField chemNameText;
    @FXML
    private TextField chemMfrText;
    @FXML
    private TextField chemRegText;
    @FXML
    private TextField chemReentryText;
    @FXML
    private ComboBox<Unit> chemUnitCombo;
    @FXML
    private TextArea chemDescText;
    @FXML
    private Button saveChemicalBtn;
    @FXML
    private Label chemNameError;
    @FXML
    private Label chemMfrError;
    @FXML
    private Label chemRegError;
    @FXML
    private Label chemReentryError;
    @FXML
    private Label chemUnitError;
    @FXML
    private Label chemDescError;
    @FXML
    private Label displayMsg;
    @FXML
    private Label chemStockLabel;
    @FXML
    private TableView<ChemStock> stockTable;
    @FXML
    private TableColumn<Chemical, Double> stockAmountCol;
    @FXML
    private TableColumn<Chemical, LocalDate> stockAddDateCol;
    @FXML
    private TableColumn stockEditCol;
    @FXML
    private TableColumn stockDeleteCol;
    @FXML
    private Button addStockBtn;
    private int chemID = -1;
    private ObservableList<ChemStock> chemStock = FXCollections.observableArrayList();

    // Methods
    /**
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Variable declarations
        ObservableList<Unit> allUnits = DBUnit.getAllUnits();

        // Populate ComboBox with a list of all units
        chemUnitCombo.setItems(allUnits);
        chemUnitCombo.setVisibleRowCount(5);
        chemUnitCombo.setPromptText("Select Unit");

        // Add change listeners to enforce the maxLength of text inputs
        Utility.enforceMaxLength(chemNameText, 50, chemNameError);
        Utility.enforceMaxLength(chemMfrText, 50, chemMfrError);
        Utility.enforceMaxLength(chemRegText, 50, chemRegError);
        Utility.enforceMaxLength(chemReentryText, 50, chemReentryError);
        Utility.enforceMaxLength(chemDescText, 500, chemDescError);

        // Disable Add to Stock button
        addStockBtn.setDisable(true);
    }

    /**
     * This method receives data and populates the form if a chemical is being updated.
     * @param chemical The Chemical object to be updated
     */
    public void showChemicalData(Chemical chemical) {
        // Populate text inputs with appropriate chemical data
        chemNameText.setText(chemical.getName());
        chemMfrText.setText(chemical.getManufacturer());
        chemRegText.setText(chemical.getRegNum());
        chemReentryText.setText(chemical.getReentry());
        chemDescText.setText(chemical.getDescription());
        chemStockLabel.setText(chemical.getCurrentStock());

        // Select appropriate unit in ComboBox
        for (Unit u : chemUnitCombo.getItems()) {
            if (u.getId() == chemical.getUnitID()) {
                chemUnitCombo.setValue(u);
            }
        }

        // Assign value to chemID
        chemID = chemical.getId();

        // Make UI changes
        saveChemicalBtn.setText("Update Chemical");
        addStockBtn.setDisable(false);

        // Populate the stockTable with all stock of this chemical
        refreshStockTable();
        stockAmountCol.setCellValueFactory(new PropertyValueFactory<>("amount"));
        stockAddDateCol.setCellValueFactory(new PropertyValueFactory<>("addDate"));

        // Create Edit link for each stock entry
        stockEditCol.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(Object o, boolean b) {
                super.updateItem(o, b);

                // Create and style Edit HyperLink
                Hyperlink editLink = new Hyperlink("Edit");
                editLink.getStyleClass().add("tealText");

                // Create event handler to open the ChemStockDetailScreen when Edit is clicked
                editLink.setOnAction(event -> {
                    try {
                        // Get stock entry from selected row
                        ChemStock rowStock = (ChemStock)getTableView().getItems().get(getIndex());

                        // Load view of the ChemStockDetailScreen
                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("/view/ChemStockDetailScreen.fxml"));
                        loader.load();

                        // Pass selected stock object to controller
                        ChemStockDetailScreen controller = loader.getController();
                        controller.showChemStockData(rowStock);

                        // Show modal
                        Utility.showLoaderModal(event, loader, "Stock Details");

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

                // Display Edit link
                setGraphic(b ? null : editLink);
            }
        });

        // Create Delete link for each stock entry
        stockDeleteCol.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(Object o, boolean b) {
                super.updateItem(o, b);

                // Create and style Delete HyperLink
                Hyperlink deleteLink = new Hyperlink("Delete");
                deleteLink.getStyleClass().add("maroonText");

                // Create event handler to confirm delete when Delete is clicked
                deleteLink.setOnAction(event -> {
                    // Get stock object from selected row
                    ChemStock rowStock = (ChemStock)getTableView().getItems().get(getIndex());

                    // Confirm whether user wants to proceed with deletion
                    boolean confirmDelete = Utility.showConfAlert(
                            "Are you sure that you want to delete this stock entry?"
                    );

                    // Upon user confirmation, delete stock entry, refresh table, and display message
                    if (confirmDelete) {
                        DBChemStock.deleteChemStock(rowStock.getId());
                        refreshStockTable();
                        //displayMsg.setText(rowChemical.getName() + " deleted");
                    }
                });

                // Display Delete link
                setGraphic(b ? null : deleteLink);
            }
        });
    }

    /**
     * When the ChemTracker button is clicked, this method shows the MainScreen.
     * @param mouseEvent The click event
     * @throws IOException If an input/output exception occurs
     */
    public void showMainScreen(MouseEvent mouseEvent) throws IOException {
        Utility.showScreen(mouseEvent, "MainScreen.fxml");
    }

    /**
     * When the Chemicals menu button is clicked, this method shows the ChemicalScreen.
     * @param actionEvent The click event
     * @throws IOException If an input/output exception occurs
     */
    public void showChemicalScreen(ActionEvent actionEvent) throws IOException {
        Utility.showScreen(actionEvent, "ChemicalScreen.fxml");
    }

    /**
     * When the Uses menu button is clicked, this method shows the ChemUseScreen.
     * @param actionEvent The click event
     * @throws IOException If an input/output exception occurs
     */
    public void showChemUseScreen(ActionEvent actionEvent) throws IOException {
        Utility.showScreen(actionEvent, "ChemUseScreen.fxml");
    }

    /**
     * When the Reports menu button is clicked, this method shows the ReportScreen.
     * @param actionEvent The click event
     * @throws IOException If an input/output exception occurs
     */
    public void showReportScreen(ActionEvent actionEvent) throws IOException {
        Utility.showScreen(actionEvent, "ReportScreen.fxml");
    }

    /**
     * When the Add to Stock button is clicked, this method opens a ChemStockDetailScreen modal.
     * @param actionEvent The click event
     * @throws IOException If an input/output exception occurs
     */
    public void showChemStockDetailScreen(ActionEvent actionEvent) throws IOException {
        Utility.showModal(actionEvent, "ChemStockDetailScreen.fxml", "Stock Details");
    }

    /**
     * This event handler adds or updates a chemical.
     * When the Add/Update Chemical button is clicked, this method first validates data in the form.
     * If all data is valid, the method either adds a new chemical or updates an existing chemical.
     * @param actionEvent The click event
     */
    public void saveChemical(ActionEvent actionEvent) {
        try {
            // Perform data validations
            // Validate name
            boolean validName = Utility.validateTextInput(
                    chemNameText.getText(),
                    true,
                    50,
                    null,
                    chemNameError
            );

            // Validate manufacturer
            boolean validMfr = Utility.validateTextInput(
                    chemMfrText.getText(),
                    true,
                    50,
                    null,
                    chemMfrError
            );

            // Validate registration number
            boolean validReg = Utility.validateTextInput(
                    chemRegText.getText(),
                    true,
                    50,
                    null,
                    chemRegError
            );

            // Validate reentry period
            boolean validReentry = Utility.validateTextInput(
                    chemReentryText.getText(),
                    true,
                    50,
                    null,
                    chemReentryError
            );

            // Validate unit
            boolean validUnit = Utility.validateComboBoxInput(chemUnitCombo, chemUnitError);

            // Validate description
            boolean validDesc = Utility.validateTextInput(
                    chemDescText.getText(),
                    false,
                    500,
                    null,
                    chemDescError
            );

            // If there are invalid fields, display an error alert
            if (!validName || !validMfr || !validReg || !validReentry || !validUnit || !validDesc) {
                Utility.showErrorAlert(
                        "Invalid fields must be corrected before this form can be submitted."
                );

            // Otherwise, proceed with adding/updating chemical
            } else {
                // Create Chemical object
                Chemical chemical = new Chemical(
                        chemID,
                        chemNameText.getText(),
                        chemMfrText.getText(),
                        chemRegText.getText(),
                        chemReentryText.getText(),
                        chemUnitCombo.getSelectionModel().getSelectedItem().getId(),
                        chemUnitCombo.getSelectionModel().getSelectedItem().getName(),
                        chemDescText.getText(),
                        null
                );

                // If updating an existing Chemical, perform update
                if (chemID != -1) {
                    DBChemical.updateChemical(chemical);

                    // Display message confirming that Chemical was updated
                    Utility.showAndFadeMessage(displayMsg, "Updated");

                // Otherwise, insert new Chemical into DB
                } else {
                    // Assign value to chemID
                    chemID = DBChemical.createChemical(chemical);

                    // If an error occurred, display an alert and redirect user to ChemicalScreen
                    if (chemID == -1) {
                        Utility.showScreen(actionEvent, "ChemicalScreen.fxml");
                        Utility.showErrorAlert(
                                "An error occurred. Please try adding a Chemical again."
                        );

                    // Otherwise, perform UI changes and confirm that Chemical was added
                    } else {
                        Utility.showAndFadeMessage(displayMsg, "Added");
                        saveChemicalBtn.setText("Update Chemical");
                        addStockBtn.setDisable(false);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This event handler redirects the user back to the ChemicalScreen.
     * When the Cancel button is clicked, this event confirms that the user wants to cancel adding
     * or updating a chemical. Upon confirmation, this method redirects a user to the ChemicalScreen.
     * @param actionEvent The click event
     * @throws IOException If an input/output exception occurs
     */
    public void cancelSaveChemical(ActionEvent actionEvent) throws IOException {
        // Display alert to confirm that user wants to cancel
        boolean confirmCancel = Utility.showConfAlert(
                "Are you sure that you want to Cancel? All unsaved changes will be lost."
        );

        // Upon confirmation, redirect user to the ChemicalScreen
        if (confirmCancel) {
            Utility.showScreen(actionEvent, "ChemicalScreen.fxml");
        }
    }

    /**
     * This method refreshes the stockTable with a list of all stock for this chemical.
     */
    public void refreshStockTable() {
        chemStock = DBChemStock.getChemStockByChemID(chemID);
        stockTable.setItems(chemStock);
    }
}
