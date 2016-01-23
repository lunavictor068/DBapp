package DBapp.Controllers;

import DBapp.*;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.IntegerStringConverter;
import javafx.util.converter.NumberStringConverter;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;


public class addTransactionController implements Initializable {
    @FXML ComboBox<TransactionProduct> nameSearchField;
    @FXML ChoiceBox<Integer> productQuantityBox;
    @FXML ChoiceBox<Status.Invoice> invoiceBox;
    @FXML ChoiceBox<Status.Quote> quoteBox;
    @FXML TextField customerID, productIDSearchField;
    @FXML TableColumn productIDTableColumn, nameTableColumn, descriptionTableColumn, priceTableColumn;
    @FXML TableColumn<TransactionProduct, Integer> quantityTableColumn;
    @FXML TableView<TransactionProduct> table;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        productIDTableColumn.setCellValueFactory(new PropertyValueFactory<>("productID"));
        nameTableColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        descriptionTableColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        priceTableColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        quantityTableColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        table.setEditable(true);
        productQuantityBox.getItems().setAll(1,2,3,4,5,6,7,8,9,10);
        productQuantityBox.getSelectionModel().selectFirst();
        invoiceBox.getItems().setAll(Status.Invoice.values());
        invoiceBox.getSelectionModel().select(Status.Invoice.PENDING);
        quoteBox.getItems().setAll(Status.Quote.values());
        quoteBox.getSelectionModel().select(Status.Quote.PENDING);
        new Thread(new Runnable() {
            @Override
            public void run() {
                nameSearchField.getItems().addAll(Main.dbConnection.getAllProducts());
            }
        }).start();
        quantityTableColumn.setCellFactory(
                TextFieldTableCell.<TransactionProduct, Integer>forTableColumn(new IntegerStringConverter()));
        new AutoCompleteComboBoxListener<>(nameSearchField);
    }

    public void addItemRowClicked(){
        TransactionProduct selected = nameSearchField.getSelectionModel().getSelectedItem();
        if (selected == null){
            System.out.println("No product selected!");
        }
        else {
            // check if item already in .
            int index = table.getItems().indexOf(selected);
            if (index == -1) {
                selected.setQuantity(productQuantityBox.getSelectionModel().getSelectedItem());
                table.getItems().add(selected);
                table.getSelectionModel().selectLast();
            } else {
                System.out.println("Item already excists!!!11");
                System.out.println("Adding selected quantity to the previous quantity.");
                TransactionProduct item = table.getItems().get(index);
                item.setQuantity(item.getQuantity() + productQuantityBox.getSelectionModel().getSelectedItem());
                table.getSelectionModel().select(index);
            }
            productQuantityBox.getSelectionModel().selectFirst();
            nameSearchField.getSelectionModel().clearSelection();
            table.refresh();
        }
    }
    public void deleteSelectedRowClicked(){
        ObservableList<TransactionProduct> selected, all;
        all = table.getItems();
        selected = table.getSelectionModel().getSelectedItems();
        selected.forEach(all::remove);

    }
    public void submitTransactionClicked(){
        if (table.getItems().isEmpty()){
            System.out.println("Empty tale!!!!!!111!!");
        }
        else {
            System.out.println("Customer ID: " + getCustomerID());
            System.out.println("Quote status: "+quoteBox.getSelectionModel().getSelectedItem());
            System.out.println("Invoice status: "+invoiceBox.getSelectionModel().getSelectedItem());
            System.out.println("Table contents:");
            Iterator items = table.getItems().iterator();
            System.out.println("-----Start TABLE CONTENTS-----");
            while (items.hasNext()){
                TransactionProduct transactionProduct = (TransactionProduct) items.next();
                System.out.println("\tProductID: "+transactionProduct.getProductID() +
                       " Name: "+transactionProduct.getName()+
                        " Description: "+transactionProduct.getDescription()+
                       " Price: "+ transactionProduct.getPrice() +
                       " Quantity: "+transactionProduct.getQuantity());
        }
        System.out.println("-----END TABLE CONTENTS-----");
            try {
                Main.dbConnection.newTransaction(
                        quoteBox.getSelectionModel().getSelectedItem(),
                        invoiceBox.getSelectionModel().getSelectedItem(),
                        getCustomerID(),
                        1,
                        table.getItems()
                );
            }
            catch (Exception e){
                System.err.println(e);
            }
            clearAll();
        }
    }

    public void commit(TableColumn.CellEditEvent editEvent){
        TransactionProduct transactionProduct = (TransactionProduct) editEvent.getTableView().getItems().get(
                editEvent.getTablePosition().getRow()
        );
        transactionProduct.setQuantity((Integer) editEvent.getNewValue());

    }

    private int getCustomerID(){
        if (customerID.getText().equals("")){
            return 1;
        }
        else {
            return Integer.parseInt(this.customerID.getText());
        }
    }

    private void clearAll(){
        AppUtils.clearTextFields(customerID, productIDSearchField);
        nameSearchField.getSelectionModel().clearSelection();
        productQuantityBox.getSelectionModel().selectFirst();
        quoteBox.getSelectionModel().select(Status.Quote.PENDING);
        invoiceBox.getSelectionModel().select(Status.Invoice.PENDING);
        table.getItems().clear();
    }






}
