package UtilityClasses.General.Patient;

import UtilityClasses.DataStructures.QueryStuff.Query;
import UtilityClasses.Enums.BloodType;
import UtilityClasses.Enums.MaritalStatus;
import UtilityClasses.Enums.Sex;
import UtilityClasses.General.Date;
import UtilityClasses.General.DateDifference;

/**
 * We want to be able to query a patient based on the following characteristics:
 * 
 * First Name
 * Last Name
 * Date of Birth
 * Blood Type
 * Marital Status
 * Sex
 * 
 * If any are null, we won't query based on that category
 * 
 */
public class PatientQuery implements Query<Patient>{

    private String firstName;
    private String lastName;
    private Date dateOfBirth;
    private BloodType bloodType;
    private MaritalStatus maritalStatus;
    private Sex sex;

    public PatientQuery(){
        firstName = null;
        lastName = null;
        dateOfBirth = null;
        bloodType = null;
        maritalStatus = null;
        sex = null;
    }

    public PatientQuery(String firstName, String lastName, Date dateOfBirth, BloodType bloodType, MaritalStatus maritalStatus, Sex sex){
        setFirstName(firstName);
        setLastName(lastName);
        setDateOfBirth(dateOfBirth);
        setBloodType(bloodType);
    }

    public void setFirstName(String firstName){
        if (firstName == null){
            this.firstName = null;
        }
        if (firstName.isBlank()){
            throw new IllegalArgumentException();
        }
        this.firstName = firstName;
    }

    public void setLastName(String lastName){
        if (lastName == null){
            this.firstName = null;
        }
        if (lastName.isBlank()){
            throw new IllegalArgumentException();
        }
        this.lastName = lastName;
    }

    public void setDateOfBirth(Date dateOfBirth){
        this.dateOfBirth = dateOfBirth;
    }

    public void setBloodType(BloodType bloodType){
        this.bloodType = bloodType;
    }

    public void setMaritalStatus(MaritalStatus maritalStatus){
        this.maritalStatus = maritalStatus;
    }

    public void setSex(Sex sex){
        this.sex= sex;
    }

    public String getFirstName(){
        return this.firstName;
    }

    public String getLastName(){
        return this.lastName;
    }

    public Date getDateOfBirth(){
        return this.dateOfBirth;
    }

    public BloodType getBloodType(){
        return this.bloodType;
    }

    public MaritalStatus getMaritalStatus(){
        return this.maritalStatus;
    }

    public Sex getSex(){
        return this.sex;
    }

    @Override
    public boolean matches(Patient object) {
        return ( firstName == null || object.getFirstName().toLowerCase().contains(this.firstName.toLowerCase())) &&
               (lastName == null || object.getLastName().toLowerCase().contains(this.lastName.toLowerCase())) &&
               (dateOfBirth == null || dateOfBirth.equals(object.getDateOfBirth())) &&
               (bloodType== null || bloodType.equals(object.getBloodType())) &&
               (maritalStatus == null || maritalStatus.equals(object.getMaritalStatus())) &&
               (sex == null || sex.equals(object.getSex()));
    }

    public int compareTo(Patient otherPatient){

        // Date of Birth is our primary sorting category, so if it is null, we can't do anything
        if (dateOfBirth == null){
            throw new IllegalStateException();
        }

        int dayDifferenceInAge = (new DateDifference(otherPatient.getDateOfBirth(), this.dateOfBirth)).magnitude();
        return dayDifferenceInAge;
        
    }

    

    
}
