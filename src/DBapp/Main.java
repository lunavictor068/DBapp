package DBapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.print.*;
import javax.print.attribute.HashPrintRequestAttributeSet;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Main extends Application {
    private static Stage primaryStage;

    public static DBConnection dbConnection;
    @Override
    public void start(Stage primaryStage) throws Exception{

        dbConnection = new DBConnection("dbuser", "password");
        dbConnection.printAllTransactions();
        this.primaryStage = primaryStage;
        System.out.println(getClass().getResource("FXML/temp2.fxml"));
        Scene root = new Scene(FXMLLoader.load(getClass().getResource("FXML/temp2.fxml")));
        System.out.println("root: "+root);
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(root);
        primaryStage.show();
    }


    public static void main(String[] args) {
                launch(args);
    }

    private static void print(){
        try {
            DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
            PrintService[] services = PrintServiceLookup.lookupPrintServices(
                    flavor, null);
            PrintService defaultService = PrintServiceLookup.lookupDefaultPrintService();
            PrintService service = ServiceUI.printDialog(null, 200, 200, services, defaultService, flavor, new HashPrintRequestAttributeSet());
            System.out.println(service);
            System.out.println("Selected service ^^^");
            FileInputStream textStream = new FileInputStream("");
            Doc mydoc = new SimpleDoc(textStream, flavor, null);
            DocPrintJob job = defaultService.createPrintJob();
            try {
                job.print(mydoc, null);
            } catch (PrintException e) {
                e.printStackTrace();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}
