package DBapp.Controllers;

import DBapp.*;
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
                ModelData.dbConnection.searchProduct(
                        AppUtils.nullify(productIDField.getText(), Integer.class),
                        AppUtils.nullify(nameField.getText(), String.class),
                        AppUtils.nullify(descriptionField.getText(), String.class),
                        AppUtils.nullify(priceField.getText(), Double.class)));
    }

}
