package UtilityClasses.Enums;

public enum MaritalStatus{

    SINGLE,
    MARRIED;

    /**
     * Returns a string representing the marital status in the ideal form to be stored in the database
     * @return
     */
    public String toDatabaseString(){

        if (this == SINGLE){
            return "S";
        }
        else{
            return "M";
        }

    }

}