package Controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import models.Appointments;
import models.Contacts;
import models.Customer;
import models.user;
import sample.Main;
import utils.ContactDB;
import utils.UserDB;
import utils.appointmentDB;
import java.net.URL;
import java.time.*;
import java.util.ResourceBundle;
import utils.customerDB;

/**
 * It used to modify appointment and the error labels check empty fields.
 * @author Omar Ahmad
 */
public class AppointmentModifyController implements Initializable {
    /**
     *Appointment id field.
     */
    @FXML
    private TextArea appointmentIdField;
    /**
     * Appointment contact dropdown.
     */
    @FXML
    private ComboBox<Contacts> contactDropdown;
    /**
     * contact error label.
     */

    @FXML
    private Label contacterror;
    /**
     * Appointment customer id dropdown.
     */

    @FXML
    private ComboBox<Customer> customerIdDropdown;
    /**
     * custome id error label.
     */
    @FXML
    private Label customeriderror;
    /**
     * Selector for date.
     */

    @FXML
    private DatePicker datePicker;
    /**
     * date error label.
     */
    @FXML
    private Label dateerror;
    /**
     * description field.
     */
    @FXML
    private TextArea descriptionField;
    /**
     * description error label.
     */
    @FXML
    private Label descriptionerror;
    /**
     * End time dropdown.
     */

    @FXML
    private ComboBox<LocalTime> endTimeDropdown;
    /**
     * end time error label.
     */

    @FXML
    private Label endtimeerror;
    /**
     * location filed.
     */

    @FXML
    private TextArea loctionField;
    /**
     * location error label.
     */

    @FXML
    private Label loctionerror;
    /**
     * start time dropdown.
     */

    @FXML
    private ComboBox<LocalTime> startTimeDropdown;
    /**
     * start time error label.
     */

    @FXML
    private Label starttimeerror;
    /**
     * tilte error label.
     */

    @FXML
    private Label tiltleerror;
    /**
     * Title field
     */

    @FXML
    private TextArea titleField;
    /**
     * type dropdown
     */

    @FXML
    private ComboBox<String> typeDropdown;
    /**
     * type error label.
     */

    @FXML
    private Label typeerror;
    /**
     * User dropdown.
     */

    @FXML
    private ComboBox<user> userIdDropdown;
    /**
     * user error label.
     */

    @FXML
    private Label useriderror;
    /**
     * It is used to Creates a new appointment object.
     */
    Appointments appointment = new Appointments(0, "", "", "", "", null, null, null, 0, 0, 0);
    /**
     * It is used to Creates a new customr object.
     */
    Customer Newcustomer = new Customer(0, "", "", "", "", 1, 1);
    /**
     * It is used to Creates a new user object.
     */
    user Newuser = new user(0, UserDB.userN);
    /**
     * It is used to Creates a new contact object.
     */
    Contacts newContact = new Contacts(0, "");

