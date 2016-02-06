package DBapp.Controllers;

import DBapp.AppUtils;
import DBapp.DatabaseModels.Employee;
import DBapp.Main;
import DBapp.ModelData;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;

public class startController {

    @FXML
    TextField employeeIDText;

    public void startApp() throws Exception {
        String employeeIDString = employeeIDText.getText();
        if (employeeIDString.equals("")) AppUtils.displayAlert(
                "Error",
                "Empty Employee ID field.",
                "Please enter your Employee ID.");
        else {
            try {
                int employeeID = Integer.parseInt(employeeIDString);
                Employee employee = ModelData.dbConnection.employeeIDExists(employeeID);
                if (employee == null) {
                    AppUtils.displayAlert("Not Found",
                            employeeID + " was not found.",
                            "Please enter your Employee ID.");
                } else {
                    ModelData.currentEmployee = employee;
                    Scene root = new Scene(FXMLLoader.load(Main.class.getResource("FXML/main.fxml")));
                    root.getStylesheets().add(Main.class.getResource("StyleSheets/MainStyleSheet.css")
                            .toExternalForm());
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
