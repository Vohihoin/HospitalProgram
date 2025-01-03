package UtilityClasses.Exceptions;

/**
 * InvalidDateException:
 * Exception thrown when invalid day, month, and year values are entered when creating a date object
 * @author Ohihoin Vahe
 */
public class InvalidDateException extends RuntimeException{

    public InvalidDateException(){}

    public InvalidDateException(String message){
        super(message);
    }

}
