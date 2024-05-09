
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import UtilityClasses.Enums.BloodType;
import UtilityClasses.Enums.MaritalStatus;
import UtilityClasses.General.Date;
import UtilityClasses.General.DateDifference;

import java.util.ArrayList;
import java.time.LocalDate;

/**
 * Patient Class to represent patient's informations
 * 
 * @author Ohihoin Vahe
 * @param firstName Patient's First Name
 * @param lastName Patient's Last Name
 * @param patientID Patient's Unique Identification NUmber
 * @param dateOFBirth Patient's Date of Birth
 * 
*/
public class Patient {

    private static File workingFile = new File("HospitalProgram\\HospitalManager\\src\\NumberOfRecords.txt");
    private static int numOfRecords;
    
    private String firstName;
    private String lastName;
    private int patientID;
    private Date dateOfBirth;
    private ArrayList<Date> datesVisited = new ArrayList<Date>();
    private BloodType bloodType;
    private MaritalStatus maritalStatus;


    /**
     * Basic Constructor for Patient Class
     * 
     * @param i_firstName Patient's first name
     * @param i_lastName Patient's last name
     * @param i_dateOfBirth Patient's Date of Birth as a date object
     * @throws IOException
     */
    public Patient(String i_firstName, String i_lastName, Date i_dateOfBirth) throws IOException{

        

        firstName = i_firstName;
        lastName = i_lastName;
        dateOfBirth = i_dateOfBirth;
        assignPatientID();


    }

    /**
     * This constructor allows us to assign information about the patient's blood type and marital status.
     * @param i_firstName
     * @param i_lastName
     * @param i_dateOfBirth
     * @param i_bloodType
     * @param i_maritalStatus
     * @throws IOException
     */
    public Patient(String i_firstName, String i_lastName, Date i_dateOfBirth, BloodType i_bloodType, MaritalStatus i_maritalStatus) throws IOException{

        this(i_firstName, i_lastName, i_dateOfBirth); // uses the standard patient constructor
        bloodType = i_bloodType;
        maritalStatus = i_maritalStatus;

    }

    /**
     * Assigns the patient ID using the number of records on file
     * 
     * @author Ohihoin Vahe
     * 
    */
    private void assignPatientID() throws IOException{

        // Creating input scanner
        if (workingFile.exists()){


        Scanner fileInput = new Scanner(workingFile);
        numOfRecords = fileInput.nextInt();

        //Getting patient ID
        patientID = numOfRecords;
        numOfRecords += 1;

        //Outputing new number of patients to file
        PrintWriter fileOutput = new PrintWriter(workingFile);
        fileOutput.print(numOfRecords);

        // Closes input and output files
        fileInput.close();
        fileOutput.close();

        }
        else{
            System.out.println("SYSTEM ERROR: RECORDS FILE NOT FOUND");
        }

    } 

    public String toString(){

        return (patientID + " " + firstName + " " + lastName);
    }

    /**
     * Returns the patient's age
     * @return
     */
    public int age(){
        // Create a local date object using the java time class
        LocalDate currDate = LocalDate.now();
        
        // Current date as a date object
        Date currentDate = new Date(currDate.getMonthValue(), currDate.getDayOfMonth(), currDate.getYear());

        //Finds the difference from the current date to date of birth
        DateDifference dDifference = currentDate.differenceToDate(dateOfBirth);
        
        return dDifference.year();
        
    }

}
