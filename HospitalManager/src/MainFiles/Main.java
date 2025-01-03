package MainFiles;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import javax.xml.crypto.Data;

import com.mysql.cj.conf.ConnectionUrlParser.Pair;

import DataManagingClasses.DatabaseManager;
import DataManagingClasses.PatientFilesManager;
import JavaFX.Controller;
import UtilityClasses.DataStructures.BinarySearchStuff.BinarySearchTree;
import UtilityClasses.Enums.BloodType;
import UtilityClasses.Enums.MaritalStatus;
import UtilityClasses.Enums.Sex;
import UtilityClasses.Exceptions.InvalidDateException;
import UtilityClasses.Exceptions.InvalidInputException;
import UtilityClasses.Exceptions.LoadInException;
import UtilityClasses.Exceptions.PatientNotFoundException;
import UtilityClasses.General.Date;
import UtilityClasses.General.Patient.Patient;
import UtilityClasses.General.Patient.PatientQuery;
import UtilityClasses.General.Patient.PatientSearchTree;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.net.URI;
import java.net.URL;

public class Main extends Application{

    // Made the patient's array list a global variable
    public static PatientSearchTree patients;
    public static Stage primStage;
    public static Scene currentScene;
    
    
    @Override
    public void start(Stage primStage) throws IOException, SQLException, PatientNotFoundException{

        patients = loadPatientsIntoBinarySearchTree(loadPatients());
        Main.primStage = primStage;

        // TITLE AND LOGO SETUP
        Main.primStage.setTitle("Hospital Manager");
        Image logo = new Image("Images\\HICILogo.png");
        Main.primStage.getIcons().add(logo);
             
        //String css = "JavaFX//generalstyle.css"; 

        // load
        //String url = "..//JavaFX//NewPage.fxml";
        String url = "..//JavaFX//LoginPage.fxml";
        Parent baseRoot = FXMLLoader.load(getClass().getResource(url));

        Scene  baseScene = new Scene(baseRoot);
        //baseScene.getStylesheets().add(css);
        setScene(baseScene);
        Main.primStage.show();
        
    }

    @Override
    public void stop() throws SQLException, IOException{
        saveData();
    }

    /**
     * Sets the scene
     * @param scene
     */
    public static void setScene(Scene scene){

        Main.primStage.setScene(scene);
        Main.currentScene = scene;

    }

    public void loadStartPage(){
        Scene scene = new Scene(null);
    }

    /**
     * 
     * 
     */
    public static void main(String[] args) throws SQLException, IOException, PatientNotFoundException, InvalidInputException, InvalidDateException{
        launch(args);
    }

    /**
     * Loads the patients into the binary search tree
     * @param patients the arraylist of patients 
     * @return the PatientSearchTree containing the patients
     */
    public static PatientSearchTree loadPatientsIntoBinarySearchTree(ArrayList<Patient> patients){
        Collections.shuffle(patients);
        PatientSearchTree patientTree = new PatientSearchTree(); 
        for (Patient patient : patients){
            patientTree.addElement(patient);
        }
        return patientTree;
    }

