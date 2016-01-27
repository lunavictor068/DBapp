package DBapp.Controllers;

import DBapp.AppUtils;
import DBapp.Customer;
import DBapp.ModelData;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class searchCustomerController {
    @FXML TextField customerIDField, firstField, lastField, businessNameField, addressField, cityField, stateField,
            zipField, phoneField, emailField, faxField;
    @FXML TableView<Customer> table;
    @FXML TableColumn<Customer, String> customerIDColumn, firstColumn, lastColumn, businessNameColumn, addressColumn,
            cityColumn, stateColumn, zipColumn, phoneColumn, emailColumn, faxColumn;

    public void printTest(){
        System.out.println("printtest");
        customerIDColumn.setCellValueFactory(new PropertyValueFactory<>("CustomerID"));
        firstColumn.setCellValueFactory(new PropertyValueFactory<>("first"));
        lastColumn.setCellValueFactory(new PropertyValueFactory<>("last"));
        businessNameColumn.setCellValueFactory(new PropertyValueFactory<>("businessName"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        cityColumn.setCellValueFactory(new PropertyValueFactory<>("city"));
        stateColumn.setCellValueFactory(new PropertyValueFactory<>("state"));
        zipColumn.setCellValueFactory(new PropertyValueFactory<>("zip"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        faxColumn.setCellValueFactory(new PropertyValueFactory<>("fax"));
        table.setItems(
                ModelData.dbConnection.searchCustomer(
                        AppUtils.integerNullify(customerIDField.getText()),
                        // TODO - nullify the rest of the inputs.
                        firstField.getText(),
                        lastField.getText(),
                        businessNameField.getText(),
                        addressField.getText(),
                        cityField.getText(),
                        stateField.getText(),
                        zipField.getText(),
                        phoneField.getText(),
                        emailField.getText(),
                        faxField.getText())
        );
        System.out.println("end");
    }

}
