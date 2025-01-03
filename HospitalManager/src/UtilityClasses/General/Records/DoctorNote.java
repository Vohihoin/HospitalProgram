package UtilityClasses.General.Records;

import UtilityClasses.General.Date;
import UtilityClasses.General.Patient.Patient;
import DataManagingClasses.PatientFilesManager;

public class DoctorNote {

    private Doctor doctor;
    private Patient patient;
    private Date dateCreated; 

    /**
     * Creates a doctor's note associated with the given doctor, patient and date of creation
     * @param doctor
     * @param patient
     * @param dateCreated
     */
    public DoctorNote(Doctor doctor, Patient patient, Date dateCreated){
        this.doctor = doctor;
        this.patient = patient;
        this.dateCreated = dateCreated;

        //DN-FL-#-MM-DD-YYYY
        String noteName = String.format("DN-%s-%d-%s", ("" + patient.getFirstName().charAt(0) + patient.getLastName().charAt(0)),
                                                               patient.getPatientID(),
                                                               dateCreated.toSpecialStringFormat()); 
        PatientFilesManager.createTextFile(noteName, patient);
    }

    
}
