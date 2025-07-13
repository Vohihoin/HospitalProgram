package DataManagingClasses;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import UtilityClasses.Enums.BloodType;
import UtilityClasses.Enums.MaritalStatus;
import UtilityClasses.Enums.Sex;
import UtilityClasses.General.Date;
import UtilityClasses.General.Patient.Patient;

/**
 * This is an entirely new approach to database management for this program
 */
public class DatabaseAccessor {

    private String username;
    private String password;
    private Connection con;

    private final static String webDatabaseName = "hospital_db";
    private final static String hostname = "hiciserver.com";
    public static final String url = String.format("jdbc:mysql://%s:3306/%s", hostname, webDatabaseName); 

    /**
     * Creates a new patient database accessor 
     * @param username
     * @param password
     * @throws SQLException if it's not able to connect with the given username and password
     */
    public DatabaseAccessor(String username, String password) throws SQLException{
        this.password = password;
        this.username = username;
        establishConnection();
        System.out.println("Able to connect to database");
        loadNextPossibleID();
        System.out.println("Able to load the next possible patient ID");
    }

    /**
     * Establishes a connection to the database
     * @throws SQLException
     */
    private void establishConnection() throws SQLException{
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch(ClassNotFoundException e){
        }

        try{
            con = DriverManager.getConnection(url, username, password);
        }catch(SQLException e){
            throw new SQLException(e);
        }
    }

    public List<Patient> getPatientsList(){
        String patientData;
        try{
            patientData = loadPatientData("patients");
        }catch(SQLException e){
            return null;
        }

        // NOW WE PARSE THROUGH THE PATIENT DATA

        // Database Readers
        Scanner lineReader = new Scanner(patientData);
        Scanner datesLineReader;
        Scanner unitReader;

        // Counters to keep track of which line we're on
        // int counter = 1;
        // int dateCounter = 1; 


        ArrayList<Patient> patients = new ArrayList<>();
        // Variable Declaration and Initialization
        int patientID = 0;
        String firstName = "";
        String lastName = "";
        Date dateOfBirth = null;
        BloodType bloodType = null;
        MaritalStatus maritalStatus = null;
        Sex sex = null;
        Patient inputPatient = null;
        
        
        Date inputDate;

        while (lineReader.hasNextLine()){

            /*
             * Phase 1 Input:
             * 
             * Patient's data is collected from the database string
             */

            String currentLine = lineReader.nextLine();
            //counter += 1;

            // Makes sure line isn't empty
            if (!(currentLine.isEmpty())){
                unitReader = new Scanner(currentLine);

                // LOADING THE PATIENT DATA FROM THE LINE
                patientID = unitReader.nextInt();
                firstName = unitReader.next();
                lastName = unitReader.next();
                dateOfBirth = Date.dateFromDBString(unitReader.next());
                String bloodTypeString = unitReader.next();
                bloodType = bloodTypeString.equalsIgnoreCase("null") ? null : BloodType.bloodTypeFromDBString(bloodTypeString);
                String maritalStatusString = unitReader.next();
                maritalStatus = maritalStatusString.equalsIgnoreCase("null") ? null : MaritalStatus.maritalStatusFromDBString(maritalStatusString);
                String sexString = unitReader.next();
                sex = (sexString.equalsIgnoreCase("null")) ? null : Sex.sexFromDBString(sexString);

                inputPatient = new Patient(patientID, firstName, lastName, dateOfBirth, bloodType, maritalStatus, sex);
                

                /*
                * Phase 2 Input: Adding patient's visited dates to date object
                */
                String resultString;
                try{
                    resultString = loadPatientDates(patientID);
                }catch(SQLException e){ // If we aren't able to load a patient's dates, we just let continue
                    patients.add(inputPatient);
                    continue;
                }
                
                datesLineReader = new Scanner(resultString);
                //dateCounter = 0;

                while (datesLineReader.hasNextLine()){
                    
                    unitReader = new Scanner(datesLineReader.nextLine());
                    //dateCounter += 1;
                    unitReader.nextInt(); // basically just gets and clears the date ID
                        
                    String dateDBString = unitReader.next();
                    inputDate = Date.dateFromDBString(dateDBString);
                    inputPatient.addVisitedDate(inputDate);                                         

                }

                datesLineReader.close();

                // Finally add patient object to the array list
                patients.add(inputPatient);

                unitReader.close();
            }

        }

        lineReader.close();
        return patients;
    }

    /**
     * Loads the visited dates of a patient with a given patient ID
     * @param patient_ID
     * @return
     * @throws SQLException
     */
    private String loadPatientDates(int patient_ID) throws SQLException{
        
        String tableName = "dates";
        Statement workingStatement = con.createStatement();
        int i;
        String lineString = "";
        String returnString = "";

        // Getting how many columns there are
        ResultSet results1 = workingStatement.executeQuery(String.format("SELECT COUNT(*) AS NUMBER_OF_COLUMNS  FROM INFORMATION_SCHEMA.COLUMNS WHERE table_name = \"%s\";", tableName));
        results1.next(); // Cursor of rows starts before the first row. So, this moves the cursor to the first row
        int numOfColumns = results1.getInt(1); // First Row in a table is 1

        // Getting our actual data
        ResultSet result2 = workingStatement.executeQuery("SELECT * FROM " + tableName + " WHERE patient_ID = " + patient_ID);
        while (result2.next()) {

            lineString = "";
            for (i=1; i<=numOfColumns; i++){
                lineString += result2.getString(i) + " ";
            }

            lineString = lineString.substring(0, lineString.length());
            returnString += lineString + "\n"; // Adding the line to the return string
        }

        return(returnString);
    }

