package DBapp.Controllers;

import DBapp.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable{

    @FXML MenuItem AddCustomer, AddEmployee, AddProduct, SearchCustomer,  SearchEmployee, SearchProduct,
            SearchTransaction, TransactionsNew, TransactionsMark;

    @FXML Button NewTransaction, Mark, SandP, AllTransactions, signOut;

    @FXML ScrollPane scrollPane;

    @FXML Text Name;

    @FXML BorderPane borderPane;

    @Override public void initialize(URL location, ResourceBundle resources) {

    }

    private void loadCenterPartial(String fxmlName){
        // TODO Correct jar nullpointer exception
        System.out.println("loadcenter");
        URL url = Main.class.getResource("FXML/Partials/"+ fxmlName +".fxml");
//        URL url = getClass().getResource("lol.txt");
        System.out.println(1);
        System.out.println(getClass().getResourceAsStream("../FXML/Partials/"+ fxmlName +".fxml"));
        System.out.println(url);
        System.out.println("2");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("../FXML/Partials/"+ fxmlName +".fxml"));
        fxmlLoader.setLocation(url);
        fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
        try {
            borderPane.setCenter((Node) fxmlLoader.load(Main.class.getResource("FXML/Partials/"+ fxmlName +".fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Load BorderPane center partials.
    public void newTransaction(){
        //TODO correct layout
        loadCenterPartial("transactions2");
    }
    public void mark(){
        loadCenterPartial("mark");
    }

    public void addCustomer(){
        //TODO correct layout
        loadCenterPartial("newCustomer");
    }
    public void addEmployee(){
        //TODO correct layout
        loadCenterPartial("addEmployee");
    }
    public void addProduct(){
        //TODO correct layout
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
        //TODO implement method
        System.out.println("TODO implement method");
    }

}