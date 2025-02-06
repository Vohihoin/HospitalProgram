package DataManagingClasses;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import UtilityClasses.Enums.BloodType;
import UtilityClasses.Enums.MaritalStatus;
import UtilityClasses.Enums.Sex;
import UtilityClasses.General.Date;
import UtilityClasses.General.Patient.Patient;
import UtilityClasses.General.Records.Doctor;
import UtilityClasses.General.Records.DoctorNote;


public class PatientFilesManager {

    private static String patientDataBaseDirectory = "PatientData\\";
    
    /**
     * Creates a directory for a patient's files in the PatientData folder
     * The directory is numbered by the patient's patient ID
     * @param patient the patient for whom we're creating a directory
     * @return true if a new directory was created, false if a new directory wasn't created (probably because it already exists)
     */
    public static boolean createPatientDirectory(Patient patient){
        File dir = new File("PatientData\\" + patient.getPatientID());
        return dir.mkdirs();
    }

    /**
     * Checks if a patient already has a directory. A patient already has a directory if and only if there exists
     * a folder whose name is the patient's patientID
     * @param patient
     * @return true if the patient already has a directory
     */
    public static boolean alreadyHasDirectory(Patient patient){
        File directory = new File("PatientData\\" + patient.getPatientID());
        return directory.exists();
    }

    /**
     * Creates a text file in a patient's directory
     * If the patient doesn't already have a directory, a directory is created for them
     * Then the file with the given name is created if it doesn't already exist
     * 
     * @param fileName
     * @param patient
     * @return true if the text file is successfully created, otherwise false. The only time this should happen is if the file already exists
     */
    public static boolean createTextFile(String fileName, Patient patient){
        if (!alreadyHasDirectory(patient)){
            createPatientDirectory(patient);
        }
        File newFile = new File("PatientData\\" + patient.getPatientID() + "\\" + fileName + ".txt");
        if (newFile.exists()){
            return false;
        }

        // the file writer line creates the new file if it doesn't exist
        try{
            FileWriter f = new FileWriter(newFile);
        }catch(IOException e){
            return false;
        }
        return true;
    }

    /**
     * Gets a file object that can be used to read the file with the given name belonging to the specific patient
     * @param fileName the name of the file we want access to
     * @param patient the patient to whom the file belongs (the patient whose folder contains the file)
     * @return a File object that points to the file or null if the file doesn't exist
     */
    public static File getTextFile(Patient patient, String fileName){
        File file = new File("PatientData\\" + patient.getPatientID() + "\\" + fileName);
        if (!file.exists()){
            return null;
        }

        return file;

    }

    public static void main(String[] args) {
        Patient dummyPatient = new Patient(0, "Vahe", "Ohihoin", new Date(8, 8, 2007), 
        null, null, null);
        System.out.println(getTextFile(dummyPatient, "Appointment1.txt"));
    }


}
