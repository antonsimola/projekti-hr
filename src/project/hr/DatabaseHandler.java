/* DatabaseHandler.java - Handler class for all database operations
 *
 * Samuli Siitonen
 *
 * Sources:
 * http://www.tutorialspoint.com/sqlite/sqlite_java.htm
 * http://www.tutorialspoint.com/sqlite/sqlite_using_joins.htm
 * http://stackoverflow.com/questions/16223979/inner-join-sqlite-from-multiple-tables
 * https://www.youtube.com/watch?v=v-JM749op-8
 * https://www.youtube.com/watch?v=-pDR2Emdzzc
 * https://bloggerinme.wordpress.com/2011/11/02/how-to-use-sqlite-with-java-using-netbeans-ide/
 * http://tech-britney.blogspot.fi/2010/07/using-sqlite-with-sqlitejdbc-driver.html
 */
package project.hr;

import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author Samuli
 */
public class DatabaseHandler {
    
    private Connection connection = null;
    private final String databaseName = "HR_Database.sqlite";;
    ArrayList<Employee> employeeSearchResultsList;
    
    public DatabaseHandler() {
       
    }
    
    // Attempt sqlite initialization and connect to database.
    public void connect() throws Exception {
        Class.forName("org.sqlite.JDBC"); 
        connection = DriverManager.getConnection(
                    "jdbc:sqlite:" + databaseName);
    }
    
    // Close database connection.
    public void disconnect() throws Exception {
        connection.close();
    }
    
    public String getDatabaseName() {
        return databaseName;
    }
    
    /* Inserts new employee into EMPLOYMENT and EMPLOYEE tables.
    */
    public void insertEmployee(Employee employee) throws Exception {

        connection.setAutoCommit(false);
        Statement statement = connection.createStatement();
        
        // EMPLOYEMENT INSERT portion begins
        StringBuilder sqlInsert = new StringBuilder(
                "INSERT INTO EMPLOYMENT "
            +   "(JOB_TITLE, HOURLY_WAGE, START_DATE, END_DATE, WEEKLY_WORKHOURS) "
            +   "VALUES ");
        
        sqlInsert.append("(");
        
        sqlInsert.append("'");
        sqlInsert.append(employee.getJobTitle()).append("', ");
        
        sqlInsert.append(employee.getJobWage()).append(", ");
        
        sqlInsert.append("'");
        sqlInsert.append(employee.getStartDate()).append("', ");
        
        if(employee.getEndDate() != null) {
            sqlInsert.append("'");
            sqlInsert.append(employee.getEndDate()).append("', ");
        }
        else
            sqlInsert.append("NULL, ");
        
        sqlInsert.append(employee.getWeeklyHours());
        
        sqlInsert.append(");");

        System.out.println(sqlInsert);
        
        statement.executeUpdate(sqlInsert.toString());
        
        connection.commit();
        statement.close();
        
        statement = connection.createStatement();

        // EMPLOYEE INSERT portion begins
        sqlInsert = new StringBuilder(
                    "INSERT INTO EMPLOYEE "
                +   "("
                +   "EMPLOYMENT_ID_FK, "
                +   "FIRST_NAME, "
                +   "SECOND_NAME, "
                +   "BIRTHDAY, "
                +   "SSN, "
                +   "PASSWORD_HASH_SALT, "
                +   "ADDRESS, "
                +   "POSTAL_CODE, "
                +   "CITY, "
                +   "PHONE_NUMBER, "
                +   "EMAIL_ADDRESS, "
                +   "FAVORITE_DRINK "
                +   ") "
                +   "VALUES ");
        
        sqlInsert.append("(");
        
        sqlInsert.append("last_insert_rowid(), ");
        
        sqlInsert.append("'");
        sqlInsert.append(employee.getFirstName()).append("', ");
        
        sqlInsert.append("'");
        sqlInsert.append(employee.getLastName()).append("', ");
        
        sqlInsert.append("'");        
        sqlInsert.append(employee.getBirthDay()).append("', ");
        
        sqlInsert.append("'");        
        sqlInsert.append(employee.getSsn()).append("', ");
        
        sqlInsert.append("'");        
        sqlInsert.append(employee.getPassword()).append("', ");
        
        //
        
        if(employee.getAddress() != null) {
            sqlInsert.append("'");
            sqlInsert.append(employee.getAddress()).append("', ");
        }
        else
            sqlInsert.append("NULL, ");
        
        if(employee.getPostal() != null) {
            sqlInsert.append("'");
            sqlInsert.append(employee.getPostal()).append("', ");
        }
        else
            sqlInsert.append("NULL, ");
        
        System.out.println("City:"+employee.getCity());
        if(employee.getCity() != null) {
            sqlInsert.append("'");
            sqlInsert.append(employee.getCity()).append("', ");
        }
        else
            sqlInsert.append("NULL, ");
        
        System.out.println("phone:"+employee.getPhone());
        if(employee.getPhone() != null) {
            System.out.println("phone:"+employee.getPhone());
            sqlInsert.append("'");
            sqlInsert.append(employee.getPhone()).append("', ");
        }
        else
            sqlInsert.append("NULL, ");
        
        if(employee.getEmail() != null) {
            sqlInsert.append("'");
            sqlInsert.append(employee.getEmail()).append("', ");
        }
        else
            sqlInsert.append("NULL, ");
        
        if(employee.getFavoriteDrink() != null) {
            sqlInsert.append("'");
            sqlInsert.append(employee.getFavoriteDrink()).append("' ");
        }
        else
            sqlInsert.append("NULL");
        
        sqlInsert.append(");");

        System.out.println(sqlInsert);
        
        statement.executeUpdate(sqlInsert.toString());
          
        connection.commit();
        statement.close();
        
        statement = connection.createStatement();

        
        connection.setAutoCommit(true);
    }
    
