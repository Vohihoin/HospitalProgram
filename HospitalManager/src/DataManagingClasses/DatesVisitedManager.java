package DataManagingClasses;

import java.io.FileWriter;
import java.io.IOException;

public class DatesVisitedManager {

    private static String fileAddress = "HospitalManager\\src\\TextFiles\\VisitedDates.txt";
    
    public static void clearFile() throws IOException{
        FileWriter fw = new FileWriter(fileAddress);
        fw.close();
    }

}