    /**
     * Loads the Patients into an array list from the database.
     * Prints out location of errors if there are any errors loading any objects
     * @return
     * @throws SQLException
     * @throws IOException
     * @throws PatientNotFoundException
     */
    public static ArrayList<Patient> loadPatients() throws SQLException, IOException, PatientNotFoundException{

        String currentLine;
        int counter = 0;
        int counter2 = 0;


        String fullResults = DatabaseManager.returnPatientResultsTable(); // Gets the full results from the database management table
        
        // Database Readers
        Scanner lineReader = new Scanner(fullResults); // Reads lines of data
        Scanner datesLineReader;
        Scanner unitReader; // Reads individual data items


        ArrayList<Patient> patients = new ArrayList<>();
        
        // Variable Declaration and Initialization
        int patientID = 0;
        String firstName = "";
        String lastName = "";
        Date dateOfBirth = null;
        BloodType bloodType = null;
        MaritalStatus maritalStatus = null;
        Sex sex = null;
        boolean inputError = false;
        Patient inputPatient = null;
        String errorMessage;
        
        
        Date inputDate;


        while (lineReader.hasNextLine()){

            /*
             * Phase 1 Input:
             * 
             * Patient's data is collected from the database string
             */

            currentLine = lineReader.nextLine();
            errorMessage = "LINE "+counter+"\n";
            counter += 1;

            // Makes sure line isn't empty
            if (!(currentLine.isEmpty())){

                unitReader = new Scanner(currentLine);

                // Loading patient data
                if (unitReader.hasNextInt()){
                    patientID = unitReader.nextInt();
                }
                else{
                    inputError = true;
                    errorMessage += "NO PATIENT ID\n";
                }
                
                if (unitReader.hasNext()){
                    firstName = unitReader.next();
                }
                else{
                    inputError = true;
                    errorMessage += "NO FIRST NAME\n";
                }

                if (unitReader.hasNext()){
                    lastName = unitReader.next();
                }
                else{
                    inputError = true;
                    errorMessage += "NO LAST NAME\n";
                }

                if (unitReader.hasNext()){
                    try{
                        dateOfBirth = Date.dateFromDBString(unitReader.next());
                    }
                    catch(InvalidDateException e){
                       errorMessage += "INVALID INPUT DATE OF BIRTH\n";
                        inputError = true;
                    }
                }
                else{
                    inputError = true;
                    errorMessage += "NO DATE OF BIRTH\n";
                }

                if (unitReader.hasNext()){
                    bloodType = BloodType.bloodTypeFromDBString(unitReader.next());
                }
                else{
                    inputError = true;
                    errorMessage += "NO VALID BLOODTYPE\n";
                }
                
                if (unitReader.hasNext()){
                    maritalStatus = MaritalStatus.maritalStatusFromDBString(unitReader.next());
                }
                else{
                    inputError = true;
                    errorMessage += "NO VALID MARITAL STATUS\n";
                }

                if (unitReader.hasNext()){
                    sex = Sex.sexFromDBString(unitReader.next());
                }
                else{
                    inputError = true;
                    errorMessage += "NO VALID SEX\n";
                }



                // Checks if there were any errors; if there are none, it creates the patient object

                if (inputError){
                    System.err.println(errorMessage);
                }
                else{
                    inputPatient = new Patient(patientID, firstName, lastName, dateOfBirth, bloodType, maritalStatus, sex);
                }

                /*
                * Phase 2 Input: Adding patient's visited dates to date object
                * Only happens if the patient object was successfully created
                */
                
                if (!(inputError)){

                    String resultString = DatabaseManager.datesResultTable(inputPatient.getPatientID());
                    datesLineReader = new Scanner(resultString);
                    counter2 = 0;

                    while (datesLineReader.hasNextLine()){
                        
                        unitReader = new Scanner(datesLineReader.nextLine());
                        counter2 += 1;

                        if (unitReader.hasNextInt()){

                            unitReader.nextInt(); // basically just gets and clears the date ID

                            String dateDBString = unitReader.next();
                            try{
                                
                                inputDate = Date.dateFromDBString(dateDBString);
                                inputPatient.addVisitedDate(inputDate);

                            }
                            catch(InvalidDateException e){

                                // If the date is invalid we print to the error stream for debugginh
                                System.err.printf("ISSUE LOADING DATE %d FOR PATIENT %d\n", counter2, counter);

                            }                          
                        }
                        else{
                            System.err.printf("ISSUE LOADING DATE %d FOR PATIENT %d\n", counter2, counter);
                        }                 

                    }

                    // Finally add patient object to the array list
                    patients.add(inputPatient);

                }

            }

        }

        // Closing of scanners and returning of arraylist of patients
        lineReader.close();
        return patients;

    }

    /**
     * Finds patient using first and last name
     * @param i_firstName
     * @param i_lastName
     * @return Patient Object
     * @throws PatientNotFoundException
     */
    public static Patient findPatient(String i_firstName, String i_lastName) throws PatientNotFoundException{

        for (Patient patient: patients){

            if (
            patient.getFirstName().equals(i_firstName.toUpperCase()) && 
            patient.getLastName().equals(i_lastName.toUpperCase()))
            {
                return patient;
            }

        }

        throw new PatientNotFoundException("CAN'T FIND PATIENT WITH NAME: " + i_firstName + " " + i_lastName);

    }

    /**
     * Finds patient using patient ID
     * @param i_patientID
     * @return Patient Object
     * @throws PatientNotFoundException
     */
    public static Patient findPatient(int i_patientID) throws PatientNotFoundException{

        for (Patient patient: patients){

            if (
                patient.getPatientID() == i_patientID
            )
            {
                return patient;
            }

        }

        throw new PatientNotFoundException("CAN'T FIND PATIENT WITH ID: " + i_patientID);

    }
    

