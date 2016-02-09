# DBapp

This is a point of sale program built for a friend. The program enables the user to graphically access and edit the business's transaction records.
### Features
  - Add Data
    - Products
    - Customers
    - Employees
  - Search Data
    - Transactions
    - Products
    - Customers
    - Employees
  - Update Transaction Records
  - Record a transactiom and all its information
  - Print (to a physical printer) receipts.
  
### Database Diagram
![Database diagram](Database/diagram.png)

A transaction has a Quote and Invoice column. Those columns represent the state of the transaction.

A customer is first given a quote with the estimated cost, after which they have 30 days to accept that quote.
The business will give the customer an invoice and start gathering the products after the customer accepts the quote. The invoice must be paid within 30 days.

### Usage
To use this program you need access to a MySQL database with the structure of the above diagram and at least one employee on your employee table.

The file [Database/DatabaseStructure.sql][DBStructure] has commands to structure a database with the required structure. 

The file [Database/sample.sql][SampleDB] has commands to structure a database with the required structure AND fill it with sample data. 

You can use [MAMP][MAMP] on Windows and Mac OS X to install MySQL and phpMyAdmin and visually manage your database.
If you're on Linux you can follow [this][Ubuntu] guide to install MySQL and phpMyAdmin. phpMyAdmin is not needed but helps to manage your database.

By default the program attempts to connect to a database called "*alex5db*" on *localhost* using a user with the username "*dbuser*" and password "*password*". If id does not find the database it will prompt you to provide the database's information.

If you dont want to go through the trouble of creating your own databse you could connect to the following database:  
**NOTE:** Your connection to the database will time-out after some time. You will have to restart the program to connect again. You may also be denied access since I can only have 4 active connections to that database.  

Database Address: us-cdbr-azure-central-a.cloudapp.net  
Database Name: acsm_dafb58a7a81b817  
Username: baa81373b56662  
Password: 8b647dc7  

You can use Employee ID *10* to login.
    

Download the [pre-compiled Jar][DemoJar] or compile [DBapp][DBapp] on your own.  
**How to launch the [pre-compiled Jar][DemoJar].
Make sure you have Java 1.8_73 or later installed and your PATH is set.  
Enter the following code in your shell/terminal/command prompt.  
```sh
java -jar <path/to/downloadedjar/DBapp.jar>
```  
**```<path/to/downloadedjar/DBapp.jar>```** is the path to the pre-compiled Jar. Most command line programs let you drag and drop the file to automatically get the path to that file.  
Connect to the databse.  
Enjoy! :smile:
   [DemoJar]: <Demo_Jar/DBapp.jar?raw=true>
   [MAMP]: <https://www.mamp.info/en/downloads/>
   [Ubuntu]: <https://help.ubuntu.com/community/ApacheMySQLPHP>
   [DBStructure]: <Database/DatabaseStructure.sql>
   [SampleDB]: <Database/sampleDB.sql>
   [DBapp]: <DBapp/>