    /**
     * Loads all the data from a given table
     * @param tableName
     * @return
     * @throws SQLException
     */
    private String loadPatientData(String tableName) throws SQLException{

        Statement workingStatement = con.createStatement();

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
            for (int i = 1; i<=numOfColumns; i++){    
                lineString += results.getString(i) + " ";
            }

            totalString += lineString.substring(0,lineString.length()-1) + "\n"; // removing the extra space and adding a new line

        }

        return(totalString);
        
    }

    /**
     * Takes all the updated patients in the list and stores them back and their associated dates
     * @param patientList
     */
    public boolean storePatients(List<Patient> patientList){

        try{
            storeNewPossiblePatientID();
        }catch(SQLException e){
            System.out.println("Couldn't store the nextPossiblePatientID\n" +
                                e.getMessage());
            return false;
        }

        if (patientList.isEmpty()){
            return true;
        }

        try{
            truncateTablesToMakeSpaceForUpdatedData();
        }catch(SQLException e){
            System.err.println("Couldn't truncate tables to make space:\n");
            System.err.println(e.getMessage());
            return false;
        }

        // WE INSERT THE PATIENTS REGULARLY

        String command = "INSERT INTO patients VALUES\n\n";
        for (Patient patient : patientList){
            String valueString = getDBValuesString(patient);
            command += valueString + ",\n";
        }
        command = command.substring(0, command.length()-2);
        command += ";";


        //System.out.println(command);
        try{
            Statement workingStatement = con.createStatement();
            workingStatement.executeUpdate(command);
        }catch(SQLException e){
            System.err.println(e.getMessage());
            return false;
        }

        // NOW WE INSERT THE DATES
        command = "INSERT INTO dates VALUES\n\n";
        boolean noDatesToStore = true;

        int dateCounter = 0;
        for (Patient patient : patientList){
            List<Date> dates = patient.getDatesVisitedArrayList();
            if (!dates.isEmpty()){
                noDatesToStore &= false;
            }
            for (Date date : dates){
                String valuesString = getDBValuesStringDates(date, patient, dateCounter++);
                command += valuesString + ",\n";
            }
        }

        if (noDatesToStore){
            return true;
        }

        command = command.substring(0, command.length()-2);
        command += ";";

        try{
            Statement workingStatement = con.createStatement();
            workingStatement.executeUpdate(command);
        }catch(SQLException e){
            System.err.println(e.getMessage());
            return false;
        }

        return true;
        
    }

    /**
     * We truncate the old tables to make space for the new
     * values
     * @throws SQLException
     */
    private void truncateTablesToMakeSpaceForUpdatedData() throws SQLException{

        String tableName = "dates";
        Statement workingStatement = con.createStatement();
        workingStatement.executeUpdate("TRUNCATE TABLE " + tableName);
        tableName = "patients";
        workingStatement.executeUpdate(String.format("DELETE FROM %s WHERE patient_ID >= 0", tableName));

    }

    /**
     * Gets the DB String of the values to add to the database
     * @param p
     * @return
     */
    private String getDBValuesString(Patient patient){
        return String.format("(%d, \"%s\", \"%s\", \"%s\", %s, %s, %s)"
                , patient.getPatientID()
                , patient.getFirstName()
                , patient.getLastName()
                , patient.getDateOfBirth().toDatabaseString()
                , (patient.getBloodType() == null) ? "null" : "\"" + patient.getBloodType().toDatabaseString() + "\""
                , (patient.getMaritalStatus() == null) ? "null" : "\"" + patient.getMaritalStatus().toDatabaseString() + "\""
                , (patient.getSex() == null) ? "null" : "\"" + patient.getSex().toDatabaseString() + "\"");
    }

    private String getDBValuesStringDates(Date date, Patient p, int id){
        return String.format("(%d, \"%s\", %d)"
              , id, date.toDatabaseString(), p.getPatientID());
    }

    /**
     * Loads the next possible ID into the Patient Class
     */
    private void loadNextPossibleID() throws SQLException{
        Statement workingStatement = con.createStatement();
        ResultSet results = workingStatement.executeQuery("SELECT * FROM nextPatientID");
        results.next();
        int nextPossibleID = results.getInt(1);

        Patient.nextPatientID = nextPossibleID;
    }

    /**
     * Stores the new possible patient ID into the data base
     * @throws SQLException
     */
    private void storeNewPossiblePatientID() throws SQLException{
        Statement workingStatement = con.createStatement();
        workingStatement.executeUpdate("TRUNCATE nextPatientID;");
        workingStatement.executeUpdate(String.format("INSERT INTO nextPatientID VALUES(%d);", Patient.getNextPossiblePatientID()));
        System.out.println(String.format("INSERT INTO nextPatientID VALUES(%d);", Patient.getNextPossiblePatientID()));
    }

    public static void main(String[] args) {
    }


    
}
