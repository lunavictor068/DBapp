package DBapp;

import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import javax.print.*;
import javax.print.attribute.HashPrintRequestAttributeSet;
import java.io.FileInputStream;
import java.io.FileNotFoundException;


public class AppUtils {
    public static void popularizeBox(ComboBox comboBox){
        TransactionProduct[] transactionProducts = {
          new TransactionProduct("ads","gfg","","",1),
          new TransactionProduct("sd","sgd","","",1),
          new TransactionProduct("23","sgfd","","",1),
          new TransactionProduct("bf","sdgdf","","",1),
          new TransactionProduct("sdfs","dsfgdg","","",1),
          new TransactionProduct("rt","sdgdfg","","",1),
          new TransactionProduct("aet","fdgdfg","","",1),
          new TransactionProduct("pppp","sdgdfg","","",1),
                new TransactionProduct(Integer.toString(1), "Name", "Des", "30", 10)
        };
        comboBox.getItems().addAll(transactionProducts);

    }

    public static void clearTextFields(TextField... textFields){
        for (TextField textField:textFields)
            textField.clear();
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
