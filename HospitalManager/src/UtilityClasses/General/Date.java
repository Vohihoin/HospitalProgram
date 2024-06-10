package UtilityClasses.General;

public class Date {

    
    
    private int day;
    private int month;
    private int year;

    public Date(){
        day = 1;
        month = 1;
        year = 2000;
    }

    /**Constructor for Date Object
     * 
     * @author Ohihoin Vahe
     * @param i_month Input Month
     * @param i_day Input Date
     * @param i_year Input Year 
     */
    public Date(int i_month, int i_day, int i_year){

    
        if (i_month <= 12 && i_month >= 1){
            month = i_month;
        }
        else{
            month = 1;
        }

        if (i_year >= 0){
            year = i_year;
        }
        else{
            year = 2000;
        }

        boolean thirtyMonth = (month == 9) || (month == 4) || (month == 6) || (month == 11);
        boolean thirtyOneMonth = (month == 1) || (month == 3) || (month == 5) || (month == 7) || (month == 8) || (month == 10) ||
                                 (month == 12);
        boolean twentyEightMonth = (month == 2) && ( (year % 4) != 0 );
        boolean twentyNineMonth = (month == 2) &&  (
                                 ( (year % 100 != 0) && ( (year % 4)  == 0)  ) ||
                                 ( (year % 400) == 0)
                                 );
    
        if (i_day < 1){

            day = 1;

        }
        else{

            if (thirtyMonth){
                if (i_day <= 30){
                    day = i_day;
                }
                else{
                    day = 1;
                }
            }

            if (thirtyOneMonth){
                if (i_day <= 31){
                    day = i_day;
                }
                else{
                    day = 1;
                }
            }

            if (twentyEightMonth){
                if (i_day <= 28){
                    day = i_day;
                }
                else{
                    day = 1;
                }
            }

            if (twentyNineMonth){

                if (i_day <= 29){
                    day = i_day;
                }
                else{
                    day = 1;
                }

            }

        }


    }

    public int day(){
        return day;
    }

    public int month(){
        return month;
    }

    public int year(){
        return year;
    }


    /** Returns a String For the Date in MM/DD/YYYY format
     * 
     */
    public String toString(){
        String dayString;
        String monthString;
        String yearString;

        // Puts the one digit values as double digits
        if (day < 10){
            dayString = "0" + String.valueOf(day);
        }
        else{
            dayString = String.valueOf(day);
        }

        if (month < 10){
            monthString = "0" + String.valueOf(month);
        }
        else{
            monthString = String.valueOf(month);
        }

        yearString = String.valueOf(year);
        int zeroesRequired = 4 - yearString.length();

        if (zeroesRequired > 0){ // If for some reason, year isn't 4 digit, it adds 0s in front of it.

            for (int i = 0; i < zeroesRequired; i++){
                yearString = "0" + yearString;
            }

        }

        return monthString + "/" + dayString + "/" + yearString;

    }

    public String toDatabaseString(){

        String month = toString().substring(0, 2);
        String day = toString().substring(3,5);
        String year = toString().substring(6,10);

        return year + "-" + month + "-" + day;

    }

    /**
     * Returns a DateDifference Object representing the estimated difference in years, months and days between two date objects
     */
    public DateDifference differenceToDate(Date date2){

        DateDifference diff;
        Date date1 = this;
        Date biggerDate;
        Date smallerDate;
        
        if (date1.year() > date2.year()){
            biggerDate = date1;
            smallerDate = date2;
        }
        else if (date2.year() > date1.year()){
            biggerDate = date2;
            smallerDate = date1;
        }
        else{
            if (date1.month() > date2.month()){
                biggerDate = date1;
                smallerDate = date2;
            }
            else if (date2.month() > date1.month()){
                biggerDate = date2;
                smallerDate = date1;
            }
            else{
                if (date1.day() > date2.day()){
                    biggerDate = date1;
                    smallerDate = date2;
                }
                else if (date2.day() > date1.day()){
                    biggerDate = date2;
                    smallerDate = date1;
                
                }
                else{
                    diff = new DateDifference(0,0,0);
                    return diff;
                }
            }
        }

        int yearDifference;
        int monthDifference;
        int dayDifference;

        yearDifference = biggerDate.year() - smallerDate.year();
        monthDifference = biggerDate.month() - smallerDate.month();

        if (monthDifference < 0){

            yearDifference -= 1;
            monthDifference += 12;

        }

        dayDifference = biggerDate.day() - smallerDate.day();

        if (dayDifference < 0){

            monthDifference -= 1;
            int prevMonth = biggerDate.month - 1;

            if (prevMonth == 0){
                prevMonth = 12;
            }

            dayDifference += numberOfDaysInMonth( prevMonth, biggerDate.year() );

        }

        return new DateDifference(dayDifference, monthDifference, yearDifference);

    }


