package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.ChemStock;

import java.sql.Date;
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
     * This method inserts a new entry into the chem_stock table.
     * @param chemStock A ChemStock object containing the data to insert into the chem_stock table
     */
    public static void createChemStock(ChemStock chemStock) {
        try {
            // Create and execute query to insert a new entry into the chem_stock table
            String sql = "INSERT INTO chem_stock (chem_id, amount, add_date) VALUES (?, ?, ?)";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setInt(1, chemStock.getChemID());
            ps.setDouble(2, chemStock.getAmount());
            ps.setDate(3, Date.valueOf(chemStock.getAddDate()));
            ps.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method updates an existing entry in the chem_stock table.
     * @param chemStock A ChemStock object containing the data to update the selected entry
     */
    public static void updateChemStock(ChemStock chemStock) {
        try {
            // Create and execute query to update an existing stock entry
            String sql = "UPDATE chem_stock SET chem_id = ?, amount = ?, add_date = ? WHERE id = ?";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setInt(1, chemStock.getChemID());
            ps.setDouble(2, chemStock.getAmount());
            ps.setDate(3, Date.valueOf(chemStock.getAddDate()));
            ps.setInt(4, chemStock.getId());
            ps.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
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
