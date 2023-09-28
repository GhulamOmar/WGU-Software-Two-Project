package views;

import Controller.AppointmentModifyController;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import models.Appointments;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.Calendar;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
import sample.Main;
import utils.appointmentDB;

/**
 *It is the Control of main menu for the appointment tableview and it is the main screen.
 * @author Omar Ahmad.
 */

public class MainMenu implements Initializable {
    /**
     * Appointment table view.
     */
    @FXML
    private TableView<Appointments> AppointmentTableView;
    /**
     * Appointment title field.
     */
    @FXML
    private TableColumn<Appointments, String> APPtTitlefield;
    /**
     * Appointment date field.
     */
    @FXML
    private TableColumn<Appointments, LocalDate> ApptDatefield;
    /**
     * Appointment id field.
     */

    @FXML
    private TableColumn<Appointments, Integer> ApptIDfield;
    /**
     * Appointment user id field.
     */

    @FXML
    private TableColumn<Appointments, Integer> UserIdfield;
    /**
     * Appointment contact field.
     */

    @FXML
    private TableColumn<Appointments, String> contactfield;
    /**
     * Appointment customer id field.
     */

    @FXML
    private TableColumn<Appointments, Integer> customeridfield;
    /**
     * Appointment discription field.
     */

    @FXML
    private TableColumn<Appointments, String> descritionfield;
    /**
     * Appointment end time field.
     */

    @FXML
    private TableColumn<Appointments, LocalTime> endtimefield;
    /**
     * Appointment location field.
     */

    @FXML
    private TableColumn<Appointments, String> locationfield;
    /**
     * Appointment start time field.
     */
    @FXML
    private TableColumn<Appointments, LocalTime> starttimefield;
    /**
     * Appointment type field.
     */

    @FXML
    private TableColumn<Appointments, String> typefield;
    /**
     * Monthly radio button.
     */

    @FXML
    private RadioButton monthlyRadioButton;
    /**
     * View all radio button.
     */

    @FXML
    private RadioButton viewallRadioButton;
    /**
     * Weakly radio button.
     */

    @FXML
    private RadioButton weaklyRadioButton;

    /**
     * It is used to Initialize appointment main menu table.
     *
     * @param url
     * @param resourceBundle
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        this.AppointmentTableView.setItems(appointmentDB.getAllAppointments());

        this.ApptIDfield.setCellValueFactory(new PropertyValueFactory("ApptId"));
        this.APPtTitlefield.setCellValueFactory(new PropertyValueFactory("ApptTitle"));
        this.descritionfield.setCellValueFactory(new PropertyValueFactory("ApptDescription"));
        this.locationfield.setCellValueFactory(new PropertyValueFactory("AppLocation"));
        this.typefield.setCellValueFactory(new PropertyValueFactory("ApptType"));
        this.ApptDatefield.setCellValueFactory(new PropertyValueFactory("ApptDate"));
        this.starttimefield.setCellValueFactory(new PropertyValueFactory("ApptStartTime"));
        this.endtimefield.setCellValueFactory(new PropertyValueFactory("ApptEndTime"));
        this.customeridfield.setCellValueFactory(new PropertyValueFactory<>("CUSTId"));
        this.contactfield.setCellValueFactory(new PropertyValueFactory("contactName"));
        this.UserIdfield.setCellValueFactory(new PropertyValueFactory("userID"));
    }

    /**
     * It takes the user to customer view table.
     *
     * @param event
     */
    @FXML
    void viewCustomerButtonPushed(ActionEvent event) {
        Main.stageAndScene(event, "/views/CUSTMain.fxml");
    }

    /**
     * It takes the user back to login screen.
     *
     * @param event
     * @throws IOException
     */
    @FXML
    void logoutButtonPushed(ActionEvent event) throws IOException {
        Stage stage;
        Parent scene;
        ResourceBundle rb = ResourceBundle.getBundle("sample/properties");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Login.fxml"));
        loader.setResources(rb);
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = loader.load();
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * It lets the user Delete appointment from table and database.
     * It Shows an alert confirming delete action, and an error alert when there is no selection made.
     *
     * @param event
     * @throws SQLException
     */

    @FXML
    void deleteAppmntButtonPushed(ActionEvent event) throws SQLException {

        Alert alert;
        if (this.AppointmentTableView.getSelectionModel().getSelectedItem() == null) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No Selection Made");
            alert.setContentText("Please select an appointment to cancel");

            alert.showAndWait();
        } else if (this.AppointmentTableView.getSelectionModel().getSelectedItem() != null) {

            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Deletion");
            alert.setContentText("Are you sure you want to cancel this appointment?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                Appointments appt = this.AppointmentTableView.getSelectionModel().getSelectedItem();
                Appointments.deleteAppointment(appt);
                alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Appointment deleted");
                alert.setContentText("ID: " + appt.getApptId() + " " + appt.getApptType() + " appointment was cancelled");
                alert.showAndWait();
            }
        }
    }

    /**
     * It takes user to add appointment form.
     *
     * @param event
     */

