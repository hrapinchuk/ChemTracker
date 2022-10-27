package controller;

import dao.DBChemUse;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.ChemUse;
import model.Month;
import utility.Utility;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

/**
 * This controller class applies to the ReportScreen.
 * @author Heather Rapinchuk
 */
public class ReportScreen implements Initializable {
    // Variable declarations
    @FXML
    private ComboBox<Month> monthCombo;
    @FXML
    private ComboBox<Integer> yearCombo;
    @FXML
    private TableView<ChemUse> reportTable;
    @FXML
    private TableColumn<ChemUse, LocalDateTime> dateTimeCol;
    @FXML
    private TableColumn<ChemUse, String> chemCol;
    @FXML
    private TableColumn<ChemUse, String> methodCol;
    @FXML
    private TableColumn<ChemUse, String> amountCol;
    @FXML
    private TableColumn<ChemUse, Double> dilutionCol;
    @FXML
    private TableColumn<ChemUse, Double> areaCol;
    @FXML
    private Label displayMsg;
    private ObservableList<Month> months = FXCollections.observableArrayList();
    private ObservableList<Integer> years = FXCollections.observableArrayList();
    private ObservableList<ChemUse> chemUses = FXCollections.observableArrayList();

    // Methods
    /**
     * This method is called when the ReportScreen is initialized.
     * This method populates the month and year ComboBoxes and prepares the reportTable to receive
     * ChemUse data.
     * @param url An object used to resolve the location of the root object
     * @param resourceBundle A bundle of objects that localize the root object
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Get data to populate ComboBoxes
        months.addAll(
                new Month(1, "January"),
                new Month(2, "February"),
                new Month(3, "March"),
                new Month(4, "April"),
                new Month(5, "May"),
                new Month(6, "June"),
                new Month(7, "July"),
                new Month(8, "August"),
                new Month(9, "September"),
                new Month(10, "October"),
                new Month(11, "November"),
                new Month(12, "December")
        );
        years = DBChemUse.getChemUseYears();

        // Load months and years into ComboBoxes
        monthCombo.setItems(months);
        monthCombo.setVisibleRowCount(5);
        monthCombo.setPromptText("Select Month");

        yearCombo.setItems(years);
        yearCombo.setVisibleRowCount(5);
        yearCombo.setPromptText("Select Year");

        // Prep the reportTable
        dateTimeCol.setCellValueFactory(new PropertyValueFactory<>("useDateTime"));
        chemCol.setCellValueFactory(new PropertyValueFactory<>("chemical"));
        methodCol.setCellValueFactory(new PropertyValueFactory<>("method"));
        amountCol.setCellValueFactory(new PropertyValueFactory<>("amountDesc"));
        dilutionCol.setCellValueFactory(new PropertyValueFactory<>("dilution"));
        areaCol.setCellValueFactory(new PropertyValueFactory<>("area"));
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
     * This event handler generates and displays a report.
     * When the Run Report button is clicked, this method checks whether a month and year have been
     * selected. If so, this method gets all chemical uses from the DB that occur within the
     * specified time frame, and displays their data in the reportTable.
     * @param actionEvent The click event
     */
    public void runReport(ActionEvent actionEvent) {
        // Variable declarations
        Month selectedMonth = monthCombo.getSelectionModel().getSelectedItem();
        Integer selectedYear = yearCombo.getSelectionModel().getSelectedItem();

        // If month and year haven't been selected, show an error
        if (selectedMonth == null || selectedYear == null) {
            Utility.showErrorAlert("Please select a month and year before running report.");

        // Otherwise, run report
        } else {
            chemUses = DBChemUse.getChemUsesByDate(selectedMonth.getNumber(), selectedYear);
            reportTable.setItems(chemUses);

            // Display message to the user
            if (chemUses.isEmpty()) {
                displayMsg.setText("No uses reported in " + selectedMonth.getName() + " " + selectedYear);
            } else {
                displayMsg.setText(selectedMonth.getName() + " " + selectedYear + " uses");
            }
        }
    }
}
