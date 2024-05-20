

import UtilityClasses.General.Date;
import java.io.IOException;
import DatabaseClasses.DatabaseManager;
import UtilityClasses.Enums.MaritalStatus;
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
        System.out.println(me.age());
        int i;

        System.out.println(DatabaseManager.returnResultsTable("patient_info"));

        
    }
}
