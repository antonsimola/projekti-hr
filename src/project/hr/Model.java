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
    private String signedInUsername;
 
    //private ArrayList<EmployeeSearchResult> employeeSearchResults;
    
    public Model () {
        propertyChangeSupport = new PropertyChangeSupport(this);
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
  
    public void signIn(String username, String password) {
        String passwordHASH = generatePasswordHASH(password);
        
        // SELECT * FROM CREDENTIALS WHERE username=username AND password_hash=passwordHASH; 
        
        // Check query results
        
        // If valid username + password:
        signedInUsername = username;
        Employee employee = new Employee(/*username, success*/);
        fireModelActionResult("login", null, (Object)employee);
    }
    
    // Needed?
    public void signOut() {
        signedInUsername = null;
    }
    
    private String generatePasswordHASH(String password) {
        
        return "";
    }
    
    public void addEmployee(Employee employee) {
        // INSERT INTO EMPLOYMENT () VALUES ();
        // Get inserted EMPLOYMENT ID --> use int EMPLOYEE INSERT
        // INSERT INTO EMPLOYEE () VALUES();
    
        fireModelActionResult("add", null, (Object)employee);
    }
    
    public void editEmployee(String socialSecurityNumber) {
        // UPDATE EMPLOYMENT and / or EMPLOYEE
    
        //fireModelActionResult("edit", null, (Object)employee);
    }
    
    public void removeEmployee(String socialSecurityNumber) {
        // DELETE EMPLOYMENT and EMPLOYEE records
        
        //fireModelActionResult("delete", null, (Object)employee);
    }
    
    // Searching will have multiple overloaded methods with different parameters
    public void searchEmployee() {
        
    }
    
    public Employee searchEmployee(String username, String password) {
        // SELECT * FROM CREDENTIALS WHERE username=username...
        
        Employee employee = new Employee();
        
        return employee;
    }
    
    public void alterEmployeeSearchResultFormatting() {
    
    }
}