    public void AddApptButtonPushed(ActionEvent event) {
        Main.stageAndScene(event, "/views/AddAppt.fxml");
    }

    /**
     * It takes the user to modify appointment form. The modifyAppButtonPushed method uses the Modify Appointment Controller
     *
     * @param event
     * @throws IOException
     */

    public void modifyAppButtonPushed(ActionEvent event) throws IOException {
        if (this.AppointmentTableView.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No selection");
            alert.setContentText("No selection made. Select an appointment to modify.");
            alert.showAndWait();
        } else {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(this.getClass().getResource("/views/ModifyAppt.fxml"));
            loader.load();
            AppointmentModifyController modifyAppointmentController = loader.getController();
            modifyAppointmentController.sendAppointment(

                    this.AppointmentTableView.getSelectionModel().getSelectedItem(),
                    this.AppointmentTableView.getSelectionModel().getSelectedItem().getApptStartTime(),
                    this.AppointmentTableView.getSelectionModel().getSelectedItem().getApptEndTime(),
                    this.AppointmentTableView.getSelectionModel().getSelectedItem().getCUSTId(),
                    this.AppointmentTableView.getSelectionModel().getSelectedItem().getContactName(),
                    this.AppointmentTableView.getSelectionModel().getSelectedItem().getUserID());

            System.out.println(this.AppointmentTableView.getSelectionModel().getSelectedItem().getApptStartTime());
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    /**
     * It is used to populate table with appointments for next seven days. Appointments will show if the they fall within the seven day.
     * <b>Lambda expression is used in this method to filter the appointment table by week</b> It is used to make code better by allowing the filter to take place in this WeakRadioButtonChanged method,
     * rather than creating a separate method in the appointmentDB.
     *
     * @param event
     */
    public void WeakRadioButtonChanged(ActionEvent event) {
        monthlyRadioButton.setSelected(false);
        viewallRadioButton.setSelected(false);
        weaklyRadioButton.setSelected(true);

        LocalDateTime today = LocalDateTime.now();
        LocalDateTime thisWeek = today.plusDays(7);
        FilteredList<Appointments> week = new FilteredList<>(appointmentDB.getAllAppointments());
        week.setPredicate(row -> {
            ObservableValue<LocalDateTime> idk = new ReadOnlyObjectWrapper<>(LocalDateTime.of(row.getApptDate(), row.getApptStartTime()));
            LocalDateTime rowDate = idk.getValue();
            return rowDate.isAfter(today) && rowDate.isBefore(thisWeek);
        });
        AppointmentTableView.setItems(week);
    }

    /**
     * It is used to display all appointments by month.
     *
     * @param event
     */
    public void MonthRadioButtonChanged(ActionEvent event) {
        viewallRadioButton.setSelected(false);
        weaklyRadioButton.setSelected(false);
        monthlyRadioButton.setSelected(true);
        Calendar calendar = Calendar.getInstance();
        String getMonth = new SimpleDateFormat("MMMM").format(calendar.getTime());
        String getYear = new SimpleDateFormat("y").format(calendar.getTime());
        ObservableList<Appointments> appointmentsList = appointmentDB.getMonthlyAppointments(getMonth, getYear);
        if (appointmentsList.size() == 0) {
            Alert alertUser = new Alert(Alert.AlertType.ERROR, "No appointments available this month.");
            Optional<ButtonType> optButton = alertUser.showAndWait();
        } else {
            AppointmentTableView.setItems(appointmentDB.getMonthlyAppointments(getMonth, getYear));
            APPtTitlefield.setCellValueFactory(new PropertyValueFactory<>("ApptId"));
            APPtTitlefield.setCellValueFactory(new PropertyValueFactory<>("ApptTitle"));
            descritionfield.setCellValueFactory(new PropertyValueFactory<>("ApptDescription"));
            locationfield.setCellValueFactory(new PropertyValueFactory<>("AppLocation"));
            contactfield.setCellValueFactory(new PropertyValueFactory<>("contactName"));
            typefield.setCellValueFactory(new PropertyValueFactory<>("ApptType"));
            starttimefield.setCellValueFactory(new PropertyValueFactory<>("ApptStartTime"));
            endtimefield.setCellValueFactory(new PropertyValueFactory<>("ApptEndTime"));
            customeridfield.setCellValueFactory(new PropertyValueFactory<>("CUSTId"));
            ApptDatefield.setCellValueFactory(new PropertyValueFactory<>("ApptDate"));
        }
    }

    /**
     * It is used to show all appointments in main menu table and it is selected as default.
     *
     * @param event
     */
    public void AllRadioButtonChanged(ActionEvent event) {
        monthlyRadioButton.setSelected(false);
        weaklyRadioButton.setSelected(false);
        viewallRadioButton.setSelected(true);
        AppointmentTableView.setItems(appointmentDB.getAllAppointments());
    }

    /**
     * It takes user to report menu.
     * @param event
     * @throws IOException
     */

    @FXML
    void reportButtonPushed(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/views/Reports.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Report Summary");
        stage.setScene(scene);
        stage.show();
    }
}

