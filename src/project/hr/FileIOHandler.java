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

/**
 *
 * @author Samuli
 */
public class FileIOHandler {
    private static FileIOHandler instance = null;
    private String userLogFilename;
    private DateFormat dateFormat;
    
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
            String additionalInfo) throws Exception {
        
        File file = new File(userLogFilename);
        PrintWriter printWriter = new PrintWriter(new FileOutputStream(
                file, true));
        
        Date date = new Date();
        
        String output = dateFormat.format(date) +
                        "|" +
                        username +
                        "|" +
                        action +
                        "|" +
                        additionalInfo +
                        "\n";
                                   
        printWriter.write(output);
        
        printWriter.close();
    }
    
    // Read log buffered
    public ArrayList<String[]> readUserLog() throws FileNotFoundException, 
            IOException {
        ArrayList<String[]> lineList = new ArrayList();
        
        File file = new File(userLogFilename);

        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        
        String readLine;
        while((readLine = bufferedReader.readLine()) != null) {
            lineList.add(readLine.split("|"));
        }
        
        return lineList;
    }
}
