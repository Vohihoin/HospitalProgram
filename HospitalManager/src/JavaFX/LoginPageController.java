package JavaFX;

import java.io.IOException;

import MainFiles.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

public class LoginPageController {

    @FXML private Button loginButton;

    public void openMainPage(ActionEvent e){
        
        try{
            String url = "..//JavaFX//Level1Page.fxml";
            Parent baseRoot = FXMLLoader.load(getClass().getResource(url));
            Scene mainPageScene = new Scene(baseRoot);
            Main.setScene(mainPageScene);
        }catch(IOException exception){
            System.out.println("IOException with opening the main page");
        }
    }
    
}
