import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.xml.crypto.Data;

import com.mysql.cj.conf.ConnectionUrlParser.Pair;

import DataManagingClasses.DatabaseManager;
import UtilityClasses.Enums.BloodType;
import UtilityClasses.Enums.MaritalStatus;
import UtilityClasses.Enums.Sex;
import UtilityClasses.Exceptions.InvalidInputException;
import UtilityClasses.Exceptions.LoadInException;
import UtilityClasses.Exceptions.PatientNotFoundException;
import UtilityClasses.General.Date;
import UtilityClasses.General.Patient;

public class Main {

    // Made the patient's array list a global variable
    public static ArrayList<Patient> patients;

    public static void main(String[] args) throws SQLException, IOException, PatientNotFoundException, InvalidInputException{


        patients = loadPatients(); 
        int i;

        for (i=0; i < patients.size(); i++){
            
            System.out.println(patients.get(i));

        }


        addPatient("Aigbe", "Ohihoin", new Date(4, 13, 1975), BloodType.A_POSITIVE, MaritalStatus.MARRIED, Sex.MALE);
        addPatient("Hephzibah", "Ohihoin", new Date(12, 8, 2004), BloodType.A_POSITIVE, MaritalStatus.SINGLE, Sex.FEMALE);

        for (i=0; i < patients.size(); i++){
            
            System.out.println(patients.get(i));

        }

        saveData();

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

        String tableName = "patient_info";
        String currentLine;
        int counter = 0;


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
        
        Date inputDate;


        while (lineReader.hasNextLine()){

            /*
             * Phase 1 Input:
             * 
             * Patient's data is collected from the database string
             */

            currentLine = lineReader.nextLine();
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
                }
                
                if (unitReader.hasNext()){
                    firstName = unitReader.next();
                }
                else{
                    inputError = true;
                }

                if (unitReader.hasNext()){
                    lastName = unitReader.next();
                }
                else{
                    inputError = true;
                }

                if (unitReader.hasNext()){
                    dateOfBirth = Date.dateFromDBString(unitReader.next());
                }
                else{
                    inputError = true;
                }

                if (unitReader.hasNext()){
                    bloodType = BloodType.bloodTypeFromDBString(unitReader.next());
                }
                else{
                    inputError = true;
                }
                
                if (unitReader.hasNext()){
                    maritalStatus = MaritalStatus.maritalStatusFromDBString(unitReader.next());
                }
                else{
                    inputError = true;
                }

                if (unitReader.hasNext()){
                    sex = Sex.sexFromDBString(unitReader.next());
                }
                else{
                    inputError = true;
                }



                // Checks if there were any errors; if there are none, it creates the patient object

                if (inputError){
                    System.out.println("INPUT ERROR WHEN LOADING FROM ROW "+ counter);
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

                    while (datesLineReader.hasNextLine()){
                        
                        unitReader = new Scanner(datesLineReader.nextLine());

                        if (unitReader.hasNextInt()){

                            unitReader.nextInt();
                            inputDate = Date.dateFromDBString(unitReader.next());
                            inputPatient.addVisitedDate(inputDate);
                            
                        }
                        else{
                            inputError = true;
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
    
    /**
     * Finds all the patient's with the given first name
     * @param i_firstName
     * @return Patient Array List
     * @throws PatientNotFoundException
     */
    public static ArrayList<Patient> findPatientsWithFirstName(String i_firstName) throws PatientNotFoundException{

        ArrayList<Patient> allPatients = new ArrayList<>();
        for (Patient patient: patients){

            if (
                patient.getFirstName().equals(i_firstName.toUpperCase())
            )
            {
                allPatients.add(patient);
            }

        }

        if (allPatients.isEmpty()){
            throw new PatientNotFoundException("CAN'T FIND ANY PATIENTS WITH NAME: " + i_firstName);
        }
        else{
            return allPatients;
        }

    }

    /**
     * Finds all patients with given last name
     * @param i_lastName
     * @return Patient ArrayList
     * @throws PatientNotFoundException
     */
    public static ArrayList<Patient> findPatientsWithLastName(String i_lastName) throws PatientNotFoundException{

        ArrayList<Patient> allPatients = new ArrayList<>();
        for (Patient patient: patients){

            if (
                patient.getLastName().equals(i_lastName.toUpperCase())
            )
            {
                allPatients.add(patient);
            }

        }

        if (allPatients.isEmpty()){
            throw new PatientNotFoundException("CAN'T FIND ANY PATIENTS WITH LAST NAME: " + i_lastName);
        }
        else{
            return allPatients;
        }

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
     * Returns 1 if patient was added successfully, else returns 0
     * @param i_firstName
     * @param i_lastName
     * @param i_dateOfBirth
     * @return
     * @throws IOException
     * @throws InvalidInputException
     */
    private static int addPatient(String i_firstName, String i_lastName, Date i_dateOfBirth) throws IOException, InvalidInputException{

        Patient i_patient = new Patient(i_firstName, i_lastName, i_dateOfBirth);

        File numOfRecordsFile = new File("HospitalManager\\src\\TextFiles\\NumberOfRecords.txt");
        Scanner numOfRecordsReader = new Scanner(numOfRecordsFile);
        int numOfRecords = numOfRecordsReader.nextInt();

        

        // Checks for duplicate patients
        for (Patient patient: patients){

            if (patient.equals(i_patient)){
                // If there is a duplicate patient, we don't want the addition to count, so
                // we remove the record added count from the NumberOfRecords.txt file

                numOfRecords -= 1;
                PrintWriter pw = new PrintWriter(numOfRecordsFile);
                pw.write(numOfRecords);
                pw.close();
                numOfRecordsReader.close();
                return 0;

            }

        }

        patients.add(i_patient);
        numOfRecordsReader.close();
        return 1;

    }

    /**
     * Adds a patient to the working patient arraylist
     * Returns 1 if patient was added successfully, else returns 0
     * @param i_firstName
     * @param i_lastName
     * @param i_dateOfBirth
     * @param i_bloodType
     * @param i_maritalStatus
     * @param i_sex
     * @return
     * @throws IOException
     * @throws InvalidInputException
     */
    private static int addPatient(String i_firstName, String i_lastName, Date i_dateOfBirth, BloodType i_bloodType, MaritalStatus i_maritalStatus, Sex i_sex) throws IOException, InvalidInputException{

        Patient i_patient = new Patient(i_firstName, i_lastName, i_dateOfBirth, i_bloodType, i_maritalStatus, i_sex);

        File numOfRecordsFile = new File("HospitalManager\\src\\TextFiles\\NumberOfRecords.txt");
        Scanner numOfRecordsReader = new Scanner(numOfRecordsFile);
        int numOfRecords = numOfRecordsReader.nextInt();

        // Checks for duplicate patients
        for (Patient patient: patients){

            if (patient.equals(i_patient)){
                // If there is a duplicate patient, we don't want the addition to count, so
                // we remove the record added count from the NumberOfRecords.txt file

                numOfRecords -= 1;
                PrintWriter pw = new PrintWriter(numOfRecordsFile);
                pw.print(numOfRecords);
                pw.close();
                numOfRecordsReader.close();
                return 0;
            }

        }

        patients.add(i_patient);
        numOfRecordsReader.close();
        return 1;
        
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
    
}