    /* Contents of a given result set are copied into Employee instances that
     * are placed into an ArrayList (that will be returned).
    */
    private ArrayList<Employee> populateEmployeeList(
            ResultSet resultSet) throws Exception{
        
        ArrayList<Employee> employeeList = new ArrayList();
        
        while(resultSet.next()) {
            Employee employee = generateEmployee(resultSet);
            
            employeeList.add(employee);
        }
        
        return employeeList;
    }
    
    /* Generate and return new Employee instance from ResultSet.
    */
    private Employee generateEmployee(ResultSet resultSet) 
            throws Exception{
        
        Employee employee = new Employee(
            resultSet.getInt("EMPLOYEE_ID"),
            resultSet.getString("FIRST_NAME"),
            resultSet.getString("SECOND_NAME"),
            resultSet.getString("BIRTHDAY"),
            resultSet.getString("SSN"),
            resultSet.getString("ADDRESS"),
            resultSet.getString("POSTAL_CODE"),
            resultSet.getString("CITY"),
            resultSet.getString("PHONE_NUMBER"),
            resultSet.getString("EMAIL_ADDRESS"),
            resultSet.getString("FAVORITE_DRINK"),
            resultSet.getString("JOB_TITLE"),
            resultSet.getDouble("HOURLY_WAGE"),
            resultSet.getString("START_DATE"),
            resultSet.getString("END_DATE"),
            resultSet.getDouble("WEEKLY_WORKHOURS"),
            resultSet.getString("PASSWORD_HASH_SALT"),
            resultSet.getInt("RIGHTS")
        );
        
        return employee;
    }
    
