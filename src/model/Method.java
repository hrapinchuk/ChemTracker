package model;

/**
 * This model class provides the ability to create and access Method objects.
 * @author Heather Rapinchuk
 */
public class Method {
    // Variable declarations
    private int id;
    private String name;

    // Constructor
    /**
     * This constructor creates a Method object.
     * @param id An int representing the application method's ID
     * @param name A String representing the name of the application method
     */
    public Method(int id, String name) {
        this.id = id;
        this.name = name;
    }

    // Methods
    /**
     * This method gets an application method's ID.
     * @return An int representing the application method's ID
     */
    public int getId() {
        return id;
    }

    /**
     * This method sets an application method's ID.
     * @param id An int representing the application method's ID
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * This method gets the name of an application method.
     * @return A String representing the name of an application method
     */
    public String getName() {
        return name;
    }

    /**
     * This method sets the name of an application method.
     * @param name A String representing the name of an application method
     */
    public void setName(String name) {
        this.name = name;
    }
}
