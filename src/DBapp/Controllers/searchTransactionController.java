package DBapp.Controllers;

import DBapp.AppUtils;
import DBapp.DatabaseModels.Transaction;
import DBapp.DatabaseModels.TransactionProduct;
import DBapp.ModelData;
import DBapp.Status;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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
    @FXML TableColumn<TransactionProduct, Integer> productIDTColumn, quantityIDTColumn;
    @FXML TableColumn<TransactionProduct, String> nameTColumn, descriptionTColumn;
    @FXML TableColumn<TransactionProduct, Double> priceIDTColumn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        transactionIDTColumn.setCellValueFactory(new PropertyValueFactory<>("transactionID"));
        quoteTColumn.setCellValueFactory(new PropertyValueFactory<>("quote"));
        invoiceTColumn.setCellValueFactory(new PropertyValueFactory<>("invoice"));
        customerIDTColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        employeeIDTColumn.setCellValueFactory(new PropertyValueFactory<>("employeeID"));
        // TransactionProduct table
        productIDTColumn.setCellValueFactory(new PropertyValueFactory<>("productID"));
        quantityIDTColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        nameTColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        descriptionTColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        priceIDTColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        transactionTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null)
                productTable.setItems(ModelData.dbConnection.searchOrderItems(newValue));
        });
        quoteBox.getItems().addAll(Status.Quote.values());
        quoteBox.getItems().add(null);
        invoiceBox.getItems().addAll(Status.Invoice.values());
        invoiceBox.getItems().add(null);
    }

    public void searchTransactionClick(){
        transactionTable.setItems(
                ModelData.dbConnection.searchTransactions(
                        AppUtils.nullify(transactionIDField.getText(), Integer.class),
                        quoteBox.getValue(),
                        invoiceBox.getValue(),
                        AppUtils.nullify(customerIDField.getText(), Integer.class),
                        AppUtils.nullify(employeeIDField.getText(), Integer.class)));
    }

    public void print() {
        Platform.runLater(new Thread(() ->
                AppUtils.print(transactionTable, productTable)
        ));
    }


}
