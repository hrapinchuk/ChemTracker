package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.ChemUse;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

/**
 * This class provides access to chem_use data in the database.
 * @author Heather Rapinchuk
 */
public abstract class DBChemUse {
    /**
     * This method gets a list of all uses of a certain chemical.
     * @param chemID An int representing the ID of the chemical whose uses are being fetched
     * @return An ObservableList of uses in the chem_use table that contain the specified chemical
     * ID
     */
    public static ObservableList<ChemUse> getUsesByChemID(int chemID) {
        // Variable declaration
        ObservableList<ChemUse> chemUses = FXCollections.observableArrayList();

        try {
            // Create and execute a query to select all uses with the specified chemID
            String sql = "SELECT cu.*, c.name AS chemical, c.unit_id, u.name AS unit, m.name as method " +
                    "FROM chem_use cu " +
                    "LEFT JOIN chemical c ON cu.chem_id = c.id " +
                    "LEFT JOIN unit u ON c.unit_id = u.id " +
                    "LEFT JOIN method m ON cu.method_id = m.id " +
                    "WHERE chem_id = ?";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setInt(1, chemID);
            ResultSet rs = ps.executeQuery();

            // Loop through results
            while (rs.next()) {
                // Create a ChemUse object from the current row
                int id = rs.getInt("id");
                LocalDateTime useDateTime = rs.getTimestamp("use_datetime").toLocalDateTime();
                String chemical = rs.getString("chemical");
                double amount = rs.getDouble("amount");
                int unitID = rs.getInt("unit_id");
                String unit = rs.getString("unit");
                int methodID = rs.getInt("method_id");
                String method = rs.getString("method");
                double dilution = rs.getDouble("dilution");
                double area = rs.getDouble("area");
                String areaDesc = rs.getString("area_desc");
                ChemUse thisUse = new ChemUse(
                        id,
                        useDateTime,
                        chemID,
                        chemical,
                        amount,
                        unitID,
                        unit,
                        methodID,
                        method,
                        dilution,
                        area,
                        areaDesc
                );

                // Add ChemUse to list of chemUses
                chemUses.add(thisUse);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Return list of chemUses
        return chemUses;
    }
}
