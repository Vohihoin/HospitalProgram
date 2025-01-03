package UtilityClasses.Exceptions;

/**
 * InvalidInputException:
 * Custom exception for handling invalid inputs to methods
 * @author Ohihoin Vahe
 */
public class InvalidInputException extends RuntimeException{

    public InvalidInputException(){}


    public InvalidInputException(String message){
        super(message);
    }


    
}
