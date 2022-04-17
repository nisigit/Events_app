package command;

import controller.Context;
import external.EntertainmentProviderSystem;
import logging.Logger;
import model.*;
import state.IEventState;
import state.ISponsorshipState;

public class CreateTicketedEventCommand extends CreateEventCommand {

    enum LogStatus {
        CREATE_TICKETED_EVENT_SUCCESS,
        CREATE_EVENT_REQUESTED_SPONSORSHIP
    }

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
        // Condition checks
        if(!isUserAllowedToCreateEvent(context)) {
            return;
        }

        IEventState eventState = context.getEventState();
        User user = context.getUserState().getCurrentUser();
        // Cast user to EntertainmentProvider since we have checked it
        TicketedEvent ticketedEvent = eventState.createTicketedEvent((EntertainmentProvider) user, title, type, ticketPrice, numTickets);

        eventNumberResult = ticketedEvent.getEventNumber();

        Logger.getInstance().logAction("CreateTicketedEventCommand", LogStatus.CREATE_TICKETED_EVENT_SUCCESS);

        if (requestSponsorship) {
            SponsorshipRequest request = context.getSponsorshipState().addSponsorshipRequest(ticketedEvent);
            ticketedEvent.setSponsorshipRequest(request);
            Logger.getInstance().logAction("CreateTicketedEventCommand", LogStatus.CREATE_EVENT_REQUESTED_SPONSORSHIP);
        }
    }

}