    private int numberOfDaysInMonth(int month, int year){

        if (month > 12 || month < 1){
            return 0;
        }
        else if (
            month == 9 ||
            month == 4 ||
            month == 6 ||
            month == 11

        ){

            return 30;

        }

        else if (
            month == 1 ||
            month == 3 ||
            month == 5 ||
            month == 7 ||
            month == 8 ||
            month == 10 ||
            month == 12  
        ){
            return 31;
        }

        else{ // month = 2

            if (
                (year % 4 != 0 ) || (year % 100 == 0 && year % 400 != 0) //Rules for not being a leap year
            ){
                return 28;
            }
            else{
                return 29;
            }

        }

    }

    /**
     * Returns true if two date objects are equivalent, else returns false
     * @param i_date
     * @return boolean
     */
    public boolean equals(Date i_date){

        return (
            i_date.day() == this.day() &&
            i_date.month() == this.month() &&
            i_date.year() == this.year()
        );
    }

    /** 
     * Tells you if a date is valid
     * 
     * @param i_month
     * @param i_day
     * @param i_year
     * @return
     */
    public static boolean validDate(int i_month, int i_day, int i_year){

        if (!(i_month <= 12 && i_month >= 1)){
            return false;
        }

        if (!(i_year >= 0)){
            return false;
        }

        boolean thirtyMonth = (i_month == 9) || (i_month == 4) || (i_month == 6) || (i_month == 11);
        boolean thirtyOneMonth = (i_month == 1) || (i_month == 3) || (i_month == 5) || (i_month == 7) || (i_month == 8) || (i_month == 10) ||
                                 (i_month == 12);
        boolean twentyEightMonth = (i_month == 2) && ( (i_year % 4) != 0 );
        boolean twentyNineMonth = (i_month == 2) &&  (
                                 ( (i_year % 100 != 0) && ( (i_year % 4)  == 0)  ) ||
                                 ( (i_year % 400) == 0)
                                 );
    
        if (i_day < 1){

            return false;

        }
        else{

            if (thirtyMonth){
                if (!(i_day <= 30)){
                    return false;
                }
            }

            if (thirtyOneMonth){
                if (!(i_day <= 31)){
                    return false;
                }
            }


            if (twentyEightMonth){
                if (!(i_day <= 28)){
                    return false;
                }
            }


            if (twentyNineMonth){
                if (!(i_day <= 29)){
                    return false;
                }
            }

        }

        return true;

    }

    /**
     * Takes a string of the format date is stored in the database and creates a date object from it
     * @param databaseString
     * @return
     */
    public static Date dateFromDBString(String databaseString){
        int i;

        try{

            int year = Integer.parseInt(databaseString.substring(0,4));
            int month = Integer.parseInt(databaseString.substring(5, 7));
            int day = Integer.parseInt(databaseString.substring(8, 10));

            return (new Date(month, day, year));

        }catch(NumberFormatException ne){

            System.out.println("DATABASE DATE STRING INPUT NOT ACCURATE");
            System.out.println("STACK TRACE:");
            
            StackTraceElement[] elements = Thread.currentThread().getStackTrace();
            for (i = 0; i < elements.length; i++){

                System.out.println(elements[i].toString());

            }
        }

        return null;

    }

    public static Date dateFromTxtFileString(String textString){

        try{
            int month = Integer.parseInt(textString.substring(0,2));
            int day = Integer.parseInt(textString.substring(3, 5));
            int year = Integer.parseInt(textString.substring(6, 10));
            return (new Date(month, day, year));
        }
        catch(NumberFormatException ne){
            return null;
        }


    }


    
    

}


