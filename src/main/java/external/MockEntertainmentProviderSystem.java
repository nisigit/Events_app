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
    private Map<Long, Integer> globalNrTickets;

    public MockEntertainmentProviderSystem(String orgName, String orgAddress) {
        eventPerformanceTickets = new HashMap<>();
        globalNrTickets = new HashMap<>();
        this.orgName = orgName;
        this.orgAddress = orgAddress;
    }

    @Override
    public void recordNewEvent(long eventNumber, String title, int numTickets) {
        eventPerformanceTickets.put(eventNumber, new HashMap<>());
        globalNrTickets.put(eventNumber, numTickets);
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
        int nrTickets = globalNrTickets.get(eventNumber);
        eventPerformanceTickets.get(eventNumber).put(performanceNumber, nrTickets);
        System.out.println("A new event performance has been added to the event with the following details");
        System.out.println("Event number: " + eventNumber);
        System.out.println("Performance number in event: " + performanceNumber);
        System.out.println("Performance start time: " + startDateTime.toString());
        System.out.println("Performance end time: " + endDateTime.toString());
    }

    @Override
    public int getNumTicketsLeft(long eventNumber, long performanceNumber) {
        int ticketsLeft = globalNrTickets.get(eventNumber);
        Map<Long, Integer> performanceTickets = eventPerformanceTickets.get(eventNumber);
        for (Long currPerformanceNumber: performanceTickets.keySet()) {
            if (currPerformanceNumber != performanceNumber) {
                ticketsLeft -= performanceTickets.get(currPerformanceNumber);
            }
        }
        System.out.println("There are " + ticketsLeft + " tickets left for current performance provided");
        return ticketsLeft;
    }

    @Override
    public void recordNewBooking(long eventNumber, long performanceNumber, long bookingNumber, String consumerName, String consumerEmail, int bookedTickets) {
        int x = getNumTicketsLeft(eventNumber, performanceNumber);
        eventPerformanceTickets.get(eventNumber).replace(performanceNumber, x, x - bookedTickets);
        System.out.println("New booking was made with the following details: ");
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MockEntertainmentProviderSystem that = (MockEntertainmentProviderSystem) o;

        if (orgName != null ? !orgName.equals(that.orgName) : that.orgName != null) return false;
        if (orgAddress != null ? !orgAddress.equals(that.orgAddress) : that.orgAddress != null) return false;
        if (eventPerformanceTickets != null ? !eventPerformanceTickets.equals(that.eventPerformanceTickets) : that.eventPerformanceTickets != null)
            return false;
        return globalNrTickets != null ? globalNrTickets.equals(that.globalNrTickets) : that.globalNrTickets == null;
    }

    @Override
    public int hashCode() {
        int result = orgName != null ? orgName.hashCode() : 0;
        result = 31 * result + (orgAddress != null ? orgAddress.hashCode() : 0);
        result = 31 * result + (eventPerformanceTickets != null ? eventPerformanceTickets.hashCode() : 0);
        result = 31 * result + (globalNrTickets != null ? globalNrTickets.hashCode() : 0);
        return result;
    }
}