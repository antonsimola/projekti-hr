/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.hr;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

/**
 *
 * @author Anton
 */
public class MainView implements Initializable {
    /* Controller instance */
    Controller controller;
    
    @FXML
    private Pane container;
    @FXML
    private MenuItem logoutMenuitem;
    @FXML
    private MenuItem exitMenuitem;
    @FXML
    private MenuItem helpMenuitem;
    @FXML
    private Button showallButton;
    @FXML
    private TextField namesearchField;
    @FXML
    private ListView<?> namelist;
    @FXML
    private Button searchButton;
    @FXML
    private Button removeselectedButton;
    @FXML
    private Button addButton;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        controller = new Controller(this);
    }    

    /*controller calls this to tell whether login succeeded or not*/
    public void logIn(Employee employee) {
        
        if(employee.getLoggedIn()) {
            /*SHOW MAINVIEW*/
        } else {
            /*VÄÄRÄ SALASANA ERROR VIESTI*/
        }
    }

    @FXML
    private void showAddContent(ActionEvent event) throws IOException {
        container.getChildren().clear();
        container.getChildren().add((Node)FXMLLoader.load(getClass().getResource("FXMLDocumentAdd.fxml")));
    }

    @FXML
    private void showSearchContent(ActionEvent event) throws IOException {
        container.getChildren().clear();
        container.getChildren().add((Node)FXMLLoader.load(getClass().getResource("FXMLDocumentSearch.fxml")));
    }

    @FXML
    private void showAllButtonPressed(ActionEvent event) {
        controller.attemptSignIn(/*textUser.getText(),textPassword.getText()*/);
    }
    
}
