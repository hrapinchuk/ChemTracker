package model;

import java.time.LocalDateTime;

/**
 * This model class provides the ability to create and access Use objects.
 * @author Heather Rapinchuk
 */
public class Use {
    // Variable declarations
    private int id;
    private LocalDateTime useDateTime;
    private int chemID;
    private String chemical;
    private double amount;
    private int unitID;
    private String unit;
    private int methodID;
    private String method;
    private double dilution;
    private double area;
    private String areaDesc;

    // Constructor
    /**
     * This constructor creates a Use object.
     * @param id An int representing the use's ID
     * @param useDateTime A LocalDateTime representing the date and time of the use
     * @param chemID An int representing the ID of the chemical being used
     * @param chemical A String representing the name of the chemical being used
     * @param amount A double representing the amount of chemical being used
     * @param unitID An int representing the ID of the unit used to measure the chemical being used
     * @param unit A String representing the name of the unit used to measure the chemical being
     *             used
     * @param methodID An int representing the ID of the method used to spread the chemical
     * @param method A String representing the name of the method used to spread the chemical
     * @param dilution A double representing the number of gallons of water used to dilute the
     *                 chemical being used
     * @param area A double representing the number of acres covered during this use
     * @param areaDesc A String representing a description of the area covered during this use
     */
    public Use(int id,
               LocalDateTime useDateTime,
               int chemID,
               String chemical,
               double amount,
               int unitID,
               String unit,
               int methodID,
               String method,
               double dilution,
               double area,
               String areaDesc) {
        this.id = id;
        this.useDateTime = useDateTime;
        this.chemID = chemID;
        this.chemical = chemical;
        this.amount = amount;
        this.unitID = unitID;
        this.unit = unit;
        this.methodID = methodID;
        this.method = method;
        this.dilution = dilution;
        this.area = area;
        this.areaDesc = areaDesc;
    }

    // Methods
    /**
     * This method gets a use's ID.
     * @return An int representing the use's ID
     */
    public int getId() {
        return id;
    }

    /**
     * This method sets a use's ID.
     * @param id An int representing the use's ID
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * This method gets the date and time of a use.
     * @return A LocalDateTime representing the date and time of a use
     */
    public LocalDateTime getUseDateTime() {
        return useDateTime;
    }

    /**
     * This method sets the date and time of a use.
     * @param useDateTime A LocalDateTime representing the date and time of a use
     */
    public void setUseDateTime(LocalDateTime useDateTime) {
        this.useDateTime = useDateTime;
    }

    /**
     * This method gets the ID of the chemical being used.
     * @return An int representing the ID of the chemical being used
     */
    public int getChemID() {
        return chemID;
    }

    /**
     * This method sets the ID of the chemical being used.
     * @param chemID An int representing the ID of the chemical being used
     */
    public void setChemID(int chemID) {
        this.chemID = chemID;
    }

    /**
     * This method gets the name of the chemical being used.
     * @return A String representing the name of the chemical being used
     */
    public String getChemical() {
        return chemical;
    }

    /**
     * This method sets the name of the chemical being used.
     * @param chemical A String representing the name of the chemical being used
     */
    public void setChemical(String chemical) {
        this.chemical = chemical;
    }

    /**
     * This method gets the amount of chemical being used.
     * @return A double representing the amount of chemical being used
     */
    public double getAmount() {
        return amount;
    }

    /**
     * This method sets the amount of chemical being used.
     * @param amount A double representing the amount of chemical being used
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }

    /**
     * This method gets the ID of unit used to measure the chemical being used.
     * @return An int representing the ID of the unit used to measure the chemical being used
     */
    public int getUnitID() {
        return unitID;
    }

    /**
     * This method sets the ID of the unit used to measure the chemical being used.
     * @param unitID An int representing the ID of the unit used to measure the chemical being used
     */
    public void setUnitID(int unitID) {
        this.unitID = unitID;
    }

    /**
     * This method gets the name of the unit used to measure the chemical being used.
     * @return A String representing the name of the unit used to measure the chemical being used
     */
    public String getUnit() {
        return unit;
    }

    /**
     * This method sets the name of the unit used to measure the chemical being used.
     * @param unit A string representing the name of the unit used to measure the chemical being
     *             used
     */
    public void setUnit(String unit) {
        this.unit = unit;
    }

    /**
     * This method gets the ID of the method used to spread the chemical.
     * @return An int representing the ID of the method used to spread the chemical
     */
    public int getMethodID() {
        return methodID;
    }

    /**
     * This method sets the ID of the method used to spread the chemical.
     * @param methodID An int representing the ID of the method used to spread the chemical
     */
    public void setMethodID(int methodID) {
        this.methodID = methodID;
    }

    /**
     * This method gets the name of the method used to spread the chemical.
     * @return A String representing the name of the method used to spread the chemical
     */
    public String getMethodName() {
        return method;
    }

    /**
     * This method sets the name of the method used to spread the chemical.
     * @param method A String representing the name of the method used to spread the chemical
     */
    public void setMethodName(String method) {
        this.method = method;
    }

    /**
     * This method gets the number of gallons of water used to dilute the chemical being used.
     * @return A double representing the number of gallons of water used to dilute the chemical
     * being used
     */
    public double getDilution() {
        return dilution;
    }

    /**
     * This method sets the number of gallons of water used to dilute the chemical being used.
     * @param dilution A double representing the number of gallons of water used to dilute the
     *                 chemical being used
     */
    public void setDilution(double dilution) {
        this.dilution = dilution;
    }

    /**
     * This method gets the number of acres covered during this use.
     * @return A double representing the number of acres covered during this use
     */
    public double getArea() {
        return area;
    }

    /**
     * This method sets the number of acres covered during this use.
     * @param area A double representing the number of acres covered during this use
     */
    public void setArea(double area) {
        this.area = area;
    }

    /**
     * This method gets a description of the area covered during this use.
     * @return A String representing a description of the area covered during this use
     */
    public String getAreaDesc() {
        return areaDesc;
    }

    /**
     * This method sets a description of the area covered during this use.
     * @param areaDesc A String representing a description of the area covered during this use
     */
    public void setAreaDesc(String areaDesc) {
        this.areaDesc = areaDesc;
    }
}
