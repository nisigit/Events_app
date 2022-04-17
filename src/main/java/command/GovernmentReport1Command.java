package command;

import controller.Context;
import logging.Logger;
import model.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Returns a list of bookings for event performances within a specified time interval, if the current user
 * is a government representative
 */
public class GovernmentReport1Command implements ICommand {

    enum LogStatus {
        GOVERNMENT_REPORT1_NOT_LOGGED_IN,
        GOVERNMENT_REPORT1_USER_NOT_GOVERNMENT_REPRESENTATIVE,
        GOVERNMENT_REPORT1_START_AFTER_END,
        GOVERNMENT_REPORT1_SUCCESS
    }

    private List<Booking> bookingListResult;
    private LocalDateTime intervalStartInclusive, intervalEndInclusive;

    /**
     * Create new instance of government report command, with given parameters
     * @param intervalStartInclusive indicates the start time of the interval provided
     * @param intervalEndInclusive indicates the end time of the interval provided
     */

    public GovernmentReport1Command(LocalDateTime intervalStartInclusive, LocalDateTime intervalEndInclusive) {
        this.intervalStartInclusive = intervalStartInclusive;
        this.intervalEndInclusive = intervalEndInclusive;
        this.bookingListResult = new ArrayList<>();
    }

    /**
     * This method should not be called directly outside of testing.
     * @param context object that provides access to global application state <br>
     *                <br>
     * Verifies that: <ul>
     * <li>Current user is logged in</li>
     * <li>Current user is a government representative </li>
     * <li>intervalStartInclusive provided is before intervalEndInclusive</li>
     * <li>The events returned are active and sponsored</li>   
     * </ul>
     */

    @Override
    public void execute(Context context) {
        List<Event> sponsoredEvents = new ArrayList<>();
        List<SponsorshipRequest> sponsorshipRequests = context.getSponsorshipState().getAllSponsorshipRequests();
        User user = context.getUserState().getCurrentUser();

        // condition checks as described in docs - abort execution if condition is satisfied
        if (user == null) {
            bookingListResult = null;
            Logger.getInstance().logAction("GovernmentReport1Command", LogStatus.GOVERNMENT_REPORT1_NOT_LOGGED_IN);
            return;
        }

        if ((!(user instanceof  GovernmentRepresentative))) {
            bookingListResult = null;
            Logger.getInstance().logAction("GovernmentReport1Command", LogStatus.GOVERNMENT_REPORT1_USER_NOT_GOVERNMENT_REPRESENTATIVE);
            return;
        }

        if ((intervalStartInclusive.isAfter(intervalEndInclusive))) {
            bookingListResult = null;
            Logger.getInstance().logAction("GovernmentReport1Command", LogStatus.GOVERNMENT_REPORT1_START_AFTER_END);
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
                    LocalDateTime performanceStart = performance.getStartDateTime();
                    LocalDateTime performanceEnd = performance.getEndDateTime();
                    if ((performanceStart.isAfter(intervalStartInclusive) || performanceStart.equals(intervalStartInclusive)) &&
                            (performanceEnd.isBefore(intervalEndInclusive) || performanceEnd.equals(intervalEndInclusive))) {
                        long eventNumber = event.getEventNumber();
                        List<Booking> bookings = context.getBookingState().findBookingsByEventNumber(eventNumber);
                        for (Booking booking: bookings) {
                            if (booking.getEventPerformance() == performance) {
                                bookingListResult.add(booking);
                            }
                        }
                    }
                }
            }
        }

        Logger.getInstance().logAction("GovernmentReport1Command", LogStatus.GOVERNMENT_REPORT1_SUCCESS);
    }

    /**
     * Get the result from the latest run of the command.
     * @return list of Bookings if execution was successful, and null otherwise
     */
    @Override
    public List<Booking> getResult() {
        return bookingListResult;
    }

}