    /* Selects without attribute limitations and returns all company employees.
     */
    public ArrayList<Employee> selectAllEmployees() throws Exception{
        ArrayList<Employee> employeeList;
        String selectQuery = 
                "SELECT "
            +       "* "
            +   "FROM "
            +       "EMPLOYEE "
            +       "INNER JOIN "
            +       "EMPLOYMENT "
            +   "ON " +
                    "EMPLOYEE.EMPLOYMENT_ID_FK=EMPLOYMENT.EMPLOYMENT_ID;";
        
        // May result in a database operation failure.
        Statement statement = connection.createStatement(); 
        ResultSet resultSet = statement.executeQuery(selectQuery);
        
        try {
            employeeList = populateEmployeeList(resultSet);
        }
        catch(Exception ex) {
            ex.printStackTrace();
            employeeList = null;
        }
        
        return employeeList;
    }
    
    public Employee selectEmployeeById(int employeeId) 
            throws Exception {
        
        Employee employee;
        
        String selectQuery = 
                "SELECT "
            +       "* "
            +   "FROM "
            +       "EMPLOYEE "
            +       "INNER JOIN "
            +       "EMPLOYMENT "
            +   "ON "
            +       "EMPLOYEE.EMPLOYMENT_ID_FK=EMPLOYMENT.EMPLOYMENT_ID "
            +   "WHERE "
            +       "EMPLOYEE.EMPLOYEE_ID=" + employeeId + ";";
        
        // May result in a database operation failure.
        Statement statement = connection.createStatement(); 
        ResultSet resultSet = statement.executeQuery(selectQuery);
        
        try {
            employee = generateEmployee(resultSet);
        }
        catch(Exception ex) {
            employee = null;
        }
        
        return employee;
    }
    
    /* Selects by social security number (SSN).
     * Returns an Employee instance with filled in details.
     * 
     * PARAMETRIZATION NOT IMPLEMENTED
    */
    public Employee selectEmployeeBySSN(String socialSecurityNumber) 
            throws Exception{
        
        Employee employee;
        String selectQuery = 
                "SELECT "
            +       "* "
            +   "FROM "
            +       "EMPLOYEE "
            +       "INNER JOIN "
            +       "EMPLOYMENT "
            +   "ON "
            +       "EMPLOYEE.EMPLOYMENT_ID_FK=EMPLOYMENT.EMPLOYMENT_ID "
            +   "WHERE "
            +       "SSN=" + socialSecurityNumber + ";";
        
        // May result in a database operation failure.
        Statement statement = connection.createStatement(); 
        ResultSet resultSet = statement.executeQuery(selectQuery);
        
        try {
            employee = generateEmployee(resultSet);
        }
        catch(Exception ex) {
            employee = null;
        }
        
        return employee;
    }
    
    /* Selects by emailAddress.
     *
     * PARAMETRIZATION NOT IMPLEMENTED
    */
    public Employee selectEmployeeByEmailAddress(String emailAddress) 
            throws Exception {
        
        Employee employee;
        
        String selectQuery = 
                "SELECT "
            +       "* "
            +   "FROM "
            +       "EMPLOYEE "
            +       "INNER JOIN "
            +       "EMPLOYMENT "
            +   "ON "
            +       "EMPLOYEE.EMPLOYMENT_ID_FK=EMPLOYMENT.EMPLOYMENT_ID "
            +   "WHERE "
            +       "EMPLOYEE.EMAIL_ADDRESS='" + emailAddress + "';";
        
        // May result in a database operation failure.
        System.out.println(selectQuery);
        Statement statement = connection.createStatement(); 
        ResultSet resultSet = statement.executeQuery(selectQuery);
        
        try {
            employee = generateEmployee(resultSet);
        }
        catch(Exception ex) {
            employee = null;
        }
        
        return employee;
    }
    
