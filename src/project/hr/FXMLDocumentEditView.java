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
public class FXMLDocumentEditView implements Initializable {
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

    Controller controller;
    @FXML
    private Label errorLabel;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        controller = Controller.getInstance();
        controller.registerView(this);
        controller.getAllEmployees();
        Employee signedEmp = controller.getSignedUser();
        if (signedEmp.getRights() == 1) {
            saveButton.setDisable(true);
            firstnameField.setEditable(false);
            lastnameField.setEditable(false);
            dobField.setEditable(false);
            ssnumField.setEditable(false);
            addressField.setEditable(false);
            cityField.setEditable(false);
            postalcodeField.setEditable(false);
            phonenumField.setEditable(false);
            emailField.setEditable(false);
            drinkField.setEditable(false);
            jobField.setEditable(false);
            wageField.setEditable(false);
            hoursField.setEditable(false);
            startdateField.setEditable(false);
            enddateField.setEditable(false);
        }
        Employee emp = controller.getCurrentlySelectedEmployee();
        firstnameField.setText(emp.getFirstName());
        lastnameField.setText(emp.getLastName());
        dobField.setText(emp.getBirthDay());
        ssnumField.setText(emp.getSsn());
        addressField.setText(emp.getAddress());
        cityField.setText(emp.getCity());
        postalcodeField.setText(emp.getPostal());
        phonenumField.setText(emp.getPhone());
        emailField.setText(emp.getEmail());
        drinkField.setText(emp.getFavoriteDrink());
        jobField.setText(emp.getJobTitle());
        wageField.setText(String.valueOf(emp.getJobWage()));
        hoursField.setText(String.valueOf(emp.getWeeklyHours()));
        startdateField.setText(emp.getStartDate());
        enddateField.setText(emp.getEndDate());
        
        
        
        
    }    
    
    @FXML
    private void saveChanges (ActionEvent event) {
        controller.attemptUpdateEmployee(firstnameField.getText(), 
                lastnameField.getText(),
                dobField.getText(),
                ssnumField.getText(),
                addressField.getText(),
                cityField.getText(),
                postalcodeField.getText(),
                phonenumField.getText(),
                emailField.getText(),
                drinkField.getText(),
                jobField.getText(),
                wageField.getText(),
                startdateField.getText(),
                enddateField.getText(),
                hoursField.getText());
    }
    
    public void updateFields (Employee emp) {
 
    }
    
    public void updateFinished (Boolean success) {
        if (success == true) {
            errorLabel.setText("Jokin meni pieleen :(");
        }
        else {
            errorLabel.setText("Tallennus onnistui");
        }
    }
}
