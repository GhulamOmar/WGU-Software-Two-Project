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
import utils.customerDB;
import utils.countryDB;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * It is used to modify the customer by saving the changes to database and table.
 * @author Omar Ahmad
 */
public class ModifyCustomerController implements Initializable {
    /**
     * address label.
     */
    @FXML
    private Label AddressError;
    /**
     * country dropdown.
     */
    @FXML
    private ComboBox<Location> CountryDropDown;
    /**
     * country error label.
     */

    @FXML
    private Label CountryError;
    /**
     * phone field.
     */
    @FXML
    private TextArea CountryPhoneField;
    /**
     * address field.
     */

    @FXML
    private TextArea CustomerAddressField;
    /**
     * id field.
     */

    @FXML
    private TextArea CustomerIdField;
    /**
     * name field.
     */

    @FXML
    private TextArea CustomerNameField;
    /**
     * postal code field.
     */

    @FXML
    private TextArea CustomerZipField;
    /**
     * division field.
     */
    @FXML
    private ComboBox<Location> DivisionDropDown;
    /**
     * division error label.
     */
    @FXML
    private Label DivisionError;
    /**
     * name error label.
     */

    @FXML
    private Label NameError;
    /**
     * phone error label.
     */

    @FXML
    private Label PhoneError;
    /**
     * postal code error field.
     */
    @FXML
    private Label PostalError;
    /**
     * It used to create new country.
     */

    Countries new_country = new Countries(0, "");
    /**
     * It used to create new division.
     */
    Divisions new_division = new Divisions(0, "", 1);
    /**
     * It used to create new costumer.
     */
    Customer new_customer = new Customer(0, "", "", "", "", 0, 1);
    /**
     * observable list for division.
     */
    ObservableList<Location> divisions = divisionDB.getAllDivisions();

    /**
     * It is used to save new customer to the database and table.
     * @param event
     */

    @FXML
    void SaveCustomerOnAction(ActionEvent event) {
        new_customer.setId(Integer.parseInt(CustomerIdField.getText()));
        try {
            if (CustomerNameField.getText().isEmpty() &&
                    CustomerAddressField.getText().isEmpty() &&
                    CustomerZipField.getText().isEmpty() &&
                    CountryPhoneField.getText().isEmpty() &&
                    DivisionDropDown.getValue() == null &&
                    CountryDropDown.getValue() == null) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("");
                alert.setContentText("The form needs to be filled  before saving");
                alert.showAndWait();
            }
        } catch (NullPointerException e) {
        }
        // It checks the name field is filled out.
        if (CustomerNameField.getText().isEmpty() || CustomerNameField.getText().equals(" ")) {
            NameError.setText("Please enter a name");
            return;
        } else {
            NameError.setText("");
            new_customer.setCUSTName(CustomerNameField.getText());
        }
        //It checks the address field is filled out.
        if (CustomerAddressField.getText().isEmpty() || CustomerNameField.getText().equals(" ")) {
            AddressError.setText("Please enter an address");
            return;
        } else {
            AddressError.setText("");
            new_customer.setCUSTAddress(CustomerNameField.getText());
        }
        // It checks the postal code field is filled out.
        if (CustomerZipField.getText().isEmpty() || CustomerZipField.getText().equals(" ")) {
            PostalError.setText("Please enter a postal code");
            return;
        } else {
            PostalError.setText("");
            new_customer.setPostalCode(CustomerZipField.getText());
        }
        //It checks the phone field is filled out.
        if (CountryPhoneField.getText().isEmpty() || CountryPhoneField.getText().equals(" ")) {
            PhoneError.setText("Please enter a phone number");
            return;
        } else {
            PhoneError.setText("");
            new_customer.setCUSTPone(CountryPhoneField.getText());
        }
        // It checks the country dropdown value is selected
        if (CountryDropDown.getValue() == null) { //error if country is null
            CountryError.setText("Please select a country");
            return;
        } else {
            new_country.setId(CountryDropDown.getValue().getId());
            CountryError.setText("");
            new_customer.setCountryId(new_country.getId());
        }
        //It checks the division dropdown value is selected
        if (DivisionDropDown.getValue() == null) {
            DivisionError.setText("Please select a division");
            return;
        } else {
            new_division.setId(DivisionDropDown.getValue().getId());
            DivisionError.setText("");
            new_customer.setDivisionId(new_division.getId());
        }

