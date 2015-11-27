/* Preliminary header information: 
 * Model.java - Acts as the model in MVC (Model View Controler) design pattern.
 * Samuli Siitonen
 *
 * Main logic for class interaction is based on an article by Robert Eckstein: 
 * "Java SE Application Design With MVC", March 2007
 * http://www.oracle.com/technetwork/articles/javase/index-142890.html
 */

//http://stackoverflow.com/questions/15758685/how-to-write-logs-in-text-file-when-using-java-util-logging-logger

package project.hr;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
    private ArrayList<Employee> employeeSearchResults;
    
    public Model () {
        propertyChangeSupport = new PropertyChangeSupport(this);
        passwordSecurity = new PasswordSecurity();
        
        signedInEmployee = null;
        
        try {
            fileIOHandler = FileIOHandler.getInstance();
            logHandler = LogHandler.getInstance();
            logger = logHandler.getLogger();
            //fileIOHandler.writeUserLog("testikäyttäjä", "update", "lisätieto");
            databaseHandler = new DatabaseHandler();
            databaseHandler.connect();
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    public Employee getSignedInEmployee() {
        return signedInEmployee;
    }
    
    public void getUserLog() {
        fireModelActionResult("user_log", null, fileIOHandler.readUserLog());
    }
    
    public void createSearchResultsPDF(ObservableList<Employee> employees, 
            HashMap<String, Boolean> limits) {
        
        fileIOHandler.writePDF(employees, limits);
        fireModelActionResult("create_pdf", null, new Boolean(true));
    
    }
    
    public static enum Sort {
        FIRST_NAME_DESC,
        LAST_NAME_DESC,
        CITY_DESC,
        JOB_TITLE_DESC,
        HOURLY_WAGE_DESC,
        START_DATE_DESC,
        END_DATE_DESC,
        WEEKLY_WORKHOURS_DESC
        
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
        }
        catch(Exception ex) {
            logger.log(Level.SEVERE, ex.getMessage());
            isSuccessful = false;
        }
        finally {
            fireModelActionResult("register", null, new Boolean(isSuccessful));
        }
    }
        
  
    private Employee searchEmployeeByEmail(String emailAddress) {
        Employee employee = null;
        
        try {
            employee = databaseHandler.selectEmployeeByEmailAddress(
                            emailAddress);
        } 
        catch(Exception ex) {
            logger.log(Level.SEVERE, ex.getMessage());
        }
   
        return employee;
    }
    
    public void signIn(String emailAddress, String password) {
        Employee employee = searchEmployeeByEmail(emailAddress);
        
        
        if(employee != null && passwordSecurity.isPasswordValid(password, 
                        employee.getPassword())) {
            signedInEmployee = employee;
            fireModelActionResult("sign_in", null, employee);
            fileIOHandler.writeUserLog(signedInEmployee.getEmail(), 
                    fileIOHandler.ACTION_SIGN_IN, 
                    fileIOHandler.ACTION_SUCCESS, 
                    signedInEmployee.getEmployeeId(),
                    signedInEmployee.toString(),
                    signedInEmployee.getSsn());
            
        }
        else {
            fireModelActionResult("sign_in", null, null);
            fileIOHandler.writeUserLog(emailAddress, 
                    fileIOHandler.ACTION_SIGN_IN, 
                    fileIOHandler.ACTION_FAILURE, 
                    fileIOHandler.EMPTY_ID,
                    fileIOHandler.EMPTY,
                    fileIOHandler.EMPTY);
        }
    }
    
    public void signOut() {
        fireModelActionResult("sign_out", signedInEmployee, null);
        signedInEmployee = null;
    }
    
    public void addEmployee(Employee employee) {
        boolean isSuccessful = true;
        String successString = fileIOHandler.ACTION_SUCCESS;
        employee.setPassword(passwordSecurity.generateHashedSaltedPassword(
                            employee.getPassword()));
        
        try {
            databaseHandler.insertEmployee(employee);
        } catch (Exception ex) {
            isSuccessful = false;
            successString = fileIOHandler.ACTION_FAILURE;
            logger.log(Level.SEVERE, ex.getMessage());
        }
        finally {
            fireModelActionResult("add", null, new Boolean(isSuccessful));
            fileIOHandler.writeUserLog(signedInEmployee.getEmail(), 
                    fileIOHandler.ACTION_ADD, 
                    successString, 
                    employee.getEmployeeId(),
                    employee.toString(),
                    employee.getSsn());
        }
        
        
    }
    
    public void editEmployee(Employee employee) {
        boolean isSuccessful = true;
        String successString = fileIOHandler.ACTION_SUCCESS;
        
        try {
            databaseHandler.updateEmployee(employee);
            
            //fireModelActionResult("edit", null, (Object)employee);
        } catch (Exception ex) {
            ex.printStackTrace();
            isSuccessful = false;
            successString = fileIOHandler.ACTION_FAILURE;
            logger.log(Level.SEVERE, ex.getMessage());
        }
        finally {
            fireModelActionResult("edit", null, isSuccessful);
            fileIOHandler.writeUserLog(signedInEmployee.getEmail(), 
                    fileIOHandler.ACTION_EDIT, 
                    successString, 
                    employee.getEmployeeId(),
                    employee.toString(),
                    employee.getSsn());
        }
    }
    
    public void removeEmployee(int employeeId) {
        boolean isSuccessful = true;
        String successString = fileIOHandler.ACTION_SUCCESS;
        
        try {
            if (employeeId != signedInEmployee.getEmployeeId())
                databaseHandler.deleteEmployeeByEmployeeId(employeeId);
            else {
                isSuccessful = false;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            isSuccessful = false;
            successString = fileIOHandler.ACTION_FAILURE;
            logger.log(Level.SEVERE, ex.getMessage());
        }
        finally {
            fireModelActionResult("remove", null, new Boolean(isSuccessful));
            fileIOHandler.writeUserLog(signedInEmployee.getEmail(), 
                    fileIOHandler.ACTION_REMOVE, 
                    successString, 
                    employeeId,
                    fileIOHandler.EMPTY,
                    fileIOHandler.EMPTY);
        }
    }
    
    // Should store search results in case the user wants to alter them
    // (call could be placed to alterEmployeeSearchResultFormatting)
    public void searchEmployee(Employee employee, Employee employeeRangevalues){
        employeeSearchResults = null;
        String successString = fileIOHandler.ACTION_SUCCESS;
        
        try {
            employeeSearchResults = databaseHandler.selectEmployee(employee, 
                    employeeRangevalues);
            if (!(employeeSearchResults.size()> 0))
                employeeSearchResults = null;
        } catch (Exception ex) {
            successString = fileIOHandler.ACTION_FAILURE;
            logger.log(Level.SEVERE, ex.getMessage());
        }
        finally {
            fireModelActionResult("search_by_employee", null, 
                employeeSearchResults);
            fileIOHandler.writeUserLog(signedInEmployee.getEmail(), 
                    fileIOHandler.ACTION_SEARCH, 
                    successString, 
                    employee.getEmployeeId(),
                    employee.toString(),
                    employee.getSsn());
        }
    }
    
    public void searchEmployee(int employeeId) {
        Employee employee = null;
        String successString = fileIOHandler.ACTION_SUCCESS;
        
        try {
            employee = databaseHandler.selectEmployeeById(employeeId);
        } 
        catch (Exception ex) {
            successString = fileIOHandler.ACTION_FAILURE;
            logger.log(Level.SEVERE, ex.getMessage());
        }
        finally {
            fireModelActionResult("search_by_id", null, 
                employee);
            fileIOHandler.writeUserLog(signedInEmployee.getEmail(), 
                    fileIOHandler.ACTION_SEARCH, 
                    successString, 
                    employeeId,
                    fileIOHandler.EMPTY,
                    fileIOHandler.EMPTY);
        }
    }
    
    public void searchEmployee(Employee emp){
        employeeSearchResults = null;
        String successString = fileIOHandler.ACTION_SUCCESS;
        
        try {
            employeeSearchResults = databaseHandler.selectEmployeeByName(emp);
            if (!(employeeSearchResults.size()> 0))
                employeeSearchResults = null;
        } catch (Exception ex) {
            successString = fileIOHandler.ACTION_FAILURE;
            logger.log(Level.SEVERE, ex.getMessage());
        }
        finally {
            fireModelActionResult("search_names", null, 
                employeeSearchResults);
            fileIOHandler.writeUserLog(signedInEmployee.getEmail(), 
                    fileIOHandler.ACTION_SEARCH, 
                    fileIOHandler.EMPTY, 
                    fileIOHandler.EMPTY_ID,
                    fileIOHandler.EMPTY,
                    fileIOHandler.EMPTY);
        }
    }
    
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
    
    public void getAllEmployees() {
        ArrayList<Employee> employeeList = null;
        String successString = fileIOHandler.ACTION_SUCCESS;
        
        try {
            employeeList = databaseHandler.selectAllEmployees();
        } catch(Exception ex) {
            successString = fileIOHandler.ACTION_FAILURE;
            logger.log(Level.SEVERE, ex.getMessage());
        }
        finally {
            fireModelActionResult("all_employees", null, employeeList);
            fileIOHandler.writeUserLog(signedInEmployee.getEmail(), 
                    fileIOHandler.ACTION_SEARCH, 
                    successString, 
                    fileIOHandler.EMPTY_ID,
                    fileIOHandler.TARGET_ALL,
                    fileIOHandler.TARGET_ALL);
        }      
    }
}
