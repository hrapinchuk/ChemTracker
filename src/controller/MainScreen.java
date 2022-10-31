package controller;

import dao.DBChemUse;
import dao.DBChemical;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import model.ChemUse;
import model.Chemical;
import utility.Utility;

import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

/**
 * This controller class applies to the MainScreen.
 * @author Heather Rapinchuk
 */
public class MainScreen implements Initializable {
    // Variable declarations
    @FXML
    private TextFlow chemStockTextFlow;
    @FXML
    private TextFlow recentUseTextFlow;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yy hh:mm a");

    // Methods
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Get lists of low stock chemicals and recent uses
        ObservableList<Chemical> lowStockChems = DBChemical.getLowStockChemicals();
        ObservableList<ChemUse> recentUses = DBChemUse.getRecentChemUses();

        // Display low stock chemical info in TextFlow
        for (Chemical c : lowStockChems) {
            StringBuilder sb = new StringBuilder();
            int numSpaces;
            if (c.getName().length() > 33) {
                sb.append(c.getName(), 0, 33);
                numSpaces = 10 - c.getCurrentStock().length();
            } else {
                sb.append(c.getName());
                numSpaces = 43 - c.getName().length() - c.getCurrentStock().length();
            }
            sb.append(" ".repeat(Math.max(0, numSpaces))).append(c.getCurrentStock());
            chemStockTextFlow.getChildren().add(new Text(sb + "\n"));
        }

        // Display recent chemical use info in TextFlow
        for (ChemUse c : recentUses) {
            String formattedLDT = c.getUseDateTime().format(formatter);
            Text textLine = new Text (formattedLDT + ", " + c.getChemical() + " applied\n");
            recentUseTextFlow.getChildren().add(textLine);
        }
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
     * When either the Reports menu button or Run Reports button is clicked, this method shows the
     * ReportScreen.
     * @param actionEvent The click event
     * @throws IOException If an input/output exception occurs
     */
    public void showReportScreen(ActionEvent actionEvent) throws IOException {
        Utility.showScreen(actionEvent, "ReportScreen.fxml");
    }

    /**
     * When the Add Chemical button is clicked, this method shows the ChemicalDetailScreen.
     * @param actionEvent The click event
     * @throws IOException If an input/output exception occurs
     */
    public void showChemDetailScreen(ActionEvent actionEvent) throws IOException {
        Utility.showScreen(actionEvent, "ChemicalDetailScreen.fxml");
    }

    /**
     * When the Add Use button is clicked, this method opens the ChemUseDetailScreen modal.
     * @param actionEvent The click event
     * @throws IOException If an input/output exception occurs
     */
    public void showUseDetailScreen(ActionEvent actionEvent) throws IOException {
        Utility.showModal(actionEvent, "ChemUseDetailScreen.fxml", "Use Details");
    }
}
