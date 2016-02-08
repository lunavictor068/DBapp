package DBapp;

import DBapp.DatabaseModels.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.sql.*;
import java.util.Optional;


public class DBConnection {
    private ResultSet resultSet;
    private Connection connection;

    public DBConnection(){
        String completeAddress;
        String driver = "jdbc:mysql://";
        String databaseAddress = "localhost";
        String databaseName = "alex5db";
        String username = "dbuser";
        String password = "password";
        try {
            completeAddress = driver + databaseAddress +"/"+ databaseName;
            connection = DriverManager.getConnection(completeAddress, username, password);
        } catch (SQLException e) {
            Dialog<DatabaseInfo> dialog = dbErrorDialog();
            dialog.setTitle("Database Error");
            boolean isConnectionValid = false;
            while (!isConnectionValid) {
                String message = "Error code: " + e.getErrorCode() + "\n" + e.getMessage();
                dialog.setHeaderText(message);
                Optional<DatabaseInfo> result = dialog.showAndWait();
                if (result.isPresent()) {
                    databaseAddress = result.get().databaseAddress;
                    databaseName = result.get().databaseName;
                    username = result.get().username;
                    password = result.get().password;
                } else {
                    // If they press cancel or exit.
                    Platform.exit();
                    System.exit(0);
                }
                completeAddress = driver + databaseAddress +"/"+ databaseName;
                try {
                    connection = DriverManager.getConnection(completeAddress, username, password);
                    isConnectionValid = true; // Should only execute if connection succeeds.
                } catch (SQLException innerException) {
                    e = innerException;
                }
            }
        }
    }

    private Dialog<DatabaseInfo> dbErrorDialog(){
        Dialog<DatabaseInfo> dialog = new Dialog<>();
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20, 150, 10, 10));

        TextField databaseAddress = new TextField();
        databaseAddress.setPromptText("Database Address");
        TextField databaseName = new TextField();
        databaseName.setPromptText("Database Name");
        TextField username = new TextField();
        username.setPromptText("Username");
        PasswordField password = new PasswordField();
        password.setPromptText("Password");

        gridPane.add(databaseAddress, 1, 0);
        gridPane.add(databaseName, 1, 1);
        gridPane.add(username, 1, 2);
        gridPane.add(password, 1, 3);
        gridPane.add(new Text("Database Address: "), 0, 0);
        gridPane.add(new Text("Database Name: "), 0, 1);
        gridPane.add(new Text("Username: "), 0, 2);
        gridPane.add(new Text("Password: "), 0, 3);

        ButtonType ok = new ButtonType("Retry", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(ok, ButtonType.CANCEL);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ok){
                return new DatabaseInfo(
                        databaseAddress.getText(),
                        databaseName.getText(),
                        username.getText(),
                        password.getText()
                );
            }
            return null;
        });


        dialog.getDialogPane().setContent(gridPane);

        return dialog;

    }

    public void addEmployee(String firstName, String lastName,
                            String address, String city, String state,
                            String zip, String phone, String email) {
        //language=SQL
        String sql = "INSERT INTO " +
                "employee(First, Last, Address, City, State, Zip, Phone, Email)" +
                " VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, address);
            preparedStatement.setString(4, city);
            preparedStatement.setString(5, state);
            preparedStatement.setString(6, zip);
            preparedStatement.setString(7, phone);
            preparedStatement.setString(8, email);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            showExceptionAlert(e);
        }
    }

    public void addCustomer(String firstName, String lastName, String businessName,
                            String address, String city, String state,
                            String zip, String phone, String email, String fax){
        //language=SQL
        String sql = "INSERT INTO " +
                "customer(First, Last, BusinessName, Address, City, State, Zip, Phone, Email, Fax)" +
                " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, businessName);
            preparedStatement.setString(4, address);
            preparedStatement.setString(5, city);
            preparedStatement.setString(6, state);
            preparedStatement.setString(7, zip);
            preparedStatement.setString(8, phone);
            preparedStatement.setString(9, email);
            preparedStatement.setString(10, fax);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            showExceptionAlert(e);
        }
    }

    private void showExceptionAlert(SQLException e){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("SQL Error");
        alert.setTitle("Error code: " + e.getErrorCode());
        alert.setContentText(e.getMessage());
        alert.show();
    }

    public void addProduct(String name, String description, double price){
        //language=SQL
        String sql ="INSERT INTO product (Name, Description, Price) " +
                "VALUES (?, ?, ?);";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, description);
            preparedStatement.setDouble(3, price);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            showExceptionAlert(e);
        }
    }

    public void markTransaction(int transactionID, Status.Quote quote, Status.Invoice invoice){
        //language=SQL
        String sql = "UPDATE transaction SET Quote = ?, Invoice = ? WHERE TransactionID =  ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, quote.toString());
            preparedStatement.setString(2, invoice.toString());
            preparedStatement.setInt(3, transactionID);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            showExceptionAlert(e);
        }

    }

