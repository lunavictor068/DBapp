package DBapp;

public class TransactionProduct extends Product{
    int quantity;
    double total;

    public  TransactionProduct(Integer productID, String name, String description, double price, int quantity){
        super(productID, name, description, price);
        this.quantity = quantity;
    }

    public  TransactionProduct(){
        super();
    }

    public TransactionProduct(Product product){
        this(product.getProductID(), product.getName(), product.getDescription(), product.getPrice(), 0);
    }


    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}