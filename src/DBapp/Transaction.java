package DBapp;


public class Transaction {
    public Transaction(String transactionID, String quote, String invoice, String customerID, String employeeID) {
        this.transactionID = transactionID;
        this.quote = quote;
        this.invoice = invoice;
        this.customerID = customerID;
        this.employeeID = employeeID;
    }

    public String getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(String transactionID) {
        this.transactionID = transactionID;
    }

    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    public String getInvoice() {
        return invoice;
    }

    public void setInvoice(String invoice) {
        this.invoice = invoice;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public String getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }

    String transactionID;
    String quote;
    String invoice;
    String customerID;
    String employeeID;
}
