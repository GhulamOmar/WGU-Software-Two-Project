package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import sample.Main;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * It reports class. It allows user to select the type of reports such as report by month and type, report by contact and appointment by customer.
 * @author Omar Ahmad
 */
public class Reports implements Initializable {
    /**
     * It is used to take user to customer report table.
     * @param event
     */

    @FXML
    void CustomerButton(ActionEvent event) {
        Main.stageAndScene(event, "/views/CustomerReport.fxml");

    }

    /**
     * It is used to take user to report summary table and displays appointment report by month and type and appointment report by contact.
     * @param event
     */
    @FXML
    void ReportButton(ActionEvent event) {
        Main.stageAndScene(event, "/views/SummaryReportMain.fxml");

    }

    /**
     * It is used to take user to back to appointment main menu.
     * @param event
     */
    @FXML
    void backOnAction(ActionEvent event) {
        Main.stageAndScene(event, "/views/MainMenu.fxml");
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
