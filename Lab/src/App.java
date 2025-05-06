import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class App {
    public static void main(String[] args) throws Exception {

        // creating a connection
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch(ClassNotFoundException e){
            System.out.println("Couldn't load jdbc class");
        }

        String databaseName = "hospital_db";
        String instanceName = "cs400projects-455605:us-central1:my-sql-vm";
        String url = String.format("jdbc:mysql://34.45.134.253/%s?cloudSqlInstance=%s", databaseName, instanceName);


        Connection connection = DriverManager.getConnection(url, "CEO", "#englog1N12345");

        Statement workingStatement = connection.createStatement();

        ResultSet intResults = workingStatement.executeQuery("select count(*) from INFORMATION_SCHEMA.columns where table_name = 'test_table'");
        intResults.next();
        int numColumns = intResults.getInt(1);
        ResultSet results = workingStatement.executeQuery("SELECT * FROM test_table");

        System.out.println(getStringFromTable(results, numColumns));

    }

    public static String getStringFromTable(ResultSet results, int columnSize){
        try{
            String returnString = "";
            while (results.next()) {
                for (int i = 1; i <= columnSize; i++){
                    returnString += results.getString(i) + " ";
                }
                returnString += "\n";
            }
            return returnString;
        }catch(SQLException e){
            return "SQL Exception, so couldn't get table";
        }
    }
}
