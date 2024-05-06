package HospitalProgram.src;

/**
 * This class is used internally in the hospital program to represent differences in date
 * that couldn't be represented as traditional dates
 */
public class DateDifference {
    
    private int dayDifference;
    private int monthDifference;
    private int yearDifference;

    

    /**
     * Main constructor for dateDifference objects
     * @param i_dayDifference
     * @param i_monthDifference
     * @param i_yearDifference
     */
    public DateDifference(int i_dayDifference, int i_monthDifference, int i_yearDifference){

        dayDifference = i_dayDifference;
        monthDifference = i_monthDifference;
        yearDifference = i_yearDifference;

    }

    /** Returns a String For the Date Difference in MM/DD/YYYY format
     * 
     */
    public String toString(){

        return (
            dayDifference + " days, " + 
            monthDifference + " months, " +
            yearDifference + " years."
        );

    }
    public int year(){
        return yearDifference;
    }
    public int month(){
        return monthDifference;
    }
    public int day(){

        return dayDifference;
        
    }


}
