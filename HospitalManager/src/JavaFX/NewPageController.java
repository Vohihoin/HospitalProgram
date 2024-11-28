package JavaFX;

import java.util.ArrayList;

import MainFiles.Main;
import UtilityClasses.Enums.BloodType;
import UtilityClasses.Enums.MaritalStatus;
import UtilityClasses.Enums.Sex;
import UtilityClasses.General.Patient.Patient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.time.LocalDate;

public class NewPageController {

    @FXML private ComboBox<BloodType> inputBloodType;
    @FXML private ComboBox<MaritalStatus> inputMaritalStatus;
    @FXML private ComboBox<Sex> inputSex;

    private ObservableList<Patient> possiblePatientsList;
    @FXML private ComboBox<Patient> possiblePatientsComboBox;
    @FXML private TextField lastName;
    @FXML private TextField firstName;
    @FXML private DatePicker dateOfBirth;

    @FXML
    public void initialize(){
        possiblePatientsList = FXCollections.observableArrayList();
        possiblePatientsList.addAll(0, Main.patients);
        possiblePatientsComboBox.setItems(possiblePatientsList);

        

    }

    
    public void updatePossiblePatientsList(){ // will be called on key pressed
        possiblePatientsList.clear();
        possiblePatientsList.addAll(Main.findPatients(firstName.getText(),lastName.getText()));
        if (!(firstName.getText().isBlank() && lastName.getText().isBlank())){
            possiblePatientsComboBox.show();
        }
        
    }
    

    
}
