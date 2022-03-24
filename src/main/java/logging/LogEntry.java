package logging;

import java.util.Map;

public class LogEntry extends Object {

    private String callerName;
    private Object result;
    private Map<String, Object> additionalInfo;

    LogEntry(String callerName, Object result, Map<String,Object> additionalInfo) {
        this.callerName = callerName;
        this.result = result;
        this.additionalInfo = additionalInfo;
    };

    // TODO: What is this method for?
    public String getResult() {
    };

    // TODO: How to stringify the additional info map?
    public String toString() {
        String string = "";
        string += "Caller Name: " + this.callerName;
        string += "\nresult: " + this.result.toString();
        return string;
    };

}