private <T> void setSQL(T input, PreparedStatement statement, int index) {
    try {
        if (input.getClass().equals(String.class))
            statement.setString(index,
                    (String) input.getClass().getConstructor(String.class).newInstance(input.toString()));
        if (input.getClass().equals(Integer.class))
            statement.setInt(index,
                    (Integer) input.getClass().getConstructor(String.class).newInstance(input.toString()));
        if (input.getClass().equals(Double.class))
            statement.setDouble(index,
                    (Double) input.getClass().getConstructor(String.class).newInstance(input.toString()));
        if (input.getClass().equals(Status.Invoice.class) || input.getClass().equals(Status.Quote.class))
            statement.setString(index, input.toString());
    } catch (SQLException e) {
        showExceptionAlert(e);
    } catch (Exception e){
        e.printStackTrace();
    }

}

public ObservableList<TransactionProduct> searchOrderItems(Transaction transaction){
    //language=SQL
    String sql = "SELECT product.ProductID, Name, Description, Price, Quantity " +
            "FROM orderitem " +
            "LEFT JOIN product " +
            "ON orderitem.ProductID = product.ProductID " +
            "WHERE TransactionID = ?";
    ResultSet resultSet;
    ObservableList<TransactionProduct> transactionProducts = FXCollections.observableArrayList();
    try {
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, transaction.getTransactionID());
        resultSet = preparedStatement.executeQuery();
    while (resultSet.next()){
        transactionProducts.add(new TransactionProduct(
                resultSet.getInt("ProductID"),
                resultSet.getString("Name"),
                resultSet.getString("Description"),
                resultSet.getDouble("Price"),
                resultSet.getInt("Quantity")
        ));
    }
    } catch (SQLException e) {
        showExceptionAlert(e);
    }
    return transactionProducts;

}

    public ObservableList<Customer> searchCustomer(Integer customerID, String first, String last, String businessName,
                                                   String address, String city, String state, String zip,
                                                   String phone, String email, String fax) {
        String sql = "SELECT * FROM customer";

        if (isNotEmpty(customerID, first, last, businessName, address, city, state, zip, phone, email, fax)){
            sql = sql + " WHERE ";
            String otherSQL = "";
            if (!(customerID == null)) {otherSQL = otherSQL + "AND customerID = ? ";}
            if (!(first == null)) { otherSQL = otherSQL + "AND First = ? "; }
            if (!(last == null)) { otherSQL = otherSQL + "AND Last = ? "; }
            if (!(businessName == null)) { otherSQL = otherSQL + "AND BusinessName = ? "; }
            if (!(address == null)) { otherSQL = otherSQL + "AND Address = ? ";}
            if (!(city == null)) { otherSQL = otherSQL + "AND City = ? ";}
            if (!(state == null)) { otherSQL = otherSQL + "AND State = ? ";  }
            if (!(zip == null)) { otherSQL = otherSQL + "AND Zip = ? "; }
            if (!(phone == null)) { otherSQL = otherSQL + "AND Phone = ? "; }
            if (!(email == null)){  otherSQL = otherSQL + "AND Email = ? ";  }
            if (!(fax == null)) { otherSQL = otherSQL + "AND Fax = ? ";  }
            otherSQL = otherSQL.substring(4).trim();
            sql = sql + otherSQL;
        }
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            int index = 1;
            if (!(customerID == null)) {setSQL(customerID, statement, index); index++;}
            if (!(first == null)) { setSQL(customerID, statement, index); index++; }
            if (!(last == null)) { setSQL(customerID, statement, index); index++; }
            if (!(businessName == null)) { setSQL(customerID, statement, index); index++; }
            if (!(address == null)) { setSQL(customerID, statement, index); index++;}
            if (!(city == null)) { setSQL(customerID, statement, index); index++;}
            if (!(state == null)) { setSQL(customerID, statement, index); index++;  }
            if (!(zip == null)) { setSQL(customerID, statement, index); index++; }
            if (!(phone == null)) { setSQL(customerID, statement, index); index++; }
            if (!(email == null)){  setSQL(customerID, statement, index); index++;  }
            if (!(fax == null)) { setSQL(customerID, statement, index);  }
            resultSet = statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customersObservable();

    }

    private boolean isNotEmpty(Object... fields){
        // Null equals empty
        for (Object field : fields){
            if (!(field == null))
                return true;
        }
        return false;
    }

    private ObservableList<Customer> customersObservable(){
        ObservableList<Customer> customers = FXCollections.observableArrayList();
        try {
            while (resultSet.next()) {
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
            showExceptionAlert(e);
        }
        return customers;

    }

    // Products stuffs
    private ObservableList<Product> productObservable() {
        ObservableList<Product> products = FXCollections.observableArrayList();
        try {
            while (resultSet.next()){
                products.add(new Product(
                        resultSet.getInt("ProductID"),
                        resultSet.getString("Name"),
                        resultSet.getString("Description"),
                        resultSet.getDouble("Price"))
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    // Add Transaction

     // Transaction ProductProducts stuffs
    @SuppressWarnings("SpellCheckingInspection")
    private ObservableList<TransactionProduct> transactionproductObservable(){
        ObservableList<TransactionProduct> products = FXCollections.observableArrayList();
        try {
            while (resultSet.next()){
                products.add(new TransactionProduct(
                        resultSet.getInt("ProductID"),
                        resultSet.getString("Name"),
                        resultSet.getString("Description"),
                        resultSet.getDouble("Price"),
                                1
                        ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    public ObservableList<Product> searchProduct(Integer productID, String name, String description, Double price){
        String sql = "SELECT * FROM product";
        if (isNotEmpty(productID, name, description, price)){
            sql = sql + " WHERE ";
            String otherSQL = "";
            if (!(productID == null)) {otherSQL = otherSQL + "AND ProductID = ? ";}
            if (!(name == null)) {otherSQL = otherSQL + "AND Name = ? "; }
            if (!(description == null)) { otherSQL = otherSQL + "AND Description = ? ";  }
            if (!(price == null)) { otherSQL = otherSQL + "AND Price = ? ";}

            otherSQL = otherSQL.substring(4).trim();
            sql = sql + otherSQL;
        }
        prepareStatement4(productID, name, description, price, sql);
        return productObservable();
    }

    private <A, B, C, D> void prepareStatement4(A a, B b, C c, D d, String sql) {
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            int index = 1;
            if (!(a == null)) {
                setSQL(a, statement, index);
                index++;
            }
            if (!(b == null)) {
                setSQL(b, statement, index);
                index++;
            }
            if (!(c == null)) {
                setSQL(c, statement, index);
                index++;
            }
            if (!(d == null)) {
                setSQL(d, statement, index);
            }
            resultSet = statement.executeQuery();
        } catch (SQLException e) {
            showExceptionAlert(e);
        }

    }


    public ObservableList<TransactionProduct> getAllProducts(){
        String sql = "SELECT * FROM product";
        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();
        } catch (SQLException e) {
            showExceptionAlert(e);
        }
        return transactionproductObservable();
    }

    // Transaction stuffs
    private ObservableList<Transaction> transactionObservable(){
        ObservableList<Transaction> transactions = FXCollections.observableArrayList();
        try {
            while (resultSet.next()){
                transactions.add(new Transaction(
                        resultSet.getInt("TransactionID"),
                        Status.Quote.valueOf(resultSet.getString("Quote")),
                        Status.Invoice.valueOf(resultSet.getString("Invoice")),
                        resultSet.getInt("CustomerID"),
                        resultSet.getInt("EmployeeID"))
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactions;
    }

    public ObservableList<Transaction> searchTransactions(Integer transactionID, Status.Quote quote,
                                                          Status.Invoice invoice, Integer customerID,
                                                          Integer employeeID){
        String sql = "SELECT * FROM transaction";
        if (isNotEmpty(transactionID, quote, invoice, customerID, employeeID)){
            sql = sql + " WHERE ";
            String otherSQL = "";
            if (!(transactionID == null)) {otherSQL = otherSQL + "AND `TransactionID` = ? ";}
            if (!(quote == null)) {otherSQL = otherSQL + "AND Quote = ? ";}
            if (!(invoice == null)) {otherSQL = otherSQL + "AND Invoice = ? ";}
            if (!(customerID == null)) { otherSQL = otherSQL + "AND CustomerID = ? "; }

            otherSQL = otherSQL.substring(4).trim();
            sql = sql + otherSQL;

        }
        prepareStatement4(transactionID, quote, invoice, customerID, sql);
        return transactionObservable();
    }


    // Employee stuffs
    public ObservableList<Employee> searchEmployee(Integer employeeID, String first, String last,
                                                   String address, String city, String state, String zip,
                                                   String  phone, String email){
        String sql = "SELECT * FROM employee";
        if (isNotEmpty(employeeID, first, last, address, city, state, zip, phone, email)){
            sql = sql + " WHERE ";
            String otherSQL = "";
            if (!(employeeID == null)) {otherSQL = otherSQL + "AND employeeID = ? ";}
            if (!(first == null)) {otherSQL = otherSQL + "AND First = ? "; }
            if (!(last == null)) { otherSQL = otherSQL + "AND Last = ? ";  }
            if (!(address == null)) { otherSQL = otherSQL + "AND Address = ? "; }
            if (!(city == null)) { otherSQL = otherSQL + "AND City = ? "; }
            if (!(state == null)) { otherSQL = otherSQL + "AND State = ? "; }
            if (!(zip == null)) { otherSQL = otherSQL + "AND Zip = ? ";}
            if (!(phone == null)) { otherSQL = otherSQL + "AND Phone = ? "; }
            if (!(email == null)){  otherSQL = otherSQL + "AND Email = ? "; }

            otherSQL = otherSQL.substring(4).trim();
            sql = sql + otherSQL;

        }

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            int index = 1;
            if (!(employeeID == null)) {setSQL(employeeID, statement, index); index++;}
            if (!(first == null)) {setSQL(employeeID, statement, index); index++;}
            if (!(last == null)) {setSQL(employeeID, statement, index); index++;}
            if (!(address == null)) {setSQL(employeeID, statement, index); index++; }
            if (!(city == null)) {setSQL(employeeID, statement, index); index++;}
            if (!(state == null)) {setSQL(employeeID, statement, index); index++;}
            if (!(zip == null)) { setSQL(employeeID, statement, index); index++;}
            if (!(phone == null)) { setSQL(employeeID, statement, index); index++; }
            if (!(email == null)){setSQL(employeeID, statement, index); }
            resultSet = statement.executeQuery();
        } catch (SQLException e) {
showExceptionAlert(e);
        }

        return employeeObservable();
    }

    private ObservableList<Employee> employeeObservable(){
        ObservableList<Employee> employees = FXCollections.observableArrayList();
        try {
            while (resultSet.next()){
                employees.add(new Employee(
                        resultSet.getInt("EmployeeID"),
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
showExceptionAlert(e);        }
        return employees;
    }

    // End Employee stuffs
// New transaction
    public void newTransaction(Status.Quote quote, Status.Invoice invoice, int customerID, int employeeID, ObservableList<TransactionProduct> transactionProducts) throws Exception{
        connection.setAutoCommit(false);
        //language=SQL
        String transactionSQL = "INSERT INTO transaction (Quote, Invoice, CustomerID, EmployeeID) VALUES (?,?,?,?)";
        PreparedStatement transactionStatement = connection.prepareStatement(transactionSQL, Statement.RETURN_GENERATED_KEYS);
        transactionStatement.setString(1, quote.toString());
        transactionStatement.setString(2, invoice.toString());
        transactionStatement.setInt(3, customerID);
        transactionStatement.setInt(4, employeeID);
        transactionStatement.executeUpdate();
        ResultSet keys = transactionStatement.getGeneratedKeys();
        keys.next();
        addTransactionProducts(transactionProducts, keys.getInt(1));
        connection.commit();
        connection.setAutoCommit(true);
    }

    private void addTransactionProducts(ObservableList<TransactionProduct> transactionProducts, int transactionID) throws SQLException{
        String orderItemSQL = "INSERT INTO orderitem (TransactionID, ProductID, Quantity) VALUES (?,?,?)";
        PreparedStatement productStatement;
        for (TransactionProduct product:transactionProducts){
            productStatement = connection.prepareStatement(orderItemSQL);
            productStatement.setInt(1, transactionID);
            productStatement.setInt(2, product.getProductID());
            productStatement.setInt(3, product.getQuantity());
            productStatement.executeUpdate();
        }
    }

    public Employee employeeIDExists(Integer employeeID) {
        try {
            //language=SQL
            String sql = "SELECT * FROM employee WHERE EmployeeID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, employeeID);
            ResultSet matches = preparedStatement.executeQuery();
            if (matches.next()) return new Employee(
                    matches.getInt("EmployeeID"),
                    matches.getString("First"),
                    matches.getString("Last"),
                    matches.getString("Address"),
                    matches.getString("City"),
                    matches.getString("State"),
                    matches.getString("Zip"),
                    matches.getString("Phone"),
                    matches.getString("Email"));
            else {
                return null;
            }
        } catch (SQLException e) {
            AppUtils.displayAlert("An error occured!", "Error code " + e.getErrorCode(), e.getMessage());
        }
        return null;
    }

    class DatabaseInfo {
        String databaseAddress;
        String databaseName;
        String username;
        String password;

        public DatabaseInfo(String databaseAddress, String databaseName, String username, String password) {
            this.databaseAddress = databaseAddress;
            this.databaseName = databaseName;
            this.username = username;
            this.password = password;
        }
    }


    }
