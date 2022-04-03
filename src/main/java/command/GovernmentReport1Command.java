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
     *
     */

    @Override
    public void execute(Context context) {
        List<Event> sponsoredEvents = new ArrayList<>();
        List<SponsorshipRequest> sponsorshipRequests = context.getSponsorshipState().getAllSponsorshipRequests();


        for (SponsorshipRequest sponsorshipRequest: sponsorshipRequests){
            sponsoredEvents.add(sponsorshipRequest.getEvent());
        }


        //TODO: Just finish main actions, see if there's more need to be checked?
        //TODO: Also, this looks a bit time-consuming, discuss if it can be improved
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
     * @return
     */

    @Override
    public List<Booking> getResult() {
        return result;
    };

}