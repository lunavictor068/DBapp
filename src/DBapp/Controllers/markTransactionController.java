package DBapp.Controllers;

import DBapp.Main;
import DBapp.ModelData;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

/**
 * Created by victorluna on 1/13/16.
 */
public class markTransactionController {
    @FXML TextField transactionIDField, quoteField, invoiceField;

    public void markTransactionClick(){
        System.out.println("transaction - " + transactionIDField);
        System.out.println("quote - " + quoteField);
        System.out.println("invoice - " + invoiceField);

        ModelData.dbConnection.markTransaction(
                transactionIDField.getText(),
                quoteField.getText(),
                invoiceField.getText()
        );
    }
}
