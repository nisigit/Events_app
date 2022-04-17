package command;

import controller.Context;
import logging.Logger;
import model.*;

import java.util.List;

public class ListEventBookingsCommand implements ICommand {

    enum LogStatus {
        LIST_EVENT_BOOKINGS_USER_NOT_LOGGED_IN,
        LIST_EVENT_BOOKINGS_EVENT_NOT_TICKETED,
        LIST_EVENT_BOOKINGS_SUCCESS,
        LIST_EVENT_BOOKINGS_EVENT_NOT_FOUND,
        LIST_EVENT_BOOKINGS_USER_NOT_ORGANISER_NOR_GOV,
    }

    private long eventNumber;
    private List<Booking> bookingListResult;

    public ListEventBookingsCommand(long eventNumber) {
        this.eventNumber = eventNumber;
        this.bookingListResult = null;
    }

    @Override
    public void execute(Context context) {
        // Condition checks
        User user = context.getUserState().getCurrentUser();
        Event event = context.getEventState().findEventByNumber(this.eventNumber);

        // Using assertions to check conditions

//        assert (user == null): "User is not logger in";
//        assert (event == null): "Event is not found";
//        assert (!(event instanceof TicketedEvent)): "Event is not ticketed";
//        assert (!(user instanceof GovernmentRepresentative || user.equals(event.getOrganiser()))): "Current user is neither a government rep or the organiser";

        if (user == null) {
            Logger.getInstance().logAction("ListEventsBookingCommand", LogStatus.LIST_EVENT_BOOKINGS_USER_NOT_LOGGED_IN);
            return;
        }
        if (event == null) {
            Logger.getInstance().logAction("ListEventsBookingCommand", LogStatus.LIST_EVENT_BOOKINGS_EVENT_NOT_FOUND);
            return;
        }
        if (!(event instanceof TicketedEvent)) {
            Logger.getInstance().logAction("ListEventsBookingCommand", LogStatus.LIST_EVENT_BOOKINGS_EVENT_NOT_TICKETED);
            return;
        }

        if (!(user instanceof GovernmentRepresentative || user.equals(event.getOrganiser()))) {
            Logger.getInstance().logAction("ListEventsBookingCommand", LogStatus.LIST_EVENT_BOOKINGS_USER_NOT_ORGANISER_NOR_GOV);
            return;
        }
        // Set the result to the bookings if all the conditions are fulfilled
        bookingListResult = context.getBookingState().findBookingsByEventNumber(this.eventNumber);

        Logger.getInstance().logAction("ListEventsBookingCommand", LogStatus.LIST_EVENT_BOOKINGS_SUCCESS);
    }

    @Override
    public List<Booking> getResult() {
        return bookingListResult;
    }

}