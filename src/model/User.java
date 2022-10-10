package model;

/**
 * This model class provides the ability to create and access User objects.
 * @author Heather Rapinchuk
 */
public class User {
    // Variable declarations
    private int id;
    private String username;
    private String password;

    // Constructor
    /**
     * This constructor creates a User object.
     * @param id An int representing a user's ID
     * @param username A String representing a user's username
     * @param password A String representing a user's password
     */
    public User(int id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    // Methods
    /**
     * This method gets a user's ID.
     * @return An int representing a user's ID
     */
    public int getId() {
        return id;
    }

    /**
     * This method sets a user's ID.
     * @param id An int representing a user's ID
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * This method gets a user's username.
     * @return A String representing a user's username
     */
    public String getUsername() {
        return username;
    }

    /**
     * This method sets a user's username.
     * @param username A String representing a user's username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * This method gets a user's password.
     * @return A String representing a user's password
     */
    public String getPassword() {
        return password;
    }

    /**
     * This method sets a user's password.
     * @param password A String representing a user's password
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
