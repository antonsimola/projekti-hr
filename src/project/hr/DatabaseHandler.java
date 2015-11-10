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
    
    public DatabaseHandler() throws Exception{
        
        // Attempt sqlite initialization and connect to database.
        Class.forName("org.sqlite.JDBC"); 
        connection = DriverManager.getConnection(
                    "jdbc:sqlite:" + databaseName);
    }
    
    public String getDatabaseName() {
        return databaseName;
    }
    
    /* Contents of a given result set are copied into Employee instances that
     * are placed into an ArrayList (that will be returned). This method does
     * not look for admin information (ADMINISTATOR table in the database).
    */
    private ArrayList<Employee> populateOrdinaryEmployeeList(
            ResultSet resultSet) throws Exception{
        
        ArrayList<Employee> employeeList = new ArrayList();
        
        while(resultSet.next()) {
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
                                        /*resultSet.getDate("START_DATE"),
                                        resultSet.getDate("END_DATE"),*/
                                        resultSet.getString("START_DATE"),
                                        resultSet.getString("END_DATE"),
                                        resultSet.getDouble("WEEKLY_WORKHOURS")
                                        );
            
            System.out.println(employee.getFirstName());
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
     * employees in ArrayList data structure.
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
        
        // May result in a database operation failure exception.
        Statement statement = connection.createStatement(); 
        ResultSet resultSet = statement.executeQuery(selectQuery);
        
        /* Populate ArrayList with result employees.
         * May result in a result set iteration failure exception.
        */
        employeeList = populateOrdinaryEmployeeList(resultSet);
        
        return employeeList;
    }
    
    /* Select by social security number (SSN).
     * Returns an ArrayList just in case (comforms with default class behavior).
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
        
        // May result in a database operation failure exception.
        Statement statement = connection.createStatement(); 
        ResultSet resultSet = statement.executeQuery(selectQuery);
        
        employee = generateEmployee(resultSet);
        
        return employee;
    }
    
    /* Select by username and password.
     *
     * PARAMETRIZATION NOT IMPLEMENTED
    */
    public Employee selectEmployeeByCredentials(String emailAddress, 
            String passwordHASH) throws Exception {
        
        Employee employee;
        //String passwordHASH = generateHASH(password); Generate hash (+salt?) in model
        
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
                +       "EMPLOYEE.EMAIL_ADDRESS=" + emailAddress + " "
                +       "AND "
                +       "ADMINISTRATOR.PASSWORD_HASH=" + passwordHASH + ";";
        
        // May result in a database operation failure exception.
        Statement statement = connection.createStatement(); 
        ResultSet resultSet = statement.executeQuery(selectQuery);
        
        /* Populate ArrayList with result employees.
         * May result in a result set iteration failure exception.
        */
        employee = generateEmployee(resultSet);
        
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
    
//    /* INSERT EMPLOYMENT and EMPLOYEE details of one employee 
//     * (Employee instance).
//     *
//    */
//    public void insertEmployee(Employee employee) throws Exception {
//        int lastEmploymentInsertID;
//        connection.setAutoCommit(false);
//        Statement statement = connection.createStatement();
//        
//        // EMPLOYEMENT INSERT
//        StringBuilder sqlInsert = new StringBuilder(
//                    "INSERT INTO EMPLOYMENT "
//                +   "(JOB_TITLE, HOURLY_WAGE, START_DATE, END_DATE, WEEKLY_WORKHOURS) "
//                +   "VALUES ");
//        
//        sqlInsert.append("(");
//        
//        sqlInsert.append(employee.getJobTitle()).append(", ");
//        sqlInsert.append(employee.getHourlyWage()).append(", ");
//        sqlInsert.append(employee.getStartDate()).append(", ");
//        
//        if(employee.getEndDate() != null)
//            sqlInsert.append(employee.getEndDate()).append(", ");
//        else
//            sqlInsert.append("NULL, ");
//        
//        sqlInsert.append(employee.getWeeklyWorkhours());
//        
//        sqlInsert.append(");");
//
//        statement.executeUpdate(sqlInsert.toString());
//        
//        lastEmploymentInsertID = last_insert_rowid(connection);
//        
//        statement.close();
//        statement = connection.createStatement();
//        
//        // GET EMPLOYEMENT_ID FROM EMPLOYEE HERE
//        
//        
//        // EMPLOYEE INSERT
//        sqlInsert = new StringBuilder(
//                    "INSERT INTO EMPLOYEE "
//                +   "("
//                +   "FIRST_NAME, "
//                +   "SECOND_NAME, "
//                +   "BIRTHDAY, "
//                +   "SSN, "
//                +   "ADDRESS, "
//                +   "POSTAL_CODE, "
//                +   "CITY, "
//                +   "PHONE_NUMBER, "
//                +   "EMAIL_ADDRESS, "
//                +   "FAVORITE_DRINK, "
//                +   ")"
//                +   "VALUES ");
//        
//        sqlInsert.append("(");
//        
//        sqlInsert.append(employee.getFirstName).append(", ");
//        sqlInsert.append(employee.getSecondName()).append(", ");
//        sqlInsert.append(employee.getBirthDay()).append(", ");
//        sqlInsert.append(employee.getSSN).append(", ");
//        
//        if(employee.getAddress() != null)
//            sqlInsert.append(employee.getAddress()).append(", ");
//        else
//            sqlInsert.append("NULL");
//        
//        if(employee.getPostalCode() != null)
//            sqlInsert.append(employee.getPostalCode()).append(", ");
//        else
//            sqlInsert.append("NULL");
//        
//        if(employee.getCity() != null)
//            sqlInsert.append(employee.getCity()).append(", ");
//        else
//            sqlInsert.append("NULL");
//        
//        if(employee.getPhoneNumber() != null)
//            sqlInsert.append(employee.getPhoneNumber()).append(", ");
//        else
//            sqlInsert.append("NULL");
//        
//        if(employee.getEmailAddress() != null)
//            sqlInsert.append(employee.getEmailAddress()).append(", ");
//        else
//            sqlInsert.append("NULL");
//        
//        if(employee.getFavoriteDrink() != null)
//            sqlInsert.append(employee.getFavoriteDrink()).append(", ");
//        else
//            sqlInsert.append("NULL");
//        
//        sqlInsert.append(");");
//
//        statement.executeUpdate(sqlInsert.toString());
//      
//        
//    }
    
    public void updateEmployee(Employee employee) {
    
    }
    
    public void deleteEmployee(String socialSecurityNumber) {
    
    } 
}
