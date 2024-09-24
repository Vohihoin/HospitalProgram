package DataManagingClasses;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import com.mysql.cj.jdbc.Driver;

import UtilityClasses.General.Date;
import UtilityClasses.General.Patient;


/**
 * Database Manager
 * This class is used staticly to perform certain database functions and receive and transmit information from and to databases
 * 
 * @author Ohihoin Vahe
 */
public class DatabaseManager{

    private final static String databaseName = "patient_data";
    private final static String url = "jdbc:mysql://localhost:3306/" + databaseName;
    private final static String username = "root";
    private final static String passcode = "#englog1N12345"; // need to make this a stored value in a database
    
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

            System.out.print("ERROR WHILE LOADING SQL DRIVER CLASS: ");
            System.out.println(exception.getMessage());
            

        }
        
        Connection con = DriverManager.getConnection(url, username, passcode);
        

        return con;

        

    }

    /**
     * Returns the complete information stored on patients in the patients info table
     * 
     * @return string of information
     * @param tableName table from which we want information from
     * @throws SQLException
     * @author Ohihoin Vahe
     */
    public static String returnPatientResultsTable() throws SQLException{

        // Utility Variables:
        int i;
        Connection con = createConnection(); // branching point to connect to databases
        Statement workingStatement = con.createStatement(); //statement used to execute queries
        String tableName = "patients";
        

        // Get the number of columns
        ResultSet result1 = workingStatement.executeQuery(String.format("select count(*) from INFORMATION_SCHEMA.columns where table_name = '%s'", tableName ));
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

    /**
     * Takes in a patient object and adds its record into the transit patients table
     * 
     * @param patient Patient object for patient's record to be added
     * @throws SQLException
     */
    public static void addRecord(Patient patient) throws SQLException{

        String tableName = "t_patients";
        Connection con = createConnection();
        Statement query = con.createStatement();
        String queryString = 

        String.format(

        "INSERT INTO %s VALUES ( %d, '%s', '%s', '%s', '%s', '%s', '%s')", 
        tableName, 
        patient.getPatientID(), 
        patient.getFirstName(), 
        patient.getLastName(), 
        patient.getDateOfBirth().toDatabaseString(),
        patient.getBloodType().toDatabaseString(), 
        patient.getMaritalStatus().toDatabaseString(),
        patient.getSex().toDatabaseString()

        );
        
        query.executeUpdate(queryString);


    }
    

    /**
     * Add a patient's visited date to the transit dates table
     * @param i_dateID
     * @param i_date
     * @param i_patientID
     * @throws SQLException
     */
    public static void addPatientDate(int i_dateID, Date i_date, int i_patientID) throws SQLException{

        String tableName = "t_dates";
        Connection con = createConnection();
        Statement workingStatement = con.createStatement();
        workingStatement.executeUpdate(String.format("INSERT INTO %s VALUES (%d, \"%s\", %d)", tableName, i_dateID, i_date.toDatabaseString(), i_patientID));
        

    }
    
    /**
     * CAUTION: Very Dangerous Method!
     * 
     * REMOVES ALL THE DATA FROM THE TRANSIT TABLE.
     * DON'T USE UNLESS YOU HAVE PROPER ADMINISTRATIVE ACCESS.
     * 
     * The transit tables were created in case of any error while transferring data to the database.
     * Since we have to first truncate the table before we add in new records, there is volatility in the fact that
     * any error while saving will basically mean we lose most of our data.
     * 
     * So, we have the transit table that lets us contain the errors before transferring to the actual storage database
     * @throws SQLException
     */
    public static void truncateTransitPatientsInfoTable() throws SQLException{

        Connection con = createConnection();
        String tableName = "t_patients";
        Statement workingStatement = con.createStatement();

        // We can't use truncate command because table has a foreign key attachement in the other table
        workingStatement.executeUpdate(String.format("DELETE FROM %s WHERE patient_ID >= 0", tableName));

    }

    /**
     * CAUTION Very Dangerous Method!
     * 
     * REMOVES ALL DATA FROM THE TRANSIT TABLE
     * DON'T USE UNLESS YOU HAVE PROPER AUTHORIZATION
     * 
     * The transit tables were created in case of any error while transferring data to the database.
     * Since we have to first truncate the table before we add in new records, there is volatility in the fact that
     * any error while saving will basically mean we lose most of our data.
     * 
     * So, we have the transit table that lets us contain the errors before transferring to the actual storage database
     * @throws SQLException
     */
    public static void truncateTransitDatesTable() throws SQLException{

        String tableName = "t_dates";

        Connection con = createConnection();
        Statement workingStatement = con.createStatement();
        workingStatement.executeUpdate("TRUNCATE TABLE " + tableName);

    }

    /**
     * MOST DANGEROUS METHOD. NEVER TO BE USED PUBLICLY
     * 
     * Empties the patient data to allow for new info from transit table
     * 
     */
    private static void truncateActualDatesTable() throws SQLException{

        String tableName = "dates";

        Connection con = createConnection();
        Statement workingStatement = con.createStatement();
        workingStatement.executeUpdate("TRUNCATE TABLE " + tableName);

    }

    /**
     * MOST DANGEROUS METHOD. NEVER TO BE USED PUBLICLY
     * 
     * Empties the patient data to allow for new info from transit table
     * 
     */
    private static void truncateActualPatientsInfoTable() throws SQLException{

        String tableName = "patients";

        Connection con = createConnection();
        Statement workingStatement = con.createStatement();
        workingStatement.executeUpdate(String.format("DELETE FROM %s WHERE patient_ID >= 0", tableName));

        // We can't use truncate command because table has a foreign key attachement in the other table

    }

    /**
     * Moves all the data from the transit tables to the actual tables
     * 
     * @throws SQLException
     */
    public static void transitToActualTables() throws SQLException{

        // Utility Variables
        Connection con = createConnection();
        Statement workingStatement = con.createStatement();

        truncateActualDatesTable();
        truncateActualPatientsInfoTable();

        workingStatement.executeUpdate(String.format("INSERT INTO %s SELECT * FROM %s", "patients", "t_patients"));
        workingStatement.executeUpdate(String.format("INSERT INTO %s SELECT * FROM %s", "dates", "t_dates"));
        




    }

    /**
     * Takes in a patient's patientID and returns a string of all the dates they've visited
     * 
     * @return String
     * @throws SQLException
     */
    public static String datesResultTable(int i_patientID) throws SQLException{

        // Utility Variables
        String tableName = "dates";
        Connection con = createConnection();
        Statement workingStatement = con.createStatement();
        int i;
        String lineString = "";
        String returnString = "";

        // results 1 just tells us how many columns there are
        ResultSet results1 = workingStatement.executeQuery(String.format("SELECT COUNT(*) AS NUMBER_OF_COLUMNS  FROM INFORMATION_SCHEMA.COLUMNS WHERE table_name = \"%s\";", tableName));
        results1.next(); // Cursor of rows starts before the first row. So, this moves the cursor to the first row
        int numOfColumns = results1.getInt(1); // First Row in a table is 1

        ResultSet result2 = workingStatement.executeQuery("SELECT * FROM " + tableName + " WHERE patient_ID = " + i_patientID);
         // Move the cursor and check if there's a next result
        while (result2.next()) {

            lineString = "";
            for (i=1; i<=numOfColumns; i++){

                lineString += result2.getString(i) + " ";

            }

            lineString = lineString.substring(0, lineString.length()); // Taking off the last space
            returnString += lineString + "\n"; // Adding the line to the return string

        }

        return(returnString);

    }
}



