package DBapp.Controllers;

import DBapp.Main;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class addProductController {
    @FXML TextField name, description, price;

    public void addProduct(){

        try {
            double doublePrice = Double.parseDouble(price.getText());
            Main.dbConnection.addProduct(
                    name.getText(),
                    description.getText(),
                    doublePrice
            );
            clearFields();
        } catch (NumberFormatException e) {
            System.out.println("Please enter a number for the price.");
        }
    }

    private void clearFields(){
        name.clear();
        description.clear();
        price.clear();
    }
}
