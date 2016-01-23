package DBapp;

public class orderItem {
    private String transactionID;
    private String productID;
    private int quantity;

    public orderItem(String transactionID, String productID, int quantity) {
        this.transactionID = transactionID;
        this.productID = productID;
        this.quantity = quantity;
    }

    public String getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(String transactionID) {
        this.transactionID = transactionID;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}
