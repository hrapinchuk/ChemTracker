package model;

import java.time.LocalDate;

/**
 * This model class provides the ability to create and access Stock objects.
 * @author Heather Rapinchuk
 */
public class Stock {
    // Variable declarations
    private int id;
    private int chemID;
    private double amount;
    LocalDate addDate;

    // Constructor
    /**
     * This constructor creates a Stock object.
     * @param id An int representing the stock object's ID.
     * @param chemID An int representing the ID of the chemical being stocked
     * @param amount A double representing the amount of chemical being stocked
     * @param addDate A LocalDate representing the date that the stock was added
     */
    public Stock(int id, int chemID, double amount, LocalDate addDate) {
        this.id = id;
        this.chemID = chemID;
        this.amount = amount;
        this.addDate = addDate;
    }

    /**
     * This method gets a stock object's ID.
     * @return An int representing the stock object's ID
     */
    public int getId() {
        return id;
    }

    /**
     * This method sets a stock object's ID.
     * @param id An int representing the stock object's ID
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * This method gets the ID of the chemical being stocked.
     * @return An int representing the ID of the chemical being stocked
     */
    public int getChemID() {
        return chemID;
    }

    /**
     * This method sets the ID of the chemical being stocked.
     * @param chemID An int representing the ID of the chemical being stocked
     */
    public void setChemID(int chemID) {
        this.chemID = chemID;
    }

    /**
     * This method gets the amount of the chemical being stocked.
     * @return A double representing the amount of the chemical being stocked
     */
    public double getAmount() {
        return amount;
    }

    /**
     * This method sets the amount of the chemical being stocked.
     * @param amount A double representing the amount of the chemical being stocked
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }

    /**
     * This method gets the date that the stock was added.
     * @return A LocalDate representing the date that the stock was added
     */
    public LocalDate getAddDate() {
        return addDate;
    }

    /**
     * This method sets the date that the stock was added.
     * @param addDate A LocalDate representing the date that the stock was added
     */
    public void setAddDate(LocalDate addDate) {
        this.addDate = addDate;
    }
}
