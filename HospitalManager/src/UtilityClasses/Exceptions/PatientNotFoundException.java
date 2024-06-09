package UtilityClasses.Exceptions;

public class PatientNotFoundException extends Exception{

    public PatientNotFoundException(){}

    public PatientNotFoundException(String message){
        super(message);
    }
    
}
