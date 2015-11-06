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

/**
 *
 * @author Samuli
 */
public class Model {
    private PropertyChangeSupport propertyChangeSupport;
    private DatabaseHandler databaseHandler;
    private String signedInUsername;
 
    //private ArrayList<EmployeeSearchResult> employeeSearchResults;
    
    public Model () {
        propertyChangeSupport = new PropertyChangeSupport(this);
        try {
        databaseHandler = new DatabaseHandler();
        }
        catch(Exception e) {
        
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
  
    public void signIn(String firstname, String password) { 
        // Database query
        
        // Check query results: was anything returned?
        
        // If valid username + password:
        signedInUsername = firstname;
        
        // For testing
        //Employee employee = new Employee();
        //employee.setLoggedIn(true);
        //employee.setFirstName("aaa");
        
        // Send query result (Employee instance) to listeners
        //fireModelActionResult("login", null, (Object)employee);
    }
    
    // Needed?
    public void signOut() {
        signedInUsername = null;
    }
    
    public void getAllEmployees() {
        ArrayList<Employee> employeeList = null;
        
        try {
            employeeList = databaseHandler.selectEmployee();
        } catch(Exception e) {
        
        }
        
        fireModelActionResult("all_employees", null, employeeList);
    }
    
    public void addEmployee(Employee employee) {
        // Perform database insert, check thrown exceptions
    
        //fireModelActionResult("add", null, (Object)employee / [false/ true]);
    }
    
    public void editEmployee(String socialSecurityNumber) {
        // Perform databse update, check thrown exceptions
    
        //fireModelActionResult("edit", null, (Object)employee);
    }
    
    public void removeEmployee(String socialSecurityNumber) {
        // Perform database delete, check thrown exceptions
        
        //fireModelActionResult("delete", null, true/false);
    }
    
    public void searchEmployee() {
        // Call a variant of a database search method (load all?)
    }
    
//    public Employee searchEmployee(String username, String password) {
//        // Call a variant of a database search method
//        
//        Employee employee = new Employee();
//        
//        return employee;
//    }
    
    public void alterEmployeeSearchResultFormatting() {
        // Alter returned database search results and send them forward with event propagation
    }
}
