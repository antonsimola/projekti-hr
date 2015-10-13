/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.hr;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 *
 * @author Anton
 */
public class MainView implements Initializable {
    /* Controller instance */
    Controller controller;
    
    @FXML
    private Button button_signin;
    @FXML
    private Label label;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        controller = new Controller(this);
    }    

    /* Example action routine (Did I get this right) -Anton
    Routine starts here*/
    @FXML
    private void signInButtonActionPerformed(ActionEvent event) {
        /* Button pressed -> Call controller's method here*/
        controller.attemptSignIn(/*username,pw*/);
    }
    
    /*controller calls this to tell whether login succeeded or not*/
    public void logIn(/*boolean*/) {
        /*update view (switch from login screen to main view, or login failed)*/
    }
    
}
