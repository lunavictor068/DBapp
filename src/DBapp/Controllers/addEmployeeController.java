package DBapp.Controllers;

import DBapp.AppUtils;
import DBapp.ModelData;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class addEmployeeController {
    @FXML TextField first, last, address, city, state, zip, phone, email;
    @FXML Button submit;
    @FXML Text message;

    public void addEmployee(){
        if (AppUtils.validify(first, last, address, city, state, zip, phone, email)) {
            ModelData.dbConnection.addEmployee(
                    first.getText(), last.getText(), address.getText(),
                    city.getText(), state.getText(), zip.getText(), phone.getText(),
                    email.getText());
            message.setText("Added new employee.");
            clearFields();
        }
        else {
            message.setText("Please enter information on the red boxes.");
        }
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

}
