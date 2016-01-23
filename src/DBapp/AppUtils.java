package DBapp;

import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;


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
}
