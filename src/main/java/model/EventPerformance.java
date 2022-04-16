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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EventPerformance that = (EventPerformance) o;

        if (performanceNumber != that.performanceNumber) return false;
        if (hasSocialDistancing != that.hasSocialDistancing) return false;
        if (hasAirFiltration != that.hasAirFiltration) return false;
        if (isOutdoors != that.isOutdoors) return false;
        if (capacityLimit != that.capacityLimit) return false;
        if (venueSize != that.venueSize) return false;
        if (event != null ? !event.equals(that.event) : that.event != null) return false;
        if (venueAddress != null ? !venueAddress.equals(that.venueAddress) : that.venueAddress != null) return false;
        if (startDateTime != null ? !startDateTime.equals(that.startDateTime) : that.startDateTime != null)
            return false;
        if (endDateTime != null ? !endDateTime.equals(that.endDateTime) : that.endDateTime != null) return false;
        return performerNames != null ? performerNames.equals(that.performerNames) : that.performerNames == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (performanceNumber ^ (performanceNumber >>> 32));
        result = 31 * result + (event != null ? event.hashCode() : 0);
        result = 31 * result + (venueAddress != null ? venueAddress.hashCode() : 0);
        result = 31 * result + (startDateTime != null ? startDateTime.hashCode() : 0);
        result = 31 * result + (endDateTime != null ? endDateTime.hashCode() : 0);
        result = 31 * result + (performerNames != null ? performerNames.hashCode() : 0);
        result = 31 * result + (hasSocialDistancing ? 1 : 0);
        result = 31 * result + (hasAirFiltration ? 1 : 0);
        result = 31 * result + (isOutdoors ? 1 : 0);
        result = 31 * result + capacityLimit;
        result = 31 * result + venueSize;
        return result;
    }
}
