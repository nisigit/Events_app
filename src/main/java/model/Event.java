package model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public abstract class Event {

    private EventStatus eventStatus;
    protected long eventNumber;
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
    }

    public long getEventNumber() {
        return eventNumber;
    }

    public EntertainmentProvider getOrganiser() {
        return organiser;
    }

    public String getTitle() {
        return title;
    }

    public EventType getType() {
        return type;
    }

    public EventStatus getStatus() {
        return eventStatus;
    }

    public void cancel() {
        this.eventStatus = EventStatus.CANCELLED;
    }

    public void addPerformance(EventPerformance performance) {
        eventPerformances.put(performance.getPerformanceNumber(), performance);
    }

    public EventPerformance getPerformanceByNumber(long performanceNumber) {
        return eventPerformances.get(performanceNumber);
    }

    public Collection<EventPerformance> getPerformances() {
        return eventPerformances.values();
    }

    @Override
    public String toString() {
        return "Event info: \n" + "ID number: " + eventNumber + "\nTitle: " + title
                + "\nType: " + type + "\nStatus: " + eventStatus + "\nOrganiser: " + organiser.getOrgName()
                + "\nPerformances: " + eventPerformances;
    }

    // added for testing purposes
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Event event = (Event) o;

        if (eventStatus != event.eventStatus) return false;
        if (organiser != null ? !organiser.getOrgName().equals(event.organiser.getOrgName()) : event.organiser != null) return false;
        if (title != null ? !title.equals(event.title) : event.title != null) return false;
        if (type != event.type) return false;
        return eventPerformances != null ? eventPerformances.equals(event.eventPerformances) : event.eventPerformances == null;
    }

    @Override
    public int hashCode() {
        int result = eventStatus != null ? eventStatus.hashCode() : 0;
        result = 31 * result + (organiser != null ? organiser.getOrgName().hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (eventPerformances != null ? eventPerformances.hashCode() : 0);
        return result;
    }
}