package UtilityClasses.General;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import UtilityClasses.Enums.BloodType;
import UtilityClasses.Enums.MaritalStatus;

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

    // Static Record Keeping Variables
    private static File recordsFile = new File("HospitalManager\\src\\TextFiles\\NumberOfRecords.txt");
    private static File visitedDatesFile = new File("HospitalManager\\src\\TextFiles\\VisitedDates.txt");
    private static int numOfRecords;
    
    // Basic Patient Variables
    private String firstName;
    private String lastName;
    private int patientID;
    private Date dateOfBirth;
    private BloodType bloodType;
    private MaritalStatus maritalStatus;
    

    // More complex information on patients
    private ArrayList<Date> datesVisited = new ArrayList<Date>();



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
        maritalStatus = null;
        bloodType = null;
        try{
            assignPatientID();
        }
        catch(IOException e){
            System.out.println("ERROR WITH NUMBER OF RECORDS FILE");
        }
        


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

    
    public Patient(int i_patientID, String i_firstName, String i_lastName, Date i_dateOfBirth, BloodType i_bloodType, MaritalStatus i_maritalStatus){

        patientID = i_patientID;
        firstName = i_firstName;
        lastName = i_lastName;
        dateOfBirth = i_dateOfBirth;
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
        if (recordsFile.exists()){


        Scanner fileInput = new Scanner(recordsFile);
        numOfRecords = fileInput.nextInt();

        //Getting patient ID
        patientID = numOfRecords;
        numOfRecords += 1;

        //Outputing new number of patients to file
        PrintWriter fileOutput = new PrintWriter(recordsFile);
        fileOutput.print(numOfRecords);

        // Closes input and output files
        fileInput.close();
        fileOutput.close();

        }
        else{
            System.out.println("SYSTEM ERROR: RECORDS FILE NOT FOUND");
        }

    } 

    /**
     * Prints out the patients name
     */
    public String toString(){

        return (firstName + " " + lastName);
    }

    /**
     * Returns the patient's age
     * @return
     */
    public int getAge(){
        // Create a local date object using the java time class
        LocalDate currDate = LocalDate.now();
        
        // Current date as a date object
        Date currentDate = new Date(currDate.getMonthValue(), currDate.getDayOfMonth(), currDate.getYear());

        //Finds the difference from the current date to date of birth
        DateDifference dDifference = currentDate.differenceToDate(dateOfBirth);
        
        return dDifference.year();
        
    }

    public void setMaritalStatus(MaritalStatus i_MaritalStatus){

        maritalStatus = i_MaritalStatus;

    }

    public void changeFirstName(String i_firstName){

        firstName = i_firstName;

    }

    public void changeLastName(String i_lastName){

        lastName = i_lastName;

    }

    public void changeName(String i_firstName, String i_lastName){

        changeFirstName(i_firstName);
        changeLastName(i_lastName);

    }

    public void setBloodType(BloodType i_bloodType){

        bloodType = i_bloodType;

    }

    public void addVisitedDate(Date i_date){

        datesVisited.add(i_date);

    }

    public int getPatientID(){

        return patientID;

    }

    public String getFirstName(){

        return firstName;

    }

    public String getLastName(){

        return lastName;

    }

    public Date getDateOfBirth(){

        return dateOfBirth;

    }

    public BloodType getBloodType(){

        return bloodType;

    }

    public MaritalStatus getMaritalStatus(){
        return maritalStatus;
    }

    public void addVisitedDate(int month, int day, int year){

        if (Date.validDate(month, day, year)){
            datesVisited.add(new Date(month, day, year));
        }
        else{
            System.out.println("INVALID VISITED DATE");
        }

    }

    private void recordPatientDates() throws IOException{
        
        FileWriter fw = new FileWriter("HospitalManager\\src\\TextFiles\\VisitedDates.txt", true);
        PrintWriter pw = new PrintWriter(fw);

        pw.print(patientID + " ");

        for (Date date : datesVisited){

            pw.print(date + " ");

        }

        pw.println();
        pw.close();

    }


    public void printDatesVisited(){
        for (Date date: datesVisited){

            System.out.println(date);

        }
    }

    /**
     * Saves all Patient Data in the appropriate locations
     */
    public void savePatientData(){

        


    }



}
