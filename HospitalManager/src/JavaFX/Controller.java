package JavaFX;

import java.io.IOException;
import java.net.URL;

import MainFiles.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Controller {

    
    public Stage stage;
    private Scene scene;

    private URL addPatientPage =  getClass().getResource("AddPatientPage.fxml");
    private URL findPatientPage = getClass().getResource("FindPatientPage.fxml");
    private URL updatePatientPage = getClass().getResource("UpdatePatientPage.fxml");
    private URL deletePatientPage = getClass().getResource("DeletePatientPage.fxml");


    // FIND PATIENT PAGE
    @FXML private TextField FPP_firstName;
    @FXML private TextField FPP_lastName;

    public Controller(){
        stage = Main.primStage;
        scene = Main.currentScene;
    }

    /**
     * Switches the scene to the add patient page
     * @param e
     */
    public void switchToAddPatientPage(ActionEvent e){

        try{        
            Parent root = FXMLLoader.load(addPatientPage);
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        }catch(IOException exception){
            System.err.println("Add Patient Page Not Found");
        }

    }

    /**
     * Switches the scene to the find patient page
     * @param e the incoming event
     */
    public void switchToFindPatientPage(ActionEvent e){

        try{

            Parent root = FXMLLoader.load(findPatientPage);
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            
        }catch(IOException exception){
            System.err.println("Add Patient Page Not Found");
        }

    }

    public void switchToDeletePatientPage(ActionEvent e){

        try{

            Parent root = FXMLLoader.load(deletePatientPage);
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            
        }catch(IOException exception){
            System.err.println("Add Patient Page Not Found");
        }

    }

    /**
     * 
     * @param e
     */
    public void switchToUpdatePatientPage(ActionEvent e){

        try{

            Parent root = FXMLLoader.load(updatePatientPage);
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            
        }catch(IOException exception){
            System.err.println("Update Patient Page Not Found");
        }

    }

    public void addPatient(ActionEvent e){
        System.out.println("sup");
    }


    public void printText(ActionEvent e){
        System.out.println(FPP_firstName.getText() + " " + FPP_lastName.getText());
    }

    public void updatePossiblePatientsList(){
        
    }
    
}
