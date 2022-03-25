package logging;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Logger {

    private List<LogEntry> logEntrys;
    private static Logger logger;

    private Logger() {

    }

    public static Logger getInstance() {
        return logger;
    };

    public void logAction(String callerName, Object result) {
        LogEntry logEntry = new LogEntry(callerName, result, Collections.emptyMap());
        logEntrys.add(logEntry);
    };

    public void logAction(String callerName, Object result, Map<String,Object> additionalInfo) {
        LogEntry logEntry = new LogEntry(callerName, result, additionalInfo);
        logEntrys.add(logEntry);
    };

    public List<LogEntry> getLog() {
        return logEntrys;
    };

    public void clearLog() {
        this.logEntrys.clear();
    };

}