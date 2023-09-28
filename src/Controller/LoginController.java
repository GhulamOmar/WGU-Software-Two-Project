package Controller;

import com.mysql.cj.exceptions.CJCommunicationsException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import sample.Main;
import utils.UserDB;
import java.io.EOFException;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 *It is used to allow the user to log in application by checking the correct credentials.
 * @author Omar Ahmad
 */
public class LoginController implements Initializable {
    /**
     * password field.
     */
    @FXML
    private TextArea PasswordText;
    /**
     * log in label.
     */
    @FXML
    private Label LoginLabel;
    /**
     * user name field.
     */
    @FXML
    private TextArea UserText;
    /**
     * language label.
     */
    @FXML
    private Label langaugelable;
    /**
     * local zone field.
     */
    @FXML
    private Label ZoneIdLabel;
    /**
     * user label.
     */
    @FXML
    private Label UserNameLabel;
    /**
     * password label.
     */
    @FXML
    private Label UserPasswordLabel;
    /**
     * invalid log in label.
     */

    @FXML
    private Label invalidLoginLabel;
    /**
     * log in label.
     */
    @FXML
    private Button loginButton;
    ResourceBundle rb;

    /**
     * It is used to Initialize the login screen elements.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.rb = rb;
        Locale currSystem = Locale.getDefault();
        rb = ResourceBundle.getBundle("sample/properties", currSystem);
        System.out.println(Locale.getDefault());
        LoginLabel.setText(rb.getString("login"));
        Main.getStage().setTitle(rb.getString("title"));
        UserNameLabel.setText(rb.getString("username"));
        UserPasswordLabel.setText(rb.getString("password"));
        loginButton.setText(rb.getString("login"));
        langaugelable.setText(rb.getString("language"));
        ZoneIdLabel.setText(rb.getString("Location") + ZoneId.systemDefault() );
    }

    /**
     * It is used for  Login validation.It alerts if user name and password are not filled out, or wrong user and password entered.
     * @param event
     * @throws IOException
     */
    @FXML
    void loginButtonPushed(ActionEvent event) throws IOException {
        try {

            if (UserText.getText().length() == 0 && PasswordText.getText().length() == 0) {
                invalidLoginLabel.setText(rb.getString("NoUserOrPass"));
                Main.logUser("Unsuccessful log in", LocalDate.now().toString(), LocalTime.now().toString());
            }
            else if(UserText.getText().length() == 0) {
                invalidLoginLabel.setText(rb.getString("NoUsername"));
                Main.logUser("Unsuccessful log in attempt", LocalDate.now().toString(), LocalTime.now().toString());
            }
            else if( PasswordText.getText().length() == 0) {
                invalidLoginLabel.setText(rb.getString("NoPassword"));
                Main.logUser("Unsuccessful log", LocalDate.now().toString(), LocalTime.now().toString());
            }

            else {
                String user = UserText.getText();
                String pass = PasswordText.getText();
                if(UserDB.validateLogin(user, pass)) {
                    Main.logUser("Successful log in attempt by: " + UserDB.userN, LocalDate.now().toString(), LocalTime.now().toString());
                    Main.stageAndScene(event, "/views/MainMenu.fxml");
                    Main.getAlert();
                }
                else {
                    invalidLoginLabel.setText(rb.getString("incorrect"));
                    Main.logUser("Unsuccessful log in attempt", LocalDate.now().toString(), LocalTime.now().toString());
                }
            }
        }catch (EOFException | CJCommunicationsException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Timed out");
            alert.setContentText("Timed out. Please close and reopen application");
            alert.showAndWait();
        }
    }
}


