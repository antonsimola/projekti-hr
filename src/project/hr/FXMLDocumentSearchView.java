/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package project.hr;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author g0403053
 */
public class FXMLDocumentSearchView implements Initializable {
    @FXML
    private TextField firstnameField;
    @FXML
    private TextField lastnameField;
    @FXML
    private TextField ageField1;
    @FXML
    private TextField ageField2;
    @FXML
    private TextField addressField;
    @FXML
    private TextField cityFIeld;
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
    private TextField startdateField1;
    @FXML
    private TextField startdateField2;
    @FXML
    private TextField enddateField1;
    @FXML
    private TextField enddateField2;
    @FXML
    private Button cancelButton;
    @FXML
    private Button addButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
