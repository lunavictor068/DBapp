package DBapp.Controllers;

import DBapp.ModelData;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import DBapp.Main;
import java.net.URL;
import java.util.ResourceBundle;


public class newCustomerController implements Initializable {
    @FXML TextField first, last, businessName, address, city, state, zip, phone, email, fax;
    @FXML Button submit;
    @Override public void initialize(URL location, ResourceBundle resources) {

    }

    public void addCustomer(){
        ModelData.dbConnection.addCustomer(
                first.getText(),last.getText(),businessName.getText(), address.getText(),
                city.getText(), state.getText(), zip.getText(), phone.getText(),
                email.getText(),fax.getText()
        );
        clearFields();
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
