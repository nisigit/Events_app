package model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public abstract class Event {

    private EventStatus eventStatus;
    private long eventNumber;
    private EntertainmentProvider organiser;
    private String title;
    private EventType type;
    private Map<Long, EventPerformance> eventPerformances;

    protected Event(long eventNumber, EntertainmentProvider organiser, String title, EventType type) {
        this.eventPerformances = new HashMap<>();
        this.eventStatus = EventStatus.ACTIVE;
        this.eventNumber = eventNumber;
        this.organiser = organiser;
        this.title = title;
        this.type = type;
    };

    public long getEventNumber() {
        return eventNumber;
    };

    public EntertainmentProvider getOrganiser() {
        return organiser;
    };

    public String getTitle() {
        return title;
    };

    public EventType getType() {
        return type;
    };

    public EventStatus getStatus() {
        return eventStatus;
    };

    public void cancel() {
        this.eventStatus = EventStatus.CANCELLED;
    };

    public void addPerformance(EventPerformance performance) {
        eventPerformances.put(performance.getPerformanceNumber(), performance);
    };

    public EventPerformance getPerformanceByNumber(long performanceNumber) {
        return eventPerformances.get(performanceNumber);
    };

    public Collection<EventPerformance> getPerformances() {
        return eventPerformances.values();
    };

}