package logging;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Logger {

    private List<LogEntry> logEntries;
    private static Logger logger;

    private Logger() {
        logEntries = new ArrayList<>();
    }

    public static Logger getInstance() {
        if (logger == null) {
            logger = new Logger();
        }
        return logger;
    }

    public void logAction(String callerName, Object result) {
        LogEntry logEntry = new LogEntry(callerName, result, Collections.emptyMap());
        this.logEntries.add(logEntry);
    }

    public void logAction(String callerName, Object result, Map<String,Object> additionalInfo) {
        LogEntry logEntry = new LogEntry(callerName, result, additionalInfo);
        this.logEntries.add(logEntry);
    }

    public List<LogEntry> getLog() {
        return logEntries;
    }

    public void clearLog() {
        this.logEntries.clear();
    }

}