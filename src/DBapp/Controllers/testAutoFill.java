package DBapp.Controllers;

import DBapp.Product;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * Created by lunav on 1/18/2016.
 */
public class testAutoFill extends Application{
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("AutoFillTextBox without FilterMode");

        Button button = new Button();
        HBox hbox = new HBox();
        Scene scene = new Scene(hbox,300,200);
        ComboBox<Product> comboBox = new ComboBox<>();
        comboBox.getItems().addAll(new Product("asfsdf","asfsd","afsd","fsfsf"));
        new AutoCompleteComboBoxListener<>(comboBox);
        comboBox.getItems().addAll(new Product("ad","m","m","m"));
        hbox.getChildren().add(comboBox);
        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (comboBox.getSelectionModel().getSelectedItem() != null) {
                    Product product = (Product) comboBox.getSelectionModel().getSelectedItem();
                    System.out.println(
                            "Name: " + product.getName() +
                                    " Description: " + product.getDescription() +
                                    " Price: " + product.getPrice()
                    );
                }
                else {
                    System.out.println("Nulll!!!!!!llll1");
                }
            }
        });
        hbox.getChildren().add(button);
        primaryStage.setScene(scene);
        primaryStage.show();

    }
}
