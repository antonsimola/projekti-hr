/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.hr;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 *
 * @author Anton
 */
public class Controller implements PropertyChangeListener  {
    
    Model model;
    MainView view;
    public Controller(MainView v) {
        model = new Model();
        model.addPropertyChangeListener(this);
        view = v;
    }
    
    
    /*Call from view when attempting sign in*/
    public void attemptSignIn(/*username pw*/) {
        /* View calls this as sign in was pressed -> Model method call? */
        /*some input validation goes here first, then call model's method*/
        model.signIn("a", "b");
        /* after this we need to catch event in listener below*/
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
            String start,
            String end,
            String hours) {
     
        return true;  
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
        if(evt.getPropertyName().equals("login")) {
            Employee emp = (Employee)evt.getNewValue();
            view.logIn(emp);
        }
    }
    
}
