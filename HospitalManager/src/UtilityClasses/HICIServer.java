package UtilityClasses;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpHandler;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;

import com.sun.net.httpserver.HttpExchange;

public class HICIServer{


    public static void main(String[] args) throws IOException{

        HttpServer server = HttpServer.create(new InetSocketAddress(80), 0);
        HttpContext mainContext = server.createContext("healthcare");
        mainContext.setHandler(new HttpHandler(){

            @Override
            public void handle(HttpExchange exchange) throws IOException{

		        String path = exchange.getRequestURI().getPath();
		        String queryString = exchange.getRequestURI().getQuery();
                // Runtime.getRuntime().exec(args, null);


                // the possible paths that can be requested are:

                /* doctor's notes - dn */ 

                // -- record of all available doctor's notes (record)
                // -- a specific doctor's note text based on date (note)
                
                
                /* test results - tr*/

                /* prescriptions - presc*/

                path = path.substring(path.indexOf("healthcare/")+"healthcare/".length());
                
                if (path.startsWith("dn")){ // Doctor's note

                    path = path.substring(path.indexOf("dn/") + 3);
                    if (path.startsWith("record")){
                        // to collect the records of doctor's notes, you need 5 things:
                        // username, passcode, patient's firstname, lastname, and id
                        List<String> args = Arrays.asList(queryString.split("&"));
                        try{
                            if (!(args.get(0).startsWith("username=")
                            && args.get(1).startsWith("passcode=")
                            && args.get(2).startsWith("firstname=")
                            && args.get(3).startsWith("lastname=")
                            && args.get(4).startsWith("id=")))
                            {
                                exchange.sendResponseHeaders(404, 0);
                                return;
                            }
                        }catch (IndexOutOfBoundsException e) {
                            exchange.sendResponseHeaders(404, 0);
                            return;
                        }
                    


                        String username = args.get(0).split("username=")[1];
                        String passcode = args.get(1).split("passcode=")[1];
                        String firstname = args.get(2).split("firstname=")[1];
                        String lastname = args.get(3).split("lastname=")[1];
                        int id = Integer.parseInt(args.get(3).split("id=")[1]);
                        

                        // make sure the username and passcode are valid
                        if (!isValid(username, passcode)){
                            exchange.sendResponseHeaders(404, 0);
                            return;
                        }

                        // and that the patient exist
                        if (!patientExists(firstname, lastname, id)){
                            exchange.sendResponseHeaders(404, 0);
                            return;
                        }
                        // 
                        //   Patients
                        //   ---> ID_FIRSTNAME_LASTNAME
                        //        ---> doctors_notes_list


                        String filepath = String.format("../Patients/%d_%s_%s", id, firstname.toUpperCase(), lastname.toUpperCase());
                        File list_of_doctors_notes = new File(filepath);

                        exchange.getResponseHeaders().add("ContentType", "text/plain");
                        exchange.sendResponseHeaders(200, list_of_doctors_notes.length());

                        OutputStream out = exchange.getResponseBody();
                        Files.copy(list_of_doctors_notes.toPath(), out);
                        out.close();

                        
                    }
                    // CASE: Looking for an actual specific note
                    else if (path.startsWith("note")){ 

                        List<String> args = Arrays.asList(queryString.split("&"));
                        try{
                            if (!(args.get(0).startsWith("username=")
                            && args.get(1).startsWith("passcode=")
                            && args.get(2).startsWith("firstname=")
                            && args.get(3).startsWith("lastname=")
                            && args.get(4).startsWith("id=")))
                            {
                                exchange.sendResponseHeaders(404, 0);
                                return;
                            }
                        }catch (IndexOutOfBoundsException e) {
                            exchange.sendResponseHeaders(404, 0);
                            return;
                        }

                        String username = args.get(0).split("username=")[1];
                        String passcode = args.get(1).split("passcode=")[1];
                        String firstname = args.get(2).split("firstname=")[1];
                        String lastname = args.get(3).split("lastname=")[1];
                        int id = Integer.parseInt(args.get(3).split("id=")[1]);
                        

                        // make sure the username and passcode are valid
                        if (!isValid(username, passcode)){
                            exchange.sendResponseHeaders(404, 0);
                            return;
                        }

                        // and that the patient exist
                        if (!patientExists(firstname, lastname, id)){
                            exchange.sendResponseHeaders(404, 0);
                            return;
                        }

                    }else{
                        exchange.sendResponseHeaders(404, 0);
                        return;
                    }
                     

                }else if (path.startsWith("tr")){

                }else if (path.startsWith("presc")) {

                }else{
                    exchange.sendResponseHeaders(404, 0);
                    return;
                }
                

	        }

        });


    }

    public static boolean isValid(String username, String password){
        return true;
    }
    
    public static boolean patientExists(String firstName, String lastName, int id){
        return true;
    }

}