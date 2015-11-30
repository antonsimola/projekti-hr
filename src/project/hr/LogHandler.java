/* LogHandler.java - Handler class for (error) log writing
 *
 * Samuli Siitonen
 */
package project.hr;

import java.io.IOException;
import java.util.logging.FileHandler;
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
        
        SimpleFormatter formatter = new SimpleFormatter();  
        fileHandler.setFormatter(formatter); 
        
        logger.addHandler(fileHandler);
    }
}
