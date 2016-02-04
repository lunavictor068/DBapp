package DBapp.Controllers;

import DBapp.AppUtils;
import DBapp.Employee;
import DBapp.Main;
import DBapp.ModelData;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;

/**
 * Created by victor on 2/2/16.
 */
public class startController {

    @FXML
    TextField employeeIDText;

    public void startApp() throws Exception {
        String employeeIDString = employeeIDText.getText();
        if (employeeIDString.equals("")) System.out.println(/****/);
        else {
            try {
                int employeeID = Integer.parseInt(employeeIDString);
                Employee employee = ModelData.dbConnection.employeeIDExists(employeeID);
                if (employee == null) {
                    AppUtils.displayAlert("Not Found", employeeID + " was not found.", "Please enter your Employee ID.");
                } else {
                    ModelData.currentEmloyee = employee;
                    Scene root = new Scene(FXMLLoader.load(Main.class.getResource("FXML/main.fxml")));
                    root.getStylesheets().add(Main.class.getResource("test.css").toExternalForm());
                    ModelData.stage.setScene(root);
                    ModelData.stage.hide();
                    ModelData.stage.show();
                }
                // TODO login logic
            } catch (NumberFormatException e) {
                AppUtils.displayAlert("Error", employeeIDString, employeeIDString + " is not a number." +
                        "\nPlease enter your Employee ID.");
            }
        }
    }
}
