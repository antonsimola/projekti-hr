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
    private ArrayList<Object> views = new ArrayList();
    
    private Controller() {
        model = new Model();
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
    
    /*model is ready with response:  catch the event which model fired */
    /*temp name*/
    public void signInListener(/*Event e*/) {
        /*inform view about whether sign in succeeded or not (tempname)*/
        //view.logIn(/*true or false*/);
    }
    
    /* this one is general property change listener: 
    model updated some employee data*/
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case "login":
                Employee emp = (Employee)evt.getNewValue();
                
                LoginWindow lw = (LoginWindow) views.get(0);
                lw.logIn();
                break;
            case "all_employees":
                MainView mv = (MainView) views.get(1);
                mv.updateEmployeeList((ArrayList<Employee>)evt.getNewValue());
                break;
        }
    }
    
}
