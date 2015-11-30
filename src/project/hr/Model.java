/* Preliminary header information: 
 * Model.java - Acts as the model in MVC (Model View Controler) design pattern.
 * Samuli Siitonen
 *
 * Main logic for class interaction is based on an article by Robert Eckstein: 
 * "Java SE Application Design With MVC", March 2007
 * http://www.oracle.com/technetwork/articles/javase/index-142890.html
 */

package project.hr;

import com.itextpdf.text.DocumentException;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;

/**
 *
 * @author Samuli
 */
public class Model {
    private PropertyChangeSupport propertyChangeSupport;
    
    private PasswordSecurity passwordSecurity;
    private DatabaseHandler databaseHandler;
    private FileIOHandler fileIOHandler;
    private LogHandler logHandler;
    private Logger logger;
    
    private Employee signedInEmployee;
    //private ArrayList<Employee> employeeSearchResults;
    
    public Model () {
        propertyChangeSupport = new PropertyChangeSupport(this);
        
        passwordSecurity = new PasswordSecurity();
        signedInEmployee = null;
        
        try {
            fileIOHandler = FileIOHandler.getInstance();
            
            logHandler = LogHandler.getInstance();
            logger = logHandler.getLogger();
            
            databaseHandler = new DatabaseHandler();
            databaseHandler.connect();
        }
        catch(Exception ex) {
            ex.printStackTrace(); // Remove
        }
    }

    public Employee getSignedInEmployee() {
        return signedInEmployee;
    }
    
    public void getUserLog() {
        ArrayList<String[]> userLog = null;
        try {
            userLog = fileIOHandler.readUserLog();
        } catch (IOException ex) {
            logger.log(Level.SEVERE, ex.getMessage());
        }
        finally {
            fireModelActionResult("user_log", null, userLog);
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
        boolean isSuccessful = true;
        
        employee.setPassword(
                   passwordSecurity.generateHashedSaltedPassword(password));
        
        
        try {
            databaseHandler.insertEmployee(employee);  
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, ex.getMessage());
            
            isSuccessful = false;
        }
        finally {
            fireModelActionResult("register", null, isSuccessful);
        }
    }
    
    public void signIn(String emailAddress, String password) {
        Employee employee = searchEmployeeByEmail(emailAddress);
         
        if(employee != null && passwordSecurity.isPasswordValid(password, 
                        employee.getPassword())) {
            
            signedInEmployee = employee;
            fireModelActionResult("sign_in", null, employee);
            
            fileIOHandler.writeUserLog(signedInEmployee.getEmail(), 
                    FileIOHandler.ACTION_SIGN_IN, 
                    FileIOHandler.ACTION_SUCCESS, 
                    signedInEmployee.getEmployeeId(),
                    signedInEmployee.toString(),
                    signedInEmployee.getSsn());       
        }
        else {
            fireModelActionResult("sign_in", null, null);
            
            fileIOHandler.writeUserLog(emailAddress, 
                    FileIOHandler.ACTION_SIGN_IN, 
                    FileIOHandler.ACTION_FAILURE, 
                    FileIOHandler.EMPTY_ID,
                    FileIOHandler.EMPTY,
                    FileIOHandler.EMPTY);
        }
    }
    
    public void signOut() {
        fireModelActionResult("sign_out", signedInEmployee, null);
        signedInEmployee = null;
    }
    
