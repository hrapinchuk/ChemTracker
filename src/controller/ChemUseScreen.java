package controller;

import dao.DBChemUse;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.ChemUse;
import utility.Utility;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * This controller class applies to the ChemUseScreen.
 * @author Heather Rapinchuk
 */
public class ChemUseScreen implements Initializable {
    // Variable declarations
    @FXML
    private TableView<ChemUse> useTable;
    @FXML
    private TableColumn<ChemUse, LocalDateTime> useDateTimeCol;
    @FXML
    private TableColumn<ChemUse, String> useChemCol;
    @FXML
    private TableColumn<ChemUse, Double> useAmountCol;
    @FXML
    private TableColumn<ChemUse, String> useMethodCol;
    @FXML
    private TableColumn<ChemUse, Double> useDilutionCol;
    @FXML
    private TableColumn<ChemUse, Double> useAreaCol;
    @FXML
    private TableColumn<ChemUse, Object> useEditCol;
    @FXML
    private TableColumn<ChemUse, Object> useDeleteCol;
    @FXML
    private Label displayMsg;
    @FXML
    private TextField searchText;
    private ObservableList<ChemUse> allChemUses = FXCollections.observableArrayList();

    // Methods
    /**
     * This method is called when the ChemUseScreen is initialized.
     * This method displays a list of all uses in the useTable and creates Edit and Delete links for
     * each row in the useTable.
     * @param url An object used to resolve the location of the root object
     * @param resourceBundle A bundle of objects that localize the root object
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Get all chemical uses and display them in useTable
        refreshUseTable();
        useDateTimeCol.setCellValueFactory(new PropertyValueFactory<>("useDateTime"));
        useChemCol.setCellValueFactory(new PropertyValueFactory<>("chemical"));
        useAmountCol.setCellValueFactory(new PropertyValueFactory<>("amountDesc"));
        useMethodCol.setCellValueFactory(new PropertyValueFactory<>("method"));
        useDilutionCol.setCellValueFactory(new PropertyValueFactory<>("dilution"));
        useAreaCol.setCellValueFactory(new PropertyValueFactory<>("area"));

        // Create Edit link for each use
        useEditCol.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(Object o, boolean b) {
                super.updateItem(o, b);

                // Create and style Edit HyperLink
                Hyperlink editLink = new Hyperlink("Edit");
                editLink.getStyleClass().add("tealText");

                // Create event handler to open the ChemUseDetailScreen modal when Edit is clicked
                editLink.setOnAction(event -> {
                    try {
                        // Get use from selected row
                        ChemUse rowChemUse = getTableView().getItems().get(getIndex());

                        // Load view of the ChemUseDetailScreen
                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("/view/ChemUseDetailScreen.fxml"));
                        loader.load();

                        // Pass selected use object to controller
                        ChemUseDetailScreen controller = loader.getController();
                        controller.showChemUseData(rowChemUse);

                        // Show modal
                        URL styleURL = Objects.requireNonNull(Utility.class.getResource("/style/Style.css"));
                        Stage primaryStage = (Stage)((Node)event.getSource()).getScene().getWindow();
                        Stage stage = new Stage();
                        Parent root = loader.getRoot();
                        Scene scene = new Scene(root);
                        scene.getStylesheets().add(styleURL.toExternalForm());
                        stage.setTitle("Use Details");
                        stage.setScene(scene);
                        // Adapted from https://www.tabnine.com/code/java/methods/javafx.stage.Stage/initModality
                        stage.initModality(Modality.WINDOW_MODAL);
                        stage.initOwner(primaryStage);
                        stage.show();

                        // Create event handler to refresh use table when modal is closed
                        primaryStage.setOnShown(windowEvent -> refreshUseTable());

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

                // Display Edit link
                setGraphic(b ? null : editLink);
            }
        });

        // Create Delete link for each use
        useDeleteCol.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(Object o, boolean b) {
                super.updateItem(o, b);

                // Create and style Delete HyperLink
                Hyperlink deleteLink = new Hyperlink("Delete");
                deleteLink.getStyleClass().add("maroonText");

                // Create event handler to confirm delete when Delete is clicked
                deleteLink.setOnAction(event -> {
                    // Get use from selected row
                    ChemUse rowChemUse = getTableView().getItems().get(getIndex());

                    // Upon user confirmation, delete use entry, refresh table, and display message
                    boolean confirmDelete = Utility.showConfAlert(
                            "Are you sure that you want to delete this entry?"
                    );

                    if (confirmDelete) {
                        DBChemUse.deleteChemUse(rowChemUse.getId());
                        refreshUseTable();
                        displayMsg.setText(rowChemUse.getUseDateTime() + " use deleted");
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
     * When the New Use button is clicked, this method shows the ChemUseDetailScreen.
     * @param actionEvent The click event
     * @throws IOException If an input/output exception occurs
     */
    public void showChemUseDetailScreen(ActionEvent actionEvent) throws IOException {
        Utility.showModal(actionEvent, "ChemUseDetailScreen.fxml", "Use Details");
    }

    /**
     * This method refreshes the list of entries in the useTable.
     */
    public void refreshUseTable() {
        allChemUses = DBChemUse.getAllChemUses();
        useTable.setItems(allChemUses);
    }

    /**
     * This event handler allows a user to search for chemical uses in the useTable.
     * When a user hits the Enter/Return button in the search field, this method gets and displays a
     * list of chemical uses that match the provided search criteria in the useTable. This method
     * then displays a message to the user based on the search results.
     * @param actionEvent The button press
     */
    public void searchChemUses(ActionEvent actionEvent) {
        // Variable declarations
        String searchString = searchText.getText();
        ObservableList<ChemUse> matchingChemUses = DBChemUse.getChemUsesBySearch(searchString);

        // Display results in useTable
        useTable.setItems(matchingChemUses);

        // Display message to the user based on search results
        if (matchingChemUses.isEmpty()) {
            displayMsg.setText("No uses matching \"" + searchString + "\" found");
        } else if (!searchString.isEmpty()) {
            displayMsg.setText("Uses matching \"" + searchString + "\"");
        } else {
            displayMsg.setText("");
        }
    }
}