    /* Load employees based on the instance variables of a given Employee
     * instance. Does not yet allow search by range.
     * 
     * PARAMETRIZATION NOT IMPLEMENTED (should be done to all input?)
    */
    public ArrayList<Employee> selectEmployee(Employee employee) 
            throws Exception {
        ArrayList<Employee> employeeList = new ArrayList();
        
        StringBuilder selectQuery = new StringBuilder(
                    "SELECT "
                +       "* "
                +   "FROM "
                +       "EMPLOYEE "
                +       "INNER JOIN "
                +       "EMPLOYMENT "
                +   "ON "
                +       "EMPLOYEE.EMPLOYMENT_ID_FK=EMPLOYMENT.EMPLOYMENT_ID "
                +   "WHERE EMPLOYEE.EMPLOYEE_ID >= 0");
        
        // EMPLOYEE spesific attributes begin
        
        if(employee.getFirstName() != null) {
            selectQuery.append(" AND FIRST_NAME='");
            selectQuery.append(employee.getFirstName()).append("'");
        }
        
        if(employee.getLastName() != null) {
            selectQuery.append(" AND LAST_NAME='");
            selectQuery.append(employee.getLastName()).append("'");
        }
        
        if(employee.getBirthDay() != null) {
            selectQuery.append(" AND BIRTHDAY='");
            selectQuery.append(employee.getBirthDay()).append("'");
        }
        
        if(employee.getSsn() != null) {
            selectQuery.append(" AND SSN='");
            selectQuery.append(employee.getSsn()).append("'");
        }
        
        if(employee.getAddress() != null) {
            selectQuery.append(" AND ADDRESS='");
            selectQuery.append(employee.getAddress()).append("'");
        }
        
        if(employee.getPostal() != null) {
            selectQuery.append(" AND POSTAL_CODE='");
            selectQuery.append(employee.getPostal()).append("'");
        }
        
        if(employee.getCity() != null) {
            selectQuery.append(" AND CITY='");
            selectQuery.append(employee.getCity()).append("'");
        }
       
        if(employee.getPhone() != null) {
            selectQuery.append(" AND PHONE_NUMBER='");
            selectQuery.append(employee.getPhone()).append("'");
        }
        
        if(employee.getEmail() != null) {
            selectQuery.append(" AND EMAIL_ADDRESS='");
            selectQuery.append(employee.getEmail()).append("'");
        }
        
        if(employee.getFavoriteDrink() != null) {
            selectQuery.append(" AND FAVORITE_DRINK='");
            selectQuery.append(employee.getFavoriteDrink()).append("'");
        }
        
        // EMPLOYMENT spesific attributes begin
        
        if(employee.getJobTitle() != null) {
            selectQuery.append(" AND JOB_TITLE='");
            selectQuery.append(employee.getJobTitle()).append("'");
        }
        
        if(employee.getJobWage() != -1) {
            selectQuery.append(" AND HOURLY_WAGE=");
            selectQuery.append(employee.getJobWage());
        }
        
        if(employee.getStartDate() != null) {
            selectQuery.append(" AND START_DATE='");
            selectQuery.append(employee.getStartDate()).append("'");
        }
        
        if(employee.getEndDate() != null) {
            selectQuery.append(" AND END_DATE='");
            selectQuery.append(employee.getEndDate()).append("'");
        }
        
        if(employee.getWeeklyHours() != -1) {
            selectQuery.append(" AND WEEKLY_WORKHOURS=");
            selectQuery.append(employee.getWeeklyHours());
        }
        
        selectQuery.append(";");
        
        // May result in a database operation failure.
        Statement statement = connection.createStatement(); 
        ResultSet resultSet = statement.executeQuery(selectQuery.toString());
        
        try {
            employeeList = populateEmployeeList(resultSet);
        }
        catch(Exception ex) {
            ex.printStackTrace();
            employeeList = null;
        }
        
        return employeeList;
    }
    
