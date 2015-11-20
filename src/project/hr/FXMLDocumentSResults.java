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
    private TableColumn<?, ?> lastnameColumn;
    @FXML
    private TableColumn<?, ?> dobColumn;
    @FXML
    private TableColumn<?, ?> ssnumColumn;
    @FXML
    private TableColumn<?, ?> addressColumn;
    @FXML
    private TableColumn<?, ?> cityColumn;
    @FXML
    private TableColumn<?, ?> postalcodeColumn;
    @FXML
    private TableColumn<?, ?> phonenumColumn;
    @FXML
    private TableColumn<?, ?> emailColumn;
    @FXML
    private TableColumn<?, ?> drinkColumn;
    @FXML
    private TableColumn<?, ?> jobColumn;
    @FXML
    private TableColumn<?, ?> wageColumn;
    @FXML
    private TableColumn<?, ?> hoursColumn;
    @FXML
    private TableColumn<?, ?> startdateColumn;
    @FXML
    private TableColumn<?, ?> enddateColumn;
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
        table.setItems(employees);
        
    }
    
}