package models;

import java.time.LocalDateTime;
import java.time.Month;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import utils.appointmentDB;

/**
 *Field for Appointment Class
 * @author Omar Ahmad
 */
public class Appointments {
    /**
     * Appointment Id field
     */
    private int ApptId;
    /**
     * Appointment title field
     */

    private String ApptTitle;
    /**
     * Appointment description field
     */

    private String ApptDescription;
    /**
     * Appointment location field
     */

    private String AppLocation;
    /**
     * Appointment type field
     */

    private String ApptType;
    /**
     * Appointment date field
     */
    private LocalDate ApptDate;
    /**
     * Appointment start time field
     */
    private LocalTime ApptStartTime;
    /**
     * Appointment end time field
     */
    private LocalTime ApptEndTime;
    /**
     * Appointment customer id field
     */
    private int CUSTId;
    /**
     * Appointment customer name field
     */
    private String CUSTName;
    /**
     * Appointment contact id field
     */
    private int contactID;
    /**
     * Appointment contact name field
     */
    private String contactName;
    /**
     * Appointment user id field
     */
    private int userID;
    /**
     * Appointment month field
     */
    private Month month;

    /**
     * The constructor is used in appointmentDB to get months.
     *
     * @param apptTitle       appointment id.
     * @param apptDescription appointment description.
     * @param appLocation     appointment location.
     * @param apptType        appointment type.
     * @param apptDate        appointment date.
     * @param apptStartTime   appointment  start time.
     * @param apptEndTime     appointment end time.
     * @param CUSTId          appointment customer id.
     * @param CUSTName        appointment customer name.
     * @param contactID       appointment contact id.
     */

    public Appointments(String apptTitle, String apptDescription, String appLocation, String apptType, LocalDate apptDate, LocalTime apptStartTime, LocalTime apptEndTime, int CUSTId, String CUSTName, int contactID) {
        ApptTitle = apptTitle;
        ApptDescription = apptDescription;
        AppLocation = appLocation;
        ApptType = apptType;
        ApptDate = apptDate;
        ApptStartTime = apptStartTime;
        ApptEndTime = apptEndTime;
        this.CUSTId = CUSTId;
        this.CUSTName = CUSTName;
        this.contactID = contactID;
    }

    /**
     * The constructor is used to create new appointments using customer id and contact id
     *
     * @param apptId          is auto generated.
     * @param apptTitle       appointment title.
     * @param apptDescription appointment description.
     * @param appLocation     appointment location.
     * @param apptType        appointment type.
     * @param apptDate        appointment date.
     * @param apptStartTime   appointment start time.
     * @param apptEndTime     appointment end time.
     * @param CUSTId          appointment customer id.
     * @param contactID       appointment contact id.
     * @param userID          appointment user id.
     */

    public Appointments(int apptId, String apptTitle, String apptDescription, String appLocation, String apptType, LocalDate apptDate, LocalTime apptStartTime, LocalTime apptEndTime, int CUSTId, int contactID, int userID) {
        ApptId = apptId;
        ApptTitle = apptTitle;
        ApptDescription = apptDescription;
        AppLocation = appLocation;
        ApptType = apptType;
        ApptDate = apptDate;
        ApptStartTime = apptStartTime;
        ApptEndTime = apptEndTime;
        this.CUSTId = CUSTId;
        this.contactID = contactID;
        this.userID = userID;

    }

    /**
     * the constructor is used for planning type.
     *
     * @param apptType appointment type
     * @param month    appointment month
     */

    public Appointments(String apptType, Month month) {
        ApptType = apptType;
        this.month = month;
    }

    /**
     * The constructor is used for new appointments with contact names.
     *
     * @param apptId          appointment id
     * @param apptTitle       appointment title
     * @param apptDescription appointment description
     * @param appLocation     appointment location
     * @param apptType        appointment type
     * @param apptDate        appointment date
     * @param apptStartTime   appointment start time
     * @param apptEndTime     appointment end time
     * @param CUSTId          appointment customer id
     * @param contactName     appointment customer name
     * @param userID          appointment user id
     */
    public Appointments(int apptId, String apptTitle, String apptDescription, String appLocation, String apptType, LocalDate apptDate, LocalTime apptStartTime, LocalTime apptEndTime, int CUSTId, String contactName, int userID) {
        ApptId = apptId;
        ApptTitle = apptTitle;
        ApptDescription = apptDescription;
        AppLocation = appLocation;
        ApptType = apptType;
        ApptDate = apptDate;
        ApptStartTime = apptStartTime;
        ApptEndTime = apptEndTime;
        this.CUSTId = CUSTId;
        this.contactName = contactName;
        this.userID = userID;
    }

    /**
     * The constructor is used to get appointment by contact name.
     *
     * @param apptId          appointment id
     * @param apptTitle       appointment title
     * @param apptDescription appointment description
     * @param apptType        appointment type
     * @param apptDate        appointment date
     * @param apptStartTime   appointment start time
     * @param apptEndTime     appointment end time
     * @param customerID      appointment customer id
     * @param CUSTId          appointment user id
     */

    public Appointments(int apptId, String apptTitle, String apptDescription, String apptType, LocalDate apptDate, LocalTime apptStartTime, LocalTime apptEndTime, int customerID, int CUSTId) {
        ApptId = apptId;
        ApptTitle = apptTitle;
        ApptDescription = apptDescription;
        ApptType = apptType;
        ApptDate = apptDate;
        ApptStartTime = apptStartTime;
        ApptEndTime = apptEndTime;
        this.CUSTId = CUSTId;
    }

