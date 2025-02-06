package JavaFX;

import java.util.ArrayList;
import java.util.List;

import com.mysql.cj.result.LongValueFactory;

import MainFiles.Main;
import UtilityClasses.DataStructures.QueueStuff.SizedQueue;
import UtilityClasses.DataStructures.StackStuff.SizedStack;
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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.time.LocalDate;

/**
 * This class is the controller class for the level 1 page
 * The level 1 page is the most basic access for patient data
 * So, you can search through patient data, view the basics of it and add new patients but you can't
 * change or delete records
 */
public class Level1PageController {

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

    // VIEW
    @FXML private Label nameView1;
    @FXML private Label dateOfBirthView1;
    @FXML private Label bloodTypeView1;
    @FXML private Label sexView1;

    @FXML private Label nameView2;
    @FXML private Label dateOfBirthView2;
    @FXML private Label bloodTypeView2;
    @FXML private Label sexView2;

    @FXML private Label nameView3;
    @FXML private Label dateOfBirthView3;
    @FXML private Label bloodTypeView3;
    @FXML private Label sexView3;
    @FXML private Label addErrorLabel;

    @FXML private ListView<Patient> selectedPatientsListView;
    SizedQueue<Patient> viewQueue = new SizedQueue<Patient>(3);


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

        // ALIGNMENT STUFF
        /** ALIGNMENT FOR THE ADDING COMPONENTS*/

        // UPDATING THE VIEW WINDOW TO CLEAR IT
        updateViewWindow();


    }

    /**
     * Called whenever the user tries to view a patient/ patients
     * It updates the view queue and then displays the patients
     */
    public void addToView(ActionEvent e){
        ObservableList<Patient> selectedItems = selectedPatientsListView.getSelectionModel().getSelectedItems();
        if (!(selectedItems.isEmpty())){
            for (int i = 0; i < selectedItems.size(); i++){
                if (!(viewQueue.isFull())){
                    viewQueue.enqueue(selectedItems.get(i));
                }else{
                    viewQueue.dequeue();
                    viewQueue.enqueue(selectedItems.get(i));
                }
            }
        }
        updateViewWindow();
    }


    /**
     * Essentially takes the items in the queue of items to be viewed and adisplays them.
     */
    public void updateViewWindow(){
        ArrayList<Patient> viewedPatients = viewQueue.getList();
        Patient p;

        if (!(viewedPatients.isEmpty())){ //if we have at least one element, we add the first element
            p = viewedPatients.get(0);            
            nameView1.setText(p.getFirstName() + " " + p.getLastName());
            dateOfBirthView1.setText("" + p.getDateOfBirth());
            bloodTypeView1.setText("" + p.getBloodType());
            sexView1.setText("" + p.getSex());
        }

        if (viewedPatients.size() >= 2){
            p = viewedPatients.get(1);            
            nameView2.setText(p.getFirstName() + " " + p.getLastName());
            dateOfBirthView2.setText("" + p.getDateOfBirth());
            bloodTypeView2.setText("" + p.getBloodType());
            sexView2.setText("" + p.getSex());
        }

        if (viewedPatients.size() >= 3){
            p = viewedPatients.get(2);            
            nameView3.setText(p.getFirstName() + " " + p.getLastName());
            dateOfBirthView3.setText("" + p.getDateOfBirth());
            bloodTypeView3.setText("" + p.getBloodType());
            sexView3.setText("" + p.getSex());
        }

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

    /**
     * Checks if a string is multiWorded
     * 
     * @param string the string we're checking
     * @return true if the string is multiworded
     */
    private boolean multiWorded(String string){
        if (string == null){
            return false;
        }
        
        // We check by splitting up the stirng by space characters
        // and if after splitting, we have non-blank strings, that means our string is multi-worded
        String[] split = string.split(" ");
        if (split.length > 1){
            for (int i = 1; i < split.length; i++){
                if (!(split[i].isBlank())){
                    return true;
                }
            }
        }
        return false;
    }

    public void addPatient(ActionEvent event){

        // the minimum criteria required to create a new patient is that we have a valid first name, valid last name and valid 
        // date of birth
        boolean invalidAdd = false;
        String cannotAddReason = "";

        if (dateOfBirthAdd.getValue() == null){
            cannotAddReason += "Invalid Date of Birth\n";
            invalidAdd = true;
        }
        if (firstNameAdd.getText() == null || firstNameAdd.getText().isBlank() || multiWorded(firstNameAdd.getText())){
            cannotAddReason += "Invalid First Name.\nCannot be blank or multi-worded\n";
            invalidAdd = true;
        }

        if (lastNameAdd.getText() == null || firstNameAdd.getText().isBlank() || multiWorded(lastNameAdd.getText())){
            cannotAddReason += "Invalid Last Name.\nCannot be blank or multi-worded \n";
            invalidAdd = true;
        }

        if (invalidAdd){
            displayCannottAdd(cannotAddReason);
            return;
        }

        try{
            Patient toAdd = new Patient(firstNameAdd.getText(), lastNameAdd.getText(), new Date(dateOfBirthAdd.getValue()), bloodTypeAdd.getValue(),maritalStatusAdd.getValue(), sexAdd.getValue());
            if (!(Main.addPatient(toAdd))){
                displayCannottAdd("PATIENT IS A DUPLICATE");
            }
            updatePossiblePatientsList();
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
        

    }

    /**
     * Displays the cannot add message
     * @param reason
     */
    public void displayCannottAdd(String reason){
        addErrorLabel.setText(reason);
    }

    /**
     * Some cool functionality that removes the error message after the user clicks on any of the input points
     * @param event
     */
    public void removeCannotAddMessage(){
        addErrorLabel.setText("");
    }

    /**
     * Adds selected patients to the selected section. Doesn't add patients that are already in the selected section.
     */
    public void select(ActionEvent event){
        List<Patient> selectedPatients = possiblePatientsListView.getSelectionModel().getSelectedItems();
        List<Patient> toAdd = new ArrayList<Patient>();

        for (Patient patient : selectedPatients){
            if (!selectedPatientsListView.getItems().contains(patient)){
                toAdd.add(patient);
            }
        }

        selectedPatientsListView.getItems().addAll(toAdd);
    }

    /**
     * Removes patients from the selected list
     */
    public void removeFromSelected(ActionEvent event){
        ObservableList<Patient> toRemove = selectedPatientsListView.getSelectionModel().getSelectedItems();
        selectedPatientsListView.getItems().removeAll(toRemove);
    }
    

    
}
