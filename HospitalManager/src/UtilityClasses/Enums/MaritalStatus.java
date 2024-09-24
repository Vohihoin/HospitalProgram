package UtilityClasses.Enums;
/**
 * MaritalStatus
 * Enum used to represent a person's marital status
 * @author Ohihoin Vahe
 */
public enum MaritalStatus{

    SINGLE,
    MARRIED,
    SEPARATED,
    DIVORCED;

    /**
     * Returns a string representing the marital status in the ideal form to be stored in the database
     * @return S if single,
     *  M if married,
     * P if separated,
     * D if divorced
     */
    public String toDatabaseString(){

        if (this == SINGLE){
            return "S";
        }
        else if (this == DIVORCED){
            return "D";
        }
        else if (this == SEPARATED){
            return "P";
        }
        else{
            return "M";
        }

    }

    public static MaritalStatus maritalStatusFromDBString(String databaseString){

        if (databaseString.equals("S")){
            return SINGLE;
        }
        else if (databaseString.equals("M")){
            return MARRIED;
        }
        else if (databaseString.equals("D")){
            return DIVORCED;
        }
        else if (databaseString.equals("P")){
            return SEPARATED;
        }
        else{
            return null;
        }

    }

}