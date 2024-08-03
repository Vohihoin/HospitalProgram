package UtilityClasses.Enums;

/**
 * Enum for keeping record of the patient's sex
 * 
 * @author Ohihoin Vahe
 */
public enum Sex {

    MALE,
    FEMALE;

    public static Sex sexFromDBString(String databaseString){

        if (databaseString.equalsIgnoreCase("M")){
            return MALE;
        }
        else if (databaseString.equalsIgnoreCase("F")){
            return FEMALE;
        }
        else{
            return null;
        }

    }

    /**
     * Returns a string representing the sex in the ideal form to be stored in the database.
     * Returns:
     * 
     * M if MALE,
     * F if FEMALE
     * @return String
     */
    public String toDatabaseString(){

        if (this == MALE){
            return "M";
        }
        else{
            return "F";
        }

    }

}
