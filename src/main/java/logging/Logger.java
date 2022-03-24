package logging;

import java.util.List;
import java.util.Map;

public class Logger extends Object {

    private List<LogEntry> logEntrys;
    private static Logger logger;

    private Logger() {

    }

    public static Logger getInstance() {
        return logger;
    };

    public void logAction(String callerName, Object result) {

    };

    public void logAction(String callerName, Object result, Map<String,Object> additionalInfo) {

    };

    public List<LogEntry> getLog() {
        return logEntrys;
    };

    public void clearLog() {

    };

}