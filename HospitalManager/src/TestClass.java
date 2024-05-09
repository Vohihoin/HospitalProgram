

import java.io.File;
import java.util.Scanner;
import java.time.LocalDate;


import java.io.IOException;

public class TestClass {
    
    public static void main(String[] args) throws IOException{
        
        Date birthDate = new Date(8, 23, 1975);

        Patient me = new Patient("Aigbe", "Ohihoin", birthDate);
        System.out.println(me.age());

    }
}
