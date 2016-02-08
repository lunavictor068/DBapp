package DBapp.Controllers;

import DBapp.AppUtils;
import DBapp.DatabaseModels.Product;
import DBapp.DatabaseModels.TransactionProduct;
import DBapp.ModelData;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;


public class searchProductController {
    @FXML TextField productIDField, nameField, descriptionField, priceField;
    @FXML
    TableColumn<TransactionProduct, String> nameColumn, descriptionColumn;
    @FXML
    TableColumn<TransactionProduct, Integer> productIDColumn;
    @FXML
    TableColumn<TransactionProduct, Double> priceColumn;
    @FXML TableView<Product> table;


    public void searchProductClick(){
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
