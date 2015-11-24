/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package project.hr;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

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
    private void firstnameFilter(ActionEvent event) {
        if(firstnameBox.isSelected()) {
            firstnameColumn.setVisible(true);
        } else {
            firstnameColumn.setVisible(false);
        }
    }

    @FXML
    private void cityFilter(ActionEvent event) {
        if(cityBox.isSelected()) {
            cityColumn.setVisible(true);
        } else {
            cityColumn.setVisible(false);
        }
    }

    @FXML
    private void lastnameFilter(ActionEvent event) {
        if(lastnameBox.isSelected()) {
            lastnameColumn.setVisible(true);
        } else {
            lastnameColumn.setVisible(false);
        }
    }

    @FXML
    private void jobFilter(ActionEvent event) {
        if(jobBox.isSelected()) {
            jobColumn.setVisible(true);
        } else {
            jobColumn.setVisible(false);
        }
    }

    @FXML
    private void postalcodeFilter(ActionEvent event) {
        if(postalcodeBox.isSelected()) {
            postalcodeColumn.setVisible(true);
        } else {
            postalcodeColumn.setVisible(false);
        }
    }

    @FXML
    private void wageFilter(ActionEvent event) {
        if(wageBox.isSelected()) {
            wageColumn.setVisible(true);
        } else {
            wageColumn.setVisible(false);
        }
    }

    @FXML
    private void dobFilter(ActionEvent event) {
        if(dobBox.isSelected()) {
            dobColumn.setVisible(true);
        } else {
            dobColumn.setVisible(false);
        }
    }

    @FXML
    private void phonenumFilter(ActionEvent event) {
        if(phonenumBox.isSelected()) {
            phonenumColumn.setVisible(true);
        } else {
            phonenumColumn.setVisible(false);
        }
    }

    @FXML
    private void hoursFilter(ActionEvent event) {
        if(hoursBox.isSelected()) {
            hoursColumn.setVisible(true);
        } else {
            hoursColumn.setVisible(false);
        }
    }

    @FXML
    private void ssnumFilter(ActionEvent event) {
        if(ssnumBox.isSelected()) {
            ssnumColumn.setVisible(true);
        } else {
            ssnumColumn.setVisible(false);
        }
    }

    @FXML
    private void emailFilter(ActionEvent event) {
        if(emailBox.isSelected()) {
            emailColumn.setVisible(true);
        } else {
            emailColumn.setVisible(false);
        }
    }

    @FXML
    private void startdateFilter(ActionEvent event) {
        if(startdateBox.isSelected()) {
            startdateColumn.setVisible(true);
        } else {
            startdateColumn.setVisible(false);
        }
    }

    @FXML
    private void addressFilter(ActionEvent event) {
        if(addressBox.isSelected()) {
            addressColumn.setVisible(true);
        } else {
            addressColumn.setVisible(false);
        }
    }

    @FXML
    private void drinkFilter(ActionEvent event) {
        if(drinkBox.isSelected()) {
            drinkColumn.setVisible(true);
        } else {
            drinkColumn.setVisible(false);
        }
    }

    @FXML
    private void enddateFilter(ActionEvent event) {
        if(enddateBox.isSelected()) {
            enddateColumn.setVisible(true);
        } else {
            enddateColumn.setVisible(false);
        }
    }

    @FXML
    private void printpdfAction(ActionEvent event) {
        HashMap selectedColumns = new HashMap<String, Boolean>();
        selectedColumns.put("firstname", firstnameBox.isSelected());
        selectedColumns.put("lastname", lastnameBox.isSelected());
        selectedColumns.put("birthDay", dobBox.isSelected());
        selectedColumns.put("ssn", ssnumBox.isSelected());
        selectedColumns.put("address", addressBox.isSelected());
        selectedColumns.put("city", cityBox.isSelected());
        selectedColumns.put("postal", postalcodeBox.isSelected());
        selectedColumns.put("phone", phonenumBox.isSelected());
        selectedColumns.put("email", emailBox.isSelected());
        selectedColumns.put("favoriteDrink", drinkBox.isSelected());
        selectedColumns.put("jobTitle", jobBox.isSelected());
        selectedColumns.put("jobWage", wageBox.isSelected());
        selectedColumns.put("weeklyHours", hoursBox.isSelected());
        selectedColumns.put("startDate", startdateBox.isSelected());
        selectedColumns.put("endDate", enddateBox.isSelected());

        controller.createPDF(table.getItems(), selectedColumns);
    }
    
}
