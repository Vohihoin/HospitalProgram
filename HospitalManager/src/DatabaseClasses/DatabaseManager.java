package DatabaseClasses;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Connection;
import java.sql.Driver;



public class DatabaseManager{

    public static void createConnection(){

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        }
        catch(ClassNotFoundException ex){
            Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}