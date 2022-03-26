package state;

import model.*;

import java.time.LocalDateTime;
import java.util.List;

public class EventState implements IEventState {


    public EventState() {

    };

    public EventState(IEventState other) {

    };

    @Override
    public Event getAllEvents() {

    };

    @Override
    public Event findEventByNumber(long eventNumber) {

    };

    @Override
    public NonTicketedEvent createNonTicketedEvent(EntertainmentProvider organiser, String title, EventType type) {

    };

    @Override
    public TicketedEvent createTicketedEvent(EntertainmentProvider organiser, String title, EventType type, double ticketPrice, int numTickets) {

    };

    @Override
    public EventPerformance createEventPerformance(Event event, String venueAddress, LocalDateTime startDateTime, LocalDateTime endDateTime, List<String> performerNames, boolean hasSocialDistancing, boolean hasAirFiltration, boolean isOutdoors, int capacityLimit, int venueSize) {

    };

}