    /**
     * It is used to save and modify the data to database and table.
     * @param event
     * @throws InterruptedException
     */
    @FXML
    void SaveOnAction(ActionEvent event) throws InterruptedException {

        appointment.setApptId(Integer.parseInt(appointmentIdField.getText()));

        try {

            if (titleField.getText().isEmpty() &&
                    descriptionField.getText().isEmpty() &&
                    loctionField.getText().isEmpty() &&
                    typeDropdown.getValue() == null &&
                    datePicker.getValue() == null &&
                    startTimeDropdown.getValue() == null &&
                    endTimeDropdown.getValue() == null &&
                    customerIdDropdown.getValue() == null &&
                    contactDropdown.getValue() == null &&
                    userIdDropdown.getValue() == null) {

                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("");
                alert.setContentText("Please fill in the form before saving");
                alert.showAndWait();
            }
        } catch (NullPointerException e) {
        }
        LocalDate apptDate = LocalDate.now();
        LocalTime st = LocalTime.now();
        LocalTime et = LocalTime.now();
        // It checks the empty title.
        if (titleField.getText().isEmpty() || titleField.getText().equals(" ")) {
            tiltleerror.setText("Please enter a title");
            return;
        } else {
            tiltleerror.setText("");
            appointment.setApptTitle(titleField.getText());
        }
        // It checks the empty description.
        if (descriptionField.getText().isEmpty() || descriptionField.getText().equals(" ")) {
            descriptionerror.setText("Please enter a description");
            return;
        } else {
            descriptionerror.setText("");
            appointment.setApptDescription(descriptionField.getText());
        }
        //It checks the empty location.
        if (loctionField.getText().isEmpty() || loctionField.getText().equals(" ")) {
            loctionerror.setText("Please enter a location");
            return;
        } else {
            loctionerror.setText("");
            appointment.setAppLocation(loctionField.getText());
        }
        // It checks the type value is selected.
        if (typeDropdown.getValue() == null) {
            typeerror.setText("Please choose type of meeting");
            return;
        } else {
            typeerror.setText("");
            appointment.setApptType(typeDropdown.getSelectionModel().getSelectedItem());
        }
        // It checks the date value is selected.
        if (datePicker.getValue() == null) {
            dateerror.setText("Please use date picker to choose a date");
            return;
        }
        // It checks the start time value is selected.
        if (startTimeDropdown.getValue() == null) {
            starttimeerror.setText("Please use drop down to select a start time");
            return;
        }
        // It checks the end time value is selected.
        if (endTimeDropdown.getValue() == null) {
            endtimeerror.setText("Please use drop down to select an end time");
            return;
        } else {
            apptDate = LocalDate.of(datePicker.getValue().getYear(), datePicker.getValue().getMonth(), datePicker.getValue().getDayOfMonth());
            st = LocalTime.of(startTimeDropdown.getSelectionModel().getSelectedItem().getHour(), startTimeDropdown.getSelectionModel().getSelectedItem().getMinute());
            et = LocalTime.of(endTimeDropdown.getSelectionModel().getSelectedItem().getHour(), endTimeDropdown.getSelectionModel().getSelectedItem().getMinute());
            // It is used to set up local time to business start and end hours
            LocalTime bizStart = LocalTime.of(8, 0);
            LocalTime bizEnd = LocalTime.of(22, 0);
            //converting selected date/time to LocalDateTime
            LocalDateTime apptDTST = LocalDateTime.of(apptDate, st);
            LocalDateTime apptDTET = LocalDateTime.of(apptDate, et);
            // It is used to convert business hours to LocalDateTime.
            LocalDateTime bizDTST = LocalDateTime.of(apptDate, bizStart);
            LocalDateTime bizDTET = LocalDateTime.of(apptDate, bizEnd);
            //It used to set a variable to the system default time zone.
            ZoneId systemZoneId = ZoneId.systemDefault();
            //It is used to set a variable to eastern time for business hours.
            ZoneId eST = ZoneId.of("America/New_York");
            //It is used to convert the selected dates and times to ZonedDateTime.
            ZonedDateTime myZDTST = ZonedDateTime.of(apptDTST, systemZoneId);
            ZonedDateTime myZDTET = ZonedDateTime.of(apptDTET, systemZoneId);
            ZonedDateTime easternZDTST = ZonedDateTime.of(bizDTST, eST);
            ZonedDateTime easternZDTET = ZonedDateTime.of(bizDTET, eST);
            //It sets up variable for utc zone.
            ZoneId utcZoneID = ZoneId.of("UTC");
            //It converts ZonedDateTime to UTC.
            ZonedDateTime utcZDTST = ZonedDateTime.ofInstant(myZDTST.toInstant(), utcZoneID);
            ZonedDateTime utcZDTET = ZonedDateTime.ofInstant(myZDTET.toInstant(), utcZoneID);

            //It is used to check if date is not fallen on weekend.
            if (myZDTST.getDayOfWeek() == DayOfWeek.SUNDAY || myZDTST.getDayOfWeek() == DayOfWeek.SATURDAY) {
                dateerror.setText("Please choose a non-weekend day");
                return;
            } else {
                dateerror.setText("");
                appointment.setApptDate(datePicker.getValue());
            }

            if (myZDTST.isBefore(easternZDTST)) {
                starttimeerror.setText("Appointment can't be before business hours 8am-10pm EST");
                return;
            } else if (myZDTST.isAfter(easternZDTET)) {
                starttimeerror.setText("Appointment can't be after business hours 8am-10pm EST");
                return;
            } else if (myZDTET.isAfter(easternZDTET)) {
                endtimeerror.setText("Please choose between business hours 8am-10pm EST");
                return;
            }

            if (startTimeDropdown.getValue() == endTimeDropdown.getValue()) {
                endtimeerror.setText("End time can't be the same as start time");
                return;
            } else if (startTimeDropdown.getValue().isAfter(endTimeDropdown.getValue())) {
                endtimeerror.setText("End time can't be before start time");
                return;
            }
            if (startTimeDropdown.getValue() != null && endTimeDropdown.getValue() != null) {
                // for loop is used to iterate through appointment to get local date time to see if overlapping.
                for (Appointments appt : appointmentDB.getAllAppointments()) {
                    LocalDateTime i = LocalDateTime.of(appt.getApptDate(), appt.getApptStartTime());
                    ZonedDateTime apptZDTST = ZonedDateTime.of(i, systemZoneId);
                    LocalDateTime j = LocalDateTime.of(appt.getApptDate(), appt.getApptEndTime());
                    ZonedDateTime apptZDTET = ZonedDateTime.of(j, systemZoneId);
                    //It checks for overlap.
                    if (myZDTST.equals(apptZDTST) &&
                            (customerIdDropdown.getSelectionModel().getSelectedItem().getId() == appt.getCUSTId()) &&
                            (Integer.parseInt(appointmentIdField.getText()) != appt.getApptId())) {
                        starttimeerror.setText("Overlapping appointment. Please choose another time");
                        return;
                    } else if ((myZDTST.isAfter(apptZDTST) && (myZDTET.isBefore(apptZDTET))) &&
                            (customerIdDropdown.getSelectionModel().getSelectedItem().getId() == appt.getCUSTId()) &&
                            (Integer.parseInt(appointmentIdField.getText()) != appt.getApptId())) {
                        starttimeerror.setText("Overlapping appointment. Please choose another time");
                        return;
                    } else if (myZDTET.equals(apptZDTET) &&
                            (customerIdDropdown.getSelectionModel().getSelectedItem().getId() == appt.getCUSTId()) &&
                            (Integer.parseInt(appointmentIdField.getText()) != appt.getApptId())) {
                        starttimeerror.setText("Overlapping appointment. Please choose another time");
                        return;
                    } else if ((myZDTST.isBefore(apptZDTST) && (myZDTET.isAfter(apptZDTET))) &&
                            (customerIdDropdown.getSelectionModel().getSelectedItem().getId() == appt.getCUSTId()) &&
                            (Integer.parseInt(appointmentIdField.getText()) != appt.getApptId())) {
                        endtimeerror.setText("Overlapping appointment. Please choose another time");
                        return;
                    } else if (((myZDTST.isBefore(apptZDTST)) && (myZDTET.isBefore(apptZDTET)) && (myZDTET.isAfter(apptZDTST))) &&
                            (customerIdDropdown.getSelectionModel().getSelectedItem().getId() == appt.getCUSTId()) &&
                            (Integer.parseInt(appointmentIdField.getText()) != appt.getApptId())) {
                        endtimeerror.setText("Overlapping appointment. Please choose another time");
                        return;
                    } else if ((myZDTST.isAfter(apptZDTST) && myZDTST.isBefore(apptZDTET)) &&
                            (customerIdDropdown.getSelectionModel().getSelectedItem().getId() == appt.getCUSTId()) &&
                            (Integer.parseInt(appointmentIdField.getText()) != appt.getCUSTId())) {
                        endtimeerror.setText("Overlapping appointment. Please choose another time");
                        return;
                    } else if (((myZDTST.equals(apptZDTET) && (Integer.parseInt(appointmentIdField.getText()) != appt.getApptId()) && customerIdDropdown.getSelectionModel().getSelectedItem().getId() == appt.getCUSTId()))
                            || ((myZDTET.equals(apptZDTST)) && (Integer.parseInt(appointmentIdField.getText()) != appt.getApptId()) && customerIdDropdown.getSelectionModel().getSelectedItem().getId() == appt.getCUSTId())) {
                        endtimeerror.setText("Overlapping appointment. Please choose another time");
                        return;
                    } else {
                        starttimeerror.setText("");
                        appointment.setApptStartTime(utcZDTST.toLocalTime());
                        endtimeerror.setText("");
                        appointment.setApptEndTime(utcZDTET.toLocalTime());
                    }
                }
            }
        }

        //It is used to check if no customer was selected.
        if (customerIdDropdown.getValue() == null) {
            customeriderror.setText("Please use drop down to select a customer");
            return;
        } else {
            customeriderror.setText("");
            Newcustomer.setId(customerIdDropdown.getValue().getId());
            appointment.setCUSTId(Newcustomer.getId());
        }
        //It is used to check if no contact was selected.
        if (contactDropdown.getValue() == null) {
            contacterror.setText("Please use drop down to select a contact");
            return;
        } else {
            contacterror.setText("");
            newContact.setContactId(contactDropdown.getValue().getContactId());
            appointment.setContactID(newContact.getContactId());
        }
        //It is used to check if no user was selected.
        if (userIdDropdown.getValue() == null) {
            useriderror.setText("Please use drop down to select a user");
            return;
        } else {
            contacterror.setText("");
            Newuser.setId(userIdDropdown.getValue().getId());
            appointment.setUserID(Newuser.getId());
        }

        try {
            if (titleField.getText().isEmpty() ||
                    descriptionField.getText().isEmpty() ||
                    loctionField.getText().isEmpty() ||
                    typeDropdown.getValue() == null ||
                    datePicker.getValue() == null ||
                    startTimeDropdown.getValue() == null ||
                    endTimeDropdown.getValue() == null ||
                    customerIdDropdown.getValue() == null ||
                    contactDropdown.getValue() == null ||
                    userIdDropdown.getValue() == null) {
                wait();
            }
            else if (!titleField.getText().isEmpty() &&
                    !descriptionField.getText().isEmpty() &&
                    !loctionField.getText().isEmpty() &&
                    typeDropdown.getValue() != null &&
                    datePicker.getValue() != null &&
                    startTimeDropdown.getValue() != null &&
                    endTimeDropdown.getValue() != null &&
                    customerIdDropdown.getValue() != null &&
                    contactDropdown.getValue() != null &&
                    userIdDropdown.getValue() != null) {
                appointmentDB.updateAppointment(appointment);

            }
        }
        catch (IllegalMonitorStateException e) {
            wait();
        }
        Main.stageAndScene(event, "/views/MainMenu.fxml");
    }