    private Employee searchEmployeeByEmail(String emailAddress) {
        Employee employee = null;
 
        try {
            employee = databaseHandler.selectEmployeeByEmailAddress(
                    emailAddress);
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, ex.getMessage());
        }
        return employee;
    }
    
    public void addEmployee(Employee employee) {
        boolean isSuccessful = true;
        String successString = FileIOHandler.ACTION_SUCCESS;
        
        employee.setPassword(passwordSecurity.generateHashedSaltedPassword(
                            employee.getPassword()));
        
        try {
            databaseHandler.insertEmployee(employee);     
        } catch (SQLException ex) { 
            isSuccessful = false;
            successString = FileIOHandler.ACTION_FAILURE;
            logger.log(Level.SEVERE, ex.getMessage());
        }
        finally {
            fireModelActionResult("add", null, isSuccessful);
            
            fileIOHandler.writeUserLog(signedInEmployee.getEmail(),
            FileIOHandler.ACTION_ADD,
            successString,
            employee.getEmployeeId(),
            employee.toString(),
            employee.getSsn());
        }
    }
    
    public void editEmployee(Employee employee) {
        boolean isSuccessful = true;
        String successString = FileIOHandler.ACTION_SUCCESS;
        
  
        try {
            databaseHandler.updateEmployee(employee);
        } catch (SQLException ex) {
            isSuccessful = false;
            successString = FileIOHandler.ACTION_FAILURE;
            
            logger.log(Level.SEVERE, ex.getMessage());
        }
        finally {
            fireModelActionResult("edit", null, isSuccessful);
            
            fileIOHandler.writeUserLog(signedInEmployee.getEmail(), 
                    FileIOHandler.ACTION_EDIT, 
                    successString, 
                    employee.getEmployeeId(),
                    employee.toString(),
                    employee.getSsn());
        }   
    }
    
    public void removeEmployee(int employeeId) {
        
        boolean isSuccessful = true;
        String successString = FileIOHandler.ACTION_SUCCESS;

        try {
            if (employeeId != signedInEmployee.getEmployeeId())
                databaseHandler.deleteEmployeeByEmployeeId(employeeId);
            else {
                isSuccessful = false;
            }  
        } catch (SQLException ex) {
    
            isSuccessful = false;
            successString = FileIOHandler.ACTION_FAILURE;
            
            logger.log(Level.SEVERE, ex.getMessage());
        }
        finally {
            fireModelActionResult("remove", null, isSuccessful);
            
            fileIOHandler.writeUserLog(signedInEmployee.getEmail(),
            FileIOHandler.ACTION_REMOVE,
            successString,
            employeeId,
            FileIOHandler.EMPTY,
            FileIOHandler.EMPTY);
        }
    }
    
    // Should store search results in case the user wants to alter them
    // (call could be placed to alterEmployeeSearchResultFormatting)
    public void searchEmployee(Employee employee, Employee employeeRangevalues){
        ArrayList<Employee> employeeSearchResults = null;
        String successString = FileIOHandler.ACTION_SUCCESS;
        
        try {
            employeeSearchResults = databaseHandler.selectEmployee(employee, 
                    employeeRangevalues);
            
            if (!(employeeSearchResults.size()> 0))
                employeeSearchResults = null;
        } catch (SQLException ex) {
            ex.printStackTrace(); // Remove
            successString = FileIOHandler.ACTION_FAILURE;
            
            logger.log(Level.SEVERE, ex.getMessage());
        }
        finally {
            fireModelActionResult("search_by_employee", null, 
                employeeSearchResults);
            
            fileIOHandler.writeUserLog(signedInEmployee.getEmail(), 
                    FileIOHandler.ACTION_SEARCH, 
                    successString, 
                    employee.getEmployeeId(),
                    employee.toString(),
                    employee.getSsn());
        }
    }
    
    public void searchEmployee(int employeeId) {
        Employee employee = null;
        String successString = FileIOHandler.ACTION_SUCCESS;
        
        try {
            employee = databaseHandler.selectEmployeeById(employeeId);
        } 
        catch (SQLException ex) {
            successString = FileIOHandler.ACTION_FAILURE;
            
            logger.log(Level.SEVERE, ex.getMessage());
        }
        finally {
            fireModelActionResult("search_by_id", null, 
                employee);
            
            fileIOHandler.writeUserLog(signedInEmployee.getEmail(), 
                    FileIOHandler.ACTION_SEARCH, 
                    successString, 
                    employeeId,
                    FileIOHandler.EMPTY,
                    FileIOHandler.EMPTY);
        }
    }
    
    public void searchEmployee(Employee emp){
        ArrayList<Employee> employeeSearchResults = null;
        
        String successString = FileIOHandler.ACTION_SUCCESS;
        
        try {
            employeeSearchResults = databaseHandler.selectEmployeeByName(emp);
            
            if (!(employeeSearchResults.size()> 0))
                employeeSearchResults = null;
        } catch (SQLException ex) {
            successString = FileIOHandler.ACTION_FAILURE;
            
            logger.log(Level.SEVERE, ex.getMessage());
        }
        finally {
            fireModelActionResult("search_names", null, 
                employeeSearchResults);
            
            fileIOHandler.writeUserLog(signedInEmployee.getEmail(), 
                    FileIOHandler.ACTION_SEARCH, 
                    FileIOHandler.EMPTY, 
                    FileIOHandler.EMPTY_ID,
                    FileIOHandler.EMPTY,
                    FileIOHandler.EMPTY);
        }
    }
    
    /*
    // http://stackoverflow.com/questions/18441846/how-to-sort-an-arraylist-in-java
    public void getSortedEmployeeSearchResults(Sort action) {
        // Alter returned database search results and send them forward with event propagation
        // (employeeSearchResults)
        Comparator comparator = null;
        
        if(action == Sort.FIRST_NAME_DESC) {
            comparator = new Comparator<Employee>() {
                @Override
                public int compare(Employee firstEmployee, 
                        Employee secondEmployee) {
                    return firstEmployee.getFirstName().compareTo(
                            secondEmployee.getFirstName());
                }
            };  
        }
        else if(action == Sort.LAST_NAME_DESC) {
            comparator = new Comparator<Employee>() {
                @Override
                public int compare(Employee firstEmployee, 
                        Employee secondEmployee) {
                    return firstEmployee.getLastName().compareTo(
                            secondEmployee.getLastName());
                }
            };
        }
        else if(action == Sort.HOURLY_WAGE_DESC) {
        
        }
        
        if(comparator != null) {
            Collections.sort(employeeSearchResults, comparator);   
            fireModelActionResult("sort_employee_search_results", null, 
                    employeeSearchResults);
        }
        else {
            fireModelActionResult("sort_employee_search_results", null, null);
        }
    }
    */
    
    public void getAllEmployees() {
        ArrayList<Employee> employeeList = null;
        
        String successString = FileIOHandler.ACTION_SUCCESS;
        
        try {
            employeeList = databaseHandler.selectAllEmployees();
        } catch(SQLException ex) {
            successString = FileIOHandler.ACTION_FAILURE;
            
            logger.log(Level.SEVERE, ex.getMessage());
        }
        finally {
            fireModelActionResult("all_employees", null, employeeList);
            
            fileIOHandler.writeUserLog(signedInEmployee.getEmail(), 
                    FileIOHandler.ACTION_SEARCH, 
                    successString, 
                    FileIOHandler.EMPTY_ID,
                    FileIOHandler.TARGET_ALL,
                    FileIOHandler.TARGET_ALL);
        }      
    }
    
    public void createSearchResultsPDF(ObservableList<Employee> employees, 
            HashMap<String, Boolean> limits) {
        
        boolean isSuccessful = true;
        
        try {
            fileIOHandler.writePDF(employees, limits);
        } catch (DocumentException | FileNotFoundException ex) {
           logger.log(Level.SEVERE, ex.getMessage());
           
           isSuccessful = false;
        }
        finally {
            fireModelActionResult("create_pdf", null, isSuccessful);
        }
    }
}
