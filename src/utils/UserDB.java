package utils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.user;

/**
 * UserDB class is used for user database.
 * @author Omar Ahmad
 */
public class UserDB {
    /**
     * Variable for user name
     */
    public static String userN;

    /**
     *iT is used to pick user and password from user table for login and validation.
     * @param user user name
     * @param pass user password
     * @return if the user and password match it turn the boolean true.
     */
    public static boolean validateLogin(String user, String pass) {
        try {
            String sql = "SELECT * from users WHERE User_Name = ? and Password = ? ";
            PreparedStatement ps = Database.getConnection().prepareStatement(sql);
            ps.setString(1, user);
            ps.setString(2, pass);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                userN = user;

                return true;
            } else
                return false;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    /**
     * It is used to pck users from user table using a select SQL statement.
     * @return list of users.
     */
    public static ObservableList<user> getAllUsers() {
        ObservableList<user> userList = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * from users";
            PreparedStatement ps = Database.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                int userID = rs.getInt("User_ID");
                String username = rs.getString("User_Name");
                user user = new user(userID, username);
                userList.add(user);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return userList;
    }
}