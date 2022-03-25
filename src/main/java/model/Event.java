package model;

public abstract class Event {

    protected Event(long eventNumber, EntertainmentProvider organiser, String title, EventType type) {

    };

    public long getEventNumber() {

    };

    public EntertainmentProvider getOrganiser() {

    };

    public String getTitle() {

    };

    public EventType getType() {

    };

    public EventStatus getStatus() {

    };

    public void cancel() {

    };

    public void addPerformance(EventPerformance performance) {

    };

    public EventPerformance getPerformanceByNumber(long performanceNumber) {

    };

    public EventPerformance getPerformances() {

    };

}