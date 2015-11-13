/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.hr;

import java.sql.Date;
import java.sql.Timestamp;

/**
 *
 * @author Anton
 */
public class Employee {

    private String firstName;
    private String lastName;
    private String birthDay;
    private String ssn;
    private String address;
    private String postal;
    private String city;
    private String phone;
    private String email;
    private String favoriteDrink;
    private String jobTitle;
    private double jobWage;
    /*private Date startDate;
    private Date endDate;*/
    private String startDate;
    private String endDate;
    private double weeklyHours;
    private String passwordHashAndSalt;
    private boolean loggedIn = false;
    
    public Employee () {
    
    }
    
    public Employee(String fn,
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
            String pw) {
        firstName = fn;
        lastName = ln;
        birthDay = bd;
        ssn = s;
        address = addr;
        postal = p;
        city = c;
        email = e;
        favoriteDrink = fav;
        jobTitle = title;
        jobWage = wage;
        startDate = start;
        endDate = end;
        weeklyHours = hours;
        passwordHashAndSalt = pw;
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

    public String getPasswordHashAndSalt() {
        return passwordHashAndSalt;
    }

    public void setPasswordHashAndSalt(String passwordHashAndSalt) {
        this.passwordHashAndSalt = passwordHashAndSalt;
    }
}
