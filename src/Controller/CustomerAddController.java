package Controller;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import models.Countries;
import models.Customer;
import models.Divisions;
import models.Location;
import sample.Main;
import utils.divisionDB;
import javafx.scene.control.*;
import utils.customerDB;
import utils.countryDB;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * It is used to add customer to database and customer table.
 * @author Omar Ahmad.
 */
public class CustomerAddController implements Initializable {
    /**
     * Address field.
     */
    @FXML
    private TextArea customerAddressField;
    /**
     * postal code field.
     */
    @FXML
    private TextArea postalCodeField;
    /**
     * customer is disabled.
     */
    @FXML
    private TextArea CustomerIdField;
    /**
     * name field.
     */

    @FXML
    private TextArea CustomerNameField;
    /**
     * phone number field.
     */

    @FXML
    private TextArea CustomerPhoneField;
    /**
     * division dropdown.
     */
    @FXML
    private ComboBox<Location> DivisionDropDown;
    /**
     * address error label.
     */
    @FXML
    private Label addresserror;
    /**
     * country dropdown.
     */

    @FXML
    private ComboBox<Location> countryDropDown;
    /**
     * country error label.
     */

    @FXML
    private Label countryerror;
    /**
     * division error label.
     */

    @FXML
    private Label divisionerror;
    /**
     * name error label.
     */

    @FXML
    private Label nameerror;
    /**
     * phone error label.
     */

    @FXML
    private Label phoneerror;
    /**
     * postal code error label.
     */

    @FXML
    private Label postalerror;
    /**
     * It used to create new division object.
     */
    Divisions Newdivision = new Divisions(0, "", 1);
    /**
     * It is used to create new country object.
     */
    Countries Newcountry = new Countries(0, "");
    /**
     * It is used to create new customer object.
     */
    Customer Newcustomer = new Customer(0, "", "", "", "", 1, 1);
    /**
     * Observable list for divisions.
     */
    ObservableList<Location> Newdivisions = divisionDB.getAllDivisions();

    /**
     * It used to add and customers to database.
     * @param event
     * @throws SQLException
     */
    @FXML
    void SaveCustomerOnAction(ActionEvent event) throws SQLException {

        Newcustomer.setId(10);
        try{

            if (CustomerNameField.getText().isEmpty() &&
                    customerAddressField.getText().isEmpty() &&
                    customerAddressField.getText().isEmpty() &&
                    CustomerPhoneField.getText().isEmpty() &&
                    DivisionDropDown.getValue() == null &&
                    countryDropDown.getValue() == null) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("");
                alert.setContentText("Please fill in the form before saving");
                alert.showAndWait();
            }
        } catch (NullPointerException e) {
        }
        //It checks  if name is empty
        if (CustomerNameField.getText().isEmpty() || CustomerNameField.getText().equals(" ")) {
            nameerror.setText("Please enter a name");
        } else {
            nameerror.setText("");
            Newcustomer.setCUSTName(CustomerNameField.getText());
        }
        // It checks  if address is empty
        if (customerAddressField.getText().isEmpty() || customerAddressField.getText().equals(" ")) {
            addresserror.setText("Please enter an address");
        } else {
            addresserror.setText("");
            Newcustomer.setCUSTAddress(customerAddressField.getText());
        }
        //It checks  if postal code is empty
        if (postalCodeField.getText().isEmpty() || postalCodeField.getText().equals(" ")) {
           postalerror.setText("Please enter a postal code");
        } else {
            postalerror.setText("");
            Newcustomer.setPostalCode(postalCodeField.getText());
        }
        //It checks  if phone is empty
        if (CustomerPhoneField.getText().isEmpty() || CustomerPhoneField.getText().equals(" ")) {
            phoneerror.setText("Please enter a phone number");
        } else {
            phoneerror.setText("");
            Newcustomer.setCUSTPone(CustomerPhoneField.getText());
        }
        if (countryDropDown.getValue() == null) {
            countryerror.setText("Please select a country");
        } else {
            //It checks  if country dropdown value is selected.
            Newcountry.setId(countryDropDown.getValue().getId());
            countryerror.setText("");
            Newcustomer.setCountryId(Newcountry.getId());
        }
        // It checks  if division dropdown value is selected.
        if (DivisionDropDown.getValue() == null) {
            divisionerror.setText("Please select a division");
        } else {
            Newdivision.setId(DivisionDropDown.getValue().getId());
            divisionerror.setText("");
            Newcustomer.setDivisionId(Newdivision.getId());
        }

        try {
            if (CustomerNameField.getText().isEmpty() ||
                    customerAddressField.getText().isEmpty() ||
                    postalCodeField.getText().isEmpty() ||
                    postalCodeField.getText().isEmpty() ||
                    countryDropDown.getValue() == null) {
                wait();
            }else if (!CustomerNameField.getText().isEmpty() &&
                    !customerAddressField.getText().isEmpty() &&
                    !postalCodeField.getText().isEmpty() &&
                    !CustomerPhoneField.getText().isEmpty() &&
                    countryDropDown.getValue() != null &&
                    DivisionDropDown.getValue() != null) {
                customerDB.addCustomer(Newcustomer);
                System.out.println(Newcustomer);
                Main.stageAndScene(event, "/views/CUSTMain.fxml");
            }
        }catch(IllegalMonitorStateException | InterruptedException e) {
        }
    }

    /**
     * It is used to Cancel the user action and take user to customer main.
     * @param event
     */
    @FXML
    void CancelCustomerOnAction(ActionEvent event) {
        Main.stageAndScene(event, "/views/CUSTMain.fxml");
    }

    /**
     * It is used to Initialize the country and division dropdowns.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Location> country = countryDB.getAllCountries();
        countryDropDown.setItems(country);
        countryDropDown.setPromptText("Select Country");

        ObservableList<Location> division = divisionDB.getAllDivisions();
        DivisionDropDown.setItems(division);
        DivisionDropDown.setPromptText("Select First Level Division");
    }

    /**
     * It is used to pick country and filter division dropdown.
     * <b>The Lambda Expression uses selected customer country and sets the filtered divisions for the country</b>This makes the code better by letting the filter to take place at
     * the onAction instead of creating three separate methods in appointmentDB, each filtering
     * for one country, then running each method at the onAction.
     */
    @FXML
    void countryDropDownOnAction() {
        FilteredList<Location> filteredDiv = new FilteredList<>(Newdivisions);
        filteredDiv.setPredicate(row->  {
            int newC = countryDropDown.getSelectionModel().getSelectedItem().getId();
            int newCD = row.getCountry();
            return (newC == newCD);
        });
        DivisionDropDown.setItems(filteredDiv);
    }
    @FXML
    void divisionDropDownOnAction() {
    }
}






