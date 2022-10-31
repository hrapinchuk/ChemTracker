package controller;

import dao.DBChemUse;
import dao.DBChemical;
import dao.DBChemStock;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.ChemUse;
import model.Chemical;
import model.ChemStock;
import utility.Utility;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * This controller class applies to the ChemicalScreen.
 * @author Heather Rapinchuk
 */
public class ChemicalScreen implements Initializable {
    // Variable declarations
    @FXML
    private TableView<Chemical> chemTable;
    @FXML
    private TableColumn<Chemical, String> chemNameCol;
    @FXML
    private TableColumn<Chemical, String> chemMfrCol;
    @FXML
    private TableColumn<Chemical, String> chemRegCol;
    @FXML
    private TableColumn<Chemical, String> chemReentryCol;
    @FXML
    private TableColumn<Chemical, String> chemStockCol;
    @FXML
    private TableColumn<Chemical, Object> chemEditCol;
    @FXML
    private TableColumn<Chemical, Object> chemDeleteCol;
    @FXML
    private TextField searchText;
    @FXML
    private Label displayMsg;

    // Methods
    /**
     * This method is called when the ChemicalScreen is initialized.
     * This method loads a list of all chemicals into the chemTable and creates Edit and Delete
     * links for each row.
     * @param url An object used to resolve the location of the root object
     * @param resourceBundle A bundle of objects that localize the root object
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Get all chemicals, and display them in chemTable
        refreshChemTable();
        chemNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        chemMfrCol.setCellValueFactory(new PropertyValueFactory<>("manufacturer"));
        chemRegCol.setCellValueFactory(new PropertyValueFactory<>("regNum"));
        chemReentryCol.setCellValueFactory(new PropertyValueFactory<>("reentry"));
        chemStockCol.setCellValueFactory(new PropertyValueFactory<>("currentStock"));

        // Create Edit link for each chemical
        chemEditCol.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(Object o, boolean b) {
                super.updateItem(o, b);

                // Create and style Edit HyperLink
                Hyperlink editLink = new Hyperlink("Edit");
                editLink.getStyleClass().add("tealText");

                // Create event handler to open the ChemicalDetailScreen when Edit is clicked
                editLink.setOnAction(event -> {
                    try {
                        // Get chemical from selected row
                        Chemical rowChemical = getTableView().getItems().get(getIndex());

                        // Load view of the ChemicalDetailScreen
                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("/view/ChemicalDetailScreen.fxml"));
                        loader.load();

                        // Pass selected chemical to controller
                        ChemicalDetailScreen controller = loader.getController();
                        controller.showChemicalData(rowChemical);

                        // Show screen
                        Utility.showLoaderScreen(event, loader);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

                // Display Edit link
                setGraphic(b ? null : editLink);
            }
        });

        // Create Delete link for each chemical
        chemDeleteCol.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(Object o, boolean b) {
                super.updateItem(o, b);

                // Create and style Delete HyperLink
                Hyperlink deleteLink = new Hyperlink("Delete");
                deleteLink.getStyleClass().add("maroonText");

                // Create event handler to confirm delete when Delete is clicked
                deleteLink.setOnAction(event -> {
                    // Get chemical from selected row
                    Chemical rowChemical = getTableView().getItems().get(getIndex());

                    // Get any stock and uses associated with this chemical
                    ObservableList<ChemStock> chemStock = DBChemStock.getChemStockByChemID(rowChemical.getId());
                    ObservableList<ChemUse> chemUses = DBChemUse.getChemUsesByChemID(rowChemical.getId());

                    // If chemical has associated stock or uses, display alert and prevent deletion
                    if (!chemStock.isEmpty() || !chemUses.isEmpty()) {
                        Utility.showInfoAlert(
                                "Stock and/or uses associated with this chemical must be " +
                                        "deleted before this chemical can be deleted."
                        );

                    // Otherwise, confirm whether the user wants to proceed with deletion
                    } else {
                        boolean confirmDelete = Utility.showConfAlert(
                                "Are you sure that you want to delete this chemical?"
                        );

                        // Upon user confirmation, delete chemical, refresh table, and display message
                        if (confirmDelete) {
                            DBChemical.deleteChemical(rowChemical.getId());
                            refreshChemTable();
                            displayMsg.setText(rowChemical.getName() + " deleted");
                        }
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
     * When the New Chemical button is clicked, this method shows the ChemicalDetailScreen.
     * @param actionEvent The click event
     * @throws IOException If an input/output exception occurs
     */
    public void showChemicalDetailScreen(ActionEvent actionEvent) throws IOException {
        Utility.showScreen(actionEvent, "ChemicalDetailScreen.fxml");
    }

    /**
     * This event handler allows a user to search for chemicals in the chemTable.
     * When a user hits the Enter/Return button in the search field, this method gets and displays a
     * list of chemicals that match the provided search criteria in the chemTable. This method then
     * displays a message to the user based on the search results.
     */
    public void searchChemicals() {
        // Variable declarations
        String searchString = searchText.getText();
        ObservableList<Chemical> matchingChemicals = DBChemical.getChemicalsBySearch(searchString);

        // Display results in chemTable
        chemTable.setItems(matchingChemicals);

        // Display message to the user based on search results
        if (matchingChemicals.isEmpty()) {
            displayMsg.setText("No chemicals matching \"" + searchString + "\" found");
        } else if (!searchString.isEmpty()) {
            displayMsg.setText("Chemicals matching \"" + searchString + "\"");
        } else {
            displayMsg.setText("");
        }
    }

    /**
     * This method refreshes chemTable with a list of all chemicals from the chemical table.
     */
    public void refreshChemTable() {
        ObservableList<Chemical> allChemicals = DBChemical.getAllChemicals();
        chemTable.setItems(allChemicals);
    }
}
