package DBapp;


public class Transaction {
    public Transaction(Integer transactionID, Status.Quote quote, Status.Invoice invoice, Integer customerID, Integer employeeID) {
        this.transactionID = transactionID;
        this.quote = quote;
        this.invoice = invoice;
        this.customerID = customerID;
        this.employeeID = employeeID;
    }

    public Integer getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(Integer transactionID) {
        this.transactionID = transactionID;
    }

    public Status.Quote getQuote() {
        return quote;
    }

    public void setQuote(Status.Quote quote) {
        this.quote = quote;
    }

    public Status.Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Status.Invoice invoice) {
        this.invoice = invoice;
    }

    public Integer getCustomerID() {
        return customerID;
    }

    public void setCustomerID(Integer customerID) {
        this.customerID = customerID;
    }

    public Integer getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(Integer employeeID) {
        this.employeeID = employeeID;
    }

    Integer transactionID;
    Status.Quote quote;
    Status.Invoice invoice;
    Integer customerID;
    Integer employeeID;
}
