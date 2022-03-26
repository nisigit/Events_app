package logging;

import java.util.Map;
import java.util.stream.Collectors;

public class LogEntry {

    private String callerName;
    private Object result;
    private Map<String, String> additionalInfo;

    LogEntry(String callerName, Object result, Map<String,Object> additionalInfo) {
        this.callerName = callerName;
        this.result = result;
        this.additionalInfo = additionalInfo
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> String.valueOf(entry.getValue()))
                );
    };

    // TODO: What is this method for?
    public String getResult() {
        return result.toString();
    };

    @Override
    public String toString() {
        return "LogEntry{" +
                "callerName='" + callerName + '\'' +
                ", result=" + result +
                ", additionalInfo=" + additionalInfo +
                '}';
    }
}