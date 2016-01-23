package DBapp;

import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;

/**
 * Created by lunav on 12/30/2015.
 */
public class ProductRow {
    public TextArea name;
    public TextArea description;
    public Text price;
    public MenuButton quantity;


    public ProductRow() {
        System.out.println("Started Product Row constructor");
        name = new TextArea();
        description = new TextArea();
        price = new Text();
        quantity = new MenuButton();
        for (int i = 0; i < 10 ; i++) {
            quantity.getItems().add(i, new MenuItem(""+i));
        }
        System.out.println("Ended product row constructor");
    }

}
