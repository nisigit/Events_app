package command;

import controller.Context;
import model.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class GovernmentReport1Command implements ICommand {

    private List<Booking> result;
    private LocalDateTime intervalStartInclusive, intervalEndInclusive;

    /**
     *
     * @param intervalStartInclusive indicates the start time of the interval provided
     * @param intervalEndInclusive indicates the end time of the interval provided
     */

    public GovernmentReport1Command(LocalDateTime intervalStartInclusive, LocalDateTime intervalEndInclusive) {
        this.intervalStartInclusive = intervalStartInclusive;
        this.intervalEndInclusive = intervalEndInclusive;
        this.result = new ArrayList<>();
    }

    /**
     * This method should not be called directly outside of testing.
     * @param context object that provides access to global application state
     * Verifies that:
     *                Current user is logged in
     *                Current user is a government representative
     *                intervalStartInclusive provided is before intervalEndInclusive
     *                The event is active
     *                The event is sponsored
     */

    @Override
    public void execute(Context context) {
        // Condition checks
        List<Event> sponsoredEvents = new ArrayList<>();
        List<SponsorshipRequest> sponsorshipRequests = context.getSponsorshipState().getAllSponsorshipRequests();
        User user = context.getUserState().getCurrentUser();
        if (user == null) {
            result = null;
            return;
        }
        if (!(user instanceof  GovernmentRepresentative)) {
            result = null;
            return;
        }
        if (intervalStartInclusive.isAfter(intervalEndInclusive)) {
            result = null;
            return;
        }

        // Filter all the events not sponsored
        for (SponsorshipRequest sponsorshipRequest: sponsorshipRequests){
            sponsoredEvents.add(sponsorshipRequest.getEvent());
        }

        // Filter the bookings as specified
        for (Event event: sponsoredEvents){
            if (event.getStatus() == EventStatus.ACTIVE) {
                Collection<EventPerformance> performances = event.getPerformances();
                for (EventPerformance performance: performances) {
                    if (performance.getStartDateTime().isAfter(intervalStartInclusive) &&
                            performance.getEndDateTime().isBefore(intervalEndInclusive)) {
                        long eventNumber = event.getEventNumber();
                        List<Booking> bookings = context.getBookingState().findBookingsByEventNumber(eventNumber);
                        for (Booking booking: bookings) {
                            if (booking.getEventPerformance() == performance) {
                                result.add(booking);
                            }
                        }
                    }
                }
            }
        }

    }

    /**
     * Get the result from the latest run of the command.
     * @return list of Bookings if successful, and null for failed conditions
     */

    @Override
    public List<Booking> getResult() {
        return result;
    }

}