package utils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Appointments;
import models.Contacts;
import sample.Main;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.*;

/**
 *It is appointmentBD class for database query.
 * @author Omar Ahmad
 */

public class appointmentDB {
    /**
     * It is used to Modify appointment in the appointment table using an update SQL statement.
     *
     * @param appt object for appointment.
     */

    public static void updateAppointment(Appointments appt) {

        try {
            String query = "UPDATE appointments set Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End  =?,  Customer_ID =?, Contact_ID =? WHERE Appointment_ID =?";

            PreparedStatement ps = Database.getConnection().prepareStatement(query);

            ps.setString(1, appt.getApptTitle());
            ps.setString(2, appt.getApptDescription());
            ps.setString(3, appt.getAppLocation());
            ps.setString(4, appt.getApptType());

            ps.setString(5, appt.getApptDate() + " " + appt.getApptStartTime() + ":00");
            ps.setString(6, appt.getApptDate() + " " + appt.getApptEndTime() + ":00");
            ps.setInt(7, appt.getCUSTId());
            ps.setInt(8, appt.getContactID());
            ps.setInt(9, appt.getApptId());

            ps.executeUpdate();
        } catch (SQLException e) {
        }
    }

    /**
     * It used to remove the appointment from the database using the delete SQL statement.
     *
     * @param appointmentID appointment id.
     * @throws SQLException
     */
    public static void deleteAppointment(int appointmentID) throws SQLException {

        String query = "DELETE FROM appointments WHERE Appointment_ID = ?";

        PreparedStatement ps = Database.getConnection().prepareStatement(query);

        ps.setInt(1, appointmentID);

        ps.execute();

    }

    /**
     * It used to Add appointment to appointment table using an insert SQL statement.
     *
     * @param appointments appointment object
     * @throws SQLException
     */
    public static void addAppointments(Appointments appointments)
            throws SQLException {
        String query = "INSERT INTO appointments (  Title, Description, Location, Type, Start, End, Created_By, Last_Updated_By, Customer_ID, User_ID, Contact_ID ) VALUES ( ?, ?, ?, ?, ?, ?,'" + UserDB.userN + "','" + UserDB.userN + "', ?, ?, ?)";

        try {
            // It is used to set all the prepared statement parameters.
            PreparedStatement st = Database.getConnection().prepareStatement(query);

            st.setString(1, appointments.getApptTitle());
            st.setString(2, appointments.getApptDescription());
            st.setString(3, appointments.getAppLocation());
            st.setString(4, appointments.getApptType());
            st.setString(5, appointments.getApptDate() + " " + appointments.getApptStartTime() + ":00"); //one hour from showing correctly in table
            st.setString(6, appointments.getApptDate() + " " + appointments.getApptEndTime() + ":00");//storing correctly in db
            // st.setString(7, DBUser.userN);
            st.setInt(7, appointments.getCUSTId());
            st.setInt(8, appointments.getUserID());
            st.setInt(9, appointments.getContactID());
            st.executeUpdate();
            st.close();
        } catch (SQLException se) {
            throw se;
        }
    }

    /**
     * It is used to select all appointments from the appointment table using a select SQL statement.
     */
    public static ObservableList<Appointments> getAllAppointments() {

        ObservableList<Appointments> appointmentList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * from appointments";

            PreparedStatement ps = Database.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int ApptId = rs.getInt("Appointment_ID");
                String ApptTitle = rs.getString("Title");
                String ApptDescription = rs.getString("Description");
                String Apptlocation = rs.getString("Location");
                String ApptType = rs.getString("Type");
                LocalDate ApptDate = rs.getDate("Start").toLocalDate();
                LocalTime ApptStartTime = rs.getTime("Start").toLocalTime(); //utc
                LocalTime AppteEndTime = rs.getTime("End").toLocalTime(); //utc
                int CUSTId = rs.getInt("Customer_ID");
                int userID = rs.getInt("User_ID");
                int contactID = rs.getInt("Contact_ID");
                String cnctName = "ID:";
                for (Contacts cnct : ContactDB.getAllContacts()) {
                    if (contactID == cnct.getContactId()) {
                        cnctName = (cnct.getContactName());
                    }
                }
                //It is used for utc start and end times.
                Appointments appointment = new Appointments(ApptId, ApptTitle, ApptDescription, Apptlocation, ApptType, ApptDate, Main.getLocalFromUTC(ApptStartTime, ApptDate), Main.getLocalFromUTC(AppteEndTime, ApptDate), CUSTId, cnctName, userID);
                appointmentList.add(appointment);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return appointmentList;
    }

    //Is used to get monthly appointments.
    public static ObservableList<Appointments> getMonthlyAppointments(String month, String year) {
        ObservableList<Appointments> appointmentsList = FXCollections.observableArrayList();
        try {
            String sql = "select \n" +
                    "Appointment_Id, \n" +
                    "Title, \n" +
                    "Description, \n" +
                    "Location, \n" +
                    "Type, \n" +
                    "Start, \n" +
                    "End, \n" +
                    "customers.Customer_ID, \n" +
                    "users.User_ID, \n" +
                    "contacts.Contact_ID, \n" +
                    "contacts.Contact_Name \n" +
                    "from appointments, \n" +
                    "customers, \n" +
                    "users, \n" +
                    "contacts \n" +
                    "where \n" +
                    "appointments.Customer_ID = customers.Customer_ID and\n" +
                    "appointments.User_ID = users.User_ID\n" +
                    "and \n" +
                    "appointments.Contact_ID = contacts.Contact_ID and\n" +
                    "monthname(start) = \"" + month + "\" and\n" +
                    "year(start) = \"" + year + "\"";
            PreparedStatement ps = Database.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Appointments appointment = new Appointments();

                appointment.setApptId(rs.getInt("Appointment_Id"));
                appointment.setApptTitle(rs.getString("Title"));
                appointment.setApptDescription(rs.getString("Description"));
                appointment.setAppLocation(rs.getString("Location"));
                appointment.setApptType(rs.getString("Type"));
                appointment.setCUSTId(rs.getInt("Customer_ID"));
                appointment.setUserID(rs.getInt("User_ID"));
                appointment.setContactID(rs.getInt("Contact_ID"));
                appointment.setContactName(rs.getString("Contact_Name"));
                appointment.setApptStartTime(rs.getTime("Start").toLocalTime());
                appointment.setApptEndTime(rs.getTime("End").toLocalTime());
                appointment.setApptDate(rs.getDate("Start").toLocalDate());
                appointmentsList.add(appointment);
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return appointmentsList;
    }
}






