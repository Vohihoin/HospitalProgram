package MainFiles;

import UtilityClasses.Exceptions.InvalidDateException;
import UtilityClasses.General.Date;
import java.sql.SQLException;
import java.io.IOException;
import UtilityClasses.General.DateDifference;

public class TestClass {
    
    public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException, InvalidDateException{

        Date date1 = new Date(8,8,2007);
        Date date2 = new Date(10,13,2024);
        


        DateDifference newDateDifference = new DateDifference(date1, date2);
        System.out.println(newDateDifference);
        System.out.println(newDateDifference.magnitude());

    }
}
