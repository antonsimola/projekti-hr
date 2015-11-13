/* DatabaseHandler.java - Handler class for all database operations
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
    
    public DatabaseHandler() {
        
    }
    
    public void connect() throws Exception {
        // Attempt sqlite initialization and connect to database.
        Class.forName("org.sqlite.JDBC"); 
        connection = DriverManager.getConnection(
                    "jdbc:sqlite:" + databaseName);
    }
    
    public void disconnect() throws Exception {
        connection.close();
    }
    
    public String getDatabaseName() {
        return databaseName;
    }
    
    /* Contents of a given result set are copied into Employee instances that
     * are placed into an ArrayList (that will be returned). This method does
     * not look for admin information (ADMINISTRATOR table in the database).
    */
    private ArrayList<Employee> populateOrdinaryEmployeeList(
            ResultSet resultSet) throws Exception{
        
        ArrayList<Employee> employeeList = new ArrayList();
        
        while(resultSet.next()) {
            Employee employee = generateEmployee(resultSet);
            
            employeeList.add(employee);
        }
        
        return employeeList;
    }
    
    private Employee generateEmployee(ResultSet resultSet) throws Exception{
        
        Employee employee = new Employee(
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
                                        resultSet.getDouble("WEEKLY_WORKHOURS")
                                        );
        
        return employee;
    }
    
    private Employee generateHumanResourcesEmployee(ResultSet resultSet) 
            throws Exception{
        
        Employee employee = new Employee(
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
                                        resultSet.getString("PASSWORD_HASH")
                                        );
        
        return employee;
    }
    
//    /* Contents of a given result set (contains human resources employees) are
//     * returned in an array list.
//    */
//    private ArrayList<Employee> populateHumanResourcesEmployeeList(
//            ResultSet resultSet) throws Exception{
//        
//        ArrayList<Employee> employeeList = new ArrayList();
//        
//        while(resultSet.next()) {
//            Employee employee = new Employee(
//                                        resultSet.getString("FIRST_NAME"),
//                                        resultSet.getString("SECOND_NAME"),
//                                        resultSet.getString("BIRTHDAY"),
//                                        resultSet.getString("SSN"),
//                                        resultSet.getString("ADDRESS"),
//                                        resultSet.getString("POSTAL_CODE"),
//                                        resultSet.getString("CITY"),
//                                        resultSet.getString("PHONE_NUMBER"),
//                                        resultSet.getString("EMAIL_ADDRESS"),
//                                        resultSet.getString("FAVORITE_DRINK"),
//                                        resultSet.getString("JOB_TITLE"),
//                                        resultSet.getDouble("HOURLY_WAGE"),
//                                        resultSet.getDate("START_DATE"),
//                                        resultSet.getDate("END_DATE"),
//                                        resultSet.getDouble("WEEKLY_WORKHOURS"),
//                                        resultSet.getBoolean("ACTIVE")                    
//                                        );
//        
//            employeeList.add(employee);
//        }
//        
//        return employeeList;
//    }
    
    
    
    /* Variant of selectEmployee that takes in no parameters. Returns all
     * employees in an ArrayList.
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
                        "EMPLOYEE.EMPLOYMENT_ID=EMPLOYMENT.ID;";
        
        // May result in a database operation failure.
        Statement statement = connection.createStatement(); 
        ResultSet resultSet = statement.executeQuery(selectQuery);
        
        try {
            employeeList = populateOrdinaryEmployeeList(resultSet);
        }
        catch(Exception ex) {
            employeeList = null;
        }
        
        return employeeList;
    }
    
    /* Select by social security number (SSN).
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
                +       "EMPLOYEE.EMPLOYMENT_ID=EMPLOYMENT.ID "
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
    
    /* Select by emailAddress.
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
                +   "ON"
                +       "EMPLOYEE.EMPLOYMENT_ID=EMPLOYMENT.ID "
                +   "WHERE "
                +       "EMPLOYEE.EMAIL_ADDRESS=" + emailAddress + ";";
        
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
    
    /* Select by emailAddress.
     *
     * PARAMETRIZATION NOT IMPLEMENTED
    */
    public Employee selectHumanResourcesEmployeeByEmail(String emailAddress) 
            throws Exception {
        
        Employee employee;
        
        String selectQuery = 
                    "SELECT "
                +       "* "
                +   "FROM "
                +       "ADMINISTRATOR "
                +       "INNER JOIN "
                +       "EMPLOYEE "
                +   "ON "
                +       "EMPLOYEE.ID=ADMINISTRATOR.EMPLOYEE_ID "
                +       "INNER JOIN "
                +       "EMPLOYMENT "
                +   "ON"
                +       "EMPLOYEE.EMPLOYMENT_ID=EMPLOYMENT.ID "
                +   "WHERE "
                +       "EMPLOYEE.EMAIL_ADDRESS=" + emailAddress + ";";
        
        // May result in a database operation failure.
        Statement statement = connection.createStatement(); 
        ResultSet resultSet = statement.executeQuery(selectQuery);
        
        try {
            employee = generateHumanResourcesEmployee(resultSet);
        }
        catch(Exception ex) {
            employee = null;
        }
        
        return employee;
    }
    
