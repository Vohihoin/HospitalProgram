package JavaFX;

import java.util.ArrayList;
import java.util.List;

import com.mysql.cj.result.LongValueFactory;

import MainFiles.Main;
import UtilityClasses.Enums.BloodType;
import UtilityClasses.Enums.MaritalStatus;
import UtilityClasses.Enums.Sex;
import UtilityClasses.Exceptions.InvalidDateException;
import UtilityClasses.Exceptions.InvalidInputException;
import UtilityClasses.General.Date;
import UtilityClasses.General.Patient.Patient;
import UtilityClasses.General.Patient.PatientQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.IOException;
import java.time.LocalDate;

public class NewPageController {

    private ObservableList<Patient> possiblePatientsList;
    @FXML private ListView<Patient> possiblePatientsListView;
    @FXML private TextField lastNameSearch;
    @FXML private TextField firstNameSearch;
    @FXML private DatePicker dateOfBirth;
    @FXML private Button addPatientButton;

    @FXML private TextField firstNameAdd;
    @FXML private TextField lastNameAdd;
    @FXML private DatePicker dateOfBirthAdd;
    @FXML private ComboBox<BloodType> bloodTypeAdd;
    private ObservableList<BloodType> bloodTypes;
    @FXML private ComboBox<MaritalStatus> maritalStatusAdd;
    private ObservableList<MaritalStatus> maritalStatuses;
    @FXML private ComboBox<Sex> sexAdd;
    private ObservableList<Sex> sexes; 

    @FXML private ListView<Patient> selectedPatientsListView;


    @FXML
    public void initialize(){
        possiblePatientsList = possiblePatientsListView.getItems();
        possiblePatientsList.clear();
        possiblePatientsList.addAll(0, Main.patients.asList());

        possiblePatientsListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        selectedPatientsListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        bloodTypes = FXCollections.observableArrayList();
        bloodTypes.addAll(BloodType.values());
        bloodTypes.add(null); // adding a null option to use if the value is unknown
        bloodTypeAdd.setItems(bloodTypes);

        maritalStatuses = FXCollections.observableArrayList();
        maritalStatuses.addAll(MaritalStatus.values());
        maritalStatuses.add(null);
        maritalStatusAdd.setItems(maritalStatuses);

        sexes = FXCollections.observableArrayList();
        sexes.addAll(Sex.values());
        sexes.add(null);
        sexAdd.setItems(sexes);

    }

    
    public void updatePossiblePatientsList(){ // will be called on key pressed
        
        // Field formatting and retreival

        // Date formatting and retrieval
        LocalDate date = dateOfBirth.getValue();
        Date searchingDate = null;
        if (date != null){
            try{
                searchingDate = new Date(date.getMonthValue(), date.getDayOfMonth(), date.getYear());
            }catch(InvalidDateException e){
            }
        }

        String firstNameString = firstNameSearch.getText();
        if (firstNameString != null){
            firstNameString = (firstNameSearch.getText().isBlank()) ? null : firstNameSearch.getText();
        }

        String lastNameString = lastNameSearch.getText();
        if (lastNameString != null){
            lastNameString = (lastNameSearch.getText().isBlank()) ? null : lastNameSearch.getText();
        }

        
        
        
        // Creating the query
        PatientQuery query = new PatientQuery();
        query.setFirstName(firstNameString);
        query.setLastName(lastNameString);
        query.setDateOfBirth(searchingDate);
        //System.out.println(query);

        // Clearing the patients list and adding all the patients that match the query
        possiblePatientsList.clear();
        possiblePatientsList.addAll(Main.findPatientsWithQuery(query));

    }

    public void addPatient(){

        // the minimum criteria required to create a new patient is that we have a valid first name, valid last name and valid 
        // date of birth
        boolean invalidAdd = false;

        if (dateOfBirthAdd.getValue() == null){
            displayCannottAdd("");
            invalidAdd = true;
        }
        if (firstNameAdd.getText() == null || firstNameAdd.getText().isBlank()){
            displayCannottAdd("");
            invalidAdd = true;
        }

        if (lastNameAdd.getText() == null || firstNameAdd.getText().isBlank()){
            displayCannottAdd("");
            invalidAdd = true;
        }

        if (invalidAdd){
            return;
        }

        try{
            Patient toAdd = new Patient(firstNameAdd.getText(), lastNameAdd.getText(), new Date(dateOfBirthAdd.getValue()), bloodTypeAdd.getValue(),maritalStatusAdd.getValue(), sexAdd.getValue());
            Main.addPatient(toAdd);
            updatePossiblePatientsList();
        }catch(IOException e){
        }catch(InvalidInputException e){
        }
        

    }

    public void displayCannottAdd(String reason){

    }

    /**
     * Adds selected patients to the selection section
     */
    public void select(){
        selectedPatientsListView.getItems().addAll(possiblePatientsListView.getSelectionModel().getSelectedItems());
    }

    /**
     * Removes patients from the selected list
     */
    public void removeFromSelected(){
        ObservableList<Patient> toRemove = selectedPatientsListView.getSelectionModel().getSelectedItems();

        for (int i = 0; i < toRemove.size(); i++){
            Patient p = toRemove.get(i);
            if (!(selectedPatientsListView.getItems().isEmpty())){
                selectedPatientsListView.getItems().remove(p);
            }
        }
        
    }
    

    
}
