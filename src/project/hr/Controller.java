/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.hr;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Anton
 */
public class Controller implements PropertyChangeListener  {
    private static Controller instance = null;
    private Model model;
    private ArrayList<Object> views;
    private Employee currentlySelectedEmployee;
    
    private Controller() {
        model = new Model();
        model.addPropertyChangeListener(this);
        /*model.registerEmployee(new Employee(
                "Jouni",
                "Sampo",
                "2002-21-12",
                "12213123-2213",
                "funktiokatu",
                "12312",
                "LPR",
                "040314123123",
                "jouni@sampo",
                "ES",
                "matikkagod",
                12,
                "1000-12-12",
                "1001-12-12",
                40,
                null,
                1),
                "trigonometria");
                model.registerEmployee(new Employee(
                "Matti",
                "Sampo",
                "2002-21-12",
                "12213123-2213",
                "funktiokatu",
                "12312",
                "LPR",
                "040314123123",
                "jouni@sampo",
                "ES",
                "matikkagod",
                12,
                "1000-12-12",
                "1001-12-12",
                40,
                null,
                1),
                "trigonometria");*/
        views = new ArrayList();
    }
    
    public static Controller getInstance() {
        if (instance == null) {
            instance = new Controller();
        }
        return instance;
    }
    public void registerView (Object view) {
        /*convert TO RIGHT CLASS FIRST*/
        if(view instanceof MainView) {
           views.add((MainView) view);
        } else if (view instanceof LoginWindow) {
            views.add((LoginWindow) view); 
        }
    }
    private boolean isEmpty(String[] list) {
        for (String str:list) {
            if (str.isEmpty()) {
                return true;
            }
        }
        return false;
    }
    
    private boolean isValidEmail(String email) {
        Pattern p = Pattern.compile(".+\\@.+\\..+");
        Matcher m  = p.matcher(email);
        return m.matches();
    }
    
    private boolean isValidNumber(String strnum) {
        try {
            Double.parseDouble(strnum);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
    
    /*Call from view when attempting sign in*/
    public void attemptSignIn(String username, String pw) {
        /* View calls this as sign in was pressed -> Model method call? */
        /*some input validation goes here first, then call model's method*/
        model.signIn(username,pw);
        /* after this we need to catch event in listener below*/
    }
    
    public Employee getSignedUser() {
        return model.getSignedInEmployee();
    }
    
    public void getAllEmployees() {
        model.getAllEmployees();
        
        
    }

    public Employee getCurrentlySelectedEmployee() {
        return currentlySelectedEmployee;
    }

    public void setCurrentlySelectedEmployee(Employee currentlySelectedEmployee) {
        model.selectEmployeeById(currentlySelectedEmployee.getEmployeeId());
    }
    
    
    
    public boolean attemptAddEmployee(String fn,
            String ln,
            String bd,
            String ssn,
            String addr,
            String p,
            String c,
            String phone,
            String email,
            String fav,
            String title,
            String wage,
            String start,
            String end,
            String hours) {
        String[] required = {fn,ln,bd,ssn,title,start,hours,wage};
        /*return false, if not OK, else return true*/
        if (isEmpty(required) == true) {
            System.out.println("Tyhjiä kenttiä.");
            return false;
        } else {
            Employee emp = new Employee(
            fn,
            ln,
            bd,
            ssn,
            addr,
            p,
            c,
            phone,
            email,
            fav,
            title,
            Double.parseDouble(wage),
            start,
            end,
            Double.parseDouble(hours),
            "vakio",
            1);
            model.addEmployee(emp);
            return true;
        }
    };
    
    public boolean attemptUpdateEmployee(String fn,
            String ln,
            String bd,
            String ssn,
            String addr,
            String p,
            String c,
            String phone,
            String email,
            String fav,
            String title,
            String wage,
            String start,
            String end,
            String hours) {
        String[] required = {fn,ln,bd,ssn,title,start,hours,wage};
        /*return false, if not OK, else return true*/
        if (isEmpty(required)) {
            System.out.println("Tyhjiä kenttiä.");
            return false;
        }
        if (isValidNumber(wage) && isValidNumber(hours)) {
            
        }
        else {
            Employee emp = new Employee(
            fn,
            ln,
            bd,
            ssn,
            addr,
            p,
            c,
            phone,
            email,
            fav,
            title,
            Double.parseDouble(wage),
            start,
            end,
            Double.parseDouble(hours),
            "vakio",
            1);
            model.editEmployee(emp);
            return true;
        }
    };
    
    /*listens for updates in the model*/
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        Employee emp;
        switch (evt.getPropertyName()) {

            case "all_employees":
                for (Object view:views) {
                    if (view instanceof MainView) {
                        MainView mv = (MainView) view;
                        Object o = evt.getNewValue();
                        ArrayList<Employee> array = (ArrayList<Employee>) o;
                        mv.updateEmployeeList(array);
                    }
                }
                break;
            case "add":
                for (Object view:views) {
                    if (view instanceof MainView) {
                        Boolean b = (Boolean) evt.getNewValue();
                        if (b.booleanValue()) {
                            getAllEmployees();
                        } else {
                            //view.errorMsg() blabla
                        }
                        
                    }
                }
                break;
            case "edit":
                for (Object view:views) {
                    if (view instanceof FXMLDocumentEditView) {
                        FXMLDocumentEditView ev = (FXMLDocumentEditView) view;
                        ev.updateFinished((Boolean)evt.getNewValue());
                    }
                }    
            case "sign_in":
                LoginWindow lw;
                Object foundView = false;
                boolean isLoginSuccessful = false;
                boolean foundLoginWindows = false;
                for (Object view:views) {
                    if (view instanceof LoginWindow) {
                        foundLoginWindows = true;
                        if(evt.getNewValue() == null) {
                            foundView = view;
                        } else {
                            foundView = view;
                            isLoginSuccessful = true;
                            
                        }
                    }
                }
                if(foundLoginWindows) {
                    lw = (LoginWindow) foundView;
                    lw.logIn(isLoginSuccessful);
                }
                break;
            case "search_by_id":
                for (Object view:views) {
                    if (view instanceof FXMLDocumentEditView) {
                        FXMLDocumentEditView ev = (FXMLDocumentEditView) view;
                        this.setCurrentlySelectedEmployee((Employee)evt.getNewValue());
                        ev.updateFields(evt.getNewValue());
                    }
                }
            default:
                System.out.println("default");
                break;
        }
    }
    
}
