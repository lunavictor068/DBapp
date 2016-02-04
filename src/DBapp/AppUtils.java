package DBapp;

import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;

import javax.print.*;
import javax.print.attribute.HashPrintRequestAttributeSet;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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


    private static void print() {
        // TODO - FINISH
        try {
            DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
            PrintService[] services = PrintServiceLookup.lookupPrintServices(
                    flavor, null);
            PrintService defaultService = PrintServiceLookup.lookupDefaultPrintService();
            PrintService service = ServiceUI.printDialog(null, 200, 200, services, defaultService, flavor, new HashPrintRequestAttributeSet());
            System.out.println(service);
            System.out.println("Selected service ^^^");
            FileInputStream textStream = new FileInputStream("");
            Doc mydoc = new SimpleDoc(textStream, flavor, null);
            DocPrintJob job = defaultService.createPrintJob();
            try {
                job.print(mydoc, null);
            } catch (PrintException e) {
                e.printStackTrace();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

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
            // TODO Build string "Receipt".
            InputStream receipt = new ByteArrayInputStream("hello world!\f".getBytes("UTF8"));
            Doc doc = new SimpleDoc(receipt, flavor, null);
            DocPrintJob job = service.createPrintJob();
            job.print(doc, null);
        } catch (Exception e) {
            AppUtils.displayAlert("Error", "Something went wrong", e.getMessage());
        }
    }
}
