package DBapp.Controllers;

import DBapp.Main;
import DBapp.ModelData;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class addEmployeeController implements Initializable{
    @FXML TextField first, last, address, city, state, zip, phone, email;
    @FXML Button submit;
    @FXML
    Text message;

    public void addEmployee(){
        ModelData.dbConnection.addEmployee(
                first.getText(),last.getText(), address.getText(),
                city.getText(), state.getText(), zip.getText(), phone.getText(),
                email.getText());
        clearFields();
    }

    private void clearFields(){
        first.clear();
        last.clear();
        address.clear();
        city.clear();
        state.clear();
        zip.clear();
        phone.clear();
        email.clear();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        message.setText("Sample warning message. Sample warning message. Sample warning message. Sample warning message. Sample warning message. Sample warning message. Sample warning message. Sample warning message. Sample warning message. Sample warning message. Sample warning message. Sample warning message. Sample warning message. Sample warning message. Sample warning message. Sample warning message. Sample warning message. Sample warning message. Sample warning message. Sample warning message. Sample warning message. Sample warning message. Sample warning message. Sample warning message. Sample warning message. Sample warning message. Sample warning messagSample warning message. Sample warning message. Sample warning message. Sample warning message. Sample warning message. e. Sample warning message. Sample warning message. Sample warning message. ");
    }
}
