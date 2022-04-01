package command;

import controller.Context;
import model.*;
import state.IEventState;
import state.ISponsorshipState;

public class CreateTicketedEventCommand extends CreateEventCommand {

    private int numTickets;
    private double ticketPrice;
    private boolean requestSponsorship;

    public CreateTicketedEventCommand(String title, EventType type, int numTickets, double ticketPrice, boolean requestSponsorship) {
        super(title, type);
        this.numTickets = numTickets;
        this.ticketPrice = ticketPrice;
        this.requestSponsorship = requestSponsorship;
    }

    public void execute(Context context) {
        eventNumberResult = null;
        if(!isUserAllowedToCreateEvent(context)) {
            return;
        }

        IEventState eventState = context.getEventState();
        User user = context.getUserState().getCurrentUser();
        TicketedEvent ticketedEvent = eventState.createTicketedEvent((EntertainmentProvider) user, title, type, ticketPrice, numTickets);

        if (requestSponsorship) {
            ISponsorshipState sponsorshipState = context.getSponsorshipState();
            SponsorshipRequest request = sponsorshipState.addSponsorshipRequest(ticketedEvent);
            ticketedEvent.setSponsorshipRequest(request);
        }
        eventNumberResult = ticketedEvent.getEventNumber();
    }

}