package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Chemical;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class provides access to chemical data in the database.
 * @author Heather Rapinchuk
 */
public abstract class DBChemical {
    /**
     * This method gets a list of all chemicals from the chemical table.
     * @return An ObservableList of all chemicals in the chemical table
     */
    public static ObservableList<Chemical> getAllChemicals() {
        // Variable declaration
        ObservableList<Chemical> allChemicals = FXCollections.observableArrayList();

        try {
            // Create and execute a query to select all chemicals from the chemical table
            String sql = "SELECT c.*, u.name AS unit, " +
                    "((SELECT SUM(amount) FROM chem_stock cs WHERE cs.chem_id = c.id) - " +
                    "(SELECT SUM(amount) FROM chem_use cu WHERE cu.chem_id = c.id)) AS current_stock " +
                    "FROM chemical c " +
                    "LEFT JOIN unit u ON c.unit_id = u.id";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            // Loop through results
            while (rs.next()) {
                // Create a Chemical object from the current row
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String manufacturer = rs.getString("manufacturer");
                String regNum = rs.getString("reg_num");
                String reentry = rs.getString("reentry");
                int unitID = rs.getInt("unit_id");
                String unit = rs.getString("unit");
                String description = rs.getString("description");
                String currentStock = rs.getDouble("current_stock") + " " + unit;
                Chemical thisChemical = new Chemical(
                        id,
                        name,
                        manufacturer,
                        regNum,
                        reentry,
                        unitID,
                        unit,
                        description,
                        currentStock
                );

                // Add Chemical to list of allChemicals
                allChemicals.add(thisChemical);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Return list of allChemicals
        return allChemicals;
    }

    /**
     * This method gets a list of chemicals from the chemical table that match the provided search
     * criteria.
     * @param searchString A String representing a user's search criteria
     * @return An ObservableList of chemicals in the chemical table that match the provided search
     * criteria
     */
    public static ObservableList<Chemical> getChemicalsBySearch(String searchString) {
        // Variable declaration
        ObservableList<Chemical> matchingChemicals = FXCollections.observableArrayList();

        try {
            // Create and execute a query to get all chemicals that match a provided search
            String sql = "SELECT c.*, u.name AS unit, " +
                    "((SELECT SUM(amount) FROM chem_stock cs WHERE cs.chem_id = c.id) - " +
                    "(SELECT SUM(amount) FROM chem_use cu WHERE cu.chem_id = c.id)) AS current_stock " +
                    "FROM chemical c " +
                    "LEFT JOIN unit u ON c.unit_id = u.id " +
                    "WHERE c.name LIKE ? OR c.manufacturer LIKE ? OR c.reg_num LIKE ?";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setString(1, "%" + searchString + "%");
            ps.setString(2, "%" + searchString + "%");
            ps.setString(3, "%" + searchString + "%");
            ResultSet rs = ps.executeQuery();

            // Loop through results
            while (rs.next()) {
                // Create a Chemical object from the current row
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String manufacturer = rs.getString("manufacturer");
                String regNum = rs.getString("reg_num");
                String reentry = rs.getString("reentry");
                int unitID = rs.getInt("unit_id");
                String unit = rs.getString("unit");
                String description = rs.getString("description");
                String currentStock = rs.getDouble("current_stock") + " " + unit;
                Chemical thisChemical = new Chemical(
                        id,
                        name,
                        manufacturer,
                        regNum,
                        reentry,
                        unitID,
                        unit,
                        description,
                        currentStock
                );

                // Add Chemical to list of matchingChemicals
                matchingChemicals.add(thisChemical);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Return list of matchingChemicals
        return matchingChemicals;
    }

    /**
     * This method deletes a chemical from the chemical table.
     * @param id An int representing the ID of the chemical to be deleted
     */
    public static void deleteChemical(int id) {
        try {
            String sql = "DELETE FROM chemical WHERE id = ?";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setInt(1, id);
            ps.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
