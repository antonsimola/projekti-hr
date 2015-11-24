/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.hr;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Anton
 */
public class Controller implements PropertyChangeListener  {
    private static Controller instance = null;
    private Model model;
    private ArrayList<Object> views;
    private Employee currentlySelectedEmployee;
    ObservableList<Employee> obsEmps = null;
    
    private Controller() {
        model = new Model();
        model.addPropertyChangeListener(this);
        /*for (int i = 0;i<1001;i++) {
            model.registerEmployee(new Employee(
                "Jouni"+i,
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
                "trigonometria");
        }*/
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
        } else if (view instanceof FXMLDocumentSResults) {
            views.add((FXMLDocumentSResults) view); 
        } else if (view instanceof FXMLDocumentAddView) {
            views.add((FXMLDocumentAddView) view); 
        } else if (view instanceof FXMLDocumentEditView) {
            views.add((FXMLDocumentEditView) view); 
        } else if (view instanceof FXMLDocumentSearchView) {
            views.add((FXMLDocumentSearchView) view); 
        } else {
            System.out.println("Not known view!!");
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
        Pattern p = Pattern.compile(".+@.+");
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
    private Employee generateEmployee(String fn,
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
        Employee emp = new Employee();
        if (!fn.isEmpty())
            emp.setFirstName(fn);
        if (!ln.isEmpty())
            emp.setLastName(ln);
        if (!bd.isEmpty())
            emp.setBirthDay(bd);
        if (!ssn.isEmpty())
            emp.setSsn(ssn);
        if (!addr.isEmpty())
            emp.setAddress(addr);
        if (!p.isEmpty())
            emp.setPostal(p);
        if (!c.isEmpty())
            emp.setCity(c);
        if (!phone.isEmpty())
            emp.setPhone(phone);
        if (!email.isEmpty())
            emp.setEmail(email);
        if (!fav.isEmpty())
            emp.setFavoriteDrink(fav);
        if (!title.isEmpty()) 
            emp.setJobTitle(title);
        if (!start.isEmpty())
            emp.setStartDate(start);
        if (!end.isEmpty())
            emp.setEndDate(end);        

        if (isValidNumber(wage))
            emp.setJobWage(Double.parseDouble(wage));

        if (isValidNumber(hours))
            emp.setWeeklyHours(Double.parseDouble(hours));
        return emp;
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
        this.currentlySelectedEmployee = currentlySelectedEmployee;
    }
    
    public ObservableList<Employee> getSearchResults() {
         return obsEmps;
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
        String[] required = {fn,ln,bd,ssn,title,start,hours,wage,email};
        /*return false, if not OK, else return true*/
        if (isEmpty(required) == true) {
            for (Object view:views) {
                if (view instanceof MainView) {
                    MainView mv = (MainView) view;
                    mv.updateStatusField("Täytä pakolliset kentät!");
                }
            }
            return false;
        } else if (!isValidEmail(email)) {
            for (Object view:views) {
                if (view instanceof MainView) {
                    MainView mv = (MainView) view;
                    mv.updateStatusField("Email on väärin kirjoitettu!");
                }
            }
            return false;
        } else {
            Employee emp = generateEmployee(
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
            wage,
            start,
            end,
            hours);
            emp.setPassword("vakio");
            emp.setRights(1);
            model.addEmployee(emp);
            return true;
        }
    };
    
    public boolean attemptUpdateEmployee(String fn,
            String ln,
            String bd,
            String ssn,
            String addr,
            String postal,
            String city,
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
            return false;
        }
        else {
            Employee emp = generateEmployee(
            fn,
            ln,
            bd,
            ssn,
            addr,
            postal,
            city,
            phone,
            email,
            fav,
            title,
            wage,
            start,
            end,
            hours);
            emp.setEmployeeId(currentlySelectedEmployee.getEmployeeId());
            emp.setRights(currentlySelectedEmployee.getRights());
            model.editEmployee(emp);
            return true;
        }
    };
    
    public void attemptRemoveEmployee() {
        
        model.removeEmployee(currentlySelectedEmployee.getEmployeeId());
    }
    
    public void searchEmployee(String fn,
            String ln,
            String startBd,
            String endBd,
            String ssn,
            String addr,
            String city,
            String postal,
            String phone,
            String email,
            String fav,
            String title,
            String startWage,
            String endWage,
            String startHours,
            String endHours,
            String startDate,
            String endDate) {
        Employee startEmp = generateEmployee(
            fn,
            ln,
            startBd,
            ssn, //SSN
            addr,
            postal,
            city,
            phone,
            email,
            fav,
            title,
            startWage,
            startDate,
            "",
            startHours);
        Employee endEmp = generateEmployee(
            "",
            "",
            endBd,
            "", //SSN
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            endWage,
            "",
            endDate,
            endHours);
        model.searchEmployee(startEmp, endEmp);
        
    }
    
    public void createPDF(ObservableList<Employee> emps, HashMap<String, Boolean> selectedColumns) {
        model.createSearchResultsPDF(emps,selectedColumns);
    }
    
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
                        MainView mv = (MainView) view;
                        Boolean b = (Boolean) evt.getNewValue();
                        if (b) {
                            mv.updateStatusField("Lisääminen onnistui");
                            getAllEmployees();
                        } else {
                            mv.updateStatusField("Lisääminen epäonnistui");
                        }
                        
                    }
                }
                break;
            case "edit":
                for (Object view:views) {
                    if (view instanceof FXMLDocumentEditView) {
                        FXMLDocumentEditView ev = (FXMLDocumentEditView) view;
                        ev.updateFinished((Boolean)evt.getNewValue());
                        getAllEmployees();
                    }
                }
                break;
            case "remove":
                for (Object view:views) {
                    if (view instanceof MainView) {
                        MainView mv = (MainView) view;
                        Boolean b = (Boolean) evt.getNewValue();
                        if (b) {
                            mv.updateStatusField("Poisto onnistui");
                            getAllEmployees();
                        } else {
                            //mv.removeFinished(b);
                            mv.updateStatusField("Poisto epäonnistui");
                        }
                        
                    }
                }
                break;
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
            case "search_by_employee":
                if (evt.getNewValue() == null) {
                    for (Object view:views) {
                        if (view instanceof MainView) {
                            System.out.println("Ei löytynyt kettään");
                            MainView mv = (MainView) view;
                            mv.updateStatusField("Haku ei tuottanut yhtään tulosta.");
                        }
                    }
                } else {
                    ArrayList<Employee> emps = (ArrayList<Employee>) evt.getNewValue();
                    obsEmps = FXCollections.observableArrayList(emps);
                    for (Object view:views) {
                        if (view instanceof FXMLDocumentSResults) {
                            FXMLDocumentSResults resultView = (FXMLDocumentSResults) view;
                            resultView.fillTable(obsEmps);
                        }
                    }
                }
                break;
            case "create_pdf":
                break;
            default:
                System.out.println("default");
                break;
        }
    }
    
}
