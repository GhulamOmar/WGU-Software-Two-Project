package Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import models.Appointments;
import models.Contacts;
import sample.Main;
import utils.ContactDB;
import utils.Database;
import javafx.scene.control.TextField;
import java.awt.*;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;

/**
 *This is the customer report controller class.
 * It lets the user to select a customer from the drop down box and shows the all appointments of that customer.
 * @author Omar Ahmad.
 */
public class CustomerReport implements Initializable {

    /**
     * Customer report table view.
     */
    @FXML
    private TableView<Appointments> customerReport;
    /**
     * Appointment contact field.
     */

    @FXML
    private TableColumn<Appointments, String> Contact;
    /**
     * Appointment date field.
     */

    @FXML
    private TableColumn<Appointments, LocalDate> Date;

    @FXML
    private TableColumn<Appointments, String> Description;
    /**
     * Appointment end time field.
     */

    @FXML
    private TableColumn<Appointments, LocalTime> EndTime;
    /**
     * Appointment id field.
     */

    @FXML
    private TableColumn<Appointments, Integer> ID;
    /**
     * Appointment start time field.
     */

    @FXML
    private TableColumn<Appointments, LocalTime> StartTime;
    /**
     * Appointment title field.
     */

    @FXML
    private TableColumn<Appointments, String> Title;
    /**
     * Appointment type field.
     */
    @FXML
    private TableColumn<Appointments, String> Type;
    @FXML
    private TextField customerIdField;
    /**
     * It is used to Initialize the appointment table columns.
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.ID.setCellValueFactory(new PropertyValueFactory("ApptId"));
        this.Title.setCellValueFactory(new PropertyValueFactory("ApptTitle"));
        this.Description.setCellValueFactory(new PropertyValueFactory("ApptDescription"));
        this.Type.setCellValueFactory(new PropertyValueFactory("ApptType"));
        this.Date.setCellValueFactory(new PropertyValueFactory("ApptDate"));
        this.StartTime.setCellValueFactory(new PropertyValueFactory("ApptStartTime"));
        this.EndTime.setCellValueFactory(new PropertyValueFactory("ApptEndTime"));
        this.Contact.setCellValueFactory(new PropertyValueFactory<>("contactName"));
    }
    /**
     * This method is used to take user back to reports selection menu.
     *
     * @param event
     */
    @FXML
    void backOnAction(ActionEvent event) {
        Main.stageAndScene(event, "/views/Reports.fxml");
    }
    /**
     * This is used to get all appointments reports by customer.
     *
     * @param customerID customer id.
     * @return list of appointments bu customer.
     */
    public static ObservableList<Appointments> getCustomerReport(int customerID) {
        ObservableList<Appointments> customers = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * from appointments WHERE Customer_ID= '" + customerID + "'";

            PreparedStatement ps = Database.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int appointmentID = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String type = rs.getString("Type");
                LocalDate date = rs.getDate("Start").toLocalDate();
                LocalTime startTime = rs.getTime("Start").toLocalTime();
                LocalTime endTime = rs.getTime("End").toLocalTime();
                int contact = rs.getInt("Contact_ID");

                String contactName = "";
                for (Contacts cnct : ContactDB.getAllContacts()) {
                    if (contact == cnct.getContactId()) {

                        contactName = (cnct.getContactName());
                    }
                }
                Appointments appt = new Appointments(appointmentID, title, description, type, date, Main.getLocalFromUTC(startTime, date), Main.getLocalFromUTC(endTime, date), contactName);
                customers.add(appt);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return customers;
    }

    /**
     * It is used to set up the appointment tableview to show all appointments for the enterd customer id.
     *  */
    @FXML
    void  searchOnAction(ActionEvent event) {
        try {
            if (customerIdField.getText().isEmpty()) {
                // Handle the case when customerIdField is empty.
                return; // Exit the method if customerIdField is empty.
            }

            int customerId = Integer.parseInt(customerIdField.getText());
            this.customerReport.setItems(getCustomerReport(customerId));
        } catch (NumberFormatException e) {
            // Handle the case when entered value in customerIdField is empty.

        }
    }
}



