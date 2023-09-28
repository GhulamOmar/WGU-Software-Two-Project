package models;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.customerDB;
import java.sql.SQLException;


/**
 * Customer class is used to customer objects.
 * @author Omar Ahmad
 */

public class Customer {
    /**
     * Customer id field.
     */
    private int id;
    /**
     * Customer name field.
     */
    private String CUSTName;
    /**
     * Customer address field.
     */
    private String CUSTAddress;
    /**
     * Customer postal code field.
     */
    private String postalCode;
    /**
     * Customer phone number field.
     */
    private String CUSTPone;
    /**
     * Customer country id field.
     */
    private int countryId;
    /**
     * Customer division id field.
     */
    private int divisionId;
    /**
     * Customer user id field.
     */
    private int userid;
    /**
     * List of customers.
     */

    public static ObservableList<Customer> allCustomer = FXCollections.observableArrayList();

    /**
     *  The constructor is used for creating new customer objects.
     * @param id customer id
     * @param CUSTName customer name
     * @param CUSTAddress customer address
     * @param postalCode customer postal code
     * @param CUSTPone customer phone
     * @param countryId customer id
     * @param divisionId customer division
     */

    public Customer(int id, String CUSTName, String CUSTAddress, String postalCode, String CUSTPone, int countryId, int divisionId) {
        this.id = id;
        this.CUSTName = CUSTName;
        this.CUSTAddress = CUSTAddress;
        this.postalCode = postalCode;
        this.CUSTPone = CUSTPone;
        this.countryId = countryId;
        this.divisionId = divisionId;
    }

    /**
     * The overload constructor is used with getAllAppointments method.
     * @param id customer id.
     * @param CUSTName customer name.
     */
    public Customer(int id, String CUSTName) {
        this.id = id;
        this.CUSTName = CUSTName;
    }

    /**
     * Getter and settrt for customer id
     * @return customer id.
     */
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;

    }

    /**
     * Getter and setter for customer name.
     * @return customer name.
     */
    public String getCUSTName() {
        return CUSTName;
    }

    public void setCUSTName(String CUSTName) {
        this.CUSTName = CUSTName;

    }

    /**
     * Getter and setter for customer address.
     * @return customer address.
     */
    public String getCUSTAddress() {
        return CUSTAddress;
    }

    public void setCUSTAddress(String CUSTAddress) {
        this.CUSTAddress = CUSTAddress;
    }

    /**
     * Getter and setter for customer postal code.
     * @return customer poster code.
     */
    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;

    }

    /**
     * Getter and setter for customer phone number.
     * @return customer phone number.
     */
    public String getCUSTPone() {
        return CUSTPone;
    }

    public void setCUSTPone(String CUSTPone) {
        this.CUSTPone = CUSTPone;

    }

    /**
     * Getter and setter for customer country id.
     * @return customer country id.
     */
    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;

    }

    /**
     * Getter and setter for customer division id.
     * @return division id.
     */
    public int getDivisionId() {
        return divisionId;
    }

    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;

    }

    /**
     * Getter and setter user id.
     * @return user id
     */

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;

    }

    /**
     * This is used to delete customer for database.
     * @param customer objects of customers.
     * @return
     * @throws SQLException
     */
    public static boolean deleteCustomer(Customer customer) throws SQLException {

        allCustomer.remove(customer);
        customerDB.deleteCustomer(customer.getId());
        return true;
    }

    /**
     * This shows the customer id and name as a string in the customer combo box.
     * @return customer name and id.
     */
    @Override
    public String toString() {
        return((id + " " + CUSTName));}
    }



