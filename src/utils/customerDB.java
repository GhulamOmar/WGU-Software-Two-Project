package utils;
import models.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *customerDB class is used for customer database.
 * @author Omar Ahmad.
 */
public class customerDB {
    /**
     *It is used to add customer to customer table using an insert SQL statement.
     * @param customer object for customer
     * @throws SQLException
     */

    public static void addCustomer(Customer customer)
            throws SQLException
    {
        String query = "INSERT INTO customers ( Customer_Name, Address, Postal_Code, Phone, Created_By, Last_Updated_By, Division_ID ) VALUES (?, ?, ?, ?, '" +UserDB.userN+ "', '" +UserDB.userN+ "', ?)";

        try {
            // This sets all the prepared statement parameters.
            PreparedStatement st = Database.getConnection().prepareStatement(query);
            st.setString(1, customer.getCUSTName());
            st.setString(2, customer.getCUSTAddress());
            st.setString(3, customer.getPostalCode());
            st.setString(4, customer.getCUSTPone());
            st.setInt(5, customer.getDivisionId());
            st.executeUpdate();
            st.close(); }
        catch (SQLException se) {
            throw se;
        }
    }

    /**
     * It is used to remove customers from customer table using an delete SQL statement.
     * @param customerID customer id
     * @throws SQLException
     */
    public static void deleteCustomer(int customerID ) throws SQLException {

        String query = "DELETE FROM customers WHERE Customer_ID = ?";
        PreparedStatement ps = Database.getConnection().prepareStatement(query);
        ps.setInt(1, customerID);
        ps.execute();
    }

    /**
     * It is used to modify the customer to the customer table using an update SQL statement
     * @param customer object for customer.
     */
    public static void updateCustomer(Customer customer)  {

        try{
            String query = "UPDATE customers set Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Created_By = '" +UserDB.userN+ "', Last_Updated_By = '" +UserDB.userN+ "', Division_ID =? WHERE Customer_ID =?";

            PreparedStatement ps = Database.getConnection().prepareStatement(query);
            ps.setString(1, customer.getCUSTName());
            ps.setString(2, customer.getCUSTAddress());
            ps.setString(3, customer.getPostalCode());
            ps.setString(4, customer.getCUSTPone());
            ps.setInt(5, customer.getDivisionId());
            ps.setInt(6, customer.getId());

            ps.executeUpdate();
        }
        catch(SQLException e) {
        }
    }

    /**
     * It is used to pick all customers from customer table by using a select SQL statement.
     * @return the customer list.
     */
    public static ObservableList<Customer> getAllCustomer() {
        ObservableList<Customer> customerList = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * FROM customers, first_level_divisions WHERE customers.Division_ID = first_level_divisions.Division_ID;";
            PreparedStatement ps = Database.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                Customer customer = new Customer(0, "", "", "", "",  1, 1);
                int customerID = rs.getInt("Customer_ID");
                String name = rs.getString("Customer_Name");
                String address = rs.getString("Address");
                String postalCode = rs.getString("Postal_Code");
                String phone = rs.getString("Phone");
                int country = rs.getInt("Country_ID");
                int divisionId =  rs.getInt("Division_ID");

                customer = new Customer(customerID, name, address, postalCode, phone, country, divisionId);
                customerList.add(customer);
            }
        } catch (SQLException throwables){
            throwables.printStackTrace();
        }
        return customerList;
    }
}
