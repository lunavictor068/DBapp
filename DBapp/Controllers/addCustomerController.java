package DBapp.Controllers;

import DBapp.AppUtils;
import DBapp.ModelData;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;


public class addCustomerController implements Initializable {
    @FXML TextField first, last, businessName, address, city, state, zip, phone, email, fax;
    @FXML Text message;
    @Override public void initialize(URL location, ResourceBundle resources) {

    }

    public void addCustomer() {
        if (AppUtils.validify(first, last, businessName, address, city, state, zip, phone, email)) {
            ModelData.dbConnection.addCustomer(
                    first.getText(), last.getText(), businessName.getText(), address.getText(),
                    city.getText(), state.getText(), zip.getText(), phone.getText(),
                    email.getText(), fax.getText()
            );
            message.setText("Added " + first.getText() + " " + last.getText() + " from " + businessName.getText());
            clearFields();
        }
        else {
            message.setText("Please enter information on the red boxes.");
        }
    }

    private void clearFields(){
        first.clear();
        last.clear();
        businessName.clear();
        address.clear();
        city.clear();
        state.clear();
        zip.clear();
        phone.clear();
        email.clear();
        fax.clear();
    }
}
