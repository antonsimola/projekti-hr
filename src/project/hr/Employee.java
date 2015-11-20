/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.hr;

/**
 *
 * @author Anton
 */
public class Employee {
    private int employeeId = -1;
    private String firstName = null;
    private String lastName = null;
    private String birthDay = null;
    private String ssn = null;
    private String address = null;
    private String postal = null;
    private String city = null;
    private String phone = null;
    private String email = null;
    private String favoriteDrink = null;
    private String jobTitle = null;
    private double jobWage = -1;
    /*private Date startDate;
    private Date endDate;*/
    private String startDate = null;
    private String endDate = null;
    private double weeklyHours = -1;
    private String password ="vakio";
    private int rights = 1;
    private boolean loggedIn = false;
    
    public Employee () {
    
    }
    
    /*Constructor without id*/
    public Employee(
            String fn,
            String ln,
            String bd,
            String s,
            String addr,
            String p,
            String c,
            String phone,
            String e,
            String fav,
            String title,
            double wage,
            String start,
            String end,
            double hours,
            String pw,
            int r) {
        firstName = fn;
        lastName = ln;
        birthDay = bd;
        ssn = s;
        address = addr;
        postal = p;
        city = c;
        this.phone = phone;
        email = e;
        favoriteDrink = fav;
        jobTitle = title;
        jobWage = wage;
        startDate = start;
        endDate = end;
        weeklyHours = hours;
        password = pw;
        rights = r;
    }
    
    public Employee(int id,
            String fn,
            String ln,
            String bd,
            String s,
            String addr,
            String p,
            String c,
            String phone,
            String e,
            String fav,
            String title,
            double wage,
            String start,
            String end,
            double hours,
            String pw,
            int r) {
        employeeId = id;
        firstName = fn;
        lastName = ln;
        birthDay = bd;
        ssn = s;
        address = addr;
        postal = p;
        city = c;
        this.phone = phone;
        email = e;
        favoriteDrink = fav;
        jobTitle = title;
        jobWage = wage;
        startDate = start;
        endDate = end;
        weeklyHours = hours;
        password = pw;
        rights = r;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }
    
    
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostal() {
        return postal;
    }

    public void setPostal(String postal) {
        this.postal = postal;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFavoriteDrink() {
        return favoriteDrink;
    }

    public void setFavoriteDrink(String favoriteDrink) {
        this.favoriteDrink = favoriteDrink;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public double getJobWage() {
        return jobWage;
    }

    public void setJobWage(double jobWage) {
        this.jobWage = jobWage;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
    

    

    public double getWeeklyHours() {
        return weeklyHours;
    }

    public void setWeeklyHours(double weeklyHours) {
        this.weeklyHours = weeklyHours;
    }

    public boolean getLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRights() {
        return rights;
    }

    public void setRights(int rights) {
        this.rights = rights;
    }
    
    @Override
    public String toString() {
        return firstName+ " " + lastName;
    }
}
