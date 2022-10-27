package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Method;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class provides access to method data in the database.
 * @author Heather Rapinchuk
 */
public abstract class DBMethod {
    /**
     * This method gets a list of all methods from the method table.
     * @return An ObservableList containing all methods in the method table
     */
    public static ObservableList<Method> getAllMethods() {
        // Variable declaration
        ObservableList<Method> allMethods = FXCollections.observableArrayList();

        try {
            // Create and execute a query to select all methods from the method table
            String sql = "SELECT * FROM method";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            // Loop through results
            while (rs.next()) {
                // Create a Method object from the current row
                int id = rs.getInt("id");
                String name = rs.getString("name");
                Method thisMethod = new Method(id, name);

                // Add Method to list of allMethods
                allMethods.add(thisMethod);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Return list of allMethods
        return allMethods;
    }
}
