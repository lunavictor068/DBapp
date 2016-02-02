package DBapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        ModelData.dbConnection = new DBConnection();
        Scene root = new Scene(FXMLLoader.load(getClass().getResource("FXML/main.fxml")));
        root.getStylesheets().add(getClass().getResource("test.css").toExternalForm());
        primaryStage.setTitle("Database Application");
        primaryStage.setScene(root);
        primaryStage.show();
    }

}
