package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.ChemUse;

import java.sql.*;
import java.time.LocalDateTime;

/**
 * This class provides access to chem_use data in the database.
 * @author Heather Rapinchuk
 */
public abstract class DBChemUse {
    /**
     * This method gets a list of all entries from the chem_use table.
     * @return An ObservableList of all entries in the chem_use table
     */
    public static ObservableList<ChemUse> getAllChemUses() {
        // Variable declaration
        ObservableList<ChemUse> allChemUses = FXCollections.observableArrayList();

        try {
            // Create and execute a query to select all entries in the chem_use table
            String sql = "SELECT cu.*, c.name AS chemical, u.id AS unit_id, u.name AS unit, m.name AS method " +
                    "FROM chem_use cu " +
                    "LEFT JOIN chemical c ON cu.chem_id = c.id " +
                    "LEFT JOIN unit u ON c.unit_id = u.id " +
                    "LEFT JOIN method m ON cu.method_id = m.id";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            // Loop through results
            while (rs.next()) {
                // Create a ChemUse object from the current row
                int id = rs.getInt("id");
                LocalDateTime useDateTime = rs.getTimestamp("use_datetime").toLocalDateTime();
                int chemID = rs.getInt("chem_id");
                String chemical = rs.getString("chemical");
                double amount = rs.getDouble("amount");
                int unitID = rs.getInt("unit_id");
                String unit = rs.getString("unit");
                int methodID = rs.getInt("method_id");
                String method = rs.getString("method");
                double dilution = rs.getDouble("dilution");
                double area = rs.getDouble("area");
                String areaDesc = rs.getString("area_desc");
                ChemUse thisChemUse = new ChemUse(
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

                // Add ChemUse to list of allChemUses
                allChemUses.add(thisChemUse);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Return list of allChemUses
        return allChemUses;
    }

    /**
     * This method gets a list of all uses of a certain chemical.
     * @param chemID An int representing the ID of the chemical whose uses are being fetched
     * @return An ObservableList of uses in the chem_use table that contain the specified chemical
     * ID
     */
    public static ObservableList<ChemUse> getChemUsesByChemID(int chemID) {
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

    /**
     * This method gets a list of chemical uses from the chem_use table that match the provided
     * search criteria.
     * @param searchString A String representing a user's search criteria
     * @return An ObservableList of chemical uses in the chem_use table that match the provided
     * search criteria
     */
    public static ObservableList<ChemUse> getChemUsesBySearch(String searchString) {
        // Variable declaration
        ObservableList<ChemUse> matchingChemUses = FXCollections.observableArrayList();

        try {
            // Create and execute a query to get all chemical uses that match a provided search
            String sql = "SELECT cu.*, c.name AS chemical, u.id AS unit_id, u.name AS unit, m.name AS method " +
                    "FROM chem_use cu " +
                    "LEFT JOIN chemical c ON cu.chem_id = c.id " +
                    "LEFT JOIN unit u ON c.unit_id = u.id " +
                    "LEFT JOIN method m ON cu.method_id = m.id " +
                    "WHERE cu.use_datetime LIKE ? OR c.name LIKE ? OR m.name LIKE ?";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setString(1, "%" + searchString + "%");
            ps.setString(2, "%" + searchString + "%");
            ps.setString(3, "%" + searchString + "%");
            ResultSet rs = ps.executeQuery();

            // Loop through results
            while (rs.next()) {
                // Create a ChemUse object from the current row
                int id = rs.getInt("id");
                LocalDateTime useDateTime = rs.getTimestamp("use_datetime").toLocalDateTime();
                int chemID = rs.getInt("chem_id");
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

                // Add ChemUse to list of matchingChemUses
                matchingChemUses.add(thisUse);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Return list of matchingChemUses
        return matchingChemUses;
    }

    /**
     * This method gets chemical uses from the chem_use table that occurred in a specified month and
     * year.
     * @param month An int representing the month in which fetched chemical uses occurred
     * @param year An int representing the year in which fetched chemical uses occurred
     * @return An ObservableList of chemical uses that occurred within the specified month and year
     */
    public static ObservableList<ChemUse> getChemUsesByDate(int month, int year) {
        // Variable declaration
        ObservableList<ChemUse> chemUses = FXCollections.observableArrayList();

        try {
            // Create and execute a query to select all uses from the specified month and year
            String sql = "SELECT cu.*, c.name AS chemical, c.unit_id, u.name AS unit, m.name as method " +
                    "FROM chem_use cu " +
                    "LEFT JOIN chemical c ON cu.chem_id = c.id " +
                    "LEFT JOIN unit u ON c.unit_id = u.id " +
                    "LEFT JOIN method m ON cu.method_id = m.id " +
                    "WHERE MONTH(use_datetime) = ? AND YEAR(use_datetime) = ?";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setInt(1, month);
            ps.setInt(2, year);
            ResultSet rs = ps.executeQuery();

            // Loop through results
            while (rs.next()) {
                // Create a ChemUse object from the current row
                int id = rs.getInt("id");
                LocalDateTime useDateTime = rs.getTimestamp("use_datetime").toLocalDateTime();
                int chemID = rs.getInt("chem_id");
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

    /**
     * This method gets a list of all distinct years in which there were chemical uses.
     * @return An ObservableList of distinct years in which chemical uses occurred
     */
    public static ObservableList<Integer> getChemUseYears() {
        // Variable declaration
        ObservableList<Integer> distinctYears = FXCollections.observableArrayList();

        try {
            // Create and execute a query to select all distinct years from chem_use table
            String sql = "SELECT DISTINCT(YEAR(use_datetime)) AS year FROM chem_use";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            // Loop through results
            while (rs.next()) {
                // Add year from current row.
                int year = rs.getInt("year");
                distinctYears.add(year);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Return list of distinctYears
        return distinctYears;
    }

    /**
     * This method inserts a new entry into the chem_use table.
     * @param chemUse A ChemUse object containing the data to insert into the chem_use table
     */
    public static void createChemUse(ChemUse chemUse) {
        try {
            // Create and execute query to insert a new entry into the chem_use table
            String sql = "INSERT INTO chem_use " +
                    "(use_datetime, chem_id, amount, method_id, dilution, area, area_desc) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setTimestamp(1, Timestamp.valueOf(chemUse.getUseDateTime()));
            ps.setInt(2, chemUse.getChemID());
            ps.setDouble(3, chemUse.getAmount());
            ps.setInt(4, chemUse.getMethodID());
            ps.setDouble(5, chemUse.getDilution());
            ps.setDouble(6, chemUse.getArea());
            ps.setString(7, chemUse.getAreaDesc());
            ps.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method updates an existing entry in the chem_use table.
     * @param chemUse A ChemUse object containing the data to update the selected entry
     */
    public static void updateChemUse(ChemUse chemUse) {
        try {
            // Create and execute query to update an existing use entry
            String sql = "UPDATE chem_use " +
                    "SET use_datetime = ?, chem_id = ?, amount = ?, method_id = ?, dilution = ?, " +
                    "area = ?, area_desc = ? " +
                    "WHERE id = ?";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setTimestamp(1, Timestamp.valueOf(chemUse.getUseDateTime()));
            ps.setInt(2, chemUse.getChemID());
            ps.setDouble(3, chemUse.getAmount());
            ps.setInt(4, chemUse.getMethodID());
            ps.setDouble(5, chemUse.getDilution());
            ps.setDouble(6, chemUse.getArea());
            ps.setString(7, chemUse.getAreaDesc());
            ps.setInt(8, chemUse.getId());
            ps.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method deletes a chemical use entry from the chem_use table.
     * @param id An int representing the ID of the chemical use entry to be deleted
     */
    public static void deleteChemUse(int id) {
        try {
            // Create and execute a query to delete the chemical use with the specified ID
            String sql = "DELETE FROM chem_use WHERE id = ?";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setInt(1, id);
            ps.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
