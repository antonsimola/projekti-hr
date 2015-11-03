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
 * @author Anton
 */
public class Model {
    private PropertyChangeSupport propertyChangeSupport;
    private String signedInUsername;
    
    /* Employee Search Results -view allows hiding of employee
     * employment details (+ printing)
     * --> Search results differ from Employee 
     * class as they must include visibility information (model must know what
     * is visible to quarantee desired printing format)
     * --> need for a new class (EmployeeSearchResult)?
    */
    private ArrayList<EmployeeSearchResult> employeeSearchResults;
    
    public Model () {
        propertyChangeSupport = new PropertyChangeSupport(this);
    }
    
    // Logic concerning property change begins ...
    public void addPropertyChangeListener(PropertyChangeListener 
            propertyChangeListener) {
        propertyChangeSupport.addPropertyChangeListener(propertyChangeListener);
    }
    
    public void removePropertyChangeListener(PropertyChangeListener 
            propertyChangeListener) {
        propertyChangeSupport.removePropertyChangeListener(
                propertyChangeListener);
    }
    
    /* Required? Yes: model must be able to transfer altered search results.
     * -Needs more work.
    */
    private void firePropertyChange(String propertyName, Object oldValue, 
            Object newValue) {
        propertyChangeSupport.firePropertyChange(propertyName, oldValue, 
                newValue);
    }
    
    /* Method fires events for database operations.
     * 'results' and 'isSuccessfull' -parameters are partly overlapping but
     * dividing the this doesn't seem to make sense at this implementation
     * scale.
     *
     * -Could be altered to fit all signal cases: operation, results, success?
    */
    private void fireDatabaseOperationResult(String operation, Object results,
            boolean isSuccessful) {
        propertyChangeSupport.firePropertyChange(operation, results, 
                isSuccessful);
    }
    // ...logic concerning property change ends
    
    public void addEmployee(Employee employee) {
        // Call a method for input parametrization.
        
        // Insert into database (separate method?)
        
        // Signal database operation result (call a method for event firing)
    }
    
    public void editEmployee() {
    
    }
    
    public void removeEmployee() {
    
    }
    
    public void searchEmployee() {
    
    }
    
    public void alterEmployeeSearchResultFormatting() {
    
    }
    
    public void someSignInMethod(/*username, pw*/) {
        /*try to match username and pw in database*/
        /*fire event with true or false bundled in event?*/
    }
    
    public void signOut() {

    }
    
    /* Load all employees to an instance variable that can be 'get' when the 
     * program is initializing, and when the UI listing of all employess is
     * subject to change (database insert, update or delete has taken place)
    */
    private void loadAllEmployees() {
    
    }
    
    // Add a method for database input parametrization
    
    // Add methods for database interaction
}
