/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.hr;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

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
    
    /*Call from view when attempting sign in*/
    public void attemptSignIn(/*username pw*/) {
        /* View calls this as sign in was pressed -> Model method call? */
        /*some input validation goes here first, then call model's method*/
        model.signIn("a", "b");
        /* after this we need to catch event in listener below*/
    }
    
    public void getAllEmployees () {
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
        return !isEmpty(required);  
    };
    
    /*listens for updates in the model*/
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case "login":
                Employee emp = (Employee)evt.getNewValue();
                for (Object view:views) {
                    if (view instanceof LoginWindow) {
                        LoginWindow lw = (LoginWindow) view;
                        lw.logIn();
                    }
                }
            
                break;
            case "all_employees":
                for (Object view:views) {
                    if (view instanceof MainView) {
                        MainView mv = (MainView) view;
                        mv.updateEmployeeList((ArrayList<Employee>)evt.getNewValue());
                    }
                }
                break;
        }
    }
    
}
