package DBapp.Controllers;

import DBapp.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class searchTransactionController {
    @FXML TreeTableView treeTableView;
    @FXML TreeTableColumn transactionIDColumn, quoteColumn, invoiceColumn, customerIDColumn, employeeIDColumn;

    @FXML
    TableView<Transaction> tableView;

    @FXML
    TableColumn transactionIDTColumn, quoteTColumn, invoiceTColumn, customerIDTColumn, employeeIDTColumn;
    @FXML
    TextField transactionIDField, quoteField, invoiceField, customerIDField, employeeIDField;
    public void searchTransactionClick(){
        transactionIDTColumn.setCellValueFactory(new PropertyValueFactory<>("transactionID"));
        quoteTColumn.setCellValueFactory(new PropertyValueFactory<>("quote"));
        invoiceTColumn.setCellValueFactory(new PropertyValueFactory<>("invoice"));
        customerIDTColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        employeeIDTColumn.setCellValueFactory(new PropertyValueFactory<>("employeeID"));
        tableView.setItems(
                ModelData.dbConnection.searchTransactions(
                        AppUtils.nullify(transactionIDField.getText(), Integer.class),
                        AppUtils.nullify(quoteField.getText(), String.class),
                        AppUtils.nullify(invoiceField.getText(), String.class),
                        AppUtils.nullify(customerIDField.getText(), Integer.class),
                        AppUtils.nullify(employeeIDField.getText(), Integer.class)));
    }
}
