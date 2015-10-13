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
public class Controller /* implements PropertyChangeListener */ {
    
    Model model;
    MainView view;
    public Controller(MainView v) {
        model = new Model();
        view = v;
    }
    
    public void attemptSignIn(/*username pw*/) {
        /* View calls this as sign in was pressed -> Model method call? */
        /*some input validation goes here first, then call model's method*/
        model.someSignInMethod(/*username, pw*/);
        /* after this we need to catch event in listener below*/
    }
    
    /*model is ready with response:  catch the event which model fired */
    /*temp name*/
    public void signInListener(/*Event e*/) {
        /*inform view about whether sign in succeeded or not (tempname)*/
        view.logIn(/*true or false*/);
    }
    
    /* this one is general property change listener: 
    model updated some employee data*/
    public void propertyChange(/*PropertyChangeEvent evt*/) {
        /*inform view about whether sign in succeeded or not */
    }
    
}
