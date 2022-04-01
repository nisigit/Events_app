package command;

import controller.Context;
import model.EntertainmentProvider;
import model.EventType;
import model.NonTicketedEvent;
import model.User;
import state.IEventState;

public class CreateNonTicketedEventCommand extends CreateEventCommand {

    public CreateNonTicketedEventCommand(String title, EventType type) {
        super(title, type);
    }

    public void execute(Context context) {
        eventNumberResult = null;
        if (!isUserAllowedToCreateEvent(context)) {
            return;
        }
        User user = context.getUserState().getCurrentUser();
        IEventState eventState = context.getEventState();

        NonTicketedEvent nonTicketedEvent = eventState.createNonTicketedEvent((EntertainmentProvider) user, this.title, this.type);
        ((EntertainmentProvider) user).addEvent(nonTicketedEvent);
        this.eventNumberResult = nonTicketedEvent.getEventNumber();
    }
}