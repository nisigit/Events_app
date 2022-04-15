package external;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MockEntertainmentProviderSystem implements EntertainmentProviderSystem {

    private String orgName;
    private String orgAddress;
    private Map<Long, Integer> eventTickets;
    private Map<Long, List<Long>> eventPerformances;
    private Map<Long, List<Long>> performanceBookings;
    private Map<Long, Integer> acceptedSponsorships;
    private List<Long> rejectedSponsorships;

    public MockEntertainmentProviderSystem(String orgName, String orgAddress) {
        eventTickets = new HashMap<>();
        eventPerformances = new HashMap<>();
        performanceBookings = new HashMap<>();
        acceptedSponsorships = new HashMap<>();
        rejectedSponsorships = new ArrayList<>();
        this.orgName = orgName;
        this.orgAddress = orgAddress;
    }

    @Override
    public void recordNewEvent(long eventNumber, String title, int numTickets) {
        eventTickets.put(eventNumber, numTickets);
        eventPerformances.put(eventNumber, new ArrayList<>());
        // globalNrTickets.put(eventNumber, numTickets);
        System.out.println("A new event is created with the following details: ");
        System.out.println("Event Number: " + eventNumber);
        System.out.println("Event Title: " + title);
        System.out.println("Number of tickets: " + numTickets);
        System.out.println();
    }

    @Override
    public void cancelEvent(long eventNumber, String message) {
        eventTickets.remove(eventNumber);
        System.out.println("The event with the following details has been cancelled: ");
        System.out.println("Event number: " + eventNumber);
        System.out.println("Message: " + message);
        System.out.println();
    }

    @Override
    public void recordNewPerformance(long eventNumber, long performanceNumber, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        eventPerformances.get(eventNumber).add(performanceNumber);
        performanceBookings.put(performanceNumber, new ArrayList<>());
        System.out.println("A new event performance has been added to the event with the following details");
        System.out.println("Event number: " + eventNumber);
        System.out.println("Performance number in event: " + performanceNumber);
        System.out.println("Performance start time: " + startDateTime.toString());
        System.out.println("Performance end time: " + endDateTime.toString());
    }

    @Override
    public int getNumTicketsLeft(long eventNumber, long performanceNumber) {
        // -1 is the performanceNumber value used internally when event information needs to be displayed in the View Event use case
        if (eventPerformances.get(eventNumber).contains(performanceNumber) || performanceNumber == -1) {
            int ticketsLeft = eventTickets.get(eventNumber);
            System.out.println("There are " + ticketsLeft + " tickets left for current performance provided");
            return ticketsLeft;
        }
        else {
            System.out.println("This event does not have a performance with this number");
            return -1;
        }
    }

    @Override
    public void recordNewBooking(long eventNumber, long performanceNumber, long bookingNumber, String consumerName, String consumerEmail, int bookedTickets) {
        int x = getNumTicketsLeft(eventNumber, performanceNumber);
        eventTickets.replace(eventNumber, x - bookedTickets);
        performanceBookings.get(performanceNumber).add(bookingNumber);
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
        for (long i : performanceBookings.keySet()) {
            if (performanceBookings.get(i).contains(bookingNumber)) {
                performanceBookings.get(i).remove(bookingNumber);
                System.out.println("The booking was cancelled successfully");
                return;
            }
        }
        System.out.println("Error: the booking does not exist");
    }

    @Override
    public void recordSponsorshipAcceptance(long eventNumber, int sponsoredPricePercent) {
        if (eventTickets.containsKey(eventNumber)) {
            acceptedSponsorships.put(eventNumber, sponsoredPricePercent);
            System.out.println("The sponsorship acceptance was recorded successfully");
        }
        else {
            System.out.println("Error: this event does not exist in our system");
        }
    }

    @Override
    public void recordSponsorshipRejection(long eventNumber) {
        if (eventTickets.containsKey(eventNumber)) {
            rejectedSponsorships.add(eventNumber);
            System.out.println("The sponsorship rejection was recorded successfully");
        }
        else {
            System.out.println("Error: this event does not exist in our system");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MockEntertainmentProviderSystem that = (MockEntertainmentProviderSystem) o;

        if (orgName != null ? !orgName.equals(that.orgName) : that.orgName != null) return false;
        if (orgAddress != null ? !orgAddress.equals(that.orgAddress) : that.orgAddress != null) return false;
        if (eventTickets != null ? !eventTickets.equals(that.eventTickets) : that.eventTickets != null) return false;
        return eventPerformances != null ? eventPerformances.equals(that.eventPerformances) : that.eventPerformances == null;
    }

    @Override
    public int hashCode() {
        int result = orgName != null ? orgName.hashCode() : 0;
        result = 31 * result + (orgAddress != null ? orgAddress.hashCode() : 0);
        result = 31 * result + (eventTickets != null ? eventTickets.hashCode() : 0);
        result = 31 * result + (eventPerformances != null ? eventPerformances.hashCode() : 0);
        return result;
    }
}