/* Preliminary header information: 
 * Model.java - Acts as the model in MVC (Model View Controler) design pattern.
 * Samuli Siitonen
 *
 * Main logic for class interaction is based on an article by Robert Eckstein: 
 * "Java SE Application Design With MVC", March 2007
 * http://www.oracle.com/technetwork/articles/javase/index-142890.html
 */

package project.hr;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Samuli
 */
public class Model {
    private PropertyChangeSupport propertyChangeSupport;
    private PasswordSecurity passwordSecurity;
    private DatabaseHandler databaseHandler;
    private Employee signedInEmployee;
    
    private ArrayList<Employee> employeeSearchResults;
    
    public Model () {
        propertyChangeSupport = new PropertyChangeSupport(this);
        employeeSearchResults = null;
        passwordSecurity = new PasswordSecurity();
        signedInEmployee = null;
        
        try {
            databaseHandler = new DatabaseHandler();
            databaseHandler.connect();
        }
        catch(Exception e) {
            // Log error, send quit/error event
        }
    }
    
    public void addPropertyChangeListener(PropertyChangeListener 
            propertyChangeListener) {
        propertyChangeSupport.addPropertyChangeListener(propertyChangeListener);
    }
    
    public void removePropertyChangeListener(PropertyChangeListener 
            propertyChangeListener) {
        propertyChangeSupport.removePropertyChangeListener(
                propertyChangeListener);
    }
    
    private void fireModelActionResult(String propertyName, Object oldValue,
            Object newValue) {
        
        propertyChangeSupport.firePropertyChange(propertyName, oldValue, 
                newValue);
        
    }
    
    public void registerEmployee(Employee employee, String password) {
        try {
            employee.setPasswordHashAndSalt(
                    passwordSecurity.generateHashedSaltedPassword(password));
            databaseHandler.insertEmployee(employee);
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
    }
        
  
    private Employee searchHumanResourcesEmployeeByEmail(String emailAddress) {
        Employee employee = null;
        
        try {
            employee = databaseHandler.selectHumanResourcesEmployeeByEmail(
                            emailAddress);
        } 
        catch(Exception ex) {
            // Write loh
        }
   
        return employee;
    }
    
    public void signIn(String emailAddress, String password) {
        Employee employee = searchHumanResourcesEmployeeByEmail(emailAddress);
        
        if(employee != null && passwordSecurity.isPasswordValid(password, 
                        employee.getPasswordHashAndSalt())) {
        
            signedInEmployee = employee;
            fireModelActionResult("sign_in", null, employee);
        }
        else {
            fireModelActionResult("sign_in", null, null);
        }
    }
    
    public void signOut() {
        fireModelActionResult("sign_out", signedInEmployee, null);
        signedInEmployee = null;
    }
    
    public void addEmployee(Employee employee) {
        boolean isSuccessfull = true;
        
        try {
            databaseHandler.insertEmployee(employee);
    
        } catch (Exception ex) {
            ex.printStackTrace();
            isSuccessfull = false;
            // Write log
        }
        
        fireModelActionResult("add", null, isSuccessfull);
    }
    
    public void editEmployee(String socialSecurityNumber, Employee employee) {
        // Perform databse update, check thrown exceptions
    
        //fireModelActionResult("edit", null, (Object)employee);
    }
    
    public void removeEmployee(String socialSecurityNumber) {
        // Perform database delete, check thrown exceptions
        
        //fireModelActionResult("delete", null, true/false);
    }
    
     public void alterEmployeeSearchResultFormatting(/*Formatting options*/) {
        // Alter returned database search results and send them forward with event propagation
        // (employeeSearchResults)
    }
    
    // Should store search results in case the user wants to alter them
    // (calls alterEmployeeSearchResultFormatting on an instance variable)
    public ArrayList<Employee> searchEmployee(Employee employee) {
        employeeSearchResults = new ArrayList();
        
        return new ArrayList(); // Placeholder ()
    }
    
    public void getAllEmployees() {
        ArrayList<Employee> employeeList = null;
        
        try {
            employeeList = databaseHandler.selectAllEmployees();
        } catch(Exception e) {
            e.printStackTrace();
            // Write log
        }

        fireModelActionResult("all_employees", null, employeeList);
    }
}
