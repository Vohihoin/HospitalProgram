package UtilityClasses.Exceptions;

/**
 * PatientNotFoundException
 * Custom Exception used when trying to find a patient and he/she isn't found
 * @author Ohihoin Vahe
 */
public class PatientNotFoundException extends RuntimeException{

    public PatientNotFoundException(){}

    public PatientNotFoundException(String message){
        super(message);
    }


    
}
