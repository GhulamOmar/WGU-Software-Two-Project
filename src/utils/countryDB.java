package utils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Countries;
import models.Location;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * countryDB class is used for country database.
 * @author Omar Ahmad
 */
public class countryDB {
    /**
     * It is used to pick all countries from the country table.
     * @return the list of country.
     */
    public static ObservableList<Location> getAllCountries(){
        ObservableList<Location> locationList = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * from countries ";
            PreparedStatement ps = Database.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){

                int CountryId = rs.getInt("Country_ID");
                String CountryName = rs.getString("Country");
                Location country = new Location(CountryId, CountryName); // Polymorphism: Creating a Location object that
                // can hold any child class of Location.
                locationList.add(country);// Polymorphism: Adding a Location object to a list can contain any child class Location.
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return locationList;

    }

}
