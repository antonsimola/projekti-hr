/* FileIOHandler.java - Handler class for all file write/read
 *
 * Samuli Siitonen
 *
 * Sources:
 * http://stackoverflow.com/questions/1053467/how-do-i-save-a-string-to-a-text-file-using-java
 * http://stackoverflow.com/questions/8210616/printwriter-append-method-not-appending
 * http://stackoverflow.com/questions/5868369/how-to-read-a-large-text-file-line-by-line-using-java
 * http://java67.blogspot.fi/2013/08/best-way-to-iterate-over-each-entry-in.html
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
import java.util.HashMap;
import java.util.Map;
import javafx.collections.ObservableList;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.util.logging.Level;

/**
 *
 * @author Samuli
 */
public class FileIOHandler {
    private static FileIOHandler instance = null;
    
    public static final String userLogFilename = "HR_user_log.txt";
    
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
       
    }
    
    public static FileIOHandler getInstance() {
        if(instance == null) {
            instance = new FileIOHandler();
        }
        return instance;
    }
    
    // Write log unbuffered
    public void writeUserLog(String username, String action, 
            String actionSuccess, int targetID, String targetName, 
            String targetSSN) {
        
        File file = new File(userLogFilename);
        PrintWriter printWriter = null;
        try {
            printWriter = new PrintWriter(new FileOutputStream(
                    file, true));
        } catch (FileNotFoundException ex) {
            LogHandler.getInstance().getLogger().log(Level.SEVERE, 
                    ex.getMessage());
        }

        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        Date date = new Date();

        String output = username +
                        ";" +
                        action +
                        ";" +
                        actionSuccess +
                        ";" +
                        targetName +
                        ";" +
                        targetSSN +
                        ";" +
                        dateFormat.format(date) +
                        "\n";

    
            printWriter.write(output);
            
            printWriter.close();

    }
    
    // Read log buffered
    public ArrayList<String[]> readUserLog() throws IOException {
        ArrayList<String[]> lineList = new ArrayList();
        
        File file = new File(userLogFilename);
        FileReader fileReader = new FileReader(file);
     
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        
        String readLine;
        try {
            while((readLine = bufferedReader.readLine()) != null) {
                lineList.add(readLine.split(";"));
            }
        }
        finally {
            bufferedReader.close();
        }
        return lineList;
    }
    
    public void writePDF(ObservableList<Employee> employees, 
            HashMap<String, Boolean> limits) throws DocumentException, FileNotFoundException {
        
        int columnCount = 0;
        Document document = new Document();
        
        DateFormat dateFormat = new SimpleDateFormat("ddMMyyHHmmss");
        Date date = new Date();

        PdfWriter.getInstance(document,
            new FileOutputStream("hakuraportti_" + dateFormat.format(date) + 
                    ".pdf"));

        document.open();

        for(Map.Entry<String, Boolean> entry : limits.entrySet()) {
            if(entry.getValue().booleanValue())
                columnCount++;
        }

        PdfPTable table = new PdfPTable(columnCount);

        PdfPCell tableCell;
        for(Employee employee : employees) {
            if(limits.get("firstName")) {
                tableCell = new PdfPCell(new Paragraph(employee.getFirstName()));
                table.addCell(tableCell);
            }

            if(limits.get("lastName")) {
                tableCell = new PdfPCell(new Paragraph(employee.getLastName()));
                table.addCell(tableCell);
            }

            if(limits.get("birthDay")) {
                tableCell = new PdfPCell(new Paragraph(employee.getBirthDay()));
                table.addCell(tableCell);
            }

            if(limits.get("ssn")) {
                tableCell = new PdfPCell(new Paragraph(employee.getSsn()));
                table.addCell(tableCell);
            }

            if(limits.get("address")) {
                tableCell = new PdfPCell(new Paragraph(employee.getAddress()));
                table.addCell(tableCell);
            }

            if(limits.get("city")) {
                tableCell = new PdfPCell(new Paragraph(employee.getCity()));
                table.addCell(tableCell);
            }

            if(limits.get("postal")) {
                tableCell = new PdfPCell(new Paragraph(employee.getPostal()));
                table.addCell(tableCell);
            }

            if(limits.get("phone")) {
                tableCell = new PdfPCell(new Paragraph(employee.getPhone()));
                table.addCell(tableCell);
            }

            if(limits.get("email")) {
                tableCell = new PdfPCell(new Paragraph(employee.getEmail()));
                table.addCell(tableCell);
            }

            if(limits.get("favoriteDrink")) {
                tableCell = new PdfPCell(new Paragraph(employee.getFavoriteDrink()));
                table.addCell(tableCell);
            }

            if(limits.get("jobTitle")) {
                tableCell = new PdfPCell(new Paragraph(employee.getJobTitle()));
                table.addCell(tableCell);
            }

            if(limits.get("jobWage")) {
                tableCell = new PdfPCell(new Paragraph(Double.toString(employee.getJobWage())));
                table.addCell(tableCell);
            }

            if(limits.get("weeklyHours")) {
                tableCell = new PdfPCell(new Paragraph(Double.toString(employee.getWeeklyHours())));
                table.addCell(tableCell);
            }

            if(limits.get("startDate")) {
                tableCell = new PdfPCell(new Paragraph(employee.getStartDate()));
                table.addCell(tableCell);
            }

            if(limits.get("endDate")) {
                tableCell = new PdfPCell(new Paragraph(employee.getEndDate()));
                table.addCell(tableCell);
            }
        }

        try {
            document.add(table);
        } 
        finally {
            document.close();
        }    
    }
}
