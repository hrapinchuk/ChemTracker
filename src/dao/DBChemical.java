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
                    "((SELECT IFNULL(SUM(amount),0) FROM chem_stock cs WHERE cs.chem_id = c.id) - " +
                    "(SELECT IFNULL(SUM(amount),0) FROM chem_use cu WHERE cu.chem_id = c.id)) " +
                    "AS current_stock " +
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
     * This method gets a chemical from the chemical table whose ID matches the provided ID.
     * @param id An int representing the ID of the chemical to fetch
     * @return A Chemical object whose ID matches the provided ID
     */
    public static Chemical getChemical(int id) {
        try {
            // Create and execute a query to select a chemical from the chemical table
            String sql = "SELECT c.*, u.name AS unit, " +
                    "((SELECT IFNULL(SUM(amount),0) FROM chem_stock cs WHERE cs.chem_id = c.id) - " +
                    "(SELECT IFNULL(SUM(amount),0) FROM chem_use cu WHERE cu.chem_id = c.id)) " +
                    "AS current_stock " +
                    "FROM chemical c " +
                    "LEFT JOIN unit u ON c.unit_id = u.id " +
                    "WHERE c.id = ?";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            // Loop through results
            while (rs.next()) {
                // Create and return Chemical object with the matching ID
                String name = rs.getString("name");
                String manufacturer = rs.getString("manufacturer");
                String regNum = rs.getString("reg_num");
                String reentry = rs.getString("reentry");
                int unitID = rs.getInt("unit_id");
                String unit = rs.getString("unit");
                String description = rs.getString("description");
                String currentStock = rs.getDouble("current_stock") + " " + unit;
                return new Chemical(
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
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Return null
        return null;
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
                    "((SELECT IFNULL(SUM(amount),0) FROM chem_stock cs WHERE cs.chem_id = c.id) - " +
                    "(SELECT IFNULL(SUM(amount),0) FROM chem_use cu WHERE cu.chem_id = c.id)) " +
                    "AS current_stock " +
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
     * This method gets a list of chemicals from the chemical table whose current stock is less than
     * 5.0 units.
     * @return An ObservableList of low stock chemicals
     */
    public static ObservableList<Chemical> getLowStockChemicals() {
        // Variable declaration
        ObservableList<Chemical> chemicals = FXCollections.observableArrayList();

        try {
            // Create and execute a query to select low stock chemicals from the chemical table
            String sql = "SELECT c.*, u.name AS unit, " +
                    "((SELECT IFNULL(SUM(amount),0) FROM chem_stock cs WHERE cs.chem_id = c.id) - " +
                    "(SELECT IFNULL(SUM(amount),0) FROM chem_use cu WHERE cu.chem_id = c.id)) " +
                    "AS current_stock " +
                    "FROM chemical c " +
                    "LEFT JOIN unit u ON c.unit_id = u.id " +
                    "WHERE " +
                    "((SELECT IFNULL(SUM(amount),0) FROM chem_stock cs WHERE cs.chem_id = c.id) - " +
                    "(SELECT IFNULL(SUM(amount),0) FROM chem_use cu WHERE cu.chem_id = c.id)) <= 5.0";
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
                chemicals.add(thisChemical);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Return list of low stock chemicals
        return chemicals;
    }

    /**
     * This method inserts a new chemical into the chemical table.
     * @param chemical A Chemical object containing the data to insert into the chemical table
     * @return An int representing the autogenerated ID of the newly inserted chemical
     */
    public static int createChemical(Chemical chemical) {
        try {
            // Create and execute query to insert a new chemical into the chemical table
            String sql = "INSERT INTO chemical (name, manufacturer, reg_num, reentry, unit_id, description) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setString(1, chemical.getName());
            ps.setString(2, chemical.getManufacturer());
            ps.setString(3, chemical.getRegNum());
            ps.setString(4, chemical.getReentry());
            ps.setInt(5, chemical.getUnitID());
            ps.setString(6, chemical.getDescription());
            ps.execute();

            // Create and execute query to get the autogenerated ID of the inserted chemical
            String sql1 = "SELECT last_insert_id()";
            PreparedStatement ps1 = DBConnection.getConnection().prepareStatement(sql1);
            ResultSet rs = ps1.executeQuery();
            while (rs.next()) {
                // Return the autogenerated ID
                return rs.getInt("last_insert_id()");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // If the autogenerated ID was not returned, return -1
        return -1;
    }

    /**
     * This method updates an existing chemical in the chemical table.
     * @param chemical A Chemical object containing the data to update the selected chemical
     */
    public static void updateChemical(Chemical chemical) {
        try {
            // Create and execute query to update an existing chemical
            String sql = "UPDATE chemical SET name = ?, manufacturer = ?, reg_num = ?, reentry = ?, " +
                    "unit_id = ?, description = ? WHERE id = ?";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setString(1, chemical.getName());
            ps.setString(2, chemical.getManufacturer());
            ps.setString(3, chemical.getRegNum());
            ps.setString(4, chemical.getReentry());
            ps.setInt(5, chemical.getUnitID());
            ps.setString(6, chemical.getDescription());
            ps.setInt(7, chemical.getId());
            ps.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method deletes a chemical from the chemical table.
     * @param id An int representing the ID of the chemical to be deleted
     */
    public static void deleteChemical(int id) {
        try {
            // Create and execute a query to delete the chemical with the specified ID
            String sql = "DELETE FROM chemical WHERE id = ?";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setInt(1, id);
            ps.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
