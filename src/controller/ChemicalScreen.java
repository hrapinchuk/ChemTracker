package controller;

import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import utility.Utility;

import java.io.IOException;

/**
 * This controller class applies to the ChemicalScreen.
 * @author Heather Rapinchuk
 */
public class ChemicalScreen {
    /**
     * When the ChemTracker button is clicked, this method shows the Main Screen.
     * @param mouseEvent The click event
     * @throws IOException If an input/output exception occurs
     */
    public void showMainScreen(MouseEvent mouseEvent) throws IOException {
        Utility.showScreen(mouseEvent, "MainScreen.fxml");
    }

    /**
     * When the Chemicals menu button is clicked, this method shows the Chemical Screen.
     * @param actionEvent The click event
     * @throws IOException If an input/output exception occurs
     */
    public void showChemicalScreen(ActionEvent actionEvent) throws IOException {
        Utility.showScreen(actionEvent, "ChemicalScreen.fxml");
    }

    public void showUseScreen(ActionEvent actionEvent) {
    }

    public void showReportScreen(ActionEvent actionEvent) {
    }
}
