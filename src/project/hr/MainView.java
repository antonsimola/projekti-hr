/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.hr;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
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
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

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
    private ListView<Employee> namelist;
    @FXML
    private Button searchButton;
    @FXML
    private Button removeselectedButton;
    @FXML
    private Button addnewButton;
    @FXML
    private Label signedinUserLabel;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        controller = Controller.getInstance();
        controller.registerView(this);
        controller.getAllEmployees();
        Employee signedEmp = controller.getSignedUser();
        if (signedEmp.getRights() == 1) {
            addnewButton.setDisable(true);
            removeselectedButton.setDisable(true);
            signedinUserLabel.setText("Kirjautuneena: " + 
                    signedEmp.getFirstName() + " " + signedEmp.getLastName() +
                    " (Ei muokkausoikeuksia)");
        }
        else {
            signedinUserLabel.setText("Kirjautuneena: " + 
                    signedEmp.getFirstName() + " " + signedEmp.getLastName());
        }
    }    
    public void updateEmployeeList(ArrayList <Employee> emplist) {
        for(Employee e: emplist) {
            namelist.getItems().add(e);
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
        
    }

    @FXML
    private void logoutAction(ActionEvent event) {
        //LOG OUT USER
        
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("FXMLDocumentLogin.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(LoginWindow.class.getName()).log(Level.SEVERE, null, ex);
        }

        //hide current window
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }

    @FXML
    private void exitAction(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    private void helpAction(ActionEvent event) {
        // OPEN HELP WINDOW
    }

    @FXML
    private void employeeSelected(MouseEvent event) {
        
        try {
            controller.setCurrentlySelectedEmployee(
                    namelist.getSelectionModel().getSelectedItem());
            container.getChildren().clear();
            container.getChildren().add((Node)FXMLLoader.load(getClass().getResource("FXMLDocumentEdit.fxml")));
        } catch (IOException ex) {
            Logger.getLogger(MainView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
