import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import DatabaseClasses.DatabaseManager;
import UtilityClasses.Enums.BloodType;
import UtilityClasses.Enums.MaritalStatus;
import UtilityClasses.General.Date;
import UtilityClasses.General.Patient;

public class Main {

    public static ArrayList<Patient> loadPatients() throws SQLException, IOException{

        String tableName = "patient_info";
        String currentLine;


        String fullResults = DatabaseManager.returnResultsTable(tableName); // Gets the full results from the database management table

        // Database Readers
        Scanner lineReader = new Scanner(fullResults); // Reads lines of data
        Scanner unitReader; // Reads individual data items

        // Dates file readers
        File datesFile = new File("HospitalManager\\src\\TextFiles\\VisitedDates.txt");
        Scanner datesLineReader = new Scanner(datesFile);


        ArrayList<Patient> patients = new ArrayList<>();
        

        int patientID = 0;
        String firstName = "";
        String lastName = "";
        Date dateOfBirth = null;
        BloodType bloodType = null;
        MaritalStatus maritalStatus = null;
        boolean inputError = false;
        Patient inputPatient = null;
        Date inputDate;

        while (lineReader.hasNextLine() && datesLineReader.hasNextLine()){

            /*
             * Phase 1 Input:
             * 
             * Patient's data is collected from the database string
             */

            currentLine = lineReader.nextLine();
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



            // Checks if there were any errors; if there are none, it creates the patient object

            if (inputError){
                System.out.println("INPUT ERROR WHEN LOADING PATIENTS");
            }
            else{
                inputPatient = new Patient(patientID, firstName, lastName, dateOfBirth, bloodType, maritalStatus);
            }

            /*
             * Phase 2 Input: Adding patient's visited dates to date object
             * Only happens if there were no errors in the first phase
             */
            
            if (!(inputError)){
                    
                currentLine = datesLineReader.nextLine();
                unitReader = new Scanner(currentLine);

                while (unitReader.hasNext()){

                    inputDate = Date.dateFromTxtFileString(unitReader.next());
                    
                    
                    if (inputDate != null){
                        inputPatient.addVisitedDate(inputDate);
                    }
                    else{
                        inputError = true;
                        System.out.println("DATE FILE CORRUPTION, INPUT ERROR");
                    }

                }

            }

            patients.add(inputPatient);

        }

        lineReader.close();
        datesLineReader.close();
        return patients;

    }

    public static void main(String[] args) {
        
    }
    
}