    /**
     * The constructor is used for report summary to get the report view table.
     *
     * @param apptId          appointment id
     * @param apptTitle       appointment title
     * @param apptDescription appointment description
     * @param apptType        appointment type
     * @param apptDate        appointment date
     * @param apptStartTime   appointment start time
     * @param apptEndTime     appointment end time
     * @param contactName     appointment contact name
     */
    public Appointments(int apptId, String apptTitle, String apptDescription, String apptType, LocalDate apptDate, LocalTime apptStartTime, LocalTime apptEndTime, String contactName) {
        ApptId = apptId;
        ApptTitle = apptTitle;
        ApptDescription = apptDescription;
        ApptType = apptType;
        ApptDate = apptDate;
        ApptStartTime = apptStartTime;
        ApptEndTime = apptEndTime;
        this.contactName = contactName;
    }

    public Appointments() {

    }

    /**
     * Getter and setter for appointment id.
     *
     * @return appointment id.
     */

    public int getApptId() {
        return ApptId;
    }

    public void setApptId(int apptId) {
        ApptId = apptId;

    }

    /**
     * Getter and setter for appointment title.
     *
     * @return appointment title.
     */

    public String getApptTitle() {
        return ApptTitle;
    }

    public void setApptTitle(String apptTitle) {
        ApptTitle = apptTitle;

    }

    /**
     * Getter and setter for appointment description.
     *
     * @return appointment description.
     */
    public String getApptDescription() {
        return ApptDescription;
    }

    public void setApptDescription(String apptDescription) {
        ApptDescription = apptDescription;

    }

    /**
     * Getter and setter for appointment location.
     *
     * @return appointment location.
     */
    public String getAppLocation() {
        return AppLocation;
    }

    public void setAppLocation(String appLocation) {
        AppLocation = appLocation;

    }

    /**
     * Getter and setter for appointment type.
     *
     * @return appointment type.
     */
    public String getApptType() {
        return ApptType;
    }

    public void setApptType(String apptType) {
        ApptType = apptType;

    }

    /**
     * Getter and setter for appointment date.
     *
     * @return appointment type.
     */
    public LocalDate getApptDate() {
        return ApptDate;
    }

    public void setApptDate(LocalDate apptDate) {
        ApptDate = apptDate;

    }

    /**
     * Getter and setter for appointment start time.
     *
     * @return appointment start time.
     */

    public LocalTime getApptStartTime() {
        return ApptStartTime;
    }

    public void setApptStartTime(LocalTime apptStartTime) {
        ApptStartTime = apptStartTime;

    }

    /**
     * Getter and setter for appointment end time.
     *
     * @return appointment end time.
     */

    public LocalTime getApptEndTime() {
        return ApptEndTime;
    }

    public void setApptEndTime(LocalTime apptEndTime) {
        ApptEndTime = apptEndTime;

    }

    /**
     * Getter and setter for appointment customer id.
     *
     * @return appointment customer id.
     */

    public int getCUSTId() {
        return CUSTId;
    }

    public void setCUSTId(int CUSTId) {
        this.CUSTId = CUSTId;

    }

    /**
     * Getter and setter for appointment customer name.
     *
     * @return appointment customer name.
     */
    public String getCUSTName() {
        return CUSTName;
    }

    public void setCUSTName(String CUSTName) {
        this.CUSTName = CUSTName;

    }

    /**
     * Getter and setter for appointment contact id.
     *
     * @return contact id.
     */
    public int getContactID() {
        return contactID;
    }

    public void setContactID(int contactID) {
        this.contactID = contactID;

    }

    /**
     * Getter and setter for appointment contact name.
     *
     * @return contact name.
     */
    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;

    }

    /**
     * Getter and setter for appointment user id.
     *
     * @return user id.
     */
    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;

    }

    /**
     * Getter and setter for appointment month.
     *
     * @return month.
     */

    public Month getMonth() {
        return month;
    }

    public void setMonth(Month month) {
        this.month = month;

    }

    /**
     * This is used to delete appointments from appointment table.
     *
     * @param appointment object for appointment.
     * @return true
     * @throws SQLException is used for data access error.
     */

    public static boolean deleteAppointment(Appointments appointment) throws SQLException {
        appointmentDB.deleteAppointment(appointment.getApptId());
        return true;
    }


    public boolean checkAppointmentOverlap(List<Appointments> existingAppointments, Appointments appointment, LocalDateTime myZDTST, LocalDateTime myZDTET) {
        for (Appointments existingAppointment : existingAppointments) {
            // Check for overlap between this appointment and existingAppointment
            LocalDateTime existingApptStartTime = LocalDateTime.of(existingAppointment.getApptDate(), existingAppointment.getApptStartTime());
            LocalDateTime existingApptEndTime = LocalDateTime.of(existingAppointment.getApptDate(), existingAppointment.getApptEndTime());

            if ((myZDTST.isAfter(existingApptStartTime) && myZDTST.isBefore(existingApptEndTime)) ||
                    (myZDTET.isAfter(existingApptStartTime) && myZDTET.isBefore(existingApptEndTime)) ||
                    (myZDTST.isBefore(existingApptStartTime) && myZDTET.isAfter(existingApptEndTime))) {
                return true; // Overlap found
            }
        }

        return false; // No overlap found
    }

    // Other class members and methods
}








