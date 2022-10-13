package controller;

import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import model.Chemical;
import utility.Utility;

import java.io.IOException;

/**
 * This controller class applies to the ChemicalDetailScreen.
 * @author Heather Rapinchuk
 */
public class ChemicalDetailScreen {
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
     * This method receives data and populates the form if a chemical is being updated.
     * @param chemical The Chemical object to be updated
     */
    public void showChemicalData(Chemical chemical) {
        System.out.println("Chem Details!");
        // FIXME...
    }
}
