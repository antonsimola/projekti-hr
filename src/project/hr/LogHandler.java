/* LogHandler.java - Handler class for (error) log writing
 *
 * Samuli Siitonen
 */
package project.hr;

import java.io.IOException;
import java.util.logging.Formatter;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 *
 * @author Samuli
 */
public class LogHandler {
    
    private static LogHandler instance = null;
    private static final Logger logger = Logger.getLogger("HR_logger");
    
    
    private LogHandler() {
        initializeLogger();
    }
    
    public static LogHandler getInstance() {
        if(instance == null) {
            instance = new LogHandler();
        }
        
        return instance;
    }
    
    public Logger getLogger() {
        return logger;
    }
    
    
    private void initializeLogger() {
        logger.setUseParentHandlers(false);
        
        // http://stackoverflow.com/questions/15758685/how-to-write-logs-in-text-file-when-using-java-util-logging-logger
        FileHandler fileHandler = null; 
        try {
            fileHandler = new FileHandler("hr_error_log.log", true);
        } catch (IOException | SecurityException ex) {
        
        }
        
        /*
        // http://www.rgagnon.com/javadetails/java-0501.html
        fileHandler.setFormatter(new Formatter() {
            public String format(LogRecord rec) {
                StringBuffer buffer = new StringBuffer(1000);
                buffer.append(new java.util.Date());
                buffer.append(' ');
                buffer.append(formatMessage(rec));
                buffer.append('\n');
                
                return buffer.toString();
            }
        });
        */
        
        SimpleFormatter formatter = new SimpleFormatter();  
        fileHandler.setFormatter(formatter); 
        
        logger.addHandler(fileHandler);

        /*
        LogRecord logRecord = new LogRecord(Level.INFO, null); 
        logRecord.setMessage("11111111111\n22222241\n235325353");
        logger.log(logRecord);
        */
    }
}
