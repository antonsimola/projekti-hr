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
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author g0403053
 */
public class FXMLDocumentAddView implements Initializable {
    Controller controller;
    
    @FXML
    private TextField firstnameField;
    @FXML
    private TextField lastnameField;
    @FXML
    private TextField dobField;
    @FXML
    private TextField ssnumField;
    @FXML
    private TextField addressField;
    @FXML
    private TextField cityField;
    @FXML
    private TextField postalcodeField;
    @FXML
    private TextField phonenumField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField drinkField;
    @FXML
    private TextField jobField;
    @FXML
    private TextField wageField;
    @FXML
    private TextField hoursField;
    @FXML
    private TextField startdateField;
    @FXML
    private TextField enddateField;
    @FXML
    private Button saveButton;
    @FXML
    private Label errorLabel;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        controller = Controller.getInstance();
        controller.registerView(this);
    }    

    @FXML
    private void saveEmployeeAction(ActionEvent event) {
        controller.attemptAddEmployee(
                firstnameField.getText(),
                lastnameField.getText(), 
                dobField.getText(), 
                ssnumField.getText(),
                addressField.getText(), 
                postalcodeField.getText(),
                cityField.getText(), 
                phonenumField.getText(), 
                emailField.getText(),
                drinkField.getText(), 
                jobField.getText(),  
                wageField.getText(), 
                startdateField.getText(), 
                enddateField.getText(), 
                hoursField.getText());
    }
    

}
