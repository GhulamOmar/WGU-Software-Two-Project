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
import utils.customerDB;
import java.net.URL;
import java.sql.SQLException;
import java.time.*;
import java.util.ResourceBundle;
import utils.appointmentDB;

/**
 *This for adding an appointment. All error labels prompt user to fill the text field left blank.
 * @author Omar Ahmad
 */

public class AddAppointmentController implements Initializable {
    /**
     * Appointment date picker field.
     */

    @FXML
    private DatePicker AppointmentDateSelect;
    /**
     * Appointment end time field.
     */

    @FXML
    private ComboBox<LocalTime> AppointmentEndTime;
    /**
     * Appointment ID is disabled.
     */
    @FXML
    private TextArea AppointmentIdField;
    /**
     * Appointment start time field.
     */
    @FXML
    private ComboBox<LocalTime> AppointmentStartTime;
    /**
     * Appointment Contact dropdown.
     */
    @FXML
    private ComboBox<Contacts> ContactDropDown;
    /**
     * Appointment customer ID dropdown.
     */
    @FXML
    private ComboBox<Customer> CustomerIDDropDown;
    /**
     * Appointment discretion field.
     */
    @FXML
    private TextArea DescriptionField;
    /**
     * Appointment Location field.
     */
    @FXML
    private TextArea LocationField;
    /**
     * Appointment title field.
     */
    @FXML
    private TextArea TitleField;
    /**
     * Appointment type dropdown.
     */
    @FXML
    private ComboBox<String> TypeDropDown;
    /**
     * Appointment user id dropdown.
     */
    @FXML
    private ComboBox<user> UserIDDropDown;
    /**
     * contact error label.
     */
    @FXML
    private Label contacterror;
    /**
     * description error label.
     */
    @FXML
    private Label descriptionerror;
    /**
     * date error label.
     */
    @FXML
    private Label appointmentdateerror;
    /**
     * end time error label.
     */

    @FXML
    private Label endtimeerror;
    /**
     * location error label.
     */
    @FXML
    private Label loctionerrror;
    /**
     * start time error label.
     */
    @FXML
    private Label starttimeerror;
    /**
     * title error label.
     */
    @FXML
    private Label titleerror;
    /**
     * type error label.
     */
    @FXML
    private Label typeerror;
    /**
     * user error label.
     */
    @FXML
    private Label useriderror;
    /**
     * This is used to create new appointment objects.
     */
        Appointments appointment = new Appointments(0, "", "", "", "", null, null, null, 0, 0, 0);
    /**
     * This is used to create new Customer objects.
     */
        Customer newCust = new Customer(0, "", "", "", "", 1, 1);

        /**
         * This is used for creating new user objects.
         */
        user Newuser = new user(0, UserDB.userN);
    /**
     * This is used to create new contact objects.
     */
    Contacts newContact = new Contacts(0, "");

    /**
     * This method cancel the action and take the user back to MainMenu page.
     * @param event
     */
    @FXML
    void CancelOnAction(ActionEvent event) {
        Main.stageAndScene(event, "/views/MainMenu.fxml");
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Customer> customer = customerDB.getAllCustomer();
            CustomerIDDropDown.setItems(customer);
            CustomerIDDropDown.setPromptText("Select a customer");

            ObservableList<Contacts> contact = ContactDB.getAllContacts();
            ContactDropDown.setItems(contact);
            ContactDropDown.setPromptText("Select a contact");

            ObservableList<user> users = UserDB.getAllUsers();
            UserIDDropDown.setItems(users);
            UserIDDropDown.setPromptText("Select a user");

            TypeDropDown.setItems(Main.typeList());
            TypeDropDown.setPromptText("Select meeting type");
            // initializing the  start and end time dropdown.
            LocalTime start = LocalTime.of(0, 0);
            LocalTime end = LocalTime.of(23, 30);
            while (start.isBefore(end.plusSeconds(1))) {
                AppointmentStartTime.getItems().add(start);
                start = start.plusMinutes(15);
                AppointmentEndTime.getItems().add(start);
            }
            AppointmentStartTime.setPromptText("Select start time");
            AppointmentEndTime.setPromptText("Select end time");
        }

