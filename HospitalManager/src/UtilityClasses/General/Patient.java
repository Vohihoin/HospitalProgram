package UtilityClasses.General;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Scanner;

import DataManagingClasses.DatabaseManager;
import UtilityClasses.Enums.BloodType;
import UtilityClasses.Enums.MaritalStatus;
import UtilityClasses.Enums.Sex;
import UtilityClasses.Exceptions.InvalidInputException;

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
    private Sex sex;
    

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
    public Patient(String i_firstName, String i_lastName, Date i_dateOfBirth) throws IOException, InvalidInputException{

        

        setName(i_firstName, i_lastName);
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
    public Patient(String i_firstName, String i_lastName, Date i_dateOfBirth, BloodType i_bloodType, MaritalStatus i_maritalStatus, Sex i_sex) throws IOException, InvalidInputException{

        this(i_firstName, i_lastName, i_dateOfBirth); // uses the standard patient constructor
        bloodType = i_bloodType;
        maritalStatus = i_maritalStatus;
        sex = i_sex;

    }

    /**
     * Constructor that takes patientID as input. Used when loading in patient objects from database.
     * @param i_patientID
     * @param i_firstName
     * @param i_lastName
     * @param i_dateOfBirth
     * @param i_bloodType
     * @param i_maritalStatus
     * @author Ohihoin Vahe
     */
    public Patient(int i_patientID, String i_firstName, String i_lastName, Date i_dateOfBirth, BloodType i_bloodType, MaritalStatus i_maritalStatus, Sex i_sex){

        patientID = i_patientID;
        firstName = i_firstName.toUpperCase();
        lastName = i_lastName.toUpperCase();
        dateOfBirth = i_dateOfBirth;
        bloodType = i_bloodType;
        maritalStatus = i_maritalStatus;
        sex = i_sex;


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
     * Returns string of patient's name
     */
    public String toString(){

        return (firstName + " " + lastName);
    }

    /**
     * Returns the patient's age using their date of birth
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

    /**
     * Setter method for marital status
     * @param i_MaritalStatus
     */
    public void setMaritalStatus(MaritalStatus i_MaritalStatus){

        maritalStatus = i_MaritalStatus;

    }

    /**
     * Setter method for first name
     * @param i_firstName
     */
    public void setFirstName(String i_firstName) throws InvalidInputException{

        if (i_firstName.isBlank()){
            throw new InvalidInputException("BLANK SPACE INPUT");
        }
        else{
            firstName = i_firstName.toUpperCase();
        }


    }

    /**
     * Setter method for last name
     * @param i_lastName
     */
    public void setLastName(String i_lastName) throws InvalidInputException{

        if (i_lastName.isBlank()){
            throw new InvalidInputException("BLANK SPACE INPUT");
        }
        else{
            lastName = i_lastName.toUpperCase();
        }

    }

    /**
     * Setter method for patient's name
     * @param i_firstName
     * @param i_lastName
     */
    public void setName(String i_firstName, String i_lastName) throws InvalidInputException{

        setFirstName(i_firstName);
        setLastName(i_lastName);

    }

    /**
     * Setter method for patient's blood type
     * @param i_bloodType
     */
    public void setBloodType(BloodType i_bloodType){

        bloodType = i_bloodType;

    }


    /**
     * Setter method for patient's sex
     * @param i_sex
     */
    public void setSex(Sex i_sex){

        sex = i_sex;

    }

    /**
     * Method to add a visited date to the patient object using a date object
     * @param i_date
     */
    public void addVisitedDate(Date i_date){

        datesVisited.add(i_date);

    }

    /**
     * Getter method for the patient's ID
     * @return int
     */
    public int getPatientID(){

        return patientID;

    }

    /**
     * Getter method for the patient's first name
     * @return String
     */
    public String getFirstName(){

        return firstName.toUpperCase();

    }

     /**
      * Getter method for patient's last name
      * @return String
      */
    public String getLastName(){

        return lastName.toUpperCase();

    }

    /**
     * Getter method for patient's date of birth
     * @return Date Object
     */
    public Date getDateOfBirth(){

        return dateOfBirth;

    }

    /**
     * Getter method for patient's blood type
     * @return BloodType Object
     */
    public BloodType getBloodType(){

        return bloodType;

    }

    /**
     * Getter method for patient's marital status
     * @return MaritalStatus Object
     */
    public MaritalStatus getMaritalStatus(){
        return maritalStatus;
    }

    /**
     * Getter method for patient's sex
     * @return Sex Object
     */
    public Sex getSex(){

        return sex;

    }


    /**
     * Adds visited date to patient object using the values of month, day and year
     * @param month
     * @param day
     * @param year
     */
    public void addVisitedDate(int month, int day, int year){

        if (Date.validDate(month, day, year)){
            datesVisited.add(new Date(month, day, year));
        }
        else{
            System.out.println("INVALID VISITED DATE");
        }

    }

    /**
     * Records the patient's visited dates in the visited dates record file
     * @throws IOException
     */
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

    /**
     * Returns a string containing the patient's visited dates, separated by spaces and NOT ending in a space
     * @return String
     */
    public String datesVisited(){

        String datesVisitedString = "";

        for (Date date: datesVisited){

            datesVisitedString += date + " ";

        }


        datesVisitedString = datesVisitedString.substring(0, datesVisitedString.length()-1); // Removes the last space
        return datesVisitedString;
        
    }

    /**
     * Returns true if the patient objects have equal first names, last names, and date of births.
     * Else returns false
     * @param i_patient
     * @return boolean
     */
    public boolean equals(Patient i_patient){

        return(

        i_patient.getFirstName().equalsIgnoreCase(this.firstName) &&
        i_patient.getLastName().equalsIgnoreCase(this.lastName) &&
        i_patient.getDateOfBirth().equals(this.dateOfBirth)

        );

    }

    public void printDatesVisited(){
        System.out.println(datesVisited());
    }

    public ArrayList<Date> getDatesVisitedArrayList(){

        return datesVisited;

    }





}
