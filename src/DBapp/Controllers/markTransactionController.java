package DBapp.Controllers;

import DBapp.AppUtils;
import DBapp.ModelData;
import DBapp.Status;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class markTransactionController implements Initializable{
    @FXML TextField transactionIDField;
    @FXML ChoiceBox<Status.Quote> quoteBox;
    @FXML ChoiceBox<Status.Invoice> invoiceBox;
    @FXML Text message;
    public void markTransactionClick() {
        String transactionID = transactionIDField.getText();
        try {
            Integer transactionIDInteger = Integer.parseInt(transactionID);
        if (transactionID.equals("")) {
            AppUtils.displayAlert(
                    "Invalid ID",
                    "Empty transaction ID field.",
                    "Please enter the ID of the transaction you wish to update."
            );
            message.setFill(Color.ORANGE);
            message.setText("Empty Transaction ID field.");
        } else {

            ModelData.dbConnection.markTransaction(
                    transactionIDInteger,
                    quoteBox.getValue(),
                    invoiceBox.getValue()
            );
            AppUtils.clearTextFields(transactionIDField);
            quoteBox.getSelectionModel().select(Status.Quote.PENDING);
            invoiceBox.getSelectionModel().select(Status.Invoice.PENDING);
            message.setFill(Color.GREEN);
            message.setText("Success!");
        }
        } catch (NumberFormatException e) {
            AppUtils.displayAlert("Error",
                    transactionID + " is not a number.",
                    "Please enter a number for the transaction ID.");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        quoteBox.getItems().addAll(Status.Quote.values());
        invoiceBox.getItems().addAll(Status.Invoice.values());
        quoteBox.getSelectionModel().select(Status.Quote.PENDING);
        invoiceBox.getSelectionModel().select(Status.Invoice.PENDING);
    }
}
