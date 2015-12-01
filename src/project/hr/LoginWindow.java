/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.hr;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Hannes
 */
public class LoginWindow implements Initializable {
    Controller controller;
    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;
    @FXML
    private Button loginButton;
    @FXML
    private Label loginerrorLabel;
    private ActionEvent tempEvent;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        controller = Controller.getInstance();
        controller.registerView(this);
    }    
    
    public void logIn(String s) {

        loginerrorLabel.setText(s);
        
    }

    
    public void loginAction() {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("FXMLDocumentMain.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(LoginWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //hide current window
        ((Node)(tempEvent.getSource())).getScene().getWindow().hide();
        tempEvent = null;
    }

    @FXML
    private void loginbuttonPressed(ActionEvent event) {
        tempEvent = event;
        System.out.println(usernameField.getText()+" "+ passwordField.getText());
        controller.attemptSignIn(usernameField.getText(), passwordField.getText());
    }
    
}
