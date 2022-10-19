package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Unit;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class provides access to unit data in the database.
 * @author Heather Rapinchuk
 */
public abstract class DBUnit {
    /**
     * This method gets a list of all units in the unit table.
     * @return An ObservableList of all units in the unit table
     */
    public static ObservableList<Unit> getAllUnits() {
        // Variable declaration
        ObservableList<Unit> allUnits = FXCollections.observableArrayList();

        try {
            // Create and execute a query to select all units from the unit table
            String sql = "SELECT * FROM unit";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            // Loop through results
            while (rs.next()) {
                // Create a Unit object from the current row
                int id = rs.getInt("id");
                String name = rs.getString("name");
                Unit thisUnit = new Unit(id, name);

                // Add Unit to list of allUnits
                allUnits.add(thisUnit);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Return list of allUnits
        return allUnits;
    }
}
