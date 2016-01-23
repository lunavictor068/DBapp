package DBapp.Controllers;

import DBapp.Employee;
import DBapp.Main;
import DBapp.Product;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * Created by lunav on 1/12/2016.
 */
public class searchProductController {
    @FXML TextField productIDField, nameField, descriptionField, priceField;
    @FXML TableColumn productIDColumn, nameColumn, descriptionColumn, priceColumn;
    @FXML TableView<Product> table;


    public void searchProductClick(){
        System.out.println("Search employee button test");
        System.out.println("printtest");
        productIDColumn.setCellValueFactory(new PropertyValueFactory<>("productID"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        table.setItems(
                Main.dbConnection.searchProduct(
                        productIDField.getText(),
                        nameField.getText(),
                        descriptionField.getText(),
                        priceField.getText()));
        System.out.println("end");

    }

}
