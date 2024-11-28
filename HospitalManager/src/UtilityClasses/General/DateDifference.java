package UtilityClasses.General;

/**
 * DateDifference
 * This class is used internally in the hospital program to represent differences in date
 * that couldn't be represented as traditional dates
 * @author Ohihoin Vahe
 */
public class DateDifference {
    
    private int dayDifference;
    private int monthDifference;
    private int yearDifference;
    private int sign;

    private static double AVERAGE_DAYS_IN_MONTH = 30.437;

    /**
     * Main constructor for dateDifference objects
     * Assumes the date difference is positive.
     * @param i_dayDifference
     * @param i_monthDifference
     * @param i_yearDifference
     */
    public DateDifference(int i_dayDifference, int i_monthDifference, int i_yearDifference){

        dayDifference = i_dayDifference;
        monthDifference = i_monthDifference;
        yearDifference = i_yearDifference;
        sign = 1;

    }

    /**
     * Other constructor for dateDifference object. 
     * 
     * If the dates are successive (date2 comes after date1), the difference is positive
     * If date2 comes before date1, the difference is negative
     * @param date1
     * @param date2
     */
    public DateDifference(Date date1, Date date2){

        boolean isZero = false;
        
        // we need to know which date is bigger for our calculations
        Date biggerDate = new Date();
        Date smallerDate = new Date();
        
        if (date1.year() > date2.year()){
            biggerDate = date1;
            smallerDate = date2;
            sign = -1;
        }
        else if (date2.year() > date1.year()){
            biggerDate = date2;
            smallerDate = date1;
            sign = 1;
        }
        else{ // they dates are in the same year
            if (date1.month() > date2.month()){
                biggerDate = date1;
                smallerDate = date2;
                sign = -1;
            }
            else if (date2.month() > date1.month()){
                biggerDate = date2;
                smallerDate = date1;
                sign = 1;
            }
            else{ // the dates are in the same year
                if (date1.day() > date2.day()){
                    biggerDate = date1;
                    smallerDate = date2;
                    sign = -1;
                }
                else if (date2.day() > date1.day()){
                    biggerDate = date2;
                    smallerDate = date1;
                    sign = 1;
                }
                else{
                    // we have the exact same date, so our date difference is 0
                    dayDifference = 0;
                    monthDifference = 0;
                    yearDifference = 0;
                    sign = 1;
                    isZero = true;
                }
            }
        }

        if (!(isZero)){ // we then calculate the difference

            yearDifference = biggerDate.year() - smallerDate.year();
            monthDifference = biggerDate.month() - smallerDate.month();

            if (monthDifference < 0){
                // take a year away and add a month
                yearDifference -= 1;
                monthDifference += 12;
            }
    
            dayDifference = biggerDate.day() - smallerDate.day();
    
            if (dayDifference < 0){
    
                monthDifference -= 1;
                int prevMonth = biggerDate.month() - 1;
    
                if (prevMonth == 0){
                    prevMonth = 12;
                }
    
                dayDifference += numberOfDaysInMonth(prevMonth, biggerDate.year() );
    
            }
        }

    }

    /**
     * Private helper method for calculating the number of days in a given month in a given year.
     * @param month
     * @param year
     * @return the number of days in a given month in a given year
     */
    private int numberOfDaysInMonth(int month, int year){

        if (month > 12 || month < 1){
            return 0;
        }
        else if ( month == 9 || month == 4 || month == 6 || month == 11){
            return 30;
        }
        else if ( month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12  ){
            return 31;
        }
        else{ 
            // Month = 2
            // Rules for not being a leap year
            if ((year % 4 != 0 ) || (year % 100 == 0 && year % 400 != 0) ){
                return 28;
            }
            else{
                return 29;
            }
        }

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

    /**
     * Returns the magnitude of the date difference as the approximate amount of dates in this date difference
     * It's approximate because it doesn't take into account for leap years and approximates each month as the average month length
     * @return an int representing the approximate number of days long a date difference is.
     */
    public int magnitude(){
        return (int)( sign * (yearDifference*(365.25) + monthDifference*(AVERAGE_DAYS_IN_MONTH) + dayDifference) );
    }


}
