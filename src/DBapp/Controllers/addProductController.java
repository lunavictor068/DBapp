package DBapp.Controllers;

import DBapp.AppUtils;
import DBapp.ModelData;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class addProductController {
    @FXML TextField name, description, price;
    @FXML Text message;

    public void addProduct(){
        if (AppUtils.validify(name, description, price)) {
            try {
                double doublePrice = Double.parseDouble(price.getText());
                ModelData.dbConnection.addProduct(
                        name.getText(),
                        description.getText(),
                        doublePrice
                );
                message.setText(name.getText() + " has been added.");
                clearFields();
            } catch (NumberFormatException e) {
                message.setText("Please enter a number for the price.");
            }
        }
        else {
            message.setText("Please enter information on the red boxes.");
        }
    }

    private void clearFields(){
        name.clear();
        description.clear();
        price.clear();
    }
}
