package DBapp.Controllers;

import DBapp.AppUtils;
import DBapp.DatabaseModels.TransactionProduct;
import DBapp.ModelData;
import DBapp.Status;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.text.Text;
import javafx.util.converter.IntegerStringConverter;

import java.net.URL;
import java.util.ResourceBundle;


public class addTransactionController implements Initializable {
    @FXML ComboBox<TransactionProduct> nameSearchField;
    @FXML ChoiceBox<Integer> productQuantityBox;
    @FXML ChoiceBox<Status.Invoice> invoiceBox;
    @FXML ChoiceBox<Status.Quote> quoteBox;
    @FXML TextField customerID, productIDSearchField;
    @FXML
    TableColumn<TransactionProduct, String> nameTableColumn, descriptionTableColumn;
    @FXML
    TableColumn<TransactionProduct, Integer> productIDTableColumn;
    @FXML
    TableColumn<TransactionProduct, Double> priceTableColumn;
    @FXML TableColumn<TransactionProduct, Integer> quantityTableColumn;
    @FXML TableView<TransactionProduct> table;
    @FXML Text message;
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
        new Thread(() ->
                nameSearchField.getItems().addAll(ModelData.dbConnection.getAllProducts())
        ).start();
        quantityTableColumn.setCellFactory(
                TextFieldTableCell.<TransactionProduct, Integer>forTableColumn(new IntegerStringConverter()));
        new AutoCompleteComboBoxListener<>(nameSearchField);
    }

    public void addItemRowClicked(){
        TransactionProduct selected = nameSearchField.getSelectionModel().getSelectedItem();
        String productIDSearch = productIDSearchField.getText();
        if (selected == null && productIDSearch.equals("")) {
            message.setText("No product selected!");
        } else if (selected != null) {
            // Search selected Product
            addProductToTable(selected);
            productQuantityBox.getSelectionModel().selectFirst();
            nameSearchField.getSelectionModel().clearSelection();
            table.refresh();
        } else {
            // There is text in the text field
            try {
                Integer productID = Integer.parseInt(productIDSearch);
                for (TransactionProduct transactionProduct : nameSearchField.getItems()) {
                    if (transactionProduct.getProductID().equals(productID)) {
                        // Add product to table
                        addProductToTable(transactionProduct);
                        AppUtils.clearTextFields(productIDSearchField);
                        // This may be bad design. Not sure :P
                        return;
                    }
                }
                AppUtils.displayAlert("Not Found",
                        productIDSearch + " is not the ID of a valid product.",
                        "Please enter the ID of a valid Product of use the Product menu.");
            } catch (NumberFormatException e) {
                AppUtils.displayAlert("Error",
                        productIDSearch + " is not a number.",
                        "Please enter a number for the Product ID.");
            }
        }
    }

    public void addProductToTable(TransactionProduct product) {
        int index = table.getItems().indexOf(product);
        // Not already in table
        if (index == -1) {
            product.setQuantity(productQuantityBox.getSelectionModel().getSelectedItem());
            table.getItems().add(product);
            table.getSelectionModel().selectLast();
        } else {
            // Already in table
            message.setText(product + " is already in the table.\n" +
                    "Added selected quantity to the previous quantity.");
            TransactionProduct item = table.getItems().get(index);
            item.setQuantity(item.getQuantity() + productQuantityBox.getSelectionModel().getSelectedItem());
            table.getSelectionModel().select(index);
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
            message.setText("The table is empty. Please add items to the table in order to submit the transaction.");
        }
        else {
            try {
                ModelData.dbConnection.newTransaction(
                        quoteBox.getSelectionModel().getSelectedItem(),
                        invoiceBox.getSelectionModel().getSelectedItem(),
                        getCustomerID(),
                        ModelData.currentEmployee.getEmployeeID(),
                        table.getItems()
                );
            }
            catch (Exception e){
                AppUtils.displayAlert(
                        "Error",
                        "Something is wrong.",
                        e.getMessage());
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
            message.setText("No customer ID was provided.\n" +
                    "Using guest account.");
            return 1;
        }
        else {
            try{
            return Integer.parseInt(this.customerID.getText());
            } catch (NumberFormatException e) {
                message.setText(
                        "A number is needed.\n"+
                        customerID.getText() + " is not an integer.\n"+
                        "Guest account will be used."
                );
            }
        }
        return 1;
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
