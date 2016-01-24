package DBapp.Controllers;

import DBapp.Customer;
import DBapp.Employee;
import DBapp.Main;
import DBapp.ModelData;
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
        System.out.println("Setting items");
        table.setItems(
                ModelData.dbConnection.searchEmployee(
                        employeeIDField.getText(),
                        firstField.getText(),
                        lastField.getText(),
                        addressField.getText(),
                        cityField.getText(),
                        stateField.getText(),
                        zipField.getText(),
                        phoneField.getText(),
                        emailField.getText()
        ));
        System.out.println("end");

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Setting columns");
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
