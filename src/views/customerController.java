package views;

import Controller.ModifyCustomerController;
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
import models.Customer;
import sample.Main;
import utils.customerDB;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class customerController implements Initializable {

    @FXML
    private TableView<Customer> customerTableView;

    @FXML
    private TableColumn<Customer, String> CSUTcourry;

    @FXML
    private TableColumn<Class, Integer> CUSTID;

    @FXML
    private TableColumn<Customer, String> CUSTaddress;

    @FXML
    private TableColumn<Customer, String> CUSTname;

    @FXML
    private TableColumn<Customer, String> CUSTphonenumber;

    @FXML
    private TableColumn<Customer, String> CUSTpostalcode;
    @FXML
    private TableColumn<Customer, String> CSUTdivision;
    @FXML
    void onActionAddCustomer(ActionEvent event) {
        Main.stageAndScene(event, "/views/AddCUST.fxml");
    }

    @FXML
    void deletecustomerButtonPushed(ActionEvent event) {
        Alert alert;

        try {
            if (this.customerTableView.getSelectionModel().getSelectedItem() == null) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("No Selection Made");
                alert.setContentText("Please select a customer to delete");
                alert.showAndWait();
            } else if (this.customerTableView.getSelectionModel().getSelectedItem() != null) {

                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirm Deletion");
                alert.setContentText("Are you sure you want to delete this customer?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    Customer cust = this.customerTableView.getSelectionModel().getSelectedItem();
                    Customer.deleteCustomer(cust);
                    alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Customer deleted");
                    alert.setContentText("Customer: " + cust.getCUSTName() + " was deleted.");
                    alert.showAndWait();
                }
            }
        } catch (SQLException throwables) {
            alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Can't Delete Customer");
            alert.setContentText("Please delete customer appointments before removing customer.");
            alert.showAndWait();
        }
        Main.stageAndScene(event, "/views/CUSTMain.fxml");
    }

    /**
     * Brings user to main appointment screen.
     *
     * @param event
     */
    @FXML
    void BackButtonPushed(ActionEvent event) {
        Main.stageAndScene(event, "/views/MainMenu.fxml");
    }
    @FXML
    void OnActionmodifyCustomer(ActionEvent event) throws IOException {

        if (this.customerTableView.getSelectionModel().getSelectedItem() == null) {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No selection");
            alert.setContentText("No selection made. Select a customer to modify.");
            alert.showAndWait();
        } else {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(this.getClass().getResource("/views/ModifyCUST.fxml"));
            loader.load();
                ModifyCustomerController modifyCustomerController = (ModifyCustomerController) loader.getController();
                modifyCustomerController.sendCustomer(this.customerTableView.getSelectionModel().getSelectedItem(), this.customerTableView.getSelectionModel().getSelectedItem().getCountryId(), this.customerTableView.getSelectionModel().getSelectedItem().getDivisionId());

            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = (Parent) loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
            System.out.println("table select " + this.customerTableView.getSelectionModel().getSelectedItem());
            System.out.println("countryid" + this.customerTableView.getSelectionModel().getSelectedItem().getCountryId());
            System.out.println("divisionid " + this.customerTableView.getSelectionModel().getSelectedItem().getDivisionId());
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.customerTableView.setItems(customerDB.getAllCustomer());

        this.CUSTID.setCellValueFactory(new PropertyValueFactory("id"));
        this.CUSTname.setCellValueFactory(new PropertyValueFactory("CUSTName"));
        this.CUSTaddress.setCellValueFactory(new PropertyValueFactory("CUSTAddress"));
        this.CUSTpostalcode.setCellValueFactory(new PropertyValueFactory("postalCode"));
        this.CUSTphonenumber.setCellValueFactory(new PropertyValueFactory("CUSTPone"));
        this.CSUTcourry.setCellValueFactory(new PropertyValueFactory("countryId"));
        this.CSUTdivision.setCellValueFactory(new PropertyValueFactory("DivisionId"));
    }
}



