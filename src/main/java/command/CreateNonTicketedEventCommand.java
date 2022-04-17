package command;

import controller.Context;
import external.EntertainmentProviderSystem;
import logging.Logger;
import model.EntertainmentProvider;
import model.EventType;
import model.NonTicketedEvent;
import model.User;
import state.IEventState;

public class CreateNonTicketedEventCommand extends CreateEventCommand {

    enum LogStatus{
        CREATE_NON_TICKETED_EVENT_SUCCESS
    }

    public CreateNonTicketedEventCommand(String title, EventType type) {
        super(title, type);
    }

    public void execute(Context context) {
        // Condition checks
        if (!isUserAllowedToCreateEvent(context)) {
            return;
        }
        User user = context.getUserState().getCurrentUser();
        IEventState eventState = context.getEventState();

        // As we have checked if the user is an entertainment provider, we cast it here
        NonTicketedEvent nonTicketedEvent = eventState.createNonTicketedEvent((EntertainmentProvider) user, this.title, this.type);
        this.eventNumberResult = nonTicketedEvent.getEventNumber();

        Logger.getInstance().logAction("CreateNonTicketedEventCommand", LogStatus.CREATE_NON_TICKETED_EVENT_SUCCESS);
    }
}