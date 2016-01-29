package DBapp.Controllers;

import DBapp.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class searchTransactionController implements Initializable{
    @FXML TableView<Transaction> transactionTable;
    @FXML TableView<TransactionProduct> productTable;
    @FXML TableColumn<Transaction, Integer> transactionIDTColumn, customerIDTColumn, employeeIDTColumn;
    @FXML TableColumn<Transaction, Status.Quote> quoteTColumn;
    @FXML TableColumn<Transaction, Status.Invoice> invoiceTColumn;
    @FXML TextField transactionIDField, customerIDField, employeeIDField;
    @FXML ChoiceBox<Status.Quote> quoteBox;
    @FXML ChoiceBox<Status.Invoice> invoiceBox;
// TODO - Finish both tableViews
    public void searchTransactionClick(){
        transactionIDTColumn.setCellValueFactory(new PropertyValueFactory<>("transactionID"));
        quoteTColumn.setCellValueFactory(new PropertyValueFactory<>("quote"));
        invoiceTColumn.setCellValueFactory(new PropertyValueFactory<>("invoice"));
        customerIDTColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        employeeIDTColumn.setCellValueFactory(new PropertyValueFactory<>("employeeID"));

        transactionTable.setItems(
                ModelData.dbConnection.searchTransactions(
                        AppUtils.nullify(transactionIDField.getText(), Integer.class),
                        quoteBox.getValue(),
                        invoiceBox.getValue(),
                        AppUtils.nullify(customerIDField.getText(), Integer.class),
                        AppUtils.nullify(employeeIDField.getText(), Integer.class)));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        transactionTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null)
                System.out.println(newValue.getCustomerID() + " " +newValue.getInvoice()+ " " +newValue.getQuote());
        });
        quoteBox.getItems().addAll(Status.Quote.values());
        quoteBox.getItems().add(null);
        invoiceBox.getItems().addAll(Status.Invoice.values());
        invoiceBox.getItems().add(null);
    }
}
