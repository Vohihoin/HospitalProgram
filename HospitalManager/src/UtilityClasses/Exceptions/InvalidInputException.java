package UtilityClasses.Exceptions;

/**
 * Custom exception for handling invalid inputs to methods
 * 
 * @author Ohihoin Vahe
 */
public class InvalidInputException extends Exception{

    public InvalidInputException(){}


    public InvalidInputException(String message){
        super(message);
    }
    
}
