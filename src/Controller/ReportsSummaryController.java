package Controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import models.Appointments;
import models.Customer;
import sample.Main;
import utils.Database;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.ResourceBundle;

/**
 * It is used to show the appointment report by contact and the appointment report by month and type.
 * @author Omar Ahmad
 */
public class ReportsSummaryController implements Initializable {
    /**
     * Status label.
     */
    public Label statsLabel;
    @FXML
    /**
     * appointment contact field.
     */
    private TableColumn<Appointments, String> ContactField;
    /**
     * appointment date field.
     */
    @FXML
    private TableColumn<Appointments, LocalDate> DateField;

    @FXML
    private TableColumn<Appointments, String> DescriptionField;
    /**
     * appointment description field.
     */
    @FXML
    private TableColumn<Appointments, LocalTime> EndTimeField;
    /**
     * appointment id field.
     */

    @FXML
    private TableColumn<Appointments, Integer> IdField;
    /**
     * appointment report view table.
     */
    @FXML
    private TableView<Appointments> RepportTableView;
    /**
     * appointment start time field.
     */
    @FXML
    private TableColumn<Appointments, LocalTime> StartTimeField;
    /**
     * appointment tittle field.
     */
    @FXML
    private TableColumn<Appointments, String> TitleField;
    /**
     * appointment type field.
     */
    @FXML
    private TableColumn<Appointments, String> TypeField;
    /**
     * appointment customer id field.
     */
    @FXML
    private TableColumn<Customer, Integer> customerID;

    /**
     * It is used to  Initialize the appointment table columns.
     * @param url
     * @param resourceBundle
     */
    public void initialize(URL url, ResourceBundle resourceBundle) {
        {getStats();}
        RepportTableView.setItems(getContactSchedule());
        ContactField.setCellValueFactory(new PropertyValueFactory<>("contactName"));
        IdField.setCellValueFactory(new PropertyValueFactory<>("ApptId"));
        TitleField.setCellValueFactory(new PropertyValueFactory<>("ApptTitle"));
        TypeField.setCellValueFactory(new PropertyValueFactory<>("ApptType"));
        DescriptionField.setCellValueFactory(new PropertyValueFactory<>("ApptDescription"));
        StartTimeField.setCellValueFactory(new PropertyValueFactory<>("ApptStartTime"));
        EndTimeField.setCellValueFactory(new PropertyValueFactory<>("ApptEndTime"));
        customerID.setCellValueFactory(new PropertyValueFactory<>("CUSTId"));
        DateField.setCellValueFactory(new PropertyValueFactory<>("ApptDate"));
    }

    /**
     * This method Schedule of Contacts and returns list of appointments.
     * @return appointment list.
     */

    public ObservableList getContactSchedule() {
        {
            ObservableList<Appointments> appointmentsList = FXCollections.observableArrayList();
            try {
                String sql = "select \n" +
                        "contacts.Contact_Name, \n" +
                        "Appointment_Id, \n" +
                        "Title, \n" +
                        "Type, \n" +
                        "Description, \n" +
                        "Start, \n" +
                        "End, \n" +
                        "users.User_ID, \n" +
                        "customers.Customer_ID \n" +
                        "from appointments, \n" +
                        "customers, \n" +
                        "users, \n" +
                        "contacts \n" +
                        "where \n" +
                        "appointments.Customer_ID = customers.Customer_ID and\n" +
                        "appointments.Contact_ID = contacts.Contact_ID and\n" +
                        "appointments.User_ID = users.User_ID";
                System.out.println(sql);
                PreparedStatement ps = Database.getConnection().prepareStatement(sql);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    Appointments appointment = new Appointments();
                    appointment.setContactName(rs.getString("Contact_Name"));
                    appointment.setApptId(rs.getInt("Appointment_Id"));
                    appointment.setApptTitle(rs.getString("Title"));
                    appointment.setApptType(rs.getString("Type"));
                    appointment.setApptDescription(rs.getString("Description"));
                    appointment.setApptStartTime(rs.getTime("Start").toLocalTime());
                    appointment.setApptEndTime(rs.getTime("End").toLocalTime());
                    appointment.setCUSTId(rs.getInt("Customer_ID"));
                    appointment.setApptDate(rs.getDate("Start").toLocalDate());
                    appointmentsList.add(appointment);
                }
            } catch (SQLException ex) {
                System.out.println("Error: " + ex.getMessage());
            }
            return appointmentsList;
        }
    }

    /**
     * It is used to take the user back to Reports menu.
     * @param event
     */
        @FXML
        void BackOnAction (ActionEvent event){
            Main.stageAndScene(event, "/views/Reports.fxml");
        }
    /**
     *  This method is used to get the appointment stats result by month and type show on the form.
     *  also, it shows the total appointments
     */
    public void getStats() {
        try {
            String sql = "select \n" +
                    "Type, \n" +
                    "month(start) as 'Month', \n" +
                    "Count(*) as 'Total' \n" +
                    "from \n" +
                    "appointments \n" +
                    "group by \n" +
                    "Type, \n" +
                    "month(start)";
            PreparedStatement ps = Database.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            StringBuilder str = new StringBuilder();
            str.append(String.format("%1$-30s %2$-10s %3$s", "Type", "Month", "Total"));
            str.append(System.getProperty("line.separator"));
            str.append(String.join("", Collections.nCopies(50, "-")));
            str.append("\n");
            str.append(System.getProperty("line.separator"));

            while (rs.next()) {
                str.append(String.format("%1$-30s %2$-10s %3$s \n",
                        rs.getString("Type"),
                        rs.getString("Month"),
                        rs.getInt("Total")));
            }
            statsLabel.setText(str.toString());
        } catch (SQLException ex) {
            System.out.println("Error " + ex.getMessage());
        }
    }
}