    private int[] selectEmployeeIdAndEmploymentIdBySsn(
            String socialSecurityNumber) throws Exception {
        int[] idArray = new int[2];
        
        String selectQuery = 
                "SELECT "
            +       "EMPLOYEE_ID, EMPLOYMENT_ID_FK "
            +   "FROM "
            +       "EMPLOYEE "
            +   "WHERE "
            +       "SSN='" + socialSecurityNumber + "';";
        
        // May result in a database operation failure.
        Statement statement = connection.createStatement(); 
        ResultSet resultSet = statement.executeQuery(selectQuery);
       
        idArray[0] = resultSet.getInt("EMPLOYEE_ID");
        idArray[1] = resultSet.getInt("EMPLOYMENT_ID_FK");
        
        return idArray;
    }
    
    private int selectEmployeeEmploymentIdByEmployeeId(int employeeId) 
            throws Exception {
        int employmentId;
        
        String selectQuery = 
                "SELECT "
            +       "EMPLOYMENT_ID_FK "
            +   "FROM "
            +       "EMPLOYEE "
            +   "WHERE "
            +       "EMPLOYEE_ID=" + employeeId + ";";
        
        // May result in a database operation failure.
        Statement statement = connection.createStatement(); 
        ResultSet resultSet = statement.executeQuery(selectQuery);
       
        employmentId = resultSet.getInt("EMPLOYMENT_ID_FK");
        
        return employmentId;
    }
    
