/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package project.hr;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author g0403053
 */
public class FXMLDocumentSResults implements Initializable {
    @FXML
    private CheckBox firstnameBox;
    @FXML
    private CheckBox cityBox;
    @FXML
    private CheckBox lastnameBox;
    @FXML
    private CheckBox jobBox;
    @FXML
    private CheckBox postalcodeBox;
    @FXML
    private CheckBox wageBox;
    @FXML
    private CheckBox dobBox;
    @FXML
    private CheckBox phonenumBox;
    @FXML
    private CheckBox hoursBox;
    @FXML
    private CheckBox ssnumBox;
    @FXML
    private CheckBox emailBox;
    @FXML
    private CheckBox startdateBox;
    @FXML
    private CheckBox addressBox;
    @FXML
    private CheckBox drinkBox;
    @FXML
    private CheckBox enddateBox;
    @FXML
    private Button filterButton;
    @FXML
    private Button printpdfButton;
    @FXML
    private TableColumn<Employee, String> firstnameColumn;
    @FXML
    private TableColumn<Employee, String> lastnameColumn;
    @FXML
    private TableColumn<Employee, String> dobColumn;
    @FXML
    private TableColumn<Employee, String> ssnumColumn;
    @FXML
    private TableColumn<Employee, String> addressColumn;
    @FXML
    private TableColumn<Employee, String> cityColumn;
    @FXML
    private TableColumn<Employee, String> postalcodeColumn;
    @FXML
    private TableColumn<Employee, String> phonenumColumn;
    @FXML
    private TableColumn<Employee, String> emailColumn;
    @FXML
    private TableColumn<Employee, String> drinkColumn;
    @FXML
    private TableColumn<Employee, String> jobColumn;
    @FXML
    private TableColumn<Employee, String> wageColumn;
    @FXML
    private TableColumn<Employee, String> hoursColumn;
    @FXML
    private TableColumn<Employee, String> startdateColumn;
    @FXML
    private TableColumn<Employee, String> enddateColumn;
    @FXML
    private TableView<Employee> table;
    
    Controller controller = null;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Controller controller = Controller.getInstance();
        controller.registerView(this);
        fillTable(controller.getSearchResults());
    }    
    
    public void fillTable(ObservableList<Employee> employees) {
        firstnameColumn.setCellValueFactory(
            new PropertyValueFactory<Employee,String>("firstName")
        );
        
        lastnameColumn.setCellValueFactory(
            new PropertyValueFactory<Employee,String>("lastName")
        );
        
        dobColumn.setCellValueFactory(
            new PropertyValueFactory<Employee,String>("birthDay")
        );
        
        ssnumColumn.setCellValueFactory(
            new PropertyValueFactory<Employee,String>("ssn")
        );
        
        addressColumn.setCellValueFactory(
            new PropertyValueFactory<Employee,String>("address")
        );
        
        cityColumn.setCellValueFactory(
            new PropertyValueFactory<Employee,String>("city")
        );
        
        postalcodeColumn.setCellValueFactory(
            new PropertyValueFactory<Employee,String>("postal")
        );
        
        phonenumColumn.setCellValueFactory(
            new PropertyValueFactory<Employee,String>("phone")
        );
        
        emailColumn.setCellValueFactory(
            new PropertyValueFactory<Employee,String>("email")
        );
        
        drinkColumn.setCellValueFactory(
            new PropertyValueFactory<Employee,String>("favoriteDrink")
        );
        
        jobColumn.setCellValueFactory(
            new PropertyValueFactory<Employee,String>("jobTitle")
        );
        
        wageColumn.setCellValueFactory(
            new PropertyValueFactory<Employee,String>("jobWage")
        );
        
        hoursColumn.setCellValueFactory(
            new PropertyValueFactory<Employee,String>("weeklyHours")
        );
        
        startdateColumn.setCellValueFactory(
            new PropertyValueFactory<Employee,String>("startDate")
        );
        
        enddateColumn.setCellValueFactory(
            new PropertyValueFactory<Employee,String>("endDate")
        );
        
        
        table.setItems(employees);
        
    }

    @FXML
    private void filterAction(ActionEvent event) {
    }
    
}
