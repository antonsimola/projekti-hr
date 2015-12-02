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
    
    public DatabaseHandler() {}
    
    // Attempt sqlite initialization and connect to database.
    public void connect() throws ClassNotFoundException, SQLException  {
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection(
                    "jdbc:sqlite:" + databaseName);
    }
    
    // Close database connection.
    public void disconnect() throws SQLException {
        connection.close();
    }
    
    public String getDatabaseName() {
        return databaseName;
    }
    
    /* Inserts new employee into EMPLOYMENT and EMPLOYEE tables.
    */
    public void insertEmployee(Employee employee) throws SQLException {

        connection.setAutoCommit(false);
        //Statement statement = connection.createStatement();
        
        PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO EMPLOYMENT "
            +   "(JOB_TITLE, HOURLY_WAGE, START_DATE, END_DATE, "
            +   "WEEKLY_WORKHOURS) "
            +   "VALUES (?, ?, ?, ?, ?)");
        
        // EMPLOYEMENT INSERT portion begins
        /*
        StringBuilder sqlInsert = new StringBuilder(
                "INSERT INTO EMPLOYMENT "
            +   "(JOB_TITLE, HOURLY_WAGE, START_DATE, END_DATE, WEEKLY_WORKHOURS) "
            +   "VALUES (?, ?, ?, ?, ?)");
        */
        
        statement.setString(1, employee.getJobTitle());
        statement.setDouble(2, employee.getJobWage());
        statement.setString(3, employee.getStartDate());
        
        if(employee.getEndDate() != null)
            statement.setString(4, employee.getEndDate());
        else
            statement.setString(4, "NULL");
        
        statement.setDouble(5, employee.getWeeklyHours());
        
        statement.executeUpdate();
        
        /*
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
        */
        
        //statement.executeUpdate();
        //connection.commit();
        //statement.close();
        
        statement = connection.prepareStatement(
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
                +   "VALUES (last_insert_rowid(), ?, ?, ?, ?, ?, ?, ?, ?, ?, "
                +   "?, ?)");
        
        statement.setString(1, employee.getFirstName());
        statement.setString(2, employee.getLastName());
        statement.setString(3, employee.getBirthDay());
        statement.setString(4, employee.getSsn());
        statement.setString(5, employee.getPassword());
        
        //if(employee.getAddress() != null)
            statement.setString(6, employee.getAddress());
        //else
            //statement.setString(6, "NULL");
        
        //if(employee.getPostal() != null)
            statement.setString(7, employee.getPostal());
        //else
           // statement.setString(7, "NULL");
        
       // if(employee.getCity() != null)
            statement.setString(8, employee.getCity());
        //else
         //   statement.setString(8, "NULL");
        
       // if(employee.getPhone() != null)
            statement.setString(9, employee.getPhone());
        //else
        //    statement.setString(9, "NULL");
        
       // if(employee.getEmail() != null)
            statement.setString(10, employee.getEmail());
       // else
        //    statement.setString(10, "NULL");
        
        //if(employee.getFavoriteDrink() != null)
            statement.setString(11, employee.getFavoriteDrink());
        //else
        //    statement.setString(11, "NULL");
 
        /*
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
        */
        statement.executeUpdate();
        
        statement.close();
        connection.commit();

        connection.setAutoCommit(true);
    }
    
    /* Contents of a given result set are copied into Employee instances that
     * are placed into an ArrayList (that will be returned).
    */
    private ArrayList<Employee> populateEmployeeList(ResultSet resultSet) 
            throws SQLException{
        
        ArrayList<Employee> employeeList = new ArrayList();
        
        while(resultSet.next()) {
            Employee employee = generateEmployee(resultSet);
            
            employeeList.add(employee);
        }
        
        return employeeList;
    }
    
    /* Generate and return new Employee instance from ResultSet.
    */
    private Employee generateEmployee(ResultSet resultSet) throws SQLException {
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
    public ArrayList<Employee> selectAllEmployees() throws SQLException {
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
        
        employeeList = populateEmployeeList(resultSet);
        
        return employeeList;
    }
    
    public Employee selectEmployeeById(int employeeId) throws SQLException {
        
        Employee employee;
        
        PreparedStatement statement = connection.prepareStatement(
                "SELECT "
            +       "* "
            +   "FROM "
            +       "EMPLOYEE "
            +       "INNER JOIN "
            +       "EMPLOYMENT "
            +   "ON "
            +       "EMPLOYEE.EMPLOYMENT_ID_FK = EMPLOYMENT.EMPLOYMENT_ID "
            +   "WHERE "
            +       "EMPLOYEE.EMPLOYEE_ID = ?;");
        
        statement.setInt(1, employeeId);
        ResultSet resultSet = statement.executeQuery();
        
        /*
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
        */
        

        employee = generateEmployee(resultSet);

        resultSet.close();
        statement.close();
       
        return employee;
    }
    
    /* Selects by social security number (SSN).
     * Returns an Employee instance with filled in details.
     * 
     * PARAMETRIZATION NOT IMPLEMENTED
    */
    public Employee selectEmployeeBySSN(String socialSecurityNumber) 
            throws SQLException {
        
        Employee employee;
        
        PreparedStatement statement = connection.prepareStatement(
                "SELECT "
            +       "* "
            +   "FROM "
            +       "EMPLOYEE "
            +       "INNER JOIN "
            +       "EMPLOYMENT "
            +   "ON "
            +       "EMPLOYEE.EMPLOYMENT_ID_FK=EMPLOYMENT.EMPLOYMENT_ID "
            +   "WHERE "
            +       "SSN = ?");
        
        statement.setString(1, socialSecurityNumber);
        ResultSet resultSet = statement.executeQuery();
        
        employee = generateEmployee(resultSet);
        
        resultSet.close();
        statement.close();
        
        /*
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
        */
        
        // May result in a database operation failure.
        /*
        Statement statement = connection.createStatement(); 
        ResultSet resultSet = statement.executeQuery(selectQuery);
        
        employee = generateEmployee(resultSet);
        */
        
        return employee;
    }
    
    /* Selects by emailAddress.
     *
     * PARAMETRIZATION NOT IMPLEMENTED
    */
    public Employee selectEmployeeByEmailAddress(String emailAddress) 
            throws SQLException {
        
        Employee employee;
 
        PreparedStatement statement = connection.prepareStatement(
                "SELECT "
            +       "* "
            +   "FROM "
            +       "EMPLOYEE "
            +       "INNER JOIN "
            +       "EMPLOYMENT "
            +   "ON "
            +       "EMPLOYEE.EMPLOYMENT_ID_FK = EMPLOYMENT.EMPLOYMENT_ID "
            +   "WHERE "
            +       "EMPLOYEE.EMAIL_ADDRESS = ?");
        
        statement.setString(1, emailAddress);
        
        ResultSet resultSet = statement.executeQuery();
  
        //statement.close(); closing this here results in empty resultset
        
        /*
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
        */
        
        employee = generateEmployee(resultSet);

        resultSet.close();
        statement.close();
 
        return employee;
    }
    
    /* Load employees based on the instance variables of a given Employee
     * instance. Does not yet allow search by range.
     * 
     * PARAMETRIZATION NOT IMPLEMENTED (should be done to all input?)
    */ 
    public ArrayList<Employee> selectEmployee(Employee employee, 
            Employee newmployeeRangeValues) throws SQLException {
        
        ArrayList<Employee> employeeList = new ArrayList();
        ArrayList<Object> parameterList = new ArrayList();
        
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
            selectQuery.append(" AND FIRST_NAME LIKE ?");
            parameterList.add(employee.getFirstName() + "%");   
            
            //selectQuery.append(employee.getFirstName()).append("'");
        }
        
        if(employee.getLastName() != null) {
            selectQuery.append(" AND SECOND_NAME LIKE ?");
            parameterList.add(employee.getLastName() + "%");
            
            //selectQuery.append(employee.getLastName()).append("'");
            
            // Add all non-empty variables into the array list and also set
            // prepared statement symbols ('?'). Set prepared statement at the
            // end of method (get array list items by attempting casting).
            // Array list items will match the prepared statement symbols 
            // sequence.
        }
        
        if(employee.getBirthDay() != null && 
                newmployeeRangeValues.getBirthDay() != null) {
            
            selectQuery.append(" AND BIRTHDAY BETWEEN ?");
            parameterList.add(employee.getBirthDay());
            
            selectQuery.append(" AND ?");
            parameterList.add(newmployeeRangeValues.getBirthDay());
            
            /*
            selectQuery.append(" AND BIRTHDAY BETWEEN ?");
            selectQuery.append(employee.getBirthDay()).append("'");
            selectQuery.append(" AND '");
            selectQuery.append(newmployeeRangeValues.getBirthDay()).append("'");
            */
        }
        else if(employee.getBirthDay() != null && 
                newmployeeRangeValues.getBirthDay() == null) {
            
            selectQuery.append(" AND BIRTHDAY BETWEEN ?");
            parameterList.add(employee.getBirthDay());
            selectQuery.append(" AND '9999-12-12'");
            
            /*
            selectQuery.append(" AND BIRTHDAY BETWEEN '");
            selectQuery.append(employee.getBirthDay()).append("'");
            selectQuery.append(" AND '9999-12-12'");
            */
        }
        else if(employee.getBirthDay() == null && 
                newmployeeRangeValues.getBirthDay() != null) {
            
            selectQuery.append(" AND BIRTHDAY BETWEEN ");
            selectQuery.append("'0000-01-01' AND ?");
            parameterList.add(newmployeeRangeValues.getBirthDay());
            
            /*
            selectQuery.append(" AND BIRTHDAY BETWEEN ");
            selectQuery.append("'0000-01-01' AND '");
            selectQuery.append(newmployeeRangeValues.getBirthDay()).append("'");
            */
        }
        
        if(employee.getSsn() != null) {
            selectQuery.append(" AND SSN = ?");
            parameterList.add(employee.getSsn());
            
            /*
            selectQuery.append(" AND SSN='");
            selectQuery.append(employee.getSsn()).append("'");
            */
        }
        
        if(employee.getAddress() != null) {
            selectQuery.append(" AND ADDRESS LIKE ?");
            parameterList.add("%" + employee.getAddress() + "%");
            
            /*
            selectQuery.append(" AND ADDRESS='");
            selectQuery.append(employee.getAddress()).append("'");
            */
        }
        
        if(employee.getPostal() != null) {
            selectQuery.append(" AND POSTAL_CODE = ?");
            parameterList.add(employee.getPostal());
            
            /*
            selectQuery.append(" AND POSTAL_CODE='");
            selectQuery.append(employee.getPostal()).append("'");
            */
        }
        
        if(employee.getCity() != null) {
            selectQuery.append(" AND CITY LIKE ?");
            parameterList.add(employee.getCity() + "%");
            
            /*
            selectQuery.append(" AND CITY='");
            selectQuery.append(employee.getCity()).append("'");
            */
        }
       
        if(employee.getPhone() != null) {
            selectQuery.append(" AND PHONE_NUMBER LIKE ?");
            parameterList.add("%" + employee.getPhone() + "%");
            
            /*
            selectQuery.append(" AND PHONE_NUMBER='");
            selectQuery.append(employee.getPhone()).append("'");
            */
        }
        
        if(employee.getEmail() != null) {
            selectQuery.append(" AND EMAIL_ADDRESS LIKE ?");
            parameterList.add("%" + employee.getEmail() + "%");
            
            /*
            selectQuery.append(" AND EMAIL_ADDRESS='");
            selectQuery.append(employee.getEmail()).append("'");
            */
        }
        
        if(employee.getFavoriteDrink() != null) {
            selectQuery.append(" AND FAVORITE_DRINK LIKE ?");
            parameterList.add("%" + employee.getFavoriteDrink() + "%");
            
            /*
            selectQuery.append(" AND FAVORITE_DRINK='");
            selectQuery.append(employee.getFavoriteDrink()).append("'");
            */
        }
        
        // EMPLOYMENT spesific attributes begin
        
        if(employee.getJobTitle() != null) {
            selectQuery.append(" AND JOB_TITLE LIKE ?");
            parameterList.add("%" + employee.getJobTitle() + "%");
            
            /*
            selectQuery.append(" AND JOB_TITLE='");
            selectQuery.append(employee.getJobTitle()).append("'");
            */
        }
        
        //System.out.println("hourly wages: " + employee.getJobWage() + " " + newmployeeRangeValues.getJobWage() );
        if(employee.getJobWage() != -1 && 
                newmployeeRangeValues.getJobWage() != -1) {
            
            selectQuery.append(" AND HOURLY_WAGE BETWEEN ?");
            parameterList.add((Double)(employee.getJobWage()));
            selectQuery.append(" AND ?");
            parameterList.add((Double)(newmployeeRangeValues.getJobWage()));
            
            /*
            selectQuery.append(" AND HOURLY_WAGE BETWEEN ");
            selectQuery.append(employee.getJobWage());
            selectQuery.append(" AND ");
            selectQuery.append(newmployeeRangeValues.getJobWage());
            */
        }
        else if(employee.getJobWage() != -1 && 
                newmployeeRangeValues.getJobWage() == -1) {
            
            selectQuery.append(" AND AND HOURLY_WAGE >= ?");
            parameterList.add(employee.getJobWage());
            
            /*
            selectQuery.append(" AND HOURLY_WAGE >= ");
            selectQuery.append(employee.getJobWage());
            */
        
        }
        else if(employee.getJobWage() == -1 && 
                newmployeeRangeValues.getJobWage() != -1) {
            
            selectQuery.append(" AND AND HOURLY_WAGE <= ?");
            parameterList.add(newmployeeRangeValues.getJobWage());
            
            /*
            selectQuery.append(" AND HOURLY_WAGE <= ");
            selectQuery.append(newmployeeRangeValues.getJobWage());
            */
        }
        
        if(employee.getStartDate() != null && 
                newmployeeRangeValues.getEndDate() != null) {
            
            selectQuery.append(" AND START_DATE BETWEEN ?");
            parameterList.add(employee.getStartDate());
            selectQuery.append(" AND ?");
            parameterList.add(newmployeeRangeValues.getEndDate());
            
            selectQuery.append(" AND END_DATE BETWEEN ?");
            parameterList.add(employee.getStartDate());
            selectQuery.append(" AND ?");
            parameterList.add(newmployeeRangeValues.getEndDate());
            
            /*
            selectQuery.append(" AND START_DATE BETWEEN '");
            selectQuery.append(employee.getStartDate()).append("'");
            selectQuery.append(" AND '");
            selectQuery.append(newmployeeRangeValues.getEndDate()).append("'");
            
            selectQuery.append(" AND");
            
            selectQuery.append(" AND END_DATE BETWEEN '");
            selectQuery.append(employee.getStartDate());
            selectQuery.append(" AND '");
            selectQuery.append(newmployeeRangeValues.getEndDate()).append("'");
            */
        }
        else if(employee.getStartDate() != null && 
                newmployeeRangeValues.getEndDate() == null) {
            
            selectQuery.append(" AND START_DATE BETWEEN ?");
            parameterList.add(employee.getStartDate());
            System.out.println("start: " + employee.getStartDate());
            selectQuery.append(" AND '9999-12-12'");
            
            /*
            selectQuery.append(" AND START_DATE BETWEEN '");
            selectQuery.append(employee.getStartDate()).append("'");
            selectQuery.append(" AND '9999-12-12'");
            */
        }
        else if(employee.getStartDate() == null && 
                newmployeeRangeValues.getEndDate() != null) {
            
            selectQuery.append(" AND END_DATE BETWEEN ");
            selectQuery.append("'0000-01-01' AND ?");
            parameterList.add(newmployeeRangeValues.getEndDate());
            
            
            /*
            selectQuery.append(" AND END_DATE BETWEEN ");
            selectQuery.append("'0000-01-01'");
            selectQuery.append(newmployeeRangeValues.getEndDate()).append("'");
            */
        }
        if(employee.getWeeklyHours() != -1 && 
                newmployeeRangeValues.getWeeklyHours() != -1) {
            
            selectQuery.append(" AND WEEKLY_WORKHOURS BETWEEN ?");
            parameterList.add((Double)(employee.getWeeklyHours()));
            selectQuery.append(" AND ?");
            parameterList.add((Double)(newmployeeRangeValues.getWeeklyHours()));
            
            /*
            selectQuery.append(" AND WEEKLY_WORKHOURS BETWEEN ");
            selectQuery.append(employee.getWeeklyHours());
            selectQuery.append(" AND ");
            selectQuery.append(newmployeeRangeValues.getWeeklyHours());
            */
        }
        else if(employee.getWeeklyHours() != -1 && 
                newmployeeRangeValues.getWeeklyHours() == -1) {
            
            selectQuery.append(" AND WEEKLY_WORKHOURS >= ?");
            parameterList.add(employee.getWeeklyHours());
            
            /*
            selectQuery.append(" AND WEEKLY_WORKHOURS >= ");
            selectQuery.append(employee.getWeeklyHours());
            */
        }
        else if(employee.getWeeklyHours() == -1 && 
                newmployeeRangeValues.getWeeklyHours() != -1) {
            
            selectQuery.append(" AND WEEKLY_WORKHOURS <= ?");
            parameterList.add(newmployeeRangeValues.getWeeklyHours());
            
            /*
            selectQuery.append(" AND WEEKLY_WORKHOURS <= ");
            selectQuery.append(newmployeeRangeValues.getWeeklyHours());
            */
        }
        
        //System.out.println(selectQuery.toString());
        System.out.println(selectQuery.toString());
        PreparedStatement statement = connection.prepareStatement(
                selectQuery.toString());
        
        /*
        for(int i = 0; i < parameterList.size(); i++) {
            System.out.println("round " + i);
            Object o = parameterList.get(i);
            if(o instanceof Double) {
                statement.setDouble(i + 1, ((Double)o));
                System.out.println("adding double");
            }
            else if(o instanceof String) {
                statement.setString(i + 1, (String)o);
                System.out.println("adding string");
            }
        }
        */

        int counter = 1;
        for(Object o : parameterList) {
            if(o instanceof Double) {
                statement.setDouble(counter, ((Double)o));
            }
            else {
                statement.setString(counter, (String)o);
            }
            
            counter++;
        }
        
        ResultSet resultSet = statement.executeQuery();
        
        // May result in a database operation failure.
        //Statement statement = connection.createStatement(); 
        //ResultSet resultSet = statement.executeQuery(selectQuery.toString());
        
        employeeList = populateEmployeeList(resultSet);

        resultSet.close();
        statement.close();
        
        return employeeList;
    }
    
    public ArrayList<Employee> selectEmployeeByName(Employee employee) 
            throws SQLException {
        
        ArrayList<Employee> employeeList = new ArrayList<>();
        ArrayList<String> parameterList = new ArrayList<>();
        
        
        StringBuilder selectQuery = new StringBuilder(
                    "SELECT "
                +       "* "
                +   "FROM "
                +       "EMPLOYEE "
                +       "INNER JOIN "
                +       "EMPLOYMENT "
                +   "ON "
                +       "EMPLOYEE.EMPLOYMENT_ID_FK = EMPLOYMENT.EMPLOYMENT_ID "
                +   "WHERE EMPLOYEE.EMPLOYEE_ID >= 0");
        
        if(employee.getFirstName() != null) {
            selectQuery.append(" AND FIRST_NAME LIKE ?");
            parameterList.add(employee.getFirstName());
        }
        
        if(employee.getLastName() != null) {
            selectQuery.append(" AND SECOND_NAME LIKE ?");
            
            parameterList.add(employee.getLastName());
        }
        
        PreparedStatement statement = connection.prepareStatement(
                selectQuery.toString());
        
        for(String parameter : parameterList) {
            //System.out.println(parameter + " " + parameterList.indexOf(parameter));
            statement.setString(parameterList.indexOf(parameter) + 1, parameter + "%");
        }
        //System.out.println(parameterArray.length + " " + parameterArray[0]);
        
        ResultSet resultSet = statement.executeQuery();
        
        // EMPLOYEE spesific attributes begin
        
        /*
        if(employee.getFirstName() != null) {
            selectQuery.append(" AND FIRST_NAME='");
            selectQuery.append(employee.getFirstName()).append("'");
        }
        
        if(employee.getLastName() != null) {
            selectQuery.append(" AND SECOND_NAME='");
            selectQuery.append(employee.getLastName()).append("'");
        }
        */
        
        /*
        selectQuery.append(";");
        System.out.println(selectQuery.toString());
        // May result in a database operation failure.
        Statement statement = connection.createStatement(); 
        ResultSet resultSet = statement.executeQuery(selectQuery.toString());
        */
 
        employeeList = populateEmployeeList(resultSet);
        
        resultSet.close();
        statement.close();

        return employeeList;
    }
    
    private int[] selectEmployeeIdAndEmploymentIdBySsn(
            String socialSecurityNumber) throws Exception {
        int[] IDArray = new int[2];
        
        PreparedStatement statement = connection.prepareStatement(
                "SELECT "
            +       "EMPLOYEE_ID, EMPLOYMENT_ID_FK "
            +   "FROM "
            +       "EMPLOYEE "
            +   "WHERE "
            +       "SSN = ?");
                
        statement.setString(1, socialSecurityNumber);
        
        ResultSet resultSet = statement.executeQuery();
        
        /*
        String selectQuery = 
                "SELECT "
            +       "EMPLOYEE_ID, EMPLOYMENT_ID_FK "
            +   "FROM "
            +       "EMPLOYEE "
            +   "WHERE "
            +       "SSN='" + socialSecurityNumber + "';";
        */
        
        // May result in a database operation failure.
        /*
        Statement statement = connection.createStatement(); 
        ResultSet resultSet = statement.executeQuery(selectQuery);
       */
        
        IDArray[0] = resultSet.getInt("EMPLOYEE_ID");
        IDArray[1] = resultSet.getInt("EMPLOYMENT_ID_FK");
        
        return IDArray;
    }
    
    private int selectEmployeeEmploymentIdByEmployeeId(int employeeId) 
            throws SQLException {
        
        int employmentID;
        
        PreparedStatement statement = connection.prepareStatement(
                "SELECT "
            +       "EMPLOYMENT_ID_FK "
            +   "FROM "
            +       "EMPLOYEE "
            +   "WHERE "
            +       "EMPLOYEE_ID = ?");
        
        statement.setInt(1, employeeId);
        /*
        String selectQuery = 
                "SELECT "
            +       "EMPLOYMENT_ID_FK "
            +   "FROM "
            +       "EMPLOYEE "
            +   "WHERE "
            +       "EMPLOYEE_ID=" + employeeId + ";";
        */
        // May result in a database operation failure.
        /*
        Statement statement = connection.createStatement(); 
        ResultSet resultSet = statement.executeQuery(selectQuery);
       
        employmentId = resultSet.getInt("EMPLOYMENT_ID_FK");
        */
        
        ResultSet resultSet = statement.executeQuery();
        employmentID = resultSet.getInt("EMPLOYMENT_ID_FK");
        
        resultSet.close();
        statement.close();
        
        return employmentID;
    }
    
    /* UPDATE EMPLOYEE with new values (sets again old value if no new value
     * given).
    */
    public void updateEmployee(Employee newEmployee) throws SQLException  {
        
        //Employee oldEmployee = selectEmployeeById(newEmployee.getEmployeeId());
        //System.out.println("old emp id: " + oldEmployee.getEmployeeId());
        
        // 0-element: employee id ; 1st element: employment id
        
        PreparedStatement statement = connection.prepareStatement(
                "UPDATE EMPLOYEE "
                +   "SET "
                +   "FIRST_NAME = ?, "
                +   "SECOND_NAME = ?, "
                +   "SSN = ?, "
                +   "BIRTHDAY = ?, "
                +   "ADDRESS = ?, "
                +   "POSTAL_CODE = ?, "
                +   "CITY = ?, "
                +   "PHONE_NUMBER = ?, "
                +   "EMAIL_ADDRESS = ?, "
                +   "FAVORITE_DRINK = ?, "
                +   "RIGHTS = ? "
                +   "WHERE EMPLOYEE_ID = ?");
                

        connection.setAutoCommit(false);
        //Statement statement = connection.createStatement();
        
        // EMPLOYEE INSERT portion begins
        /*
        StringBuilder sqlInsert = new StringBuilder(
                    "UPDATE EMPLOYEE "
                +   "SET ");
        */
        
        //sqlInsert.append("FIRST_NAME='");
       // if(newEmployee.getFirstName() != null) {
            statement.setString(1, newEmployee.getFirstName());
            //sqlInsert.append(newEmployee.getFirstName()).append("', ");
            //System.out.println("FIRST NAME: " + newEmployee.getFirstName());
        //}
        //else
            //statement.setString(1, oldEmployee.getFirstName());
            //sqlInsert.append(oldEmployee.getFirstName()).append("', ");
        
        //sqlInsert.append("SECOND_NAME='");
        //if(newEmployee.getLastName() != null)
            statement.setString(2, newEmployee.getLastName());
            //sqlInsert.append(newEmployee.getLastName()).append("', ");
       // else
            //statement.setString(2, oldEmployee.getLastName());
            //sqlInsert.append(oldEmployee.getLastName()).append("', ");
       
        //sqlInsert.append("BIRTHDAY='");
        //if(newEmployee.getBirthDay() != null)
            statement.setString(3, newEmployee.getSsn());
            statement.setString(4, newEmployee.getBirthDay());
            //sqlInsert.append(newEmployee.getBirthDay()).append("', ");
        //else
          // statement.setString(3, null);
            //sqlInsert.append(oldEmployee.getBirthDay()).append("', ");
        
        /*
        sqlInsert.append("SSN='");
        if(newEmployee.getSsn() != null)
            sqlInsert.append(newEmployee.getSsn()).append("', ");
        else
            sqlInsert.append(oldEmployee.getSsn()).append("', ");
        */
        
        //sqlInsert.append("ADDRESS='");
        //if(newEmployee.getAddress() != null)
            statement.setString(5, newEmployee.getAddress());
            //sqlInsert.append(newEmployee.getAddress()).append("', ");
        //else
          // statement.setString(4, "");
            //sqlInsert.append(oldEmployee.getAddress()).append("', ");
        
        //sqlInsert.append("POSTAL_CODE='");
        //if(newEmployee.getPostal() != null)
            statement.setString(6, newEmployee.getPostal());
            //sqlInsert.append(newEmployee.getPostal()).append("', ");
        //else
         //   statement.setString(5, oldEmployee.getPostal());
            //sqlInsert.append(oldEmployee.getPostal()).append("', ");

        //sqlInsert.append("CITY='");
        //if(newEmployee.getCity() != null)
            statement.setString(7, newEmployee.getCity());
            //sqlInsert.append(newEmployee.getCity()).append("', ");
       // else
           // statement.setString(6, oldEmployee.getCity());
            //sqlInsert.append(oldEmployee.getCity()).append("', ");
        
        //sqlInsert.append("PHONE_NUMBER='");
       // if(newEmployee.getPhone() != null)
            statement.setString(8, newEmployee.getPhone());
            //sqlInsert.append(newEmployee.getPhone()).append("', ");
       // else
           // statement.setString(7, oldEmployee.getPhone());
            //sqlInsert.append(oldEmployee.getPhone()).append("', ");
        
        //sqlInsert.append("EMAIL_ADDRESS='");
       // if(newEmployee.getEmail() != null)
            statement.setString(9, newEmployee.getEmail());
            //sqlInsert.append(newEmployee.getEmail()).append("', ");
       // else
           // statement.setString(8, oldEmployee.getEmail());
            //sqlInsert.append(oldEmployee.getEmail()).append("', ");
        
        //sqlInsert.append("FAVORITE_DRINK='");
       // if(newEmployee.getFavoriteDrink() != null)
            statement.setString(10, newEmployee.getFavoriteDrink());
            //sqlInsert.append(newEmployee.getFavoriteDrink()).append("', ");
       // else
            //statement.setString(9, oldEmployee.getFavoriteDrink());
            //sqlInsert.append(oldEmployee.getFavoriteDrink()).append("', ");
        
        //sqlInsert.append("RIGHTS=");
       //if(newEmployee.getRights() != -1)
            statement.setInt(11, newEmployee.getRights());
            //sqlInsert.append(newEmployee.getRights()).append(" ");
        //else
          //  statement.setInt(10, oldEmployee.getRights());
            //sqlInsert.append(oldEmployee.getRights()).append(" ");
        
        // Query end: WHERE EMPLOYEE_ID = ?
        statement.setInt(12, newEmployee.getEmployeeId());
        /*
        sqlInsert.append("WHERE EMPLOYEE_ID=");
        sqlInsert.append(oldEmployee.getEmployeeId());
        sqlInsert.append(";");
        */
        //statement.executeUpdate(sqlInsert.toString());
        
        statement.executeUpdate();
        
        statement = connection.prepareStatement(
                "UPDATE EMPLOYMENT "
                +   "SET "
                +   "JOB_TITLE = ?, "
                +   "HOURLY_WAGE = ?, "
                +   "START_DATE = ?, "
                +   "END_DATE = ?, "
                +   "WEEKLY_WORKHOURS = ? "
                +   "WHERE EMPLOYMENT_ID = ?");
                        
        
        // EMPLOYEMENT INSERT portion begins
        /*
        sqlInsert = new StringBuilder(
                    "UPDATE EMPLOYMENT "
                +   "SET ");
        */
        
        //sqlInsert.append("JOB_TITLE='");
        //if(newEmployee.getJobTitle() != null)
            statement.setString(1, newEmployee.getJobTitle());
            //sqlInsert.append(newEmployee.getJobTitle()).append("', ");
       // else
         //   statement.setString(1, oldEmployee.getJobTitle());
            //sqlInsert.append(oldEmployee.getJobTitle()).append("', ");
        
        //sqlInsert.append("HOURLY_WAGE=");
        //if(newEmployee.getJobWage() != -1)
            statement.setDouble(2, newEmployee.getJobWage());
            //sqlInsert.append(newEmployee.getJobWage()).append(", ");
       // else
         //   statement.setDouble(2, oldEmployee.getJobWage());
            //sqlInsert.append(oldEmployee.getJobWage()).append(", ");
        
        //sqlInsert.append("START_DATE='");
        //if(newEmployee.getStartDate() != null)
            statement.setString(3, newEmployee.getStartDate());
            //sqlInsert.append(newEmployee.getStartDate()).append("', ");
        //else
         //   statement.setString(3, oldEmployee.getStartDate());
            //sqlInsert.append(oldEmployee.getStartDate()).append("', ");
        
        //sqlInsert.append("END_DATE='");
       // if(newEmployee.getEndDate() != null)
            statement.setString(4, newEmployee.getEndDate());
            //sqlInsert.append(newEmployee.getEndDate()).append("', ");
        //else
        //    statement.setString(4, oldEmployee.getEndDate());
            //sqlInsert.append(oldEmployee.getEndDate()).append("', ");
        
        //sqlInsert.append("WEEKLY_WORKHOURS=");
       // if(newEmployee.getWeeklyHours() != -1)
            statement.setDouble(5, newEmployee.getWeeklyHours());
            //sqlInsert.append(newEmployee.getWeeklyHours()).append(" ");
        //else
        //    statement.setDouble(5, oldEmployee.getWeeklyHours());
            //sqlInsert.append(oldEmployee.getWeeklyHours()).append(" ");
        
        // Query end: WHERE EMPLOYEE_ID = ?
        //int id = selectEmployeeEmploymentIdByEmployeeId(
          //              oldEmployee.getEmployeeId());
        statement.setInt(6, newEmployee.getEmployeeId());
        
        /*
        sqlInsert.append("WHERE EMPLOYMENT_ID=");
        sqlInsert.append(selectEmployeeEmploymentIdByEmployeeId(oldEmployee.getEmployeeId()));
        sqlInsert.append(";");
        */
        
        statement.executeUpdate();
        //statement.executeUpdate(sqlInsert.toString());
        
        statement.close();
        connection.commit();
        
        connection.setAutoCommit(true);
    }
    
    public void deleteEmployeeByEmployeeId(int employeeId) throws SQLException {
        int employmentId = selectEmployeeEmploymentIdByEmployeeId(employeeId);
        //System.out.println(employmentId+" employee id:"+employeeId);
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