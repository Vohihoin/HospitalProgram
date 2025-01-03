package UtilityClasses.Exceptions;

/**
 * LoadInException
 * Custom exception for handling errors while loading inputs from database
 * @author Ohihoin Vahe
 */
public class LoadInException extends RuntimeException{
    
    String patientName; // Used if loading errors occur when loading a particular patient

    public LoadInException(){}

    public LoadInException(String message){

        super(message);

    }

    public LoadInException(String message, Throwable cause, String firstName, String lastName){
        super(message, cause);
        patientName = firstName + " " + lastName;

    }

    public String getPatientName(){
        if (patientName != null){
            return patientName;
        }
        else{
            return "Exception Doesn't Involve Patient";
        }
    }
}
