package DBapp;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;


public class DBConnection {
    private ResultSet resultSet;
    // private String USERNAME;
   //  private String PASSWORD;
    private Connection connection;
    private String connectionAddress;

    private final String addCustoerUpdate = "";

    public DBConnection(String username, String password){
        connectionAddress = "jdbc:mysql://localhost/alex5db";
        try {
            connection = DriverManager.getConnection(connectionAddress, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setConnectionAddress(String address){
        this.connectionAddress = address;
    }

    public void printAllTransactions(){
        String allTransactionsQuery =
                "SELECT * FROM transaction, customer, employee " +
                        "WHERE transaction.CustomerID = customer.CustomerID " +
                        "AND transaction.EmployeeID = employee.EmployeeID";
        query(allTransactionsQuery);
    }

    private void print(ResultSet resultSet) throws SQLException{
        System.out.println("----------TRANSACTIONS----------");
        while (resultSet.next()) {
            System.out.println(resultSet.getString(5));
        }
        System.out.println("----------END TRANSACTIONS----------");
    }

    private void query(String sql){
        try (Statement statement=connection.createStatement(
                ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
             ResultSet resultSet = statement.executeQuery(sql);
        ){
            print(resultSet);
        } catch (SQLException e){
            System.out.println(e);
        }
    }

    private void update(String sql){

        try (Statement statement=connection.createStatement(
                ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ){
           statement.execute(sql);
        } catch (SQLException e){
            System.out.println(e);
        }
    }

    public void markTransaction(String transactionID, String quote, String invoice){
        String sql = "UPDATE transaction SET Quote = ?, Invoice = ? WHERE `Transaction ID` =  ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, quote);
            preparedStatement.setString(2, invoice);
            preparedStatement.setString(3, transactionID);
            System.out.println(preparedStatement);
            System.out.println(
                    preparedStatement.executeUpdate());
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void addCustomer(String firstName, String lastName, String businessName,
                            String address, String city, String state,
                            String zip, String phone, String email, String fax){
        String sql = "INSERT INTO customer (`CustomerID`, `First`, `Last`, `BusinessName`, `Address`, `City`, `State`, `Zip`, `Phone`, `Email`, `Fax`) VALUES (NULL, '"+firstName+"', '"+lastName+"', '"+businessName+"', '"+address+"', '"+city+"', '"+state+"', '"+zip+"', '"+phone+"', '"+email+"', '"+fax+"');";
        System.out.println("SQL_________________");
        System.out.println(sql);
        update(sql);
    }

    public void addEmployee(String firstName, String lastName,
                            String address, String city, String state,
                            String zip, String phone, String email){
        String sql = "INSERT INTO `alexsupply`.`employee` (`EmployeeID`, `First`, `Last`, `Address`, `City`, `State`, `Zip`, `Phone`, `Email`) VALUES (NULL, '"+firstName+"', '"+lastName+"', '"+address+"', '"+city+"', '"+state+"', '"+zip+"', '"+phone+"', '"+email+"');";
        System.out.println("SQL_________________");
        System.out.println(sql);
        update(sql);
    }
    public void addProduct(String name, String description, double price){
        String sql ="INSERT INTO `alexsupply`.`product` (`ProductID`, `Name`, `Description`, `Price`) VALUES " +
                "(NULL, '"+name+"', '"+description+"', '"+price+"');";
        System.out.println("------------SQL------------");
        System.out.println(sql);
        update(sql);
    }

    public ObservableList<Customer> searchCustomer(String customerID, String first, String last, String businessName,
                               String address, String city, String state, String zip,
                               String  phone, String email, String fax){
        String sql = "SELECT * FROM customer";
        ArrayList<String> arrayListStrings = new ArrayList<>();

        if (isNotEmpty(customerID, first, last, businessName, address, city, state, zip, phone, email, fax)){
            sql = sql + " WHERE ";
            String otherSQL = "";
            if (!customerID.equals("")) {otherSQL = otherSQL + "AND customerID = ? "; arrayListStrings.add(customerID);}
            if (!first.equals("")) {otherSQL = otherSQL + "AND First = ? "; arrayListStrings.add(first); }
            if (!last.equals("")) { otherSQL = otherSQL + "AND Last = ? "; arrayListStrings.add(last); }
            if (!businessName.equals("")) {
                otherSQL = otherSQL + "AND BusinessName = ? "; arrayListStrings.add(businessName);
            }
            if (!address.equals("")) { otherSQL = otherSQL + "AND Address = ? "; arrayListStrings.add(address); }
            if (!city.equals("")) { otherSQL = otherSQL + "AND City = ? "; arrayListStrings.add(city); }
            if (!state.equals("")) { otherSQL = otherSQL + "AND State = ? "; arrayListStrings.add(state); }
            if (!zip.equals("")) { otherSQL = otherSQL + "AND Zip = ? "; arrayListStrings.add(zip); }
            if (!phone.equals("")) { otherSQL = otherSQL + "AND Phone = ? "; arrayListStrings.add(phone); }
            if (!email.equals("")){  otherSQL = otherSQL + "AND Email = ? "; arrayListStrings.add(email); }
            if (!fax.equals("")) { otherSQL = otherSQL + "AND Fax = ? "; arrayListStrings.add(fax); }

            otherSQL = otherSQL.substring(4).trim();
            System.out.println("Other SQL: "+otherSQL);
            sql = sql + otherSQL;

        }

        System.out.println("Final SQL: "+ sql);
        System.out.println("Arraylist size: "+arrayListStrings.size());
        System.out.println("\tList contents: ");
        for (String s : arrayListStrings)
            System.out.println("\t\t- "+s);
                // TODO All of this... construct sql statement with AND and te rest of stuffs...
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            int indexCounter = 1;
            for (String s : arrayListStrings) {
                statement.setString(indexCounter, s);
                indexCounter++;
            }
            resultSet = statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return customersObservable();

    }
    private boolean isNotEmpty(String... fields){
        for (String field : fields){
            if (!field.equals(""))
                return true;
        }
        return false;
    }

    private ObservableList<Customer> customersObservable(){
        ObservableList<Customer> customers = FXCollections.observableArrayList();
        try {
            while (resultSet.next()){
                customers.add(new Customer(
                        resultSet.getString("CustomerID"),
                        resultSet.getString("First"),
                        resultSet.getString("Last"),
                        resultSet.getString("BusinessName"),
                        resultSet.getString("Address"),
                        resultSet.getString("City"),
                        resultSet.getString("State"),
                        resultSet.getString("Zip"),
                        resultSet.getString("Phone"),
                        resultSet.getString("Email"),
                        resultSet.getString("Fax"))
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;

    }

    // Add Transaction


    // Products stuffs
    private ObservableList<Product> productObservable(){
        ObservableList<Product> products = FXCollections.observableArrayList();
        try {
            while (resultSet.next()){
                products.add(new Product(
                        resultSet.getString("ProductID"),
                        resultSet.getString("Name"),
                        resultSet.getString("Description"),
                        resultSet.getString("Price"))
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }
     // Transaction ProductProducts stuffs
    private ObservableList<TransactionProduct> transactionproductObservable(){
        ObservableList<TransactionProduct> products = FXCollections.observableArrayList();
        try {
            while (resultSet.next()){
                products.add(new TransactionProduct(
                        resultSet.getString("ProductID"),
                        resultSet.getString("Name"),
                        resultSet.getString("Description"),
                        resultSet.getString("Price"),
                                1
                        ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    public ObservableList<Product> searchProduct(String productID, String name, String description, String price){
        String sql = "SELECT * FROM product";
        ArrayList<String> arrayListStrings = new ArrayList<>();

        if (isNotEmpty(productID, name, description, price)){
            sql = sql + " WHERE ";
            String otherSQL = "";
            if (!productID.equals("")) {otherSQL = otherSQL + "AND ProductID = ? "; arrayListStrings.add(productID);}
            if (!name.equals("")) {otherSQL = otherSQL + "AND Name = ? "; arrayListStrings.add(name); }
            if (!description.equals("")) { otherSQL = otherSQL + "AND Description = ? "; arrayListStrings.add(description); }
            if (!price.equals("")) { otherSQL = otherSQL + "AND Price = ? "; arrayListStrings.add(price); }

            otherSQL = otherSQL.substring(4).trim();
            System.out.println("Other SQL: "+otherSQL);
            sql = sql + otherSQL;

        }

        System.out.println("Final SQL: "+ sql);
        System.out.println("Arraylist size: "+arrayListStrings.size());
        System.out.println("\tList contents: ");
        for (String s : arrayListStrings)
            System.out.println("\t\t- "+s);
        // TODO All of this... construct sql statement with AND and te rest of stuffs...
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            int indexCounter = 1;
            for (String s : arrayListStrings) {
                statement.setString(indexCounter, s);
                indexCounter++;
            }
            resultSet = statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productObservable();
    }
    public ObservableList<TransactionProduct> getAllProducts(){
        String sql = "SELECT * FROM product";
        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactionproductObservable();
    }
    // Transaction stuffs
    private ObservableList<Transaction> transactionObservable(){
        ObservableList<Transaction> transactions = FXCollections.observableArrayList();
        try {
            while (resultSet.next()){
                transactions.add(new Transaction(
                        resultSet.getString("Transaction ID"),
                        resultSet.getString("Quote"),
                        resultSet.getString("Invoice"),
                        resultSet.getString("CustomerID"),
                        resultSet.getString("EmployeeID"))
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactions;
    }

    public ObservableList<Transaction> searchTransactions(String transactionID, String quote, String invoice, String customerID, String employeeID){
        String sql = "SELECT * FROM transaction";
        ArrayList<String> arrayListStrings = new ArrayList<>();

        System.out.println(transactionID + quote + invoice + customerID+ employeeID);
        if (isNotEmpty(transactionID, quote, invoice, customerID, employeeID)){
            sql = sql + " WHERE ";
            String otherSQL = "";
            if (!transactionID.equals("")) {otherSQL = otherSQL + "AND `Transaction ID` = ? "; arrayListStrings.add(transactionID);}
            if (!quote.equals("")) {otherSQL = otherSQL + "AND Quote = ? "; arrayListStrings.add(quote);}
            if (!invoice.equals("")) {otherSQL = otherSQL + "AND Invoice = ? "; arrayListStrings.add(invoice); }
            if (!customerID.equals("")) { otherSQL = otherSQL + "AND CustomerID = ? "; arrayListStrings.add(customerID); }
            if (!employeeID.equals("")) { otherSQL = otherSQL + "AND EmployeeID = ? "; arrayListStrings.add(employeeID); }

            otherSQL = otherSQL.substring(4).trim();
            System.out.println("Other SQL: "+otherSQL);
            sql = sql + otherSQL;

        }

        System.out.println("Final SQL: "+ sql);
        System.out.println("Arraylist size: "+arrayListStrings.size());
        System.out.println("\tList contents: ");
        for (String s : arrayListStrings)
            System.out.println("\t\t- "+s);
        // TODO All of this... construct sql statement with AND and te rest of stuffs...
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            int indexCounter = 1;
            for (String s : arrayListStrings) {
                statement.setString(indexCounter, s);
                indexCounter++;
            }
            resultSet = statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactionObservable();
    }

    // Employee stuffs
    public ObservableList<Employee> searchEmployee(String employeeID, String first, String last,
                                                   String address, String city, String state, String zip,
                                                   String  phone, String email){
        String sql = "SELECT * FROM employee";
        ArrayList<String> arrayListStrings = new ArrayList<>();

        if (isNotEmpty(employeeID, first, last, address, city, state, zip, phone, email)){
            sql = sql + " WHERE ";
            String otherSQL = "";
            if (!employeeID.equals("")) {otherSQL = otherSQL + "AND employeeID = ? "; arrayListStrings.add(employeeID);}
            if (!first.equals("")) {otherSQL = otherSQL + "AND First = ? "; arrayListStrings.add(first); }
            if (!last.equals("")) { otherSQL = otherSQL + "AND Last = ? "; arrayListStrings.add(last); }
            if (!address.equals("")) { otherSQL = otherSQL + "AND Address = ? "; arrayListStrings.add(address); }
            if (!city.equals("")) { otherSQL = otherSQL + "AND City = ? "; arrayListStrings.add(city); }
            if (!state.equals("")) { otherSQL = otherSQL + "AND State = ? "; arrayListStrings.add(state); }
            if (!zip.equals("")) { otherSQL = otherSQL + "AND Zip = ? "; arrayListStrings.add(zip); }
            if (!phone.equals("")) { otherSQL = otherSQL + "AND Phone = ? "; arrayListStrings.add(phone); }
            if (!email.equals("")){  otherSQL = otherSQL + "AND Email = ? "; arrayListStrings.add(email); }

            otherSQL = otherSQL.substring(4).trim();
            System.out.println("Other SQL: "+otherSQL);
            sql = sql + otherSQL;

        }

        System.out.println("Final SQL: "+ sql);
        System.out.println("Arraylist size: "+arrayListStrings.size());
        System.out.println("\tList contents: ");
        for (String s : arrayListStrings)
            System.out.println("\t\t- "+s);
        // TODO All of this... construct sql statement with AND and te rest of stuffs...
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            int indexCounter = 1;
            for (String s : arrayListStrings) {
                statement.setString(indexCounter, s);
                indexCounter++;
            }
            resultSet = statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return employeeObservable();
    }

    private ObservableList<Employee> employeeObservable(){
        ObservableList<Employee> employees = FXCollections.observableArrayList();
        try {
            while (resultSet.next()){
                employees.add(new Employee(
                                resultSet.getString("EmployeeID"),
                                resultSet.getString("First"),
                                resultSet.getString("Last"),
                                resultSet.getString("Address"),
                                resultSet.getString("City"),
                                resultSet.getString("State"),
                                resultSet.getString("Zip"),
                                resultSet.getString("Phone"),
                                resultSet.getString("Email"))
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // TODO other stuff
        return employees;
    }
// End Employee stuffs
// New transaction
    public void newTransaction(Status.Quote quote, Status.Invoice invoice, int customerID, int employeeID, ObservableList<TransactionProduct> transactionProducts) throws Exception{
        connection.setAutoCommit(false);
        String transactionSQL =
                "INSERT INTO transaction (`Transaction ID`, `Quote`, `Invoice`, `CustomerID`, `EmployeeID`) VALUES (NULL,?,?,?,?)";
        //language=SQL
        String string = "INSERT INTO transaction (Quote, Invoice, CustomerID, EmployeeID) VALUES (?,?,?,?)";
        System.out.println(string);
        PreparedStatement transactionStatement = connection.prepareStatement(string, Statement.RETURN_GENERATED_KEYS);
        System.out.println("Preparing statement");
        System.out.println(quote.toString());
        System.out.println(invoice.toString());
        transactionStatement.setString(1, quote.toString());
        transactionStatement.setString(2, invoice.toString());
        transactionStatement.setInt(3, customerID);
        transactionStatement.setInt(4, employeeID);
//        transactionStatement.setString(1, quote.toString());
//        transactionStatement.setString(2, invoice.toString());
//        transactionStatement.setString(3, Integer.toString(customerID));
//        transactionStatement.setString(4, Integer.toString(employeeID));
        System.out.println("Excecuting update");
        transactionStatement.executeUpdate();
        System.out.println("Update excecuted");
        System.out.println();
        ResultSet keys = transactionStatement.getGeneratedKeys();
        keys.next();
        addTransactionProducts(transactionProducts, keys.getInt(1));
        connection.commit();
        connection.setAutoCommit(true);
    }

    private void addTransactionProducts(ObservableList<TransactionProduct> transactionProducts, int transactionID) throws SQLException{
        System.out.println("Lel");
        String orderItemSQL = "INSERT INTO orderitem (TransactionID, ProductID, Quantity) VALUES (?,?,?)";
        PreparedStatement productStatement;
        for (TransactionProduct product:transactionProducts){
            productStatement = connection.prepareStatement(orderItemSQL);
            productStatement.setInt(1, transactionID);
            productStatement.setString(2, product.getProductID());
            productStatement.setInt(3, product.getQuantity());
            productStatement.executeUpdate();
            System.out.println("Done with "+product.getName());
        }
    }


    }
