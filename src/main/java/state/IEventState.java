package state;

import model.*;

public interface IEventState {

    Event getAllEvents();

    Event findEventByNumber(long eventNumber);

    NonTicketedEvent createNonTicketedEvent(EntertainmentProvider organiser, String title, EventType type);

    TicketedEvent createTicketedEvent(EntertainmentProvider organiser, String title, EventType type, double ticketPrice, int numTickets);

    EventPerformance createEventPerformance(Event event, String venueAddress, LocalDateTime startDateTime, LocalDateTime endDateTime, List<String> performerNames, boolean hasSocialDistancing, boolean hasAirFiltration, boolean isOutdoors, int capacityLimit, int venueSize);

}