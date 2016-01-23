package DBapp.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import DBapp.ProductRow;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by lunav on 12/30/2015.
 */
public class newTransactionController implements Initializable {

    int totalRows = 3;
    ArrayList arrayList = new ArrayList();

    @FXML
    ScrollPane scrollPane;

    @FXML
    GridPane gridPane;
    RowConstraints row = new RowConstraints(50,50,50);

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        scrollPane.setFitToHeight(true);
        addRow();
        gridPane.getRowConstraints().add(row);

    }

    private void addRow(){
        System.out.println("Making product row");
        ProductRow productRow = new ProductRow();
        productRow.name.setText("");
        System.out.println("Adding Row");
        System.out.println("Tot rws: "+totalRows);
        System.out.println("Prod name: "+productRow.name);
        System.out.println("Prod des: "+productRow.description);
        System.out.println("Prod price: "+productRow.price);
        System.out.println("Prod qty: "+productRow.quantity);
        gridPane.addRow(totalRows,
                productRow.name,
                productRow.description,
                productRow.price,
                productRow.quantity
        );
        gridPane.getRowConstraints().add(row);

        totalRows = totalRows + 1;
        System.out.println("Added row");
    }

    public void addRowClick(MouseEvent event){
        addRow();
    }
}
