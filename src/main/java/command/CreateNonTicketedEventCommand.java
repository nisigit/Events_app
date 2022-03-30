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
        User user = context.getUserState().getCurrentUser();
        if (!(user instanceof EntertainmentProvider)) {
            return;
        }

        IEventState eventState = context.getEventState();
        NonTicketedEvent nonTicketedEvent = eventState.createNonTicketedEvent((EntertainmentProvider) user, this.title,
                this.type);

        this.eventNumberResult = nonTicketedEvent.getEventNumber();
    }
}