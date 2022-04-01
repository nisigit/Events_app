package external;

import model.EntertainmentProvider;
import model.Event;
import model.TicketedEvent;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class MockEntertainmentProviderSystem implements EntertainmentProviderSystem {

    private String orgName;
    private String orgAddress;
    private Map<Long, Map<Long, Integer>> eventPerformanceTickets;

    public MockEntertainmentProviderSystem(String orgName, String orgAddress) {
        eventPerformanceTickets = new HashMap<>();
        this.orgName = orgName;
        this.orgAddress = orgAddress;
    }

    @Override
    public void recordNewEvent(long eventNumber, String title, int numTickets) {
        eventPerformanceTickets.put(eventNumber, new HashMap<>());
        System.out.println("A new event is created with the following details: ");
        System.out.println("Event Number: " + eventNumber);
        System.out.println("Event Title: " + title);
        System.out.println("Number of tickets: " + numTickets);
        System.out.println();
    }

    @Override
    public void cancelEvent(long eventNumber, String message) {
        eventPerformanceTickets.remove(eventNumber);
        System.out.println("The event with the following details has been cancelled: ");
        System.out.println("Event number: " + eventNumber);
        System.out.println("Message: " + message);
        System.out.println();
    }

    @Override
    public void recordNewPerformance(long eventNumber, long performanceNumber, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        eventPerformanceTickets.get(eventNumber).put(performanceNumber, 0);
        System.out.println("A new event performance has been added to the event with the following details");
        System.out.println("Event number: " + eventNumber);
        System.out.println("Performance number in event: " + performanceNumber);
        System.out.println("Performance start time: " + startDateTime.toString());
        System.out.println("Performance end time: " + endDateTime.toString());
    }

    @Override
    public int getNumTicketsLeft(long eventNumber, long performanceNumber) {
        return eventPerformanceTickets.get(eventNumber).get(performanceNumber);
    }

    @Override
    public void recordNewBooking(long eventNumber, long performanceNumber, long bookingNumber, String consumerName, String consumerEmail, int bookedTickets) {
        System.out.println("New performance was made for a performance with the following details: ");
        System.out.println("Event number: " + eventNumber);
        System.out.println("Performance number: " + performanceNumber);
        System.out.println("Booking number: " + bookingNumber);
        System.out.println("Booker name: " + consumerName);
        System.out.println("Booker email: " + consumerEmail);
        System.out.println("Number of tickets booked: " + bookedTickets);
        System.out.println();
    }

    @Override
    public void cancelBooking(long bookingNumber) {

    }

    @Override
    public void recordSponsorshipAcceptance(long eventNumber, int sponsoredPricePercent) {

    }

    @Override
    public void recordSponsorshipRejection(long eventNumber) {

    }

}