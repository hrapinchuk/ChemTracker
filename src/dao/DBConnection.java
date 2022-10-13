package dao;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * This class provides access to the database.
 * @author Heather Rapinchuk
 */
public class DBConnection {
    // Variable declarations
    private static Dotenv dotenv = Dotenv.load();
    private static final String DB_NAME = dotenv.get("DB_NAME");
    private static final String DB_URL = dotenv.get("DB_URL") + DB_NAME + "?connectionTimeZone=UTC";
    private static final String DB_DRIVER = dotenv.get("DB_DRIVER");
    private static final String USER_NAME = dotenv.get("DB_USER");
    private static final String PASSWORD = dotenv.get("DB_PASSWORD");
    static Connection connection;

    // Methods
    /**
     * This method opens a database connection.
     * @return A Connection object representing the database connection
     */
    public static Connection openConnection() {
        // Try to establish a database connection
        try {
            Class.forName(DB_DRIVER);
            connection = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
            System.out.println("DB connection opened");

        // If an exception is caught, print stack trace
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        // Return the connection object
        return connection;
    }

    /**
     * This  method gets a database connection to use in a prepared statement.
     * @return A Connection object representing the database connection
     */
    public static Connection getConnection() {
        return connection;
    }

    /**
     * This method closes a database connection.
     */
    public static void closeConnection() {
        // Try closing the database connection
        try {
            connection.close();
            System.out.println("DB connection closed");

        // If an exception is caught, print a message to the console
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
