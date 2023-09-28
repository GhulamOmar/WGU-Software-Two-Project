package utils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Divisions;
import models.Location;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * divisionDB class is used for division database.
 * @author Omar Ahmad
 */
public class divisionDB {
    /**
     * It is used to pick all divisions from the first level division table using a select SQL query.
     * @return the list of division.
     */
    public static ObservableList<Location> getAllDivisions() {
        ObservableList<Location> divisionList = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * from first_level_divisions";
            PreparedStatement ps = Database.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                int DivisionID = rs.getInt("Division_ID");
                String DivisionName = rs.getString("Division");
                int country = rs.getInt("COUNTRY_ID");
                Location division = new Divisions(DivisionID, DivisionName, country); // Polymorphism: Creating a Division object that
                // can hold any child class of Division.
                divisionList.add(division); // Polymorphism: Adding a Division object to a list can contain any child class Division.
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return divisionList;
    }
}