//    /* Load employees based on the instance variables of a given employee
//     * instance. Does not yet allow search by range.
//     * 
//     * PARAMETRIZATION NOT IMPLEMENTED (should be done to all input?)
//    */
//    public ArrayList<Employee> selectEmployee(Employee employee) 
//            throws Exception {
//        
//        ArrayList<Employee> employeeList;
//        StringBuilder selectQuery = new StringBuilder(
//                    "SELECT "
//                +       "* "
//                +   "FROM "
//                +       "EMPLOYEE "
//                +       "INNER JOIN "
//                +       "EMPLOYMENT "
//                +   "ON "
//                +       "EMPLOYEE.EMPLOYMENT_ID=EMPLOYMENT.ID "
//                +   "WHERE EMPLOYEE.ID >= 0");
//        
//        if(employee.getFirstName() != null) {
//            selectQuery.append(" AND FIRST_NAME=").append(employee.getFirstName());
//        }
//        
//        if(employee.getLastName() != null) {
//            selectQuery.append(" AND LAST_NAME=").append(employee.getLastName());
//        }
//        
//        if(employee.getBirthday() != null) {
//            selectQuery.append(" AND BIRTHDAY=").append(employee.getBirthday());
//        }
//        
//        if(employee.getSSN() != null) {
//            selectQuery.append(" AND SSN=").append(employee.getSSN());
//        }
//        
//        if(employee.getAddress() != null) {
//            selectQuery.append(" AND ADDRESS=").append(employee.getAddress());
//        }
//        
//        if(employee.getPostalCode() != null) {
//            selectQuery.append(" AND POSTAL_CODE=").append(employee.getPostalCode());
//        }
//        
//        if(employee.getCity() != null) {
//            selectQuery.append(" AND CITY=").append(employee.getCity());
//        }
//       
//        if(employee.getPhoneNumber() != null) {
//            selectQuery.append(" AND PHONE_NUMBER=").append(
//                    employee.getPhoneNumber());
//        }
//        
//        if(employee.getEmailAddress() != null) {
//            selectQuery.append(" AND EMAIL_ADDRESS=").append(
//                    employee.getEmailAddress());
//        }
//        
//        if(employee.getFavoriteDrink() != null) {
//            selectQuery.append(" AND FAVORITE_DRINK=").append(
//                    employee.getFavoriteDrink());
//        }
//        
//        if(employee.getJobTitle() != null) {
//            selectQuery.append(" AND JOB_TITLE=").append(
//                    employee.getJobTitle());
//        }
//        
//        if(employee.getHourlyWage() != null) {
//            selectQuery.append(" AND HOURLY_WAGE=").append(
//                    employee.getHourlyWage());
//        }
//        
//        if(employee.getStartDate() != null) {
//            selectQuery.append(" AND START_DATE=").append(
//                    employee.getStartDate());
//        }
//        
//        if(employee.getEndDate() != null) {
//            selectQuery.append(" AND END_DATE=").append(
//                    employee.getEndDate());
//        }
//        
//        if(employee.getWeeklyWorkhours() != null) {
//            selectQuery.append(" AND WEEKLY_WORKHOURS=").append(
//                    employee.getWeeklyWorkhours());
//        }
//    }
    
    /* INSERT EMPLOYMENT and EMPLOYEE details of one employee 
     * (Employee instance).
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

        statement.executeUpdate(sqlInsert.toString());
        
        connection.commit();
        statement.close();
        
        statement = connection.createStatement();

        // EMPLOYEE INSERT portion begins
        sqlInsert = new StringBuilder(
                    "INSERT INTO EMPLOYEE "
                +   "("
                +   "EMPLOYMENT_ID, "
                +   "FIRST_NAME, "
                +   "SECOND_NAME, "
                +   "BIRTHDAY, "
                +   "SSN, "
                +   "ADDRESS, "
                +   "POSTAL_CODE, "
                +   "CITY, "
                +   "PHONE_NUMBER, "
                +   "EMAIL_ADDRESS, "
                +   "FAVORITE_DRINK"
                +   ")"
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
        
        if(employee.getCity() != null) {
            sqlInsert.append("'");
            sqlInsert.append(employee.getCity()).append("', ");
        }
        else
            sqlInsert.append("NULL, ");
        
        if(employee.getPhone() != null) {
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

        statement.executeUpdate(sqlInsert.toString());
          
        connection.commit();
        statement.close();
      
        connection.setAutoCommit(true);
    }
    
    /* UPDATE EMPLOYEE with new values (sets again old value if no new value
     * given).
    */
    public void updateEmployeeDetails(Employee newEmployee) throws Exception {
        Employee oldEmployee = selectEmployeeBySSN(newEmployee.getSsn());
        
        connection.setAutoCommit(false);
        Statement statement = connection.createStatement();
        
        // EMPLOYEMENT INSERT portion begins
        StringBuilder sqlInsert = new StringBuilder(
                    "UPDATE EMPLOYEE "
                +   "SET ");
        
        sqlInsert.append("FIRST_NAME='");
        if(newEmployee.getFirstName() != null)  
            sqlInsert.append(newEmployee.getFirstName()).append("', ");
        else
            sqlInsert.append(oldEmployee.getFirstName()).append("', ");
        
        sqlInsert.append("LAST_NAME='");
        if(newEmployee.getLastName() != null)
            sqlInsert.append(newEmployee.getLastName()).append("', ");
        else
            sqlInsert.append(oldEmployee.getLastName()).append("', ");
        
        // To be continued
        
        statement.executeUpdate(sqlInsert.toString());
        
        connection.commit();
        statement.close();
        
    }
    
    public void updateEmploymentDetails(Employee employee) {
    
    }
    
    public void deleteEmployee(String socialSecurityNumber) {
    
    } 
}
