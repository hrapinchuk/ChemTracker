package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.ChemStock;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 * This class provides access to chem_stock data in the database.
 * @author Heather Rapinchuk
 */
public abstract class DBChemStock {
    /**
     * This method gets a list of all stock pertaining to a certain chemical.
     * @param chemID An int representing the ID of the chemical whose stock is being fetched
     * @return An ObservableList of stock in the chem_stock table that pertain to the specified
     * chemical ID
     */
    public static ObservableList<ChemStock> getChemStockByChemID(int chemID) {
        // Variable declaration
        ObservableList<ChemStock> chemStock = FXCollections.observableArrayList();

        try {
            // Create and execute a query to select all stock with the specified chemID
            String sql = "SELECT * FROM chem_stock WHERE chem_id = ?";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setInt(1, chemID);
            ResultSet rs = ps.executeQuery();

            // Loop through results
            while (rs.next()) {
                // Create a ChemStock object from the current row
                int id = rs.getInt("id");
                double amount = rs.getDouble("amount");
                LocalDate addDate = rs.getTimestamp("add_date").toLocalDateTime().toLocalDate();
                ChemStock thisChemStock = new ChemStock(id, chemID, amount, addDate);

                // Add Stock to list of chemStock
                chemStock.add(thisChemStock);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Return list of chemStock
        return chemStock;
    }

    /**
     * This method deletes an entry from the chem_stock table.
     * @param id An int representing the ID of the chemical stock entry to be deleted
     */
    public static void deleteChemStock(int id) {
        try {
            // Create and execute a query to delete the stock entry with the specified ID
            String sql = "DELETE FROM chem_stock WHERE id = ?";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setInt(1, id);
            ps.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
