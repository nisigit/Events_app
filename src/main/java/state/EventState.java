package state;

import model.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class EventState implements IEventState {

    private List<Event> events;
    private long uniqueEventNumber;
    private int performanceNumber;


    public EventState() {
        this.events = new ArrayList<>();
        this.performanceNumber = 1;
        this.uniqueEventNumber = 1;
    }

    public EventState(IEventState other) {
        new EventState();
        this.events = other.getAllEvents();
    }

    @Override
    public List<Event> getAllEvents() {
        return this.events;
    }

    @Override
    public Event findEventByNumber(long eventNumber) {
        for (Event event : events) {
            if (event.getEventNumber() == eventNumber) {
                return event;
            }
        }
        return null;
    }

    @Override
    public NonTicketedEvent createNonTicketedEvent(EntertainmentProvider organiser, String title, EventType type) {
        NonTicketedEvent nonTicketedEvent = new NonTicketedEvent(uniqueEventNumber++, organiser, title, type);
        events.add(nonTicketedEvent);
        return nonTicketedEvent;
    }

    @Override
    public TicketedEvent createTicketedEvent(EntertainmentProvider organiser, String title, EventType type, double ticketPrice, int numTickets) {
        TicketedEvent ticketedEvent = new TicketedEvent(uniqueEventNumber++, organiser, title, type, ticketPrice, numTickets);
        events.add(ticketedEvent);
        return ticketedEvent;
    }

    @Override
    public EventPerformance createEventPerformance(Event event, String venueAddress, LocalDateTime startDateTime, LocalDateTime endDateTime, List<String> performerNames, boolean hasSocialDistancing, boolean hasAirFiltration, boolean isOutdoors, int capacityLimit, int venueSize) {
        EventPerformance newPerformance =  new EventPerformance(performanceNumber++, event, venueAddress, startDateTime, endDateTime, performerNames, hasSocialDistancing, hasAirFiltration, isOutdoors, capacityLimit, venueSize);
        event.addPerformance(newPerformance);
        return newPerformance;
    }

}