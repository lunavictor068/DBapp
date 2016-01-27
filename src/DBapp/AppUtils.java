package DBapp;

import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import javax.print.*;
import javax.print.attribute.HashPrintRequestAttributeSet;
import java.io.FileInputStream;
import java.io.FileNotFoundException;


public class AppUtils {

    public static void clearTextFields(TextField... textFields){
        for (TextField textField:textFields)
            textField.clear();
    }

    public static Integer integerNullify(String input){
        if (input.equals("")) return null;
        else {
            try {
                return Integer.parseInt(input);
            }
            catch (NumberFormatException e){
                displayAlert("Not an integer!", "Please enter an integer.", e.getMessage());
            }
        }
        return null;
    }

    public static String stringNullify(String input){
        if (input.equals("")) return null;
        else return input;
    }

    public static Double doubleNullify(String input){
        if (input.equals("")) return null;
        else
            try{
                return Double.parseDouble(input);
            }
            catch (NumberFormatException e){
                displayAlert("Not a number!!", "Please enter a number.", e.getMessage());
            }
        return null;
    }

    public static void displayAlert(String title, String header, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.show();

    }


    private static void print(){
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
}
