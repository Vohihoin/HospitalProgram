

import UtilityClasses.General.Date;
import java.io.IOException;
import DatabaseClasses.DatabaseManager;
import UtilityClasses.Enums.MaritalStatus;

public class TestClass {
    
    public static void main(String[] args) throws IOException{
        
        Date newDate = new Date();

        Patient me = new Patient("Aigbe", "Ohihoin", newDate);
        System.out.println(me.age());
        DatabaseManager.createConnection();

    }
}
