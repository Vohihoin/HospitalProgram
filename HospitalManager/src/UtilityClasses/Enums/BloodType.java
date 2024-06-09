package UtilityClasses.Enums;

public enum BloodType {
    
    A_POSITIVE,
    B_POSITIVE,
    AB_POSITIVE,
    O_POSITIVE,
    A_NEGATIVE,
    B_NEGATIVE,
    AB_NEGATIVE,
    O_NEGATIVE;

    public String toDatabaseString(){

        if (this == A_POSITIVE){
            return "A+";
        }
        else if (this == B_POSITIVE){
            return "B+";
        }
        else if (this == AB_POSITIVE){
            return "AB+";
        }
        else if (this == O_POSITIVE){
            return "O+";
        }
        else if (this == A_NEGATIVE){
            return "A-";
        }
        else if (this == B_NEGATIVE){
            return "B-";
        }
        else if (this == AB_NEGATIVE){
            return "AB-";
        }
        else{
            return "O-";
        }

    }

    public static BloodType bloodTypeFromDBString(String databaseString){

        if (databaseString.equals("A+")){
            return A_POSITIVE;
        }
        else if (databaseString.equals("A-")){
            return A_NEGATIVE;
        }
        else if (databaseString.equals("B+")){
            return B_POSITIVE;
        }
        else if (databaseString.equals("B-")){
            return B_NEGATIVE;
        }
        else if (databaseString.equals("AB+")){
            return AB_POSITIVE;
        }
        else if (databaseString.equals("AB-")){
            return AB_NEGATIVE;
        }
        else if (databaseString.equals("O+")){
            return O_POSITIVE;
        }
        else if (databaseString.equals("O-")){
            return O_NEGATIVE;
        }
        else{
            return null;
        }
    

    }



}



