package command;

import controller.Context;
import model.*;

import java.util.List;

public class ListEventBookingsCommand implements ICommand {

    private long eventNumber;
    private List<Booking> result;

    public ListEventBookingsCommand(long eventNumber) {
        this.eventNumber = eventNumber;
    }

    @Override
    public void execute(Context context) {
        result = null;
        User user = context.getUserState().getCurrentUser();
        Event event = context.getEventState().findEventByNumber(this.eventNumber);
        if (event == null) return;
        if (!(event instanceof TicketedEvent)) return;
        if (user instanceof GovernmentRepresentative || user.equals(event.getOrganiser())) {
            result = context.getBookingState().findBookingsByEventNumber(this.eventNumber);
        }
        else return;
    }

    @Override
    public List<Booking> getResult() {
        return result;
    }

}