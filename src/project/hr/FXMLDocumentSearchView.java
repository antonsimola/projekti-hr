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
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author g0403053
 */
public class FXMLDocumentSearchView implements Initializable {
    Controller controller;
    
    @FXML
    private TextField firstnameField;
    @FXML
    private TextField lastnameField;
    @FXML
    private TextField addressField;
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
    private TextField wageField1;
    @FXML
    private TextField wageField2;
    @FXML
    private TextField hoursField2;
    @FXML
    private TextField hoursField1;
    @FXML
    private Button cancelButton;
    @FXML
    private Button searchButton;
    @FXML
    private TextField cityField;
    @FXML
    private TextField startdateField;
    @FXML
    private TextField enddateField;
    @FXML
    private TextField dobField1;
    @FXML
    private TextField dobField2;
    @FXML
    private TextField ssnumField;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        controller = Controller.getInstance();
        controller.registerView(this);
    }    

    @FXML
    private void searchAction(ActionEvent event) {
        controller.getAllEmployees();
        controller.searchEmployee(firstnameField.getText(), 
                lastnameField.getText(),
                dobField1.getText(),
                dobField2.getText(),
                ssnumField.getText(),
                addressField.getText(),
                cityField.getText(),
                postalcodeField.getText(),
                phonenumField.getText(),
                emailField.getText(),
                drinkField.getText(),
                jobField.getText(),
                wageField1.getText(),
                wageField2.getText(),
                hoursField1.getText(),
                hoursField2.getText(),
                startdateField.getText(),
                enddateField.getText());
                    Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("FXMLDocumentSResults.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(LoginWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