    public static ArrayList<Patient> findPatients(String firstName, String lastName){

        ArrayList<Patient> toReturn = new ArrayList<Patient>();

        // CASE 1: Both fields are blank, we return our full list
        if (firstName.isBlank() && lastName.isBlank()){ 
            toReturn.addAll(patients.asList());
            return toReturn;
        }

        // CASE 2: If only the first name has a field, we looked based only on the first name
        if (!(firstName.isBlank()) && lastName.isBlank()){
            for (Patient patient : patients){
                if (patient.getFirstName().toLowerCase().startsWith(firstName.toLowerCase())){
                    toReturn.add(patient);
                }
            }
            return toReturn;
        }

        // CASE 3: If only the last name has a field, we look based only on the last name
        if (!(lastName.isBlank()) && firstName.isBlank()){
            for (Patient patient : patients){
                if (patient.getLastName().toLowerCase().startsWith(lastName.toLowerCase())){
                    toReturn.add(patient);
                }
            }
            return toReturn;
        }

        // CASE 4: Otherwise, we check based on both first and last name
        for (Patient patient : patients){
            if (patient.getFirstName().toLowerCase().startsWith(firstName.toLowerCase()) &&
                patient.getLastName().toLowerCase().startsWith(lastName.toLowerCase())){
                toReturn.add(patient);
            }
        }

        return toReturn;
    }

    /**
     * Finds all the patients that match a given query
     * @param query
     * @return all the patients that match a given query
     */
    public static ArrayList<Patient> findPatientsWithQuery(PatientQuery query){
        return patients.findWithQueryBinarySearch(query);
    }

    
    /**
     * Adds a patient to the patients tree
     * @param p
     * @return true if the patient was added probably, false otherwise
     */
    public static boolean addPatient(Patient p){
        // because we're a tree, we don't even need to worry about duplicates,as those won't even add in the first place
        // but if we don't add a patient, we have to decrement our counter to account for the fact that that new patient created took
        // up a counter value
        if (!(patients.addElement(p))){
            try{
                Patient.decrementIDCounter();
            }catch(IOException e){
            }
            return false;
        }
        return true;
    }


    
    /**
     * Deletes a given patient
     * @param patient
     */
    private static void deletePatient(Patient patient){

        patients.remove(patient);

    }

    /**
     * Saves All The Patient Data In Appropiate Locations
     * Returns 1 is save is successful. Else, returns 0
     * @throws SQLException
     * @throws IOException
     */
    private static int saveData() throws SQLException, IOException{

        int i = 0; // Dates ID counter 
        DatabaseManager.truncateTransitDatesTable();
        DatabaseManager.truncateTransitPatientsInfoTable();
       

        /**
         * Because the patient data is a very important data, I don't want it to be volatile because when we want to save the 
         * new information, we have to completely clear out our table. So, I created transit tables where I try and save the data first
         * before moving it to the actual tables
         */
        try{
            for (Patient patient: patients){

                DatabaseManager.addRecord(patient);

                for (Date date: patient.getDatesVisitedArrayList()){
                    DatabaseManager.addPatientDate(i, date, patient.getPatientID());
                    i++;
                }
            }
        }
        catch(SQLException e){
            System.out.println(e.toString());
            return 0;
        }

        // Then if there are no errors, then we move everything to the actual table
        DatabaseManager.transitToActualTables();

        System.out.println("DATA SAVED....");
        return(1);

    }



    /**
     * Adds a patient to the working patient arraylist
     * 
     * @param i_firstName
     * @param i_lastName
     * @param i_dateOfBirth
     * @param i_bloodType
     * @param i_maritalStatus
     * @param i_sex
     * @return
     * @throws IOException
     * @throws InvalidInputException if patient is already in the records
     */
    private static void addPatient(String i_firstName, String i_lastName, Date i_dateOfBirth, BloodType i_bloodType, MaritalStatus i_maritalStatus, Sex i_sex) throws IOException, InvalidInputException{

        Patient i_patient = new Patient(i_firstName, i_lastName, i_dateOfBirth, i_bloodType, i_maritalStatus, i_sex);

        // Checks for duplicate patients
        for (Patient patient: patients){

            if (patient.equals(i_patient)){
                // If there is a duplicate patient, we don't want the addition to count, so
                // we remove the record added count from the NumberOfRecords.txt file
                throw new InvalidInputException("Patient Already Exists");

            }
        }

        patients.addElement(i_patient);
        
    }

    /**
     * Adds a visited date to a patient's record
     * Returns 0 in the case of duplicate dates
     * @param patient
     * @param visitedDate
     * @return
     */
    public static int addVisitedDate(Patient patient, Date visitedDate){

        for (Date date: patient.getDatesVisitedArrayList()){
            if (date.equals(visitedDate)){
                return 0;
            }
        }
        return 1;

    }

    /**
     * Private utility method for printing the patients in the arraylist.
     * 
     * @return nothing
     */
    private static void printPatients(){

        for (Patient patient: patients){
            System.out.println(patient);
        }

    }
    
}