    /**
     * The method is used to cancel the user action and take the user back to MainMenu.
     * @param event
     */
    @FXML
    void CancelOnAction(ActionEvent event) {
        Main.stageAndScene(event, "/views/MainMenu.fxml");
    }

    /**
     * It used to Initialize the dropdown and date selector.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ObservableList<Customer> customer = customerDB.getAllCustomer();
        customerIdDropdown.setItems(customer);
        customerIdDropdown.setPromptText("Select a customer");

        ObservableList<Contacts> contact = ContactDB.getAllContacts();
        contactDropdown.setItems(contact);
        contactDropdown.setPromptText("Select a contact");

        ObservableList<user> users = UserDB.getAllUsers();
        userIdDropdown.setItems(users);
        userIdDropdown.setPromptText("Select a user");

        typeDropdown.setItems(Main.typeList());
        typeDropdown.setPromptText("Select meeting type");

        LocalTime start = LocalTime.of(0, 0);
        LocalTime end = LocalTime.of(23, 30);
        while (start.isBefore(end.plusSeconds(1))) {
            startTimeDropdown.getItems().add(start);
            start = start.plusMinutes(15);
            endTimeDropdown.getItems().add(start);
        }
        startTimeDropdown.setPromptText("Select start time");
        endTimeDropdown.setPromptText("Select end time");
    }

    /**
     * It is used to Fill in the modify appointment form with the selected appointment data.
     * @param appointments object for appointment.
     * @param start start time.
     * @param end end time.
     * @param customerID customer id.
     * @param contactName contact name.
     * @param userID user id.
     */
    public void sendAppointment(Appointments appointments, LocalTime start, LocalTime end, int customerID, String contactName, int userID) {

        appointmentIdField.setText(String.valueOf(appointments.getApptId()));
        titleField.setText(appointments.getApptTitle());
        descriptionField.setText(appointments.getApptDescription());
        datePicker.setValue(appointments.getApptDate());
        loctionField.setText(appointments.getAppLocation());
        typeDropdown.setValue(appointments.getApptType());
        startTimeDropdown.setValue(start);
        endTimeDropdown.setValue(end);

        for (Customer cust : customerIdDropdown.getItems()) {
            if (customerID == cust.getId()) {
                customerIdDropdown.setValue(cust);
                break;
            }
        }
        for (Contacts contact : contactDropdown.getItems()) {
            if (contactName.equals(contact.getContactName())) {
                contactDropdown.setValue(contact);
                break;
            }
        }
        for (user user : userIdDropdown.getItems()) {
            if (userID == user.getId()) {
                userIdDropdown.setValue(user);
                break;
            }
        }
    }
}
