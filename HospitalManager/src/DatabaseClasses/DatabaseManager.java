package DatabaseClasses;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


import com.mysql.cj.jdbc.Driver;



public class DatabaseManager{

    private static String databaseName = "patient_data";
    private static String url = "jdbc:mysql://localhost:3306/" + databaseName;
    private static String username = "root";
    private static String passcode = "#englog1N12345";
    
    /**
     * Creates a connection object that can be used to make statements
     * 
     * @return A Connection Object
     * @throws SQLException
     * @author Ohihoin Vahe
     */
    public static Connection createConnection() throws SQLException {

        // Make the sql driver available for use by Driver manager which helps creater the connection
        try{
        Class.forName("com.mysql.cj.jdbc.Driver");
        }
        catch(ClassNotFoundException exception){
            System.out.print("SQL ERROR IN DATABASE MANAGER CLASS");
        }
        
        Connection con = DriverManager.getConnection(url, username, passcode);
        return con;

    }

    /**
     * Returns the complete information stored on patients in a particular table
     * 
     * @return string of information
     * @param tableName table from which we want information from
     * @throws SQLException
     * @author Ohihoin Vahe
     */
    public static String returnResultsTable(String tableName) throws SQLException{

        // Utility Variables:
        int i;
        Connection con = createConnection(); // branching point to connect to databases
        Statement workingStatement = con.createStatement(); //statement used to execute queries
        

        // Get the number of columns
        ResultSet result1 = workingStatement.executeQuery("select count(*) from INFORMATION_SCHEMA.columns where table_name = 'patient_info'");
        result1.next();
        int numOfColumns = result1.getInt(1);

        //Getting the actual results from the table
        ResultSet results = workingStatement.executeQuery("select * from " + tableName);

        String totalString = "";
        String lineString = "";

        while (results.next()){

            lineString = "";
            for (i = 1; i<=numOfColumns; i++){    

                lineString += results.getString(i) + " ";

            }

            totalString += lineString + "\n";

        }

        return(totalString);
        
    }

}