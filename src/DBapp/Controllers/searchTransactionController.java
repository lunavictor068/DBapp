package DBapp.Controllers;

import DBapp.Main;
import DBapp.ModelData;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class searchTransactionController {
    @FXML TreeTableView treeTableView;
    @FXML TreeTableColumn transactionIDColumn, quoteColumn, invoiceColumn, customerIDColumn, employeeIDColumn;

    @FXML
    TableView tableView;

    @FXML
    TableColumn transactionIDTColumn, quoteTColumn, invoiceTColumn, customerIDTColumn, employeeIDTColumn;
    @FXML
    TextField transactionIDField, quoteField, invoiceField, customerIDField, employeeIDField;
    public void searchTransactionClick(){

        System.out.println("Search employee button test");
        System.out.println("printtest");
        transactionIDTColumn.setCellValueFactory(new PropertyValueFactory<>("transactionID"));
        quoteTColumn.setCellValueFactory(new PropertyValueFactory<>("quote"));
        invoiceTColumn.setCellValueFactory(new PropertyValueFactory<>("invoice"));
        customerIDTColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        employeeIDTColumn.setCellValueFactory(new PropertyValueFactory<>("employeeID"));
        System.out.println(transactionIDColumn.getText() + " ------------");
        tableView.setItems(
                ModelData.dbConnection.searchTransactions(
                        transactionIDField.getText(),
                        quoteField.getText(),
                        invoiceField.getText(),
                        customerIDField.getText(),
                        employeeIDField.getText()
                ));
        System.out.println("end");

    }
}