    /**
     * This used to Saves the data to the database and table.
     * @param event
     * @throws SQLException
     * @throws InterruptedException
     * @throws IllegalMonitorStateException
     */
        @FXML
        void SaveAppointmentOnAction(ActionEvent event) throws SQLException, InterruptedException, IllegalMonitorStateException{
            // This is used for initial errors of empty form.

            if (TitleField.getText().isEmpty() &&
                    DescriptionField.getText().isEmpty() &&
                    LocationField.getText().isEmpty() &&
                    TypeDropDown.getValue() == null &&
                    AppointmentDateSelect.getValue() == null &&
                    AppointmentStartTime.getValue() == null &&
                    AppointmentEndTime.getValue() == null &&
                    CustomerIDDropDown.getValue() == null &&
                    ContactDropDown.getValue() == null &&
                    UserIDDropDown.getValue() == null) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("");
                alert.setContentText("Please fill in the form before saving");
                alert.showAndWait();
            }

            LocalDate apptDate = LocalDate.now();
            LocalTime st = LocalTime.now();
            LocalTime et = LocalTime.now();
            // The random int is used to set up Appointment id.
            appointment.setApptId(10);

            // It is used to check if the title is not filled.
            if (TitleField.getText().isEmpty() || TitleField.getText().equals(" ")) {
                titleerror.setText("Please enter a title");
            } else {
                titleerror.setText("");
                appointment.setApptTitle(TitleField.getText()); //setting title
            }
            // It is used to check if the description is not filled.
            if (DescriptionField.getText().isEmpty()) //error if description is empty
                descriptionerror.setText("Please enter a description");
            else {
                descriptionerror.setText("");
                appointment.setApptDescription(DescriptionField.getText());
            }
            // It is used to check if the location is not filled.
            if (LocationField.getText().isEmpty())
                loctionerrror.setText("Please enter a location");
            else {
                loctionerrror.setText("");
                appointment.setAppLocation(LocationField.getText());
            }
            // It is used to check if the type is not filled.
            if (TypeDropDown.getValue() == null)  //error if no type selection is made
               typeerror.setText("Please choose type of meeting");
            else {
                typeerror.setText("");
                appointment.setApptType(TypeDropDown.getSelectionModel().getSelectedItem());
            }
            // It is used to check if the the date is selected.
            if (AppointmentDateSelect.getValue() == null) {
                appointmentdateerror.setText("Please use date picker to choose a date");
            }
            //It is used to check if the the start time is selected.
            if (AppointmentStartTime.getValue() == null) {
                starttimeerror.setText("Please use drop down to select a start time");
            }
            // It is used to check if the the end time is selected.
            if (AppointmentEndTime.getValue() == null) {
                endtimeerror.setText("Please use drop down to select an end time");
            } else {
                apptDate = LocalDate.of(AppointmentDateSelect.getValue().getYear(), AppointmentDateSelect.getValue().getMonth(), AppointmentDateSelect.getValue().getDayOfMonth());
                st = LocalTime.of(AppointmentStartTime.getSelectionModel().getSelectedItem().getHour(), AppointmentStartTime.getSelectionModel().getSelectedItem().getMinute());
                et = LocalTime.of(AppointmentEndTime.getSelectionModel().getSelectedItem().getHour(), AppointmentEndTime.getSelectionModel().getSelectedItem().getMinute());
                // It is used to set local time for business start and end.
                LocalTime bizStart = LocalTime.of(8, 0);
                LocalTime bizEnd = LocalTime.of(22, 0);
                //It is used to convert selected date or time to LocalDateTime.
                LocalDateTime apptDTST = LocalDateTime.of(apptDate, st);
                LocalDateTime apptDTET = LocalDateTime.of(apptDate, et);
                // It is used to convert  business hours to LocalDateTime.
                LocalDateTime bizDTST = LocalDateTime.of(apptDate, bizStart);
                LocalDateTime bizDTET = LocalDateTime.of(apptDate, bizEnd);
                // It sets the variable to the system default zone.
                ZoneId systemZoneId = ZoneId.systemDefault();
                // It sets the variable to eastern time for business hours.
                ZoneId eST = ZoneId.of("America/New_York");
                // It is used to convert the user selected dates, times, and business hours to ZonedDateTime.
                ZonedDateTime myZDTST = ZonedDateTime.of(apptDTST, systemZoneId);
                ZonedDateTime myZDTET = ZonedDateTime.of(apptDTET, systemZoneId);
                ZonedDateTime easternZDTST = ZonedDateTime.of(bizDTST, eST);
                ZonedDateTime easternZDTET = ZonedDateTime.of(bizDTET, eST);
                // It is used to set up  variable for utc zone
                ZoneId utcZoneID = ZoneId.of("UTC");
                // It is used to convert ZonedDateTime to UTC.
                ZonedDateTime utcZDTST = ZonedDateTime.ofInstant(myZDTST.toInstant(), utcZoneID);
                ZonedDateTime utcZDTET = ZonedDateTime.ofInstant(myZDTET.toInstant(), utcZoneID);
                // It is used to check if the date is falling on weekends.
                if (myZDTST.getDayOfWeek() == DayOfWeek.SUNDAY || myZDTST.getDayOfWeek() == DayOfWeek.SATURDAY) {
                    appointmentdateerror.setText("Please choose a non-weekend day");
                    return;
                } else {
                    appointmentdateerror.setText("");
                    appointment.setApptDate(AppointmentDateSelect.getValue());
                }

                if(myZDTST.isBefore(easternZDTST))  {
                    starttimeerror.setText("Appointment can't be before business hours 8am-10pm EST");
                    return;
                } else if(myZDTST.isAfter(easternZDTET)) {
                    starttimeerror.setText("Appointment can't be after business hours 8am-10pm EST");
                    return;
                } else if (myZDTET.isAfter(easternZDTET)) {
                    endtimeerror.setText("Please choose between business hours 8am-10pm EST");
                    return;
                }

                if (AppointmentStartTime.getValue() == AppointmentEndTime.getValue()) {
                    endtimeerror.setText("End time can't be the same as start time");
                    return;
                } else if (AppointmentStartTime.getValue().isAfter(AppointmentEndTime.getValue())) {
                    endtimeerror.setText("End time can't be before start time");
                    return;
                }
                if(AppointmentStartTime.getValue() != null && AppointmentEndTime.getValue() !=null) {
                    // The for loop is used  to iterate through appointment to get local date time to see if overlapping.
                    for (Appointments appt : appointmentDB.getAllAppointments()) {
                        LocalDateTime i = LocalDateTime.of(appt.getApptDate(), appt.getApptStartTime());
                        ZonedDateTime apptZDTST = ZonedDateTime.of(i, systemZoneId);
                        LocalDateTime j = LocalDateTime.of(appt.getApptDate(), appt.getApptEndTime());
                        ZonedDateTime apptZDTET = ZonedDateTime.of(j, systemZoneId);
                        // It is used to check the overlap.
                        if (myZDTST.equals(apptZDTST) && (CustomerIDDropDown.getSelectionModel().getSelectedItem().getId() == appt.getCUSTId())) {
                            starttimeerror.setText("Overlapping appointment. Please choose another time");
                            return;
                        } else if ((myZDTST.isAfter(apptZDTST) && (myZDTET.isBefore(apptZDTET))) && (CustomerIDDropDown.getSelectionModel().getSelectedItem().getId() == appt.getCUSTId())) {
                            starttimeerror.setText("Overlapping appointment. Please choose another time");
                            return;
                        } else if (myZDTET.equals(apptZDTET) && (CustomerIDDropDown.getSelectionModel().getSelectedItem().getId() == appt.getCUSTId())) {
                            starttimeerror.setText("Overlapping appointment. Please choose another time");
                            return;
                        } else if ((myZDTST.isBefore(apptZDTST) && (myZDTET.isAfter(apptZDTET))) && (CustomerIDDropDown.getSelectionModel().getSelectedItem().getId() == appt.getCUSTId())) {
                            endtimeerror.setText("Overlapping appointment. Please choose another time");
                            return;
                        } else if(((myZDTST.isBefore(apptZDTST)) && (myZDTET.isBefore(apptZDTET)) && (myZDTET.isAfter(apptZDTST))) &&
                                (CustomerIDDropDown.getSelectionModel().getSelectedItem().getId() == appt.getCUSTId())) {
                            endtimeerror.setText("Overlapping appointment. Please choose another time");
                            return;
                        } else if((myZDTST.isAfter(apptZDTST) && myZDTST.isBefore(apptZDTET)) &&
                                (CustomerIDDropDown.getSelectionModel().getSelectedItem().getId() == appt.getCUSTId())) {
                            endtimeerror.setText("Overlapping appointment. Please choose another time");
                            return;
                        }
                        else if((myZDTST.equals(apptZDTET) || myZDTET.equals(apptZDTST)) &&
                                (CustomerIDDropDown.getSelectionModel().getSelectedItem().getId() == appt.getCUSTId())) {
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
            // It is used to make suer the customer is selected.
            if (CustomerIDDropDown.getValue() == null) {
                contacterror.setText("Please use drop down to select a customer");
            } else {
                contacterror.setText("");
                newCust.setId(CustomerIDDropDown.getValue().getId());
                appointment.setCUSTId(newCust.getId());
            }
            // It is used to make suer the contact is selected.
            if (ContactDropDown.getValue() == null) {
                contacterror.setText("Please use drop down to select a contact");
            } else {
                contacterror.setText("");
                newContact.setContactId(ContactDropDown.getValue().getContactId());
                appointment.setContactID(newContact.getContactId());
            }
            //It is used to make suer the user is selected.
            if (UserIDDropDown.getValue() == null) {
                useriderror.setText("Please use drop down to select a user");
            } else {
                contacterror.setText("");
                Newuser.setId(UserIDDropDown.getValue().getId());
                appointment.setUserID(Newuser.getId());
            }
            // It is used to check the null value and save it to the table.
            try {
                if (TitleField.getText().isEmpty() ||
                        DescriptionField.getText().isEmpty() ||
                       LocationField.getText().isEmpty() ||
                        TypeDropDown.getValue() == null ||
                        AppointmentDateSelect.getValue() == null ||
                        AppointmentStartTime.getValue() == null ||
                        AppointmentEndTime.getValue() == null ||
                        CustomerIDDropDown.getValue() == null ||
                        ContactDropDown.getValue() == null ||
                        UserIDDropDown.getValue() == null) {
                    wait();
                }else if (!TitleField.getText().isEmpty() &&
                        !DescriptionField.getText().isEmpty() &&
                        !LocationField.getText().isEmpty() &&
                        TypeDropDown.getValue() != null &&
                        AppointmentDateSelect.getValue() != null &&
                        AppointmentStartTime.getValue() != null &&
                        AppointmentEndTime.getValue() != null &&
                        CustomerIDDropDown.getValue() != null &&
                        ContactDropDown.getValue() != null &&
                        UserIDDropDown.getValue() != null) {
                    appointmentDB.addAppointments(appointment);
                    System.out.println(appointment);
                    System.out.println(appointmentDB.getAllAppointments());
                    Main.stageAndScene(event, "/views/MainMenu.fxml");
                }
            }
            // The catch is used for wait method and checks if there is empty value.
            catch (IllegalMonitorStateException e) {
            }
        }
    }
