package utils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Contacts;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * ContactBD class is used for contact database.
 * @author Omar Ahmad
 */
public class ContactDB {
    /**
     * It is used to pick all contacts from the contact table.
     * @return the the list of contact.
     */
    public static ObservableList<Contacts> getAllContacts() {
        ObservableList<Contacts> contactList = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * from contacts";

            PreparedStatement ps = Database.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int contactID = rs.getInt("Contact_ID");
                String contactName = rs.getString("Contact_Name");
                Contacts contact = new Contacts(contactID, contactName);
                contactList.add(contact);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return contactList;
    }
}

