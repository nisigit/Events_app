package model;

import java.util.Collection;
import java.util.HashMap;

public abstract class Event {

    private EventStatus eventStatus;
    private long eventNumber;
    private EntertainmentProvider organiser;
    private String title;
    private EventType type;
    private HashMap<Long, EventPerformance> EventPerformances;

    protected Event(long eventNumber, EntertainmentProvider organiser, String title, EventType type) {
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
        EventPerformances.put(performance.getPerformanceNumber(), performance);
    };

    public EventPerformance getPerformanceByNumber(long performanceNumber) {
        return EventPerformances.get(performanceNumber);
    };

    public Collection<EventPerformance> getPerformances() {
        return EventPerformances.values();
    };

}