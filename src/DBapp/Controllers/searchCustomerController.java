package DBapp.Controllers;

import DBapp.AppUtils;
import DBapp.DatabaseModels.Customer;
import DBapp.ModelData;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class searchCustomerController {
    @FXML TextField customerIDField, firstField, lastField, businessNameField, addressField, cityField, stateField,
            zipField, phoneField, emailField, faxField;
    @FXML TableView<Customer> table;
    @FXML TableColumn<Customer, String> firstColumn, lastColumn, businessNameColumn, addressColumn,
            cityColumn, stateColumn, zipColumn, phoneColumn, emailColumn, faxColumn;
    @FXML TableColumn<Customer, Integer> customerIDColumn;

    public void searchCustomer(){
        System.out.println("123");
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
                        AppUtils.nullify(customerIDField.getText(), Integer.class),
                        AppUtils.nullify(firstField.getText(), String.class),
                        AppUtils.nullify(lastField.getText(), String.class),
                        AppUtils.nullify(businessNameField.getText(), String.class),
                        AppUtils.nullify(addressField.getText(), String.class),
                        AppUtils.nullify(cityField.getText(), String.class),
                        AppUtils.nullify(stateField.getText(), String.class),
                        AppUtils.nullify(zipField.getText(), String.class),
                        AppUtils.nullify(phoneField.getText(), String.class),
                        AppUtils.nullify(emailField.getText(), String.class),
                        AppUtils.nullify(faxField.getText(), String.class))
        );
    }

}
