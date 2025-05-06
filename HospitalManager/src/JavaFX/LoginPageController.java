package JavaFX;

import java.io.IOException;
import java.sql.SQLException;

import MainFiles.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginPageController {

    @FXML private Button loginButton;
    @FXML private TextField user;
    @FXML private PasswordField passcode;
    @FXML private Label errorText;

    @FXML
    public void initialize(){
    }

    public void openMainPage(ActionEvent e){

        String userText = user.getText();
        if (userText.isBlank()){
            errorText.setText("""
                Can't have empty userName
            """);
            return;
        }
        String passcodeText = passcode.getText();
        if (passcodeText.isBlank()){
            errorText.setText("""
                Can't have empty passcode
            """);
            return;
        }


        try{
            Main.loadMainPageFromLogin(userText, passcodeText);
        }catch(IOException | SQLException exception){
            errorText.setText("""
                Login wrong, passcode wrong or Main Page unavailable
            """);
        }
        
    }
    
}