    /* UPDATE EMPLOYEE with new values (sets again old value if no new value
     * given).
    */
    public void updateEmployee(Employee newEmployee) throws Exception {
        Employee oldEmployee = selectEmployeeById(
                newEmployee.getEmployeeId());
        
        // 0-element: employee id ; 1st element: employment id 

        connection.setAutoCommit(false);
        Statement statement = connection.createStatement();
        
        // EMPLOYEE INSERT portion begins
        StringBuilder sqlInsert = new StringBuilder(
                    "UPDATE EMPLOYEE "
                +   "SET ");
        
        sqlInsert.append("FIRST_NAME='");
        if(newEmployee.getFirstName() != null)  
            sqlInsert.append(newEmployee.getFirstName()).append("', ");
        else
            sqlInsert.append(oldEmployee.getFirstName()).append("', ");
        
        sqlInsert.append("SECOND_NAME='");
        if(newEmployee.getLastName() != null)
            sqlInsert.append(newEmployee.getLastName()).append("', ");
        else
            sqlInsert.append(oldEmployee.getLastName()).append("', ");
        
        sqlInsert.append("BIRTHDAY='");
        if(newEmployee.getBirthDay() != null)
            sqlInsert.append(newEmployee.getBirthDay()).append("', ");
        else
            sqlInsert.append(oldEmployee.getBirthDay()).append("', ");
        
        /*
        sqlInsert.append("SSN='");
        if(newEmployee.getSsn() != null)
            sqlInsert.append(newEmployee.getSsn()).append("', ");
        else
            sqlInsert.append(oldEmployee.getSsn()).append("', ");
        */
        
        sqlInsert.append("ADDRESS='");
        if(newEmployee.getAddress() != null)
            sqlInsert.append(newEmployee.getAddress()).append("', ");
        else
            sqlInsert.append(oldEmployee.getAddress()).append("', ");
        
        sqlInsert.append("POSTAL_CODE='");
        if(newEmployee.getPostal() != null)
            sqlInsert.append(newEmployee.getPostal()).append("', ");
        else
            sqlInsert.append(oldEmployee.getPostal()).append("', ");

        sqlInsert.append("CITY='");
        if(newEmployee.getCity() != null)
            sqlInsert.append(newEmployee.getCity()).append("', ");
        else
            sqlInsert.append(oldEmployee.getCity()).append("', ");
        
        sqlInsert.append("PHONE_NUMBER='");
        if(newEmployee.getPhone() != null)
            sqlInsert.append(newEmployee.getPhone()).append("', ");
        else
            sqlInsert.append(oldEmployee.getPhone()).append("', ");
        
        sqlInsert.append("EMAIL_ADDRESS='");
        if(newEmployee.getEmail() != null)
            sqlInsert.append(newEmployee.getEmail()).append("', ");
        else
            sqlInsert.append(oldEmployee.getEmail()).append("', ");
        
        sqlInsert.append("FAVORITE_DRINK='");
        if(newEmployee.getFavoriteDrink() != null)
            sqlInsert.append(newEmployee.getFavoriteDrink()).append("', ");
        else
            sqlInsert.append(oldEmployee.getFavoriteDrink()).append("', ");
        
        sqlInsert.append("RIGHTS=");
        if(newEmployee.getRights() != -1)
            sqlInsert.append(newEmployee.getRights()).append(" ");
        else
            sqlInsert.append(oldEmployee.getRights()).append(" ");
        
        sqlInsert.append("WHERE EMPLOYEE_ID=");
        sqlInsert.append(oldEmployee.getEmployeeId());
        sqlInsert.append(";");
        
        statement.executeUpdate(sqlInsert.toString());
        
        // EMPLOYEMENT INSERT portion begins
        sqlInsert = new StringBuilder(
                    "UPDATE EMPLOYMENT "
                +   "SET ");
        
        sqlInsert.append("JOB_TITLE='");
        if(newEmployee.getJobTitle() != null)  
            sqlInsert.append(newEmployee.getJobTitle()).append("', ");
        else
            sqlInsert.append(oldEmployee.getJobTitle()).append("', ");
        
        sqlInsert.append("HOURLY_WAGE=");
        if(newEmployee.getJobWage() != -1)
            sqlInsert.append(newEmployee.getJobWage()).append(", ");
        else
            sqlInsert.append(oldEmployee.getJobWage()).append(", ");
        
        sqlInsert.append("START_DATE='");
        if(newEmployee.getStartDate() != null)
            sqlInsert.append(newEmployee.getStartDate()).append("', ");
        else
            sqlInsert.append(oldEmployee.getStartDate()).append("', ");
        
        sqlInsert.append("END_DATE='");
        if(newEmployee.getEndDate() != null)  
            sqlInsert.append(newEmployee.getEndDate()).append("', ");
        else
            sqlInsert.append(oldEmployee.getEndDate()).append("', ");
        
        sqlInsert.append("WEEKLY_WORKHOURS=");
        if(newEmployee.getWeeklyHours() != -1)
            sqlInsert.append(newEmployee.getWeeklyHours()).append(" ");
        else
            sqlInsert.append(oldEmployee.getWeeklyHours()).append(" ");
        
        sqlInsert.append("WHERE EMPLOYMENT_ID=");
        sqlInsert.append(selectEmployeeEmploymentIdByEmployeeId(oldEmployee.getEmployeeId()));
        sqlInsert.append(";");
        
        statement.executeUpdate(sqlInsert.toString());
        
        statement.close();
        connection.commit();
        
        connection.setAutoCommit(true);
    }
    
    public void deleteEmployeeByEmployeeId(int employeeId) 
            throws Exception {
        int employmentId = selectEmployeeEmploymentIdByEmployeeId(employeeId);
        System.out.println(employmentId+" employee id:"+employeeId);
        connection.setAutoCommit(false);
        StringBuilder sqlDelete = new StringBuilder(
                    "DELETE FROM EMPLOYMENT "
                +   "WHERE "
                +   "EMPLOYMENT_ID=");
        
        sqlDelete.append(employmentId);
        sqlDelete.append(";");
        
        Statement statement = connection.createStatement();
        statement.executeUpdate(sqlDelete.toString());
        
        sqlDelete = new StringBuilder(
                    "DELETE FROM EMPLOYEE "
                +   "WHERE "
                +   "EMPLOYEE_ID=");
        
        sqlDelete.append(employeeId);
        sqlDelete.append(";");
        
        statement = connection.createStatement();
        statement.executeUpdate(sqlDelete.toString());
        
        statement.close();
        connection.commit();
        
        connection.setAutoCommit(true);
    } 
}
