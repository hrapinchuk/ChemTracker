package model;

/**
 * This model class provides the ability to create and access Chemical objects.
 * @author Heather Rapinchuk
 */
public class Chemical {
    // Variable declarations
    private int id;
    private String name;
    private String manufacturer;
    private String regNum;
    private String reentry;
    private int unitID;
    private String unit;
    private String description;
    private double totalStock;

    // Constructor
    /**
     * This constructor creates a Chemical object.
     * @param id An int representing the chemical's ID
     * @param name A String representing the chemical's name
     * @param manufacturer A String representing a chemical's manufacturer
     * @param regNum A String representing a chemical's registration number
     * @param reentry A String representing a chemical's reentry period
     * @param unitID An int representing the ID of the unit used to measure this chemical
     * @param unit A String representing the name of the unit used to measure this chemical
     * @param description A String representing a description of the chemical
     * @param totalStock A double representing the current stock amount of this chemical
     */
    public Chemical(int id,
                    String name,
                    String manufacturer,
                    String regNum,
                    String reentry,
                    int unitID,
                    String unit,
                    String description,
                    double totalStock) {
        this.id = id;
        this.name = name;
        this.manufacturer = manufacturer;
        this.regNum = regNum;
        this.reentry = reentry;
        this.unitID = unitID;
        this.unit = unit;
        this.description = description;
        this.totalStock = totalStock;
    }

    // Methods
    /**
     * This method gets a chemical's ID.
     * @return An int representing the chemical's ID
     */
    public int getId() {
        return id;
    }

    /**
     * This method sets a chemical's ID.
     * @param id An int representing the chemical's ID
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * This method gets a chemical's name.
     * @return A String representing the chemical's name
     */
    public String getName() {
        return name;
    }

    /**
     * This method sets a chemical's name.
     * @param name A String representing the chemical's name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * This method gets the name of a chemical's manufacturer.
     * @return A String representing the chemical's manufacturer
     */
    public String getManufacturer() {
        return manufacturer;
    }

    /**
     * This method sets the name of a chemical's manufacturer.
     * @param manufacturer A String representing the chemical's manufacturer
     */
    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    /**
     * This method gets a chemical's registration number.
     * @return A String representing the chemical's registration number
     */
    public String getRegNum() {
        return regNum;
    }

    /**
     * This method sets a chemical's registration number.
     * @param regNum A String representing the chemical's registration number
     */
    public void setRegNum(String regNum) {
        this.regNum = regNum;
    }

    /**
     * This method gets a chemical's reentry period.
     * @return A String representing the chemical's reentry period
     */
    public String getReentry() {
        return reentry;
    }

    /**
     * This method sets a chemical's reentry period.
     * @param reentry A String representing the chemical's reentry period
     */
    public void setReentry(String reentry) {
        this.reentry = reentry;
    }

    /**
     * This method gets the ID of the unit that is used to measure this chemical.
     * @return An int representing the ID of the unit that is used to measure this chemical
     */
    public int getUnitID() {
        return unitID;
    }

    /**
     * This method sets the ID of the unit that is used to measure this chemical.
     * @param unitID An int representing the ID of the unit that is used to measure this chemical
     */
    public void setUnitID(int unitID) {
        this.unitID = unitID;
    }

    /**
     * This method gets the name of the unit that is used to measure this chemical.
     * @return A String representing the name of the unit that is used to measure this chemical
     */
    public String getUnit() {
        return unit;
    }

    /**
     * This method sets the name of the unit that is used to measure this chemical.
     * @param unit A string representing the name of the unit that is used to measure this chemical
     */
    public void setUnit(String unit) {
        this.unit = unit;
    }

    /**
     * This method gets a description of this chemical.
     * @return A String representing a description of this chemical
     */
    public String getDescription() {
        return description;
    }

    /**
     * This method sets a description of this chemical.
     * @param description A String representing a description of this chemical
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * This method gets the current stock amount of this chemical.
     * @return A double representing the current stock amount of this chemical
     */
    public double getTotalStock() {
        return totalStock;
    }

    /**
     * This method sets the current stock amount of this chemical.
     * @param totalStock A double representing the current stock amount of this chemical
     */
    public void setTotalStock(double totalStock) {
        this.totalStock = totalStock;
    }

    /**
     * This method formats a Chemical object that is converted to a String.
     * @return A String that concatenates a chemical's name and registration number
     */
    @Override
    public String toString() {
        return name + " (" + regNum + ")";
    }
}
