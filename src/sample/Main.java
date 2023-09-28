package sample;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import models.Appointments;
import utils.QueryManager;
import utils.appointmentDB;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.TimeZone;
import utils.Database;

/**
 * This is the main class for Appointment Scheduler application.
 * @author  Omar Ahmad.
 */
public class Main extends Application {
    static Stage stage;

    /**
     * Starting point for all JavaFX applications.
     * @param stage setting the application scene.
     */
    @Override
    public void start(Stage stage){

        this.stage = stage;

        Locale.setDefault(Locale.getDefault());
        ResourceBundle rb = ResourceBundle.getBundle("sample/properties");
        Group root = new Group();
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Login.fxml"));
            loader.setResources(rb);
            Parent roots = loader.load();
            root.getChildren().add(roots);
            stage.setScene(new Scene(root,  450, 500 ));
            stage.setX(100);
            stage.setY(0);
            stage.show(); }
        catch(NullPointerException | IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Starting point of scheduler application. Calls the database start connection method
     * @param args
     * @throws SQLException
     * @throws NullPointerException
     * @throws IOException
     */
    public static void main(String[] args) throws SQLException, NullPointerException, IOException {


        Database.startConnection();  // controls the log in.
        Connection conn = Database.getConnection();

       QueryManager.setStatement(conn); // creating statement object
        launch(args);


        Database.closeConnection(); //closing connection.
    }

    /**
     * This method is used to change scenes/screens.
     * @param event
     * @param string
     */
    public static void stageAndScene(ActionEvent event, String string){
        Stage stage;
        Parent scene = null;
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        try {
            scene = FXMLLoader.load(Main.class.getResource(string));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * This method is used to log the user login activity in the log_activity.txt file.
     * @param inItem
     * @param inDay
     * @param inTime
     * @throws IOException if an input or output exception occurred
     */
    public static void logUser(String inItem, String inDay, String inTime) throws IOException {

        //The variable for filename and item.
        String filename = "login_activity.txt", item, day, time;

        //creating file writer object.
        FileWriter fwriter = new FileWriter(filename, true);
        // Used to create and open file.
        PrintWriter outputFile = new PrintWriter(fwriter);
        item = inItem;
        day = inDay;
        time = inTime;
        outputFile.println(item +" "+ day + " " + time);

        //It closes file.
        outputFile.close();
        System.out.println("file written");
    }
    public static Stage getStage() {
        return stage;
    }

    /**
     * This method is used to create an upcoming appointment alert when user logs in.
     */
    public static void getAlert() {

        Alert alert;
        int counter = 0;
        for(Appointments appt :  appointmentDB.getAllAppointments()) {
            LocalTime st = appt.getApptStartTime();
            LocalTime ct = LocalTime.now();

            long timeDiff = ChronoUnit.MINUTES.between(ct, st);
            long interval = timeDiff ;

            if (interval > 0 && interval <= 15 && LocalDate.now().equals( appt.getApptDate())) {
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Appointment Reminder");
                alert.setContentText("Appointment ID: " + appt.getApptId() + " " + "on " + appt.getApptDate() + " at " + appt.getApptStartTime());
                alert.showAndWait();
                counter ++;
            }
        }
        if (counter == 0) {
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Appointment Reminder");
            alert.setContentText("There is no upcoming appointments");
            alert.showAndWait();
        }
    }

    /**
     * It is used to Define  the type list for Dropdown.
     * @return type
     */
    public static ObservableList<String> typeList () {
        ObservableList<String> type = FXCollections.observableArrayList();
        type.add("Planning Session");
        type.add("De-Briefing");
        return type;
    }

    /**
     * This is used to get UTC start and end time from appointmentDB.getAllAppointments and return the local time.
     * @param utcT
     * @param utcD
     * @return
     */
    public static LocalTime getLocalFromUTC(LocalTime utcT, LocalDate utcD) {

        ZoneId localZoneID = ZoneId.of(TimeZone.getDefault().getID());
        ZoneId utcZoneId = ZoneId.of("UTC");
        ZonedDateTime utcZDT = ZonedDateTime.of(utcD,utcT, utcZoneId);
        Instant localToUTCInstant = utcZDT.toInstant();
        ZonedDateTime utcToLocalZDT = localToUTCInstant.atZone(localZoneID);
        LocalTime utcToLocal = utcToLocalZDT.toLocalTime();
        return utcToLocal;
    }

}