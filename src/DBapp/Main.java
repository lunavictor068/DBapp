package DBapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.print.*;
import javax.print.attribute.HashPrintRequestAttributeSet;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.SQLException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
            ModelData.dbConnection = new DBConnection("dbuser", "password");
        Scene root = new Scene(FXMLLoader.load(getClass().getResource("FXML/main.fxml")));
        primaryStage.setTitle("Database Application");
        primaryStage.setScene(root);
        primaryStage.show();
    }


    public static void main(String[] args) {
                launch(args);
    }

}
