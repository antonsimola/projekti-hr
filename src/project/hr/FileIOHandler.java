/* FileIOHandler.java - Handler class for all file write/read
 *
 * Samuli Siitonen
 *
 * Sources:
 * http://stackoverflow.com/questions/1053467/how-do-i-save-a-string-to-a-text-file-using-java
 * http://stackoverflow.com/questions/8210616/printwriter-append-method-not-appending
 * http://stackoverflow.com/questions/5868369/how-to-read-a-large-text-file-line-by-line-using-java
 */
package project.hr;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPRow;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.util.HashMap;
import java.util.Map;
import javafx.collections.ObservableList;

/**
 *
 * @author Samuli
 */
public class FileIOHandler {
    private static FileIOHandler instance = null;
    private String userLogFilename;
    private DateFormat dateFormat;
    
    public static final int EMPTY_ID = -1;
    public static final String TARGET_MULTIPLE = "<multiple>";
    public static final String TARGET_ALL = "<all>";
    
    public static final String ACTION_SIGN_IN = "sign_in";
    public static final String ACTION_ADD = "add";
    public static final String ACTION_REMOVE = "remove";
    public static final String ACTION_EDIT = "change";
    public static final String ACTION_SEARCH = "search";
    
    public static final String ACTION_SUCCESS = "success";
    public static final String ACTION_FAILURE = "failure";
    
    public static final String EMPTY = "<empty>";
    
    private FileIOHandler() {
       userLogFilename = "HR_user_log.txt";
       dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
    }
    
    public static FileIOHandler getInstance() {
        if(instance == null) {
            instance = new FileIOHandler();
        }
        return instance;
    }
    
    // Write log unbuffered
    public void writeUserLog(String username, String action, 
            String actionSuccess, int targetID, String targetName, String targetSSN) {
        
        File file = new File(userLogFilename);
        PrintWriter printWriter;
        try {
            printWriter = new PrintWriter(new FileOutputStream(
                    file, true));
        
            Date date = new Date();

            String output = username +
                            "|" +
                            action +
                            "|" +
                            actionSuccess +
                            "|" +
                            Integer.toString(targetID) +
                            "|" +
                            targetName +
                            "|" +
                            targetSSN +
                            "|" +
                            dateFormat.format(date) +
                            "\n";

            printWriter.write(output);

            printWriter.close();
            
        } catch (FileNotFoundException ex) {
            
        }
    }
    
    // Read log buffered
    public ArrayList<String[]> readUserLog() {
        ArrayList<String[]> lineList = new ArrayList();
        
        File file = new File(userLogFilename);

        FileReader fileReader = null;
        try {
            fileReader = new FileReader(file);
        } catch (FileNotFoundException ex) {
           
        }
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        
        String readLine;
        try {
            while((readLine = bufferedReader.readLine()) != null) {
                lineList.add(readLine.split("|"));
            }
        } catch (IOException ex) {
            
        }
        
        return lineList;
    }
    
    public void writePDF(ObservableList<Employee> employees, 
            HashMap<String, Boolean> limits) {
        int columnCount = 0;
        
        Document document = new Document();

        try {
            PdfWriter.getInstance(document,
                new FileOutputStream("HelloWorld-Table.pdf"));

            document.open();
            
            // http://java67.blogspot.fi/2013/08/best-way-to-iterate-over-each-entry-in.html
            for(Map.Entry<String, Boolean> entry : limits.entrySet()) {
                if(entry.getValue().booleanValue())
                    columnCount++;
            }
            
            PdfPTable table = new PdfPTable(columnCount);
            
            PdfPCell tableCell;
            for(Employee employee : employees) {
                if(limits.get("firstName").booleanValue()) {
                    tableCell = new PdfPCell(new Paragraph(employee.getFirstName()));
                    table.addCell(tableCell);
                }
                if(limits.get("lastName").booleanValue()) {
                    tableCell = new PdfPCell(new Paragraph(employee.getLastName()));
                    table.addCell(tableCell);
                }
            }

            document.add(table);

            document.close();
        } catch(Exception e){

        }
    }
}
