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
    
    private Controller() {
        model = new Model();
        model.addPropertyChangeListener(this);
        model.registerEmployee(new Employee(
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
                null),
                "trigonometria"); //uusisalasana
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
    
    /*Call from view when attempting sign in*/
    public void attemptSignIn(String username, String pw) {
        /* View calls this as sign in was pressed -> Model method call? */
        /*some input validation goes here first, then call model's method*/
        model.signIn(username,pw);
        /* after this we need to catch event in listener below*/
    }
    
    public void getAllEmployees() {
        System.out.println("Haetaan hlöt");
        model.getAllEmployees();
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
            null
            );
            model.addEmployee(emp);
            return true;
        }
    };
    
    /*listens for updates in the model*/
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        Employee emp;
        LoginWindow lw;
        switch (evt.getPropertyName()) {
            case "login":
                System.out.println("login event");
                emp = (Employee)evt.getNewValue();
                for (Object view:views) {
                    if (view instanceof LoginWindow) {
                        lw = (LoginWindow) view;
                        lw.logIn();
                    }
                }
            
                break;
            case "all_employees":
                for (Object view:views) {
                    if (view instanceof MainView) {
                        MainView mv = (MainView) view;
                        Object o = evt.getOldValue();
                        ArrayList<Employee> array = (ArrayList<Employee>) o;
                        mv.updateEmployeeList((ArrayList<Employee>)evt.getNewValue());
                    }
                }
                break;
            case "add":
                for (Object view:views) {
                    if (view instanceof MainView) {
                        getAllEmployees();
                    }
                }
            case "sign_in":
                for (Object view:views) {
                    if (view instanceof LoginWindow) {
                        if(evt.getNewValue() == null) {
                            lw = (LoginWindow) view;
                            lw.logIn(false);
                        } else {
                            lw.logIn(true);
                        }
                    }
                }
                break;
            default:
                System.out.println("default");
                break;
        }
    }
    
}
