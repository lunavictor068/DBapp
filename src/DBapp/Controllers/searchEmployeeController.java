package DBapp.Controllers;

import DBapp.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;


public class searchEmployeeController implements Initializable{
    @FXML
    TextField employeeIDField, firstField, lastField, addressField, cityField, stateField,
            zipField, phoneField, emailField;
    @FXML
    TableView<Employee> table;
    @FXML
    TableColumn<Customer, String> customerIDColumn, firstColumn, lastColumn, addressColumn,
            cityColumn, stateColumn, zipColumn, phoneColumn, emailColumn;

    public void searchCustomerClick(){
        table.setItems(
                ModelData.dbConnection.searchEmployee(
                        AppUtils.nullify(employeeIDField.getText(), Integer.class),
                        AppUtils.nullify(firstField.getText(), String.class),
                        AppUtils.nullify(lastField.getText(), String.class),
                        AppUtils.nullify(addressField.getText(), String.class),
                        AppUtils.nullify(cityField.getText(), String.class),
                        AppUtils.nullify(stateField.getText(), String.class),
                        AppUtils.nullify(zipField.getText(), String.class),
                        AppUtils.nullify(phoneField.getText(), String.class),
                        AppUtils.nullify(emailField.getText(), String.class)));
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        customerIDColumn.setCellValueFactory(new PropertyValueFactory<>("employeeID"));
        firstColumn.setCellValueFactory(new PropertyValueFactory<>("first"));
        lastColumn.setCellValueFactory(new PropertyValueFactory<>("last"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        cityColumn.setCellValueFactory(new PropertyValueFactory<>("city"));
        stateColumn.setCellValueFactory(new PropertyValueFactory<>("state"));
        zipColumn.setCellValueFactory(new PropertyValueFactory<>("zip"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
    }
}
