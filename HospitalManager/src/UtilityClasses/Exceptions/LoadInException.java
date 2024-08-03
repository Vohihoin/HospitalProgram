package UtilityClasses.Exceptions;

/**
 * Custom exception for handling errors while loading input from database
 * 
 * @author Ohihoin Vahe
 */
public class LoadInException extends Exception{
    
    public LoadInException(){}

    public LoadInException(String message){

        super(message);

    }

}
