package model;

import java.time.LocalDateTime;
import java.util.List;

public class EventPerformance {

    private long performanceNumber;
    private Event event;
    private String venueAddress;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private List<String> performerNames;
    private boolean hasSocialDistancing;
    private boolean hasAirFiltration;
    private boolean isOutdoors;
    private int capacityLimit;
    private int venueSize;

    public EventPerformance(long performanceNumber, Event event, String venueAddress, LocalDateTime startDateTime, LocalDateTime endDateTime, List<String> performerNames, boolean hasSocialDistancing, boolean hasAirFiltration, boolean isOutdoors, int capacityLimit, int venueSize) {
        this.performanceNumber = performanceNumber;
        this.event = event;
        this.venueAddress = venueAddress;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.performerNames = performerNames;
        this.hasAirFiltration = hasAirFiltration;
        this.hasSocialDistancing = hasSocialDistancing;
        this.isOutdoors = isOutdoors;
        this.capacityLimit = capacityLimit;
        this.venueSize = venueSize;
    }


    public long getPerformanceNumber() {
        return performanceNumber;
    }

    public Event getEvent() {
        return event;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public boolean hasSocialDistancing() {
        return hasSocialDistancing;
    }

    public boolean hasAirFiltration() {
        return hasAirFiltration;
    }

    public boolean isOutdoors() {
        return isOutdoors;
    }

    public int getCapacityLimit() {
        return capacityLimit;
    }

    public int getVenueSize() {
        return venueSize;
    }

    @Override
    public String toString() {
        return "EventPerformance{" +
                "performanceNumber=" + performanceNumber +
                ", event=" + event.getTitle() +
                ", venueAddress='" + venueAddress + '\'' +
                ", startDateTime=" + startDateTime +
                ", endDateTime=" + endDateTime +
                ", performerNames=" + performerNames +
                ", hasSocialDistancing=" + hasSocialDistancing +
                ", hasAirFiltration=" + hasAirFiltration +
                ", isOutdoors=" + isOutdoors +
                ", capacityLimit=" + capacityLimit +
                ", venueSize=" + venueSize +
                '}';
    }
}
