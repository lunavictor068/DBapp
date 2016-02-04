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
        ModelData.stage = primaryStage;
        ModelData.stage.setMinHeight(600);
        ModelData.stage.setMinWidth(1000);
        ModelData.dbConnection = new DBConnection();
        Scene root = new Scene(FXMLLoader.load(getClass().getResource("FXML/start.fxml")));
        root.getStylesheets().add(getClass().getResource("test.css").toExternalForm());
        primaryStage.setTitle("Database Application");
        primaryStage.setScene(root);
        primaryStage.show();
    }


}
