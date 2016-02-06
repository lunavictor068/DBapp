package DBapp;

import DBapp.DatabaseModels.Transaction;
import DBapp.DatabaseModels.TransactionProduct;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;

import javax.print.*;
import javax.print.attribute.HashPrintRequestAttributeSet;
import java.io.ByteArrayInputStream;
import java.io.InputStream;


public class AppUtils {

    public static void clearTextFields(TextField... textFields) {
        for (TextField textField : textFields)
            textField.clear();
    }

    public static <T> T nullify(String input, Class<T> returnType) {
        try {
            if (input.equals("")) return null;
            else {
                return returnType.getConstructor(String.class).newInstance(input);
            }
        } catch (Exception e) {
            displayAlert(
                    "Error",
                    "An error occurred. Action will continue without input \"" + input +"\".",
                    e.getMessage());
        }
        return null;
    }

    public static void displayAlert(String title, String header, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        /* Used to fix Linux bug where the window is not large enough to display the text,
         resulting in text being replaced with ... (three dots). */
        alert.getDialogPane().getChildren().stream().filter(node -> node instanceof Label).forEach(
                node -> ((Label) node).setMinHeight(Region.USE_PREF_SIZE)
        );
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.show();

    }

    public static boolean isEmpty(TextField textField){
        return textField.getText().equals("");
    }

    public static boolean validify(TextField... textFields){
        boolean valid = true;
        for (TextField textField : textFields){
            if (isEmpty(textField)) {
                textField.setStyle("-fx-border-color: red;");
                valid = false;
            }
            else textField.setStyle("");
        }
        return valid;
    }


    public static void print(TableView<Transaction> transactionTableView,
                             TableView<TransactionProduct> transactionProductTableView) {
        try {
            DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
            PrintService[] services = PrintServiceLookup.lookupPrintServices(
                    flavor, null);
            PrintService defaultService = PrintServiceLookup.lookupDefaultPrintService();
            PrintService service = ServiceUI.printDialog(null, 200, 200, services, defaultService,
                    flavor, new HashPrintRequestAttributeSet());
            Transaction transaction = transactionTableView.getSelectionModel().getSelectedItem();
            StringBuilder receiptText = new StringBuilder();
            receiptText.append("----- RECEIPT START -----");
            receiptText.append("\n");
            receiptText.append("TRANSACTION: ");
            receiptText.append(transaction.getTransactionID());
            receiptText.append("\n");
            receiptText.append("Customer ID: ");
            receiptText.append(transaction.getCustomerID());
            receiptText.append("\n");
            receiptText.append("Helped by employee: ");
            receiptText.append(transaction.getEmployeeID());
            receiptText.append("\n");
            receiptText.append("QUOTE: ");
            receiptText.append(transaction.getQuote());
            receiptText.append("\n");
            receiptText.append("INVOICE: ");
            receiptText.append(transaction.getInvoice());
            receiptText.append("\n");
            receiptText.append("\n\n");
            receiptText.append("--- Order Items Start---");
            receiptText.append("\n");
            for (TransactionProduct transactionProduct : transactionProductTableView.getItems()) {
                receiptText.append(transactionProduct.prettyString());
                receiptText.append("\n");
                receiptText.append("------------------------------------------------------------");
                receiptText.append("\n");
            }
            receiptText.append("--- Order Items End ---");
            receiptText.append("\n\n");
            receiptText.append("--- RECEIPT END ---");
            // TODO Build string "Receipt".
            InputStream receipt = new ByteArrayInputStream(receiptText.toString().getBytes());
            Doc doc = new SimpleDoc(receipt, flavor, null);
            DocPrintJob job = service.createPrintJob();
            job.print(doc, null);
        } catch (Exception e) {
            AppUtils.displayAlert("Error", "Something went wrong", e.getMessage());
        }
    }
}
