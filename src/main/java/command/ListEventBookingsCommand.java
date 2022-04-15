package command;

import controller.Context;
import logging.Logger;
import model.*;

import java.util.List;

public class ListEventBookingsCommand implements ICommand {

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
        if (user == null) return;
        if (event == null) return;
        if (!(event instanceof TicketedEvent)) return;
        // Set the result to the bookings if all the conditions are fulfilled
        if (user instanceof GovernmentRepresentative || user.equals(event.getOrganiser())) {
            bookingListResult = context.getBookingState().findBookingsByEventNumber(this.eventNumber);
        }

        Logger.getInstance().logAction("ListEventsBookingCommand", bookingListResult);
    }

    @Override
    public List<Booking> getResult() {
        return bookingListResult;
    }

}