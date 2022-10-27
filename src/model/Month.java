package model;

/**
 * This model class provides the ability to create and access Month objects.
 * @author Heather Rapinchuk
 */
public class Month {
    // Variable declarations
    private int number;
    private String name;

    // Constructor
    /**
     * This constructor creates a Month object.
     * @param number An int representing a month's number
     * @param name A String containing the name of the month
     */
    public Month(int number, String name) {
        this.number = number;
        this.name = name;
    }

    // Methods
    /**
     * This method gets a month's number.
     * @return An int representing a month's number
     */
    public int getNumber() {
        return number;
    }

    /**
     * This method sets a month's number.
     * @param number An int representing a month's number
     */
    public void setNumber(int number) {
        this.number = number;
    }

    /**
     * This method gets a month's name.
     * @return A String representing a month's name
     */
    public String getName() {
        return name;
    }

    /**
     * This method sets a month's name.
     * @param name A String representing a month's name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * This method formats a Month object that is converted to a String.
     * @return A String that indicates the month's name
     */
    @Override
    public String toString() {
        return name;
    }
}
