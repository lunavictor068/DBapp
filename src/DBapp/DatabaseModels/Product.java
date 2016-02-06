package DBapp.DatabaseModels;

@SuppressWarnings("unused")
public class Product {

   private Integer productID;
   private String name;
   private String description;
   private double price;

    public Product(Integer productID, String name, String description, double price) {
        this.productID = productID;
        this.name = name;
        this.description = description;
        this.price = price;
    }
    public Product() {
    }

    public Integer getProductID() {
        return productID;
    }

    public void setProductID(Integer productID) {
        this.productID = productID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString(){
        return  name;
    }


}
