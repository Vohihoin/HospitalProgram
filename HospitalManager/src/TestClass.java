import UtilityClasses.General.Date;
import UtilityClasses.General.Patient;

import java.io.IOException;
import DatabaseClasses.DatabaseManager;
import UtilityClasses.Enums.*;
import UtilityClasses.General.*;



import com.mysql.cj.jdbc.Driver;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;


public class TestClass {
    
    public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException{
        
        Date newDate = new Date();

        Patient me = new Patient("Aigbe", "Ohihoin", newDate);
        

        me.addVisitedDate(5, 20, 2024);
        me.addVisitedDate(6,1,2024);



        me.setMaritalStatus(MaritalStatus.MARRIED);
        me.setBloodType(BloodType.A_POSITIVE);


        System.out.println(DatabaseManager.returnResultsTable("patient_info"));
        
    }
}
