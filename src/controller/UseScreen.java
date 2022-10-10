package controller;

import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import utility.Utility;

import java.io.IOException;

/**
 * This controller class applies to the UseScreen.
 * @author Heather Rapinchuk
 */
public class UseScreen {
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
     * When the Uses menu button is clicked, this method shows the UseScreen.
     * @param actionEvent The click event
     * @throws IOException If an input/output exception occurs
     */
    public void showUseScreen(ActionEvent actionEvent) throws IOException {
        Utility.showScreen(actionEvent, "UseScreen.fxml");
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
     * When the New Use button is clicked, this method shows the UseDetailScreen.
     * @param actionEvent The click event
     * @throws IOException If an input/output exception occurs
     */
    public void showUseDetailScreen(ActionEvent actionEvent) throws IOException {
        Utility.showModal(actionEvent, "UseDetailScreen.fxml", "Use Details");
    }
}