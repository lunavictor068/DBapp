package DBapp.Controllers;

import DBapp.Main;
import DBapp.ModelData;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable{

    @FXML MenuItem AddCustomer, AddEmployee, AddProduct, SearchCustomer,  SearchEmployee, SearchProduct,
            SearchTransaction, TransactionsNew, TransactionsMark;

    @FXML Button NewTransaction, Mark, SandP, AllTransactions, signOut;

    @FXML ScrollPane scrollPane;

    @FXML
    Label nameLabel;

    @FXML BorderPane borderPane;

    @Override public void initialize(URL location, ResourceBundle resources) {
        nameLabel.setText(ModelData.currentEmloyee.getFirst() + " " + ModelData.currentEmloyee.getLast());
    }

    private void loadCenterPartial(String fxmlName){
        try {
            borderPane.setCenter( FXMLLoader.load(Main.class.getResource("FXML/Partials/"+ fxmlName +".fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Load BorderPane center partials.
    public void newTransaction(){
        loadCenterPartial("addTransaction");
    }
    public void mark(){
        loadCenterPartial("mark");
    }

    public void addCustomer(){
        loadCenterPartial("addCustomer");
    }
    public void addEmployee(){
        loadCenterPartial("addEmployee");
    }
    public void addProduct(){
        loadCenterPartial("addProduct");
    }

    public void searchCustomer(){
        loadCenterPartial("searchCustomers");
    }
    public void searchEmployee(){
        loadCenterPartial("searchEmployee");
    }
    public void searchProduct(){
        loadCenterPartial("searchProduct");
    }
    public void searchTransaction(){
        loadCenterPartial("searchTransaction");
    }

    public void searchAndPrint(){
        //TODO implement method
        System.out.println("TODO implement method");
    }

    public void allTransactions(){
        try {
            Node node = FXMLLoader.load(Main.class.getResource("FXML/Partials/searchTransaction.fxml"));
            borderPane.setCenter(node);
            // Simulate mouse click on Submit button. Will search for all transactions.
            Event.fireEvent(node.lookup("#searchTransactionButton"),
                    new MouseEvent(
                            MouseEvent.MOUSE_CLICKED, 0, 0, 0, 0,
                            MouseButton.PRIMARY, 1, true, true, true, true, true, true, true, true, true, true, null));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void signout() {
        try {
            Scene root = new Scene(FXMLLoader.load(Main.class.getResource("FXML/start.fxml")));
            root.getStylesheets().add(Main.class.getResource("test.css").toExternalForm());
            ModelData.stage.setScene(root);
            ModelData.stage.hide();
            ModelData.stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
        ModelData.currentEmloyee = null;
    }


}
