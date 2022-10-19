package model;

/**
 * This model class provides the ability to create and access Unit objects.
 * @author Heather Rapinchuk
 */
public class Unit {
    // Variable declarations
    private int id;
    private String name;

    // Constructor
    /**
     * This constructor creates a Unit object.
     * @param id An int representing a unit's ID
     * @param name A String representing a unit's name
     */
    public Unit(int id, String name) {
        this.id = id;
        this.name = name;
    }

    // Methods
    /**
     * This method gets a unit's ID.
     * @return An int representing a unit's ID
     */
    public int getId() {
        return id;
    }

    /**
     * This method sets a unit's ID.
     * @param id An int representing a unit's ID
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * This method gets a unit's name.
     * @return A String representing a unit's name
     */
    public String getName() {
        return name;
    }

    /**
     * This method sets a unit's name.
     * @param name A String representing a unit's name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * This method formats a Unit object that is converted to a String.
     * @return A String that indicates the unit's name
     */
    @Override
    public String toString() {
        return name;
    }
}
