package external;

import java.time.LocalDateTime;

public class MockEntertainmentProviderSystem implements EntertainmentProviderSystem {

    public MockEntertainmentProviderSystem(String orgName, String orgAddress) {

    };

    @Override
    public void recordNewEvent(long eventNumber, String title, int numTickets) {

    };

    @Override
    public void cancelEvent(long eventNumber, String message) {

    };

    @Override
    public void recordNewPerformance(long eventNumber, long performanceNumber, LocalDateTime startDateTime, LocalDateTime endDateTime) {

    };

    @Override
    public int getNumTicketsLeft(long eventNumber, long performanceNumber) {

    };

    @Override
    public void recordNewBooking(long eventNumber, long performanceNumber, long bookingNumber, String consumerName, String consumerEmail, int bookedTickets) {

    };

    @Override
    public void cancelBooking(long bookingNumber) {

    };

    @Override
    public void recordSponsorshipAcceptance(long eventNumber, int sponsoredPricePercent) {

    };

    @Override
    public void recordSponsorshipRejection(long eventNumber) {

    };

}