        try {
            if (CustomerNameField.getText().isEmpty() ||
                    CustomerAddressField.getText().isEmpty() ||
                    CustomerZipField.getText().isEmpty() ||
                    CountryPhoneField.getText().isEmpty() ||
                    CountryDropDown.getValue() == null ||
                    DivisionDropDown.getValue() == null) {
                wait();
            } else if (!CustomerNameField.getText().isEmpty() &&
                    !CustomerAddressField.getText().isEmpty() &&
                    !CustomerZipField.getText().isEmpty() &&
                    !CountryPhoneField.getText().isEmpty() &&
                    CountryDropDown.getValue() != null &&
                    DivisionDropDown.getValue() != null) {
                customerDB.updateCustomer(new_customer);
                System.out.println(new_customer);
                System.out.println(customerDB.getAllCustomer());
                Main.stageAndScene(event, "/views/CUSTMain.fxml");
            }
        } catch (IllegalMonitorStateException | InterruptedException e) {

        }
    }

    /**
     * It used to cancel the action and take the user back to customer table.
     * @param event
     */
    @FXML
    void CancelCustomerOnAction(ActionEvent event) {
        Main.stageAndScene(event, "/views/CUSTMain.fxml");
    }

    /**
     * It used for Initializing the country and division dropdowns.
     * @param url
     * @param resourceBundle
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ObservableList<Location> country = countryDB.getAllCountries();
        CountryDropDown.setItems(country);
        CountryDropDown.setPromptText("Select a country");
        ObservableList<Location> division = divisionDB.getAllDivisions();
        DivisionDropDown.setItems(division);
        DivisionDropDown.setPromptText("Select a division");
    }

    /**
     * It is used to send the customer data to customer main table.
     * @param customer  customer.
     * @param countryID country id.
     * @param divisionID division id.
     */
    public void sendCustomer(Customer customer, int countryID, int divisionID) {

        for (Location ctry : CountryDropDown.getItems()) {
            if (countryID == ctry.getId()) {
                CountryDropDown.setValue(ctry);
                break;
            }
        }
        for (Location div : DivisionDropDown.getItems()) {
            if (divisionID == div.getId()) {
                DivisionDropDown.setValue(div);
                break;
            }
        }
        CustomerIdField.setText(String.valueOf(customer.getId()));
        CustomerNameField.setText(customer.getCUSTName());
        CustomerAddressField.setText(customer.getCUSTAddress());
        CustomerZipField.setText(customer.getPostalCode());
        CountryPhoneField.setText(customer.getCUSTPone());
    }

    /**
     * It is used for selecting country and filtering the division dropdown.
     *<b>  The Lambda Expression is used for selected country to set up  the filtered divisions for the country.</b>
     *This makes the code better by letting the filter to take place at the onAction instead of creating three
     * separate methods in appointmentDB, each filtering for one country, then running each method at the onAction.
     * @param event
     */
    @FXML
    void CountryDropDowmOnAction(ActionEvent event) {
        FilteredList<Location> divisionfilter = new FilteredList<Location>(divisions);
        divisionfilter.setPredicate(row -> {
            int newC = CountryDropDown.getSelectionModel().getSelectedItem().getId();
            int newCD = row.getCountry();
            return newC == newCD;
        });
        DivisionDropDown.setItems(divisionfilter);

    }
    @FXML
    void DivisionDropDownOnAction() {
    }
}
